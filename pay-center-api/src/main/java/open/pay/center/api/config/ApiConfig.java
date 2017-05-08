package open.pay.center.api.config;

import open.pay.center.core.model.MerInfo;

import java.util.Map;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 16:46
 * Email: qyuhy@qq.com
 */
public class ApiConfig {
    public static final int CONNECTON_TIME_OUT = 60000;
    public static final int READ_TIME_OUT = 120000;
    public static final String ENV_TEST = "TEST";

    /**
     * 环境信息：测试环境信息会打印原文信息
     * 正式环境：打印加密信息；
     */
    private String env = ENV_TEST;
    /**
     * 日志加密密码
     */
    private String logEncryptPassword;
    /**
     * ============================================
     * ==========宝付代付交易配置信息开始==========
     * ============================================
     */
    /**
     * 宝付代付交易商户号
     */
    private String bfDaifuSubmitMerId;
    /**
     * 宝付代付交易接口版本号
     */
    private String bfDaifuSubmitVersion;
    /**
     * 宝付代付交易终端号
     */
    private String bfDaifuSubmitTerminal;
    /**
     * 宝付代付交易提交地址
     */
    private String bfDaifuSubmitUrl;
    /**
     * 宝付代付交易链接超时时间
     * 单位：毫秒
     */
    private int bfDaifuSubmitHttpConnectionTimeOut = CONNECTON_TIME_OUT;
    /**
     * 宝付代付交易读取超时时间
     */
    private int bfDaifuSubmitHttpReadTimeout = READ_TIME_OUT;
    /**
     * ============================================
     * ==========宝付代付交易配置信息结束==========
     * ============================================
     */

    /**
     * ============================================
     * ==========宝付代付交易配置信息开始==========
     * ============================================
     */
    private String bfDaifuQueryMerId;
    /**
     * 宝付代付查询接口版本号
     */
    private String bfDaifuQueryVersion;
    /**
     * 宝付代付查询终端号
     */
    private String bfDaifuQueryTerminal;
    /**
     * 宝付代付查询提交地址
     */
    private String bfDaifuQueryUrl;
    /**
     * 宝付代付查询链接超时时间
     * 单位：毫秒
     */
    private int bfDaifuQueryHttpConnectionTimeOut = CONNECTON_TIME_OUT;
    /**
     * 宝付代付查询读取超时时间
     */
    private int bfDaifuQueryHttpReadTimeout = READ_TIME_OUT;
    /**
     * 宝付商户号列表
     */
    private Map<String,MerInfo> baofuMerInoList;

    public Map<String, MerInfo> getBaofuMerInoList() {
        return baofuMerInoList;
    }

    public void setBaofuMerInoList(Map<String, MerInfo> baofuMerInoList) {
        this.baofuMerInoList = baofuMerInoList;
    }

    public String getBfDaifuSubmitMerId() {
        return bfDaifuSubmitMerId;
    }

    public void setBfDaifuSubmitMerId(String bfDaifuSubmitMerId) {
        this.bfDaifuSubmitMerId = bfDaifuSubmitMerId;
    }

    public String getBfDaifuSubmitVersion() {
        return bfDaifuSubmitVersion;
    }

    public void setBfDaifuSubmitVersion(String bfDaifuSubmitVersion) {
        this.bfDaifuSubmitVersion = bfDaifuSubmitVersion;
    }

    public String getBfDaifuSubmitTerminal() {
        return bfDaifuSubmitTerminal;
    }

    public void setBfDaifuSubmitTerminal(String bfDaifuSubmitTerminal) {
        this.bfDaifuSubmitTerminal = bfDaifuSubmitTerminal;
    }

    public String getBfDaifuSubmitUrl() {
        return bfDaifuSubmitUrl;
    }

    public void setBfDaifuSubmitUrl(String bfDaifuSubmitUrl) {
        this.bfDaifuSubmitUrl = bfDaifuSubmitUrl;
    }

    public int getBfDaifuSubmitHttpConnectionTimeOut() {
        return bfDaifuSubmitHttpConnectionTimeOut;
    }

    public void setBfDaifuSubmitHttpConnectionTimeOut(int bfDaifuSubmitHttpConnectionTimeOut) {
        this.bfDaifuSubmitHttpConnectionTimeOut = bfDaifuSubmitHttpConnectionTimeOut;
    }

    public int getBfDaifuSubmitHttpReadTimeout() {
        return bfDaifuSubmitHttpReadTimeout;
    }

    public void setBfDaifuSubmitHttpReadTimeout(int bfDaifuSubmitHttpReadTimeout) {
        this.bfDaifuSubmitHttpReadTimeout = bfDaifuSubmitHttpReadTimeout;
    }

    public String getBfDaifuQueryMerId() {
        return bfDaifuQueryMerId;
    }

    public void setBfDaifuQueryMerId(String bfDaifuQueryMerId) {
        this.bfDaifuQueryMerId = bfDaifuQueryMerId;
    }

    public String getBfDaifuQueryVersion() {
        return bfDaifuQueryVersion;
    }

    public void setBfDaifuQueryVersion(String bfDaifuQueryVersion) {
        this.bfDaifuQueryVersion = bfDaifuQueryVersion;
    }

    public String getBfDaifuQueryTerminal() {
        return bfDaifuQueryTerminal;
    }

    public void setBfDaifuQueryTerminal(String bfDaifuQueryTerminal) {
        this.bfDaifuQueryTerminal = bfDaifuQueryTerminal;
    }

    public String getBfDaifuQueryUrl() {
        return bfDaifuQueryUrl;
    }

    public void setBfDaifuQueryUrl(String bfDaifuQueryUrl) {
        this.bfDaifuQueryUrl = bfDaifuQueryUrl;
    }

    public int getBfDaifuQueryHttpConnectionTimeOut() {
        return bfDaifuQueryHttpConnectionTimeOut;
    }

    public void setBfDaifuQueryHttpConnectionTimeOut(int bfDaifuQueryHttpConnectionTimeOut) {
        this.bfDaifuQueryHttpConnectionTimeOut = bfDaifuQueryHttpConnectionTimeOut;
    }

    public int getBfDaifuQueryHttpReadTimeout() {
        return bfDaifuQueryHttpReadTimeout;
    }

    public void setBfDaifuQueryHttpReadTimeout(int bfDaifuQueryHttpReadTimeout) {
        this.bfDaifuQueryHttpReadTimeout = bfDaifuQueryHttpReadTimeout;
    }
    public String getEnv() {
        return env;
    }

    public String getLogEncryptPassword() {
        return logEncryptPassword;
    }

    public void setLogEncryptPassword(String logEncryptPassword) {
        this.logEncryptPassword = logEncryptPassword;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
