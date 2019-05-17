package com.silas.project.main.entity;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.silas.module.sys.entity.IBaseUser;

/**
 * <p>
 * 会员信息
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
@TableName("p_sys_member")
public class SysMember extends Model<SysMember> implements IBaseUser {

    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.UUID)
    private String id;
    /**
     * 账号，格式U+ 手机后6位+3位随机值
     */
    private String account;
    /**
     * 密码 格式 密码_盐值
     */
    private String password;
    /**
     * 交易密码 格式 密码_盐值
     */
    private String tradePassword;
    /**
     * 推荐人Id
     */
    private String referrerId;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 性别 0=未知，1=男，2=女
     */
    private Integer gender;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 生日
     */
    private String birthDate;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 状态 1=启用 0=禁用
     */
    private Integer status;
    /**
     * 会员积分
     */
    private BigDecimal points;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;
    /**
     * openId
     */
    private String openId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 男方姓名
     */
    private String boyName;
    /**
     * 女方姓名
     */
    private String girlName;
    /**
     * 婚期
     */
    private String weddingDate;
    /**
     * 需求备注
     */
    private String remark;

    @TableField(exist=false)
    private String nick;


    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public String getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(String referrerId) {
        this.referrerId = referrerId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getBoyName() {
        return boyName;
    }

    public void setBoyName(String boyName) {
        this.boyName = boyName;
    }

    public String getGirlName() {
        return girlName;
    }

    public void setGirlName(String girlName) {
        this.girlName = girlName;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PSysMember{" +
                ", id=" + id +
                ", account=" + account +
                ", password=" + password +
                ", tradePassword=" + tradePassword +
                ", referrerId=" + referrerId +
                ", phone=" + phone +
                ", gender=" + gender +
                ", avatar=" + avatar +
                ", birthDate=" + birthDate +
                ", nickname=" + nickname +
                ", status=" + status +
                ", points=" + points +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", openId=" + openId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lastLoginTime=" + lastLoginTime +
                ", boyName=" + boyName +
                ", girlName=" + girlName +
                ", weddingDate=" + weddingDate +
                ", remark=" + remark +
                "}";
    }
}
