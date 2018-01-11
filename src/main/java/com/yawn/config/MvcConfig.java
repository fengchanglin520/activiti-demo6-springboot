package com.yawn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by yawn on 2017/8/5.
 */
@EnableWebMvc
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index");
        registry.addViewController("/user");
//        registry.addRedirectViewController("/index","/templates/index.html");
        registry.addRedirectViewController("/","/templates/login.html");
//        registry.addStatusController("/403", HttpStatus.FORBIDDEN);
        super.addViewControllers(registry);
    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addWebRequestInterceptor();
////        registry.addInterceptor();
//        super.addInterceptors(registry);
//    }
//
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
////        registry.addConverter();
//        super.addFormatters(registry);
//    }
//
//    // 跨域(cross-site)请求的配置注册
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/api/**")
////                .allowedOrigins("http://domain2.com")
////                .allowedMethods("PUT", "DELETE")
////                .allowedHeaders("header1", "header2", "header3")
////                .exposedHeaders("header1", "header2")
////                .allowCredentials(false).maxAge(3600);
//        super.addCorsMappings(registry);
//    }
//
//    //    @Bean
////    public InternalResourceViewResolver resolver(){
////        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
////        resolver.setSuffix(".jsp");
////        resolver.setPrefix("classpath:/templates/jsp/");
////        return resolver;
////    }
//
////    @Override
////    public void configureViewResolvers(final ViewResolverRegistry registry) {
////        registry.jsp("classpath:/templates/jsp/", ".jsp");
////    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
}
