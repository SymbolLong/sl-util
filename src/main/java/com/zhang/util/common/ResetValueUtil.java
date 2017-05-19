package com.zhang.util.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;

public final class ResetValueUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void resetValue(Object object, Class<?> clzss,
			String orgValue, String newValue) throws IllegalArgumentException,
			IllegalAccessException {
		Class<?> superClzss = clzss.getSuperclass();
		if (superClzss != null) {
			resetValue(object, superClzss, orgValue, newValue);
		}
		Field[] fileds = clzss.getDeclaredFields();
		for (Field field : fileds) {
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}
			field.setAccessible(true);
			// 集合属性
			if (Iterable.class.isAssignableFrom(field.getType())) {
				Iterable<?> iterable = (Iterable<?>) field.get(object);
				if (iterable != null) {
					for (Iterator<?> iterator = iterable.iterator(); iterator
							.hasNext();) {
						Object iterableValue = iterator.next();
						resetValue(iterableValue, iterableValue.getClass(),
								orgValue, newValue);
					}
				}
			}
			if (field.getType().isAssignableFrom(String.class)) {
				Object oldValue = field.get(object);
				if (oldValue == null || orgValue.equals(oldValue.toString())) {
					field.set(object, newValue);
				}
			}
		}
	}

}
