/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CGA;

import CellUpdate.FixedRandomSweep;
import CellUpdate.LineSweep;
import CellUpdate.NewRandomSweep;
import Configuration.Parameters;
import Population.Individual;
import Population.Population;
import Tools.Point;
import java.util.Random;
import java.util.Vector;
import neighborhoods.Compact13;
import neighborhoods.Compact9;
import neighborhoods.Linear5;
import operator.CellUpdate;
import operator.LocalSearch;
import operator.LocalSearchs.MixLS;
import operator.LocalSearchs.SimulatedAnnealingLS;
import operator.Mutation;
import operator.Mutations.InsertionMutation;
import operator.Mutations.InversionMutation;
import operator.Mutations.MixMutation;
import operator.Mutations.SwapMutation;
import operator.Neighborhood;
import operator.Recombination;
import operator.Recombinations.EAX;
import operator.Recombinations.Erx;
import operator.Replacement;
import operator.Replacment.ReplaceAlways;
import operator.Replacment.ReplaceIfBetter;
import operator.Replacment.ReplaceIfNonWorse;
import operator.Selection;
import operator.Selections.BestSelection;
import operator.Selections.CenterSelection;
import operator.Selections.LinearRankSelection;
import operator.Selections.RandomSelection;
import operator.Selections.RouletteWheelSelection;
import operator.Selections.TournamentSelection;

/**
 *
 * @author Mostafa
 */
public class CProblem implements Problem
{
    // operators variables
    private CellUpdate cUpdate;
    private LocalSearch local ;
    private Mutation mutation ;
    private Neighborhood neighbor ;
    private Recombination recomb ;
    private Replacement replace;
    private Selection select1;
    private Selection select2;
    private Population pop;
    private Random r;
    //end

    // input variables
    private  int evaluationLimit , nVehicle , nCustomer;
    private double[][] cost ;
    private double[] demand , dropTime ;// the first cell must be zero!!! to handle depot location -----important
    private double overTime , overCapacity ,pc , pm ,rFactor;
    //end

    //problem variables
    private int[] domain;
    private int curGeneration;
    //end



    /*
     * configurate problem , this constructor is called only one time , after that call getCurrentInstance
     */

    public CProblem(Parameters param)
    {
        r = new Random();
        evaluationLimit = param.getEvaluationLimit();
        nVehicle = param.getnVehicle();
        nCustomer = param.getnCustomer();
        cost = param.getCost();
        demand = param.getDemand();
        dropTime = param.getDropTime();
        overTime = param.getOverTime();
        overCapacity = param.getOverCapacity();
        pc = param.getPc();
        pm = param.getPm();
        rFactor = param.getRFactor();
        domain = calcDomain(nVehicle, nCustomer);

        // to write

        instance = this;
        setPopulation(param.getPopSize());
        setcUpdate(param.getUpdatePolicy());
        setMutation( param.getMutation(), param.getPm());
        setNeighbor(param.getNeighborhood());
        setLocal(param.getLocalSearchType() , param.getMaxSteps(),param.getMaxTempreture()
                ,param.getMinTempreture(),param.getCoolingRate());
        setRecomb(param.getCrossover(),param.getPc());
        setReplace(param.getReplacement());
        setSelect1(param.getSelectParent1());
        setSelect2(param.getSelectParent2());
        

        
    }
    /*
     * get the current instance used , and create new instance if there is no instance before
     */
    private static Problem instance = null;
    public static Problem getCurrentInstance()
    {
        return instance;
    }



    public int compare(Individual ind1, Individual ind2)
    {
        if(ind1.getFitness()<ind2.getFitness())
            return 1;
        else if(ind1.getFitness()>ind2.getFitness())
            return -1;
        else return 0;
    }

