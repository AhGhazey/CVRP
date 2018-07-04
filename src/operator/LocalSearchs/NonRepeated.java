/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.LocalSearchs;

/**
 *
 * @author Ramy
 */


import CGA.CProblem;
import Population.Individual;
import Tools.Point;
import Tools.Changing_Loc;

import java.util.Vector;

public class NonRepeated {
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

    Changing_Loc change = new Changing_Loc(null);
    public Individual search(Individual ind, Point p)
    {
        Vector<Integer> chrom_temp,chrom = ind.getchromosome();
        double saving=1;
        CProblem prob = (CProblem)CProblem.getCurrentInstance();
        double cost[][] = prob.getCost();
        int t1best=-1 , t2best=-1,t1ind,t2ind,t3ind,t4ind,t1 = 0,t2 = 0,t3=0,t4=0;
        Vector<Point> points = new Vector<Point>();
        int len = p.getY()-p.getX()+1;
        while(saving > 0)
        {
            saving=0;
            chrom_temp = (Vector<Integer>) chrom.clone();
            int top = p.getY();

            for(int t=p.getX() ; t<=p.getY() ; t++)
            {
                int rand = (int)(Math.random()*len)+p.getX();
                 
                 t1ind = chrom.indexOf(chrom_temp.get(rand));
                 t1 = chrom.get(t1ind);

                /* System.out.println("x = "+p.getX()+"  "+p.getY()+"  rand = "+rand+"  top = "+top);
                 for(int k=p.getX();k<=p.getY();k++)
                     System.out.print(chrom_temp.get(k)+" ");
                 System.out.println("");
*/

                change.swap(rand, top, chrom_temp);

               /* for(int k=p.getX();k<=p.getY();k++)
                     System.out.print(chrom_temp.get(k)+" ");
                System.out.println("");
                //chrom_temp.set(rand, chrom_temp.get(top));
                //chrom_temp.set(top, t1);*/

                 t2ind = (t1ind+1) % (p.getY()+1);
                // System.out.println("t2 = "+t2ind+"  t1 = "+t1ind+"  "+(t1ind%p.getY()));
                 if(t2ind == 0)t2ind=p.getX();
                 t2 = chrom.get(t2ind);
                saving =0;

//                for(int k=p.getX();k<=p.getY();k++)
//                     System.out.print(chrom.get(k)+" ");
//                System.out.println("");
                for(int tf = p.getX() ; tf<=p.getY() ; tf++)
                {
                    int tmp = (tf+1)%(p.getY()+1);
                    if(tmp == 0)tmp=p.getX();
                    if(tf != t1ind && tf != t2ind && tmp != t1ind)
                    {
                         t4ind = tf;
                         t4 = chrom.get(t4ind);
                         t3ind = ((t4ind+1) % (p.getY()+1));
                         if(t3ind == 0)t3ind=p.getX();
                         t3 = chrom.get(t3ind);

                       //  System.out.println("no. = "+t1+"  "+t2+"  "+t4+"  "+t3);
                         //System.out.println("indx = "+t1ind+"  "+t2ind+"  "+t4ind+"  "+t3ind);

                        double diff = cost[chrom.get(t1ind)][chrom.get(t2ind)]+cost[chrom.get(t4ind)][chrom.get(t3ind)]
                                -cost[chrom.get(t2ind)][chrom.get(t3ind)]-cost[chrom.get(t1ind)][chrom.get(t4ind)];

                        if(diff>saving )
                        {
                            saving=diff;
                            t1best = t2ind;
                            t2best = t3ind;
                        }
                    }

                }
                top--;
                 if(saving>0)
            {
                //rearrange(t1best,t2best,t3best,t4best,chrom);
             /*   System.out.print("Chr = ");
                for(int k=p.getX();k<=p.getY();k++)
                     System.out.print(chrom.get(k)+" ");
                System.out.println("");
                System.out.println("tess = "+t1+"  "+t2+"  "+t4+"  "+t3);
                System.out.println("t1best = "+t1best+"   "+t2best);
*/
                change.relocate(t1best, t2best, chrom);

  /*              System.out.print("chrom aftr = ");
                for(int k=p.getX();k<=p.getY();k++)
                     System.out.print(chrom.get(k)+" ");
                System.out.println("");
*/
            }
            }
           // Scanner s = new Scanner(System.in);
          //  s.next();
        }
        return ind;
    }

    private void rearrange(int t1best, int t2best, int t3best,int t4best,Vector<Integer> chrom)
    {
        int temp = chrom.get(t2best);
        chrom.set(t2best,chrom.get(t3best));
        chrom.set(t3best, temp);

    }


}
