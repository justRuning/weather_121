package com.hebj.forecast.dao.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.GetForecast;
import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.util.ForecastHelper;
import com.hebj.forecast.util.Helper;
import com.hebj.forecast.util.ReadDataFromInternet;
import com.hebj.forecast.util.ReadDataFromTxt;
import com.hebj.forecast.util.ReadForecastFromHTML;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

@Repository
public class GetForecastImpl implements GetForecast {

	@Autowired
	StationDao stationDao;
	@Autowired
	HibernateTemplate hibernateTemplate;
	@Autowired
	WeatherActualDao weatherActualDao;
	@Autowired
	ForecastDao forecastDao;

	/**
	 * 读取中央指导预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public List<Forecast> getSEVPForecast(Date time, int hour) throws UnsupportedEncodingException, IOException {

		List<Station> stations = stationDao.getPreparedStations();
		List<Forecast> forecasts = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		String line = null;
		String filePath = null;
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String second = null;

		if (hour == 8) {
			second = "12";
		} else {
			second = "00";
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, "m3", "qaz123!@#");
		try {
			SmbFile dir = new SmbFile("smb://172.18.132.5/mproduct/refine/", auth);
			String fileName = "Z_SEVP_C_BABJ_\\d+_P_RFFC-SCMOC-EME-ACHN-L88-P9-" + dateFormat.format(time) + second
					+ "00-16812.TXT";
			if (dir.isDirectory() && dir.exists()) {
				SmbFile[] files = dir.listFiles();
				for (SmbFile smbFile : files) {
					String string = smbFile.getName();

					if (string.matches(fileName)) {
						filePath = smbFile.getURL().toString();
						smbFile = new SmbFile(filePath, auth);
						if (smbFile.exists()) {
							line = ReadDataFromTxt.readData(smbFile);
							String[] lines = line.split("\\s+");
						}
						break;
					}
				}
			}
		} catch (MalformedURLException e1) {
			return null;
		}

		if (line == null) {
			return null;
		}
		String[] lines = line.split("\\n");
		for (Station station : stations) {
			for (int i = 0; i < lines.length; i++) {
				if (lines[i].contains(station.getStationId())) {
					String ss = lines[i];
					i++;
					for (int j = 1; j < 8; j++) {
						String windDrec12 = null, windSpeed12 = null, windDrec24 = null, windSpeed24 = null,
								sky12 = null, sky24 = null, rain12 = null, rain24 = null, rh12 = null, rh24 = null;
						Forecast forecast = new Forecast();

						forecast.setForecastType("中央指导");
						forecast.setHour(hour);
						forecast.setStation(station);
						try {
							forecast.setBeginTime(dateFormat2.parse(dateFormat2.format(calendar.getTime())));
						} catch (ParseException e) {
							continue;
						}

						forecast.setAge(j * 24);

						while (i < lines.length) {
							String[] strings = lines[i].trim().split("\\s+");
							if (Integer.parseInt(strings[0]) == (j * 24 - 12)) {
								if (hour == 20) {
									if (Integer.parseInt(lines[i - 1].trim().split("\\s+")[0]) == (j * 24 - 18)) {
										strings = lines[i - 1].trim().split("\\s+");
									} else if (Integer
											.parseInt(lines[i - 2].trim().split("\\s+")[0]) == (j * 24 - 18)) {
										strings = lines[i - 2].trim().split("\\s+");
									}
								} else if (hour == 8) {
									if (Integer.parseInt(lines[i - 1].trim().split("\\s+")[0]) == (j * 24 + 6)) {
										strings = lines[i - 1].trim().split("\\s+");
									} else if (Integer.parseInt(lines[i - 2].trim().split("\\s+")[0]) == (j * 24 + 6)) {
										strings = lines[i - 2].trim().split("\\s+");
									}
								}
								rh12 = strings[2];
								windDrec12 = Helper.wind2String(strings[3]);
								windSpeed12 = Helper.ms2String(strings[4]);
								sky12 = strings[7];
								rain12 = Helper.rain2Rain(strings[16]);

							} else if (Integer.parseInt(strings[0]) == (j * 24)) {
								rh24 = strings[2];
								windDrec24 = Helper.wind2String(strings[3]);
								windSpeed24 = Helper.ms2String(strings[4]);
								sky24 = strings[7];
								rain24 = Helper.rain2Rain(strings[16]);

								forecast.setMaxTemp(strings[11]);
								forecast.setMinTemp(strings[12]);
								break;
							}
							i++;
						}
						if (hour == 8) {
							forecast.setSkyDay(Helper.cloud2Sky(sky24));
							forecast.setRHDay(rh24);
							forecast.setRainDay(rain24);
							forecast.setWindDirectionDay(windDrec24);
							forecast.setWindVelocityDay(windSpeed24);

							forecast.setSkyNight(Helper.cloud2Sky(sky12));
							forecast.setRHNight(rh12);
							forecast.setRainNight(rain12);
							forecast.setWindDirectionNight(windDrec12);
							forecast.setWindVelocityNight(windSpeed12);
						}

						else if (hour == 20) {
							forecast.setSkyDay(Helper.cloud2Sky(sky12));
							forecast.setRHDay(rh12);
							forecast.setRainDay(rain12);
							forecast.setWindDirectionDay(windDrec12);
							forecast.setWindVelocityDay(windSpeed12);

							forecast.setSkyNight(Helper.cloud2Sky(sky24));
							forecast.setRHNight(rh24);
							forecast.setRainNight(rain24);
							forecast.setWindDirectionNight(windDrec24);
							forecast.setWindVelocityNight(windSpeed24);
						}
						i++;
						forecasts.add(forecast);
					}
				}
			}
		}

		return forecasts;
	}

	/**
	 * 读取本地天气预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public List<Forecast> getLocalForecast(Date time, int hour) throws UnsupportedEncodingException, IOException {

		List<Forecast> forecasts = new ArrayList<Forecast>();
		String line = null;
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.HOUR_OF_DAY, -12);
		int second = hour == 16 ? 7 : 5;
		DateFormat dateFormat = new SimpleDateFormat("dd");
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", "administrator", "123");
		String filePath = "smb://172.18.132.45/forecast/LHDY" + dateFormat.format(time) + second + "W.ENN";
		SmbFile smbFile;
		try {
			smbFile = new SmbFile(filePath, auth);
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
		String[] lines = line.split("\r\n");
		String string;

		int aga = 24;
		Station station;
		for (int i = 0; i < lines.length; i++) {
			String[] strs = null;
			string = lines[i];
			while (string.contains(aga + "小时预报")) {
				i = i + 3;
				strs = lines[i].split("\\s+");
				while (strs.length > 4) {
					Forecast forecast = new Forecast();

					try {
						forecast.setBeginTime(dateFormat2.parse(dateFormat2.format(time)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					station = stationDao.getStationByName(strs[1].replace("乌", ""));
					if (station == null) {
						break;
					}
					forecast.setForecastType("Local");
					forecast.setAge(aga);
					forecast.setHour(hour);
					forecast.setStation(station);
					String[] skys = Helper.getSkys(strs[2]);
					forecast.setSkyDay(strs[2]);
					forecast.setSkyNight(hour == 8 ? skys[0] : skys[1]);
					String sky = forecast.getSkyDay();
					forecast.setRainDay(ForecastHelper.getMainSky(sky));
					sky = forecast.getSkyNight();
					forecast.setRainNight((sky.contains("雨") || sky.contains("雪")) ? sky : null);
					List<String> strings = Helper.wind2wind(strs[3]);
					forecast.setWindDirectionDay(strs[3]);
					forecast.setWindVelocityDay(strings.get(1));
					forecast.setMinTemp(String.valueOf(Integer.parseInt(strs[4])));
					forecast.setMaxTemp(String.valueOf(Integer.parseInt(strs[5])));

					forecasts.add(forecast);
					i++;
					if (i < lines.length) {
						strs = lines[i].split("\\s+");
					} else {
						break;
					}
				}
				aga = aga + 24;
				if (i < lines.length) {
					string = lines[i];
				}

			}
		}
		return forecasts;
	}

	/**
	 * 从中国天气网读取天气预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	public List<Forecast> getChinaForecast(Date time, int hour)
			throws UnsupportedEncodingException, IOException, ParseException {
		List<Forecast> forecasts = new ArrayList<Forecast>();
		List<Station> stations = stationDao.getPreparedStations();
		for (Station station : stations) {
			String url = ReadDataFromInternet.getChinaForecastURL(station.getStationId());

			String lines = ReadDataFromInternet.getHtmlContent(url, "UTF-8");
			if (lines == null) {
				continue;
			}
			forecasts.addAll(ReadForecastFromHTML.readChinaForecast(lines, station, time, hour));
		}
		return forecasts;
	}

	/**
	 * 从www.weather.com读取天气预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public List<Forecast> getAmericanForecast(Date time, int hour)
			throws ParseException, UnsupportedEncodingException, IOException {

		List<Forecast> forecasts = new ArrayList<Forecast>();
		List<Station> stations = stationDao.getPreparedStations();

		for (Station station : stations) {
			String url = ReadDataFromInternet.getAmericanURL(station.getStationId());

			String lines = ReadDataFromInternet.getHtmlContent(url, "UTF-8");
			if (lines == null) {
				continue;
			}
			forecasts.addAll(ReadForecastFromHTML.readAmeForecast(lines, station, time, hour));
		}
		return forecasts;
	}

	/**
	 * 读取天气在线网站预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public List<Forecast> getT7OnlineForecast(Date time, int hour) throws UnsupportedEncodingException, IOException {

		List<Forecast> forecasts = new ArrayList<Forecast>();
		List<Station> stations = stationDao.getPreparedStations();

		for (Station station : stations) {
			String url = ReadDataFromInternet.getT7OnlineURL(station.getStationId());
			String lines = ReadDataFromInternet.getHtmlContent(url, "GBK");
			if (lines == null) {
				continue;
			}

			forecasts.addAll(ReadForecastFromHTML.readT7OnlineForecast(lines, station, time, hour));
		}
		return forecasts;
	}

	/**
	 * 从micaps文件读取ec预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 * @throws ParseException
	 */
	public List<Forecast> getEcForecast(Date time, int hour) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Forecast> forecasts = new ArrayList<>();
		List<Station> stations = stationDao.getPreparedStations();
		String string1 = null;