    public double fitness(Individual ind)
    {
        double fit=0 , overT=0 , overC=0;
        int c1=0 , c2=0 , rout=0;
        double[] time=new double[nVehicle] , cap = new double[nVehicle];

        Vector<Integer> chrom = ind.getchromosome();
        
        for(int i=0 ; i<chrom.size() ; i++)
        {
            c2 = chrom.get(i);
            if(c2 > nCustomer)
            {
                if(c1!=0)
                {
                    fit+=cost[c1][0];
                    time[rout] +=cost[c1][0];
                }
                c1=0;
                rout++;
                continue;
            }
            double temp = (cost[c1][c2]+dropTime[c2]);
            fit+= temp;
            time[rout] += temp;
            cap[rout] += demand[c2];
            c1=c2;
        }
        if(c2 <= nCustomer)
        {
             fit+=cost[c1][0];
             time[time.length-1] +=cost[c1][0];
        }
        
        for(int i=0 ; i<time.length ; i++)
        {
            if(time[i]>overTime)
                overT += time[i]-overTime;

            if(cap[i]>overCapacity)
                overC += cap[i]-overCapacity;
        }

        double result = fit +(1000*overT)+(1000*overC);

        ind.setFitness(result);

        return result ;
    }

    public void evaluatePop() {
        for(int i=0;i<this.pop.popSize();i++)
                this.fitness(pop.getIndividual(i));
    }

    public void evaluatePop(Individual[] inds) {

        for(int i=0;i<inds.length;i++)
            this.fitness(inds[i]);
    }

    public Vector<Integer> getGeneDomain() {
        Vector<Integer> temp = new Vector<Integer>();
        for(int i=0 ; i<domain.length ; i++)
        {
            temp.add(domain[i]);
        }
        return temp;
    }

    public Population getPop() {
        return pop;
    }

    public void setPop(Population pop) {
        this.pop = pop;
    }

    public void resetUpdate() {
        this.cUpdate.reset();
    }

    public boolean hasSoluations() {

        return this.cUpdate.hasNext();
    }

    public Individual getNextSolution() {
        
            return this.pop.getIndividual(this.cUpdate.getNext());
    }

    public Individual[] getNeighbors(Individual ind) {
        return this.neighbor.getNeighbors(ind);
    }

    public Individual[] getSelected(Individual[] neighbors)
    {
        Individual[] sel = new Individual[neighbors.length*2];
        
        for(int i=0 ; i<sel.length ; i++)
        {
            if((i%2) == 0)
            {
                sel[i] = this.select1.getSelected(neighbors);
            }
            else
            {
                sel[i] = this.select2.getSelected(neighbors);
            }
        }

        return sel;
    }

    public Individual[] recombination(Individual[] selected) {
         
        this.recomb.setPc(pc);
        return this.recomb.xOver(selected);
    }

    public void mutation(Individual[] offspring) {
            this.mutation.mutate(offspring);
    }

    public void localSearch(Individual[] offspring) {
        local.local(offspring);
    }

    public void replace(Individual[] offspring, Individual centerInd, Population replacedPop) {
        this.replace.replace(offspring, centerInd, replacedPop);
    }

    public boolean stop(int curGeneration)
    {
        this.curGeneration = curGeneration;
        return (curGeneration < evaluationLimit);
    }

    //////////// set parameters

    private void setcUpdate(String Update) {
        if(Update.equalsIgnoreCase("FixedRandomSweep"))
            this.cUpdate = new FixedRandomSweep();
        else if( Update.equalsIgnoreCase("LineSweep"))
            this.cUpdate = new LineSweep();
        else if( Update.equalsIgnoreCase("NewRandomSweep"))
            this.cUpdate = new NewRandomSweep();
        
    }

    private void setLocal(String localSearch , int max , int maxTempreture , int minTempreture , double CoolingRate ) {
        
        if(localSearch.equalsIgnoreCase("Opt"))
            this.local =new MixLS(max,0);
        else if(localSearch.equalsIgnoreCase("NonRepeated"))
            this.local = new MixLS(max,1);
        else if(localSearch.equalsIgnoreCase("SimulatedAnnealingLS"))
            this.local = new SimulatedAnnealingLS(r, instance, mutation, max,maxTempreture,minTempreture , CoolingRate);
                // new MixLS(20);//

    }

    private void setMutation(String mutationType , double pm) {
        
        if(mutationType.equalsIgnoreCase("InsertionMutation"))
            this.mutation = new InsertionMutation(this.r);

        else if(mutationType.equalsIgnoreCase("SwapMutation"))
            this.mutation = new SwapMutation(this.r);
        
        else if(mutationType.equalsIgnoreCase("InversionMutation"))
            this.mutation = new InversionMutation(this.r);

        else if(mutationType.equalsIgnoreCase("MixMutation"))
            this.mutation = new MixMutation(this.r);

        this.mutation.setPm(pm);
    }

