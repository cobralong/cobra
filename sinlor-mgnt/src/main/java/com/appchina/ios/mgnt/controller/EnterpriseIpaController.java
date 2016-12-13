package com.appchina.ios.mgnt.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.app.EnterpriseIpaFile;
import com.appchina.ios.core.dto.app.IpaCertSignature;
import com.appchina.ios.core.dto.app.IpaItunesMetaData;
import com.appchina.ios.core.dto.app.IpaLoadStat;
import com.appchina.ios.core.dto.app.IpaPlist;
import com.appchina.ios.core.dto.app.MarketInfo;
import com.appchina.ios.mgnt.controller.model.IpaPlistModifyParameter;
import com.appchina.ios.mgnt.controller.model.IpaPlistParameter;
import com.appchina.ios.mgnt.controller.model.IpaUploadListParameter;
import com.appchina.ios.mgnt.controller.model.IpaLoadStatParameter;
import com.appchina.ios.mgnt.controller.model.MarketInfoParameter;
import com.appchina.ios.mgnt.controller.model.SignatureIpasParameter;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.DaemonApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;
import com.appchina.ios.web.utils.IOUtils;
import com.appchina.ios.web.utils.UploadFileUtils;

@Controller
@RequestMapping(value = "/auth/enterprise/*")
public class EnterpriseIpaController {
    @Autowired
    private DaemonApiService daemonApiService;
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/ipaupload", method = RequestMethod.GET)
    public String add(String errMsg, Model model) {
        return "enterprise/ipaupload.ftl";
    }

