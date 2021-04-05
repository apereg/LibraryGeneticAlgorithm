package com.adrip.ce;

import com.adrip.ce.librarygeneticalgorithm.GeneticAlgorithm;

public class Main {

    public static final int GENERATIONS = 150;
    public static final int CHROMOSOMES = 10;

    public static final int MUTATION_PROB = 5;

    public static final boolean DEBUG_CREATE = true;
    public static final boolean DEBUG_CROSSOVER = true;
    public static final boolean DEBUG_EVALUATE = true;

    public static final int CROSSOVER_PROB = 95;


    /* A=0, B=1, C=2 ... */
    public static final int CITY_CHOSEN = 0;

    public static int[][] DISTANCES = {{5, 10, 20, Integer.MAX_VALUE, 10}, {5, 20, Integer.MAX_VALUE, 15, 1}, {10, 20, 10, 15, 50}, {20, Integer.MAX_VALUE, 10, 25, 20}, {Integer.MAX_VALUE, 15, 15, 25, 100}, {10, 1, 50, 20, 100}};
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Main.checkDistances();
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        algorithm.run();
        long time = System.currentTimeMillis() - start;
        System.out.println("\nEl algoritmo ha tardado " + time + " ms.");
    }
    private static void checkDistances() {
        /*for (int i = 0; i < DISTANCES.length; i++) {
            for (int j = 0; j < DISTANCES[0].length; j++) {
                if(DISTANCES[i][j] == )
            }
        }*/
    }

    public static int getCitiesNumber() {
        return Main.DISTANCES.length;
    }

    public static int[] getDistances(int index) {
        return Main.DISTANCES[index];
    }

    public static int getGenerations() {
        return Main.GENERATIONS;
    }

    public static int getChromosomes() {
        return Main.CHROMOSOMES;
    }

    public static int getMutationProb() {
        return Main.MUTATION_PROB;
    }

    public static int getCrossoverProb() {
        return Main.CROSSOVER_PROB;
    }

    public static int getCityChosen() {
        return Main.CITY_CHOSEN;
    }

    public static void debugCreate(String text) {
        if(Main.DEBUG_CREATE)
            System.out.println(text);
    }
    public static void debugCrossover(String text) {
        if(Main.DEBUG_CROSSOVER)
            System.out.println(text);
    }
    public static void debugEvaluate(String text) {
        if(Main.DEBUG_EVALUATE)
            System.out.println(text);
    }

}
