package com.adrip.ce.utils;

import com.adrip.ce.Main;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

import java.util.LinkedList;
import java.util.List;
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
        int[] chromosomeArray = new int[Main.getCitiesNumber()];
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            chromosomeArray[i] = (Integer) chromosome.getGene(i).getAllele();
        return chromosomeArray;
    }

    public static List<Integer> getChromosomeAsList(IChromosome chromosome) {
        List<Integer> chromosomeList = new LinkedList<>();
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            chromosomeList.add((Integer) chromosome.getGene(i).getAllele());
        return chromosomeList;
    }

    public static String getChromosomeToString(IChromosome chromosome) {
        int[] chromosomeArray = Utils.getChromosomeAsArray(chromosome);
        StringBuilder str = new StringBuilder();
        for (int j : chromosomeArray) str.append(Utils.getCityCode(j));
        return str.toString();
    }

    public static String getChromosomeToString(List<Integer> chromosomeList) {
        StringBuilder str = new StringBuilder();
        for (int gene: chromosomeList)
            str.append(Utils.getCityCode(gene));

        return str.toString();
    }
}
