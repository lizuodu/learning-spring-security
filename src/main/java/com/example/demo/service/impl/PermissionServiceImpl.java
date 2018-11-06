package com.example.demo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.PermissionMapper;
import com.example.demo.model.Permission;
import com.example.demo.service.PermissionService;

/**
 * 
 * @author lizuodu
 * @date   2018年10月31日
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionMapper permissionMapper;
	
	/**
	 * 根据请求的url找到对应的权限
	 */
	@Override
	public Permission getPermissionByUrlAndMethod(String url, String method) {
		return this.permissionMapper.getPermissionByUrlAndMethod(url, method);
	}
	

}
