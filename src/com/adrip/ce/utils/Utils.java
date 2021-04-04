package com.adrip.ce.utils;

import org.jgap.Gene;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    /* Clase estatica. */
    private Utils() {
    }

    public static int generateRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static char getCityCode(int cityId) {
        if (cityId < 0 || cityId > 25)
            return '?';

        cityId += 65;
        return (char) cityId;
    }

    public static char getCityCode(Gene gene) {
        return Utils.getCityCode(Integer.parseInt(gene.getAllele().toString()));
    }
}
