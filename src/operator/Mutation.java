/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator;

import Population.Individual;

/**
 *
 * @author Mostafa
 */
public interface Mutation {

    /*
     * mutate the offsprings in the parameters
     */
    public void mutate(Individual[] offsprings);

    /*
     * set the probabilty of mutation
     */
    public void setPm(double pm);

    /*
     * get the probabilty of mutation
     */
    public double getPm();
}
