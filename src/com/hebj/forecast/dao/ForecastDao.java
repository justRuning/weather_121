package com.hebj.forecast.dao;

import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;

public interface ForecastDao {

	void save(List<Forecast> forecasts);

	List<Forecast> readForecast(Date time, int second,String forecastType);

	List<Forecast> getLastForecast(Station station);
}
