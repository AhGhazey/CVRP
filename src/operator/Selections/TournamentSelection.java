/**
 * @author Bernabe Dorronsoro
 *
 * Tournament selection operator 
 * 
 */


package operator.Selections;

import Population.Individual;
import java.util.Random;
import operator.Selection;

public class TournamentSelection implements Selection
{
   private Random r;
   
   public TournamentSelection(Random r)
   {
      this.r = r;
   }
   
   // Parametro array de individuos, devuelve individuo
   public Individual getSelected(Individual[] neighbors) {
       
      Individual  ind1, ind2;

      ind1 = neighbors[r.nextInt(neighbors.length)];
      ind2 = neighbors[r.nextInt(neighbors.length)];
      
      if (ind1.getFitness()<= ind2.getFitness())
         return ind1;
      else
         return ind2;
    }
}
