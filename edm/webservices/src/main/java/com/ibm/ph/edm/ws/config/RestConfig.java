package com.ibm.ph.edm.ws.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Configuration
@ComponentScan(basePackages = {"com.ibm.ph.edm.ws"})
public class RestConfig {
    private static final Logger LOG = LoggerFactory.getLogger(RestConfig.class);

}
