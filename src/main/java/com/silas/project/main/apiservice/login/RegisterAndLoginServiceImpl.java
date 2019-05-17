package com.silas.project.main.apiservice.login;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.silas.project.main.entity.SysMember;
import com.silas.project.main.service.IMSysMemberService;
import com.silas.core.entity.CApiBase;
import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.ParamContextHolder;
import com.silas.core.utils.BaseValueProcessUtil;
import com.silas.module.sys.config.MSysPropertyBean;
import com.silas.module.sys.constants.ErrorCode;
import com.silas.module.sys.constants.UserState;
import com.silas.module.sys.service.impl.LoginXcdApiProcessService;
import com.silas.module.sys.utils.ApiUtil;
import com.silas.module.wechat.entity.mini.MiniUserInfoBean;
import com.silas.module.wechat.entity.wxpublic.WeChatEventBase;
import com.silas.module.wechat.service.IWechatQrCodeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Map;
import static com.silas.module.wechat.preaction.miniprogram.MiniUserInfoAction.MINI_USER_INFO_KEY;

/**
 * @author silas
 */
@Service("registerAndLoginService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor={RuntimeException.class, Exception.class, ApiProcessException.class})
public class RegisterAndLoginServiceImpl extends LoginXcdApiProcessService implements IWechatQrCodeEventService {

    @Autowired
    private MSysPropertyBean sysPropertyBean;

    @Autowired
    private IMSysMemberService memMemberService;


    public static final String JWT_TYPE = "member";

    @Override
    protected String getRole() throws ApiProcessException {
        return JWT_TYPE;
    }

    @Override
    public Object apiProcess(ParamContextHolder paramContextHolder, CApiBase cApiBase) throws ApiProcessException {
        super.apiProcess(paramContextHolder, cApiBase);
        MiniUserInfoBean miniUserInfoBean = (MiniUserInfoBean)paramContextHolder.getReqAttribute(MINI_USER_INFO_KEY);

        String referrerId = (String) paramContextHolder.getReqParam("referrerId");
        logger.info("referrerId",referrerId);
        // 查询会员是否存在
        EntityWrapper<SysMember> memMemberEntityWrapper = new EntityWrapper<>();
        memMemberEntityWrapper.eq("openId", miniUserInfoBean.getOpenId());
        SysMember member = memMemberService.selectOne(memMemberEntityWrapper);
        String memId= BaseValueProcessUtil.uuid("");
        if (member == null) {
            long random = (long) ((Math.random() * 9 + 1) * 1000000000);
            //用户第一次登录，则自动为之注册
            member = new SysMember();
            member.setId(memId);
            member.setAccount("H"+random);
            member.setOpenId(miniUserInfoBean.getOpenId());
            member.setNickname(miniUserInfoBean.getNickName());
            member.setPassword("小程序授权登录");
            member.setGender(miniUserInfoBean.getGender());
            member.setAvatar(miniUserInfoBean.getAvatarUrl());
            member.setStatus(1);
            member.insert();
        }

        if (member.getStatus() != UserState.STATE_ENABLED) {
            throw new ApiProcessException(ErrorCode.LOGIN_ERROR_ACCOUNT_UNABLED);
        }
        member.setLastLoginTime(new Date());
        member.updateById();
        // 生成用户登录信息
        Map<String, Object> data = ApiUtil.createTokenInfo(sysPropertyBean, reqClientBaseType,miniUserInfoBean.getOpenId(), JWT_TYPE);
        return resultMap(data);
    }

    @Override
    public String onQrCodeEventListener(WeChatEventBase weChatEventBase, ParamContextHolder paramContextHolder) throws ApiProcessException {
        return null;
    }
}
