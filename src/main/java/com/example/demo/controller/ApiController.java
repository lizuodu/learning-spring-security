package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * api权限测试
 * @author lizuodu
 * @date   2018年11月7日
 */
@Controller
@RequestMapping("/api")
public class ApiController {

	@RequestMapping("/get")
	@ResponseBody
	public Object get() {
		return "get";
	}

}
