package com.hayate.wechat.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Map工具类
 * @author kaede
 *
 */
public class MapUtil {
	/**
	 * Map转对象
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass){
		if (map == null)
			return null;

		Object obj;
		try {
			obj = beanClass.newInstance();
			BeanUtils.populate(obj, map);
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 对象转Map
	 * @param obj
	 * @return
	 */
	public static Map<?, ?> objectToMap(Object obj) {
		if (obj == null)
			return null;

		return new org.apache.commons.beanutils.BeanMap(obj);
	}
}
