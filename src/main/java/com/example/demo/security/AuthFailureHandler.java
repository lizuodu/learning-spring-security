package com.example.demo.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 认证失败处理程序
 * @author lizuodu
 * @date   2018年11月7日
 */
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		JSONObject obj = new JSONObject();
		obj.put("isSuccess", false);
		obj.put("message", "FAILURE");
		PrintWriter out = response.getWriter();
		out.write(obj.toJSONString());
		out.flush();
		out.close();
	}

}
