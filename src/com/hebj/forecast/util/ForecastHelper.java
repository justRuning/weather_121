package com.hebj.forecast.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.jdom2.JDOMException;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.lvyou;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * 预报类帮助类，主要计算订正预报数据、由天气现象计算降雨量等
 * 
 * @author hebj
 *
 */
public class ForecastHelper {

	/**
	 * 通过天气预报制作短信内容
	 * 
	 * @param forecasts
	 * @return
	 */
	public static Map<String, String> makeForecastMsg(List<Forecast> forecasts) {

		Map<String, String> weathers = new LinkedHashMap<String, String>();
		for (Forecast forecast : forecasts) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(forecast.getBeginTime());
			String string = "";
			if (forecast.getStation().getStationName().equals("海力素")
					|| forecast.getStation().getStationName().equals("大佘太")) {
				continue;
			}

			if (forecast.getStation().getStationName().equals("后旗")) {

				String[] ShanHouTemp = getShanHouTemp(true);
				string = String.format("巴市气象台%s日下午发布%s天气预报：今晚到明天%s,%s,巴音镇%s到%s度，山后%s到%s度。",
						calendar.get(Calendar.DAY_OF_MONTH), forecast.getStation().getStationName(),
						forecast.getSkyDay(), forecast.getWindDirectionDay(), forecast.getMinTemp(),
						forecast.getMaxTemp(), ShanHouTemp[0], ShanHouTemp[1]);
				string = string.replace("4-5", "5-6");
				string = string.replace("3-4", "4-5");
				string = string.replace("2-3", "3-4");

			} else {
				string = String.format("巴市气象台%s日下午发布%s天气预报：今晚到明天%s,%s,气温：%s到%s度。", calendar.get(Calendar.DAY_OF_MONTH),
						forecast.getStation().getStationName(), forecast.getSkyDay(), forecast.getWindDirectionDay(),
						forecast.getMinTemp(), forecast.getMaxTemp());
			}

			weathers.put(forecast.getStation().getStationName(), string);
		}