    @RequestMapping(value = "/able.json")
    @ResponseBody
    protected String able(int plistId, boolean able) {
        IpaPlistModifyParameter param = new IpaPlistModifyParameter();
        param.setPlistId(plistId);
        param.setAble(able);
        ApiRespWrapper<Boolean> resp = backendApiService.modifyPlsit(param);
        if (resp == null || resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/intervene.json")
    @ResponseBody
    protected String intervene(int plistId, boolean intervene) {
        IpaPlistModifyParameter param = new IpaPlistModifyParameter();
        param.setPlistId(plistId);
        param.setIntervene(intervene);
        ApiRespWrapper<Boolean> resp = backendApiService.modifyPlsit(param);
        if (resp == null || resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/ipaupload", method = RequestMethod.POST)
    public String add(@RequestParam(required = false) CommonsMultipartFile ipa, Model model) {
        // save to dir
        ApiRespWrapper<String> apiResp = daemonApiService.getEnterpriseTmpFilePath();
        String enterpriseTmpFilePath = null;
        boolean failed = true;
        String errMsg = "";
        if (apiResp == null || StringUtils.isEmpty(apiResp.getData())) {
            errMsg = "未知的路径";
        } else {
            enterpriseTmpFilePath = apiResp.getData();
        }
        String fileName = ipa.getOriginalFilename();
        if (!StringUtils.isEmpty(enterpriseTmpFilePath)) {
            try {
                saveFile(ipa, enterpriseTmpFilePath);
                failed = false;
            } catch (NoSuchAlgorithmException e) {
                errMsg = "不支持的算法.Errmsg:" + e.getMessage();
            } catch (IOException e) {
                errMsg = "系统IO错误。Errmsg:" + e.getMessage();
            } catch (Exception e) {
                errMsg = "未知错误.Errmsg:" + e.getMessage();
            }
        } else {
            errMsg = "上传失败，未知的存储路径.";
        }
        if (failed) {
            model.addAttribute("errMsg", errMsg);
        } else {
            model.addAttribute("resultMsg", "上传" + fileName + "成功，已加入待解析列表.");
        }
        return "enterprise/ipaupload.ftl";
    }

    private void saveFile(CommonsMultipartFile ipa, String enterpriseTmpFilePath) throws NoSuchAlgorithmException,
            IOException {
        // get md5
        String md5 = UploadFileUtils.getMd5(ipa);
        File outFile = new File(enterpriseTmpFilePath, md5 + ".ipa");
        UploadFileUtils.writeFile(ipa, outFile);
    }

    @RequestMapping(value = "/ipaplist")
    public String ipaplist(IpaPlistParameter para, Model model) {
        List<IpaPlist> values = null;
        if (para == null) {
            para = new IpaPlistParameter();
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<IpaPlist>> ret = backendApiService.getIpaPlists(para);

        para.getPager().setTotal(ret.getData().getTotalCount());
        values = ret.getData().getResultList();
        Map<String, ApplicationSimple> itemIdRootSimpleMap = new HashMap<String, ApplicationSimple>();;
        Map<String, ApplicationSimple> bundleIdRootSimpleMap = new HashMap<String, ApplicationSimple>();;
        if (values != null && !values.isEmpty()) {
            List<Integer> itemIds = initItemIds(values);
            List<String> bundleIds = initBundleIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> itemIdRootSimpleMapResp = backendApiService
                    .getRootSimpleByItemIds(itemIds);
            ApiRespWrapper<Map<String, ApplicationSimple>> bundleIdRootSimpleMapResp = backendApiService
                    .getRootIdByBundleIds(bundleIds);
            // List<Integer> rootIds = initRootIds(values);
            if (itemIdRootSimpleMapResp != null && itemIdRootSimpleMapResp.getData() != null
                    && itemIdRootSimpleMapResp.getData().size() > 0) {
                for (Entry<Integer, ApplicationSimple> entry : itemIdRootSimpleMapResp.getData().entrySet()) {
                    itemIdRootSimpleMap.put(entry.getKey().toString(), entry.getValue());
                }
            }
            if (bundleIdRootSimpleMapResp != null && bundleIdRootSimpleMapResp.getData() != null
                    && bundleIdRootSimpleMapResp.getData().size() > 0) {
                bundleIdRootSimpleMap.putAll(bundleIdRootSimpleMapResp.getData());
            }
        }
        ApiRespWrapper<ListWrapResp<MarketInfo>> marketInfoResp = this.backendApiService.getMarketInfos();
        if (marketInfoResp != null && marketInfoResp.getData() != null
                && marketInfoResp.getData().getResultList() != null) {
            model.addAttribute("marketinfos", buildMarketIdNames(marketInfoResp.getData().getResultList()));
        }
        formatEnterplistUrl(values);
        model.addAttribute("itemIdRootSimpleMap", itemIdRootSimpleMap);
        model.addAttribute("bundleIdRootSimpleMap", bundleIdRootSimpleMap);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("intervenes", IpaPlistParameter.INTERVENES);
        model.addAttribute("types", IpaPlistParameter.TYPES);
        model.addAttribute("status", IpaPlistParameter.STATUS);
        model.addAttribute("cates", IpaPlistParameter.CATES);
        model.addAttribute("orderBys", IpaPlistParameter.ORDERBYS);
        return "enterprise/ipaplist.ftl";
    }

    /**
     * 应用上架情况统计
     * 
     * @param para
     * @param model
     * @return
     */
    @RequestMapping(value = "/ipaloadstatlist")
    public String ipaloadstatlist(IpaLoadStatParameter para, Model model) {
        List<IpaLoadStat> values = null;
        if (para == null) {
            para = new IpaLoadStatParameter();
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<IpaLoadStat>> ret = backendApiService.listIpaLoadStat(para);
        if (ret != null && ret.getData() != null) {
            para.getPager().setTotal(ret.getData().getTotalCount());
            values = ret.getData().getResultList();
            IpaLoadStat sumIpaLoadStat = new IpaLoadStat();
            sumIpaLoadStat.setMarketId(-2);
            for (IpaLoadStat ipaLoadStat : values) {
                sumIpaLoadStat.setExcludSdk(ipaLoadStat.getExcludSdk() + sumIpaLoadStat.getExcludSdk());
                sumIpaLoadStat.setFailed(ipaLoadStat.getFailed() + sumIpaLoadStat.getFailed());
                sumIpaLoadStat.setNoEnterprise(ipaLoadStat.getNoEnterprise() + sumIpaLoadStat.getNoEnterprise());
                sumIpaLoadStat.setNotFound(ipaLoadStat.getNotFound() + sumIpaLoadStat.getNotFound());
                sumIpaLoadStat.setOpenFailed(ipaLoadStat.getOpenFailed() + sumIpaLoadStat.getOpenFailed());
                sumIpaLoadStat.setRevoked(ipaLoadStat.getRevoked() + sumIpaLoadStat.getRevoked());
                sumIpaLoadStat.setSuccess(ipaLoadStat.getSuccess() + sumIpaLoadStat.getSuccess());
                sumIpaLoadStat.setTotal(ipaLoadStat.getTotal() + sumIpaLoadStat.getTotal());
                sumIpaLoadStat.setUnknown(ipaLoadStat.getUnknown() + sumIpaLoadStat.getUnknown());
            }
            values.add(0, sumIpaLoadStat);
        }

        List<MarketInfo> marketInfos = null;
        ApiRespWrapper<ListWrapResp<MarketInfo>> marketInfosResp = backendApiService.getMarketInfos();
        marketInfos = marketInfosResp == null || marketInfosResp.getData() == null ? null : marketInfosResp.getData()
                .getResultList();

        Map<String, String> marketNames = new LinkedHashMap<String, String>();
        if (CollectionUtils.notEmptyAndNull(marketInfos)) {
            marketNames.put("-2", "所有市场");
            for (MarketInfo marketInfo : marketInfos) {
                marketNames.put(String.valueOf(marketInfo.getId()), marketInfo.getName());
            }
            marketNames.put("-1", "未知市场");
        }
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("marketNames", marketNames);
        return "enterprise/ipaloadstat.ftl";
    }

    @RequestMapping(value = "/uploadenterpriseipas")
    public String uploadenterpriseipas(IpaUploadListParameter para, Model model) {
        List<EnterpriseIpaFile> values = null;
        para.transfer();
        ApiRespWrapper<ListWrapResp<MarketInfo>> marketInfosResp = backendApiService.getMarketInfos();
        ApiRespWrapper<ListWrapResp<EnterpriseIpaFile>> uploadIpaDatasResp = backendApiService.listUploadIpaDatas(para);
        if (uploadIpaDatasResp != null && uploadIpaDatasResp.getData() != null) {
            para.getPager().setTotal(uploadIpaDatasResp.getData().getTotalCount());
            values = uploadIpaDatasResp.getData().getResultList();
        }
        model.addAttribute("marketinfos", initMarketInfosMap(marketInfosResp));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("types", IpaUploadListParameter.TYPES);
        model.addAttribute("status", IpaUploadListParameter.ALL_STATUS);
        return "enterprise/uploadenterpriseipas.ftl";
    }

    @RequestMapping(value = "/parsedenterpriseipas")
    public String parsedenterpriseipas(IpaPlistParameter para, Model model) {
        List<IpaItunesMetaData> values = null;
        para.transfer();
        ApiRespWrapper<ListWrapResp<MarketInfo>> marketInfosResp = backendApiService.getMarketInfos();
        ApiRespWrapper<ListWrapResp<IpaItunesMetaData>> ipaItunesMetaDatasResp = backendApiService
                .listIpaItunesMetaDatas(para);
        if (ipaItunesMetaDatasResp != null && ipaItunesMetaDatasResp.getData() != null) {
            para.getPager().setTotal(ipaItunesMetaDatasResp.getData().getTotalCount());
            values = ipaItunesMetaDatasResp.getData().getResultList();
        }
        model.addAttribute("marketinfos", initMarketInfosMap(marketInfosResp));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("types", IpaPlistParameter.TYPES);
        return "enterprise/parsedenterpriseipas.ftl";
    }

    @RequestMapping(value = "/marketinfos")
    public String marketinfos(@ModelAttribute("errMsg") String errMsg, Model model) {
        List<MarketInfo> values = null;
        ApiRespWrapper<ListWrapResp<MarketInfo>> ret = backendApiService.getMarketInfos();
        values = ret == null || ret.getData() == null ? null : ret.getData().getResultList();
        model.addAttribute("values", values);
        model.addAttribute("errMsg", errMsg);
        return "enterprise/marketinfo.ftl";
    }

    private Map<String, String> initMarketInfosMap(ApiRespWrapper<ListWrapResp<MarketInfo>> marketInfosResp) {
        Map<String, String> ret = new LinkedHashMap<String, String>();
        ret.put("0", "全部");
        if (marketInfosResp == null || marketInfosResp.getData() == null
                || CollectionUtils.emptyOrNull(marketInfosResp.getData().getResultList())) {
            return ret;
        }
        for (MarketInfo marketInfo : marketInfosResp.getData().getResultList()) {
            ret.put(String.valueOf(marketInfo.getId()), marketInfo.getName());
        }
        return ret;
    }

    @RequestMapping(value = "/marketipas")
    public String marketipas(Integer id, Model model) {
        List<IpaPlist> values = null;
        ApiRespWrapper<ListWrapResp<IpaPlist>> ret = backendApiService.listMarketIpas(id);
        ApiRespWrapper<ListWrapResp<MarketInfo>> marketInfoResp = this.backendApiService.getMarketInfos();
        if (marketInfoResp != null && marketInfoResp.getData() != null
                && marketInfoResp.getData().getResultList() != null) {
            model.addAttribute("marketinfos", buildMarketIdNames(marketInfoResp.getData().getResultList()));
        }
        values = ret == null || ret.getData() == null ? null : ret.getData().getResultList();
        model.addAttribute("values", values);
        model.addAttribute("cates", IpaPlistParameter.CATES);
        return "enterprise/marketinfo.ftl";
    }

    private Map<String, String> buildMarketIdNames(List<MarketInfo> datas) {
        Map<String, String> ret = new HashMap<String, String>();
        for (MarketInfo data : datas) {
            ret.put(String.valueOf(data.getId()), data.getName());
        }
        return ret;
    }

    @RequestMapping(value = "/addmarketinfo")
    protected String addmarketinfo(int id, String name, String site, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(name)) {
            errMsg = "名称不能为空.";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(site)) {
            errMsg = "网站不能为空.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                MarketInfoParameter parameter = new MarketInfoParameter();
                parameter.setId(id);
                parameter.setName(name);
                parameter.setSite(site);
                ApiRespWrapper<Boolean> resp = this.backendApiService.addMarketInfo(parameter);
                if (resp == null || resp.getData() == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/enterprise/marketinfos";
    }

    @RequestMapping(value = "/signatures")
    public String signatures(StatusStartSizeParameter para, Model model) {
        List<IpaCertSignature> values = null;
        if (para == null) {
            para = new StatusStartSizeParameter();
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<IpaCertSignature>> ret = backendApiService.getEnterpriseSignatures(para);

        para.getPager().setTotal(ret.getData().getTotalCount());
        values = ret.getData().getResultList();
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("status", IpaCertSignature.STATUS);
        return "enterprise/signatures.ftl";
    }

    @RequestMapping(value = "/signatureipas")
    public String signatureipas(SignatureIpasParameter para, Model model) {
        List<IpaPlist> values = null;
        if (para == null) {
            para = new SignatureIpasParameter(null);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<IpaPlist>> ret = backendApiService.getEnterpriseSignatureIpas(para);
        ApiRespWrapper<IpaCertSignature> enterpriseSignatureResp = this.backendApiService.getEnterpriseSignature(para);
        para.getPager().setTotal(ret.getData().getTotalCount());
        values = ret.getData().getResultList();
        formatEnterplistUrl(values);
        ApiRespWrapper<ListWrapResp<MarketInfo>> marketInfoResp = this.backendApiService.getMarketInfos();
        if (marketInfoResp != null && marketInfoResp.getData() != null
                && marketInfoResp.getData().getResultList() != null) {
            model.addAttribute("marketinfos", buildMarketIdNames(marketInfoResp.getData().getResultList()));
        }
        model.addAttribute("values", values);
        model.addAttribute("enterpriseSignature", enterpriseSignatureResp.getData());
        model.addAttribute("para", para);
        model.addAttribute("status", SignatureIpasParameter.ALL_STATUS);
        model.addAttribute("cates", IpaPlistParameter.CATES);
        return "enterprise/signatureipas.ftl";
    }

    private void formatEnterplistUrl(List<IpaPlist> values) {
        if (values == null) {
            return;
        }
        String host = this.backendApiService.getPlistHostUrl().getData();
        for (IpaPlist ipaPlist : values) {
            if (StringUtils.isEmpty(ipaPlist.getEnterprisePlistUrl())) {
                continue;
            }
            if (ipaPlist.getEnterprisePlistUrl().startsWith("http")) {
                continue;
            }
            ipaPlist.setEnterprisePlistUrl(host + ipaPlist.getEnterprisePlistUrl());
        }
    }

    @RequestMapping(value = "/ipatest.json")
    @ResponseBody
    public String ipatest(int plistId) {
        ApiRespWrapper<Map<String, IpaPlist>> resp = this.backendApiService.getIpaPlists(Arrays.asList(plistId));
        if (resp == null || CollectionUtils.emptyOrNull(resp.getData())) {
            return "未找到id" + plistId + "对应的ipa记录，请联系系统管理员.";
        }
        IpaPlist data = resp.getData().get(String.valueOf(plistId));
        if (data == null) {
            return "未找到id" + plistId + "对应的ipa记录，请联系系统管理员.";
        }
        String value = "<html><style type='text/css'>.download-btn{display:inline-block;width:264px;height:71px;margin-top:40px;}</style>"
                + "<body><h2>"
                + data.getAppName()
                + "</h2>"
                + "<a class=\"download-btn\" href=\"itms-services://?action=download-manifest&url=https://dl.appchina.com/ipas/plist/"
                + data.getEnterprisePlistUrl()
                + "\">"
                + "<img src=\"./cover_download.png\" alt=\"下载\"/></a></body></html>";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("/srv/images/client/test.html"));
            fos.write(value.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            return "生成测试文件失败，请联系管理员.";
        } finally {
            IOUtils.close(fos);
        }
        return "";
    }

    @RequestMapping(value = "/sigdisable.json")
    @ResponseBody
    public String sigdisable(Long certSerial) {
        ApiRespWrapper<Boolean> resp = this.backendApiService.disableEnterpriseSignatures(new SignatureIpasParameter(
                certSerial));
        if (resp == null || resp.getData() == null || !resp.getData()) {
            return "更新失败，原因" + resp == null ? "未知" : resp.getMessage();
        }
        return "";
    }

    private List<String> initBundleIds(List<IpaPlist> values) {
        List<String> bundleIds = new ArrayList<String>();;
        for (IpaPlist ipaPlistIntervention : values) {
            if (!StringUtils.isEmpty(ipaPlistIntervention.getBundleId())) {
                bundleIds.add(ipaPlistIntervention.getBundleId());
            }
        }
        return bundleIds;
    }

    private List<Integer> initItemIds(List<IpaPlist> values) {
        List<Integer> itemIds = new ArrayList<Integer>();;
        for (IpaPlist ipaPlistIntervention : values) {
            if (ipaPlistIntervention.getItemId() != null) {
                itemIds.add(ipaPlistIntervention.getItemId());
            }
        }
        return itemIds;
    }
}
