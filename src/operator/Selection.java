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
public interface Selection
{
    // ruletwheel , ranking
    public Individual getSelected(Individual[] neighbors);
}
