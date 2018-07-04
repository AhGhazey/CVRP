/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.LocalSearchs;

import CGA.CProblem;
import CGA.Problem;
import Population.Individual;
import Tools.Point;
import java.util.Vector;
import operator.LocalSearch;

/**
 *
 * @author Mostafa
 */
public class MixLS implements LocalSearch {

    private int maxSteps;
    private int objIndex;

    public MixLS(int maxSteps,int objIndex) {
        this.maxSteps = maxSteps;
        this.objIndex = objIndex;
    }



    public void local(Individual[] offsprings) {
        
        for(int i=0 ; i<offsprings.length ; i++)
        {
            optimize(offsprings[i]);
        }
    }


    /* not tested*/
    private Point[] calcRoutesIndex(Individual ind)
    {
        CProblem prob = (CProblem) CProblem.getCurrentInstance();
        Point[] routes = new Point[prob.getnVehicle()];
        Vector<Integer> chrom = ind.getchromosome();
        int nC = prob.getnCustomer();
        int spliters = prob.getnVehicle()-1;

        int x=0 , y=0 , curSpliter=0 ;

        for(int i=0 ; i<chrom.size() ; i++)
        {
            y=i;

            if(chrom.get(i) > nC)
            {
                routes[curSpliter] = new Point(x, y-1);
                x=y+1 ;
                curSpliter++;
            }
            if(curSpliter == spliters)
            {
                routes[routes.length-1] = new Point(x, chrom.size()-1);
                break;
            }
        }
        return routes;
    }

    private void optimize(Individual ind)
    {
        Problem prob = CProblem.getCurrentInstance();

        // 2-opt localsearch     
        Individual bestOpt = ind.copyIndividual();
        Point[] routes = calcRoutesIndex(bestOpt);
        Opt o = null;
        NonRepeated o1 = null;
        /* ObjIndex == 0 call opt class
         * ObjIndex == 1 call nonrepeated class
        */

        Individual  sol = null;
        if(objIndex == 0){
            o = new Opt();
            for(int i=0 ; i<routes.length ; i++)
                sol = o.search(bestOpt.copyIndividual() , routes[i]);

            CProblem.getCurrentInstance().fitness(sol);
            if(prob.compare(sol,bestOpt)>0)
                bestOpt = sol;
        }
           
        else if(objIndex == 1){
            o1 = new NonRepeated();
            for(int i=0 ; i<routes.length ; i++)
                sol = o1.search(bestOpt.copyIndividual() , routes[i]);

            CProblem.getCurrentInstance().fitness(sol);
            if(prob.compare(sol,bestOpt)>0)
                bestOpt = sol;
        }
        
        
        
            
            Interchange inter = new Interchange();
            Individual temp = bestOpt.copyIndividual();
            inter.interchange( temp , routes);
            CProblem.getCurrentInstance().fitness(temp);
            if(prob.compare(temp,bestOpt)>0)
            {
                bestOpt.setChromosome(temp.getchromosome());                
                System.out.println("yes it work !!!");
            }
        
        
        
        
        ind.setChromosome(bestOpt.getchromosome());
        ind.setFitness(bestOpt.getFitness());
    }
}