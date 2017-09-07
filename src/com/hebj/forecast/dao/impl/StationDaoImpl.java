package com.hebj.forecast.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.Station;

@Repository("StationDao")
public class StationDaoImpl implements StationDao {



	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	
	public void test() {
		hibernateTemplate.clear();
		System.out.println(hibernateTemplate.getSessionFactory().toString());
		System.out.println(hibernateTemplate.toString());
		System.out.println(new Date());
	}
	

	@Override
	public Station getStationByName(String stationName) {

		String hql = "from Station s where s.stationName=? ";
		Object[] params = new Object[1];
		params[0] = stationName;
		List<?> stations = hibernateTemplate.find(hql, params);
		if (stations.size() < 1) {
			return null;
		} else {
			return (Station) stations.get(0);
		}

	}

	@Override
	public List<Station> getPreparedStations() {

		String sql = "from Station s where s.readData = 1";
		hibernateTemplate.setMaxResults(20);
		List<?> stations = hibernateTemplate.find(sql);
		
		if (stations.size() < 1) {
			return null;
		} else {
			return (List<Station>) stations;
		}
	}

}
