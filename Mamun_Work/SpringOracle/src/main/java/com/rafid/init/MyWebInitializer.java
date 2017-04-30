package com.rafid.init;

/**
 * Created by ab9ma on 4/24/2017.
 *
 * needed for file upload--- created following https://www.mkyong.com/spring-mvc/spring-mvc-file-upload-example/
 */

import com.rafid.config.SpringWebMvcConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;


public class MyWebInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

   // @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{//SpringWebMvcConfig.class  //--mamun commented this out to disable config
                 };
    }

   // @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

   // @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

   // @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        // upload temp file will put here
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        // register a MultipartConfigElement
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

        registration.setMultipartConfig(multipartConfigElement);

    }

}