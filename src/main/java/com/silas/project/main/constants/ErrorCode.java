package com.silas.project.main.constants;

import com.silas.core.exception.ApiProcessException;

public class ErrorCode {

    public static final ApiProcessException MEMBER_POINT_ERROR = new ApiProcessException(1010001, "该账号积分不足,请重新选择");

    public static final ApiProcessException STORE_ADD_ERROR = new ApiProcessException(1010002, "店铺添加失败！");

    public static final ApiProcessException STORE_WITHDRAW_ERROR  = new ApiProcessException(1010003, "提现失败！");

    public static final ApiProcessException STORE_WITHDRAW_AMOUNT_ERROR = new ApiProcessException(1010004, "店铺账户余额不足,无法完场提现！");

    public static final ApiProcessException TRADE_PASSWORD_ERROR  = new ApiProcessException(1010005, "交易密码错误！");

    public static final ApiProcessException STORE_NOT_EXIST = new ApiProcessException(1010006, "店铺账户信息不存在！");

    public static final ApiProcessException REFERRAL_ORDER_ERROR = new ApiProcessException(1010007, "订单完成失败！");

    public static final ApiProcessException ACCOUNT_TYPE_ERROR = new ApiProcessException(1010010, "accountType类型错误！");

    public static final ApiProcessException MEMBER_ACCOUNT_EXIST = new ApiProcessException(1010008, "会员账户信息错误！");

    public static final ApiProcessException STORE_ACCOUNT_EXIST = new ApiProcessException(1010009, "店铺账户信息错误！");

}
