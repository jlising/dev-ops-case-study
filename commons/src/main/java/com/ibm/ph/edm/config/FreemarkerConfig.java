package com.ibm.ph.edm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@PropertySource("classpath:mail.properties")
@Configuration
public class FreemarkerConfig {

    @Value("${mail.templates.path}")
    private String templatesPath;

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/" + this.templatesPath);
        return bean;
    }
}
