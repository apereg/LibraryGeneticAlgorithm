package com.adrip.ce.librarygeneticalgorithm;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;


public class Evaluation extends FitnessFunction {

    public double evaluate(IChromosome chromosome) {
        Conurbation conurbation = Conurbation.getConurbation();
        return conurbation.maxValue() - conurbation.tripLength(chromosome);
    }
}
