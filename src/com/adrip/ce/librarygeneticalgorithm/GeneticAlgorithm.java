package com.adrip.ce.librarygeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.utils.Utils;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.StringGene;
import org.jgap.impl.SwappingMutationOperator;

import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithm {

    public GeneticAlgorithm() {

    }

    public void run() throws InvalidConfigurationException {

        Conurbation conurbation = Conurbation.getConurbation();
        System.out.println(conurbation.toString() + "\n");
        System.out.println("Comienza el algoritmo genetico");
        Configuration configuration = new DefaultConfiguration();

        int populationSize = Main.getChromosomes();



        StringBuilder str = new StringBuilder();
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            str.append(i);
        String validAlphabet = str.toString();

        List<Gene> genes = new LinkedList<>();
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            genes.add(new StringGene(configuration, 0, 1, validAlphabet));

        IChromosome chromosome = new Chromosome(configuration, genes.toArray(new Gene[0]));
        configuration.getGeneticOperators().clear();
        configuration.setSampleChromosome(chromosome);
        configuration.setPopulationSize(Main.getChromosomes());
        configuration.setFitnessFunction(new Evaluation());
        configuration.addGeneticOperator(new MyGreedyCrossover(configuration));
        configuration.addGeneticOperator(new SwappingMutationOperator(configuration, 5));
        configuration.setKeepPopulationSizeConstant(true);

        IChromosome[] population = new IChromosome[populationSize];
        for (int i = 0; i < populationSize; i++) {

            Gene[] newGenes = new Gene[6];
            RandomGenerator generator = configuration.getRandomGenerator();
            List<String> aux = new LinkedList<>();
            for (int j = 0; j < genes.size(); j++) {
                newGenes[j] = genes.get(j).newGene();
                j = getJ(conurbation, newGenes, generator, aux, j);
            }

            population[i] = new Chromosome(configuration, newGenes);

        }

        Genotype genotype = new Genotype(configuration, new Population(configuration, population));

        genotype.evolve(Main.getGenerations());
        IChromosome fittest = genotype.getFittestChromosome();
        StringBuilder geneChain = new StringBuilder();
        for (int i = 0; i < Main.getCitiesNumber(); i++) {
            geneChain.append(Utils.getCityCode(Integer.parseInt(fittest.getGene(i).getAllele().toString())));
        }

        System.out.println("Fittest Chromosome is " +geneChain.toString() + " with value " + fittest.getFitnessValue());
    }

    static int getJ(Conurbation conurbation, Gene[] newGenes, RandomGenerator generator, List<String> aux, int j) {
        newGenes[j].setToRandomValue(generator);
        aux.add(newGenes[j].getAllele().toString());

        if (newGenes[j].getAllele().toString().equals("") || !conurbation.validTrip(aux)) {
            newGenes[j].setToRandomValue(generator);
            aux.remove(j);
            j = j - 1;
        }
        return j;
    }
}
