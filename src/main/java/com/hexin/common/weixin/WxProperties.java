package com.hexin.common.weixin;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  微信相关配置信息
 *
 * @author shenxiaojie
 * @version 1.0
 * @date 2020/8/12
 */
@Component
@ConfigurationProperties(prefix = "wx.configs")
public class WxProperties {

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String appsecret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    private String url;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




//          accessToken
//    private String accessToken; //网页授权接口调用凭证
//    private int expiresIn; //凭证有效时长
//    private String refreshToken; //用于刷新凭证
//    private String openId; //用户标识
//    private String scope; //用户授权作用域
//
//
//         用户信息
//    private Integer id; //主键 id
//    private String openId; //用户主键
//    private String nickname; //用户昵称
//    private Integer sex; //性别(1:男,2:女,0:未知)
//    private String country; //国家
//    private String province; //省份
//    private String city; //城市
//    private String headimgurl; //用户头像链接
//    private String unionid; //unionid
//    private List<String> privilegeList; //用户特权信息
}
