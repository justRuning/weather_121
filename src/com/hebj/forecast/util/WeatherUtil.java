package com.hebj.forecast.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.JDOMException;

import com.hebj.forecast.entity.WeatherActual;

public class WeatherUtil {

	/**
	 * 通过天气实况得到121语音实况
	 * 
	 * @param weatherActuals
	 * @return
	 */
	public static Map<String, String> get121Weather(List<WeatherActual> weatherActuals) {

		Map<String, String> weathers = new LinkedHashMap<String, String>();
		for (WeatherActual weatherActual : weatherActuals) {
			String string = String.format(
					"听众朋友您好<br>、<br>下面为您提供<br>%s<br>今天<br>、<br>%s点<br>、<br>天气实况<br>、<br>%s风<br>%s级 <br>、<br>气温<br>、<br>%s℃<br>、<br>相对湿度<br>、<br>%s%%<br>、<br>欢迎您收听其它气象信息",
					weatherActual.getName(), weatherActual.getHour(),
					Helper.wind2String3(weatherActual.getWindDirection2min()),
					Helper.ms2String(String.valueOf(Double.parseDouble(weatherActual.getWindVelocity2min()) / 10)),
					Integer.parseInt(weatherActual.getTem()) / 10,
					Integer.parseInt(weatherActual.getRelativeHumidity()));
			if (string.contains("静风")) {
				string.replace("0级", "");
			}
			weathers.put(weatherActual.getName(), string);
		}

		return weathers;
	}

	public static String savaWeather(String name, String text) {
		switch (name) {
		case "临河":
			name = "710";
			break;
		case "前旗":
			name = "711";
			break;
		case "五原":
			name = "712";
			break;
		case "磴口":
			name = "713";
			text = text.replace("磴口", "磴(deng)口");
			break;
		case "杭锦后旗":
			name = "714";
			break;
		case "中旗":
			name = "715";
			break;
		case "后旗":
			name = "716";
			break;
		default:
			return "站点错误！!";
		}

		String result = "";
		try {
			writeTxt(name, text);
			result = "文件更新完成！";
		} catch (IOException e) {
			result = "文件更新失败！";
		}

		return result;

	}

	public static String readYYWeatehr(String name) {

		switch (name) {
		case "临河":
			name = "710";
			break;
		case "前旗":
			name = "711";
			break;
		case "五原":
			name = "712";
			break;
		case "磴口":
			name = "713";
			break;
		case "杭锦后旗":
			name = "714";
			break;
		case "中旗":
			name = "715";
			break;
		case "后旗":
			name = "716";
			break;
		default:
			return "站点错误！!";
		}

		String result = "";
		try {
			result = readTxt(name);
		} catch (IOException e) {
			result = null;
		}

		return result;
	}

	/**
	 * 写入文件
	 * 
	 * @param name
	 * @param text
	 * @throws IOException
	 */
	public static void writeTxt(String name, String text) throws IOException {

		String content = text.replace("<br>", "\r\n");

		File file = null;
		try {
			file = new File(ReadDataFromConfig.getValue("12121路径") + name + ".txt");

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// file = new File("/home/hebj/121.txt");
		StringBuilder result = new StringBuilder();

		if (!file.exists()) {
			file.createNewFile();
		}

		// FileWriter fw = new FileWriter(file.getAbsoluteFile());
		// BufferedWriter bw = new BufferedWriter(fw);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()), "GBK"));
		bw.write(content.trim());
		bw.close();

	}

	/**
	 * 读取txt文件
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static String readTxt(String name) throws IOException {

		File file = null;
		try {
			file = new File(ReadDataFromConfig.getValue("12121路径") + name + ".txt");

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// file = new File("/home/hebj/121.txt");
		StringBuilder result = new StringBuilder();

		if (!file.exists()) {
			file.createNewFile();
		}

		// FileReader fr = new FileReader(file.getAbsoluteFile());
		// BufferedReader br = new BufferedReader(fr);

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));

		String str = null;
		while ((str = br.readLine()) != null) {
			result.append(str);
			result.append("<br>");
		}
		br.close();
		return result.toString().trim();

	}
}
