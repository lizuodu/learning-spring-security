package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.mapper.PermissionMapper;
import com.example.demo.model.Permission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.ServletUtil;

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
	 * 用户权限
	 */
	@Override
	public List<Permission> getPermission(String accountName) {
		List<Permission> permissionList = this.permissionMapper.getPermissionByUsername(accountName);
		return permissionList;
	}

	@Override
	public Permission getPermissionByUrlAndMethod(String url, String method) {
		return this.permissionMapper.getPermissionByUrlAndMethod(url, method);
	}
	
		
	/**
	 * 根据资源类型分组权限
	 */
	@Override
	public Map<String, List<Permission>> getPermissionGroupByResType() {
		
		HttpServletRequest request = ServletUtil.getRequest();
		
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
				.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		
		@SuppressWarnings("unchecked")
		Collection<Permission> authorities = (Collection<Permission>) securityContextImpl.getAuthentication().getAuthorities();
		List<Permission> authList = new ArrayList<>(authorities);
		
		Map<String, List<Permission>> groupbyPermissions = 
				authList.stream().collect(Collectors.groupingBy(Permission::getResType));
		
		return groupbyPermissions;
	}

	

}
