package com.adrip.ce;

import com.adrip.ce.librarygeneticalgorithm.GeneticAlgorithm;

public class Main {

    public static final int GENERATIONS = 10;
    public static final int CHROMOSOMES = 10;
    public static int[][] DISTANCES = {{20, 40, 10, 0, 0}, {20, 5, 15, 0, 0}, {40, 5, 10, 0, 0}, {10, 15, 10, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};

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
}
