/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Population;

import CGA.CProblem;
import CGA.Problem;
import Tools.Point;
import java.util.Vector;

/**
 *
 * @author Mostafa
 */
public class Individual
{
    private double fitness;
    private Vector<Integer> chromosome ;
    private Point point ;
    private Problem prob;

    public Individual() {

        prob = CProblem.getCurrentInstance();
    }



    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void setLocation(Point point)
    {
        this.point = point;
    }

    public Point getLocation()
    {
        if(point == null)
            System.out.println(" individual has no location exception");
        return point;
    }

    public int selection(double Ranking[],int size)
    {
        int index[]= new int[Ranking.length];

        for(int i=0;i<Ranking.length;i++)
            index[i]=i;
            
        for(int i=0;i<Ranking.length;i++)
        {
            for(int j=0;j<Ranking.length-1;j++)
            {
                if(Ranking[j]>Ranking[j+1])
                {
                    double temp = Ranking[j];
                    Ranking[j]= Ranking[j+1];
                    Ranking[j+1] = temp;
                    int temp_indx = index[j];
                    index[j]=index[j+1];
                    index[j+1]=temp_indx;

                }
            }
        }
        double fit[]=new double[Ranking.length];
            fit[0] = Ranking[0];
//System.out.println("fit[0] = "+fit[0]+"      index = "+index[0]);
        for(int i=1;i<Ranking.length;i++)
        {
                fit[i] = fit[i-1]+Ranking[i];
         //   System.out.println("fit["+i+"] = "+fit[i]+"      index = "+index[i]);
        }

        double rand = Math.random()*fit[Ranking.length-1];
        
        for(int i=0;i<Ranking.length;i++)
            if(fit[i]>=rand)
            {
               // System.out.println("rand = "+rand+" chosen = "+fit[i]+"   indx = "+index[i]);
                return index[i];

            }
        return -1;
    }

    public void generateRandom()
    {
        Vector<Integer> geneDomain = prob.getGeneDomain();
        int nCustomer = prob.getnCustomer();
        chromosome = new Vector<Integer>();
        int size = geneDomain.size();
        double cost[][] = prob.getCost();
        double demand[] = prob.getDemand();
        double OverTime = prob.OverTime();
        double OverCap = prob.Overcap();
        double Ranking[];

        int cur=0;
        double time=0,cap=0;
        for(int i=0; i <size;i++)
        {
            Ranking = new double[size-i];
            if(cur>nCustomer)cur=0;
            for(int j=0; j<geneDomain.size(); j++)
            {

                if(geneDomain.get(j)>nCustomer)
                {
                    if(time>OverTime || cap>OverCap)
                    {
                        Ranking[j]=1000000;
                    }
                    else
                    {
                        Ranking[j]=0;
                    }

                }
 
                else
                {
                    if(time>OverTime || cap>OverCap)
                    {
                        Ranking[j]= cost[cur][geneDomain.get(j)]*1000;
                    }
                    else
                    {
                        Ranking[j]=cost[cur][geneDomain.get(j)];
                    }

                }
//                System.out.println("Ranking ["+geneDomain.get(j)+"] = " +Ranking[j] );
            }

            int index = this.selection(Ranking,size-i);
            //System.out.println("val = "+cost[cur][geneDomain.get(index)]);
             if(geneDomain.get(index)>nCustomer)
             {
                 time = 0;
                 cap=0;
                 cur = geneDomain.get(index);
             }
 
             else
             {
                time += cost[cur][geneDomain.get(index)];
                cur = geneDomain.get(index);
                cap += demand[cur];
             }
  //          System.out.println("cur = "+cur+"    =");
    //          Scanner s = new Scanner(System.in);
      //  s.next();
            chromosome.add(cur);
            geneDomain.removeElementAt(index);
         
        }
       // System.out.println("Chrom = "+chromosome);
      //    Scanner s = new Scanner(System.in);
       // s.next();
    }

    /*added one */
    public Vector<Integer> getchromosome()
    {
        return  this.chromosome;
    }

    /**added one*/
    public int getLen()
    {
        if(chromosome!=null )return this.chromosome.size();
        else
        {
            System.out.println("No Chromosome Set");
            return 0;
        }
    }
    /*added one */
    public int getGen(int pos)
    {
        if(chromosome!=null)
            return chromosome.get(pos);
        else
        {
            System.out.println("No Chromsome Yet");
            return -1;
        }
    }
    /*added one */
    public void setGen(int pos,int value)
    {
        this.chromosome.set(pos, value);
    }
    
    public Individual copyIndividual()
    {

        Individual tmp = new Individual();
        tmp.chromosome = (Vector<Integer>) this.chromosome.clone();
        tmp.fitness=this.getFitness();
        tmp.point = this.getLocation();

        return tmp;
    }

    public void setChromosome(Vector<Integer> chromosome) {
        this.chromosome = chromosome;
    }
    
}
