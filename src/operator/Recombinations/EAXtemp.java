/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.Recombinations;

import CGA.CProblem;
import CGA.Problem;
import Population.Individual;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import operator.Recombination;


public class EAXtemp implements Recombination {
    
    double pc=0.7;
    private Problem prob;
    int numOfV;
    int numOfC;
    int chrLength;

    public EAXtemp() {
        prob=CProblem.getCurrentInstance();
        numOfV=prob.getnVehicle();
        numOfC=prob.getnCustomer();
    }
    
    //====================================================
     public Individual[] xOver(Individual[] selected) {
            Individual [] Offsprings =null;
            
            chrLength=selected[0].getLen();
            
            
            for (int i = 0; i < selected.length/2; i+=2) {
                if (Math.random()<=pc){
                    int indv1[]=new int [chrLength];
                    int indv2[]=new int [chrLength];
                    cpyFrmVecToArray(selected[i].getchromosome() , indv1 );
                    cpyFrmVecToArray(selected[i+1].getchromosome(), indv2);
                    eax(indv1, indv2);
                }
         }
         
         
         
         return Offsprings;
    }
//==============================================================================
     public void cpyFrmVecToArray(Vector<Integer>chro,int [] newArr){
         for (int i = 0; i < chro.size(); i++) 
             newArr[i] = chro.get(i);
     }
//==============================================================================
         public Individual cpyFrmArrayToVector(Vector<Integer>chro,int [] arr){
         for (int i = 0; i < arr.length; i++) 
             chro.add(arr[i]);
         Individual indiv=new Individual();
         indiv.setChromosome(chro);
         return indiv;
     }
//==============================================================================
    public void setPc(double pc) {
        this.pc=pc;
    }
//==============================================================================
    public double getPc() {
        return pc;
    }
    //============================================================================
    public void fillMat(int[] a, boolean[][] G) {
        for (int i = 0; i < a.length; i++) {

            //if depot is connected with each city

            
            
            ArrayList<Integer> l = new ArrayList<Integer>();
            while (i < a.length && a[i] <= numOfC) {
                l.add(a[i]);
                ++i;
            }
            
            for (int j = 0; j < l.size() - 1; j++) {
                G[l.get(j)][l.get(j + 1)] = true;
            }
            G[0][l.get(0)] = true;
            G[l.get(l.size() - 1)][0] = true;
            
        }
        
    }
    //============================================================================

    public void xor(boolean[][] A, boolean[][] B, boolean[][] GAB) {
        for (int i = 0; i < GAB.length; i++) {
            for (int j = 0; j < GAB.length; j++) {
                if (A[i][j] != B[i][j]) {
                    GAB[i][j] = true;
                }
            }
        }
    }
    //===========================================================================
    
    //============================================================================
    public int[] eax(int[] a, int[] b) {
        int[] ofsprng = new int[a.length];
        // cityNo + 1 because the depot is counted
        boolean GA[][] = new boolean[numOfC + 1][numOfC + 1];
        boolean GB[][] = new boolean[numOfC + 1][numOfC + 1];
        boolean GAB[][] = new boolean[numOfC + 1][numOfC + 1];
        
        fillMat(a, GA);
        fillMat(b, GB);
        xor(GA, GB, GAB);
        
        GraphCycles g = new GraphCycles();// here get all Cycles in the graph
        ArrayList<List<Integer>> cycles = g.setLists(GAB); // Array List of all Cycles 
       // System.out.println(cycles);
        // call for step 4 and Step 5
        if (cycles.isEmpty()){
            System.out.println("Valid Solution :P");
            return a;
        }
     
        step4And5(cycles, GA, numOfC, numOfV);
        
        return ofsprng;
    }

    //============================================================================

