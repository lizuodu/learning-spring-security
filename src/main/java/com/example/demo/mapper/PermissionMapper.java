package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Permission;

/**
 * 权限Mapper
 * 
 * @author lizuodu
 * @date 2018年10月29日
 */
@Mapper
public interface PermissionMapper {

	/**
	 * 获取角色对应权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermissionByRoleId(@Param("id") Long roleId);
	
	/**
	 * 获取帐号对应权限
	 * @param accountName
	 * @return
	 */
	public List<Permission> getPermissionByUsername(@Param("username") String accountName);
	
	/**
	 * 获取资源权限
	 * @param url
	 * @param method
	 * @return
	 */
	public Permission getPermissionByUrlAndMethod(@Param("url") String url, @Param("method") String method);

}
