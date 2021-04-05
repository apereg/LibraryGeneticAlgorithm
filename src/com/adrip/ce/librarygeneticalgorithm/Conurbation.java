package com.adrip.ce.librarygeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.utils.Colors;
import com.adrip.ce.utils.Utils;
import org.jgap.IChromosome;

import java.util.LinkedList;
import java.util.List;

public class Conurbation {

    private static Conurbation instance;

    private final List<City> cities;

    private final City originCity;

    public static Conurbation getConurbation() {
        if (instance == null)
            instance = new Conurbation();
        return instance;
    }

    private Conurbation() {
        this.cities = new LinkedList<>();
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            this.cities.add(new City(i, Main.getDistances(i)));
        this.originCity = this.cities.get(Main.getCityChosen());
    }

    public int routeLength(IChromosome chromosome) {
        if (this.isValidRoute(chromosome)) {
            int length = 0;
            int[] route = Utils.getChromosomeAsArray(chromosome);
            for (int i = 0; i < Main.getCitiesNumber() - 1; i++) {
                int distance = cities.get(route[i]).getDistance(route[i + 1]);
                if (distance == Integer.MAX_VALUE)
                    return Integer.MAX_VALUE;
                length += distance;
            }

            int finalDistance = cities.get(route[Main.getCitiesNumber() - 1]).getDistance(route[0]);
            if (finalDistance == Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            length += finalDistance;
            return length;
        }
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

    public int maxValue() {
        int max = 0;
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            for (int j = 0; j < Main.getCitiesNumber(); j++)
                if (i < j)
                    max += this.cities.get(i).getDistance(j);
        return max;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Mapa ciudades:\n");
        str.append(Colors.RED + "A" + Colors.RESET + "----------------" + Colors.RED + "C" + Colors.RESET + "---------------" + Colors.RED + "E" + Colors.RESET + "\n");
        str.append("|\\              /|\\             /|\n");
        str.append("| \\            / | \\           / |\n");
        str.append("|  \\          /  |  \\         /  |\n");
        str.append("|   \\        /   |   \\       /   |\n");
        str.append("|    \\      /    |    \\     /    |\n");
        str.append("|     \\    /     |     \\   /     |\n");
        str.append("|      \\  /      |      \\ /      |\n");
        str.append("|       \\/       |       \\       |\n");
        str.append("|       /\\       |      / \\      |\n");
        str.append("|      /  \\      |     /   \\     |\n");
        str.append("|     /    \\     |    /     \\    |\n");
        str.append("|    /      \\    |   /       \\   |\n");
        str.append("|   /        \\   |  /         \\  |\n");
        str.append("|  /          \\  | /           \\ |\n");
        str.append("| /            \\ |/             \\|\n");
        str.append(Colors.RED + "D" + Colors.RESET + "----------------" + Colors.RED + "D" + Colors.RESET + "---------------" + Colors.RED + "F" + Colors.RESET + "\n");
        str.append("\nDistancias:\n");
        for (int i = 0; i < Main.getCitiesNumber() - 1; i++)
            for (int j = 0; j < Main.getCitiesNumber(); j++)
                if (i < j)
                    str.append("\tDistancia ").append(Utils.getCityCode(i)).append(Utils.getCityCode(j)).append(": ").append(((this.cities.get(i).getDistance(j) == Integer.MAX_VALUE) ? "inf" : this.cities.get(i).getDistance(j))).append("\n");
        return str.deleteCharAt(str.length() - 1).toString();
    }

}
