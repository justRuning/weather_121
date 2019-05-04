package com.hebj.forecast.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;

import jcifs.smb.SmbException;

public interface WeatherActualDao {

	List<WeatherActual> readWeather(Date time)
			throws MalformedURLException, SmbException, UnsupportedEncodingException, IOException;

	void save(List<WeatherActual> weatherActuals);

	List<Station> getStations();

	List<WeatherActual> getWeather(String name);

	List<WeatherActual> getWeather(Date time, int hour);

	WeatherActual getWeather(Station station, Date time, int hour);

	List<WeatherActual> getWeather(Station station, Date time, int hour, int count);

	List<WeatherActual> getLastWeather();

	public HashMap<String, ArrayList<WeatherActual>> getLast24Weather();
}
