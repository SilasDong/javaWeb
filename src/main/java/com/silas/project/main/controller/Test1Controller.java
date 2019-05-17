package com.silas.project.main.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.silas.project.main.config.ProjectPropertyBean;
import com.silas.core.cache.CacheFactory;
import com.silas.core.okhttp3.OkHttp3Endpoint;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.silas.module.sms.config.MSmsMsgPropertyBean.SMS_CONFIG_PARAMS;

@RestController
@RequestMapping("/test1")
public class Test1Controller {

    @Resource(name = "cacheFactory")
    CacheFactory cacheFactory;

    @Autowired
    private OkHttpClient httpClient;
    @Autowired
    private OkHttp3Endpoint http3Endpoint;


    @RequestMapping("/ehcach")
    public Object greeting(String resourceName) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://www.baidu.com";
        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        System.out.println(response.body().string());
        System.out.println(objectMapper.writeValueAsString(http3Endpoint));
        cacheFactory.getCacheService().del(ProjectPropertyBean.PROJECT_CONFIG_PARAMS);
        cacheFactory.getCacheService().del(SMS_CONFIG_PARAMS);
        cacheFactory.getCacheService().set("test", 123131221);
        int test  = cacheFactory.getCacheService().getObj("test",Integer.class);
        return test;
    }
}
