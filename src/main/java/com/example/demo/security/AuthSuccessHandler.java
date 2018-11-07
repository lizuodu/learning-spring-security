package com.example.demo.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.constants.SecurityConstants;
import com.example.demo.util.JWTUtil;

/**
 * 认证成功执行程序
 * 生成token返回
 * @author lizuodu
 * @date   2018年11月7日
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		UserDetails user = (UserDetails) authentication.getPrincipal();
		String token = JWTUtil.generateTokenBySubject(user.getUsername());

		response.addHeader(SecurityConstants.HEADER_AUTH_STRING, SecurityConstants.TOKEN_PREFIX + token);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json;charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("isSuccess", true);
		obj.put("message", "OK");
		PrintWriter out = response.getWriter();
		out.write(obj.toJSONString());
		out.flush();
		out.close();
	}

}
