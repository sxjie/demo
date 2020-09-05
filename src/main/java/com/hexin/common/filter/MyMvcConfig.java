package com.hexin.common.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @Description TODO
 * @ClassName MyMvcConfig
 * @Author shenxiaojie
 * @Date 2019-10-25 11:22
 * @Version 1.0
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /***
     * 设置静态资源过滤等
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/resources/");
    }

    /**
     * 视图跳转控制器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index").setViewName("/login");
        registry.addViewController("/main.html").setViewName("index");
    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**") // 拦截所有请求
//                // 排除不需求拦截的请求
//                .excludePathPatterns("/", "/index.html", "/user/login");
//    }


//    /* 拦截器配置 */
//    void addInterceptors(InterceptorRegistry var1);

//    /**
//     *静态资源处理
//     **/
//    void addResourceHandlers(ResourceHandlerRegistry registry);
//    /* 默认静态资源处理器 */
//    void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);
//    /**
//     * 这里配置视图解析器
//     **/
//    void configureViewResolvers(ViewResolverRegistry registry);
//    /* 配置内容裁决的一些选项*/

}
