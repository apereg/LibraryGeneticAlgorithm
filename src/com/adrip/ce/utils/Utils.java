package com.adrip.ce.utils;

import com.adrip.ce.Main;
import org.jgap.IChromosome;

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


    public static String getCityCode(int id) {
        /* Se obtiene cada Letra de principio a final de forma recursiva. */
        int quot = id / 26;
        int actualLetter = id % 26;
        char letter = (char) ((int) 'A' + actualLetter);
        return ((quot == 0) ? "" + letter : getCityCode(quot - 1) + letter);
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
        for (int j : chromosomeArray) str.append(Utils.getCityCode(j)).append("-");
        return str.deleteCharAt(str.length() - 1).toString();
    }

    public static String getChromosomeToString(List<Integer> chromosomeList) {
        StringBuilder str = new StringBuilder();
        for (int gene : chromosomeList) str.append(Utils.getCityCode(gene)).append("-");
        return str.deleteCharAt(str.length() - 1).toString();
    }

    public static String getChromosomeToIntString(IChromosome chromosome) {
        int[] chromosomeArray = Utils.getChromosomeAsArray(chromosome);
        StringBuilder str = new StringBuilder();
        for (int j : chromosomeArray) str.append(j).append("-");
        return str.deleteCharAt(str.length() - 1).toString();
    }
}
