/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CGA;

import Population.Individual;
import Population.Population;
import java.util.Vector;

/**
 *
 * @author Mostafa
 */
public interface Problem
{
    /*
     * get the current instance used , and create new instance if there is no instance before
     */
    //public static Problem getCurrentInstance();

    /*
     * compare between ind1 and ind2
     * return 1 if ind1 > ind2
     * return -1 if ind1 < ind2
     * return 0 if ind1 = ind2
     */
    public int compare(Individual ind1 , Individual ind2);

    /*
     * evaluate the fitness for one individual
     * set the value of fitness to individual (setFitness) *********important*******
     */
    public double fitness(Individual ind);

    /* evaluate current population , and update the fitness of each individual
     */
    public void evaluatePop();

    /*
     *evaluate the array of individuals which take in parameter ,  and update the fitness of each individual
     */
    public void evaluatePop(Individual[]inds);

    /*
     * return the copy(clone) of gene domain
     */
    public Vector<Integer> getGeneDomain();

    public int getnCustomer();
    public int getnVehicle() ;
    public double [][]getCost();

    public double[] getDemand();
    
    public double Overcap();

    public double OverTime();



    /*
     * return the current population
     */

    public Population getPop();

    /*
     * set updated population to the problem
     */
    public void setPop(Population pop);

    /*
     * using CellUpdate , reset the order of visiting individuals
     */
    public void resetUpdate();

    /*
     *using CellUpdate , check if there is another individuals or not
     */
    public boolean hasSoluations();

    /*
     * using CellUpdate
     * return the next solution of specific order
     */
    public Individual getNextSolution();

    /*
     * using neighborhood
     * return array of neighbors of individual
     */
    public Individual[] getNeighbors(Individual ind);

    /*
     * using selection
     * return the array of selected individuals of neighbors
     */
    public Individual[] getSelected(Individual[] neighbors);

    /*
     * using Recombination
     * parameters : array of selected individuals , the size is even , each two adjacent individual make crossover
     * return : array offsprings
     */
    public Individual[] recombination(Individual[] selected);

    /*
     * using Mutation
     * mutate the offsprings in the parameters
     */
    public void mutation(Individual[] offspring);

    /*
     * make local search for offpsrings
     */
    public void localSearch(Individual[] offspring);

    /*
     * compare between offsprings and center individual , and put the result in replaced population
     * with the location of center individual
     */
    public void replace(Individual[] offspring , Individual centerInd , Population replacedPop);

    /*
     * determine when is the algorithm Stop!
     * parameter: number of current generation
     * return true to continue
     * return false to Stop
     */
    public boolean stop(int curGeneration);

    /*
     * 
     */
    public Individual getBestSolution();
    

    public int getCurrentGenration();

     public int getEvaluationLimit();

}
