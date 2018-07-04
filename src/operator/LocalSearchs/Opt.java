/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.LocalSearchs;

import CGA.CProblem;
import Population.Individual;
import Tools.Point;
import java.util.Vector;

/**
 *
 * @author Mostafa
 */
class Opt {

    public Individual search(Individual ind, Point p)
    {
        Vector<Integer> chrom = ind.getchromosome();
        double saving=1;
        CProblem prob = (CProblem)CProblem.getCurrentInstance();
        double cost[][] = prob.getCost();
        int t1best=-1 , t2best=-1;
        Vector<Point> points = new Vector<Point>();

        while(saving > 0)
        {
            saving=0;
            for(int t1=p.getX() ; t1<p.getY() ; t1++)
            {
                for(int t4=p.getX() ; t4<p.getY() ; t4++)
                {
                    if(t1!=t4 && t4!=(t1+1) && t1!=(t4+1))
                    {
                        double diff = cost[chrom.get(t1)][chrom.get(t1+1)]+cost[chrom.get(t4)][chrom.get(t4+1)]
                                -cost[chrom.get(t1)][chrom.get(t4)]-cost[chrom.get(t1+1)][chrom.get(t4+1)];

                        if(diff>saving && !checkPoint(new Point(t1+1, t4), points))
                        {
                            saving=diff;
                            t1best = t1+1;
                            t2best = t4;
                            points.add(new Point(t1+1, t4));
                        }
                    }
                }
            }
            if(saving>0)
            {
                rearrange(t1best,t2best,chrom);
            }
        }
        return ind;
    }

    private void rearrange(int t1best, int t2best, Vector<Integer> chrom)
    {
        int temp = chrom.get(t1best);
        chrom.set(t1best,chrom.get(t2best));
        chrom.set(t2best, temp);
    }

    private boolean checkPoint(Point p , Vector<Point> ps)
    {
        for(int i=0 ; i<ps.size() ; i++)
        {
            Point temp = ps.get(i);
            if((p.getX()==temp.getX() && p.getY()==temp.getY()) || (p.getX()==temp.getY() && p.getX()==temp.getY()))
                return true;
        }
        return false;
    }

}
