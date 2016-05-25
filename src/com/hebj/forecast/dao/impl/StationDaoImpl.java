package com.hebj.forecast.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.Station;

@Component
public class StationDaoImpl implements StationDao {

	@Autowired
	HibernateTemplate HibernateTemplate;

	@Override
	public Station getStationByName(String stationName) {

		String hql = "from Station s where s.stationName=? ";
		Object[] params = new Object[1];
		params[0] = stationName;
		List<?> stations = HibernateTemplate.find(hql, params);
		if (stations.size() < 1) {
			return null;
		} else {
			return (Station) stations.get(0);
		}

	}

	@Override
	public List<Station> getPreparedStations() {

		String sql = "from Station s where s.readData = 1";
		List<?> stations = HibernateTemplate.find(sql);
		
		if (stations.size() < 1) {
			return null;
		} else {
			return (List<Station>) stations;
		}
	}

}
