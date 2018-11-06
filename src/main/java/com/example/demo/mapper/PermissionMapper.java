package com.example.demo.mapper;

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
	 * 获取资源权限
	 * @param url
	 * @param method
	 * @return
	 */
	public Permission getPermissionByUrlAndMethod(@Param("url") String url, @Param("method") String method);

}
