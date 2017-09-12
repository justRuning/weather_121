package com.hebj.forecast.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.service.ForecastService;
import com.hebj.forecast.util.ForecastHelper;

@Service
public class ForecastServiceImpl implements ForecastService {

	@Autowired
	ForecastDao forecastDao;
	@Autowired
	StationDao station;
	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public String readForecast(Date time, int second, String forecastType)
			throws UnsupportedEncodingException, IOException, ParseException {
		List<Forecast> forecasts;
		forecasts = forecastDao.readForecast(time, second, forecastType);
		if (forecasts != null) {
			forecastDao.save(forecasts);
			return "入库完成!";
		}
		return null;
	}

	@Override
	public List<Forecast> getLastForecast() {

		List<Station> stations = station.getPreparedStations();

		return forecastDao.getLastForecast(stations);
	}

	@Override
	public List<Forecast> getLastForecast(Station station, Date time, int hour, int age) {
		List<Forecast> forecasts = (List<Forecast>) forecastDao.getforecast(station, time, hour, age);

		for (int i = 0; i < forecasts.size(); i++) {
			if (forecasts.get(i).getForecastType().equals("Local")) {
				forecasts.remove(i);
				i--;
			}
		}
		return forecasts;
	}

	/**
	 * 制作语音预报
	 */
	@Override
	public void makeYYmsg() {
		List<Forecast> forecasts = getLastForecast();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(forecasts.get(0).getBeginTime());
		int h = forecasts.get(0).getHour();
		String hour = "";
		if (h <= 8) {
			hour = "早上";
		} else if (h <= 12) {
			hour = "上午";
		} else if (h <= 15) {
			hour = "中午";
		} else if (h <= 17) {
			hour = "下午";
		} else if (h <= 21) {
			hour = "晚上";
		} else {
			hour = "凌晨";
		}
		Map<String, String> forecasts2 = ForecastHelper.getYYMsg(forecasts, hour);
		for (Entry<String, String> entry : forecasts2.entrySet()) {
			ForecastHelper.savaYYMsg(entry.getKey(), entry.getValue());
		}

	}

	/**
	 * 制作预报短信
	 */
	@Override
	public void makeForecastMsg() {
		List<Forecast> forecasts = getLastForecast();

		Map<String, String> forecasts2 = ForecastHelper.makeForecastMsg(forecasts);
		for (Entry<String, String> entry : forecasts2.entrySet()) {
			ForecastHelper.savaMsg(entry.getKey(), entry.getValue());
		}

	}

	@Override
	public Forecast getForecast(Station station, Date time, int hour, int age, String type) {
		Forecast forecasts = forecastDao.getForecast(station, time, hour, type, age);
		if (forecasts == null) {
			return null;
		}
		return forecasts;
	}

	@Override
	public List<Forecast> getLastForecast(Station station, Date time, int hour) {
		List<Forecast> forecasts = (List<Forecast>) forecastDao.getForecast(station, time, hour);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int second = calendar.get(Calendar.HOUR_OF_DAY);
		if (second > 8 && hour == 8) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		} else if (second > 8 && hour == 20) {

		} else {
			calendar.add(Calendar.DAY_OF_MONTH, -2);
		}

		String[] types = { "EC", "T639", "American", "T7Online", "Grapes", "中央指导", "中国天气网", "集成" };
		for (int i = 0; i < 7; i++) {

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			for (String string : types) {
				Forecast forecasts2 = forecastDao.getForecast(station, calendar.getTime(), hour, string, 24);
				if (forecasts2 == null) {
					Forecast forecast = new Forecast();
					forecast.setForecastType(string);
					forecasts.add(0, forecast);
					continue;
				}
				forecasts.add(0, forecasts2);
			}

		}

		return forecasts;
	}

	@Override
	public TreeMap<String, List<Forecast>> getEveryTypeForecasts(Date time, int hour) {
		TreeMap<String, List<Forecast>> everyTypeForecasts = new TreeMap<>();
		String[] types = { "EC", "T639", "American", "T7Online", "中国天气网", "Grapes", "中央指导", "集成" };

		for (String type : types) {
			everyTypeForecasts.put(type, forecastDao.getForecast(time, hour, type));
		}

		return everyTypeForecasts;
	}

	@Override
	public TreeMap<String, List<Forecast>> getEveryTypeForecasts(Station station, Date time, int hour) {
		TreeMap<String, List<Forecast>> everyTypeForecasts = new TreeMap<>();
		String[] types = { "EC", "T639", "American", "T7Online", "中国天气网", "Grapes", "中央指导", "集成" };

		for (String type : types) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			List<Forecast> forecasts = new ArrayList<Forecast>();
			List<Forecast> fs = forecastDao.getForecast(station, time, hour, type);
			if (fs != null) {
				forecasts.addAll(fs);
			}
			Forecast f = new Forecast();
			forecasts.add(0, f);
			forecasts.add(0, f);

			for (int i = 0; i < 7; i++) {
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				Forecast forecast = new Forecast();
				forecast = ((Forecast) forecastDao.getForecast(station, calendar.getTime(), hour, type, 24));
				forecasts.add(0, forecast);

			}

			everyTypeForecasts.put(type, forecasts);
		}

		return everyTypeForecasts;
	}

	@Override
	public void suppleForecast(Date time, int hour, String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public LinkedHashMap<String, String> getYYForecast() {

		LinkedHashMap<String, String> forecasts = new LinkedHashMap<>();
		String[] stations = { "临河", "磴口", "五原", "杭锦后旗", "前旗", "中旗", "后旗" };

		for (String string : stations) {

			forecasts.put(string, ForecastHelper.readYYMsg(string));

		}
		return forecasts;
	}

	@Override
	public LinkedHashMap<String, String> getForecastMsg() {

		List<Forecast> forecasts2 = getLastForecast();

		Map<String, String> forecasts = ForecastHelper.makeForecastMsg(forecasts2);

		return (LinkedHashMap<String, String>) forecasts;
	}

	@Override
	public List<Forecast> getWeChatForecast() {
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();

		String hql = "from Forecast f where f.beginTime=? order by f.hour desc, f.age, f.station.id";
		Object[] params = new Object[1];
		try {
			params[0] = dateFormat3.parse(dateFormat3.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		hibernateTemplate.setMaxResults(63);
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.isEmpty()) {
			return null;
		}

		Iterator<?> f = list.iterator();
		while (f.hasNext()) {
			Forecast forecast = (Forecast) f.next();
			if (forecast.getStation().getStationName().equals("海力素")
					|| forecast.getStation().getStationName().equals("大佘太")) {
				f.remove();
			}
		}

		return (List<Forecast>) list;
	}

}
