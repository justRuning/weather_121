package com.hebj.forecast.dao;

import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;

public interface WeatherActualDao {

	List<WeatherActual> readWeather(Date time);
	void save(List<WeatherActual> weatherActuals);
	List<Station> getStations();
	List<WeatherActual> getWeather(String name);
}
