package com.sanoxy.dao.user;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Sanoxy_user")
public class User {
	
	@Id
	@GenericGenerator(name = "id-generator", strategy = "com.sanoxy.repository.ResourceIdGenerator")
	@GeneratedValue(generator = "id-generator")
	private String id;
	@Column(nullable=false)
	private Integer permission;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private String salt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPermission() {
		return permission;
	}
	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
