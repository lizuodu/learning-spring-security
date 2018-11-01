package com.example.demo.cache;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 基于Redis的Cache简单实现
 * 
 * @author lizuodu
 * @date 2018/09/27
 */
@Component("redisCache")
public class RedisCache {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void put(String key, String value, long expire, TimeUnit... timeUnits) {
		ValueOperations<String, String> valueOps = this.redisTemplate.opsForValue();
		if (timeUnits.length > 0) {
			valueOps.set(key, value, expire, timeUnits[0]);
		} else {
			valueOps.set(key, value, expire, TimeUnit.SECONDS);
		}
	}

	public String get(String key) {
		ValueOperations<String, String> valueops = this.redisTemplate.opsForValue();
		String value = valueops.get(key);
		return value;
	}
	
	public <T> void putObject(String key, T t, Long expire, TimeUnit... timeUnits) {
		String json = JSON.toJSONString(t);
		this.put(key, json, expire, timeUnits);
	}
	
	public <T> T getObject(String key, Class<T> clazz) {
		String json = this.get(key);
		T t = JSON.parseObject(json, clazz);
		return t;
	}
	
	public <T> void putList(String key, List<T> t, Long expire, TimeUnit... timeUnits) {
		String json = JSON.toJSONString(t);
		this.put(key, json, expire, timeUnits);
	}
	
	public <T> List<T> getList(String key, Class<T> clazz) {
		String json = this.get(key);
		List<T> t = JSONArray.parseArray(json, clazz);
		return t;
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	public void deleteByPattern(String patternKey) {
		Set<String> keys = this.redisTemplate.keys(patternKey);
		this.redisTemplate.delete(keys);
	}
	

}