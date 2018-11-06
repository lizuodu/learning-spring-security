package com.example.demo.util;

import org.springframework.security.core.context.SecurityContextImpl;

/**
 * Spring Security工具类
 * @author lizuodu
 * @date   2018年11月6日
 */
public class SecurityUtil {
	
	public static SecurityContextImpl getSecurityContext() {		
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) ServletUtil.getRequest().getSession()
				.getAttribute("SPRING_SECURITY_CONTEXT");		
		return securityContextImpl;
	}

}
