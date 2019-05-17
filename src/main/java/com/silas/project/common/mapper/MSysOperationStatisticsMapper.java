package com.silas.project.common.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.silas.project.common.entity.OperationStatistics;
import com.silas.project.common.entity.MSysOperationStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * api操作日志的统计表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-27
 */
@Mapper
public interface MSysOperationStatisticsMapper extends BaseMapper<MSysOperationStatistics> {

    @Select("select t.apiId, t.apiVersion, count(t.apiVersion) ipCount from \n" +
            "(select l.apiId, l.apiVersion, l.ip  from m_sys_operation_log l where datediff(l.createTime,now())=-1 group by l.apiId, l.apiVersion, l.ip) t group by t.apiId, t.apiVersion order by apiId")
    public List<OperationStatistics> findIpCountList();

    @Select("select l.apiId, l.apiVersion, l.isSuccess, count(l.isSuccess) isSuccessNum from m_sys_operation_log l where datediff(l.createTime,now())=-1 group by l.apiId,l.apiVersion,l.isSuccess order by apiId")
    public List<OperationStatistics> findIsSuccessCountList();
}