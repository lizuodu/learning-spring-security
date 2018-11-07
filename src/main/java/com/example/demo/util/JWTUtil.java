package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.example.demo.constants.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT工具类
 * 
 * @author lizuodu
 * @date 2018年11月7日
 */
public class JWTUtil {

	/**
	 * 根据subject生成token
	 * 
	 * @param info
	 * @return
	 */
	public static String generateTokenBySubject(String subject) {
		String token = null;
		try {
			token = Jwts.builder().setSubject(subject)
					.setExpiration(Date.from(LocalDateTime.now().plusSeconds(SecurityConstants.EXPIRATION_TIME)
							.atZone(ZoneId.systemDefault()).toInstant()))
					.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET).compact();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return token;
	}

	/**
	 * 从token中获取subject
	 * 
	 * @param token
	 * @return
	 */
	public static String getSubjectByToken(String token) {
		String username = null;
		try {
			Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
					.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
			Claims claims = jwsClaims.getBody();
			username = claims.getSubject();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return username;
	}

}