		for (Station station : stations) {
			for (int i = 1; i < 8; i++) {

				Forecast forecast = new Forecast();
				forecast = new Forecast();
				forecast.setStation(station);
				forecast.setAge(i * 24);
				forecast.setForecastType("EC");
				forecast.setHour(hour);
				forecast.setBeginTime(dateFormat.parse(dateFormat.format(time)));

				// 最高气温
				string1 = "max(e.value*1.0) ";
				String hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				Object[] params = new Object[8];
				params[0] = station;
				params[1] = dateFormat.parse(dateFormat.format(time));
				params[2] = hour;
				params[3] = "EC_mx2t6";
				params[4] = i * 24 - 12 + 1;
				params[5] = i * 24 + 12;
				params[6] = "999";
				params[7] = "999";
				List<?> list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setMaxTemp(list.get(0).toString());
				}

				// 最低气温
				string1 = "min(e.value*1.0)";
				hql = "SELECT " + string1
						+ "FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "EC_mn2t6";
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setMinTemp(list.get(0).toString());
				}

				// 降水量
				string1 = "e.value";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "EC_TP";
				params[4] = hour == 8 ? i * 24 + 12 : i * 24;
				params[5] = hour == 8 ? i * 24 + 12 : i * 24;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRainDay(Helper.rain2Rain(list.get(0).toString()));
				}

				params[4] = hour == 20 ? i * 24 + 12 : i * 24;
				params[5] = hour == 20 ? i * 24 + 12 : i * 24;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRainNight(Helper.rain2Rain(list.get(0).toString()));
				}

				// 云量
				string1 = "e.value";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "EC_TCC";
				params[4] = hour == 8 ? i * 24 + 12 : i * 24;
				params[5] = hour == 8 ? i * 24 + 12 : i * 24;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setSkyDay(Helper.cloud2Sky(list.get(0).toString()));
				}

				params[4] = hour == 20 ? i * 24 + 12 : i * 24;
				params[5] = hour == 20 ? i * 24 + 12 : i * 24;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setSkyNight(Helper.cloud2Sky(list.get(0).toString()));
				}

				// 湿度
				string1 = "max(e.value*1.0)";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "EC_R";
				params[4] = hour == 8 ? i * 24 - 3 : i * 24 + 12 - 3;
				params[5] = hour == 8 ? i * 24 : i * 24 + 12;
				params[6] = "300";
				params[7] = "850";
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null && list.get(0) != null) {
					forecast.setRHNight(list.get(0).toString());
				}

				params[4] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				params[5] = hour == 8 ? i * 24 + 6 + 3 : i * 24 - 6 + 3;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRHDay(list.get(0).toString());
				}

				// 风向风速
				string1 = "e.value,e.value2";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "EC_10uv";
				params[4] = hour == 8 ? i * 24 : i * 24 + 12;
				params[5] = hour == 8 ? i * 24 : i * 24 + 12;
				params[6] = "999";
				params[7] = "999";
				List<Object[]> lists = (List<Object[]>) hibernateTemplate.find(hql, params);
				if (lists.size() > 0 && lists.get(0)[0] != null) {
					forecast.setWindDirectionNight(Helper.wind2String(lists.get(0)[1].toString()));
					forecast.setWindVelocityNight(Helper.ms2String(lists.get(0)[0].toString()));
				}

				params[4] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				params[5] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				lists = (List<Object[]>) hibernateTemplate.find(hql, params);
				if (lists.size() > 0 && list.get(0) != null) {
					forecast.setWindDirectionDay(Helper.wind2String(lists.get(0)[1].toString()));
					forecast.setWindVelocityDay(Helper.ms2String(lists.get(0)[0].toString()));
				}

				forecasts.add(forecast);
			}

		}
		return forecasts;
	}

	/**
	 * 读取T639预报
	 * 
	 * @param time
	 * @param hour
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public List<Forecast> getT639Forecast(Date time, int hour) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Forecast> forecasts = new ArrayList<>();
		List<Station> stations = stationDao.getPreparedStations();
		String string1 = null;

		for (Station station : stations) {
			for (int i = 1; i < 8; i++) {

				Forecast forecast = new Forecast();
				forecast = new Forecast();
				forecast.setStation(station);
				forecast.setAge(i * 24);
				forecast.setForecastType("T639");
				forecast.setHour(hour);
				forecast.setBeginTime(dateFormat.parse(dateFormat.format(time)));

				// 最高、最低气温
				string1 = "max(e.value*1.0),min(e.value*1.0) ";
				String hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				Object[] params = new Object[8];
				params[0] = station;
				params[1] = dateFormat.parse(dateFormat.format(time));
				params[2] = hour;
				params[3] = "T639_T2M";
				params[4] = i * 24 - 12 + 1;
				params[5] = i * 24 + 12;
				params[6] = "999";
				params[7] = "999";
				List<Object[]> lists = (List<Object[]>) hibernateTemplate.find(hql, params);
				if (lists.size() > 0 && lists.get(0)[0] != null) {
					forecast.setMaxTemp(lists.get(0)[0].toString());
					forecast.setMinTemp(lists.get(0)[1].toString());
				}

				// 降水量
				string1 = "e.value";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "T639_RAIN12";
				params[4] = hour == 8 ? i * 24 + 12 : i * 24;
				params[5] = hour == 8 ? i * 24 + 12 : i * 24;
				List<?> list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRainDay(Helper.rain2Rain(list.get(0).toString()));
				}

				params[4] = hour == 20 ? i * 24 : i * 24 + 12;
				params[5] = hour == 20 ? i * 24 : i * 24 + 12;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRainNight(Helper.rain2Rain(list.get(0).toString()));
				}

				// 湿度
				string1 = "max(e.value*1.0)";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "T639_RH";
				params[4] = hour == 8 ? i * 24 : i * 24 + 12;
				params[5] = hour == 8 ? i * 24 : i * 24 + 12;
				params[6] = "300";
				params[7] = "850";
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRHNight(list.get(0).toString());
					String sky = Helper.cloud2Sky(list.get(0).toString());
					forecast.setSkyNight(sky);
				}

				params[4] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				params[5] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRHDay(list.get(0).toString());
					String sky = Helper.cloud2Sky(list.get(0).toString());
					forecast.setSkyDay(sky);
				}

				// 风向风速
				string1 = "e.value,e.value2";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "T639_WIND10M";
				params[4] = hour == 8 ? i * 24 : i * 24 + 12;
				params[5] = hour == 8 ? i * 24 : i * 24 + 12;
				params[6] = "999";
				params[7] = "999";
				lists = (List<Object[]>) hibernateTemplate.find(hql, params);
				if (lists.size() > 0 && lists.get(0)[0] != null) {
					forecast.setWindDirectionNight(Helper.wind2String(lists.get(0)[0].toString()));
					forecast.setWindVelocityNight(Helper.ms2String(lists.get(0)[1].toString()));
				}

				params[4] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				params[5] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				lists = (List<Object[]>) hibernateTemplate.find(hql, params);
				if (lists.size() > 0 && lists.get(0)[0] != null) {
					forecast.setWindDirectionDay(Helper.wind2String(lists.get(0)[0].toString()));
					forecast.setWindVelocityDay(Helper.ms2String(lists.get(0)[1].toString()));
				}

				forecasts.add(forecast);
			}

		}
		return forecasts;
	}

	/**
	 * 读取Grapes天气预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 * @throws ParseException
	 */
	public List<Forecast> getGrapesForecast(Date time, int hour) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Forecast> forecasts = new ArrayList<>();
		List<Station> stations = stationDao.getPreparedStations();
		String string1 = null;

		for (Station station : stations) {
			for (int i = 1; i < 8; i++) {

				Forecast forecast = new Forecast();
				forecast = new Forecast();
				forecast.setStation(station);
				forecast.setAge(i * 24);
				forecast.setForecastType("Grapes");
				forecast.setHour(hour);
				forecast.setBeginTime(dateFormat.parse(dateFormat.format(time)));

				// 最高、最低气温
				string1 = "max(e.value*1.0),min(e.value*1.0) ";
				String hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				Object[] params = new Object[8];
				params[0] = station;
				params[1] = dateFormat.parse(dateFormat.format(time));
				params[2] = hour;
				params[3] = "Grapes_T2M";
				params[4] = i * 24 - 12;
				params[5] = i * 24 + 12;
				params[6] = "999";
				params[7] = "999";
				List<Object[]> lists = (List<Object[]>) hibernateTemplate.find(hql, params);
				if (lists.size() > 0 && lists.get(0)[0] != null) {
					forecast.setMaxTemp(lists.get(0)[0].toString());
					forecast.setMinTemp(lists.get(0)[1].toString());
				}

				// 降水量
				string1 = "e.value";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "Grapes_RAIN12";
				params[4] = hour == 8 ? i * 24 + 12 : i * 24;
				params[5] = hour == 8 ? i * 24 + 12 : i * 24;
				List<?> list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRainDay(Helper.rain2Rain(list.get(0).toString()));
				}

				params[4] = hour == 20 ? i * 24 + 12 : i * 24;
				params[5] = hour == 20 ? i * 24 + 12 : i * 24;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRainNight(Helper.rain2Rain(list.get(0).toString()));
				}

				// 湿度
				string1 = "max(e.value*1.0)";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "Grapes_RH";
				params[4] = hour == 8 ? i * 24 : i * 24 + 12;
				params[5] = hour == 8 ? i * 24 : i * 24 + 12;
				params[6] = "300";
				params[7] = "850";
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRHNight(list.get(0).toString());
				}

				params[4] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				params[5] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				list = hibernateTemplate.find(hql, params);
				if (list.size() > 0 && list.get(0) != null) {
					forecast.setRHDay(list.get(0).toString());
				}

				// 风向风速
				string1 = "e.value,e.value2";
				hql = "SELECT " + string1
						+ " FROM ModeForecast e where e.station=? and e.beginTime=? and e.hour=? and e.forecastType=? and e.age between ? and ? and e.high between ? and ?";
				params[3] = "Grapes_WIND10M";
				params[4] = hour == 8 ? i * 24 : i * 24 + 12;
				params[5] = hour == 8 ? i * 24 : i * 24 + 12;
				params[6] = "999";
				params[7] = "999";
				lists = (List<Object[]>) hibernateTemplate.find(hql, params);
				if (lists.size() > 0 && lists.get(0)[0] != null) {
					forecast.setWindDirectionNight(Helper.wind2String(lists.get(0)[0].toString()));
					forecast.setWindVelocityNight(Helper.ms2String(lists.get(0)[1].toString()));
				}

				params[4] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				params[5] = hour == 8 ? i * 24 + 6 : i * 24 - 6;
				lists = (List<Object[]>) hibernateTemplate.find(hql, params);
				if (lists.size() > 0 && lists.get(0)[0] != null) {
					forecast.setWindDirectionDay(Helper.wind2String(lists.get(0)[0].toString()));
					forecast.setWindVelocityDay(Helper.ms2String(lists.get(0)[1].toString()));
				}
				forecasts.add(forecast);
			}

		}
		return forecasts;
	}

	/**
	 * 计算集成预报
	 */
	@Override
	public List<Forecast> getIntegratedForecast(Date time, int hour) {

		List<Forecast> forecasts = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<double[]> listMax = new ArrayList<double[]>();
		List<double[]> listMaxRe = new ArrayList<double[]>();
		List<double[]> listMinRe = new ArrayList<double[]>();
		List<double[]> listMin = new ArrayList<double[]>();
		List<Double> maxResult = new ArrayList<>();
		List<Double> minResult = new ArrayList<Double>();
		List<Station> stations = stationDao.getPreparedStations();

		int wHour = 0;
		WeatherActual weatherActual;

		for (Station station : stations) {
			OLSMultipleLinearRegression olsMax = new OLSMultipleLinearRegression();
			OLSMultipleLinearRegression olsMin = new OLSMultipleLinearRegression();
			OLSMultipleLinearRegression olsMaxRe = new OLSMultipleLinearRegression();
			OLSMultipleLinearRegression olsMinRe = new OLSMultipleLinearRegression();

			Calendar weatherCalendar = Calendar.getInstance();
			Calendar forecastCalendar = Calendar.getInstance();
			weatherCalendar.setTime(time);
			forecastCalendar.setTime(time);
			if (hour == 8) {
				forecastCalendar.add(Calendar.DAY_OF_MONTH, -1);
			} else if (hour == 20) {
				forecastCalendar.add(Calendar.DAY_OF_MONTH, -2);
			} else {
				return null;
			}

			List<String> types = new ArrayList<>();
			types.add("EC");
			types.add("T639");
			types.add("American");
			types.add("T7Online");
			types.add("Grapes");
			types.add("中央指导");
			for (int i = 0; i < types.size(); i++) {
				List<Forecast> forecasts2 = null;
				forecasts2.add(forecastDao.getForecast(station, forecastCalendar.getTime(), hour, types.get(i), 24));
				if (forecasts2 == null) {
					types.remove(i);
					i--;
				}
			}
			for (int i = 0; i < 100; i++) {
				if (listMax.size() > 60) {
					break;
				}
				LinkedHashMap<String, List<Forecast>> everyForecasts = new LinkedHashMap<>();
				weatherCalendar.add(Calendar.DAY_OF_MONTH, -1);
				forecastCalendar.add(Calendar.DAY_OF_MONTH, -1);
				weatherActual = weatherActualDao.getWeather(station, weatherCalendar.getTime(), wHour);
				if (weatherActual == null) {
					continue;
				}
				double maxTem = Double.parseDouble(weatherActual.getMaxTemp24());
				double minTem = Double.parseDouble(weatherActual.getMinTemp24());

				for (String type : types) {
					List<Forecast> forecasts2 = null;
					forecasts2.add(forecastDao.getForecast(station, forecastCalendar.getTime(), hour, type, 24));
					if (forecasts2 != null) {
						everyForecasts.put(type, forecasts2);
					}
				}
				if (everyForecasts.values().size() < types.size()) {
					continue;
				}

				double[] valueMaxs = new double[types.size()];
				double[] valueMaxsRe = new double[types.size()];
				double[] valueMins = new double[types.size()];
				double[] valueMinsRe = new double[types.size()];

				for (int j = 0; j < types.size(); j++) {
					List<Forecast> forecasts2 = everyForecasts.get(types.get(j));
					valueMaxs[j] = Double.parseDouble(forecasts2.get(0).getMaxTemp());
					valueMins[j] = Double.parseDouble(forecasts2.get(0).getMinTemp());
					valueMaxsRe[j] = Double.parseDouble(forecasts2.get(0).getMaxTempRevised());
					valueMinsRe[j] = Double.parseDouble(forecasts2.get(0).getMinTempRevised());
				}

				listMax.add(valueMaxs);
				listMaxRe.add(valueMaxsRe);
				listMin.add(valueMins);
				listMinRe.add(valueMinsRe);
				maxResult.add(maxTem / 10);
				minResult.add(minTem / 10);

			}
			double[][] maxs = new double[listMax.size()][];
			double[] maxTemps = new double[maxResult.size()];
			double[][] mins = new double[listMin.size()][];
			double[] minTemps = new double[minResult.size()];
			double[][] maxRes = new double[listMax.size()][];
			double[][] minRes = new double[listMin.size()][];
			for (int i = 0; i < listMax.size(); i++) {
				maxs[i] = listMax.get(i);
				maxTemps[i] = maxResult.get(i);
				mins[i] = listMin.get(i);
				minTemps[i] = minResult.get(i);
				maxRes[i] = listMaxRe.get(i);
				minRes[i] = listMinRe.get(i);

			}

			olsMax.newSampleData(maxTemps, maxs);
			// olsMax.setNoIntercept(true);
			olsMin.newSampleData(minTemps, mins);
			// olsMin.setNoIntercept(true);
			olsMaxRe.newSampleData(maxTemps, maxRes);
			// olsMaxRe.setNoIntercept(true);
			olsMinRe.newSampleData(minTemps, minRes);
			// olsMinRe.setNoIntercept(true);
			double[] dateMax = olsMax.estimateRegressionParameters();
			double[] dateMin = olsMin.estimateRegressionParameters();
			double[] dateMaxRe = olsMaxRe.estimateRegressionParameters();
			double[] dateMinRe = olsMinRe.estimateRegressionParameters();
			// dateMax = olsMax.estimateResiduals();
			// dateMax = olsMax.estimateRegressionParametersStandardErrors();

			for (int k = 1; k < 6; k++) {
				LinkedHashMap<String, List<Forecast>> everyForecasts24 = new LinkedHashMap<>();
				for (int i = 0; i < types.size(); i++) {
					List<Forecast> forecasts2 = null;
					forecasts2.add(forecastDao.getForecast(station, time, hour, types.get(i), 24 * k));
					everyForecasts24.put(types.get(i), forecasts2);
				}

				Object[] fObjects = new Object[types.size()];
				for (int i = 0; i < types.size(); i++) {
					fObjects[i] = everyForecasts24.get(types.get(i));
				}

				for (int j = 0; j < fObjects.length; j++) {
					for (int i = 1; i < 5; i++) {
						if (fObjects[j] == null) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(time);
							calendar.add(Calendar.HOUR_OF_DAY, -i * 24);
							fObjects[j] = forecastDao.getForecast(station, calendar.getTime(), hour, types.get(j),
									(i + 1) * 24 + 24 * k);
						} else {
							break;
						}
					}
				}
				LinkedHashMap<String, List<Forecast>> Forecasts24 = new LinkedHashMap<>();

				for (int i = 0; i < fObjects.length; i++) {
					Forecasts24.put(types.get(i), (List<Forecast>) fObjects[i]);
				}

				double[] maxValues = new double[Forecasts24.values().size()];
				double[] minValues = new double[Forecasts24.values().size()];

				for (int i = 0; i < Forecasts24.values().size(); i++) {
					maxValues[i] = Double.parseDouble(Forecasts24.get(types.get(i)).get(0).getMaxTemp());
					minValues[i] = Double.parseDouble(Forecasts24.get(types.get(i)).get(0).getMinTemp());
				}

				double maxTemp = dateMax[0], minTemp = dateMin[0], maxTempRe = dateMaxRe[0], minTempRe = dateMinRe[0];

				for (int i = 0; i < maxValues.length; i++) {
					maxTemp = maxTemp + maxValues[i] * dateMax[i + 1];
					minTemp = minTemp + minValues[i] * dateMin[i + 1];
					maxTempRe = maxTempRe + maxValues[i] * dateMaxRe[i + 1];
					minTempRe = minTempRe + minValues[i] * dateMinRe[i + 1];
				}

				maxTemp = ((int) (maxTemp * 100)) / 100.0;
				maxTempRe = ((int) (maxTempRe * 100)) / 100.0;
				minTemp = ((int) (minTemp * 100)) / 100.0;
				minTempRe = ((int) (minTempRe * 100)) / 100.0;

				Forecast forecast = new Forecast();
				forecast.setMaxTemp(String.valueOf(maxTemp));
				forecast.setMaxTempRevised(String.valueOf(maxTempRe));
				forecast.setMinTemp(String.valueOf(minTemp));
				forecast.setMinTempRevised(String.valueOf(minTempRe));
				forecast.setStation(station);
				forecast.setAge(24 * k);
				forecast.setHour(hour);
				forecast.setForecastType("集成");
				try {
					forecast.setBeginTime(dateFormat.parse(dateFormat.format(time)));
				} catch (ParseException e) {
					continue;
				}

				forecasts.add(forecast);
			}

		}

		return forecasts;
	}

}
