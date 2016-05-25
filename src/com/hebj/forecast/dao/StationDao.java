package com.hebj.forecast.dao;

import java.util.List;

import com.hebj.forecast.entity.Station;

public interface StationDao {

	Station getStationByName(String stationName);
	
	List<Station> getPreparedStations();
}
