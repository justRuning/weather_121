package com.hebj.forecast.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.service.ForecastService;

@Service
public class ForecastServiceImpl implements ForecastService {

	@Autowired
	ForecastDao forecastDao;

	@Override
	public String readForecast(Date time, int second, String forecastType) {
		List<Forecast> forecasts;
		forecasts = forecastDao.readForecast(time, second, forecastType);
		forecastDao.save(forecasts);
		return "success";
	}

	@Override
	public void save(List<Forecast> forecasts) {
		forecastDao.save(forecasts);

	}

	@Override
	public List<Forecast> getLastForecast(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
