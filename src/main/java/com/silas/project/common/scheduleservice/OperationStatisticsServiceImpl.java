package com.silas.project.common.scheduleservice;

import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.ParamContextHolder;
import com.silas.module.scheduler.entity.MSchedulerTask;
import com.silas.module.scheduler.service.ISchedulerJobProcessor;
import com.silas.project.common.entity.MSysOperationStatistics;
import com.silas.project.common.entity.OperationStatistics;
import com.silas.project.common.mapper.MSysOperationStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作统计
 */
@Service("operationStatisticsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor={RuntimeException.class, Exception.class, ApiProcessException.class})
public class OperationStatisticsServiceImpl implements ISchedulerJobProcessor {
    @Autowired
    private MSysOperationStatisticsMapper operationStatisticsMapper;
    @Override
    public Object runJob(MSchedulerTask schedulerTask, ParamContextHolder paramContextHolder) throws Exception {
        try {
            //获取成功失败个数
            List<OperationStatistics> IsSuccessList= operationStatisticsMapper.findIsSuccessCountList();
            //获取ip个数
            List<OperationStatistics> IpCountList=operationStatisticsMapper.findIpCountList();

            Map<String, MSysOperationStatistics> map = new HashMap<>();

            for (OperationStatistics item: IsSuccessList) {
                String key = item.getApiId() + item.getApiVersion();
                MSysOperationStatistics statistics;
                if (map.containsKey(key)){
                    statistics = map.get(key);
                } else {
                    statistics = new MSysOperationStatistics();
                    statistics.setApiId(item.getApiId());
                    statistics.setApiVersion(item.getApiVersion());
                    map.put(key, statistics);
                }
                if(item.getIsSuccess() == 0){
                    statistics.setFailNum(item.getIsSuccessNum());
                } else {
                    statistics.setSuccessNum(item.getIsSuccessNum());
                }
            }

            for (OperationStatistics item: IpCountList) {
                String key = item.getApiId() + item.getApiVersion();
                MSysOperationStatistics statistics;
                if (map.containsKey(key)){
                    statistics = map.get(key);
                } else {
                    statistics = new MSysOperationStatistics();
                    statistics.setApiId(item.getApiId());
                    statistics.setApiVersion(item.getApiVersion());
                    map.put(key, statistics);
                }
                statistics.setIpNum(item.getIpCount());
            }

            for (String key : map.keySet()) {
                MSysOperationStatistics statistics = map.get(key);
                statistics.insert();
            }

            return  null;
        }catch (Exception e){
//            throw new ApiProcessException(ErrorCode.STORE_COUPON_ERROR, e.getMessage());
            throw new ApiProcessException("1234");
        }
    }
}
