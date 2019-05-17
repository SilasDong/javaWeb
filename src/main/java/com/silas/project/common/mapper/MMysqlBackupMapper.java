package com.silas.project.common.mapper;

import com.silas.project.common.entity.MMysqlBackup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-14
 */
@Mapper
public interface MMysqlBackupMapper extends BaseMapper<MMysqlBackup> {

	@Select("SELECT * from m_mysql_backup where DATE_ADD(createTime,INTERVAL ${saveMysqlDays} DAY) < NOW() ORDER BY createTime desc")
	List<MMysqlBackup> queryMysqlBacks(@Param("saveMysqlDays")int saveMysqlDays);
}