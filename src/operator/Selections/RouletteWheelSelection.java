
/**
 * @author Sergio Romero
 *
 * Roulette wheel selection operator. 
 * 
 */

package operator.Selections;
import Population.Individual;
import java.util.Random;
import operator.Selection;

public class RouletteWheelSelection implements Selection
{
   private Random r;
   
   public RouletteWheelSelection(Random r)
   {
      this.r = r;
   }
   
   public Individual getSelected(Individual[] neighbors)
   {
	   Individual iv[] = neighbors;
      double sumFit, acFit, randValue;
      int i;
      
      sumFit = 0.0;
      for (i=0; i<iv.length; i++)
         sumFit += ((Double)iv[i].getFitness()).doubleValue(); //Fitness total
      
      i = 0;

      //this part for maximaization
     /* if (Target.maximize)
      {
      	acFit = ((Double)iv[i].getFitness()).doubleValue() / sumFit;
        randValue = r.nextDouble();
        while ((acFit < randValue) && (i<iv.length))
        {
           i++;
           acFit += ((Double)iv[i].getFitness()).doubleValue() / sumFit;
        }
      }
      else*/
      {
      	acFit = 1.0 - (((Double)iv[i].getFitness()).doubleValue() / sumFit);
        randValue = r.nextDouble();
        while ((acFit > randValue) && (i<iv.length))
        {
           i++;
           acFit -= ((Double)iv[i].getFitness()).doubleValue() / sumFit;
        }
      }
      
      if (i==iv.length) return neighbors[r.nextInt(iv.length)];
      return  neighbors[i];
   }
    
}
