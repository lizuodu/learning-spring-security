package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.cache.RedisCache;
import com.example.demo.constants.CacheConstants;
import com.example.demo.constants.SecurityConstants;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.service.AccountService;
import com.example.demo.util.ServletUtil;

/**
 * 帐号操作
 * 
 * @author lizuodu
 * @date 2018年10月30日
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
		// 当前帐号权限存Redis中
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		/*for (Role role : account.getRoles()) {
			List<Permission> permissions = role.getPermissions();
			for (Permission permission : permissions) {
				if (permission != null && permission.getCode() != null) {
					grantedAuthorities.add(permission);
				}
			}
		}*/

		return new User(account.getUsername(), account.getPassword(), grantedAuthorities);
	}

	/**
	 * 查找账号
	 */
	@Override
	public List<Account> findByAccountModel(Account account) {
		List<Account> accountList = new ArrayList<>();
		String key = CacheConstants.AUTH_KEY_PREFIX + account.getUsername();
		accountList = this.redisCache.getList(key, Account.class);
		try {
			if (accountList == null || accountList.isEmpty()) {
				accountList = this.accountMapper.findByModel(account);
				this.redisCache.putList(key, accountList, SecurityConstants.EXPIRATION_TIME, TimeUnit.SECONDS);
			} else {
				accountList = this.redisCache.getList(key, Account.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return accountList;
	}

	/**
	 * 查找对应帐号权限
	 */
	@Override
	public List<Permission> getPermission(Account account) {
		List<Account> accountList = this.findByAccountModel(account);
		List<Permission> permissionList = new ArrayList<>();
		for (Account a : accountList) {
			List<Role> roleList = a.getRoles();
			for (Role r : roleList) {
				permissionList.addAll(r.getPermissions());
			}
		}
		return permissionList;
	}

	/**
	 * 根据资源类型对当前用户分组权限
	 */
	@Override
	public Map<String, List<Permission>> getPermissionGroupByResType() {

		SecurityContextImpl securityContextImpl = (SecurityContextImpl) ServletUtil.getRequest().getSession()
				.getAttribute("SPRING_SECURITY_CONTEXT");

		if (securityContextImpl == null) {
			return null;
		}

		String username = securityContextImpl.getAuthentication().getName();

		Account account = new Account();
		account.setUsername(username);
		List<Permission> authList = this.getPermission(account);

		Map<String, List<Permission>> groupbyPermissions = authList.stream()
				.collect(Collectors.groupingBy(Permission::getResType));

		return groupbyPermissions;
	}

}
