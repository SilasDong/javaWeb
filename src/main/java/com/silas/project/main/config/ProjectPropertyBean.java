package com.silas.project.main.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.silas.core.cache.CacheFactory;
import com.silas.core.constants.ErrorCode;
import com.silas.core.entity.CConfigParam;
import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.ICConfigParamService;
import com.silas.core.utils.RequestReflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ProjectPropertyBean {

    private final Logger logger = LoggerFactory.getLogger(ProjectPropertyBean.class);
    public static String PROJECT_CONFIG_PARAMS = "projecConfigParams";
    @Resource(name = "cacheFactory")
    CacheFactory cacheFactory;

    @Autowired
    private ICConfigParamService configParamService;

    private ProjectConfigParams configParams;

    @Value("${spring.datasource.username:}")
    private String dbUsername;
    @Value("${spring.datasource.dbhost:}")
    private String dbHost;

    @Value("${spring.datasource.password:}")
    private String dbPassword;
    @Value("${spring.datasource.dbname:}")
    private String dbName;


    public ProjectConfigParams getConfigParams() throws ApiProcessException {
        configParams = cacheFactory.getCacheService().getObj(PROJECT_CONFIG_PARAMS, ProjectConfigParams.class);
        if (configParams == null){
            EntityWrapper<CConfigParam> configParamEntityWrapper = new EntityWrapper<>();
            configParamEntityWrapper.eq("configType", 100);
            List<CConfigParam> list = configParamService.selectList(configParamEntityWrapper);
            try {
                configParams = RequestReflect.configParamReflects(list, ProjectConfigParams.class);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new ApiProcessException(ErrorCode.CONFIG_PARAM_TO_JSON_ERROR, e.getMessage());
            }
            cacheFactory.getCacheService().set(PROJECT_CONFIG_PARAMS, configParams);
        }
        return configParams;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }
}
