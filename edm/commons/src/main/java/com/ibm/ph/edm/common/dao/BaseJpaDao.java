package com.ibm.ph.edm.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by Jesus Lising <jess.lising@gmail.com>
 */

@NoRepositoryBean
public interface BaseJpaDao<T extends PersistableData, ID extends Serializable> extends BaseDao<T,ID>, JpaRepository<T, ID>, PagingAndSortingDao<T,ID> {
    T findById(ID var1);
    T deleteById(ID var1);
}
