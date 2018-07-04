/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.Recombinations;

import CGA.CProblem;
import CGA.Problem;
import Population.Individual;
import Tools.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import operator.Recombination;

/**
 *
 * @author Ahmed Ghazey
 */
public class EAX implements Recombination {

    double pc = 0.7;
    private Problem prob;
    int numOfV;
    int numOfC;
    int chrLength;
    double cost[][];
    int delim;
    ArrayList<Individual> offsprings;

    public ArrayList<Individual> getOffsprings() {
        return offsprings;
    }

    public void setOffsprings(ArrayList<Individual> offsprings) {
        this.offsprings = offsprings;
    }

    public EAX() {
        prob = CProblem.getCurrentInstance();
        numOfV = prob.getnVehicle();
        numOfC = prob.getnCustomer();
        cost = prob.getCost();
        chrLength = numOfC + numOfV - 1;
        delim = numOfC + 1;
        //  prob.fitness(null);
    }

    //============================================================================
    // it works fine and good too .
    public void fillMatrix(Individual chromosome, boolean[][] graph) {
        Vector<Integer> chro = chromosome.getchromosome();

        // dealing with array much faster.
        int a[] = new int[chro.size()];
        // just copy the vector into array.
        cpyFrmVecToArray(chro, a);

        for (int i = 0; i < a.length; i++) {
            ArrayList<Integer> l = new ArrayList<Integer>();
            while (i < a.length && a[i] <= numOfC) {
                l.add(a[i]);
                ++i;
            }
            if (l.isEmpty())
                continue;
            for (int j = 0; j < l.size() - 1; j++) {
                graph[l.get(j)][l.get(j + 1)] = true;
            }
            graph[0][l.get(0)] = true;
            graph[l.get(l.size() - 1)][0] = true;
        }
        // some thing is missing the initialization of 0 edges alot of info is missing too
    }
    //==========================================================================
    // it works fine 

    public void cpyFrmVecToArray(Vector<Integer> chro, int[] newArr) {
        for (int i = 0; i < chro.size(); i++) {
            newArr[i] = chro.get(i);
        }
    }
    //============================================================================
    // it works fine 

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
    // it works fine
    public ArrayList<ArrayList<Integer>> getCyclesWithoutarcRepeition(boolean GA[][], boolean GAB[][]) {

        ArrayList<ArrayList<Integer>> c = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < GAB.length; i++) {
            for (int j = 0; j < GAB.length; j++) {
                if (GAB[i][j]) {
                    boolean vis[] = new boolean[GAB.length];
                    ArrayList<Integer> l = new ArrayList<Integer>();

                    if (helperDfs(GAB, GA, j, GA[i][j], i, vis, l)) {
                        l.add(i);
                        GAB[i][j] = false;
                        c.add(l);
                    }
                }
            }
        }
        //  System.out.println(c);
        return c;
    }
    //============================================================================
    // it works fine 

    public boolean helperDfs(boolean[][] GAB, boolean[][] A, int node, boolean isA,
            int startNode, boolean[] visited, ArrayList<Integer> result) {
        if (node == startNode) {
            return true;
        }
        if (visited[node]) {
            return false;
        }
        visited[node] = true;
        for (int i = 0; i < GAB.length; i++) {
            if (GAB[node][i] && A[node][i] != isA) {
                if (helperDfs(GAB, A, i, !isA, startNode, visited, result)) {
                    result.add(node);
                    GAB[node][i] = false;
                    return true;
                }
            }
        }
        return false;
    }

    //==========================================================================
    // it works fine 
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
    // it works fine 

    public Individual[] xOver(Individual[] selected) {
offsprings = new ArrayList<Individual>();
        for (int i = 0; i < selected.length; i += 2) {
            
            if (Math.random() > pc) {
                if (prob.fitness(selected[i]) > prob.fitness(selected[i + 1])) {
                    offsprings.add(selected[i]);
                } else {
                    offsprings.add(selected[i + 1]);
                }

            } else {
                Individual indv1 = selected[i];
                Individual indv2 = selected[i + 1];
                eax(indv1, indv2);

            }
        }
        Individual[] ofSprings = new Individual[offsprings.size()];
        for (int i = 0; i < offsprings.size(); i++) {
            ofSprings[i] = offsprings.get(i);
            ofSprings[i].setLocation(new Point(23232, 232323));
        }


        return ofSprings;
    }
