package com.silas.project.main.apiauthority;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.silas.project.main.apiservice.login.RegisterAndLoginServiceImpl;
import com.silas.project.main.entity.SysMember;
import com.silas.project.main.service.IMSysMemberService;
import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.ParamContextHolder;
import com.silas.module.sys.constants.ErrorCode;
import com.silas.module.sys.constants.UserState;
import com.silas.module.sys.entity.IBaseUser;
import com.silas.module.sys.service.IMemberApiAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberApiAuthorityServiceImpl implements IMemberApiAuthorityService {

    @Autowired
    private IMSysMemberService memMemberService;

    @Override
    public ApiProcessException getAccountInfo(ParamContextHolder paramContextHolder, String openId, String role) {
        paramContextHolder.setReqAttribute("openId", openId);
        paramContextHolder.setReqAttribute("role", role);
        String noSession = paramContextHolder.getString("noSession");
        ApiProcessException exception;
        //会员
        if(RegisterAndLoginServiceImpl.JWT_TYPE.equals(role)){
            SysMember member = "on".equals(noSession)? null:(SysMember)paramContextHolder.getSessionAttribute(role);
            if(member == null) {
                member = memMemberService.selectOne(new EntityWrapper<SysMember>().eq("openId", openId));
                paramContextHolder.setSessionAttribute(role, member);
            }
            exception = checkAccountStatus(member, paramContextHolder, role);
        }
        else{
            exception = new ApiProcessException("用户角色不存在");
        }
        return exception;
    }

    /**
     * 检查账户的状态
     * @param account
     * @param paramContextHolder
     * @param role
     * @return
     */
    private ApiProcessException checkAccountStatus(IBaseUser account, ParamContextHolder paramContextHolder, String role){
        ApiProcessException exception = null;
        if (account == null) {
            exception = ErrorCode.LOGIN_ERROR_ACCOUNT_NOT_EXIST;
        } else if (account.getStatus() != UserState.STATE_ENABLED) {
            exception = new ApiProcessException(ErrorCode.LOGIN_ERROR_ACCOUNT_UNABLED, " user state：" + account.getStatus());
        }else {
            paramContextHolder.setReqAttribute(role+"Id", account.getId());
            paramContextHolder.setReqAttribute(role, account);
        }
        return exception;
    }
}
