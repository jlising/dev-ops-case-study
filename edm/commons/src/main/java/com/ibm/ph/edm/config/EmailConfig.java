package com.ibm.ph.edm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@PropertySource("classpath:mail.properties")
@Configuration
public class EmailConfig {
    private static final String[] EMAIL_SMTP_HOST = {"d23ml100.ph.ibm.com","d23ml101.ph.ibm.com","d23ml102.ph.ibm.com","d23ml103.ph.ibm.com"};

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private Integer port;

    @Resource(name = "mailProperties")
    private Properties mailProperties;

    @Bean(name = "mailProperties")
    public PropertiesFactoryBean mapper() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("mail.properties"));
        return bean;
    }

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(mailProperties);
        javaMailSender.setHost(this.host);
        javaMailSender.setPort(this.port);

        return javaMailSender;
    }
}
