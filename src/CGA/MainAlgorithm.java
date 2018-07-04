/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CGA;

import Configuration.ReadConfig;
import Configuration.ReadConfigration;
import Population.Individual;
import Population.Population;
import Tools.Point;
import Tools.WriteFile;
import main.Main;

/**
 *
 * @author Mostafa
 */
public class MainAlgorithm
{
    private Statistics stat;

    public MainAlgorithm(String path)
    {
        ReadConfig read = new ReadConfigration();
        Problem prob = new CProblem(read.getParameters(path));
        Main.wf = new WriteFile("indivdual.txt",0);
    }

    public void cMA()
    {
        stat  = new Statistics();
        Problem prob = CProblem.getCurrentInstance();
        prob.getPop().initializeGrid();
        prob.evaluatePop();
    
        for(int i=0 ; prob.stop(i) ; i++)
        {
            Population tempPop = new Population(prob.getPop().getDimX(), prob.getPop().getDimY(), prob.getGeneDomain(),prob.getnCustomer());
            prob.resetUpdate();
            int num=0;
            while(prob.hasSoluations())
            {
               
                Individual ind = prob.getNextSolution();
               //System.out.println("point first = "+ind.getLocation().getX());
                Individual[] neighbors = prob.getNeighbors(ind);

                Individual[] parents = prob.getSelected(neighbors);
                
                Individual[] offsprings = prob.recombination(parents);
                //System.out.println("point recom = "+ind.getLocation().getX());
                prob.mutation(offsprings);

                prob.evaluatePop(offsprings);
                prob.localSearch(offsprings);
                prob.replace(offsprings, ind,tempPop);
//                System.out.println("num = "+num);
  //              num++;
                
                
            }
            prob.setPop(tempPop.copyGrid());
            
            Main.test();

            stat.calculate(tempPop,i);
        }


        System.out.println("best = "+Main.indx+"   = "+Main.val);
    }


}
