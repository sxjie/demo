package com.hexin.common.weixin;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 【类或接口功能描述】
 *
 * @author shenxiaojie
 * @version 1.0
 * @date 2020/8/12
 */
@Component
public class Configs {

    @Autowired
    private WxProperties wxProperties;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService=new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

//    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage=new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(wxProperties.getAppId());
        wxMpInMemoryConfigStorage.setSecret(wxProperties.getAppsecret());
        wxMpInMemoryConfigStorage.setToken(wxProperties.getToken());
        return  wxMpInMemoryConfigStorage;
    }
}
