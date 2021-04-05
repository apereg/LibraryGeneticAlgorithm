package com.adrip.ce.librarygeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.utils.Utils;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.SwappingMutationOperator;

import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithm {

    private Configuration configuration;

    public GeneticAlgorithm() {
        /* Se crea el mapa de la ciudad. */
        Conurbation conurbation = Conurbation.getConurbation();
        System.out.println(conurbation.toString() + "\n");
    }

    public void run() throws InvalidConfigurationException {
        System.out.println("Comienza el algoritmo genetico");

        /* Se crea la configuracion heredada de la por defecto. */
        this.configuration = new DefaultConfiguration();
        this.configuration.getGeneticOperators().clear();
        /* Se marca el tamaño de la poblacion. */
        this.configuration.setPopulationSize(Main.getChromosomes());
        this.configuration.setKeepPopulationSizeConstant(true);
        /* Se asignan la evaluacion y crossover a funciones dedicadas. */
        this.configuration.setFitnessFunction(new Evaluation());
        this.configuration.addGeneticOperator(new Crossover(this.configuration));
        /* Se marca el tipo de mutacion. */
        this.configuration.addGeneticOperator(new SwappingMutationOperator(this.configuration, 5));
        /* Se marca un cromosoma de ejemplo para la configuracion. */
        IChromosome chromosome = new Chromosome(this.configuration, this.createGeneChain());
        this.configuration.setSampleChromosome(chromosome);

        /* Se crean todos los cromosomas con un metodo externo para crear la poblacion inicial. */
        IChromosome[] population = new IChromosome[Main.getChromosomes()];
        for (int i = 0; i < Main.getChromosomes(); i++)
            population[i] = new Chromosome(this.configuration, this.createGeneChain());

        /* Se crea el algoritmo genetico con la poblacion generada y se evoluciona el numero de generaciones marcada. */
        Genotype genotype = new Genotype(this.configuration, new Population(this.configuration, population));
        genotype.evolve(Main.getGenerations());

        /* Se obtiene el mejor cromosoma. */
        IChromosome bestChromosome = genotype.getFittestChromosome();

        /* Se convierte la cadena de genes del mejor cromosoma a las letras. */
        StringBuilder bestGeneChain = new StringBuilder();
        for (int i = 0; i < Main.getCitiesNumber(); i++)
            bestGeneChain.append(Utils.getCityCode((Integer) bestChromosome.getGene(i).getAllele()));

        /* Se imprime la informacion de este cromosoma. */
        System.out.println("Acaba el algoritmo genetico tras " + Main.getGenerations() + " generaciones.");
        System.out.println("El mejor cromosoma es " + bestGeneChain.toString() + " (Distancia total: " + (Integer.MAX_VALUE - bestChromosome.getFitnessValue()) + ")");
    }

    private Gene[] createGeneChain() throws InvalidConfigurationException {
        List<Integer> chromosome = new LinkedList<>();
        Gene[] geneChain = new Gene[Main.getCitiesNumber()];

        /* Se añade como primer gen del cromosoma la ciudad elegida como origen. */
        chromosome.add(Main.getCityChosen());
        geneChain[0] = new IntegerGene(this.configuration, 0, Main.getCitiesNumber() - 1);
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
                    geneChain[i] = new IntegerGene(this.configuration, 0, Main.getCitiesNumber() - 1);
                    geneChain[i].setAllele(random);
                    valid = true;
                }
            }
        }
        return geneChain;
    }

}

class Evaluation extends FitnessFunction {

    public double evaluate(IChromosome chromosome) {
        return Integer.MAX_VALUE - Conurbation.getConurbation().routeLength(chromosome);
    }

}

class Crossover extends BaseGeneticOperator {

    private int startOffset = 1;

    public Crossover(Configuration c) throws InvalidConfigurationException {
        super(c);
    }

    public void operate(final Population a_population,
                        final List a_candidateChromosomes) {
        int size = Math.min(getConfiguration().getPopulationSize(),
                a_population.size());
        int numCrossovers = (2 * size) / 3;
        RandomGenerator generator = getConfiguration().getRandomGenerator();
        // For each crossover, grab two random chromosomes and do what
        // Grefenstette et al say.
        // --------------------------------------------------------------
        for (int i = 0; i < numCrossovers; i++) {
            IChromosome origChrom1 = a_population.getChromosome(generator.
                    nextInt(size));
            IChromosome firstMate = (IChromosome) origChrom1.clone();
            IChromosome origChrom2 = a_population.getChromosome(generator.
                    nextInt(size));
            IChromosome secondMate = (IChromosome) origChrom2.clone();
            // In case monitoring is active, support it.
            // -----------------------------------------
            if (m_monitorActive) {
                firstMate.setUniqueIDTemplate(origChrom1.getUniqueID(), 1);
                firstMate.setUniqueIDTemplate(origChrom2.getUniqueID(), 2);
                secondMate.setUniqueIDTemplate(origChrom1.getUniqueID(), 1);
                secondMate.setUniqueIDTemplate(origChrom2.getUniqueID(), 2);
            }

            geneCrossover(firstMate, secondMate);
            // Add the modified chromosomes to the candidate pool so that
            // they'll be considered for natural selection during the next
            // phase of evolution.
            // -----------------------------------------------------------
            a_candidateChromosomes.add(firstMate);
            a_candidateChromosomes.add(secondMate);
        }
    }

    public void geneCrossover(IChromosome chromosome1, IChromosome chromosome2) {
        //try {
        /* Para cruzar se selecciona un punto de corte y se cambia el orden con el del siguiente cromosoma. */
        Gene[] aux = new Gene[Main.getCitiesNumber()];
        int crossoverPoint = Utils.generateRandom(0, (Main.getCitiesNumber() - 1));
        //TODO el crossover de los cojones
        //} catch (InvalidConfigurationException ignored) {}
    }

    public void setStartOffset(int offset) {
        this.startOffset = offset;
    }

    public int getStartOffset() {
        return this.startOffset;
    }

    /**
     * Compares the given GeneticOperator to this GeneticOperator.
     *
     * @param a_other the instance against which to compare this instance
     * @return a negative number if this instance is "less than" the given
     * instance, zero if they are equal to each other, and a positive number if
     * this is "greater than" the given instance
     * @author Klaus Meffert
     * @since 2.6
     */
    public int compareTo(final Object a_other) {
        if (a_other == null) {
            return 1;
        }
        Crossover op = (Crossover) a_other;
        // start offset less, meaning more to do --> return 1 for "is greater than"
        // Everything is equal. Return zero.
        // ---------------------------------
        return Integer.compare(op.getStartOffset(), getStartOffset());
    }
}