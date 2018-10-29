package com.example.demo.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
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
