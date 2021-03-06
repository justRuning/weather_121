package com.hebj.forecast.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.Field;
import com.sun.mail.handlers.text_html;
import com.sun.org.apache.regexp.internal.recompile;

public class GetIndexForecast {

	public static Map<String, String> getWeatherWeb() {

		String[] stations = { "临河", "磴口", "五原", "杭锦后旗", "乌拉特前旗", "乌拉特中旗", "乌拉特后旗" };
		Map<String, String> weathers = new HashMap<>();

		for (String string : stations) {

			String url = getUrl(string);
			String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36";
			Document doc;

			try {
				doc = Jsoup.connect(url).get();
				Elements links = doc.getElementsByClass("weather_shzs");
				String index = links.get(0).toString();
				links = doc.getElementsByClass("weather_zdsk");
				String weather = links.get(0).toString();

				weathers.put(string, index);

			} catch (IOException e) {
				return null;
			}
		}
		return weathers;

	}

	public static String getUrl(String station) {

		String url = "";

		switch (station) {
		case "临河":
			url = "http://www.weather.com.cn/weather1dn/101080801.shtml";
			break;
		case "磴口":
			url = "http://www.weather.com.cn/weather1dn/101080803.shtml";
			break;
		case "杭锦后旗":
			url = "http://www.weather.com.cn/weather1dn/101080810.shtml";
			break;
		case "五原":
			url = "http://www.weather.com.cn/weather1dn/101080802.shtml";
			break;
		case "乌拉特前旗":
			url = "http://www.weather.com.cn/weather1dn/101080804.shtml";
			break;
		case "乌拉特中旗":
			url = "http://www.weather.com.cn/weather1dn/101080806.shtml";
			break;
		case "乌拉特后旗":
			url = "http://www.weather.com.cn/weather1dn/101080807.shtml";
			break;
		case "海力素":
			url = "http://www.weather.com.cn/weather1dn/101080808.shtml";
			break;
		case "大佘太":
			url = "http://www.weather.com.cn/weather1dn/101080805.shtml";
			break;
		default:
			break;
		}

		return url;
	}

	/**
	 * 获取中国天气网指数预报 临河
	 * 
	 * @return
	 */
	public static Map<String, String> getIndex() {
		String url = "http://www.weather.com.cn/weather1d/101080801.shtml";
		String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36";
		Document doc;
		Map<String, String> indexForecast = new HashMap<>();

		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.getElementsByClass("hot");
			for (int i = 0; i < 6; i++) {
				StringBuffer content = new StringBuffer();
				Element element = links.get(i);
				if (element.toString().contains("a href")) {
					element = element.getElementsByTag("a").first();
				}
				Elements element2 = element.getElementsByTag("em");
				String[] txt = element2.get(element2.size() - 1).text().split("·");
				content.append(txt[txt.length - 1] + ":");
				element2 = links.get(i).getElementsByTag("span");
				content.append(element2.get(0).text().toString() + "\n");
				element2 = links.get(i).getElementsByTag("p");
				// content.append(element2.get(0).text().toString()+"\r");
				indexForecast.put(content.toString(), element2.get(0).text().toString());
			}

			return indexForecast;

		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 将指数预报写入word
	 * 
	 * @param indexForecast
	 * @throws Exception
	 */
	public void writeIndexForecast(Map<String, String> indexForecast) throws Exception {

		String paths = ReadDataFromConfig.getValue("产品路径");
		String templatePath = paths + "//指数预报//模板.doc";

		// String templatePath ="/home/hebj/Documents/模板.doc";

		InputStream is = new FileInputStream(templatePath);
		HWPFDocument doc = new HWPFDocument(is);
		Range range = doc.getRange();

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String time = df.format(calendar.getTime());

		range.replaceText("time", time);

		Set<String> keys = indexForecast.keySet();

		int i = 1;
		for (Entry<String, String> entry : indexForecast.entrySet()) {
			String[] keys1 = entry.getKey().split("·");
			range.replaceText("key" + i, keys1[keys1.length - 1]);
			range.replaceText("value" + i, entry.getValue());
			i++;
		}
		// for (String string : keys) {
		// if (string.contains("紫外线")) {
		// range.replaceText("ziwaixian", string);
		// range.replaceText("zwxtxt", indexForecast.get(string));
		// } else if (string.contains("感冒")) {
		// range.replaceText("ganmao", string);
		// range.replaceText("gmtxt", indexForecast.get(string));
		// } else if (string.contains("穿衣")) {
		// range.replaceText("chuanyi", string);
		// range.replaceText("cytxt", indexForecast.get(string));
		// } else if (string.contains("洗车")) {
		// range.replaceText("xiche", string);
		// range.replaceText("xctxt", indexForecast.get(string));
		// } else if (string.contains("运动")) {
		// range.replaceText("yundong", string);
		// range.replaceText("ydtxt", indexForecast.get(string));
		// } else if (string.contains("空气")) {
		// range.replaceText("kongqi", string);
		// range.replaceText("kqtxt", indexForecast.get(string));
		// }
		// }
		//
		// for (String string : keys) {
		// if (string.contains("紫外线")) {
		// range.replaceText("ziwaixian", string);
		// range.replaceText("zwxtxt", indexForecast.get(string));
		// } else if (string.contains("感冒")) {
		// range.replaceText("ganmao", string);
		// range.replaceText("gmtxt", indexForecast.get(string));
		// } else if (string.contains("穿衣")) {
		// range.replaceText("chuanyi", string);
		// range.replaceText("cytxt", indexForecast.get(string));
		// } else if (string.contains("洗车")) {
		// range.replaceText("xiche", string);
		// range.replaceText("xctxt", indexForecast.get(string));
		// } else if (string.contains("运动")) {
		// range.replaceText("yundong", string);
		// range.replaceText("ydtxt", indexForecast.get(string));
		// } else if (string.contains("空气")) {
		// range.replaceText("kongqi", string);
		// range.replaceText("kqtxt", indexForecast.get(string));
		// }
		// }

		df = new SimpleDateFormat("yyyyMMdd");
		String strDate = df.format(calendar.getTime());
		df = new SimpleDateFormat("yyyyMM");
		String strYear = df.format(calendar.getTime());

		String filePath = paths + "指数预报\\" + strYear + "\\";
		// String filePath = "/home/hebj/Documents/" + strYear + "//";
		File file = new File(filePath + "指数预报" + strDate + ".doc");
		if (!file.exists()) {
			File path = new File(filePath);
			if (!path.exists()) {
				path.mkdirs();
			}
			file.createNewFile();
		}
		OutputStream os = new FileOutputStream(file, false);

		// 把doc输出到输出流中
		doc.write(os);
		this.closeStream(os);
		this.closeStream(is);
	}

	/**
	 * 关闭输入流
	 * 
	 * @param is
	 */
	private void closeStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭输出流
	 * 
	 * @param os
	 */
	private void closeStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
