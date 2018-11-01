package com.example.demo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PermissionService;

@RestController
@RequestMapping("/permission")
public class PermissionController {
	
	@Resource
	private PermissionService permissionService;
			
	@RequestMapping(value = "/get", method = {RequestMethod.GET})
	public Object get() {
		return this.permissionService.getPermissionGroupByResType();
	}
	

}
