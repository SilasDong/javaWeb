package com.silas.project.main.apiresultservice;

import com.silas.project.main.config.ProjectPropertyBean;
import com.silas.core.cache.CacheFactory;
import com.silas.core.entity.CApiBase;
import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.IXcdApiResultProcessService;
import com.silas.core.service.ParamContextHolder;
import com.silas.core.utils.TypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.silas.module.filemanager.config.MFilemanagerPropertyBean.FILE_CONFIG_PARAMS;
import static com.silas.module.sms.config.MSmsMsgPropertyBean.SMS_CONFIG_PARAMS;
import static com.silas.module.sys.config.MSysPropertyBean.SYS_CONFIG_PARAMS;
import static com.silas.module.wechat.config.WeChatPropertyBean.WECHAT_CONFIG_PARAMS;

/**
 * 清空项目模块的缓存
 */
@Service("clearConfigCacheService")
public class ClearConfigCacheService implements IXcdApiResultProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ClearConfigCacheService.class);
    @Resource(name = "cacheFactory")
    CacheFactory cacheFactory;

    @Override
    public Object processApiResult(ParamContextHolder paramContextHolder, CApiBase cApiBase, Object o) throws ApiProcessException {
        int configType = TypeUtil.toInt(paramContextHolder.getString("configType"));
        if (cacheFactory != null && cacheFactory.getCacheService() != null){
            switch (configType){
                case 2:
                    cacheFactory.getCacheService().del(SYS_CONFIG_PARAMS);
                    break;
                case 3:
                    cacheFactory.getCacheService().del(SMS_CONFIG_PARAMS);
                    break;
                case 4:
                    cacheFactory.getCacheService().del(WECHAT_CONFIG_PARAMS);
                    break;
                case 6:
                    cacheFactory.getCacheService().del(FILE_CONFIG_PARAMS);
                    break;
                //case 7:
                //    cacheFactory.getCacheService().del(ALIPAY_CONFIG_PARAMS);
                //    break;
                case 100:
                    cacheFactory.getCacheService().del(ProjectPropertyBean.PROJECT_CONFIG_PARAMS);
                    break;
                default:
            }
            logger.info("c_config_param "+ configType +" cache cleared");
        }
        return o;
    }
}
