package com.silas.project.common.scheduleservice;

import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.ParamContextHolder;
import com.silas.module.scheduler.entity.MSchedulerTask;
import com.silas.module.scheduler.service.ISchedulerJobProcessor;
import com.silas.project.common.service.IMMysqlBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 数据库备份
 */
@Service("mysqlBackupService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor={RuntimeException.class, Exception.class, ApiProcessException.class})
public class MysqlBackupServiceImpl implements ISchedulerJobProcessor {


    @Autowired
    IMMysqlBackupService backupService;

    @Override
    public Object runJob(MSchedulerTask schedulerTask, ParamContextHolder paramContextHolder) throws Exception {
        backupService.backup(true);
        return true;
    }
}
