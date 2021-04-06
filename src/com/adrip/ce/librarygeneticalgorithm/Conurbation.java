package com.adrip.ce.librarygeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.utils.Utils;
import org.jgap.IChromosome;

import java.util.LinkedList;
import java.util.List;

public class Conurbation {

    private static Conurbation instance;

    private final List<City> cities;

    private final City originCity;

    public static Conurbation getConurbation() {
        /* Clase de instancia unica o Singleton. */
        if (instance == null)
            instance = new Conurbation();
        return instance;
    }

    private Conurbation() {
        /* Se rellena la lista de ciudades con su id y distancias. */
        this.cities = new LinkedList<>();
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            this.cities.add(new City(i, Main.getDistances(i)));
        /* Se marca la ciudad origen para el problema del viajante. */
        this.originCity = this.cities.get(Main.getCityChosen());
    }

    public int routeLength(IChromosome chromosome) {
        /* Solo se comprueba si la ruta es valida, si no distancia infinita. */
        Main.debugEvaluate("------------------------------------*------------------------------------");
        Main.debugEvaluate("Se va a evaluar el cromosoma " + Utils.getChromosomeToString(chromosome));
        if (this.isValidRoute(chromosome)) {
            int length = 0;
            int[] route = Utils.getChromosomeAsArray(chromosome);
            /* Se comprueba la distancia entre las ciudades del cromosoma y si no es infinita se va sumando. */
            for (int i = 0; i < Main.getCitiesNumber() - 1; i++) {
                int distance = cities.get(route[i]).getDistance(route[i + 1]);
                Main.debugEvaluate("La distancia entre " + Utils.getCityCode(route[i]) + " y " + Utils.getCityCode(route[i + 1]) + " es " + ((distance == Integer.MAX_VALUE) ? "infinita" : distance));
                if (distance == Integer.MAX_VALUE) {
                    /* Si alguna distancia es infinita se devuelve infinito. */
                    Main.debugEvaluate("Distancia total = infinita");
                    Main.debugEvaluate("------------------------------------*------------------------------------\n");
                    return Integer.MAX_VALUE;
                }
                length += distance;
            }

            /* Se realiza la misma comprobacion desde la ultima ciudad del cromosoma al inicio. */
            int finalDistance = cities.get(route[Main.getCitiesNumber() - 1]).getDistance(route[0]);
            Main.debugEvaluate("La distancia entre " + Utils.getCityCode(route[Main.getCitiesNumber() - 1]) + " y " + Utils.getCityCode(route[0]) + " es " + ((finalDistance == Integer.MAX_VALUE) ? "infinita" : finalDistance));
            if (finalDistance == Integer.MAX_VALUE) {
                Main.debugEvaluate("Distancia total = infinita");
                Main.debugEvaluate("------------------------------------*------------------------------------\n");
                return Integer.MAX_VALUE;
            }

            length += finalDistance;
            Main.debugEvaluate("Distancia total = " + length);
            Main.debugEvaluate("------------------------------------*------------------------------------\n");
            return length;
        }
        Main.debugEvaluate("No es una ruta valida, distancia infinita.");
        Main.debugEvaluate("------------------------------------*------------------------------------\n");
        return Integer.MAX_VALUE;
    }

    public boolean isValidRoute(IChromosome chromosome) {
        /* Se convierten los genes del cromosoma a un array de enteros para poder operar. */
        int[] route = Utils.getChromosomeAsArray(chromosome);

        /* Se compara cada gen con los posteriores buscandose a si mismo. */
        for (int i = 0; i < route.length - 1; i++)
            for (int j = i + 1; j < route.length; j++)
                if (route[i] == route[j])
                    return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Mapa ciudades (Distancias):\n");
        for (int i = 0; i < Main.getCitiesNumber() - 1; i++)
            for (int j = 0; j < Main.getCitiesNumber(); j++)
                if (i < j)
                    str.append("\tDistancia ").append(Utils.getCityCode(i)).append("-").append(Utils.getCityCode(j)).append(": ").append(((this.cities.get(i).getDistance(j) == Integer.MAX_VALUE) ? "infinita" : this.cities.get(i).getDistance(j))).append("\n");
        return str.deleteCharAt(str.length() - 1).toString();
    }

}
