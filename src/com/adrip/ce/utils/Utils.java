package com.adrip.ce.utils;

public class Utils {

    /* Clase estatica. */
    private Utils() {
    }

    public static char getCityCode(int cityId) {
        if (cityId < 0 || cityId > 25)
            return '?';

        cityId += 65;
        return (char) cityId;
    }

}
