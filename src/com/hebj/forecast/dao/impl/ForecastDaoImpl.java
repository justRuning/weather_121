package com.hebj.forecast.dao.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.GetForecast;
import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.util.ForecastHelper;

@Repository
public class ForecastDaoImpl implements ForecastDao {

	@Autowired
	StationDao stationDao;

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Autowired
	GetForecast getForecast;

	@Override
	public List<Forecast> readForecast(Date time, int hour, String forecastType)
			throws UnsupportedEncodingException, IOException, ParseException {
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
			forecasts = getForecast.getEcForecast(time, hour);
			break;
		case "t639":
			forecasts = getForecast.getT639Forecast(time, hour);
			break;
		case "grapes":
			forecasts = getForecast.getGrapesForecast(time, hour);
			break;
		case "china":
			forecasts = getForecast.getChinaForecast(time, hour);
			break;
		case "中央指导":
			forecasts = getForecast.getSEVPForecast(time, hour);
			break;
		case "集成":
			forecasts = getForecast.getIntegratedForecast(time, hour);
			return forecasts;
		default:
			return null;
		}

		if (forecasts == null) {
			return null;
		}
		for (int i = 0; i < forecasts.size(); i++) {
			if (forecasts.get(i).getMaxTemp() == null || forecasts.get(i).getMinTemp() == null) {
				forecasts.remove(i);
				i--;
			} else {
				forecasts.set(i, ForecastHelper.correctForecast(forecasts.get(i)));
			}
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
			hibernateTemplate.setMaxResults(20);
			List<?> forecast2 = hibernateTemplate.find(hql, params);
			if (!forecast2.isEmpty()) {
				hibernateTemplate.deleteAll(forecast2);
			}
			hibernateTemplate.save(forecast);
		}
		hibernateTemplate.flush();

	}

	@Override
	public List<Forecast> getLastForecast(List<Station> stations) {

		List<Forecast> forecasts = new ArrayList<Forecast>();
		String hql = "from Forecast f where f.station=? and f.age=24 order by f.beginTime DESC,id.hour DESC ";
		Object[] params = new Object[1];
		hibernateTemplate.setMaxResults(1);
		for (Station station : stations) {
			params[0] = station;
			List<?> list = hibernateTemplate.find(hql, params);
			if (list.size() < 1) {
				continue;
			}
			forecasts.add((Forecast) list.get(0));
		}
		return forecasts;
	}

	@Override
	public Forecast getForecast(Station station, Date time, int hour, String type, int age) {
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");

		String hql = "from Forecast f where f.beginTime=? and f.hour=? and f.forecastType=? and f.age=? and f.station=? order by f.id DESC";
		Object[] params = new Object[5];
		try {
			params[0] = dateFormat3.parse(dateFormat3.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		params[1] = hour;
		params[2] = type;
		params[3] = age;
		params[4] = station;
		hibernateTemplate.setMaxResults(1);
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.isEmpty()) {
			return null;
		}
		Collections.reverse(list);
		return (Forecast) list.get(0);
	}

	@Override
	public List<Forecast> getforecast(Station station, Date time, int hour, int age) {
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");

		String hql = "from Forecast f where f.beginTime=? and f.hour=? and f.age=? and f.station=? order by f.forecastType DESC";
		Object[] params = new Object[4];
		try {
			params[0] = dateFormat3.parse(dateFormat3.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		params[1] = hour;
		params[2] = age;
		params[3] = station;
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.isEmpty()) {
			return null;
		}
		Collections.reverse(list);
		return (List<Forecast>) list;
	}

	@Override
	public List<Forecast> getForecast(Station station, Date time, int hour) {
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");

		String hql = "from Forecast f where f.beginTime=? and f.hour=? and f.station=? order by f.forecastType,age";
		Object[] params = new Object[3];
		try {
			params[0] = dateFormat3.parse(dateFormat3.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		params[1] = hour;
		params[2] = station;
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.isEmpty()) {
			return null;
		}

		return (List<Forecast>) list;
	}

	@Override
	public List<Forecast> getForecast(Date time, int hour, String type) {
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");

		String hql = "from Forecast f where f.beginTime=? and f.hour=? and f.forecastType=? and f.age=24";
		Object[] params = new Object[3];
		try {
			params[0] = dateFormat3.parse(dateFormat3.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		params[1] = hour;
		params[2] = type;
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.isEmpty()) {
			return null;
		}

		return (List<Forecast>) list;
	}

	@Override
	public List<Forecast> getForecast(Station station, Date time, int hour, String type) {
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");

		String hql = "from Forecast f where f.beginTime=? and f.hour=? and f.station=? and f.forecastType=? order by f.age";
		Object[] params = new Object[4];
		try {
			params[0] = dateFormat3.parse(dateFormat3.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		params[1] = hour;
		params[2] = station;
		params[3] = type;
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.isEmpty()) {
			return null;
		}

		return (List<Forecast>) list;
	}

}
