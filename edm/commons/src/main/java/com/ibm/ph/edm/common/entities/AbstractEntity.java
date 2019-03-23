package com.ibm.ph.edm.common.entities;

import com.ibm.ph.edm.common.dao.PersistableData;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
//http://javaprogrammingtips4u.blogspot.com/2010/04/field-versus-property-access-in.html
@MappedSuperclass
public abstract class AbstractEntity implements PersistableData<String> {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isNew() {
		return null == this.getId();
	}
}
