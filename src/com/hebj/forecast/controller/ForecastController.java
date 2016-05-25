package com.hebj.forecast.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.service.ForecastService;
import com.hebj.forecast.service.WeatherActualService;

@Controller
@RequestMapping("/")
public class ForecastController {

	@Autowired
	ForecastService forecastService;

	@Autowired
	WeatherActualService weatherActualservice;
	
	String msg;

	@RequestMapping("/")
	public String index() {
		Date time = new Date();
		List<Forecast> forecasts = null;

		msg = forecastService.readForecast(time, 6, "local");
		forecastService.save(forecasts);

		msg = forecastService.readForecast(time, 6, "american");
		forecastService.save(forecasts);

		return msg;
	}

	@RequestMapping(value = "/forecast")
	public ModelAndView forecast(String name) {

		List<Forecast> forecasts = forecastService.getLastForecast(name);
		List<WeatherActual> weathers = weatherActualservice.getWeather(name);
		List<Integer> highTems = new ArrayList<Integer>();
		List<String> days = new ArrayList<String>();
		List<Integer> lowTems = new ArrayList<Integer>();
		List<String> tems = new ArrayList<String>();
		List<String> hours = new ArrayList<String>();

		if (forecasts.get(0).getHour() == 16) {
			highTems.add(0, null);
			highTems.remove(highTems.size() - 1);
		}
		for (WeatherActual weatherActual : weathers) {
			tems.add(String.valueOf(Double.parseDouble(weatherActual.getTem()) / 10));
			hours.add("'" + String.valueOf(weatherActual.getHour()) + "时" + "'");
		}
		ModelAndView model = new ModelAndView("forecast.jsp");
		model.addObject("forecasts", forecasts);
		model.addObject("weathers", weathers);
		model.addObject("highTems", highTems);
		model.addObject("lowTems", lowTems);
		model.addObject("tems", tems);
		model.addObject("hours", hours);
		model.addObject("days", days);
		return model;

	}

}
