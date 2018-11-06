package com.example.demo.service;

import com.example.demo.model.Permission;

public interface PermissionService {
	
	public Permission getPermissionByUrlAndMethod(String url, String method);
	

}
