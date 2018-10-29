package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
		
		List<Account> list = this.accountMapper.findByModel(accountQuery);
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
		List<Account> list = this.accountMapper.findByModel(account);
		return list;
	}
	
	

}




