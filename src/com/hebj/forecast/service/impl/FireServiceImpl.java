package com.hebj.forecast.service.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.FireDao;
import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.Fire;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.service.FireService;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Service
public class FireServiceImpl implements FireService {

	@Autowired
	FireDao FireDao;
	@Autowired
	WeatherActualDao weatherActualDao;
	@Autowired
	ForecastDao forecastDao;

	@Override
	public Fire makeFire() {

		List<Forecast> forecasts = forecastDao.getLastForecast(weatherActualDao.getStations());
		String date;
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("M月d日");
		date = dateFormat.format(calendar.getTime());
		String[] winds = forecasts.get(0).getWindDirectionDay().split("风");
		String wind = null;
		int[] tempTao = { 30, -30 };
		int[] tempBei = { 30, -30 };
		if (winds[0].length() == 1) {
			winds[0] = "偏" + winds[0];
		}
		if (winds[1].contains("5-6")) {
			wind = "6级左右" + winds[0] + "风";
		}
		if (winds[1].contains("4-5")) {
			wind = "5级左右" + winds[0] + "风";
		}
		if (winds[1].contains("3-4")) {
			wind = "4级左右" + winds[0] + "风";
		}
		if (winds[1].contains("2-3")) {
			wind = "3级左右" + winds[0] + "风";
		}
		for (Forecast forecast : forecasts) {
			if (forecast.getStation().getStationName().equals("中旗")
					|| forecast.getStation().getStationName().equals("海力素")) {
				tempBei[0] = Integer.parseInt(forecast.getMinTemp()) < tempBei[0]
						? Integer.parseInt(forecast.getMinTemp())
						: tempBei[0];
				tempBei[1] = Integer.parseInt(forecast.getMaxTemp()) > tempBei[1]
						? Integer.parseInt(forecast.getMaxTemp())
						: tempBei[1];

				String[] hou = getHou();
				if (!hou[0].equals("")) {
					tempBei[0] = Integer.parseInt(hou[0]) < tempBei[0] ? Integer.parseInt(hou[0]) : tempBei[0];
					tempBei[1] = Integer.parseInt(hou[1]) > tempBei[1] ? Integer.parseInt(hou[1]) : tempBei[1];
				}

			} else {
				tempTao[0] = Integer.parseInt(forecast.getMinTemp()) < tempTao[0]
						? Integer.parseInt(forecast.getMinTemp())
						: tempTao[0];
				tempTao[1] = Integer.parseInt(forecast.getMaxTemp()) > tempTao[1]
						? Integer.parseInt(forecast.getMaxTemp())
						: tempTao[1];
			}
		}

		String content = String.format(
				"巴市气象台%s下午发布森林火险预报：今晚到明天全市%s，%s。相对湿度15%%到65%%。气温：套区%d到%d℃,北部%d到%d℃。森林火险等级：三级，可燃。", date,
				forecasts.get(0).getSkyDay(), wind, tempTao[0], tempTao[1], tempBei[0], tempBei[1]);

		Fire fire = new Fire();
		fire.content = content;
		fire.date = new Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));

		return fire;
	}

	@Override
	public Fire getFire(Date date) {
		return FireDao.getFire(date);
	}

	@Override
	public void sava(Fire Fire) {
		FireDao.saveFire(Fire);

	}

	public String[] getHou() {
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

		try {
			String user = "bynrs";

			String password = "BMbm333555";
			Connection con = DriverManager.getConnection(connectDB, user, password);

			Statement stmt = con.createStatement();
			Calendar calendar1 = Calendar.getInstance();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
			String date1 = dateFormat1.format(calendar1.getTime());
			ResultSet rs = stmt.executeQuery("select top 1 * from 预报表 where 预报站点=66 and 预报时限=24 order by 发布日期 desc");// 返回SQL语句查询结果集(集合)
			// 循环输出每一条记录
			while (rs.next()) {
				// 输出每个字段
				// System.out.println(rs.getString("发布日期") + "\t" +
				// rs.getString("预报时限") + "\t" + rs.getString("预报站点")
				// + "\t" + rs.getString("天气形式") + "\t" + rs.getString("最低气温") +
				// "\t" + rs.getString("最高气温"));
				if (date1.equals(rs.getString("发布日期"))) {
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

}
