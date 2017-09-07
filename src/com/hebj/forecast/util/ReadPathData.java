package com.hebj.forecast.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom2.JDOMException;

public class ReadPathData {

	/**
	 * 读取路径
	 * 
	 * @author hebj
	 * @throws IOException 
	 * @throws JDOMException 
	 */

	public static String getPath(Date time, String shiCi, int age, String forecastType, String modeType) throws JDOMException, IOException {
		String path;
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String rootPath = ReadDataFromConfig.getValue("micaps路径");
		switch (modeType.toLowerCase()) {
		case "ec":
			switch (forecastType.toLowerCase()) {
			case "physic":
				path = rootPath + File.separator + "ecmwf" + File.separator + "height" + File.separator + "500"
						+ File.separator + sdf.format(time) + shiCi + "." + new DecimalFormat("000").format(age);
				break;
			case "rh":
				path = rootPath + File.separator + "ecmwf_thin" + File.separator + "R" + File.separator + "850"
						+ File.separator + sdf.format(time) + shiCi + "." + new DecimalFormat("000").format(age);
				break;

			default:
				path = null;
				break;
			}
			break;

		default:
			path = null;
			break;
		}
		return path;
	}
}
