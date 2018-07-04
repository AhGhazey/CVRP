/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Population;

import Tools.Point;
import java.util.Vector;

/**
 *
 * @author Mostafa
 */
public class Population
{

    private int dimX , dimY;
    Individual[][] pop;
    Vector<Integer> geneDomain;//added varible
    int nCustomer;

    public Population(int dimX , int dimY, Vector<Integer>geneDomain,int nCustomer)
    {
        this.dimX = dimX;
        this.dimY = dimY;
        this.geneDomain = geneDomain;
        this.nCustomer = nCustomer;
        pop = new Individual[dimY][dimX];
        
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public int popSize() // new one
    {
        return dimY*dimX;
    }
    
   
    public Population copyGrid()
    {
        Population pop = new Population(this.getDimX(), this.getDimY(),this.geneDomain,this.nCustomer);
        pop.pop = this.pop.clone();
        pop.geneDomain = (Vector<Integer>) this.geneDomain.clone();
        return pop;
    }

    public void initializeGrid()
    {
        for(int i =0;i<dimY;i++)
        {
            for(int j=0;j<dimX;j++)
            {
                    pop[i][j] = new Individual();
                pop[i][j].generateRandom();
                pop[i][j].setLocation(new Point(j,i));
            }
        }
    }

    public Individual getIndividual(int indx)
    {
        int x_pt = indx%dimX;
        int y_pt = indx/dimX;

        return this.pop[y_pt][x_pt];
        
    }
    
    public Individual getIndividual(Point point)
    {//this equation to handle -ve points
        return pop[(point.getY()+dimY)%dimY][(point.getX()+dimX)%dimX];
    }

    public void SetIndividual(Individual individual , Point point)
    {
        /*this methodes mainly used by temp population var
         * e3mel setLocation lel individual, as offspring has no location
         * setlocation lel individual gowa el populatioin
         */
        individual.setLocation(point);
        pop[point.getY()][point.getX()] = individual;
    }

}
