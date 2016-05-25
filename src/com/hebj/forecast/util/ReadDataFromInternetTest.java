package com.hebj.forecast.util;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;

public class ReadDataFromInternetTest {

	@Test
	public void test() {

		String url = ReadDataFromInternet.getT7OnlineURL("53513");
		String lines = ReadDataFromInternet.getHtmlContent(url, "GBK");
		Date time = new Date();
		List<Forecast> forecasts = ReadForecastFromHTML.readT7OnlineForecast(lines, new Station(), time, 6);

	}

}
