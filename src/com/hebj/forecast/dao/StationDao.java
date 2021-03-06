package com.hebj.forecast.dao;

import java.util.List;

import com.hebj.forecast.entity.Station;
import com.hebj.forecast.service.impl.test;

public interface StationDao {

	Station getStationByName(String stationName);
	
	Station getStationById(String stationId);
	
	List<Station> getPreparedStations();
	
	void test();
}
