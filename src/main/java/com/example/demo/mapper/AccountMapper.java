package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Account;

/**
 * 帐号Mapper
 * @author lizuodu
 * @date   2018年10月29日
 */
@Mapper
public interface AccountMapper {
	
	/**
	 * 获取一个账号
	 * @param account
	 * @return
	 */
	public List<Account> findByModel(Account account);

}
