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

    public static Conurbation getConurbation() {
        if (instance == null)
            instance = new Conurbation();
        return instance;
    }

    private Conurbation() {
        this.cities = new LinkedList<>();
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            this.cities.add(new City(i, Main.getDistances(i)));
    }

    public int tripLength(IChromosome trip) {

        int length = 0;

        List<String> chromosome = new LinkedList<>();
        for (int i = 0; i < trip.getGenes().length; i++) {
            chromosome.add(trip.getGene(i).getAllele().toString());
        }

        if (this.validTrip(chromosome)) {

            for (int i = 0; i < trip.size() - 1; i++) {

                if ((trip.getGene(i).getAllele().toString().equals(this.cities.get(0)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(1)))
                        || (trip.getGene(i).getAllele().toString().equals(this.cities.get(1)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(0)))) {
                    length = length + this.cities.get(0).getDistance(1);
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cities.get(0)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(2)))
                        || (trip.getGene(i).getAllele().toString().equals(this.cities.get(2)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(0)))) {
                    length = length + this.cities.get(0).getDistance(2);
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cities.get(0)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(3)))
                        || (trip.getGene(i).getAllele().toString().equals(this.cities.get(3)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(0)))) {
                    length = length + this.cities.get(0).getDistance(3);
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cities.get(1)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(2)))
                        || (trip.getGene(i).getAllele().toString().equals(this.cities.get(2)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(1)))) {
                    length = length + this.cities.get(1).getDistance(2);
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cities.get(1)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(3)))
                        || (trip.getGene(i).getAllele().toString().equals(this.cities.get(3)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(1)))) {
                    length = length + this.cities.get(1).getDistance(3);
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cities.get(2)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(3)))
                        || (trip.getGene(i).getAllele().toString().equals(this.cities.get(3)) && trip.getGene(i + 1).getAllele().toString().equals(this.cities.get(2)))) {
                    length = length + this.cities.get(2).getDistance(3);
                }
            }
        }

        return length;
    }

    public boolean validTrip(List<String> trip) {
        for (int i = 0; i < trip.size() - 1; i++)
            for (int j = i + 1; j < trip.size(); j++)
                if (trip.get(i).equals(trip.get(j)))
                    return false;
        return true;
    }

    public int maxValue() {
        return 20 + 40 + 10 + 5 + 15 + 10;
    }

    public City getCity(int id) {
        return this.cities.get(id);
    }

    public int getDistance(int cityA, int cityB) {
        return this.cities.get(cityA).getDistance(cityB);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Mapa ciudades:\n");
        str.append(Colors.RED+"A"+Colors.RESET+"----------------"+Colors.RED+"C"+Colors.RESET+"---------------"+Colors.RED+"E"+Colors.RESET+"\n");
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
        str.append(Colors.RED+"D"+Colors.RESET+"----------------"+Colors.RED+"D"+Colors.RESET+"---------------"+Colors.RED+"F"+Colors.RESET+"\n");
        str.append("\nDistancias:\n");
        for (int i = 0; i < Main.getCitiesNumber() - 1; i++)
            for (int j = 0; j < Main.getCitiesNumber(); j++)
                if (i < j)
                    str.append("\tDistancia ").append(Utils.getCityCode(i)).append(Utils.getCityCode(j)).append(": ").append(this.cities.get(i).getDistance(j)).append("\n");
        return str.deleteCharAt(str.length()-1).toString();
    }

}
