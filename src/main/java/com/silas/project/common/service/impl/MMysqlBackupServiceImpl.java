package com.silas.project.common.service.impl;

import com.silas.project.common.entity.MMysqlBackup;
import com.silas.project.common.mapper.MMysqlBackupMapper;
import com.silas.project.main.config.ProjectPropertyBean;
import com.silas.core.exception.ApiProcessException;
import com.silas.module.filemanager.config.MFilemanagerPropertyBean;
import com.silas.project.common.service.IMMysqlBackupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-14
 */
@Service
public class MMysqlBackupServiceImpl extends ServiceImpl<MMysqlBackupMapper, MMysqlBackup> implements IMMysqlBackupService {

	private static final Logger logger = LoggerFactory.getLogger(MMysqlBackupServiceImpl.class);

	@Autowired
    ProjectPropertyBean propertyBean;

	@Autowired
	MFilemanagerPropertyBean filePropertyBean;

	@Autowired
	IMMysqlBackupService backupService;

	@Override
	public List<MMysqlBackup> queryMysqlBacks(int saveMysqlDays) {
		return this.baseMapper.queryMysqlBacks(saveMysqlDays);
	}

	@Override
	public boolean backup(boolean isDel) throws ApiProcessException {
		String sqlFileName = propertyBean.getDbName() + "_" + System.currentTimeMillis() + ".sql";
		String rootDir = filePropertyBean.getConfigParams().getLocalRootDir();
		rootDir = (rootDir.endsWith(File.separator) ? rootDir : rootDir + File.separator);
		String dirPath = rootDir + "mysql_backup/";
		String sqlPath = dirPath + "sql_tmp";
		String zipFileName = dirPath + sqlFileName.replace(".sql", ".zip");
		String sqlFullPath = sqlPath +  "/" + sqlFileName;
		File fileSqlPath = new File(sqlPath);
		if (!fileSqlPath.exists()) {
			fileSqlPath.mkdirs();
		}
		logger.info(sqlPath + "：备份地址：" + sqlFullPath);
		String[] execCMD = new String[] {"mysqldump", "-u" + propertyBean.getDbUsername(), "-p" + propertyBean.getDbPassword(), "-h" + propertyBean.getDbHost(), propertyBean.getDbName(),
				"-r" + sqlFullPath, "--skip-lock-tables"};
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(execCMD);
			int processComplete = process.waitFor();
			if (processComplete == 0) {

				ZipUtil.pack(fileSqlPath, new File(zipFileName));
				MMysqlBackup mysqlBackup = new MMysqlBackup();
				mysqlBackup.setFileName(zipFileName.replace(rootDir, ""));
				mysqlBackup.setSize(new File(zipFileName).length());
				mysqlBackup.insert();
				File fileSqlFullPath = new File(sqlFullPath);
				if (fileSqlFullPath.exists()){
					fileSqlFullPath.delete();
					logger.info(fileSqlPath.getPath() + "  文件删除成功.");
				}
				logger.info("数据库备份成功.");
			} else {
				throw new RuntimeException("数据库备份数据库失败.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ApiProcessException(new ApiProcessException("数据库备份数据库失败"), e.getMessage());
		}

		// 删除大于n天的备份数据
		if (isDel && propertyBean.getConfigParams().getMysqlBackup()) {
			List<MMysqlBackup> list = backupService.queryMysqlBacks(propertyBean.getConfigParams().getSaveMysqlDays());
			for (MMysqlBackup item: list) {
				String delPath = rootDir + item.getFileName();
				File file = new File(delPath);
				if (file.exists()) {
					file.delete();
				}
				item.deleteById();
			}
		}
		return true;
	}
}
