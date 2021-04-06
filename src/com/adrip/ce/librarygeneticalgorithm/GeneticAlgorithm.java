package com.adrip.ce.librarygeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.utils.Colors;
import com.adrip.ce.utils.Utils;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.SwappingMutationOperator;

import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithm {

    private static Configuration configuration;

    public GeneticAlgorithm() {
        /* Se crea el mapa de la ciudad. */
        Conurbation conurbation = Conurbation.getConurbation();
        System.out.println(conurbation.toString() + "\n");
    }

    public void run() throws InvalidConfigurationException {
        System.out.println(Colors.RED + "Comienza el algoritmo genetico" + Colors.RESET);

        /* Se crea la configuracion heredada de la por defecto. */
        configuration = new DefaultConfiguration();
        configuration.getGeneticOperators().clear();
        /* Se marca el tamaño de la poblacion. */
        configuration.setPopulationSize(Main.getChromosomes() - 1);
        configuration.setKeepPopulationSizeConstant(true);
        /* Se asignan la evaluacion y crossover a funciones dedicadas. */
        configuration.setFitnessFunction(new Evaluation());
        configuration.addGeneticOperator(new Crossover(configuration));
        /* Se marca el tipo de mutacion. */
        configuration.addGeneticOperator(new SwappingMutationOperator(configuration, Main.getMutationProb()));
        /* Se marca un cromosoma de ejemplo para la configuracion. */
        IChromosome chromosome = new Chromosome(configuration, this.createGeneChain());
        configuration.setSampleChromosome(chromosome);

        /* Se crean todos los cromosomas con un metodo externo para crear la poblacion inicial. */
        IChromosome[] population = new IChromosome[Main.getChromosomes()];
        Main.debugCreate("------------------------------------*------------------------------------");
        Main.debugCreate("Se va a crear la poblacion inicial con valores al azar de 1 al numero de ciudades (" + Main.getCitiesNumber() + ")");
        Main.debugCreate("\tRealmente por motivos de estructuras de datos en java se decrementa este conjunto a [0-" + (Main.getCitiesNumber() - 1) + "]\n");
        Main.debugCreate("Se van a crear " + Main.getChromosomes() + " cromosomas");
        for (int i = 0; i < Main.getChromosomes(); i++) {
            population[i] = new Chromosome(configuration, this.createGeneChain());
            Main.debugCrossover("\tCromosoma " + (i + 1) + ":\t" + Utils.getChromosomeToIntString(population[i]) + " (" + Utils.getChromosomeToString(population[i]) + ")");
        }
        Main.debugCreate("Creada con exito la poblacion inicial");
        Main.debugCreate("------------------------------------*------------------------------------\n");

        /* Se crea el algoritmo genetico con la poblacion generada y se evoluciona el numero de generaciones marcada. */
        Genotype genotype = new Genotype(configuration, new Population(configuration, population));
        genotype.evolve(Main.getGenerations());

        /* Se obtiene el mejor cromosoma. */
        IChromosome bestChromosome = genotype.getFittestChromosome();

        /* Se imprime la informacion de este cromosoma. */
        System.out.println(Colors.RED + "Acaba el algoritmo genetico tras " + Main.getGenerations() + " generaciones.\n" + Colors.RESET);
        System.out.println("La ruta mas optima es " + Utils.getChromosomeToString(bestChromosome) + " (Distancia total: " + (Integer.MAX_VALUE - bestChromosome.getFitnessValue()) + ")");
    }

    private Gene[] createGeneChain() throws InvalidConfigurationException {
        List<Integer> chromosome = new LinkedList<>();
        Gene[] geneChain = new Gene[Main.getCitiesNumber()];

        /* Se añade como primer gen del cromosoma la ciudad elegida como origen. */
        chromosome.add(Main.getCityChosen());
        geneChain[0] = new IntegerGene(configuration, 0, Main.getCitiesNumber() - 1);
        geneChain[0].setAllele(chromosome.get(0));

        boolean valid;
        int random;
        for (int i = 1; i < Main.getCitiesNumber(); i++) {
            valid = false;
            /* Se generan valores aleatorios hasta obtener las permutaciones del resto de ciudades. */
            while (!valid) {
                random = Utils.generateRandom(0, Main.getCitiesNumber() - 1);
                if (!chromosome.contains(random)) {
                    chromosome.add(random);
                    geneChain[i] = new IntegerGene(configuration, 0, Main.getCitiesNumber() - 1);
                    geneChain[i].setAllele(random);
                    valid = true;
                }
            }
        }
        return geneChain;
    }

    public static Gene[] createGeneChain(List<Integer> chromosomeList) throws InvalidConfigurationException {
        Gene[] geneChain = new Gene[Main.getCitiesNumber()];
        for (int i = 0; i < geneChain.length; i++) {
            geneChain[i] = new IntegerGene(GeneticAlgorithm.configuration, 0, Main.getCitiesNumber() - 1);
            geneChain[i].setAllele(chromosomeList.get(i));
        }
        return geneChain;
    }

}

