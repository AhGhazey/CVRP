
/**
 * @author Bernabe Dorronsoro
 *
 * Replacement operator. Replaces the current individual in 
 * the population if the individual to insert is not worse 
 * 
 */


package operator.Replacment;

import Population.Individual;
import Population.Population;
import operator.Replacement;


public class ReplaceIfNonWorse implements Replacement
{
   // Parametro array de individuos, devuelve individuo
  public void replace(Individual[] offsprings, Individual oldone, Population temp)
   {
      int index =0;
        double min = offsprings[0].getFitness();
        for(int i=1;i<offsprings.length;i++)
        {
            if(offsprings[i].getFitness() < min)
            {
                min = offsprings[i].getFitness();
                index = i;
            }
        }

        if (offsprings[index].getFitness() <= oldone.getFitness())
            temp.SetIndividual(offsprings[index],oldone.getLocation());
        else
            temp.SetIndividual(oldone,oldone.getLocation());  
   }

}
