package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.example.demo.constants.SecurityConstants;
import com.example.demo.model.Permission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.JWTUtil;
import com.example.demo.util.SecurityUtil;
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

		Collection<ConfigAttribute> configAttributes = new ArrayList<>();

		HttpServletRequest request = ServletUtil.getRequest();
		HttpServletResponse response = ServletUtil.getResponse();
		RequestMatcher requestMatcher = new AntPathRequestMatcher(SecurityConstants.ANT_API_PATTERN);

		// 携带token请求/api
		if (requestMatcher.matches(request)) {
			String token = request.getHeader(SecurityConstants.HEADER_AUTH_STRING);
			String username = JWTUtil.getSubjectByToken(token);
			if (StringUtils.isEmpty(username)) {
				throw new AccessDeniedException("没有权限");
			}
			ConfigAttribute configAttribute = new SecurityConfig(SecurityConstants.API_ROLE);
			configAttributes.add(configAttribute);
		} else {
			// 未登录
			//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			SecurityContextImpl ctx = SecurityUtil.getSecurityContext();
			if (ctx == null) {
				try {
					response.sendRedirect("/login.html");
					return null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Authentication auth = ctx.getAuthentication();
			if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
				try {
					response.sendRedirect("/login.html");
					return null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 请求url
		FilterInvocation fi = (FilterInvocation) object;
		String requestURL = fi.getRequestUrl();
		if (requestURL.contains("?")) {
			requestURL = requestURL.substring(0, requestURL.indexOf("?"));  //忽略URL参数
		}
		String requestMethod = fi.getRequest().getMethod();
		AntPathMatcher antPathMatcher = new AntPathMatcher();


		// 获取请求对应权限
		Permission permissions = this.permissionService.getPermissionByUrlAndMethod(requestURL, requestMethod);
		if (permissions != null) {
			if (antPathMatcher.match(permissions.getUrl(), requestURL) && requestMethod.equals(permissions.getMethod())) {
				ConfigAttribute configAttribute = new SecurityConfig(permissions.getCode());
				configAttributes.add(configAttribute);
			}
		}

		// 没有匹配的权限
		if (configAttributes.isEmpty()) {
			configAttributes.add(null);
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
