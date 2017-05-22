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
     * ==========宝付代付交易查询配置信息开始==========
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
     * ============================================
     * ==========宝付代付交易查询配置信息结束==========
     * ============================================
     */
    /**
     * 宝付商户号列表
     */
    private Map<String,MerInfo> baofuMerInoList;

    /**
     * 银联商户号列表
     */
    private Map<String,MerInfo> unionMerInfoList;

    /**
     * ============================================
     * ==========银联代付交易配置信息开始==========
     * ============================================
     */
    /**
     * 银联代付交易商户号
     */
    private String unionDaifuSubmitMerId;
    /**
     * 银联代付交易接口版本号
     */
    private String unionDaifuSubmitVersion;
    /**
     * 银联代付交易提交地址
     */
    private String unionDaifuSubmitUrl;
    /**
     * 银联代付交易链接超时时间
     * 单位：毫秒
     */
    private int unionDaifuSubmitHttpConnectionTimeOut = CONNECTON_TIME_OUT;
    /**
     * 银联代付交易读取超时时间
     */
    private int unionDaifuSubmitHttpReadTimeout = READ_TIME_OUT;
    /**
     * ============================================
     * ==========银联代付交易配置信息结束==========
     * ============================================
     */

    /**
     * ============================================
     * ==========银联代付交易查询配置信息开始==========
     * ============================================
     */
    private String unionDaifuQueryMerId;
    /**
     * 银联代付查询接口版本号
     */
    private String unionDaifuQueryVersion;
    /**
     * 银联代付查询终端号
     */
    private String unionDaifuQueryTerminal;
    /**
     * 银联代付查询提交地址
     */
    private String unionDaifuQueryUrl;
    /**
     * 银联代付查询链接超时时间
     * 单位：毫秒
     */
    private int unionDaifuQueryHttpConnectionTimeOut = CONNECTON_TIME_OUT;
    /**
     * 银联代付查询读取超时时间
     */
    private int unionDaifuQueryHttpReadTimeout = READ_TIME_OUT;
    /**
     * ============================================
     * ==========银联代付交易查询配置信息结束==========
     * ============================================
     */

    public Map<String, MerInfo> getUnionMerInfoList() {
        return unionMerInfoList;
    }

    public void setUnionMerInfoList(Map<String, MerInfo> unionMerInfoList) {
        this.unionMerInfoList = unionMerInfoList;
    }

    public String getUnionDaifuQueryMerId() {
        return unionDaifuQueryMerId;
    }

    public void setUnionDaifuQueryMerId(String unionDaifuQueryMerId) {
        this.unionDaifuQueryMerId = unionDaifuQueryMerId;
    }

    public String getUnionDaifuQueryVersion() {
        return unionDaifuQueryVersion;
    }

    public void setUnionDaifuQueryVersion(String unionDaifuQueryVersion) {
        this.unionDaifuQueryVersion = unionDaifuQueryVersion;
    }

    public String getUnionDaifuQueryTerminal() {
        return unionDaifuQueryTerminal;
    }

    public void setUnionDaifuQueryTerminal(String unionDaifuQueryTerminal) {
        this.unionDaifuQueryTerminal = unionDaifuQueryTerminal;
    }

    public String getUnionDaifuQueryUrl() {
        return unionDaifuQueryUrl;
    }

    public void setUnionDaifuQueryUrl(String unionDaifuQueryUrl) {
        this.unionDaifuQueryUrl = unionDaifuQueryUrl;
    }

    public int getUnionDaifuQueryHttpConnectionTimeOut() {
        return unionDaifuQueryHttpConnectionTimeOut;
    }

    public void setUnionDaifuQueryHttpConnectionTimeOut(int unionDaifuQueryHttpConnectionTimeOut) {
        this.unionDaifuQueryHttpConnectionTimeOut = unionDaifuQueryHttpConnectionTimeOut;
    }

    public int getUnionDaifuQueryHttpReadTimeout() {
        return unionDaifuQueryHttpReadTimeout;
    }

    public void setUnionDaifuQueryHttpReadTimeout(int unionDaifuQueryHttpReadTimeout) {
        this.unionDaifuQueryHttpReadTimeout = unionDaifuQueryHttpReadTimeout;
    }

    public String getUnionDaifuSubmitMerId() {
        return unionDaifuSubmitMerId;
    }

    public void setUnionDaifuSubmitMerId(String unionDaifuSubmitMerId) {
        this.unionDaifuSubmitMerId = unionDaifuSubmitMerId;
    }

    public String getUnionDaifuSubmitVersion() {
        return unionDaifuSubmitVersion;
    }

    public void setUnionDaifuSubmitVersion(String unionDaifuSubmitVersion) {
        this.unionDaifuSubmitVersion = unionDaifuSubmitVersion;
    }

    public String getUnionDaifuSubmitUrl() {
        return unionDaifuSubmitUrl;
    }

    public void setUnionDaifuSubmitUrl(String unionDaifuSubmitUrl) {
        this.unionDaifuSubmitUrl = unionDaifuSubmitUrl;
    }

    public int getUnionDaifuSubmitHttpConnectionTimeOut() {
        return unionDaifuSubmitHttpConnectionTimeOut;
    }

    public void setUnionDaifuSubmitHttpConnectionTimeOut(int unionDaifuSubmitHttpConnectionTimeOut) {
        this.unionDaifuSubmitHttpConnectionTimeOut = unionDaifuSubmitHttpConnectionTimeOut;
    }

    public int getUnionDaifuSubmitHttpReadTimeout() {
        return unionDaifuSubmitHttpReadTimeout;
    }

    public void setUnionDaifuSubmitHttpReadTimeout(int unionDaifuSubmitHttpReadTimeout) {
        this.unionDaifuSubmitHttpReadTimeout = unionDaifuSubmitHttpReadTimeout;
    }

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
