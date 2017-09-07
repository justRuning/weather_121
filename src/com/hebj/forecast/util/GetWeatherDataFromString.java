package com.hebj.forecast.util;

import com.hebj.forecast.entity.WeatherActual;

public class GetWeatherDataFromString {

	public static WeatherActual getData(String line) {

		/*
		 * 根据预报文件字符串分割天气预报
		 */
		WeatherActual weatherActual = new WeatherActual();
		String[] lines = line.split("\r\n");
		String[] strings;

		if (lines.length < 10) {
			return null;
		}

		String regex = "\\s+";
		strings = lines[1].split(regex);

		int value;
		if (!strings[1].contains("//")) {
			value = Integer.parseInt(strings[1]);
			weatherActual.setBarometricPressure(String.valueOf(value));
		}

		strings = lines[2].split(regex);
		value = 1000 - Integer.parseInt(strings[1]);
		weatherActual.setTem(String.valueOf(value));
		value = 1000 - Integer.parseInt(strings[2]);
		weatherActual.setMaxTemp(String.valueOf(value));
		value = 1000 - Integer.parseInt(strings[4]);
		weatherActual.setMinTemp(String.valueOf(value));
		value = 1000 - Integer.parseInt(strings[7]);
		weatherActual.setMaxTemp24(String.valueOf(value));
		value = 1000 - Integer.parseInt(strings[8]);
		weatherActual.setMinTemp24(String.valueOf(value));
		weatherActual.setRelativeHumidity(strings[10]);

		strings = lines[3].split(regex);
		weatherActual.setWater(strings[1]);
		weatherActual.setWater3(strings[2]);
		weatherActual.setWater6(strings[3]);
		weatherActual.setWater12(strings[4]);
		weatherActual.setWater24(strings[5]);

		strings = lines[4].split(regex);
		weatherActual.setWindDirection2min(strings[1]);
		weatherActual.setWindVelocity2min(strings[2]);
		weatherActual.setWindDirection10min(strings[3]);
		weatherActual.setWindVelocity10min(strings[4]);

		strings = lines[5].split(regex);
		value = 1000 - Integer.parseInt(strings[1]);
		weatherActual.setSurfaceTemp(String.valueOf(value));

		strings = lines[6].split(regex);
		weatherActual.setVisibility(strings[2]);

		return weatherActual;
	}

}
