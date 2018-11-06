package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.model.Account;
import com.example.demo.model.Permission;

public interface AccountService extends UserDetailsService {
	
	public List<Account> findByAccountModel(Account account);
	
	public List<Permission> getPermission(Account account);
	
	public Map<String, List<Permission>> getPermissionGroupByResType();

}