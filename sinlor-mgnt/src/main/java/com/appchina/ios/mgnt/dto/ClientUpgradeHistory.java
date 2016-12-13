// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dto;

import java.util.Date;

import com.appchina.ios.core.DbStatus;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class ClientUpgradeHistory extends DbStatus {
    public static final int STATUS_PUBLISHED = 2;// 已正式发布
    public static final int STATUS_PUBLISH_FAILED = -2;// 已正式发布

    private int id;
    private String filePath;
    private String upgradeInfo;
    // 更新日期
    private Date releaseDate;
    // 当前版本
    private String version;
    private int shortVersion;
    // plist文件
    private String plist;
    // 支持的最小版本后，不填则支持所有版本
    private String supportMinOs;
    // 支持设备BIT 111 MEANS IPAD/ITOUCH/IPHONE INT VALUE IS 7
    private int supportDeviceFlag;
    private int test;
    // 应用渠道
    private String ipaChannel;
    // 包名
    private String bundleId;
    // 打包所用签名
    private String sign;
    private int testIpaStatus;
    private String ipaMd5;
    private String info;
    // 允许相应低版本更新此自身
    public static final int ACCEPT_UPGRADE_YES = 0;
    public static final int ACCEPT_UPGRADE_NO = 1;
    private int acceptUpgrade;
    // 允许自身更新
    public static final int SUPPORT_UPGRADE_YES = 0;
    public static final int SUPPORT_UPGRADE_NO = 1;
    private int supportUpgrade;
    // 灰度要支持的数量
    private Integer upgradeCount;

    public int getTestIpaStatus() {
        return testIpaStatus;
    }

    public void setTestIpaStatus(int testIpaStatus) {
        this.testIpaStatus = testIpaStatus;
    }

    public String getIpaMd5() {
        return ipaMd5;
    }

    public void setIpaMd5(String ipaMd5) {
        this.ipaMd5 = ipaMd5;
    }

    public String getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(String upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlist() {
        return plist;
    }

    public void setPlist(String plist) {
        this.plist = plist;
    }

    public String getSupportMinOs() {
        return supportMinOs;
    }

    public void setSupportMinOs(String supportMinOs) {
        this.supportMinOs = supportMinOs;
    }

    public int getSupportDeviceFlag() {
        return supportDeviceFlag;
    }

    public void setSupportDeviceFlag(int supportDeviceFlag) {
        this.supportDeviceFlag = supportDeviceFlag;
    }

    public String getIpaChannel() {
        return ipaChannel;
    }

    public void setIpaChannel(String ipaChannel) {
        this.ipaChannel = ipaChannel;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "ClientUpgradeHistory [id=" + id + ", filePath=" + filePath + ", upgradeInfo=" + upgradeInfo
                + ", releaseDate=" + releaseDate + ", version=" + version + ", shortVersion=" + shortVersion
                + ", plist=" + plist + ", supportMinOs=" + supportMinOs + ", supportDeviceFlag=" + supportDeviceFlag
                + ", test=" + test + ", ipaChannel=" + ipaChannel + ", bundleId=" + bundleId + ", sign=" + sign
                + ", testIpaStatus=" + testIpaStatus + ", ipaMd5=" + ipaMd5 + ", info=" + info + ", acceptUpgrade="
                + acceptUpgrade + ", supportUpgrade=" + supportUpgrade + "]";
    }

    public int getShortVersion() {
        return shortVersion;
    }

    public void setShortVersion(int shortVersion) {
        this.shortVersion = shortVersion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getAcceptUpgrade() {
        return acceptUpgrade;
    }

    public void setAcceptUpgrade(int acceptUpgrade) {
        this.acceptUpgrade = acceptUpgrade;
    }

    public int getSupportUpgrade() {
        return supportUpgrade;
    }

    public void setSupportUpgrade(int supportUpgrade) {
        this.supportUpgrade = supportUpgrade;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public Integer getUpgradeCount() {
        return upgradeCount;
    }

    public void setUpgradeCount(Integer upgradeCount) {
        this.upgradeCount = upgradeCount;
    }
}
