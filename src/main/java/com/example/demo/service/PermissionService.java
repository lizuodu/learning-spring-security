package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.model.Permission;

public interface PermissionService {
	
	public List<Permission> getPermission(String accountName);
	
	public Permission getPermissionByUrlAndMethod(String url, String method);
	
	public Map<String, List<Permission>> getPermissionGroupByResType();
	

}
