package com.hebj.forecast.util;

public class Helper {

	public static String wind2String(String str){
		double direction1 = Double.valueOf(str);
        String direction2 = null;
        if (337.5 < direction1 || direction1 <= 22.5)
        {
            direction2 = "北风";
        }
        else if (direction1 <= 67.5)
        {
            direction2 = "东北风";
        }
        else if (direction1 <= 112.5)
        {
            direction2 = "东风";
        }
        else if (direction1 <= 157.5)
        {
            direction2 = "东南风";
        }
        else if (direction1 <= 202.5)
        {
            direction2 = "南风";
        }
        else if (direction1 <= 247.5)
        {
            direction2 = "西南风";
        }
        else if (direction1 <= 292.5)
        {
            direction2 = "西风";
        }
        else if (direction1 <= 337.5)
        {
            direction2 = "西北风";
        }
        return direction2;
		
	}
	
	public static String[] wind2wind(String wind){
		if (wind==null) {
			return null;
		}
		
		String[] strings = wind.split("风");
		return strings;
	}
}
