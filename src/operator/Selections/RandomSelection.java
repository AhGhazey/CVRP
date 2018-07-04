
/**
 * @author Bernabe Dorronsoro
 *
 * Random selection operator. Randomly selects one individual from the neighborhood
 * 
 */
   
package operator.Selections;   

import Population.Individual;
import java.util.Random;
import operator.Selection;

public class RandomSelection implements Selection
{
   private Random r;
   
   public RandomSelection(Random r)
   {
      this.r = r;
   }

    public Individual getSelected(Individual[] neighbors) {
        return neighbors[r.nextInt(neighbors.length)];
    }
}