//==============================================================================
    // it is supposed to be fine and works good :) 

    public void eax(Individual chro1, Individual chro2) {

        boolean[][] GA = new boolean[numOfC + 1][numOfC + 1];
        boolean[][] GB = new boolean[numOfC + 1][numOfC + 1];
        boolean[][] GAB = new boolean[numOfC + 1][numOfC + 1];

        fillMatrix(chro2, GB);
        fillMatrix(chro1, GA);

        // step1
        xor(GA, GB, GAB);

        //step2
        ArrayList< ArrayList< Integer>> cycles = getCyclesWithoutarcRepeition(GA, GAB);
        //===========
         if (cycles.isEmpty()){
            if (prob.fitness(chro1) > prob.fitness(chro2)) {
                    offsprings.add(chro1);
                } else {
                    offsprings.add(chro2);
                }
            return;
        }
     //   System.out.println("cycles "+ cycles);
        ArrayList<Individual> tempOffsprings = new ArrayList<Individual>();

        // step 3 ommited because we will use single stratagy 
        int indx = (int) (Math.random() * cycles.size());
       
        boolean cycle[][] = new boolean[numOfC + 1][numOfC + 1];
        boolean result[][] = new boolean[numOfC + 1][numOfC + 1];
        // construct graph form cycles to Xor it with the parent :D
        Collections.reverse(cycles.get(indx));
         cycles.get(indx).add(cycles.get(indx).get(0));
        constructGraphFromCycle(cycles.get(indx), cycle);
        // step 4
        xor(cycle, GA, result);
        // step 5 fix result   

        Individual indv = new Individual();
        /*********************************/
        fixSolution(result, indv);// problem here 
        /*********************************/
        offsprings.add(indv);

    }
//======================================
    /*
     * i ask for all cycles  then i append all valid cycles in different tours 
     * the problem that valid cycles may contain the same City so i voilate the 
     * rules , so what is the solution ???
     */

    public void fixSolution(boolean[][] result, Individual indv) {
        ArrayList<ArrayList<Integer>> lists = getTours(result);
        if (lists.size() > numOfV) {
            lists = sort(lists);
            lists = merge(lists, numOfV);
        }
        Vector<Integer> chromosome = new Vector<Integer>();
        // now lists are avaliable to construct chromosome :D
        for (int i = 0; i < lists.size() - 1; i++) {
            chromosome.addAll(lists.get(i));
            chromosome.add(delim);
            delim++;
        }
        chromosome.addAll(lists.get(lists.size() - 1));

        while (chromosome.size()!=  chrLength) {
            chromosome.add(delim);
            delim++;
        }
        delim = numOfC + 1;
   //     System.out.println(chromosome);
        
        indv.setChromosome(chromosome);

    }
//==============================================================================
    // it works fine 

    public void constructGraphFromCycle(ArrayList<Integer> cycle, boolean graph[][]) {
        for (int i = 0; i < cycle.size() - 1; i++) {
            graph[cycle.get(i)][cycle.get(i + 1)] = true;
        }
    }
