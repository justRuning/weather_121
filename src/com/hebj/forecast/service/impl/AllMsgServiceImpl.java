package com.hebj.forecast.service.impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.AllMsgDao;
import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.AllMsg;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.service.AllMsgService;

@Service
public class AllMsgServiceImpl implements AllMsgService {

	@Autowired
	AllMsgDao AllMsgDao;
	@Autowired
	WeatherActualDao weatherActualDao;
	@Autowired
	ForecastDao forecastDao;

	@Override
	public AllMsg makeAllMsg() {

		List<Forecast> forecasts = forecastDao.getLastForecast(weatherActualDao.getStations());
		String date;
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("d日");
		date = dateFormat.format(calendar.getTime());
		String[] winds = forecasts.get(0).getWindDirectionDay().split("风");
		String wind = null;
		String wind2 = null;
		int[] tempTao = { 30, -30 };
		int[] tempBei = new int[2];
		if (winds[0].length() == 1) {
			winds[0] = "偏" + winds[0];
		}

		if (winds[1].split("转").clone()[0].contains("4-5")) {
			wind2 = "风力较大";
		} else if (winds[1].split("转").clone()[0].contains("3-4")) {
			wind2 = "风力稍大";
		} else if (winds[1].split("转").clone()[0].contains("2-3")) {
			wind2 = "风力不大";
		}

		wind = winds[1] + winds[0] + "风";

		String content = String.format("巴市气象台%s下午发布天气预报：今晚到明天全市%s，%s。临河%s，气温%s到%s度。", date,
				forecasts.get(0).getSkyDay(), wind2, wind, forecasts.get(0).getMinTemp(),
				forecasts.get(0).getMaxTemp());

		AllMsg AllMsg = new AllMsg();
		AllMsg.content = content;
		AllMsg.date = new Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));

		return AllMsg;
	}

	@Override
	public AllMsg getAllMsg(Date date) {
		return AllMsgDao.getAllMsg(date);
	}

	@Override
	public void sava(AllMsg AllMsg) {
		AllMsgDao.saveAllMsg(AllMsg);

	}

}
