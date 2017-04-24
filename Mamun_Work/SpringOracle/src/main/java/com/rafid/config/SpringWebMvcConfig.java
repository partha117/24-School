package com.rafid.config;

/**
 * Created by ab9ma on 4/24/2017.
 *
 * needed for file upload--- created following https://www.mkyong.com/spring-mvc/spring-mvc-file-upload-example/
 */

import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


/***
 *Important: For some reason, bootstrap library is not working when this class is operational.. To get back bootstrap design, comment
 * out all of the annotations
 *
 *
 *
 *
 */
//@EnableWebMvc
//@Configuration
//@ComponentScan({"com.rafid.config"})
public class SpringWebMvcConfig extends WebMvcConfigurerAdapter {

    // Bean name must be "multipartResolver", by default Spring uses method name as bean name.
  //  @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

	/*
	// if the method name is different, you must define the bean name manually like this :
	@Bean(name = "multipartResolver")
    public MultipartResolver createMultipartResolver() {
        return new StandardServletMultipartResolver();
    }*/

    //@Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}