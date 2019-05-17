package com.silas.project.main.apiresultservice;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.silas.project.main.entity.SysMember;
import com.silas.project.main.service.IMSysMemberService;
import com.silas.core.constants.ClientBaseType;
import com.silas.core.constants.SuccessCode;
import com.silas.core.entity.CApiBase;
import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.IXcdApiResultProcessService;
import com.silas.core.service.ParamContextHolder;
import com.silas.module.sys.config.MSysPropertyBean;
import com.silas.module.sys.constants.ErrorCode;
import com.silas.module.sys.utils.ApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.silas.core.constants.MCoreConstants.CODE;

@Service("changeJwtAftdoService")
public class ChangeJwtAftdoService implements IXcdApiResultProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ChangeJwtAftdoService.class);

    @Autowired
    private IMSysMemberService memberService;

    @Autowired
    private MSysPropertyBean sysPropertyBean;

    @Override
    public Object processApiResult(ParamContextHolder paramContextHolder, CApiBase cApiBase, Object o) throws ApiProcessException {
        JSONObject resultObj = null;
        if (o instanceof String) {
            resultObj = JSONObject.parseObject((String) o);
            if (resultObj.getInteger(CODE) != SuccessCode.GENERAL_SUCCESS) {
                return o;
            }
            String memId = paramContextHolder.getString("memberId");

            if (memId == null) {
                return o;
            }
            JSONObject data=null;
            Object object = resultObj.get("data") ;
            if(object instanceof  Integer){
                data=new JSONObject();
                data.put("result",object);
            }
            if (data != null) {
                EntityWrapper<SysMember> memberEntityWrapper = new EntityWrapper<>();
                memberEntityWrapper.eq("id", memId);
                SysMember newMemberObj = memberService.selectOne(memberEntityWrapper);
                if (newMemberObj == null) {
                    return o;
                }
                try {
                    ClientBaseType reqClientBaseType = paramContextHolder.getClientBaseType();
                    data.put("newToken", ApiUtil.createTokenInfo(sysPropertyBean, reqClientBaseType, newMemberObj.getAccount(), ""));
                    return resultObj.toJSONString();
                } catch (Exception e) {
                    logger.error(ErrorCode.LOGIN_ERROR_JWT_ERROR.getMessage(), e);
                    throw new ApiProcessException(ErrorCode.LOGIN_ERROR_JWT_ERROR, e.getMessage());
                }
            } else {
                return o;
            }
        } else {
            return o;
        }
    }
}
