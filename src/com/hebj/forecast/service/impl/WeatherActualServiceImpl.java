package com.hebj.forecast.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.service.WeatherActualService;

@Service
public class WeatherActualServiceImpl implements WeatherActualService {

	@Autowired
	WeatherActualDao weatherActualDao;

	@Override
	public List<WeatherActual> readWeather(Date time) {
		List<WeatherActual> weatherActuals = weatherActualDao.readWeather(time);
		return weatherActuals;
	}

	@Override
	public void save(List<WeatherActual> weatherActuals) {
		weatherActualDao.save(weatherActuals);

	}

	@Override
	public List<WeatherActual> getWeather(String name) {
		List<WeatherActual> weatherActuals = weatherActualDao.getWeather(name);
		if (weatherActuals.size()<1) {
			return null;
		}
		else {
			return weatherActuals;
		}
	}

}
