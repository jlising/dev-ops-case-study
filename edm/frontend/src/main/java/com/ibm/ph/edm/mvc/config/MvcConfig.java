package com.ibm.ph.edm.mvc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Configuration
@ComponentScan(basePackages = {"com.ibm.ph.edm.mvc"})
public class MvcConfig {
    private static final Logger LOG = LoggerFactory.getLogger(MvcConfig.class);

}
