package com.hebj.forecast.service;

import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.WeatherActual;


public interface WeatherActualService {


	List<WeatherActual> readWeather(Date time);
	void save(List<WeatherActual> weatherActuals);
	List<WeatherActual> getWeather(String name);
	
}
