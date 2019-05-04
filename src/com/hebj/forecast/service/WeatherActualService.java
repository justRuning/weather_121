package com.hebj.forecast.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;

import jcifs.smb.SmbException;

public interface WeatherActualService {

	String readWeather(Date time)
			throws MalformedURLException, SmbException, UnsupportedEncodingException, IOException, ParseException;

	void save(List<WeatherActual> weatherActuals);

	List<WeatherActual> getWeather(String name);

	List<WeatherActual> getLastWeather();

	List<WeatherActual> get5Weather(Station station, Date time, int hour);

	List<WeatherActual> getWeather(Station station, Date time, int hour, int count);

	void make121Weather();

	LinkedHashMap<String, String> getYYWeather();

	HashMap<String, ArrayList<WeatherActual>> getLast24Weather();
	
	

}
