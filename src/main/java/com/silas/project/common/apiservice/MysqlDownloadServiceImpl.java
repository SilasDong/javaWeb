package com.silas.project.common.apiservice;

import com.silas.project.common.constants.ErrorCode;
import com.silas.project.common.entity.MMysqlBackup;
import com.silas.core.entity.CApiBase;
import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.IXcdApiProcessService;
import com.silas.core.service.ParamContextHolder;
import com.silas.module.filemanager.config.FileConfigParams;
import com.silas.module.filemanager.config.MFilemanagerPropertyBean;
import com.silas.module.filemanager.service.IMFilemanagerFileService;
import com.silas.project.common.service.IMMysqlBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("mysqlBackUpDownloadService")
public class MysqlDownloadServiceImpl implements IXcdApiProcessService {

    @Autowired
    private MFilemanagerPropertyBean filemanagerPropertyBean;

    @Autowired
    private IMMysqlBackupService mysqlBackupService;

    @Autowired
    private IMFilemanagerFileService filemanagerFileService;

    @Override
    public Object apiProcess(ParamContextHolder paramContextHolder, CApiBase cApiBase) throws ApiProcessException {
        FileConfigParams configParams = filemanagerPropertyBean.getConfigParams();
        String id = paramContextHolder.getString("id");
        MMysqlBackup mysqlBackup = mysqlBackupService.selectById(id);
        if (mysqlBackup == null) {
            throw new ApiProcessException(ErrorCode.MYSQL_BACKUP_FILE_NOT_EXITS_ERROR, ",id=" + id);
        } else {
            String rootDir = configParams.getLocalRootDir();
            String lastDir = rootDir.split(File.separator)[rootDir.split(File.separator).length-1];
            String filePath = "";
            if (!mysqlBackup.getFileName().contains(lastDir)) {
                filePath = (rootDir.endsWith(File.separator) ? rootDir : rootDir + File.separator) + mysqlBackup.getFileName();
            } else {
                filePath = rootDir.replace(lastDir, "") + mysqlBackup.getFileName();
            }
            File realFile = new File(filePath);
            if (!realFile.exists()) {
                throw new ApiProcessException(ErrorCode.MYSQL_BACKUP_FILE_NOT_EXITS_ERROR, ",id=" + id);
            }
            //本地文件，执行下载
            filemanagerFileService.download(paramContextHolder.getReq(), paramContextHolder.getResp(), filePath, realFile.getName());
        }
        return null;
    }
}
