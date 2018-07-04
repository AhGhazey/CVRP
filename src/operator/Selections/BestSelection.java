/**
 * @author Bernabe Dorronsoro
 *
 * Best selection operator. It returns the best individual in the neighborhood
 * 
 */
   
package operator.Selections;

import Population.Individual;
import operator.Selection;


public class BestSelection implements Selection
{
   // Asume como parametro array de Individuos
   // Devuelve individuo
  
    public Individual getSelected(Individual[] neighbors) {

        Individual ind = neighbors[0];

        for (int i=1; i<neighbors.length; i++)
            if (neighbors[i].getFitness()<= ind.getFitness())
                ind = neighbors[i];

        return ind;
    }
}
