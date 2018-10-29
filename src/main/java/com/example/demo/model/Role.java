package com.example.demo.model;

import java.util.List;

/**
 * 角色
 * @author lizuodu
 * @date   2018年10月29日
 */
public class Role {

	private Long id;
	private String name;
	private List<Permission> permissions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}
