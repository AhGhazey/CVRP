/**
 * @author Sergio Romero
 *
 * Center selection operator. It returns the individual in the center of the neighborhood
 * 
 */
   
package operator.Selections;

import Population.Individual;

import operator.Selection;

public class CenterSelection implements Selection
{
	
   // The input parameter is an array of Individual
   // Returns the location of the center individual in the list (it is always the first one)

    public Individual getSelected(Individual[] neighbors) {
        return neighbors[0];
    }
}
