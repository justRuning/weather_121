package com.hebj.forecast.service;

import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.Forecast;

public interface ForecastService {

	String readForecast(Date time, int second, String forecastType);

	void save(List<Forecast> forecasts);

	List<Forecast> getLastForecast(String name);
}
