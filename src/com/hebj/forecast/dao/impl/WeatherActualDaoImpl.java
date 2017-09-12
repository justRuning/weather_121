package com.hebj.forecast.dao.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.util.GetWeatherDataFromString;
import com.hebj.forecast.util.ReadDataFromTxt;

import jcifs.smb.SmbFile;

@Repository
public class WeatherActualDaoImpl implements WeatherActualDao {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public List<WeatherActual> readWeather(Date time) throws UnsupportedEncodingException, IOException {

		List<WeatherActual> weatherActuals = new ArrayList<WeatherActual>();
		List<Station> stations = getStations();
		if (stations.size() < 1) {
			return null;
		}
		WeatherActual weatherActual = null;

		String line = null;
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(time);
		cal.setTime(time);
		cal.add(Calendar.HOUR_OF_DAY, -8);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHH");
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		for (Station station : stations) {

			String filePath = "smb://172.18.112.10/dqfw/raw/awstest/" + dateFormat.format(cal.getTime()) + "/"
					+ "Z_SURF_I_" + station.getStationId() + "_" + dateFormat2.format(cal.getTime())
					+ "0000_O_AWS_FTM.TXT";
			SmbFile smbFile;
			smbFile = new SmbFile(filePath);
			if (smbFile.exists()) {
				line = ReadDataFromTxt.readData(smbFile);
			}

			if (line == null) {
				return null;
			}
			weatherActual = GetWeatherDataFromString.getData(line);
			if (weatherActual != null) {
				try {
					weatherActual.setTime(dateFormat3.parse(dateFormat3.format(time)));
					weatherActual.setHour(cal2.get(Calendar.HOUR_OF_DAY));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				weatherActual.setStationID(station.getStationId());
				weatherActual.setStation(station);
				weatherActual.setName(station.getStationName());
				weatherActuals.add(weatherActual);
			}
		}
		return weatherActuals;
	}

	@Override
	public void save(List<WeatherActual> weatherActuals) {
		for (WeatherActual weatherActual : weatherActuals) {
			String hql = "from WeatherActual w where w.name = ? and w.time = ? and w.hour = ?";
			Object[] params = new Object[3];
			params[0] = weatherActual.getName();
			params[1] = weatherActual.getTime();
			params[2] = weatherActual.getHour();
			List<?> weather = hibernateTemplate.find(hql, params);
			if (!weather.isEmpty()) {
				hibernateTemplate.deleteAll(weather);
			}
			hibernateTemplate.save(weatherActual);
			hibernateTemplate.flush();
		}
	}

	@Override
	public List<Station> getStations() {
		hibernateTemplate.setMaxResults(20);
		return (List<Station>) hibernateTemplate.find("from Station s where s.readData = ?", true);
	}

	@Override
	public List<WeatherActual> getWeather(String name) {
		String hql = "from WeatherActual w where w.name=?  order by w.id DESC";
		Object[] params = new Object[1];
		params[0] = name;
		// hibernateTemplate.setMaxResults(12);
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.size() < 1) {
			return null;
		}
		Collections.reverse(list);
		return (List<WeatherActual>) list;
	}

	@Override
	public List<WeatherActual> getWeather(Date time, int hour) {

		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from WeatherActual w where w.time=? and w.hour=? order by w.id DESC";
		Object[] params = new Object[2];
		try {
			params[0] = dateFormat3.parse(dateFormat3.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		params[1] = hour;
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.size() < 1) {
			return null;
		}
		Collections.reverse(list);
		return (List<WeatherActual>) list;
	}

	@Override
	public WeatherActual getWeather(Station station, Date time, int hour) {
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from WeatherActual w where w.station=? and w.time=? and w.hour=? ";
		Object[] params = new Object[3];
		params[0] = station;
		try {
			params[1] = dateFormat3.parse(dateFormat3.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		params[2] = hour;
		hibernateTemplate.setMaxResults(20);
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.size() < 1) {
			return null;
		}
		Collections.reverse(list);
		return (WeatherActual) list.get(0);
	}

	@Override
	public List<WeatherActual> getWeather(Station station, Date time, int hour, int count) {
		DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from WeatherActual w where w.station=? and w.time <= ? and w.hour=?";
		Object[] params = new Object[3];
		params[0] = station;
		try {
			params[1] = dateFormat3.parse(dateFormat3.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		params[2] = hour;
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.size() < 1) {
			return null;
		}
		Collections.reverse(list);
		return (List<WeatherActual>) list;
	}

	@Override
	public List<WeatherActual> getLastWeather() {
		List<Station> stations = getStations();
		List<WeatherActual> weatherActuals = new ArrayList<>();

		for (Station station : stations) {
			Object[] params = new Object[1];
			params[0] = station;
			String hql = "from WeatherActual w where w.station=? order by w.time desc,w.hour desc";
			hibernateTemplate.setMaxResults(1);
			List<WeatherActual> list = (List<WeatherActual>) hibernateTemplate.find(hql, params);
			weatherActuals.add(list.get(0));

		}
		hibernateTemplate.setMaxResults(50);
		return weatherActuals;

	}

	@Override
	public HashMap<String, ArrayList<WeatherActual>> getLast24Weather() {
		List<Station> stations = getStations();
		Map<String, ArrayList<WeatherActual>> map = new HashMap<String, ArrayList<WeatherActual>>();

		for (Station station : stations) {
			Object[] params = new Object[1];
			params[0] = station;
			String hql = "from WeatherActual w where w.station=? order by w.time desc,w.hour desc";
			hibernateTemplate.setMaxResults(24);
			List<WeatherActual> list = (List<WeatherActual>) hibernateTemplate.find(hql, params);
			Collections.reverse(list);
			map.put(station.getStationName(), (ArrayList<WeatherActual>) list);

		}
		hibernateTemplate.setMaxResults(50);
		return (HashMap<String, ArrayList<WeatherActual>>) map;

	}
}
