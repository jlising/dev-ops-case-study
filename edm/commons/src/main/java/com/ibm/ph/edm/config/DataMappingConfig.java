package com.ibm.ph.edm.config;

import com.ibm.ph.edm.paging.DataMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@Configuration
public class DataMappingConfig {
	@Bean
    public MapperFactory mapperFactory() {
        return new DefaultMapperFactory.Builder().mapNulls(false).build();
    }

    @Bean
    public DataMapper mapper(MapperFactory mapperFactory) {
        return new DataMapper(mapperFactory);
    }
}
