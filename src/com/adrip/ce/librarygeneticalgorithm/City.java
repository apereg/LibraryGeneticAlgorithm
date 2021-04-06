package com.adrip.ce.librarygeneticalgorithm;

public class City {

    private final int id;

    private final int[] distances;

    public City(int id, int[] distances) {
        this.id = id;
        this.distances = distances;
    }

    public int getDistance(int idOtherCity) {
        if (this.id < idOtherCity)
            return distances[idOtherCity - 1];
        return distances[idOtherCity];
    }

}
