package com.increff.pos.util;

import com.increff.pos.service.ApiException;

public class StringUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }


    public static String toLowerCase(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

    public static double roundOff(double num) {
        return Math.round((num * 100.0)/100.0) ;
    }
    public static void checkSpecialChar(String string) throws ApiException {
        String strPattern = "^[a-zA-Z0-9]*$";
        boolean specialCharactersBrandCheck = string.matches(strPattern);

        if(!specialCharactersBrandCheck)
            throw new ApiException("you can't use special characters");
    }
}
