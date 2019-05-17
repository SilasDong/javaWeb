package com.silas.project.main.preaction;

import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.IXcdApiParamService;
import com.silas.core.service.ParamContextHolder;
import org.springframework.stereotype.Service;

/**
 * 交易密码校验
 */
@Service("checkTradePasswordAction")
public class CheckTradePasswordAction implements IXcdApiParamService {


    @Override
    public void processApiParam(ParamContextHolder paramContextHolder) throws ApiProcessException {

    }
}
