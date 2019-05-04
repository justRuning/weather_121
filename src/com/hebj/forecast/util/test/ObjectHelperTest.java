package com.hebj.forecast.util.test;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.util.ObjectHelper;

public class ObjectHelperTest {

	@SuppressWarnings("unused")
	@Test
	public void test() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Forecast forecast = new Forecast();
		forecast.setId(1);
		Forecast forecast2 = new Forecast();
		forecast2.setAge(12);
		Forecast forecast3 = (Forecast) ObjectHelper.merge(forecast, forecast2);
		forecast.setMaxTemp("20");
	}

}
