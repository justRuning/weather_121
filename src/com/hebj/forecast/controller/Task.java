package com.hebj.forecast.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.service.ForecastService;
import com.hebj.forecast.service.WeatherActualService;

@Service
public class Task {

	@Autowired
	WeatherActualService weatherActualService;
	@Autowired
	ForecastService forecastService;

	public void doIt() {
		System.out.println(123);
	}

	public void weahterAct() {

		Calendar calendar = Calendar.getInstance();

		int minute = calendar.get(Calendar.MINUTE);
		List<WeatherActual> weatherActuals;
		weatherActuals = weatherActualService.readWeather(calendar.getTime());
		if (weatherActuals != null) {
			weatherActualService.save(weatherActuals);
		}
	}

	public void readT7() {

		Calendar calendar = Calendar.getInstance();
		int minute = calendar.get(Calendar.HOUR_OF_DAY);
		int second = minute < 14 ? 6 : 16;

		forecastService.readForecast(calendar.getTime(), second, "t7online");
	}

	public void forecast() {

		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);

		if (hours == 8 || hours == 6 || hours == 10 || hours == 16) {
			List<Forecast> forecasts;

		}
	}

}