		return weathers;
	}

	public static String[] getShanHouTemp(boolean today) {

		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connectDB = "jdbc:sqlserver://172.18.132.7;DatabaseName=YBPF";
		String[] result = { "", "" };

		try {

			Class.forName(JDriver);
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			System.out.println("加载数据库引擎失败");
			System.exit(0);
		}
		// System.out.println("数据库驱动成功");

		try {
			String user = "bynrs";
			// 这里只要注意用户名密码不要写错即可
			String password = "BMbm333555";
			Connection con = DriverManager.getConnection(connectDB, user, password);
			// 连接数据库对象
			// System.out.println("连接数据库成功");
			Statement stmt = con.createStatement();
			// 创建SQL命令对象

			// DatabaseMetaData dMetaData = con.getMetaData();
			// ResultSet tableRet = dMetaData.getTables(null, "%", "%", new
			// String[] { "TABLE" });
			// while (tableRet.next()) {
			// System.out.println(tableRet.getString("TABLE_NAME"));
			//
			// }
			//
			// ResultSet colRet = dMetaData.getColumns(null, "%", "预报表", "%");
			// while (colRet.next()) {
			// System.out.println(colRet.getString("COLUMN_NAME"));
			//
			// }

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String date = dateFormat.format(calendar.getTime());
			ResultSet rs = stmt.executeQuery("select top 1 * from 预报表 where 预报站点=66 and 预报时限=24 order by 发布日期 desc");// 返回SQL语句查询结果集(集合)
			// 循环输出每一条记录
			while (rs.next()) {
				// 输出每个字段
				// System.out.println(rs.getString("发布日期") + "\t" +
				// rs.getString("预报时限") + "\t" + rs.getString("预报站点")
				// + "\t" + rs.getString("天气形式") + "\t" + rs.getString("最低气温") +
				// "\t" + rs.getString("最高气温"));
				if (!today) {
					result[0] = rs.getString("最低气温");
					result[1] = rs.getString("最高气温");
				} else if (date.equals(rs.getString("发布日期"))) {
					result[0] = rs.getString("最低气温");
					result[1] = rs.getString("最高气温");
				}
			}

			// 关闭连接
			stmt.close();// 关闭命令对象连接
			con.close();// 关闭数据库连接
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print(e.getErrorCode());
			// System.out.println("数据库连接错误");
			System.exit(0);
		}

		return result;
	}

	/**
	 * 旅游景点预报读取
	 * 
	 * @return
	 */
	public static List<lvyou> getLvyou() {

		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connectDB = "jdbc:sqlserver://172.18.132.7;DatabaseName=YBPF";
		String[] result = { "", "" };
		List<lvyou> lvyous = new ArrayList<lvyou>();

		try {

			Class.forName(JDriver);
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			System.out.println("加载数据库引擎失败");
			System.exit(0);
		}
		// System.out.println("数据库驱动成功");

		try {
			String user = "bynrs";
			// 这里只要注意用户名密码不要写错即可
			String password = "BMbm333555";
			Connection con = DriverManager.getConnection(connectDB, user, password);
			// 连接数据库对象
			// System.out.println("连接数据库成功");
			Statement stmt = con.createStatement();
			// 创建SQL命令对象

			// DatabaseMetaData dMetaData = con.getMetaData();
			// // ResultSet tableRet = dMetaData.getTables(null, "%", "%", new
			// // String[] { "TABLE" });
			// // while (tableRet.next()) {
			// // System.out.println(tableRet.getString("TABLE_NAME"));
			// //
			// // }
			// //
			// ResultSet colRet = dMetaData.getColumns(null, "%", "预报表", "%");
			// while (colRet.next()) {
			// System.out.println(colRet.getString("COLUMN_NAME"));
			//
			// }

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String date = dateFormat.format(calendar.getTime());
			Map<String, String> stations = new LinkedHashMap<>();

			stations.put("100", "黄河湿地公园,临河");
			stations.put("101", "镜湖,临河");
			stations.put("9", "甘露寺,临河新华");
			stations.put("18", "黄河三盛公水利枢纽,磴口");
			stations.put("115", "纳林湖,磴口");
			stations.put("116", "恐龙化石保护区,乌后旗宝音图,");
			stations.put("31", "天吉泰机场,五原");
			stations.put("50", "抗日烈士陵园,五原");
			stations.put("117", "原生态旅游区,五原牧羊海");
			stations.put("104", "秦长城,乌前旗小佘太");
			stations.put("124", "乌梁素海,乌拉特前旗");
			stations.put("125", "大桦背,乌拉特前旗");
			stations.put("50 ", "石林,乌拉特中旗");
			stations.put("50  ", "地质公园,乌拉特中旗");
			for (Map.Entry<String, String> entry : stations.entrySet()) {

				String sql = "select top 1 * from 预报表 where 预报站点=" + entry.getKey().trim()
						+ " and 预报时限=24 order by 发布日期 desc";
				ResultSet rs = stmt.executeQuery(sql);// 返回SQL语句查询结果集(集合)
				// 循环输出每一条记录
				while (rs.next()) {
					// 输出每个字段
					// System.out.println(rs.getString("发布日期") + "\t" +
					// rs.getString("预报时限") + "\t" + rs.getString("预报站点")
					// + "\t" + rs.getString("天气形式") + "\t" +
					// rs.getString("最低气温") +
					// "\t" + rs.getString("最高气温"));
					// if (date.equals(rs.getString("发布日期"))) {
					lvyou lvyou = new lvyou();
					lvyou.name = entry.getValue().split(",")[0];
					lvyou.city = entry.getValue().split(",")[1];
					lvyou.sky = rs.getString("天气形式");
					lvyou.temp = rs.getString("最低气温") + "到" + rs.getString("最高气温");

					lvyou.wind = getWind(rs.getString("风速")) + rs.getString("风向") + "风";
					lvyous.add(lvyou);
					// }
				}
			}

			// 关闭连接
			stmt.close();// 关闭命令对象连接
			con.close();// 关闭数据库连接
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print(e.getErrorCode());
			// System.out.println("数据库连接错误");
			System.exit(0);
		}

		return lvyous;
	}

	/**
	 * 旅游天气预报写入到doc
	 * 
	 * @param indexForecast
	 * @throws Exception
	 */
	public static void writeLvyouForecast(List<lvyou> lvyous) throws Exception {
		String paths = ReadDataFromConfig.getValue("产品路径");
		String templatePath = paths + "//旅游预报//模板.doc";
		InputStream is = new FileInputStream(templatePath);
		HWPFDocument doc = new HWPFDocument(is);
		Range range = doc.getRange();

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String time = df.format(calendar.getTime());

		range.replaceText("time", time);

		for (int i = 0; i < lvyous.size(); i++) {
			String weather = "w" + (i + 1) + "t";
			String wind = "f" + (i + 1) + "t";
			String temp = "t" + (i + 1) + "t";

			range.replaceText(weather, lvyous.get(i).sky);
			range.replaceText(wind, lvyous.get(i).wind);
			range.replaceText(temp, lvyous.get(i).temp);
		}

		df = new SimpleDateFormat("yyyyMMdd");
		String strDate = df.format(calendar.getTime());
		df = new SimpleDateFormat("yyyyMM");
		String strYear = df.format(calendar.getTime());

		String filePath = paths + "旅游预报\\" + strYear + "\\";
		File file = new File(filePath + "旅游预报" + strDate + ".doc");
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

		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getWind(String wind) {

		if (wind.contains("≤3")) {
			return "2到3级";
		}
		return wind.replace("～", "到");
	}

	/**
	 * 通过天气预报得到121语音内容
	 * 
	 * @param forecasts
	 * @return
	 */
	public static Map<String, String> getYYMsg(List<Forecast> forecasts, String hour) {

		String time2 = null;
		if (hour == "早上" || hour == "上午" || hour == "中午") {
			time2 = "白天到夜间";
		} else {
			time2 = "夜间到明天白天";
		}
		Map<String, String> weathers = new LinkedHashMap<String, String>();
		for (Forecast forecast : forecasts) {
			Calendar calendar = Calendar.getInstance();
			List<String> winds = Helper.wind2wind(forecast.getWindDirectionDay());
			String wind = winds.get(1).replace("-", "到");
			String wind2 = Helper.getShanhouWind(wind);

			if (forecast.getStation().getStationName().equals("临河")) {
				String string = String.format(
						"听众朋友您好\r\n、\r\n今天是\r\n%s月\r\n、\r\n%s日\r\n、\r\n%s\r\n下面向您播送今天%s%s点\r\n、\r\n发布的\r\n天气预报\r\n、\r\n今天%s\r\n、\r\n全市\r\n、\r\n%s\r\n、\r\n北部\r\n%s级\r\n、\r\n河套地区\r\n%s级\r\n、\r\n%s风\r\n、\r\n%s\r\n、\r\n%s\r\n、\r\n气温\r\n、\r\n%s到\r\n%s℃\r\n、\r\n欢迎收听其他气象信息",
						calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
						Helper.getDayOfWeek(calendar.getTime()), hour, forecast.getHour(), time2, forecast.getSkyDay(),
						wind2, wind, winds.get(0), forecast.getStation().getStationName(), forecast.getSkyDay(),
						forecast.getMinTemp(), forecast.getMaxTemp());
				weathers.put(forecast.getStation().getStationName(), string);
			} else if (forecast.getStation().getStationName().equals("后旗")) {
				String[] ShanHouTemp = getShanHouTemp(false);
				String string = String.format(
						"下面向您播送今天%s%s点\r\n发布的\r\n%s\r\n天气预报\r\n、\r\n今天%s\r\n、\r\n%s\r\n、\r\n%s级\r\n、\r\n%s风\r\n、\r\n气温\r\n、\r\n前山地区\r\n、\r\n%s到\r\n%s℃\r\n、\r\n后山地区\r\n、\r\n%s到\r\n%s℃\r\n、\r\n欢迎收听其他气象信息",
						hour, forecast.getHour(), forecast.getStation().getStationName(), time2, forecast.getSkyDay(),
						wind2, winds.get(0), forecast.getMinTemp(), forecast.getMaxTemp(), ShanHouTemp[0],
						ShanHouTemp[1]);
				// string = string.replace("4到5", "5到6");
				// string = string.replace("3到4", "4到5");
				// string = string.replace("2到3", "3到4");
				weathers.put(forecast.getStation().getStationName(), string);

			} else {
				String string = String.format(
						"下面向您播送今天%s%s点\r\n发布的\r\n%s\r\n天气预报\r\n、\r\n今天%s\r\n、\r\n%s\r\n、\r\n%s级\r\n、\r\n%s风\r\n、\r\n气温\r\n、\r\n%s到\r\n%s℃\r\n、\r\n欢迎收听其他气象信息",
						hour, forecast.getHour(), forecast.getStation().getStationName(), time2, forecast.getSkyDay(),
						winds.get(1).replace("-", "到"), winds.get(0), forecast.getMinTemp(), forecast.getMaxTemp());
				weathers.put(forecast.getStation().getStationName(), string);
			}
		}

		return weathers;

	}

	/**
	 * 保存短信文件
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	public static String savaMsg(String name, String text) {
		switch (name) {
		case "临河":
			name = "BMLH";
			break;
		case "前旗":
			name = "BMWQ";
			break;
		case "五原":
			name = "BMWY";
			break;
		case "磴口":
			name = "BMDK";
			break;
		case "杭锦后旗":
			name = "BMHH";
			text = text.replace("杭锦后旗", "杭后");
			break;
		case "中旗":
			name = "BMWZ";
			break;
		case "后旗":
			name = "BMWH";
			break;
		default:
			return "站点错误！!";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		Calendar calendar = Calendar.getInstance();
		String time = dateFormat.format(calendar.getTime());
		name = name + time + "16";

		String result = "";
		try {
			writeMsgTxt(name, text);
			result = "文件更新完成！";
		} catch (IOException e) {
			result = "文件更新失败！";
		}

		return result;

	}

	/**
	 * 读取预报txt文件
	 * 
	 * @param name
	 *            站点名称
	 * @return 文件内容
	 */
	public static String readMsg(String name) {
		switch (name) {
		case "临河":
			name = "BMLH";
			break;
		case "前旗":
			name = "BMWQ";
			break;
		case "五原":
			name = "BMWY";
			break;
		case "磴口":
			name = "BMDK";
			break;
		case "杭锦后旗":
			name = "BMHH";
			break;
		case "中旗":
			name = "BMWZ";
			break;
		case "后旗":
			name = "BMWH";
			break;
		default:
			return "站点错误！!";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		Calendar calendar = Calendar.getInstance();
		String time = dateFormat.format(calendar.getTime());
		name = name + time + "16";

		String result = "";
		try {
			result = readMsgTxt(name);
		} catch (IOException e) {
			result = null;
		}

		return result;
	}

	/**
	 * 写入短信文件
	 * 
	 * @param name
	 * @param text
	 * @throws IOException
	 */
	public static void writeMsgTxt(String name, String content) throws IOException {

		File file = null;

		try {
			file = new File(ReadDataFromConfig.getValue("短信路径") + name + ".txt");
		} catch (JDOMException e) {
			e.printStackTrace();
		}

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
	 * 读取短信txt文件
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static String readMsgTxt(String name) throws IOException {

		File file = null;
		try {
			file = new File(ReadDataFromConfig.getValue("短信路径") + name + ".txt");
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		return result.toString();

	}

	/**
	 * 保存语音文件
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	public static String savaYYMsg(String name, String text) {
		switch (name) {
		case "临河":
			name = "7m0";
			break;
		case "前旗":
			name = "741";
			break;
		case "五原":
			name = "742";
			break;
		case "磴口":
			name = "743";
			text = text.replace("磴口", "磴(deng)口");
			break;
		case "杭锦后旗":
			name = "744";
			break;
		case "中旗":
			name = "745";
			break;
		case "后旗":
			name = "746";
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

	/**
	 * 读取预报txt文件
	 * 
	 * @param name
	 *            站点名称
	 * @return 文件内容
	 */
	public static String readYYMsg(String name) {
		switch (name) {
		case "临河":
			name = "7m0";
			break;
		case "前旗":
			name = "741";
			break;
		case "五原":
			name = "742";
			break;
		case "磴口":
			name = "743";
			break;
		case "杭锦后旗":
			name = "744";
			break;
		case "中旗":
			name = "745";
			break;
		case "后旗":
			name = "746";
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

	/**
	 * 对预报对象进行调整，如降水量变降水等级
	 * 
	 * @param forecast
	 * @return
	 */
	public static Forecast correctForecast(Forecast forecast) {
		if (forecast.getMaxTemp() == null || forecast.getMinTemp() == null) {
			return null;
		}
		double maxTem = Double.parseDouble(forecast.getMaxTemp());
		double minTem = Double.parseDouble(forecast.getMinTemp());
		forecast.setMinTempRevised(forecast.getMinTemp());

		if (forecast.getForecastType() == "American") {
			forecast = ForecastHelper.americForecast(forecast);
		}
		if (forecast.getForecastType() == "T7Online") {
			forecast = ForecastHelper.T7onlineForecast(forecast);
		}

		switch (forecast.getForecastType()) {

		case "American":
		case "T7Online":
		case "中国天气网":
		case "中央指导":
			maxTem = maxTem + ForecastHelper.windDirection(forecast.getWindDirectionDay())
					- ForecastHelper.windVelocity(forecast.getWindVelocityDay())
					- ForecastHelper.sky(forecast.getSkyDay()) + ForecastHelper.rain(forecast.getRainDay());
			minTem = minTem + ForecastHelper.windDirection(forecast.getWindDirectionNight())
					+ ForecastHelper.windVelocity(forecast.getWindVelocityNight())
					+ ForecastHelper.sky(forecast.getSkyNight()) + ForecastHelper.rain(forecast.getRainNight());
			break;
		case "Ec":
		case "T639":
		case "Grapes":
			maxTem = maxTem + ForecastHelper.windDirection(forecast.getWindDirectionDay())
					- ForecastHelper.windVelocity(forecast.getWindVelocityDay())
					- ForecastHelper.RH(forecast.getRHDay()) + ForecastHelper.rain(forecast.getRainDay());
			minTem = minTem + ForecastHelper.windDirection(forecast.getWindDirectionNight())
					+ ForecastHelper.windVelocity(forecast.getWindVelocityNight())
					+ ForecastHelper.RH(forecast.getRHNight()) + ForecastHelper.rain(forecast.getRainNight());
			break;
		case "local":
			forecast.setWindDirectionNight(forecast.getWindDirectionDay());
			forecast.setWindVelocityNight(forecast.getWindVelocityDay());
			maxTem = maxTem + ForecastHelper.windDirection(forecast.getWindDirectionDay())
					- ForecastHelper.windVelocity(forecast.getWindVelocityDay())
					- ForecastHelper.sky(forecast.getSkyDay());
			minTem = maxTem + ForecastHelper.windDirection(forecast.getWindDirectionNight())
					+ ForecastHelper.windVelocity(forecast.getWindVelocityNight())
					+ ForecastHelper.sky(forecast.getSkyNight());
			break;
		default:
			break;
		}
		forecast.setMaxTempRevised(String.valueOf(((int) (maxTem * 10)) / 10.0));
		forecast.setMinTempRevised(String.valueOf(((int) (minTem * 10)) / 10.0));
		return forecast;
	}

	/**
	 * 降雨等级对温度的影响
	 * 
	 * @param value
	 * @return
	 */
	public static double rain(String value) {

		double result;
		if (value == null) {
			return 0;
		}
		Matcher matcher = Pattern.compile("^[-\\+]?[\\d]*\\.[\\d]*$").matcher(value);
		if (matcher.find()) {
			double douValue = Double.parseDouble(value);
			if (douValue < 5) {
				result = 0;
			} else if (douValue < 10) {
				result = 0.2;
			} else if (douValue < 15) {
				result = 0.5;
			} else if (douValue < 30) {
				result = 1;
			} else if (douValue < 70) {
				result = 2;
			} else {
				result = 3;
			}

			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH);
			if (month <= 11 && month >= 2) {
				result = -result;
			}
		}

		else {
			switch (value) {
			case "微量":
			case "阵雨":
			case "小阵雨":
			case "阵雪":
				result = 0;
				break;
			case "小雨":
				result = -0.2;
				break;
			case "小雪":
				result = 0.2;
				break;
			case "中雨":
				result = -0.5;
				break;
			case "中雪":
				result = 0.5;
				break;
			case "大雨":
				result = -1;
				break;
			case "大雪":
				result = 1;
				break;
			case "暴雨":
				result = -2;
				break;
			case "暴雪":
				result = 2;
				break;
			case "大暴雨":
				result = -3;
				break;
			case "大暴雪":
				result = 3;
				break;
			default:
				result = 0;
				break;
			}
		}

		return result;
	}

	public static String getMainSky(String sky) {
		String mainSky = "";
		if (sky.contains("暴雨")) {
			mainSky = "暴雨";
		} else if (sky.contains("暴雪")) {
			mainSky = "暴雪";
		} else if (sky.contains("大雨")) {
			mainSky = "大雨";
		} else if (sky.contains("大雪")) {
			mainSky = "大雪";
		} else if (sky.contains("中雨")) {
			mainSky = "中雨";
		} else if (sky.contains("中雪")) {
			mainSky = "中雪";
		} else if (sky.contains("小雨")) {
			mainSky = "小雨";
		} else if (sky.contains("小雪")) {
			mainSky = "小雪";
		} else if (sky.contains("阵雨")) {
			mainSky = "阵雨";
		} else if (sky.contains("阵雪")) {
			mainSky = "阵雪";
		} else if (sky.contains("多云")) {
			mainSky = "多云";
		} else {
			mainSky = "晴";
		}

		return mainSky;
	}

	/**
	 * 天空状况对温度的影响——cloud
	 * 
	 * @param value
	 * @return
	 */
	private static double sky(String value) {
		double result;
		if (value == null) {
			return 0;
		}
		switch (value) {
		case "晴":
		case "晴间少云":
			result = 0;
			break;
		case "晴间多云":
			result = 0.2;
			break;
		case "多云间晴":
			result = 0.3;
			break;
		case "多云":
		case "轻雾":
		case "浮尘":
			result = 0.5;
			break;
		case "多云间阴":
		case "霾":
		case "扬沙":
			result = 0.8;
			break;
		case "阴":
		case "大雾":
		case "沙尘暴":
			result = 1;
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}

	/**
	 * 风向对温度的影响
	 * 
	 * @param value
	 * @return
	 */
	private static double windDirection(String value) {
		double result;
		if (value == null) {
			return 0;
		}
		switch (value) {
		case "N":
		case "NNE":
		case "NNW":
			result = -0.3;
			break;
		case "NW":
		case "NE":
			result = -0.2;
			break;
		case "E":
		case "ENE":
		case "ESE":
			result = -0.1;
			break;
		case "W":
		case "WNW":
		case "WSW":
			result = 0.1;
			break;
		case "SW":
		case "SE":
			result = 0.2;
			break;
		case "S":
		case "SSW":
		case "SSE":
			result = 0.3;
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}

	/**
	 * 风速对温度的影响
	 * 
	 * @param value
	 * @return
	 */
	private static double windVelocity(String value) {
		double result;
		if (value == null) {
			return 0;
		}
		switch (value) {
		case "C":
		case "1":
		case "2":
		case "1-2":
			result = 0;
			break;
		case "3":
		case "2-3":
			result = 0.2;
			break;
		case "4":
		case "3-4":
			result = 0.3;
			break;
		case "5":
		case "4-5":
			result = 0.5;
			break;
		case "6":
		case "5-6":
			result = 0.8;
			break;
		case "7":
		case "8":
		case "9":
		case "10":
		case "11":
		case "6-7":
		case "7-8":
		case "8-9":
			result = 1;
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}

	/**
	 * 湿度对温度的影响
	 * 
	 * @param value
	 * @return
	 */
	private static double RH(String value) {
		if (value == null) {
			return 0;
		}

		double result;
		double douValue = Double.parseDouble(value);
		if (douValue <= 40) {
			result = 0;
		} else if (douValue <= 60) {
			result = 0.2;
		} else if (douValue <= 70) {
			result = 0.3;
		} else if (douValue <= 80) {
			result = 0.5;
		} else if (douValue <= 90) {
			result = 0.8;
		} else if (douValue <= 100) {
			result = 1;
		} else {
			result = 0;
		}

		return result;
	}

	/**
	 * 通过天气在线天气描述，得到天空状况和降水
	 * 
	 * @param forecast
	 * @return
	 */
	private static Forecast T7onlineForecast(Forecast forecast) {

		forecast.setWindDirectionNight(forecast.getWindDirectionDay());
		forecast.setWindVelocityNight(forecast.getWindVelocityDay());

		String[] valueDay = ForecastHelper.T7Rain(forecast.getSkyDay());
		String[] valueNight = ForecastHelper.T7Rain(forecast.getSkyNight());

		forecast.setSkyDay(valueDay[0]);
		forecast.setRainDay(valueDay[1]);

		forecast.setSkyNight(valueNight[0]);
		forecast.setRainNight(valueNight[1]);

		return forecast;
	}

	/**
	 * 通过天气在线天气描述，得到天空状况和降水
	 * 
	 * @param forecast
	 * @return
	 */
	private static String[] T7Rain(String value) {
		String[] result = new String[2];
		value = value.replace("有", "");
		if (value.contains(",")) {

			result[0] = value.split(",")[0].trim();
			result[1] = value.split(",")[1].trim();
		} else {
			result[0] = value.trim();
			result[1] = null;
		}
		return result;
	}

	/**
	 * 通过美国天气网站天气描述，得到天空状况和降水
	 * 
	 * @param forecast
	 * @return
	 */
	private static Forecast americForecast(Forecast forecast) {
		String[] valueDay = ForecastHelper.getAmericRain(forecast.getSkyDay());
		String[] valueNight = ForecastHelper.getAmericRain(forecast.getSkyNight());
		forecast.setSkyDay(valueDay[0]);
		forecast.setRainDay(valueDay[1]);
		forecast.setSkyNight(valueNight[0]);
		forecast.setRainNight(valueNight[1]);

		return forecast;
	}

	/**
	 * 通过美国天气网站天气描述，得到天空状况和降水
	 * 
	 * @param value
	 * @return
	 */
	private static String[] getAmericRain(String value) {

		String[] result = new String[2];
		result[1] = null;
		if (value == null) {
			result[1] = result[0] = null;
			return result;
		}
		switch (value) {
		case "Sun":
		case "Sunny":
		case "Clear":
			result[0] = "晴";
			break;
		case "Mostly Sunny":
		case "Mostly Clear":
			result[0] = "晴间少云";
			break;
		case "Partly Cloudy":
			result[0] = "晴间多云";
			break;
		case "Mostly Cloudy":
		case "Clouds Early / Clearing Late":
		case "AM Clouds / PM. Clear":
		case "AM Clouds / PM Sun":
			result[0] = "多云间晴";
			break;
		case "Cloudy":
		case "Clearing Early / Clouds Late":
		case "AM Clear / PM Clouds":
			result[0] = "多云";
			break;
		case "Cloudy Day":
			result[0] = "阴";
			break;
		case "Wind":
		case "Showers/ Wind Late":
		case "PM Showers/ Wind":
			result[0] = "大风";
			break;
		case "Light Rain / Wind":
		case "PM Rain / Wind":
		case "Rain":
		case "Rain Early":
		case "Light Rain Early":
		case "Rain / Wind Early":
		case "Rain / Wind Late":
		case "Rain / Wind":
		case "AM Rain":
		case "PM Rain":
		case "AM Clouds Rain":
		case "AM Rain / Wind":
		case "PM Clouds Rain":
		case "AM Light Rain":
		case "AM Light Rain / Wind":
		case "PM Light Rain":
		case "Light Rain":
		case "Light Rain Late":
		case "Rain Late":
		case "Clouds Day Rain":
			result[1] = "小雨";
			result[0] = "阴";
			break;
		case "Showers Late": // 晚些时候的阵雨
		case "Showers Early": // 早期阵雨
		case "Showers / Wind": // 阵雨/风
		case "Showers / Wind Early":
		case "Showers":
		case "AM Showers":
		case "AM Showers / Wind":
		case "PM Showers":
		case "Few Showers":
			result[0] = "阴";
			result[1] = "阵雨";
			break;
		case "Isolated Thunderstorms": // 孤立的雷暴
		case "Scattered Thunderstorms": // 分散的雷暴
		case "Rain / Thunder": // 雨伴有雷声
		case "Thunderstorms": // 雷暴
		case "Thunderstorms Early": // 早期的雷暴
		case "Thunderstorms Late": // 晚些时候的雷暴
		case "PM Thunderstorms": // 下午雷暴
		case "AM Thunderstorms": // 上午雷暴
			result[0] = "雷暴";
			break;
		case "Snow": // 雪
		case "Snow Showers": // 阵雪
		case "AM Snow Showers": // 上午阵雪
		case "PM Snow Showers": // 下午阵雪
		case "Snow Showers Early": // 早期阵雪
		case "Snow Showers Late": // 晚些时候的阵雪
			result[0] = "阴";
			result[1] = "小雪";
			break;
		case "Rain / Snow": // 雨或雪
		case "Rain / Snow Early":
		case "Rain / Snow Showers Early":
		case "AM Rain / Snow":
		case "PM Rain / Snow":
		case "PM Rain / Snow Showers":
		case "Rain / Snow Showers":
		case "Ice Late": // 后期冻雨
		case "Ice Early": // 早期冻雨
		case "Rain / Ice Early": // 雨/早期冻雨
		case "Rain to Snow": // 雨转雪
		case "Snow to Rain": // 雪转雨
		case "Rain to Ice": // 雨转冻雨
		case "Ice to Rain": // 冻雨转雨
			result[0] = "阴";
			result[1] = "雨夹雪";
			break;
		case "Mostly Clear / Wind":
		case "Mostly Sunny / Wind":
		case "Cloudy Early / Clearing Late / Wind":
		case "Sunny / Wind":
		case "Cloudy / Wind":
		case "Partly Cloudy / Wind":
		case "Clear / Wind":
		case "Mostly Cloudy / Wind":
		case "AM Clouds / PM Sun / Wind":
			result[0] = "晴间多云";
			break;
		default:
			result[0] = value;
			break;
		}
		return result;
	}

	public static String getForecastTxt() {
		String line = null;
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int second = hour >= 16 ? 7 : 5;
		DateFormat dateFormat = new SimpleDateFormat("dd");
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", "administrator", "123");
		String filePath = "smb://172.18.132.45/forecast/LHDY" + dateFormat.format(calendar.getTime()) + second
				+ "W.ENN";
		SmbFile smbFile;
		try {
			smbFile = new SmbFile(filePath, auth);
			if (smbFile.exists()) {
				try {
					line = ReadDataFromTxt.readData(smbFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {

		}

		if (line != null) {
			line = line.replace("\r\n", "<br>");
			line = line.replace(" ", "&nbsp");
		}

		return line;
	}
}
