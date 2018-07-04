/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator;

import Population.Individual;
import Population.Population;

/**
 *
 * @author Mostafa
 */
public interface Replacement {

    void replace(Individual[] offsprings,Individual oldone,Population temp);
}
