package com.hebj.forecast.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.Forecast;

public interface GetForecast {

	
	List<Forecast> getIntegratedForecast(Date time,int hour);
	List<Forecast> getLocalForecast(Date time, int hour) throws UnsupportedEncodingException, IOException;
	
	List<Forecast> getLocalForecast2(Date time, int hour) ;

	List<Forecast> getT639Forecast(Date time, int hour) throws ParseException;

	List<Forecast> getEcForecast(Date time, int hour) throws ParseException;

	List<Forecast> getAmericanForecast(Date time, int hour)
			throws ParseException, UnsupportedEncodingException, IOException;

	List<Forecast> getT7OnlineForecast(Date time, int hour) throws UnsupportedEncodingException, IOException;

	List<Forecast> getGrapesForecast(Date time, int hour) throws ParseException;

	List<Forecast> getChinaForecast(Date time, int hour)
			throws UnsupportedEncodingException, IOException, ParseException;

	List<Forecast> getSEVPForecast(Date time, int hour) throws UnsupportedEncodingException, IOException;

}
