/**
 * @author Bernabe Dorronsoro
 *
 * Suitable for permutations
 * 
 * Inserts an allele into a different location
 * 
 */

package operator.Mutations;
import Population.Individual;
import Tools.Changing_Loc;
import java.util.Random;
import java.util.Vector;
import operator.Mutation;


public class InsertionMutation implements Mutation
{
    private Random r;
    private double pm;
    private Changing_Loc change;
    
    public InsertionMutation(Random r)
    {
       this.r = r;
       change = new Changing_Loc(r);
       
    }
    
   // Parameter Individual, returns Individual
    public void mutate(Individual[] offsprings) {
       
        for(int I=0;I<offsprings.length;I++)
        {

            int len = offsprings[I].getchromosome().size(); //Length of individual --> Number of elements in the chromosome
            pm = this.getPm();
           // System.out.println("prob = "+prob);
            for (int i=0; i<len; i++)
	       if (r.nextDouble() <= pm)
	       {

		       int r1 = r.nextInt(len);
		       int r2 = 0;
		       do
		       {
		    	   r2 = r.nextInt(len);
		       } while (r1==r2);
                  
		       this.change.relocate(r1, r2, offsprings[I].getchromosome());
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

