package com.hebj.forecast.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;

public class ReadForecastFromHTML {

	/**
	 * 通过html解析预报数据
	 * 
	 * @throws IOException
	 */
	public static List<Forecast> readAmeForecast(String lines, Station station, Date time, int hour) {

		List<Forecast> forecasts = new ArrayList<Forecast>();
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		if (lines == null) {
			return null;
		}

		String[] strings = null;
		// lowTem
		Pattern pattern = Pattern.compile("\"loTmpC\":.?\\d+");
		Matcher matcher = pattern.matcher(lines);
		int i = 0;
		while (matcher.find()) {
			forecasts.add(new Forecast());
			try {
				forecasts.get(i).setBeginTime(dateFormat2.parse(dateFormat2.format(time)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			strings = matcher.group().split(":");
			forecasts.get(i).setLowTem(strings[1]);
			forecasts.get(i).setAge((i + 1) * 24);
			forecasts.get(i).setStation(station);
			forecasts.get(i).setForecastType("American");
			forecasts.get(i).setHour(hour);
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"hiTmpC\":.?\\d+");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setHighTem(strings[1]);
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"pOP12\":\\d+");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setRainDay(strings[1]);
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"pOP12_24\":\\d+");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setRainNight(strings[1]);
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"wDirAsc12\":\"\\w+\"");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setWindDirectionDay(strings[1].substring(1, strings[1].length() - 1));
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"wDirAsc12_24\":\"\\w+\"");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setWindDirectionNight(strings[1].substring(1, strings[1].length() - 1));
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"wSpdK12\":\\d+");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setWindVelocityDay(strings[1]);
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"wSpdK12_24\":\\d+");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setWindVelocityNight(strings[1]);
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"rH12\":\\d+");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setRHDay(strings[1]);
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"rH12_24\":\\d+");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setRHNight(strings[1]);
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"snsblWx12\":\"\\w+\\s?\\w+\"");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setSkyDay(strings[1].substring(1, strings[1].length() - 1));
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"snsblWx12_24\":\"\\w+\\s?\\w+\"");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setSkyNight(strings[1].substring(1, strings[1].length() - 1));
			i++;
		}

		return forecasts.subList(0, 7);
	}

	/**
	 * 解析html获取天气在线7天预报
	 * 
	 * @param lines
	 * @param station
	 * @param time
	 * @param hour
	 * @return
	 */
	public static List<Forecast> readT7OnlineForecast(String lines, Station station, Date time, int hour) {

		List<Forecast> forecasts = new ArrayList<Forecast>();
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		if (lines == null || lines.length() < 5000) {
			return null;
		}

		Document document = Jsoup.parse(lines);
		Elements elements = document.getElementsByTag("td");
		// 高温
		List<Element> elements2 = elements.subList(20, 27);
		for (int i = 0; i < elements2.size(); i++) {
			forecasts.add(new Forecast());
			forecasts.get(i).setAge((i + 1) * 24);
			forecasts.get(i).setHour(hour);
			forecasts.get(i).setStation(station);
			forecasts.get(i).setStation(station);
			try {
				forecasts.get(i).setBeginTime(dateFormat2.parse(dateFormat2.format(time)));
			} catch (ParseException e) {
			}
			String string = elements2.get(i).text();
			forecasts.get(i).setHighTem(string.substring(0, string.length() - 2));
		}

		// 低温
		elements2 = elements.subList(28, 35);
		for (int i = 0; i < elements2.size(); i++) {
			String string = elements2.get(i).text();
			forecasts.get(i).setLowTem(string.substring(0, string.length() - 2));
		}

		// 白天天气
		elements2 = elements.subList(44, 51);
		for (int i = 0; i < elements2.size(); i++) {
			String string = elements2.get(i).getElementsByTag("img").get(0).attr("title").trim();
			forecasts.get(i).setSkyDay(string);
		}

		// 夜间天气
		elements2 = elements.subList(52, 59);
		for (int i = 0; i < elements2.size(); i++) {
			String string = elements2.get(i).getElementsByTag("img").get(0).attr("title").trim();
			forecasts.get(i).setSkyNight(string);
		}

		// 风向风速
		elements2 = elements.subList(68, 75);
		for (int i = 0; i < elements2.size(); i++) {
			String string = elements2.get(i).text();
			String[] strings = string.split(" ");
			forecasts.get(i).setWindDirectionDay(strings[0]);
			forecasts.get(i).setWindVelocityDay(strings[1]);
		}

		return forecasts;
	}
}
