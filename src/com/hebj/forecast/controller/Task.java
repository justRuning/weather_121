package com.hebj.forecast.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.SimpleAliasRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.GetForecast;
import com.hebj.forecast.entity.AllMsg;
import com.hebj.forecast.entity.Fire;
import com.hebj.forecast.entity.ZaoJian;
import com.hebj.forecast.entity.lvyou;
import com.hebj.forecast.service.AllMsgService;
import com.hebj.forecast.service.FireService;
import com.hebj.forecast.service.ForecastService;
import com.hebj.forecast.service.WeatherActualService;
import com.hebj.forecast.service.ZaoJianService;
import com.hebj.forecast.util.ForecastHelper;
import com.hebj.forecast.util.GetIndexForecast;

import jdk.internal.dynalink.support.AbstractCallSiteDescriptor;

@Service
public class Task {

	private static Logger logger = Logger.getLogger(Task.class);
	@Autowired
	WeatherActualService weatherActualService;
	@Autowired
	ForecastService forecastService;
	@Autowired
	ZaoJianService zaoJianService;
	@Autowired
	FireService fireService;
	@Autowired
	AllMsgService allMsgService;

	@Autowired

	/**
	 * 整点实况定时读取
	 */
	@Scheduled(cron = "0 5,7,10,15 * * * ?")
	public void weahterAct() {

		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd-HH-mm");
		String msg;
		try {
			msg = weatherActualService.readWeather(calendar.getTime());
			weatherActualService.make121Weather();
			if (calendar.get(Calendar.HOUR_OF_DAY) == 7) {
				ZaoJian zaoJian = zaoJianService.makeZaoJian();
				zaoJianService.sava(zaoJian);
			}
			logger.info(msg);
		} catch (IOException | ParseException e) {
			logger.error("读取实况出错，时间：" + dateFormat.format(calendar.getTime()));
		}

	}

	/**
	 * 整点实况定时读取
	 */
	@Scheduled(cron = "0 15 16 * * ?")
	public void other() {

		Map<String, String> indexForecast = GetIndexForecast.getIndex();
		List<lvyou> lvyous = ForecastHelper.getLvyou();
		GetIndexForecast indexForecast2 = new GetIndexForecast();
		try {
			indexForecast2.writeIndexForecast(indexForecast);
			ForecastHelper.writeLvyouForecast(lvyous);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Scheduled(cron = "0 10 7 * * ?")
	public void zaoJian() {

		ZaoJian zaoJian = zaoJianService.makeZaoJian();
		zaoJianService.sava(zaoJian);

	}

	/**
	 * 天气预报入库
	 */
	@Scheduled(cron = "0 15,16,17,18,19,20,21,22,23,24,25,28,30,50 6,10,16 * * ?")
	public void localForecast() {

		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd-HH-mm");
		int hours = calendar.get(Calendar.HOUR_OF_DAY);

		if (hours == 6 || hours == 10 || hours == 16) {
			try {
				String result = forecastService.readForecast(calendar.getTime(), hours, "local");
				if (result != null) {
					forecastService.makeYYmsg();
					if (hours == 16) {
						forecastService.makeForecastMsg();

						AllMsg allMsg = allMsgService.makeAllMsg();
						allMsgService.sava(allMsg);

						Fire fire = fireService.makeFire();
						fireService.sava(fire);
					}
				}
			} catch (IOException | ParseException e) {
				logger.error("本地预报入库失败，时间：" + dateFormat.format(calendar.getTime()) + hours);
			}
		}
	}

}
