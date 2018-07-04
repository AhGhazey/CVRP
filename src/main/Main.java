/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import CGA.CProblem;
import CGA.MainAlgorithm;
import Population.Individual;
import Tools.WriteFile;
import java.util.Vector;

/**
 *
 * @author Mostafa
 */
public class Main {
public static void main(String [] args){
    MainAlgorithm mA = new MainAlgorithm("config.xml");
    mA.cMA();


    
}
static int o = 0;
public static double val = 10000000;
public static int indx =0;
public static WriteFile wf;
public static void test()
{
    Individual ind = CProblem.getCurrentInstance().getBestSolution();
    Vector<Integer> ch = ind.getchromosome();
    //System.out.print(o+"--- ");
    String s =(o+",");
    for(int i=0 ; i<ch.size()-1 ; i++)
        s+=ch.get(i)+" , ";
    s+=ch.get(ch.size()-1);
    wf.write(s);
    System.out.println(s);
    System.out.println(ind.getFitness());
    if(ind.getFitness()<val)
    {
        val = ind.getFitness();
        indx = o;
    }
    o++;
}
}
