package com.hebj.forecast.util.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.hebj.forecast.util.GetIndexForecast;

public class GetIndexForecastTest {

	@Test
	public void test() {
		Map<String, String> indexForecast = GetIndexForecast.getIndex();
		GetIndexForecast indexForecast2 = new GetIndexForecast();
		try {
			indexForecast2.writeIndexForecast(indexForecast);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
