/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator;

import CGA.CProblem;
import CGA.Problem;
import Population.Individual;
import Population.Population;
import Tools.Point;

/**
 *
 * @author Mostafa
 */
public class Neighborhood
{
    /*
     * el mafrod araga3 array men le indidivuals el neighbors lel individual ele ana ba3to
     *
     */
     protected Point moves[]; // Movements from the central point to get the neighbors
     protected int size; // Neighborhood size
     private Problem prob;
     public Neighborhood()
     {
         prob = CProblem.getCurrentInstance();
         size = 0;
     }

   public Neighborhood(int size)
   {
      prob = CProblem.getCurrentInstance();
      this.size = size;
      moves = new Point[size];

      for (int i=0; i<size; i++)
      {
         moves[i] = new Point();
      }
   }

   public int getNeighSize()
   {
      return size;
   }

    public Individual[] getNeighbors(Individual ind )
    {
        //N.B that each neighours has their individual at first index
        Individual neigh[] = new Individual[size];
        //neigh[0] = ind;
        Population pop = prob.getPop();
        Point tmp ;
        for (int i=0; i<size; i++)
        {
            tmp = new Point();
            tmp.setXY(moves[i].getX()+ind.getLocation().getX(), moves[i].getY()+ind.getLocation().getY());
            neigh[i] = pop.getIndividual(tmp).copyIndividual();
        }
        return neigh;
    }
}
