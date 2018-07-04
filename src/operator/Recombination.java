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
public interface Recombination
{
    // 5od kol etnen ganb ba3d e3melehom crossover
    public Individual[] xOver (Individual[] selected);

    /*
     * set the probabilty of crossover
     */
    public void setPc(double pc);

    /*
     * get the probabilty of crossover
     */
    public double getPc();
}
