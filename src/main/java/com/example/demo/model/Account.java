package com.example.demo.model;

import java.util.List;

/**
 * 帐号
 * @author lizuodu
 * @date   2018年10月29日
 */
public class Account {
	
	private Long id;
	private String username;
	private String password;
	private List<Role> roles;
	
	public Account() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


}
