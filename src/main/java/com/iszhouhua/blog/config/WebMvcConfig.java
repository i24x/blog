package com.iszhouhua.blog.config;

import com.iszhouhua.blog.controller.interceptor.AdminInterceptor;
import com.iszhouhua.blog.controller.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 *
 * @author ZhouHua
 * @date 2018/12/17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //前后台登录后都可以请求的内容
        String[] frontLoginPaths = {
                "/api/comment/save",
                "/api/logout",
                "/api/user/changePass"
        };
        //后台登录拦截器
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/api/**").
                excludePathPatterns(frontLoginPaths)
                .excludePathPatterns(
                        "/api/login",
                        "/api/uploadImage",
                        "/api/comment/more",
                        "/api/register"
                );
        //前台登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(frontLoginPaths);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 访问/admin重定向到/admin/index.html
        registry.addRedirectViewController("/admin", "/admin/index.html");
    }

    /**
     * 跨域配置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
