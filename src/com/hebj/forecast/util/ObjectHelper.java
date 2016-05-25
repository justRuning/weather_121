package com.hebj.forecast.util;

import java.lang.reflect.Method;

public class ObjectHelper {

	public static Object merge(Object obj, Object update) {
		if (!obj.getClass().isAssignableFrom(update.getClass())) {
			return null;
		}

		Method[] methods = obj.getClass().getMethods();

		for (Method fromMethod : methods) {
			if (fromMethod.getDeclaringClass().equals(obj.getClass()) && fromMethod.getName().startsWith("get")) {

				String fromName = fromMethod.getName();
				String toName = fromName.replace("get", "set");

				try {
					Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
					Object valueObj = fromMethod.invoke(obj, (Object[]) null);

					if (valueObj == null) {
						Object value = fromMethod.invoke(update, (Object[]) null);
						if (value != null) {
							toMetod.invoke(obj, value);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

}
