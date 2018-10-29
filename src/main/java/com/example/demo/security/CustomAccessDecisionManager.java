package com.example.demo.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.model.Permission;

/**
 * 权限决策
 * @author lizuodu
 * @date   2018年10月30日
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
					
		// 用户权限
		@SuppressWarnings("unchecked")
		Collection<Permission> authorities = (Collection<Permission>) authentication.getAuthorities();
		Iterator<Permission> ait = authorities.iterator();
		
		// 资源权限
		Iterator<ConfigAttribute> cit = configAttributes.iterator();
		
		while (cit.hasNext()) {
			ConfigAttribute configAttribute = cit.next();
			while (ait.hasNext()) {
				Permission permission = ait.next();
				if (configAttribute.getAttribute().equals(permission.getCode())) {
					return;
				}
			}			
		}
		
		throw new AccessDeniedException("没有权限");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}


}
