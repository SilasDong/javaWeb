package com.silas.project.common.apiservice;

import com.silas.core.entity.CApiBase;
import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.ParamContextHolder;
import com.silas.core.service.impl.XcdApiProcessService;
import com.silas.project.common.service.IMMysqlBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mysqlBackupApiServiceImpl")
public class MysqlBackupApiServiceImpl extends XcdApiProcessService {

    @Autowired
    IMMysqlBackupService backupService;

    @Override
    public Object apiProcess(ParamContextHolder paramContextHolder, CApiBase cApiBase) throws ApiProcessException {
        backupService.backup(false);
        return resultMap(true);
    }
}
