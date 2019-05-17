package com.silas.project.common.service.impl;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.silas.project.common.entity.MSysOperationStatistics;
import com.silas.project.common.mapper.MSysOperationStatisticsMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * api操作日志的统计表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-27
 */
@Service
public class MSysOperationStatisticsServiceImpl extends ServiceImpl<MSysOperationStatisticsMapper, MSysOperationStatistics> implements IService<MSysOperationStatistics> {

}
