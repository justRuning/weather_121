package com.hebj.forecast.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.util.GetForecast;

@Component
public class ForecastDaoImpl implements ForecastDao {

	@Autowired
	protected HibernateTemplate HibernateTemplate;
	@Autowired
	protected StationDao stationDao;
	@Autowired
	GetForecast getForecast;

	@Override
	public List<Forecast> readForecast(Date time, int hour, String forecastType) {
		List<Forecast> forecasts;
		

		switch (forecastType.toLowerCase()) {
		case "local":
			forecasts = getForecast.getLocalForecast(time, hour);
			break;
		case "american":
			forecasts = getForecast.getAmericanForecast(time, hour);
			break;
		case "t7online":
			forecasts = getForecast.getT7OnlineForecast(time, hour);
			break;
		case "ec":
			
		default:
			forecasts = null;
			break;
		}
		return forecasts;
	}

	@Override
	public void save(List<Forecast> forecasts) {
		if (forecasts == null) {
			return;
		}
		for (Forecast forecast : forecasts) {
			String hql = "from Forecast f where f.station=? and f.age=? and f.beginTime =? and f.hour =? and f.forecastType =?";
			Object[] params = new Object[5];
			params[0] = forecast.getStation();
			params[1] = forecast.getAge();
			params[2] = forecast.getBeginTime();
			params[3] = forecast.getHour();
			params[4] = forecast.getForecastType();
			List<?> forecast2 = HibernateTemplate.find(hql, params);
			if (forecast2.size() < 1) {
				HibernateTemplate.save(forecast);
			} else {
				forecast.setId(((Forecast) (forecast2.get(0))).getId());
				HibernateTemplate.clear();
				HibernateTemplate.saveOrUpdate(forecast);
			}
		}

	}

	@Override
	public List<Forecast> getLastForecast(Station station) {
		String hql = "from Forecast f where f.statione=? order by f.id DESC";
		Object[] params = new Object[1];
		params[0] = station;
		HibernateTemplate.setMaxResults(7);
		List<?> list = HibernateTemplate.find(hql, params);
		if (list.size() < 1) {
			return null;
		}
		Collections.reverse(list);
		return (List<Forecast>) list;
	}

}
