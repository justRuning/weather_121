package com.hebj.forecast.service.impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.analysis.function.Abs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.dao.ZaoJianDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.entity.ZaoJian;
import com.hebj.forecast.service.ZaoJianService;

@Service
public class ZaoJianServiceImpl implements ZaoJianService {

	@Autowired
	ZaoJianDao zaoJianDao;
	@Autowired
	WeatherActualDao weatherActualDao;
	@Autowired
	ForecastDao forecastDao;

	@Override
	public ZaoJian makeZaoJian() {
		HashMap<String, ArrayList<WeatherActual>> weatherActuals = weatherActualDao.getLast24Weather();
		WeatherActual weatherActual = null;
		ArrayList<WeatherActual> weatherActuals2 = weatherActuals.get("临河");
		weatherActual = weatherActuals2.get(weatherActuals2.size() - 1);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(weatherActual.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		WeatherActual weatherActual2 = weatherActualDao
				.getWeather(weatherActual.getStation(), calendar.getTime(), weatherActual.getHour(), 1).get(0);
		String bijiao = "";
		double b = (Double.parseDouble(weatherActual.getTem()) - Double.parseDouble(weatherActual2.getTem())) / 10;
		if (Math.abs(b) <= 3) {
			bijiao = "与昨天接近";
		} else if (b > 3) {
			bijiao = "比昨天高" + Math.round(b) + "℃";
		} else if (b < -3) {
			bijiao = "比昨天低" + Math.abs(Math.round(b)) + "℃";
		}
		Forecast forecast = forecastDao.getForecast(weatherActual.getStation(), weatherActual.getTime(), 6, "local")
				.get(0);
		String wind = "";
		String wind2 = "";
		String date = "";
		String[] winds = forecast.getWindDirectionDay().split("风");
		if (winds[1].split("转").clone()[0].contains("4-5")) {
			wind2 = "风力较大";
		} else if (winds[1].split("转").clone()[0].contains("3-4")) {
			wind2 = "风力稍大";
		} else if (winds[1].split("转").clone()[0].contains("2-3")) {
			wind2 = "风力不大";
		}
		wind = winds[1].split("转")[0] + winds[0].split("转")[0] + "风";
		calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("d日H时");
		date = dateFormat.format(calendar.getTime());

		String content = String.format("【早间气象】目前临河气温%s℃，%s。今天白天全市%s，%s。临河%s，最高气温%s℃。巴市气象台%s发布",
				Math.round(Double.parseDouble(weatherActual.getTem()) / 10), bijiao, forecast.getSkyDay(), wind2,
				wind.replace("-", "到"), forecast.getMaxTemp(), date);

		ZaoJian zaoJian = new ZaoJian();
		zaoJian.content = content;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		zaoJian.date = new Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		return zaoJian;
	}

	@Override
	public ZaoJian getZaoJian(Date date) {
		return zaoJianDao.getZaoJian(date);
	}

	@Override
	public void sava(ZaoJian zaoJian) {
		zaoJianDao.saveZaoJian(zaoJian);

	}

}
