package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.cache.RedisCache;
import com.example.demo.constants.ConstantCache;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.service.AccountService;

/**
 * 帐号操作
 * @author lizuodu
 * @date   2018年10月30日
 */
@Service
public class AccountServiceImpl implements AccountService {
		
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private RedisCache redisCache;

	/**
	 * 加载认证通过帐号角色及权限资源
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		if (username == null || username == "") {
			throw new RuntimeException("帐号错误");
		}
		
		Account accountQuery = new Account();
		accountQuery.setUsername(username);
		
		List<Account> list = this.findByAccountModel(accountQuery);
		if (list == null || list.isEmpty()) {
			throw new UsernameNotFoundException("没有用户" + username);
		}
		
		Account account = list.get(0);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role: account.getRoles()) {
            List<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                if (permission != null && permission.getCode() != null) {
                    grantedAuthorities.add(permission);
                }
            }
        }
        
        return new User(account.getUsername(), account.getPassword(), grantedAuthorities);
	}

	/**
	 * 查找账号
	 */
	@Override
	public List<Account> findByAccountModel(Account account) {
		List<Account> accountList = null;
		String key = ConstantCache.AUTH_KEY_PREFIX + account.getUsername();
		String accountStr = this.redisCache.get(key);
		try {
			if (StringUtils.isEmpty(accountStr)) {
				accountList = this.accountMapper.findByModel(account);
				redisCache.put(key, JSON.toJSONString(accountList), 1200, TimeUnit.SECONDS);
			} else {
				accountList = JSON.parseArray(accountStr, Account.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return accountList;
	}
	
	

}




