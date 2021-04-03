package com.adrip.ce.librarygeneticalgorithm;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;


public class MyFitnessFunction
        extends FitnessFunction {

    public double evaluate(IChromosome chromosome) {
        Map map = new Map();
        return map.maxValue() - map.tripLength(chromosome);
    }
}
