package com.silas.project.common.entity;
/**
 * <p>
 * 系统管理操作统计
 * </p>
 *
 * @author ${author}
 * @since 2018-9-27
 */
public class OperationStatistics {

    /**
     * apiId
     */
    private String apiId;
    /**
     * apiVersion
     */
    private String apiVersion;
    /**
     * ipCount
     */
    private Integer ipCount;
    /**
     * isSuccess
     */
    private Integer isSuccess;
    /**
     * isSuccessNum
     */
    private Integer isSuccessNum;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Integer getIpCount() {
        return ipCount;
    }

    public void setIpCount(Integer ipCount) {
        this.ipCount = ipCount;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getIsSuccessNum() {
        return isSuccessNum;
    }

    public void setIsSuccessNum(Integer isSuccessNum) {
        this.isSuccessNum = isSuccessNum;
    }
}