    private void setNeighbor(String neighborType) {
        
        if(neighborType.equalsIgnoreCase("Compact13"))
            this.neighbor = new Compact13();
        else if(neighborType.equalsIgnoreCase("Compact9"))
            this.neighbor = new Compact9();
        else if(neighborType.equalsIgnoreCase("Linear5"))
            this.neighbor = new Linear5();
    }

    private void setRecomb(String recombType , double pc) {
        
//        if(recombType.equalsIgnoreCase(""))
            this.recomb = new EAX();
    }

    private void setReplace(String replaceType) {

        if(replaceType.equalsIgnoreCase("ReplaceAlways"))
            this.replace = new ReplaceAlways(rFactor);

        else if(replaceType.equalsIgnoreCase("ReplaceIfBetter"))
            this.replace = new ReplaceIfBetter();

        else if(replaceType.equalsIgnoreCase("ReplaceIfNonWorse"))
            this.replace = new ReplaceIfNonWorse();
    }

    private void setSelect1(String select) {

        if(select.equalsIgnoreCase("BestSelection"))
            this.select1 = new BestSelection();

        else if(select.equalsIgnoreCase("CenterSelection"))
            this.select1 = new CenterSelection();

        else if(select.equalsIgnoreCase("LinearRankSelection"))
            this.select1 = new LinearRankSelection(this.r);

        else if(select.equalsIgnoreCase("RandomSelection"))
            this.select1 = new RandomSelection(this.r);

        else if(select.equalsIgnoreCase("RouletteWheelSelection"))
            this.select1 = new RouletteWheelSelection(this.r);
        else if(select.equalsIgnoreCase("TournamentSelection"))
            this.select1 = new TournamentSelection(this.r);

    }

    private void setSelect2(String select) {

        if(select.equalsIgnoreCase("BestSelection"))
            this.select2 = new BestSelection();

        else if(select.equalsIgnoreCase("CenterSelection"))
            this.select2 = new CenterSelection();

        else if(select.equalsIgnoreCase("LinearRankSelection"))
            this.select2 = new LinearRankSelection(this.r);

        else if(select.equalsIgnoreCase("RandomSelection"))
            this.select2 = new RandomSelection(this.r);

        else if(select.equalsIgnoreCase("RouletteWheelSelection"))
            this.select2 = new RouletteWheelSelection(this.r);
        else if(select.equalsIgnoreCase("TournamentSelection"))
            this.select2 = new TournamentSelection(this.r);
    }

    private void setPopulation(String popSize)
    {
        this.pop = new Population(Integer.parseInt(popSize.split(",")[0]),Integer.parseInt( popSize.split(",")[1]), this.getGeneDomain(),this.nCustomer);
    }
    ///////////// end

    /*
     * calculate the domain for all individuals
     * parameters : number of vehicles , number of customer
     * retuen : integer array of domain
     */
    private int[] calcDomain(int vehicles , int customers )
    {
        int[] dom = new int [vehicles+customers-1];
        for(int i=0 ; i<dom.length ; i++)
        {
            dom[i] = i+1;
        }
        return dom;
    }

    public Individual getBestSolution()
    {
        Individual[] inds = new Individual[pop.getDimX()*pop.getDimY()];
        int k=0;

        for(int i=0 ; i<pop.getDimX() ; i++)
        {
            for(int j=0 ; j< pop.getDimY() ; j++)
            {
                inds[k] = pop.getIndividual(new Point(i, j));
                k++;
            }
        }

        BestSelection ind = new BestSelection();
        return ind.getSelected(inds);
    }

    public int getnCustomer() {
        return nCustomer;
    }

    public int getnVehicle() {
        return nVehicle;
    }

    public double[][] getCost() {
        return cost;
    }

    public double[] getDemand() {
        return demand;
    }

    public double Overcap()
    {
        return overCapacity;
    }

    public double OverTime()
    {
        return overTime;
    }

    public int getCurrentGenration()
    {
        return curGeneration;
    }

    public int getEvaluationLimit()
    {
        return evaluationLimit;
    }
}
