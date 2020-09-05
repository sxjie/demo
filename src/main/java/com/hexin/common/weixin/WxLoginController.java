package com.hexin.common.weixin;

import com.thoughtworks.xstream.XStream;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 【类或接口功能描述】
 *
 * @author shenxiaojie
 * @version 1.0
 * @date 2020/8/5
 */

@RestController
public class WxLoginController {

    @Autowired
    private SignUtil signUtil;

    @Autowired
    private WxProperties wxProperties;

    @Autowired
    private WxMpService wxMpService;

    private static Logger logger = LoggerFactory.getLogger(WxLoginController.class);

    /**
     * 微信签名验证
     * 1. 将token、timestamp、nonce三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     * <p>
     * weixin-java-mp.jar包提供了wxMpService.checkSignature(timestamp,nonce, signature)方法，传入值返回布尔值
     */
    @RequestMapping(value = "wxsign")
    public String WeChatInterface(HttpServletRequest request) throws Exception {
        logger.info("[微信][微信签名验证][开始]");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        logger.info("[微信][微信签名验证][参数timestamp:" + timestamp + "nonce:" + nonce + "signature:" + signature + "]");
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            logger.info("[微信][微信签名验证][通过][结束]");
            return echostr;
        } else {
            // 此处可以实现其他逻辑
            logger.warn("[微信][不是微信服务器发过来的请求，请小心！]");
            return null;
        }
    }
//       http://tneqd5.natappfree.cc/wxLogin
    @RequestMapping("/wxLogin")
    public void wxLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 引导用户同意授权，获取用户code
        String url = wxMpService.oauth2buildAuthorizationUrl(wxProperties.getUrl() + "/callBack", "snsapi_userinfo", "STATE#wechat_redirect");
        //重定向
        response.sendRedirect(url);
    }

    //	回调方法
    @RequestMapping("/callBack")
    public void wxCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, WxErrorException {
        logger.info("用户同意授权,重定向到重定向地址,获取 code");
        String code = request.getParameter("code");
        logger.info("[微信][回调][用户code：" + code + "]");

        WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
        logger.info("[微信][回调][请求获取access_token：" + accessToken + "]");
        WxMpUser userInfo = wxMpService.oauth2getUserInfo(accessToken, null);
        logger.info("[微信][回调][用户信息：" + userInfo + "]");

    }



    @RequestMapping("/pages")
    public String pages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
//        response.sendRedirect( "/aaaaaaaa.html");
        return "redirect:/aaaaaaaa.html";
    }


    public WxMenu getMenu() {
        WxMenu menu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setName("百度一下");
        button1.setType(WxConsts.MenuButtonType.VIEW);
        button1.setUrl("http://www.baidu.com");





        WxMenuButton button2 = new WxMenuButton();
        button2.setName("贝贝同学");
        WxMenuButton button21 = new WxMenuButton();
        button21.setType(WxConsts.MenuButtonType.VIEW);
        button21.setName("贝贝一号");
        button21.setUrl(wxProperties.getUrl() + "/pages");

        WxMenuButton button22 = new WxMenuButton();
        button22.setType(WxConsts.MenuButtonType.VIEW);
        button22.setName("贝贝二号");
        button22.setUrl(wxProperties.getUrl() + "/pages");
        button2.getSubButtons().add(button21);
        button2.getSubButtons().add(button22);






        WxMenuButton button3 = new WxMenuButton();
        button3.setName("我的");

        WxMenuButton button31 = new WxMenuButton();
        button31.setType(WxConsts.MenuButtonType.VIEW);
        button31.setName("管理系统");
        button31.setUrl(wxProperties.getUrl() + "/login");

        WxMenuButton button33 = new WxMenuButton();
        button33.setType(WxConsts.MenuButtonType.VIEW);
        button33.setName("联系我们");
        button33.setUrl(wxProperties.getUrl() + "/wx/page/joinUs.html");

        WxMenuButton button35 = new WxMenuButton();
        button35.setType(WxConsts.MenuButtonType.VIEW);
        button35.setName("帮助");
        button35.setUrl(wxProperties.getUrl() + "/mortgage/weixin/contactme");

        button3.getSubButtons().add(button31);
        button3.getSubButtons().add(button33);
        button3.getSubButtons().add(button35);

        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        return menu;
    }
//       http://tneqd5.natappfree.cc/createMenu

    @RequestMapping("/createMenu")
    public void createMenu() {
        try {
            wxMpService.getMenuService().menuCreate(getMenu());
            System.out.println("success");
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }



}
