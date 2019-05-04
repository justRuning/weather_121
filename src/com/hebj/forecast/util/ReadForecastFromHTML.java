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
	 * @throws ParseException
	 * 
	 * @throws IOException
	 */
	public static List<Forecast> readAmeForecast(String lines, Station station, Date time, int hour)
			throws ParseException {

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
			forecasts.get(i).setBeginTime(dateFormat2.parse(dateFormat2.format(time)));
			strings = matcher.group().split(":");
			forecasts.get(i).setMinTemp(strings[1]);
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
			forecasts.get(i).setMaxTemp(strings[1]);
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
			forecasts.get(i).setWindVelocityDay(Helper.kmh2String(strings[1]));
			i++;
		}

		i = 0;
		pattern = Pattern.compile("\"wSpdK12_24\":\\d+");
		matcher = pattern.matcher(lines);
		while (matcher.find()) {
			strings = matcher.group().split(":");
			forecasts.get(i).setWindVelocityNight(Helper.kmh2String(strings[1]));
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
	 * 解析html数据得到中国天气网单站预报数据
	 * 
	 * @param lines
	 * @param station
	 * @param time
	 * @param hour
	 * @return
	 * @throws ParseException
	 */
	public static List<Forecast> readChinaForecast(String lines, Station station, Date time, int hour)
			throws ParseException {
		List<Forecast> forecasts = new ArrayList<Forecast>();
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		if (lines == null || lines.length() < 5000) {
			return null;
		}

		Document document = Jsoup.parse(lines);
		Elements elements = document.getElementsByClass("t").addClass("clearfix");
		Elements lis = elements.select("li");

		for (int i = 0; i < lis.size(); i++) {
			Forecast forecast = new Forecast();
			forecast.setAge((i + 1) * 24);
			forecast.setBeginTime(dateFormat2.parse(dateFormat2.format(time)));
			forecast.setForecastType("中国天气网");
			forecast.setHour(hour);
			forecast.setStation(station);

			String value = lis.get(i).select("p[class=wea]").text();
			String[] values = Helper.getSkys(value);
			if (hour == 8) {

				forecast.setSkyDay(values[1]);
				if (values[1].contains("雨") || values[1].contains("雪")) {
					forecast.setRainDay(values[1]);
				}
				forecast.setSkyNight(values[0]);
				if (values[0].contains("雨") || values[0].contains("雪")) {
					forecast.setRainNight(values[0]);
				}
				if ((i + 1) < lis.size()) {
					forecast.setMaxTemp(lis.get(i + 1).select("span").text().replace("℃", "").trim());
				} else {
					forecast.setMaxTemp(null);
				}
			} else {
				forecast.setSkyDay(values[0]);
				if (values[0].contains("雨") || values[0].contains("雪")) {
					forecast.setRainDay(values[0]);
				}
				forecast.setSkyNight(values[1]);
				if (values[1].contains("雨") || values[1].contains("雪")) {
					forecast.setRainNight(values[1]);
				}
				forecast.setMaxTemp(lis.get(i).select("span").text().replace("℃", "").trim());
			}

			value = lis.get(i).select("i").text().split("\\s+")[0];
			forecast.setMinTemp(value.substring(0, value.length() - 1).replace("℃", "").trim());
			value = lis.get(i).select("span[title]").attr("title");
			value = Helper.wind2String2(value);
			forecast.setWindDirectionDay(value);
			forecast.setWindDirectionNight(value);
			value = lis.get(i).select("i").text().split("\\s+")[1];
			value = value.contains("微风") ? "1-2" : value.substring(0, value.length() - 1);
			forecast.setWindVelocityDay(value);
			forecast.setWindVelocityNight(value);
			forecasts.add(forecast);
		}

		return forecasts;
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
			forecasts.get(i).setForecastType("T7Online");
			String string = elements2.get(i).text();
			// forecasts.get(i).setMaxTemp(string.substring(0, string.length() -
			// 2));

			if (hour == 8) {
				if ((i + 1) < elements2.size()) {
					string = elements2.get(i + 1).text();
					forecasts.get(i).setMaxTemp(string.substring(0, string.length() - 2));
				} else {
					forecasts.get(i).setMaxTemp(null);
				}
			} else {
				forecasts.get(i).setMaxTemp(string.substring(0, string.length() - 2));
			}

			try {
				forecasts.get(i).setBeginTime(dateFormat2.parse(dateFormat2.format(time)));
			} catch (ParseException e) {
			}

		}

		// 低温
		elements2 = elements.subList(28, 35);
		for (int i = 0; i < elements2.size() - 1; i++) {
			String string = elements2.get(i + 1).text();
			forecasts.get(i).setMinTemp(string.substring(0, string.length() - 2));
			// if (hour == 8) {
			// if ((i + 1) < elements2.size()) {
			// string = elements2.get(i + 1).text();
			// forecasts.get(i).setMinTemp(string.substring(0, string.length() -
			// 2));
			// } else {
			// forecasts.get(i).setMinTemp(null);
			// }
			// } else {
			// forecasts.get(i).setMinTemp(string.substring(0, string.length() -
			// 2));
			// }
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
			forecasts.get(i).setWindDirectionDay(Helper.wind2String2(strings[0]));
			forecasts.get(i).setWindVelocityDay(strings[1]);
		}

		return forecasts;
	}
}
