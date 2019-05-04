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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.util.GetWeatherDataFromString;
import com.hebj.forecast.util.ReadDataFromTxt;
import com.hebj.forecast.util.WebsUtil;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

@Repository
public class WeatherActualDaoImpl implements WeatherActualDao {

	@Autowired
	HibernateTemplate hibernateTemplate;
	StationDao stationDao;

	@Override
	public List<WeatherActual> readWeather(Date time) throws UnsupportedEncodingException, IOException {

		List<WeatherActual> weatherActuals = new ArrayList<WeatherActual>();
		List<Station> stations = new ArrayList<Station>();
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
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", "comm", "comm");
		String params = "userId=BEHT_BFLH_hebj" + "&pwd=123123" + "&interfaceId=getSurfLatestTime"
				+ "&dataCode=SURF_CHN_MUL_HOR_R" + "&latestTime=1" + "&dataFormat=json";
		WebsUtil websUtil = new WebsUtil();
		String rstData = websUtil.getWsString("callAPI_to_serializedStr", params);
		JSONArray objects = new JSONArray();

		Object lastTime = null;
		try {
			JSONObject jsonObject = new JSONObject(rstData);
			JSONObject object = (JSONObject) jsonObject.getJSONArray("DS").get(0);
			lastTime = object.get("Datetime");

			params = "userId=BEHT_BFLH_hebj" + "&pwd=123123" + "&interfaceId=getSurfEleByTimeAndStaID"
					+ "&dataCode=SURF_CHN_MUL_HOR_R" + "&elements=SStation_Name,Datetime,Station_Id_C,PRS,TEM,TEM_Max,TEM_Max_OTime,TEM_Min,TEM_Min_OTime,RHU,PRE_1h,PRE,WIN_D_Avg_2mi,WIN_S_Avg_2mi,GST,VIS_HOR_1MI" + "&times=" + lastTime
					+ "&limitCnt=30" + "&staIds=53513,53419,53420，53231,53324,53336,53337,53348,53433" + "&dataFormat=json";
			rstData = websUtil.getWsString("callAPI_to_serializedStr", params);
			jsonObject = new JSONObject(rstData);
			objects = jsonObject.getJSONArray("DS");

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < objects.length(); i++) {
			try {
				String stationId = (String) objects.getJSONObject(i).get("Station_Id_C");
				Station station = stationDao.getStationById(stationId);
//				switch (object) {
//				case "C3640":
//					lvyous.get(0).nowTem = (String) objects.getJSONObject(i).get("TEM");
//					break;
//				case "C3502":
//					lvyous.get(1).nowTem = (String) objects.getJSONObject(i).get("TEM");
//					break;
//				case "C3523":
//					lvyous.get(2).nowTem = (String) objects.getJSONObject(i).get("TEM");
//					break;
//				case "C3680":
//					lvyous.get(3).nowTem = (String) objects.getJSONObject(i).get("TEM");
//					break;
//				case "C3521":
//					lvyous.get(4).nowTem = (String) objects.getJSONObject(i).get("TEM");
//					break;
//				case "C3588":
//					lvyous.get(5).nowTem = (String) objects.getJSONObject(i).get("TEM");
//					break;
//
//				default:
//					break;
//				}
//				object = null;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
//		for (Station station : stations) {
//
//			String filePath = "smb://172.18.112.10/dqfw/raw/awstest/" + dateFormat.format(cal.getTime()) + "/"
//					+ "Z_SURF_I_" + station.getStationId() + "_" + dateFormat2.format(cal.getTime())
//					+ "0000_O_AWS_FTM.TXT";
//			SmbFile smbFile;
//			smbFile = new SmbFile(filePath);
//			if (smbFile.exists()) {
//				line = ReadDataFromTxt.readData(smbFile);
//			}
////			String filePath = "W:\\awstest\" + dateFormat.format(cal.getTime()) + "\"
////					+ "Z_SURF_I_" + station.getStationId() + "_" + dateFormat2.format(cal.getTime())
////					+ "0000_O_AWS_FTM.TXT";
////			line = ReadDataFromTxt.readDataFromTxtByGBK(filePath);
//
//			if (line == null) {
//				continue;
//			}
//			weatherActual = GetWeatherDataFromString.getData(line);
//			if (weatherActual != null) {
//				try {
//					weatherActual.setTime(dateFormat3.parse(dateFormat3.format(time)));
//					weatherActual.setHour(cal2.get(Calendar.HOUR_OF_DAY));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				weatherActual.setStationID(station.getStationId());
//				weatherActual.setStation(station);
//				weatherActual.setName(station.getStationName());
//				weatherActuals.add(weatherActual);
//			}
//		}
//		return weatherActuals;
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
		return (List<Station>) hibernateTemplate.find("from Station s where 1=1");
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
