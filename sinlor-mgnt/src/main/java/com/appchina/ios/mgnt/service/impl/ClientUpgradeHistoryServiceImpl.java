// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import com.appchina.ios.core.cahe.model.app.IpaFileResp;
import com.appchina.ios.core.dto.app.ClientUpgradeInfo;
import com.appchina.ios.core.dto.app.IpaCertSignature;
import com.appchina.ios.core.dto.app.IpaItunesMetaData;
import com.appchina.ios.core.dto.system.ApplePlatform;
import com.appchina.ios.core.service.impl.PlistGenerator;
import com.appchina.ios.core.utils.IpaReaderUtils;
import com.appchina.ios.mgnt.controller.model.ClientIpaUploadParameter;
import com.appchina.ios.mgnt.dao.ClientUpgradeHistoryDao;
import com.appchina.ios.mgnt.dto.ClientUpgradeHistory;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.ClientUpgradeHistoryService;
import com.appchina.ios.mgnt.service.DaemonApiService;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.utils.IOUtils;
import com.appchina.ios.web.utils.UrlUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Service("clientUpgradeHistoryService")
public class ClientUpgradeHistoryServiceImpl implements ClientUpgradeHistoryService {
    private static final Logger log = Logger.getLogger(ClientUpgradeHistoryServiceImpl.class);
    @Autowired
    private ClientUpgradeHistoryDao clientUpgradeHistoryDao;
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private DaemonApiService daemonApiService;
    @Value("${ios.mgnt.testipauploadpath}")
    private String testIpaUploadPath;
    @Value("${ios.mgnt.productipauploadpath}")
    private String productIpaUploadPath;
    @Autowired
    private PlistGenerator plistGenerator;
    private String installIcon = "";
    private String title = "";
    private String ituneIcon = "";

