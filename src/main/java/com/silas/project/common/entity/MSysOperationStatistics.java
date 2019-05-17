package com.silas.project.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * api操作日志的统计表
 * </p>
 *
 * @author ${author}
 * @since 2018-09-27
 */
@TableName("m_sys_operation_statistics")
public class MSysOperationStatistics extends Model<MSysOperationStatistics> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;
    /**
     * apiId
     */
    private String apiId;
    /**
     * apiVersion
     */
    private String apiVersion;
    /**
     * 成功次数
     */
    private Integer successNum;
    /**
     * 失败次数
     */
    private Integer failNum;
    /**
     * ip数
     */
    private Integer ipNum;
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getSuccessNum() {
        return successNum == null ? 0: successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public Integer getFailNum() {
        return failNum == null ? 0: failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    public Integer getIpNum() {
        return ipNum == null?0:ipNum;
    }

    public void setIpNum(Integer ipNum) {
        this.ipNum = ipNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MSysOperationStatistics{" +
                ", id=" + id +
                ", apiId=" + apiId +
                ", apiVersion=" + apiVersion +
                ", successNum=" + successNum +
                ", failNum=" + failNum +
                ", ipNum=" + ipNum +
                ", createTime=" + createTime +
                "}";
    }
}
