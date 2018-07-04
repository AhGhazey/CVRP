/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.LocalSearchs;

/**
 *
 * @author Mostafa
 */

/**
 * @author Sergio Romero
 *
 * Modified by Bernabe Dorronsoro
 *
 * Defines a Simulated Annealing local search operator for JCell
 *
 */

import CGA.Problem;
import Population.Individual;
import java.util.Random;
import operator.LocalSearch;
import operator.Mutation;




public class SimulatedAnnealingLS  implements LocalSearch
{
   private Random r;
   private Problem prob; //Problem
   private Mutation suc; // this operator returns a neighbor solution
   private int steps; // for modifying the temperature value
   private double tmax, tmin, coolingRate; //temp max, min and cooling rate
   private Individual ind = null;

   public SimulatedAnnealingLS(Random r, Problem prob, Mutation suc, int steps, double tmax,
      double tmin, double coolingRate)
   {
      this.r = r;
      this.prob = prob;
      this.suc = suc;
      this.steps = steps;
      this.tmax = tmax;
      this.tmin = tmin;
      this.coolingRate = coolingRate;
   }

   public Individual execute(Individual current)
   {
      Individual next[];
      double temp, diff;

      next = new Individual[1];
      ind = current.copyIndividual();
     
      temp = tmax;
      while (temp > tmin)
      {
         for (int k=0; k<steps; k++)
         {
            next[0] = current.copyIndividual();

            if(Math.random()>.5)
            {
                suc.mutate(next);
            }
            else
            {
                new MixLS(2,0).local(next);
                k=steps;
            }

            prob.fitness(next[0]); // evaluate it
            if (next[0].getFitness()<=current.getFitness())
            {
               current = next[0]; // Accept it if it is better solution
            }
            else
            {
               diff = ((Double)next[0].getFitness()).doubleValue() - ((Double)current.getFitness()).doubleValue();
               diff /= temp;
               if (r.nextDouble() < Math.exp(diff))
               {
                  current = next[0]; // if it is worse, it is accepted with a given probability
                 // System.out.println("here");
               }
            }
         }
         temp *= coolingRate;
       }
       if(ind.getFitness()<current.getFitness())
         return ind;
       else return current;
   }

    public void local(Individual[] offsprings) {
     
        for(int i=0;i<offsprings.length;i++)
        {
            offsprings[i] = this.execute(offsprings[i]);
           // Scanner s = new Scanner(System.in);
          //  s.next();
        }
    }
}
