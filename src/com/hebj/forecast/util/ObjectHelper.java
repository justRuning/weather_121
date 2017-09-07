package com.hebj.forecast.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hebj.forecast.entity.Forecast;

//新的对象替换久对象
public class ObjectHelper {

	/**
	 * 新的对象替换久的对象，如果久对象值为null则替换
	 * 
	 * @param obj
	 * @param update
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object merge(Object obj, Object update) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (!obj.getClass().isAssignableFrom(update.getClass())) {
			return null;
		}

		Method[] methods = obj.getClass().getMethods();

		for (Method fromMethod : methods) {
			if (fromMethod.getDeclaringClass().equals(obj.getClass()) && fromMethod.getName().startsWith("get")) {

				String fromName = fromMethod.getName();
				String toName = fromName.replace("get", "set");
				Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
				Object valueObj = fromMethod.invoke(obj, (Object[]) null);

				if (valueObj == null) {
					Object value = fromMethod.invoke(update, (Object[]) null);
					if (value != null) {
						toMetod.invoke(obj, value);
					}
				}
			}
		}
		return obj;
	}
	
	/**
	 * 新对象代替久对象，除了id
	 * @param oldObj
	 * @param newObj
	 * @return
	 */
	public static Forecast replaceForecast (Forecast oldObj,Forecast newObj){
		newObj.setId(oldObj.getAge());
		return newObj;
	}
	


}
