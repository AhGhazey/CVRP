/**
 * @author Sergio Romero Leiva
 * 
 * Modified by Bernabe Dorronsoro
 *
 * Replacement operator. Replaces the current individual in the population allways
 * 
 */

package operator.Replacment;

import CGA.CProblem;
import CGA.Problem;
import Population.Individual;
import Population.Population;
import operator.Replacement;



public class ReplaceAlways implements Replacement
{
   //Parametro array de individuos, devuelve individuo

    private Problem prob = CProblem.getCurrentInstance();
    private int total_g = prob.getEvaluationLimit();
    private int cur_gen ;
    private double b;
    public ReplaceAlways(double b){
        this.b = b;
    }
    public void replace(Individual[] offsprings, Individual oldone, Population temp) {
        
        cur_gen = prob.getCurrentGenration();
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
        
        double  value = 1-Math.pow(Math.exp((cur_gen/(float)total_g)-1),b);
        //loSystem.out.println("ana hna");
       // System.out.println("oldone = "+oldone.getLocation().getX());
        if (offsprings[index].getFitness()<=oldone.getFitness())
            temp.SetIndividual(offsprings[index], oldone.getLocation());
        else
        {
            if(Math.random()<value)
            {
                temp.SetIndividual(offsprings[index], oldone.getLocation());
                System.out.println("3aml t3'yer ;)");
            }
            else
                temp.SetIndividual(oldone, oldone.getLocation());
        }
    }
}
