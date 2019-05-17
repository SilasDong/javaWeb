package com.silas.project.common.service;

import com.silas.project.common.entity.MMysqlBackup;
import com.silas.core.exception.ApiProcessException;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-14
 */
public interface IMMysqlBackupService extends IService<MMysqlBackup> {

	List<MMysqlBackup> queryMysqlBacks(int saveMysqlDays);

	/**
	 * 备份数据库
	 * @param isDel 是否删除大于n天的数据，n在数据可以配置表设置
	 * @return
	 * @throws ApiProcessException
	 */
	boolean backup(boolean isDel) throws ApiProcessException;
}