//==============================================================================
//    // logic have many problems , same problem as fixSolution , what is the solution ??
//    // i think coach is our final solution.
//    public void constructIndividualFromGraph(boolean [][] graph, Individual indv){
//        // graph must contain Only cycles which start with the depot to make valid chromosome 
//        // if number of cycles must less than or equal Car number else indvidual will be null and function will return
//        
//         GraphCycles g = new GraphCycles();// here get all Cycles in the graph
//        ArrayList<List<Integer>> cycles = g.setLists(graph); // Array List of all Cycles
//        
//        /*check if the number of cycles greater than number of Cars  return null */
//        //======================================================================
//        if (numOfV<cycles.size()){
//            indv.generateRandom();
//            System.out.println("um here ");
//            return;
//        }
//        //======================================================================
//        
//        /* if number of cycles less than or equals to number of cars insert cycle then delimeter */
//        Vector<Integer> chromosome = new Vector<Integer>();
//        int delim = numOfC+1;
//        for (int i = 0; i < cycles.size(); i++) {
//            
//            List<Integer> cycle= cycles.get(i);
//            //==================================================================
//            for (int j = 0; j < cycle.size(); j++) {
//                if (cycle.get(j)!= 0)   
//                    chromosome.add(cycle.get(j));    
//            }
//            //==================================================================
//            // insert delimeter after each cycle except the last one 
//            if (i!= cycles.size()-1){
//                chromosome.add(delim);
//                delim++;
//            }    
//        }
//        // ensure that chromosome length is perfect 
//        while (chromosome.size()<chrLength){
//            chromosome.add(delim);
//                delim++;
//        }
//        //======================================================================
//        // final result 
//       indv.setChromosome(chromosome);         
//        
//    }
//==============================================================================

    public ArrayList<ArrayList<Integer>> getTours(boolean graph[][]) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();

        int n = graph.length;
        boolean vis[] = new boolean[n];
        for (int i = 1; i < n; i++) {
            ArrayList<Integer> cycle = new ArrayList<Integer>();
            if (!vis[i]) {

                cycle.add(i);
                vis[i] = true;
                for (int j = 0; j < cycle.size(); j++) {
                    for (int k = 1; k < n; k++) {

                        if (graph[k][cycle.get(j)] && !vis[k]) {
                            cycle.add(k);
                            vis[k] = true;
                        } else if (graph[cycle.get(j)][k] && !vis[k]) {
                            cycle.add(k);
                            vis[k] = true;
                        }

                    }

                }
            }
            if (!cycle.isEmpty()) {
                lists.add(cycle);
            }
        }
        //     System.out.println(lists);
        return lists;
    }
    //==========================================================================

    public ArrayList<ArrayList<Integer>> sort(ArrayList<ArrayList<Integer>> lists) {

        ArrayList<ArrayList<Integer>> tmpList = new ArrayList<ArrayList<Integer>>();
        int n = lists.size();
        while (!lists.isEmpty()) {
            int indx = getMinLength(lists);
            tmpList.add(lists.get(indx));
            lists.remove(indx);
        }
        return tmpList;
        // System.out.println(lists);
    }
    //==========================================================================

    public ArrayList<ArrayList<Integer>> merge(ArrayList<ArrayList<Integer>> lists, int desired) {
        if (lists.size() < 2) {
            return lists;
        }
        if (lists.size() == desired) {
            return lists;
        }
        ArrayList<Integer> merged = new ArrayList<Integer>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < lists.get(i).size(); j++) {
                merged.add(lists.get(i).get(j));
            }
        }
        //System.out.println(merged);
        lists.remove(0);
        lists.remove(0);
        lists.add(merged);
        lists = sort(lists);
        return merge(lists, desired);
    }

    //==========================================================================
    public int getMinLength(ArrayList<ArrayList<Integer>> lists) {
        int indx = 0;
        int prv = lists.get(0).size();
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).size() < prv) {
                prv = lists.get(i).size();
                indx = i;
            }
        }
        return indx;
    }

    public void setPc(double pc) {
        this.pc = pc;
    }
//==============================================================================

    public double getPc() {
        return pc;
    }
//==============================================================================
//    public static void main(String[] args) {
//   boolean[][]m=new boolean[5][5];
//   m[0][1]=true;
//   m[1][4]=true;
//   m[1][2]=true;
//   m[2][0]=true;
//   m[2][3]=true;
//   m[3][1]=true;
//   m[4][0]=true;
//   boolean[][]n=new boolean[5][5];
//   n[0][1]=true;
//   n[1][3]=true;
//   n[1][2]=true;
//   n[2][0]=true;
//   n[2][4]=true;
//   n[3][0]=true;
//   n[3][2]=true;
//   n[4][1]=true;
//   boolean GAB[][] =  new boolean[5][5];
//   ArrayList<Integer>l=new ArrayList<Integer>();
//   l.add(0);
//   l.add(1);
//   l.add(0);
//   EAX aX=new EAX();
//  // aX.getCyclesWithoutarcRepeition(n,m,GAB);
//   aX.constructGraphFromCycle(l, GAB);
//   
//   
//   
//    }
}
