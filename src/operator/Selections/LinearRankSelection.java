package operator.Selections;


/**
 * @author Sergio Romero
 *
 * Modified by Bernabe Dorronsoro
 *
 * Linear ranking selection operator. It returns an individual from the neighborhood
 * chosen by linear ranking using: Min + (Max - Min)*rank/(n-1) with Max=2 and Min=0
 * 
 */

import Population.Individual;
import java.util.*;
import operator.Selection;

public class LinearRankSelection implements Selection
{
   private Random r;
   
   public LinearRankSelection(Random r)
   {
      this.r = r;
   }
   
   // The parameter is an array of individuals, and it returns only one of them, selected by Linear Ranking
   public Individual getSelected(Individual[] neighbors)
   {
      Individual iCopy[] = new Individual[neighbors.length];
      int i, len = neighbors.length;
      double acFit, randValue;
      
      for (i=0; i<neighbors.length; i++)
         iCopy[i] = neighbors[i];
         
      Arrays.sort(iCopy,new Comparator() // Order the array in terms of the fitness value
      {
         // Compare the individuals in terms of their fitness values
         public int compare(Object o1, Object o2)
         {
             Individual indv = (Individual)o1;
             Individual indv2 = (Individual)o2;
            if (indv.getFitness()>indv2.getFitness())
               return -1;
            else
               if (indv.getFitness()<indv2.getFitness())
                  return 1;
               else
                  return 0;
         }
      });

      i=0;
      acFit = 0.0;
      randValue = r.nextDouble();
      while (acFit < randValue)
      {
         i++;
         acFit += ((double)2*i)/(len*(len-1));
      }
      
      if (i == neighbors.length)
      	i = r.nextInt(neighbors.length);
      
      int x = 0;
      while ((x<neighbors.length) && (iCopy[i] != neighbors[x]))
      	x++;
       
      return neighbors[x];
   }

}
