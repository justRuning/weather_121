package com.hebj.forecast.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.hibernate.procedure.internal.Util.ResultClassesResolutionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ReadDataFromInternet {

	/**
	 * 获取www.weather.com对应站点url
	 * 
	 * @param stationID
	 * @return
	 */
	public static String getAmericanURL(String stationID) {
		String url = null;
		switch (stationID) {
		case "53513":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHXX0253:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%2020HTTP/1.1";
			break;
		case "53419":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHXX2394:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%20HTTP/1.1";
			break;
		case "53420":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHXX4842:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%20HTTP/1.1";
			break;
		case "53337":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHXX4833:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%20HTTP/1.1";
			break;
		case "53433":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHNM1450:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%20HTTP/1.1";
			break;
		case "53336":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHXX0246:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%20HTTP/1.1";
			break;
		case "53324":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHNM0112:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%20HTTP/1.1";
			break;
		case "53231":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHXX0244:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%20HTTP/1.1";
			break;
		case "53348":
			url = "http://dsx.weather.com/(x/v2/web/WebDFRecord/en_US;cs/v2/datetime/en_US)/CHXX1243:1:CH?api=7bb1c920-7027-4289-9c96-ae5e263980bc&jsonp=angular.callbacks._5%20HTTP/1.1";
		default:
			break;
		}
		return url;
	}

	/**
	 * 通过站号获取天气在线网站7天预报url
	 * 
	 * @param stationID
	 * @return
	 */
	public static String getT7OnlineURL(String stationID) {

		String url = "http://www.t7online.com/cgi-bin/citybild?WMO=" + stationID
				+ "&LANG=cn&BKM=InnereMongolei/Linhe&SID=b";
		String referer = "http://www.t7online.com/cgi-bin/region?PRG=citybild&CONT=cncn&CREG=hkch&WMO=" + stationID
				+ "&LAND=CI&LANG=cn";
		String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36";
		Document doc;
		try {
			doc = Jsoup.connect(url).referrer(referer).userAgent(userAgent).ignoreContentType(true).get();
			Elements links = doc.getElementsByTag("a");
			String link = "http://www.t7online.com" + links.get(3).attr("onclick").split("'")[1];
			return link;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 通过url读取html内容
	 * 
	 * @param url
	 * @return
	 */
	public static String getHtmlContent(String url, String encoding) {
		URL getURL;
		try {
			getURL = new URL(url);
		} catch (MalformedURLException e1) {
			return null;
		}
		String temp = null;
		StringBuffer content = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(getURL.openStream(), encoding));
			while ((temp = in.readLine()) != null) {
				content.append(temp);
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return content.toString();
	}
}
