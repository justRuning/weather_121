package com.hebj.forecast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.service.Test;
import com.hebj.forecast.util.WeatherUtil;

@Service
public class test implements Test {

	@Autowired
	StationDao stationDao;
	
	@Async
	public void test() {
		
		
	}
}
