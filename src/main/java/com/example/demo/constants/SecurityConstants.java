package com.example.demo.constants;

/**
 * Spring Security 相关常量
 * @author lizuodu
 * @date   2018年11月6日
 */
public class SecurityConstants {

	/**
	 * 过期时间
	 */
	public static final long EXPIRATION_TIME = 12000; // 12000s

	public static final String USERNAME = "username";
	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_AUTH_STRING = "Authorization";
	public static final String ANT_API_PATTERN = "/api/**";
	public static final String API_ROLE = "API_ROLE";

}
