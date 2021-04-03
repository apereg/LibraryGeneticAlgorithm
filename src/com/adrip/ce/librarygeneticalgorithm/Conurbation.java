package com.adrip.ce.librarygeneticalgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.adrip.ce.Main;
import org.jgap.*;

public class Conurbation {

    private static Conurbation instance;

    private List<City> cities;

    public static Conurbation getConurbation() {
        if(instance == null)
            instance = new Conurbation();
        return instance;
    }

    private Conurbation() {
        this.cities = new LinkedList<>();
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            this.cities.add(new City(i, Main.getDistances(i)));

        System.out.println(this.cities);
    }

    public String printMap() {

        String map = "    "+this.cities.get(1).getDistance(0)+"\n"
                + "  A————B\n"
                + "  |\\10/|\n"
                + "40| \\/5|15\n"
                + "  | /\\ |\n"
                + "  C————D\n"
                + "    10";

        return map;
    }

    public int tripLength(IChromosome trip) {

        int length = 0;

        ArrayList<String> chromosome = new ArrayList<String>();
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

    public boolean validTrip(ArrayList<String> trip) {
        for (int i = 0; i < trip.size() - 1; i++)
            for (int j = i + 1; j < trip.size(); j++)
                if (trip.get(i).equals(trip.get(j)))
                    return false;
        return true;
    }

    public int maxValue(){
        return 20 + 40 + 10 + 5 + 15 + 10;
    }

    public City getCity(int id){
        return this.cities.get(id);
    }

    public int getDistance(int cityA, int cityB){
        return this.cities.get(cityA).getDistance(cityB);
    }



}
