package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.example.demo.model.Permission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.ServletUtil;

/**
 * 权限资源
 * @author lizuodu
 * @date   2018年10月30日
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	

	@Resource
	private PermissionService permissionService;

	/**
	 * 获取资源，构建ConfigAttribute
	 * permission->role,person.has(role)	
	 * person.roles.permissions.has(permission)
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal().equals("anonymousUser")) {
			HttpServletResponse response = ServletUtil.getResponse();
			try {
				response.sendRedirect("/login.html");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		FilterInvocation fi = (FilterInvocation) object;
		String requestURL = fi.getRequestUrl();
		if (requestURL.contains("?")) {
			requestURL = requestURL.substring(0, requestURL.indexOf("?"));  //不支持GET参数，跳转页面GET参数js自己可以处理
		}
		String requestMethod = fi.getRequest().getMethod();
		
		AntPathMatcher antPathMatcher = new AntPathMatcher();
        
		Collection<ConfigAttribute> configAttributes = new ArrayList<>();
		
		Permission permissions = this.permissionService.getPermissionByUrlAndMethod(requestURL, requestMethod);
		
		if (permissions != null) {
			if (antPathMatcher.match(permissions.getUrl(), requestURL) && requestMethod.equals(permissions.getMethod())) {
				ConfigAttribute configAttribute = new SecurityConfig(permissions.getCode());
				configAttributes.add(configAttribute);
			}
		}
		
		if (configAttributes.isEmpty()) {
			configAttributes.add(new SecurityConfig(UUID.randomUUID().toString()));
		}
		return configAttributes;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

}
