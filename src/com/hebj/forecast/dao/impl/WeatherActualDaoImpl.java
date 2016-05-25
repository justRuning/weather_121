package com.hebj.forecast.dao.impl;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.util.GetWeatherDataFromString;
import com.hebj.forecast.util.ReadDataFromTxt;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

@Repository
public class WeatherActualDaoImpl implements WeatherActualDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public List<WeatherActual> readWeather(Date time) {

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
			try {
				smbFile = new SmbFile(filePath);
				if (smbFile.exists()) {
					line = ReadDataFromTxt.readData(smbFile);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SmbException e) {
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
			if (weather.size() < 1) {
				hibernateTemplate.save(weatherActual);
			} else {
				weatherActual.setId(((WeatherActual) weather.get(0)).getId());
				hibernateTemplate.clear();
				hibernateTemplate.saveOrUpdate(weatherActual);
			}
		}
	}

	@Override
	public List<Station> getStations() {
		return (List<Station>) hibernateTemplate.find("from Station s where s.readData = ?", true);
	}

	@Override
	public List<WeatherActual> getWeather(String name) {
		String hql = "from WeatherActual w where w.name=?  order by w.id DESC";
		Object[] params = new Object[1];
		params[0] = name;
		hibernateTemplate.setMaxResults(12);
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.size() < 1) {
			return null;
		}
		Collections.reverse(list);
		return (List<WeatherActual>) list;
	}
}
