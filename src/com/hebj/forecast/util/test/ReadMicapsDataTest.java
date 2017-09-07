package com.hebj.forecast.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hebj.forecast.util.ReadMicapsData;

public class ReadMicapsDataTest {

	@Test
	public void test() {
		double value = ReadMicapsData.getValueFromInterpolated(9999, 9999, 9999.00, 9, 41, 14, 0.12);
		value = 1;
	}

}
