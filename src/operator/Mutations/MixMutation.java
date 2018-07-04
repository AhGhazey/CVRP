/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.Mutations;

import Population.Individual;
import Tools.Changing_Loc;
import java.util.Random;
import operator.Mutation;

/**
 *
 * @author Ramy
 */
public class MixMutation implements Mutation{

    private Random r;
    private double pm;
    private Changing_Loc change;

    public MixMutation(Random r)
    {
       this.r = r;
       change = new Changing_Loc(r);
    }

    public void mutate(Individual[] offsprings) {

        Mutation mu;
        for(int I=0;I<offsprings.length;I++)
        {

            int len = offsprings[I].getchromosome().size(); //Length of individual --> Number of elements in the chromosome
            Individual tmp[]= new Individual[1];
            tmp[0] = offsprings[I];
            pm = this.getPm();
            for (int i=0; i<len; i++)
            {
                if(r.nextDouble()<=pm)
                {
                   

                   double rand = r.nextDouble();
                   if (rand < 0.33)
                   {
                        mu = new InversionMutation(r);
                   }

                   else if(rand > 0.66)
                   {

                          mu= new InsertionMutation(r);
                   }

                   else
                   {
                       mu = new SwapMutation(r);
                   }
                   mu.setPm(pm);
                   mu.mutate(tmp);
                }
            }
        }
        
    }

    public void setPm(double pm) {
        this.pm = pm;
    }

    public double getPm() {
        return this.pm;
    }

}
