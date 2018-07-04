/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.LocalSearchs;

import CGA.CProblem;
import CGA.Problem;
import Population.Individual;
import Tools.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Ahmed Ghazey
 */
public class Interchange {

    private Problem prob;
    private double cost[][];
    int numOfC;
    int numOfV;
    int chrLength;

    public Interchange() {
        prob = CProblem.getCurrentInstance();
        cost = prob.getCost();
        numOfC = prob.getnCustomer();
        numOfV = prob.getnVehicle();
        chrLength = numOfC + numOfV - 1;
    }

    public void interchange(Individual indv, Point[] points) {
        Vector<Integer> chro = indv.getchromosome();
        ArrayList<List<Integer>> tours = new ArrayList<List<Integer>>();
        for (int i = 0; i < points.length; i++) {
            List tour = new ArrayList();
            for (int j = points[i].getX(); j < points[i].getY() + 1; j++) {
                tour.add(chro.get(j));
            }
            // tour =  chro.subList(, points[i].getY());
            tours.add(tour);
        }

        for (int i = 0; i < tours.size() - 1; i++) {
            for (int j = i + 1; j < tours.size(); j++) {
                
                List<Integer> tour1 = tours.get(i);
                List<Integer> tour2 = tours.get(j);
                
                int sz1 = tour1.size();
                int sz2 = tour2.size();
                
                double originalDistance = getDistance(tour2) + getDistance(tour1);

                int indx11 = (int) (Math.random() * sz1);
                int indx12 = indx11 + (int) (Math.random() * (sz1 - indx11));

                int indx21 = (int) (Math.random() * sz2);
                int indx22 = indx21 + (int) (Math.random() * (sz2 - indx21));

                List<Integer> vTour1 = new ArrayList<Integer>();
                List<Integer> vTour2 = new ArrayList<Integer>();



                if (tour1.isEmpty() && tour2.isEmpty()) {
                    break;
                } 
                
                else if (tour1.isEmpty()) {
                    for (int k = 0; k < indx21; k++) {
                        vTour2.add(tour2.get(k));  
                    }
                    
                    for (int k = indx22+1; k < tour2.size(); k++) {
                            vTour2.add(tour2.get(k));
                        }
                    
                    for (int k = indx21; k <= indx22; k++) {
                        vTour1.add(tour2.get(k)); 
                        
                    }
                    
                }
                
                else if (tour2.isEmpty()) {
                    for (int k = 0; k < indx11; k++) {
                        vTour1.add(tour1.get(k));  
                    }
                    
                    for (int k = indx12+1; k < tour1.size(); k++) {
                            vTour1.add(tour1.get(k));
                        }
                    
                    for (int k = indx11; k <= indx12; k++) {
                        vTour2.add(tour1.get(k)); 
                        
                    }
                }

                //---------------------------------------
                else{
                for (int k = 0; k < indx11; k++) {
                    vTour1.add(tour1.get(k));
                }

                for (int k = indx21; k <= indx22; k++) {
                    vTour1.add(tour2.get(k));
                }

                for (int k = indx12 + 1; k < tour1.size(); k++) {
                    vTour1.add(tour1.get(k));
                }
                //------------------------------------
                for (int k = 0; k < indx21; k++) {
                    vTour2.add(tour2.get(k));
                }

                for (int k = indx11; k <= indx12; k++) {
                    vTour2.add(tour1.get(k));
                }

                for (int k = indx22 + 1; k < tour2.size(); k++) {
                    vTour2.add(tour2.get(k));
                }
                }
                //--------------------------------------
                double newDistance = getDistance(vTour1) + getDistance(vTour2);

                if (newDistance < originalDistance) {
                    tours.set(i, vTour1);
                    tours.set(j, vTour2);
                }

            }// end of for j
        }// end of for i


        Vector<Integer> chromosome = new Vector<Integer>();
        // now lists are avaliable to construct chromosome :D

        int delim = numOfC + 1;

        for (int i = 0; i < tours.size() - 1; i++) {
            chromosome.addAll(tours.get(i));
            chromosome.add(delim);
            delim++;
        }

        chromosome.addAll(tours.get(tours.size() - 1));

        while (chromosome.size() < chrLength) {
            chromosome.add(delim);
            delim++;
        }

        //     System.out.println(chromosome);

        indv.setChromosome(chromosome);
    }

    public void copyList(List<Integer> copied, List<Integer> original) {
        for (int i = 0; i < original.size(); i++) {
            copied.add(original.get(i));
        }
    }

    public double getDistance(List<Integer> tour) {
        double distance = 0;
        if (tour.isEmpty()) {
            return 0;
        }
        distance += cost[0][tour.get(0)];
        for (int i = 1; i < tour.size(); i++) {
            distance += cost[tour.get(i - 1)][tour.get(i)];
        }
        distance += cost[tour.get(tour.size() - 1)][0];

        return distance;
    }
}
