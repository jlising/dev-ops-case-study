package com.ibm.ph.edm.common.dao;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface PersistableData<ID extends Serializable> extends Persistable<ID>{
}
