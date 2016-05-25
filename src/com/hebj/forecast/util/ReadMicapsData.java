package com.hebj.forecast.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hebj.forecast.entity.Station;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 * 读取micaps数据
 * 
 * @author hebj
 *
 */
public class ReadMicapsData {

	public static String getMicapsData(String type, Date time, int hour, int age) {

		String path = null;
		String rootPath = ReadDataFromConfig.getValue("micaps路径");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		switch (type.toLowerCase()) {
		case "physic":
			path = rootPath + File.separator + "ecmwf" + File.separator + "height" + File.separator + "500"
					+ File.separator + sdf.format(time) + hour + "." + new DecimalFormat("000").format(age);
			break;
		case "rh":
			path = rootPath + File.separator + "ecmwf_thin" + File.separator + "R" + File.separator + "850"
					+ File.separator + sdf.format(time) + hour + "." + new DecimalFormat("000").format(age);
			break;
		default:
			return null;
		}
		
		SmbFile smbFile = null;
		try {
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", "m3", "qaz123!@#");
			smbFile = new SmbFile(path, auth);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		String micapsData = null;

		try {
			if (smbFile.exists()) {
				BufferedReader br = null;
				br = new BufferedReader(new InputStreamReader(new SmbFileInputStream(smbFile)));
				try {
					String line = br.readLine();
					StringBuffer micaps = new StringBuffer();
					while (line != null) {
						micaps.append(line);
						micaps.append("\n");
						line = br.readLine();
					}
					micapsData = micaps.toString();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (SmbException | MalformedURLException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return micapsData;
	}

	public static String getValueLikePhysic(String micapsData, Station station) {

		/**
		 * 双线性插值求站点值
		 */
		String[] strList = micapsData.split("\n{2,}");
		double beginLatitude = Double.parseDouble(strList[2].split("\\s{1,}")[4]);
		double beginLongitude = Double.parseDouble(strList[2].split("\\s{1,}")[2]);
		double range = Double.parseDouble(strList[2].split("\\s{1,}")[0]);
		double f11, f12, f21, f22;

		// Math.Ceiling 如3.4，则在第四行
		int q1 = (int) Math.abs((beginLatitude - station.getLatitude()) / range) + 1;
		int q2 = (int) Math.abs((beginLongitude - station.getLongitude()) / range);

		String[] liStrings = strList[q1].split("\t{1}");
		f11 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + 1].split("\t{1}");
		f12 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1].split("\t{1}");
		f21 = Double.parseDouble(liStrings[q2 + 1]);

		liStrings = strList[q1 + 1].split("\t{1}");
		f22 = Double.parseDouble(liStrings[q2 + 1]);

		double result = getValueFromInterpolated(f21, f11, f22, f12, station.getLongitude(), station.getLatitude(),
				range);
		return Double.toString(result);
	}

	private static double getValueFromInterpolated(double f21, double f11, double f22, double f12, double longitude,
			double latitude, double range) {

		/**
		 * 双线性插值求站点值 四个格点数求站点数
		 */

		Double result;
		Double N1, E1, N2, E2;
		N1 = ((int) (latitude / range)) * range;
		E1 = ((int) (longitude / range)) * range;
		N2 = ((int) (latitude / range)) * range + range;
		E2 = ((int) (longitude / range)) * range + range;
		result = f11 * (E2 - longitude) * (N2 - latitude) / ((E2 - E1) * (N2 - N1))
				+ f21 * (longitude - E1) * (N2 - latitude) / ((E2 - E1) * (N2 - N1))
				+ f12 * (E2 - longitude) * (latitude - N1) / ((E2 - E1) * (N2 - N1))
				+ f22 * (longitude - E1) * (latitude - N1) / ((E2 - E1) * (N2 - N1));
		return ((int) (result * 100)) / 100;

	}
}