class Evaluation extends FitnessFunction {

    public double evaluate(IChromosome chromosome) {
        /* Como las opciones de JGAP proporcionan maximizacion de aptitud utilizamos una funcion inversora para minimizar la distancia. */
        return Integer.MAX_VALUE - Conurbation.getConurbation().routeLength(chromosome);
    }

}

class Crossover extends BaseGeneticOperator {


    public Crossover(Configuration c) throws InvalidConfigurationException {
        super(c);
    }

    public void operate(Population population, List chromosomes) {
        Main.debugCrossover("------------------------------------*------------------------------------");
        Main.debugCrossover("Va a comenzar el cruce de la poblacion");
        Main.debugCrossover("El metodo escogido es el usado en los apuntes del aula virtual:");
        Main.debugCrossover("\tSe selecciona un intervalo del primer cromosoma");
        Main.debugCrossover("\tSe introduce en el segundo cromosoma respetando el orden relativo");
        Main.debugCrossover("Probabilidad de crossover: " + Main.getCrossoverProb() + "%");
        IChromosome c1, c2;
        for (int i = 0; i < Main.getChromosomes(); i += 2) {
            if ((i + 1) == population.size())
                break;
            Main.debugCrossover("\nCruzando cromosomas " + (i + 1) + " y " + (i + 2));
            if (Utils.generateRandom(0, 99) < Main.getCrossoverProb()) {
                /* Se cruzan de dos en dos. */
                c1 = population.getChromosome(i);
                c2 = population.getChromosome(i + 1);
                /* Se añaden a la lista de candidatos para la seleccion. */
                try {
                    chromosomes.add(geneCrossover(c1, c2));
                    chromosomes.add(geneCrossover(c2, c1));
                } catch (InvalidConfigurationException e) {
                    System.out.println("Error en el cruce");
                }
            } else {
                Main.debugCrossover("No se cruzan por probabilidad de cruzamiento");
            }
        }
        Main.debugCrossover("------------------------------------*------------------------------------\n");
    }

    public IChromosome geneCrossover(IChromosome chromosome1, IChromosome chromosome2) throws InvalidConfigurationException {
        /* Para cruzar se seleccionan dos puntos de corte y se cambia el orden con el segmento generado. */
        int crossoverPoint1 = Utils.generateRandom(0, Main.getCitiesNumber() - 1);
        int crossoverPoint2 = Utils.generateRandom(crossoverPoint1, Main.getCitiesNumber() - 1);
        List<Integer> segment = new LinkedList<>();
        for (int i = crossoverPoint1; i <= crossoverPoint2; i++)
            segment.add((Integer) chromosome1.getGene(i).getAllele());

        List<Integer> chromosome2List = Utils.getChromosomeAsList(chromosome2);
        List<Integer> sonList = new LinkedList<>();
        Main.debugCrossover("Se van a hacer el hijo de " + Utils.getChromosomeToString(chromosome1) + " y " + Utils.getChromosomeToString(chromosome2));
        Main.debugCrossover("Intervalo de cruce [" + (crossoverPoint1 + 1) + ", " + (crossoverPoint2 + 1) + "]");
        /* Se completa con genes de c2 no incluidos en el segmento de c1 hasta la posicion en la que insertar el segmento. */
        int solutionIter = 0;
        while (sonList.size() < crossoverPoint1 && solutionIter < Main.getCitiesNumber()) {
            if (!segment.contains(chromosome2List.get(solutionIter))) {
                sonList.add(chromosome2List.get(solutionIter));
            }
            solutionIter++;
        }

        /* Se añade el segmento extraido de c1. */
        sonList.addAll(segment);

        /* Se completa con los genes restantes de c2. */
        solutionIter = 0;
        while (sonList.size() != Main.getCitiesNumber() && solutionIter < Main.getCitiesNumber()) {
            if (!sonList.contains(chromosome2List.get(solutionIter))) {
                sonList.add(chromosome2List.get(solutionIter));
            }
            solutionIter++;
        }
        Main.debugCrossover("Cromosoma hijo: " + Utils.getChromosomeToString(sonList));


        /* Se convierte el hijo de lista de enteros a objeto cromosoma (clonado del primer cromosoma para obtener la configuracion del algoritmo). */
        IChromosome son = (IChromosome) chromosome1.clone();
        son.setGenes(GeneticAlgorithm.createGeneChain(sonList));
        return son;
    }

    @Override
    public int compareTo(Object other) {
        return 0;
    }

}