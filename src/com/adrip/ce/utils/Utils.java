package com.adrip.ce.utils;

import com.adrip.ce.Main;
import org.jgap.Gene;
import org.jgap.IChromosome;

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

    public static int[] getChromosomeAsArray(IChromosome chromosome) {
        int[] route = new int[Main.getCitiesNumber()];
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            route[i] = (Integer) chromosome.getGene(i).getAllele();
        return route;
    }

    public static String getChromosomeToString(IChromosome chromosome) {
        int[] c = Utils.getChromosomeAsArray(chromosome);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < c.length; i++)
            str.append(Utils.getCityCode(c[i]));
        return str.toString();
    }
}
