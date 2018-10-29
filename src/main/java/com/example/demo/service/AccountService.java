package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.model.Account;

public interface AccountService extends UserDetailsService {
	
	public List<Account> findByAccountModel(Account account);

}
