package com.example.demo.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * 资源权限
 * 
 * @author lizuodu
 * @date 2018年10月29日
 */
public class Permission implements GrantedAuthority {

	private static final long serialVersionUID = 3456306811083761877L;

	private Long id;
	private String code;
	private String description;
	private String resType;
	private String url;
	private String method;
	private Long pid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Override
	public String getAuthority() {
		return this.code;
	}


}
