/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import CGA.CProblem;

import CGA.Problem;

import Configuration.ReadConfig;
import Configuration.ReadConfigration;
import Population.Population;


/**
 *
 * @author Ramy
 */
public class TestStat {

    public static void main(String args[])
    {
        for(int i =0;i<100;i++)
        {
            System.out.println("R = "+Math.log10(i)+"    "+Math.exp((i/(float)100)-1.01));
        }
        ReadConfig read = new ReadConfigration();
        Problem prob = new CProblem(read.getParameters("config.xml"));
        prob.getPop().initializeGrid();//po = new Population_Old(20, 20, null, 31)
        prob.evaluatePop();
        double fit=0;
        for(int i=0;i<prob.getPop().popSize();i++)
        {
            fit+=prob.getPop().getIndividual(i).getFitness();
        }
        System.out.println("si = "+(fit/prob.getPop().popSize()));
    }
}
