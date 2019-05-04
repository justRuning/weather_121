package com.hebj.forecast.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;

public interface ForecastService {

	String readForecast(Date time, int second, String forecastType)
			throws UnsupportedEncodingException, IOException, ParseException;

	List<Forecast> getLastForecast();

	List<Forecast> getWeChatForecast();

	List<Forecast> getLastForecast(Station staion, Date time, int hour, int age);

	List<Forecast> getLastForecast(Station staion, Date time, int hour);

	Forecast getForecast(Station station, Date time, int hour, int age, String type);

	TreeMap<String, List<Forecast>> getEveryTypeForecasts(Date time, int hour);

	TreeMap<String, List<Forecast>> getEveryTypeForecasts(Station station, Date time, int hour);

	void suppleForecast(Date time, int hour, String type);

	void makeYYmsg();

	void makeForecastMsg();

	/**
	 * 读取语音预报txt内容
	 * 
	 * @return
	 */
	LinkedHashMap<String, String> getYYForecast();

	LinkedHashMap<String, String> getForecastMsg();

}
