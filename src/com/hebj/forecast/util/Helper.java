package com.hebj.forecast.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;

public class Helper {

	// 风向角度转方位
	public static String wind2String(String str) {
		if (str.contains("C")) {
			return "C";
		}
		double direction1 = Double.valueOf(str);
		String direction2 = null;
		if (337.5 < direction1 || direction1 <= 22.5) {
			direction2 = "N";
		} else if (direction1 <= 67.5) {
			direction2 = "NE";
		} else if (direction1 <= 112.5) {
			direction2 = "E";
		} else if (direction1 <= 157.5) {
			direction2 = "SE";
		} else if (direction1 <= 202.5) {
			direction2 = "S";
		} else if (direction1 <= 247.5) {
			direction2 = "SW";
		} else if (direction1 <= 292.5) {
			direction2 = "W";
		} else if (direction1 <= 337.5) {
			direction2 = "NW";
		}
		return direction2;

	}

	public static String getShanhouWind(String str) {
		if (str.contains("6")) {
			return "6到7";
		} else if (str.contains("5")) {
			return "5到6";
		} else if (str.contains("4")) {
			return "4到5";
		} else if (str.contains("3")) {
			return "3到4";
		} else if (str.contains("2")) {
			return "2到3";
		} else {
			return str;
		}
	}

	// 风向角度转中文方位
	public static String wind2String3(String str) {
		str = str.replace("风", "");
		str = wind2String(str);
		String string = null;
		switch (str) {
		case "E":
			string = "东";
			break;
		case "NE":
			string = "东北";
			break;
		case "N":
			string = "北";
			break;
		case "NW":
			string = "西北";
			break;
		case "W":
			string = "西";
			break;
		case "SW":
			string = "西南";
			break;
		case "S":
			string = "南";
			break;
		case "SE":
			string = "东南";
			break;
		case "C":
			string = "静";
			break;
		default:
			break;
		}
		return string;

	}

	// 中文方位转英文方位
	public static String wind2String2(String str) {
		str = str.replace("风", "");
		String string = null;
		switch (str) {
		case "东":
			string = "E";
			break;
		case "东北":
			string = "NE";
			break;
		case "北":
			string = "N";
			break;
		case "西北":
			string = "NW";
			break;
		case "西":
			string = "W";
			break;
		case "西南":
			string = "SW";
			break;
		case "南":
			string = "S";
			break;
		case "东南":
			string = "SE";
			break;
		case "静":
		case "微":
			string = "C";
			break;
		default:
			break;
		}
		return string;

	}

	// 风描述转数值
	public static List<String> wind2wind(String wind) {
		if (wind == null) {
			return null;
		}
		List<String> list = new ArrayList<>();
		wind = wind.replace("级", "");
		if (wind.contains("转")) {
			String[] liStrings = wind.split("转");
			if (liStrings.length > 2) {
				liStrings[0] = liStrings[0].replaceAll("风", "");
				liStrings[1] = liStrings[1].replaceAll("风", "");
				list.add(liStrings[1]);
				list.add(liStrings[3]);
				return list;
			} else {
				if (liStrings[1].contains("风")) {
					String[] strings = liStrings[1].split("风");
					list.add(strings[0]);
					list.add(strings[1]);
					return list;
				} else {
					String[] strings = liStrings[1].split("风");
					list.add(liStrings[0]);
					list.add(strings[0]);
					return list;
				}
			}
		}
		String[] strings = wind.split("风");

		return Arrays.asList(strings);
	}

	// m/s转等级
	public static String ms2String(String string) {
		double d = 0;
		try {
			d = Double.parseDouble(string);
		} catch (Exception e) {
			return null;
		}

		String result = null;

		if (d <= 0.2) {
			result = "0";
		} else if (d <= 1.6) {
			result = "1";
		} else if (d <= 3.4) {
			result = "2";
		} else if (d <= 5.5) {
			result = "3";
		}

		else if (d <= 8) {
			result = "4";
		} else if (d <= 10.8) {
			result = "5";
		} else if (d <= 13.9) {
			result = "6";
		} else if (d <= 17.2) {
			result = "7";
		} else if (d <= 20.8) {
			result = "8";
		} else if (d <= 24.5) {
			result = "9";
		} else if (d <= 28.5) {
			result = "10";
		} else if (d <= 32.6) {
			result = "11";
		} else if (d > 32.6) {
			result = "12";
		}
		return result;
	}

	public static String kmh2String(String string) {
		double d = 0;
		try {
			d = Double.parseDouble(string);
		} catch (Exception e) {
			return null;
		}
		return ms2String(String.valueOf(d * 5 / 18));
	}

	/**
	 * 中国天气天气现象分割
	 * 
	 * @param string
	 * @return
	 */
	public static String[] getSkys(String string) {
		String[] strings = new String[2];
		if (string.contains("转")) {
			strings = string.split("转");
		} else {
			strings[1] = strings[0] = string;
		}

		return strings;

	}

	public static String cloud2Sky(String cloud) {
		double dou;
		String sky = null;
		try {
			dou = Double.parseDouble(cloud);
		} catch (Exception e) {
			return null;
		}
		if (dou <= 40) {
			sky = "晴间少云";
		} else if (dou <= 60) {
			sky = "晴间多云";
		} else if (dou <= 70) {
			sky = "多云间晴";
		} else if (dou <= 80) {
			sky = "多云";
		} else if (dou <= 100) {
			sky = "阴";
		}
		return sky;
	}

	public static String rain2Rain(String string) {
		if (string.isEmpty()) {
			return null;
		}
		String rain = null;
		double dou = Double.parseDouble(string);
		if (dou == 0 || dou >= 999) {
			rain = null;
		} else if (dou < 0.1) {
			rain = "微量";
		} else if (dou < 5) {
			rain = "小雨";
		} else if (dou < 15) {
			rain = "中雨";
		} else if (dou < 30) {
			rain = "大雨";
		} else if (dou < 70) {
			rain = "暴雨";
		} else if (dou < 140) {
			rain = "大暴雨";
		} else if (dou >= 140) {
			rain = "特大暴雨";
		}
		return rain;
	}

	/**
	 * 获取日期的星期
	 * 
	 * @param date
	 * @return 星期一
	 */
	public static String getDayOfWeek(Date date) {

		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		return weekDays[week];

	}

	/**
	 * 获取日期的星期
	 * 
	 * @param date
	 * @return 星期一
	 */
	public static String getDayOfWeek2(Date date) {

		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		return weekDays[week];

	}

}