    public ArrayList<Individual> step4And5(ArrayList<List<Integer>> cycles, boolean[][] parent, int cityNo, int carNo) {
        //================================
       int delim=cityNo+carNo+1;
       //=================================
       //=================================
       // offsprings 
       ArrayList<Vector<Integer>> ofsprngs=new ArrayList<Vector<Integer>>();
       //===================================================================
       // defining chromosome length 
       int chrLength=cityNo+carNo-1;
       //===================================================================
        boolean allParents[][][] = new boolean[cycles.size()][parent.length][parent.length];
        boolean c[][] = new boolean[parent.length][parent.length];
        boolean result[][] = new boolean[parent.length][parent.length];
        GraphCycles g = new GraphCycles();
        for (int i = 0; i < cycles.size(); i++) {
            List<Integer> cycle = cycles.get(i);
            for (int j = 0; j < cycle.size() - 1; j++) {
                c[cycle.get(j)][cycle.get(j + 1)] = true;
            }
            
            //=========================================
            xor(parent, c, result);
            //=========================================
            
            ArrayList<List<Integer>> cyclesTest = g.setLists(result); // Array List of all Cycles 
           // System.out.println(cyclesTest);
            for (int j = 0; j < cyclesTest.size(); j++) {
                if (cyclesTest.get(j).get(0) != 0) {
                    System.out.println("here is invalid solution ");
                   
                    // node ....some nodes here ....prv node , node 
                    // after fixing 0 ,node .. some node , prv node , 0
                    result [0][cyclesTest.get(j).get(0)]=true;
                    // break the edge from prv node and node
                    result [cyclesTest.get(j).get(cycles.get(j).size()-2)][cyclesTest.get(j).get(0)]=false;
                    // conect prv node with the Zero 0 node :D
                    result [cyclesTest.get(j).get(cycles.get(j).size()-2)][0]=true;
                    
                    //update cycles after update result 
                    cyclesTest = g.setLists(result);
                    
                }             
            
        }
            // this matrix now contain all generated parent
             allParents[i] = result;
        }
        
        // ArrayList<List<Integer>>OffSprings;
        for (int i = 0; i < allParents.length; i++) {
            boolean offSpring[][]=allParents[i];
            ArrayList<List<Integer>> cy = g.setLists(offSpring);
            
            // define chromosome with its initial capacity 
            Vector<Integer> ofspring=new Vector();
           // check if it valid chromosome 
            if (cy.size()>carNo)
                continue;
            //==================================================================
            for (int j = 0; j < cy.size(); j++) {
                // get one cycle per time to but it in the chromosome  
               List<Integer> cycl=cy.get(j);
               //===============================================================
                for (int k = 0; k < cycl.size(); k++) {
                    if (cycl.get(k)!=0)
                        //inserting every cycle as a separted tour 
                        ofspring.add(cycl.get(k));   
                }// end of each cycle 
                
                // if it is not the last cycle in the chromosome add delimeter
                if (j!= cy.size()-1 ){
                    ofspring.add(delim);
                    delim++;
                }
                
                //============================================================== 
                
                // full the rest of chromosme to avoid variable length chromosome i.e. cycles no is less than cars 
                 if (ofspring.size()<chrLength){
                     while (ofspring.size()!=chrLength){
                         ofspring.add(delim);
                         delim++;
                     }// end while
                 }// end if
                 
            }// end cycles for  
            System.out.println(ofspring);
            ofsprngs.add(ofspring);
        }
        ArrayList<Individual> all= new ArrayList<Individual>();
        for (int i = 0; i < ofsprngs.size(); i++) {
            Individual indv=new Individual();
            indv.setChromosome(ofsprngs.get(i));
            all.add(indv);
        }
        return all;
    }

    //============================================================================
    public void print(boolean[][] a) {
        System.out.println("");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + "  ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    //============================================================================

    public static void main(String[] args) {
        int p1[] = {1, 2, 3, 4, 100, 5, 6, 7, 8};
        //  int p3[] = {1, 3, 2, 4, 100, 5, 6, 7, 8};
        int p2[] = {5, 1, 7, 8, 100, 6, 2, 3, 4};
        EAXtemp m = new EAXtemp();
        boolean k[][] = new boolean[9][9];
      //  m.eax(p1, p2);
  
    }

   
}
