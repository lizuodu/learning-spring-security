package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Role;

/**
 * 角色Mapper
 * @author lizuodu
 * @date   2018年10月29日
 */
@Mapper
public interface RoleMapper {

	/**
	 * 根据账号id找对应的角色
	 * @param userId
	 * @return
	 */
	public List<Role> findByAccountId(@Param("id") Long accountId);


}
