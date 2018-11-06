package com.example.demo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

/**
 * 
 * @author lizuodu
 * @date   2018年10月30日
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Resource private AccountService accountService;
	
	
	@RequestMapping("/get")
	@ResponseBody
	public Object getAccount() {
		Account account = new Account();
		account.setUsername("admin");
		return this.accountService.findByAccountModel(account);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Object add() {
		return "add";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete() {
		return "delete";
	}
	
	@RequestMapping(value = "/permission")
	@ResponseBody
	public Object get() {
		return this.accountService.getPermissionGroupByResType();
	}
	

}
