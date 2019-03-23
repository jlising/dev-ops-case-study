package com.ibm.ph.edm.paging;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingException;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class DataMapper {

    private MapperFactory mapperFactory;

    public DataMapper(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    public MapperFacade getMapper() {
        return mapperFactory.getMapperFacade();
    }

    public MapperFactory getMapperFactory() {
        return mapperFactory;
    }

    public <T, K> K map(T source, Class<K> dest) throws MappingException {
        return getMapper().map(source, dest);
    }

    public <T, K> K map(T source, K dest) throws MappingException {
        getMapper().map(source, dest);
        return dest;
    }
}