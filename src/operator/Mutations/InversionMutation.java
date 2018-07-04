/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.Mutations;

import Population.Individual;
import Tools.Changing_Loc;
import java.util.Random;
import java.util.Vector;
import operator.Mutation;

/**
 *
 * @author Ramy
 */
public class InversionMutation implements Mutation{

    private Random r;
    private double pm;
    private Changing_Loc change;

    public InversionMutation(Random r)
    {
       this.r = r;
       change = new Changing_Loc(r);
       
    }
    public void mutate(Individual[] offsprings) {
          for(int I=0;I<offsprings.length;I++)
        {

            int len = offsprings[I].getchromosome().size(); //Length of individual --> Number of elements in the chromosome
            pm = this.getPm();

            for (int i=0; i<len; i++)
                if (r.nextDouble() <= pm)
                {
                    int r1 = r.nextInt(len);
                    int r2 = 0;

                    do
                    {
                        r2 = r.nextInt(len);
                    }while (r1==r2);

                    if(r1==r2+1 || r2==r1+1)
                        this.change.swap(r1, r2,  offsprings[I].getchromosome());

                    else
                    {
                        while( r1 != r2  )
                        {
                            this.change.swap(r1, r2,  offsprings[I].getchromosome());
                            if(r1 >r2){r1--;r2++;}

                            else{

                                r2--;r1++;
                               }

                            if((r1==r2+1 || r2==r1+1))
                            {
                                change.swap(r1, r2,  offsprings[I].getchromosome());
                                break;
                            }
                        }
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
