package com.hebj.forecast.util;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.dao.impl.StationDaoImpl;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

@Component
public class GetForecast {

	@Autowired
	protected StationDao stationDao = new StationDaoImpl();

	List<Station> stations = stationDao.getPreparedStations();
	List<Forecast> forecasts = new ArrayList<Forecast>();

	/**
	 * 读取本地天气预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 */
	public List<Forecast> getLocalForecast(Date time, int hour) {

		String line = null;
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		int second = hour == 16 ? 7 : 5;
		DateFormat dateFormat = new SimpleDateFormat("dd");
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, "dell", "123");
		String filePath = "smb://172.18.132.159/cityforecast/UPLOAD/LHDY" + dateFormat.format(time) + second + "W.ENN";
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
					station = stationDao.getStationByName(strs[1]);
					if (station == null) {
						continue;
					}
					forecast.setForecastType("Local");
					forecast.setAge(aga);
					forecast.setHour(hour);
					forecast.setStation(station);
					forecast.setSkyDay(strs[2]);
					forecast.setSkyNight(strs[2]);
					String[] strings = Helper.wind2wind(strs[3]);
					forecast.setWindDirectionDay(strings[0]);
					forecast.setWindVelocityDay(strings[1]);
					forecast.setLowTem(strs[4]);
					forecast.setHighTem(strs[5]);

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
	 * 从www.weather.com读取天气预报
	 * 
	 * @param time
	 * @param hour
	 * @return
	 */
	public List<Forecast> getAmericanForecast(Date time, int hour) {

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
	 */
	public List<Forecast> getT7OnlineForecast(Date time, int hour) {

		for (Station station : stations) {
			String url = ReadDataFromInternet.getAmericanURL(station.getStationId());
			String lines = ReadDataFromInternet.getHtmlContent(url, "GBK");
			if (lines == null) {
				continue;
			}

			forecasts.addAll(ReadForecastFromHTML.readAmeForecast(lines, station, time, hour));
		}
		return forecasts;
	}

	public List<Forecast> getEcForecast(Date time, int hour) {

		String[] type = { "temp", "rh", "wind", "rain" };
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 10; i++) {
			for (String string : type) {
				String lines = ReadMicapsData.getMicapsData(string, time, hour, i * 12);
				for (Station station : stations) {
					String value = ReadMicapsData.getValueLikePhysic(lines, station);
					switch (string) {
					case "temp":
						if (i % 2 != 0) {
							Forecast forecast = new Forecast();
							forecast.setStation(station);
							forecast.setAge(i * 12);
							forecast.setForecastType("Ec");
							forecast.setHour(hour);
							try {
								forecast.setBeginTime(dateFormat2.parse(dateFormat2.format(time)));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							forecasts.add(forecast);
						}
						break;
					default:
						break;
					}
				}
			}
		}

		for (Station station : stations) {

		}
		return null;
	}
}
