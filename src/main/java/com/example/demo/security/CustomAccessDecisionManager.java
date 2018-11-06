package com.example.demo.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.model.Account;
import com.example.demo.model.Permission;
import com.example.demo.service.AccountService;

/**
 * 权限决策
 * @author lizuodu
 * @date   2018年10月30日
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
	
	@Resource private AccountService accountService;

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
					
		Account account = new Account();
		account.setUsername(authentication.getName());
		List<Permission> permissionList = accountService.getPermission(account);
		
		// 资源权限
		Iterator<ConfigAttribute> cit = configAttributes.iterator();
		
		while (cit.hasNext()) {
			ConfigAttribute configAttribute = cit.next();
			if (configAttributes == null) {
				throw new AccessDeniedException("没有权限");
			}
			for (Permission p: permissionList) {
				if (configAttribute.getAttribute().equals(p.getCode())) {
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