    @Override
    public long count(String channel, Integer test, Integer status) throws ServiceException {
        try {
            return clientUpgradeHistoryDao.count(channel, test, status);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public List<ClientUpgradeHistory> list(String channel, Integer test, Integer status, int start, int size)
            throws ServiceException {
        ApiRespWrapper<String> plistHost = backendApiService.getPlistHostUrl();
        try {
            List<ClientUpgradeHistory> ret = clientUpgradeHistoryDao.queryList(channel, test, status, start, size);
            for (ClientUpgradeHistory clientUpgradeHistory : ret) {
                if (clientUpgradeHistory.getPlist() == null) {
                    continue;
                }
                clientUpgradeHistory.setPlist(UrlUtils.spliceUrl(plistHost.getData(), clientUpgradeHistory.getPlist()));
            }
            return ret;
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public void saveIpaFile(String upgradeInfo, Integer test) throws ServiceException {
        File file = null;
        if (test != null && test == ClientIpaUploadParameter.ISTEST) {
            file = new File(testIpaUploadPath);
        } else {
            test = ClientIpaUploadParameter.ISPRODUCT;
            file = new File(productIpaUploadPath);
        }
        for (File localeFile : file.listFiles()) {
            ClientUpgradeHistory history = new ClientUpgradeHistory();
            history.setTest(test);
            history.setUpgradeInfo(upgradeInfo);
            history.setFilePath(localeFile.getAbsolutePath());
            try {
                parseLocalFile(localeFile, history);
            } catch (Exception e) {
                history.setStatus(ClientUpgradeHistory.STATUS_DEL);
                history.setInfo(e.getMessage());
                log.error("Parse local file failed. Errmsg:" + e.getMessage(), e);
            } finally {
                try {
                    clientUpgradeHistoryDao.insertOrUpdate(history);
                    if (history.getStatus() != ClientUpgradeHistory.STATUS_DEL) {
                        // 删除此ipa文件
                        localeFile.delete();
                    }
                } catch (SQLException e) {
                    throw ServiceException.getSQLException(e.getMessage());
                }
            }
        }
    }

    private void parseLocalFile(File localeFile, ClientUpgradeHistory history) {
        String fileName = localeFile.getName();
        if (!fileName.contains("ipa.")) {
            throw ServiceException.getInternalException("错误的文件全名，请以***.ipa.渠道名 命名.FileName:" + fileName);
        }

        String ipaChannel = fileName.substring(fileName.lastIndexOf("ipa.") + 4);

        String md5;
        try {
            md5 = IOUtils.getMd5(localeFile);
        } catch (Exception e) {
            throw ServiceException.getInternalException("无法获取文件的MD5值.");
        }
        IpaItunesMetaData metaInfo = null;
        try {
            Pair<IpaItunesMetaData, IpaCertSignature> ipaInfoPair = IpaReaderUtils.buildIpaInfo(localeFile, null);
            metaInfo = ipaInfoPair == null ? null : ipaInfoPair.getLeft();
        } catch (Exception e) {
            throw ServiceException.getInternalException("无法解析上传的ipa文件.");
        }
        if (metaInfo == null) {
            throw ServiceException.getInternalException("无法解析上传的ipa文件.其不存企业包或不存在版本号.");
        }
        buildHistory(metaInfo, history);
        ApiRespWrapper<IpaFileResp> filePathResp = daemonApiService.getEnterpriseIpaFilePath(metaInfo.getSignatures(),
                md5);
        if (filePathResp == null || filePathResp.getData() == null) {
            throw ServiceException.getInternalException("无法获取文件迁移地址.");
        }
        String plistFile = plistGenerator.generate(md5, metaInfo.getBundleId(), filePathResp.getData().getFileUrl(),
                installIcon, ipaChannel + "_" + metaInfo.getBundleShortVersion(), title, ituneIcon, false);
        if (StringUtils.isEmpty(plistFile)) {
            throw ServiceException.getInternalException("生成Plist文件失败.");
        }
        moveIpaToDownloadPath(localeFile, filePathResp.getData().getFilePath());
        history.setPlist(plistFile);
        history.setIpaMd5(md5);
        history.setIpaChannel(ipaChannel);
    }

    private void buildHistory(IpaItunesMetaData metaInfo, ClientUpgradeHistory history) {
        history.setBundleId(metaInfo.getBundleId());
        history.setReleaseDate(new Date());
        formatShortVersion(metaInfo, history);
        // 出于历史太2的原因，错误的将Beijing Zhanghui World Technology的uuid当做它的签名
        if (StringUtils.startsWithIgnoreCase(metaInfo.getSigName(), "Beijing Zhanghui World Technology Co., LTD")
                || metaInfo.getCertSerial() == 5626798103473061086l) {
            history.setSign("188e1cb3-f397-40e0-afd1-cb5fc445ca80");
        } else {
            history.setSign(String.valueOf(metaInfo.getCertSerial()));
        }
        history.setSupportDeviceFlag(ApplePlatform.ALL_DEVICE_SUPPORT);
        history.setSupportMinOs(metaInfo.getSupportMinOs());
        history.setVersion(metaInfo.getBundleShortVersion());
    }

    private void formatShortVersion(IpaItunesMetaData metaInfo, ClientUpgradeHistory histroy) {
        String bundleShortVersion = metaInfo.getBundleShortVersion();
        if (StringUtils.isEmpty(bundleShortVersion)) {
            bundleShortVersion = metaInfo.getBundleVersion();
            if (StringUtils.isEmpty(bundleShortVersion)) {
                throw ServiceException.getInternalException("未知的应用包版本号.");
            }
        }
        if (bundleShortVersion.contains(".")) {
            String version = bundleShortVersion.substring(bundleShortVersion.lastIndexOf(".") + 1);
            try {
                int shortVersion = Integer.parseInt(version);
                histroy.setShortVersion(shortVersion);
            } catch (Exception e) {
                throw ServiceException.getInternalException("未知的应用包版本号.Version:" + bundleShortVersion);
            }
        }

    }

    private void moveIpaToDownloadPath(File localFile, String ipaDownloadPath) {
        File downloadIpaFile = new File(ipaDownloadPath);
        if (downloadIpaFile.exists()) {
            throw ServiceException.getInternalException("文件已存在，请删除后再上传.Filepath:" + ipaDownloadPath);
        }
        // move to download path
        log.info("Move ipa file " + localFile.getAbsolutePath() + " to download path "
                + downloadIpaFile.getAbsolutePath() + ".");
        try {
            downloadIpaFile.getParentFile().mkdir();
            FileUtils.moveFile(localFile, downloadIpaFile);
            downloadIpaFile.setReadable(true, false);
        } catch (IOException e) {
            log.warn("Move ipa file from temp to download path failed.Errmsg:" + e.getMessage(), e);
            throw ServiceException.getInternalException("无法进行文件迁移.");
        }
    }

    @Override
    public int getTestIpaCount() {
        File file = new File(testIpaUploadPath);
        return file.listFiles().length;
    }

    @Override
    public int getProductIpaCount() {
        File file = new File(productIpaUploadPath);
        return file.listFiles().length;
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            clientUpgradeHistoryDao.delete(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public ClientUpgradeHistory get(int id) throws ServiceException {
        try {
            return clientUpgradeHistoryDao.queryObject(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public void publish(int id, int supportUpgrade, int acceptUpgrade, Integer upgradeCount) throws ServiceException {
        ClientUpgradeHistory data = get(id);
        if (data == null || data.getStatus() != ClientUpgradeHistory.STATUS_OK) {
            return;
        }
        data.setId(id);
        data.setSupportUpgrade(supportUpgrade);
        data.setAcceptUpgrade(acceptUpgrade);
        data.setUpgradeCount(upgradeCount);
        ClientUpgradeInfo info = convert(data);
        ApiRespWrapper<Boolean> ret = this.backendApiService.publishClient(info);
        if (ret != null && ret.getData() != null && ret.getData()) {
            data.setStatus(ClientUpgradeHistory.STATUS_PUBLISHED);
        } else {
            data.setInfo(ret == null ? "未知错误" : ret.getMessage());
            data.setStatus(ClientUpgradeHistory.STATUS_PUBLISH_FAILED);
        }
        try {
            clientUpgradeHistoryDao.updatePublishInfo(data);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }

    }

    private ClientUpgradeInfo convert(ClientUpgradeHistory data) {
        ClientUpgradeInfo info = new ClientUpgradeInfo();
        info.setAcceptUpgrade(data.getAcceptUpgrade());
        info.setBundleId(data.getBundleId());
        info.setChannel(data.getIpaChannel());
        info.setIpaMd5(data.getIpaMd5());
        info.setPlist(data.getPlist());
        info.setReleaseDate(data.getReleaseDate());
        info.setShortVersion(data.getShortVersion());
        info.setSign(data.getSign());
        info.setSupportDeviceFlag(data.getSupportDeviceFlag());
        info.setSupportMinOs(data.getSupportMinOs());
        info.setSupportUpgrade(data.getSupportUpgrade());
        info.setTest(data.getTest());
        info.setUpgradeCount(data.getUpgradeCount());
        info.setUpgradeInfo(data.getUpgradeInfo());
        info.setVersion(data.getVersion());
        return info;
    }
}
