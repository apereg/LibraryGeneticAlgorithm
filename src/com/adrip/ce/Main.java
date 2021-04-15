package com.adrip.ce;

import com.adrip.ce.exceptions.ModifiableParameterException;
import com.adrip.ce.librarygeneticalgorithm.GeneticAlgorithm;
import com.adrip.ce.utils.Colors;
import com.adrip.ce.utils.Utils;

public class Main {

    /* PARAMETROS CONFIGURABLES */

    /* Ciudad elegida como nodo raiz A=0, B=1, C=2 ... */
    public static final int CITY_CHOSEN = 0;

    public static final int CITIES_NUMBER = 6;
    /* La matriz debe ser coherente (Distancias al resto de ciudades, tam NumCiudades*NumCiudades-1) */
    public static int[][] DISTANCES = {{5, 10, 20, Integer.MAX_VALUE, 10}, {5, 20, Integer.MAX_VALUE, 15, 1}, {10, 20, 10, 15, 50}, {20, Integer.MAX_VALUE, 10, 25, 20}, {Integer.MAX_VALUE, 15, 15, 25, 100}, {10, 1, 50, 20, 100}};

    public static final int GENERATIONS = 150;
    public static final int CHROMOSOMES = 10;

    /* Probabilidades (5 = 5%) */
    public static final int MUTATION_PROB = 5;
    public static final int CROSSOVER_PROB = 95;

    public static final boolean DEBUG_CREATE = false;
    public static final boolean DEBUG_CROSSOVER = false;
    /* Debido a que la evaluacion es un proceso a cargo de la libreria el debug solo muestra la operacion de calculo de distancia en cada ruta. */
    public static final boolean DEBUG_EVALUATE = false;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Main.checkParams();
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        algorithm.run();
        long time = System.currentTimeMillis() - start;
        System.out.println("\nEl algoritmo ha tardado " + Colors.GREEN + time + Colors.RESET + " ms.");
    }

    private static void checkParams() throws ModifiableParameterException {
        System.out.println(Colors.GREEN + "------------------------------------*------------------------------------");

        /* Se comprueban todos los parametros en busca de inconsistencias. */
        StringBuilder exceptions = new StringBuilder();
        /* Parametros generales */
        if (Main.CITIES_NUMBER < 4)
            exceptions.append("\tEl numero de ciudades debe ser al menos 4\n");
        exceptions.append(Main.checkDistances());
        if (Main.CITY_CHOSEN < 0 || Main.CITY_CHOSEN >= Main.CITIES_NUMBER)
            exceptions.append("\tLa ciudad de origen debe ser una ciudad posible [0-" + (Main.CITIES_NUMBER - 1) + "]");
        if (Main.CHROMOSOMES < 1)
            exceptions.append("\tEl numero de cromosomas debe ser al menos 1\n");
        if (Main.GENERATIONS < 1)
            exceptions.append("\tEl numero de generaciones debe ser al menos 1\n");
        /* Parametros de CROSSOVER */
        if (Main.CROSSOVER_PROB < 1 || Main.CROSSOVER_PROB > 100)
            exceptions.append("\tLa probabilidad de crossover debe estar en el intervalo [1-100]\n");
        /* Parametros de MUTACION */
        if (Main.MUTATION_PROB < 1 || Main.MUTATION_PROB > 100)
            exceptions.append("\tLa probabilidad de mutacion debe estar en el intervalo [1-100]\n");

        /* Si alguno de los parametros es inconsistente se muestra la información como excepcion. */
        if (exceptions.length() != 0)
            throw new ModifiableParameterException("Listado de fallos:\n" + exceptions);

        /* Si todos los parametros son consistentes se muestra la informacion basica de ejecucion. */
        System.out.println("Configuracion de ejecucion:");
        System.out.println("\tNumero de ciudades: " + Main.CITIES_NUMBER);
        System.out.println("\tCiudad elegida como raiz: " + Utils.getCityCode(Main.CITY_CHOSEN));
        System.out.println("\tNumero de cromosomas: " + Main.CHROMOSOMES);
        System.out.println("\tNúmero de generaciones: " + Main.GENERATIONS);
        System.out.println("\tProbabilidades: \n\t\tDe cruce: " + Main.CROSSOVER_PROB + "%\n\t\tDe mutacion: " + Main.MUTATION_PROB + "%");
        System.out.println("------------------------------------*------------------------------------" + Colors.RESET);
    }

    private static String checkDistances() {
        if (Main.DISTANCES.length != Main.CITIES_NUMBER || Main.DISTANCES[0].length != (Main.CITIES_NUMBER - 1))
            return "Las longitudes de la matriz de distancias no son coherentes, debe ser " + Main.CITIES_NUMBER + "x" + (Main.CITIES_NUMBER - 1);

        /* Se convierte la matriz a una cuadrada para poder trabajar con ella. */
        int[][] distances = new int[Main.CITIES_NUMBER][Main.CITIES_NUMBER];
        int iter;
        for (int i = 0; i < Main.CITIES_NUMBER; i++) {
            iter = 0;
            for (int j = 0; j < Main.CITIES_NUMBER; j++) {
                if (i == j) {
                    distances[i][j] = 0;
                } else {
                    distances[i][j] = Main.DISTANCES[i][iter];
                    iter++;
                }
            }
        }

        /* Comprobamos si es una matriz simetrica. */
        StringBuilder inconsistencies = new StringBuilder();
        for (int i = 1; i < distances.length; i++)
            for (int j = 0; j < i; j++)
                if (distances[i][j] != distances[j][i])
                    inconsistencies.append(Main.printDistancesError(i, j, distances[i][j], distances[j][i]));
        return inconsistencies.toString();
    }

    private static String printDistancesError(int i, int j, int value1, int value2) {
        StringBuilder error = new StringBuilder("\tError en las distancias: ");
        String iName = Utils.getCityCode(i);
        String jName = Utils.getCityCode(j);
        error.append("La distancia de ").append(iName).append(" a ").append(jName).append(" (").append(value1).append(") no concuerda con la distancia de ").append(jName).append(" a ").append(iName).append(" (").append(value2).append(")");
        return error.toString();
    }

    public static int getCitiesNumber() {
        return Main.CITIES_NUMBER;
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
        if (Main.DEBUG_CREATE)
            System.out.println(text);
    }

    public static void debugCrossover(String text) {
        if (Main.DEBUG_CROSSOVER)
            System.out.println(text);
    }

    public static void debugEvaluate(String text) {
        if (Main.DEBUG_EVALUATE)
            System.out.println(text);
    }

}
