package com.adrip.ce.librarygeneticalgorithm;

import java.util.Arrays;

public class City {

    private int id;

    private int[] distances;

    public City(int id, int[] distances){
        this.id = id;
        this.distances = distances;
    }

    public int getDistance(int idOtherCity) {
        if(this.id < idOtherCity)
            return distances[idOtherCity-1];
        return distances[idOtherCity];
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", distances=" + Arrays.toString(distances) +
                '}';
    }
}
