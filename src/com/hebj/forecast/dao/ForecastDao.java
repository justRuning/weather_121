package com.hebj.forecast.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;

public interface ForecastDao {

	void save(List<Forecast> forecasts);

	List<Forecast> readForecast(Date time, int hour, String forecastType)
			throws UnsupportedEncodingException, IOException, ParseException;

	List<Forecast> getLastForecast(List<Station> stations);

	Forecast getForecast(Station station, Date time, int hour, String type, int age);

	List<Forecast> getforecast(Station station, Date time, int hour, int age);

	List<Forecast> getForecast(Station station, Date time, int hour);

	List<Forecast> getForecast(Date time, int hour,String type);
	
	List<Forecast> getForecast(Station station,Date time, int hour, String type);

}
