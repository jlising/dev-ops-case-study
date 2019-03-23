package com.ibm.ph.edm.paging;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Component
@Lazy(value = false)
public final class MapperUtil {

    private static DataMapper MAPPER;

    @Autowired(required = true)
    private MapperUtil(DataMapper dataMapper) {
        MAPPER = dataMapper;
    }

    /**
     * Shorthand for mapping a sourceList to another List of destination DTO using
     * the Mapper services.
     *
     * @param dataMapper
     * @param sourceList
     * @param clazz
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<K> mapList(DataMapper dataMapper, Iterable<T> sourceList, Class<K> clazz) {
        List<K> destList = Lists.newArrayList();
        for (T item : sourceList) {
            destList.add(dataMapper.map(item, clazz));
        }
        return destList;
    }

    public static <T, K> List<K> mapList(Iterable<T> sourceList, Class<K> clazz) {
        return mapList(MAPPER, sourceList, clazz);
    }

    public static <T, K> K mapDTO(T source, Class<K> clazz) {

        return MAPPER.map(source, clazz);
    }

    public static <T, K> K mapDTO(T source, K dest) {

        MAPPER.map(source, dest);

        return dest;
    }
}