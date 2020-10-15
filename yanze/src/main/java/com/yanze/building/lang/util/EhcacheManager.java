
package com.yanze.building.lang.util;


import com.yanze.building.dao.model.SysUser;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Ehcache管理器
 * 
 * @author sulei
 *
 */
public class EhcacheManager {

	public static final Cache cache;

	static {
		// 初始化
		CacheManager cacheManager = CacheManager.create();
		cache = cacheManager.getCache("NormalCache");
	}

	/**
	 * 设置通用缓存
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}

	/**
	 * 获取通用缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Element value = cache.get(key);
		if (value != null) {
			return value.getObjectValue();
		}

		return null;
	}

	private static final String KEY_USER = "USER_";

	/**
	 * 设置用户缓存
	 * 
	 * @param key
	 * @param value
	 */
	public static void putUser(String key, SysUser value) {
		put(KEY_USER + key, value);
	}

	/**
	 * 获取用户缓存
	 * 
	 * @param key
	 * @return
	 */
	public static SysUser getUser(String key) {
		return (SysUser) get(KEY_USER + key);
	}
}
