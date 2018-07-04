/* ---------------------------------------------------------
   Fichero: Erx.java
   Autor:   Sergio Romero Leiva
   Descripcion
   Cruce por recombinacion de ejes para genotipo permutacion
   Operador cruce ERX
   ---------------------------------------------------------*/

/**
 * @author Sergio Romero
 *
 * Edge recombination crossover
 * 
 */

package operator.Recombinations;

import CGA.CProblem;
import CGA.Problem;
import Population.Individual;
import Tools.Point;
import java.util.Random;
import operator.Recombination;


public class Erx implements Recombination
{
   private Problem prob;
   public int edgeTable[][]; // adjacencies
   public int numEdges[]; // alive edges
   public boolean active[]; // for storing whether edges are alive or not
   double cost[][];
   Individual newInd;
   double pc;

   public Erx()    
   {
       prob = CProblem.getCurrentInstance();
       cost = prob.getCost();
   }
   
   private void insertEdge(int n, int e)
   {
      for (int i=0; i<4; i++)
      {
         if (edgeTable[n-1][i] == e-1) // repeated edge
            return;
         if (edgeTable[n-1][i] == -1) // empty edge
         {
            edgeTable[n-1][i] = e-1;
            numEdges[n-1]++;
            return;
         }
      }
   }
   
   private void setup(Individual i1, Individual i2)
   {
      int i, j, c10, c1, c11, c20, c2, c21, len = i1.getLen();
      
      // Initializes the table of edges
      for (i=0; i<i1.getLen(); i++)
      {
         for (j=0; j<4; j++)
            edgeTable[i][j] = -1;
         active[i] = true;
         numEdges[i] = 0;
      }
      c1 = i1.getGen(0);
      c10 = i1.getGen(1);
      c2 = i2.getGen(0);
      c20 = i2.getGen(1);
      insertEdge(c1,c10);
      insertEdge(c2,c20);
      
      c1 = i1.getGen(len-1);
      c10 = i1.getGen(len-2);
      c2 = i2.getGen(len-1);
      c20 = i2.getGen(len-2);
      insertEdge(c1,c10);
      insertEdge(c2,c20);
      
      for (i=1; i<i1.getLen()-1; i++)
      {
         c1 = i1.getGen(i);
         c10 = i1.getGen(i-1);
         c11 = i1.getGen(i+1);
         c2 = i2.getGen(i);
         c20 = i2.getGen(i-1);
         c21 = i2.getGen(i+1);
         insertEdge(c1,c10);
         insertEdge(c1,c11);
         insertEdge(c2,c20);
         insertEdge(c2,c21);
      }
   }
   
   // Returns the first free element
   private int getRandom()
   {
      for (int i=0; i<active.length; i++)
         if (active[i])
            return i;
      return -1;
   }
   
   // remove a node and decrement references
   private void kill(int n)
   {
      int c;
      
      active[n] = false;
      for (int i=0; i<4; i++)
      {
         c = edgeTable[n][i];
         if (c != -1 && active[c])
            numEdges[c]--;
      }
   }
   
   private int next(int n)
   {
      int c, cmin=-1, edges;
      
      edges = 5;
      for (int i=0; i<4; i++)
      {
         c = edgeTable[n][i];
         if (c != -1 && active[c])
         {
            if (numEdges[c] < edges)
            {
               edges = numEdges[c];
               cmin = c;
            }
 
            else if(numEdges[c] == edges && n < prob.getnCustomer())
            {
                if( c <prob.getnCustomer() && cmin<prob.getnCustomer())
                {

                    if(cost[n+1][c+1] < cost[n+1][cmin+1])
                        cmin = c;
                }

                else if( cmin < prob.getnCustomer() )
                {
                    if(cost[n+1][0] < cost[n+1][cmin+1])
                        cmin = c;
                }
 
                else if( c < prob.getnCustomer() )
                {
                    if(cost[n+1][c+1] < cost[n+1][0])
                        cmin = c;
                }
            }

            else if(numEdges[c] == edges )
            {


                if( c <prob.getnCustomer() && cmin<prob.getnCustomer())
                {

                    if(cost[0][c+1] < cost[0][cmin+1])
                        cmin = c;
                }

                else if( c < prob.getnCustomer() )
                    cmin = c;
            }

         }
      }
      
      if (edges == 5)
         return getRandom();
      else
         return cmin;
   }
   
   public Individual[] xOver(Individual[] selected) {

       Individual indv[] = new Individual[selected.length/2];
       int ind=0;
     //  System.out.println("sizeofSelc = "+selected.length);
       for(int I=0;I<selected.length;I+=2)
       {
           if(Math.random()<=pc)
           {
              Individual i1, i2;
              newInd = new Individual();
              int c1, c2, len;

              newInd = (Individual)selected[I].copyIndividual();
              i1 = (Individual)selected[I];
              i2 = (Individual)selected[I+1];
              len = i1.getLen();

              // create tables
              if (active == null || active.length != len)
              {
                 edgeTable = new int[len][4];
                 numEdges = new int[len];
                 active = new boolean[len];
              }

              setup(i1,i2);

              c1 = i1.getGen(0)-1;
              c2 = i2.getGen(0)-1;



              if (numEdges[c2] < numEdges[c1])
                 c1 = c2;
              else
              {
                  if(c1 < prob.getnCustomer() && c2<prob.getnCustomer())
                  {
                      if(cost[0][c1+1]>cost[0][c2+1])
                          c1=c2;
                  }

                  else
                  {
                      if(c2<prob.getnCustomer())
                        c1 = c2;
                  }
              }

              newInd.setGen(0,c1+1);
              kill(c1);
              for (int i=1; i<len; i++)
              {
                 c1 = next(c1);
                 newInd.setGen(i,c1+1);
                 kill(c1);
              }
               newInd.setLocation(new Point(13121321, 12131231));
              indv[ind] =newInd;
              active=null;
           }


           else
           {
               if(selected[I].getFitness()<selected[I+1].getFitness())
               {
                   indv[ind] = (Individual)selected[I].copyIndividual();
               }

               else
                   indv[ind] = (Individual)selected[I+1].copyIndividual();
           }

           ind++;
       }
      return indv;
   }


    public void setPc(double pc) {
      this.pc = pc;
    }

    public double getPc() {
      return pc;
    }


    }
