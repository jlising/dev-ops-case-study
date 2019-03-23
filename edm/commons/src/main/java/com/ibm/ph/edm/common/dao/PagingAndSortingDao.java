package com.ibm.ph.edm.common.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface PagingAndSortingDao <T, ID extends Serializable> extends CrudDao<T, ID> {
    Iterable<T> findAll(Sort var1);

    Page<T> findAll(Pageable var1);
}

