package org.apereo.cas.config;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.apereo.cas.filter.LoginCheckFilter;
import org.apereo.cas.servlet.CheckKaptchaCodeServlet;
import org.apereo.cas.servlet.ForgetPwdRedirectServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/4/9 8:22
 */
@Configuration
public class LocalApiConfig {
    @Value("${user.forget-pwd-redirect-url}")
    private String forgetPwdRedirectUrl;

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet =
                new ServletRegistrationBean(new ForgetPwdRedirectServlet(forgetPwdRedirectUrl), "/pm/forget");
        return servlet;
    }

    @Bean
    public ServletRegistrationBean kaptchaServletRegisBean() {
        ServletRegistrationBean servlet =
                new ServletRegistrationBean(new KaptchaServlet(), "/images/kaptcha.jpg");
        servlet.addInitParameter("kaptcha.border", "no");
        return servlet;
    }

    @Bean
    public ServletRegistrationBean checkCodeServletRegisBean() {
        ServletRegistrationBean servlet =
                new ServletRegistrationBean(new CheckKaptchaCodeServlet(), "/check/code");
        return servlet;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilterRegisBean() {
        FilterRegistrationBean regisBean = new FilterRegistrationBean(new LoginCheckFilter());
        regisBean.addUrlPatterns("/login");
        regisBean.setOrder(1);
        regisBean.setEnabled(true);

        return regisBean;
    }

}
