package com.increff.pos.util;

public class StringUtil {

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}


	public static String toLowerCase(String s) {
		return s == null ? null : s.trim().toLowerCase();
	}
    public static double roundOff(double num)
	{
		double newNum=Math.round(num*100.0)/100.0;
        return newNum;
	}
}
