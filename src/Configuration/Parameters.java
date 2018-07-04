/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Configuration;

/**
 *
 * @author Mostafa
 */
public class Parameters
{
    private String updatePolicy , popSize , neighborhood ,
                   mutation , crossover , selectParent1 ,
                   selectParent2 , replacement , localSearchType  ;

    private  int evaluationLimit , nVehicle , nCustomer , maxSteps ,
                 maxTempreture , minTempreture ;

    private double pc , pm , overTime , overCapacity , coolingRate , rFactor;

    private double[][] cost ;

    private double[] demand , dropTime;

    public double[][] getCost() {
        return cost;
    }

    public void setCost(double[][] cost) {
        this.cost = cost;
    }

    public String getCrossover() {
        return crossover;
    }

    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }

    public double[] getDemand() {
        return demand;
    }

    public void setDemand(double[] demand) {
        this.demand = demand;
    }

    public double[] getDropTime() {
        return dropTime;
    }

    public void setDropTime(double[] dropTime) {
        this.dropTime = dropTime;
    }

    public int getEvaluationLimit() {
        return evaluationLimit;
    }

    public void setEvaluationLimit(int evaluationLimit) {
        this.evaluationLimit = evaluationLimit;
    }

    public String getMutation() {
        return mutation;
    }

    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    public int getnCustomer() {
        return nCustomer;
    }

    public void setnCustomer(int nCustomer) {
        this.nCustomer = nCustomer;
    }

    public int getnVehicle() {
        return nVehicle;
    }

    public void setnVehicle(int nVehicle) {
        this.nVehicle = nVehicle;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public double getOverCapacity() {
        return overCapacity;
    }

    public void setOverCapacity(double overCapacity) {
        this.overCapacity = overCapacity;
    }

    public double getOverTime() {
        return overTime;
    }

    public void setOverTime(double overTime) {
        this.overTime = overTime;
    }

    public double getPc() {
        return pc;
    }

    public void setPc(double pc) {
        this.pc = pc;
    }

    public double getPm() {
        return pm;
    }

    public void setPm(double pm) {
        this.pm = pm;
    }

    public String getPopSize() {
        return popSize;
    }

    public void setPopSize(String popSize) {
        this.popSize = popSize;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String getSelectParent1() {
        return selectParent1;
    }

    public void setSelectParent1(String selectParent1) {
        this.selectParent1 = selectParent1;
    }

    public String getSelectParent2() {
        return selectParent2;
    }

    public void setSelectParent2(String selectParent2) {
        this.selectParent2 = selectParent2;
    }

    public String getUpdatePolicy() {
        return updatePolicy;
    }

    public void setUpdatePolicy(String updatePolicy) {
        this.updatePolicy = updatePolicy;
    }

    public String getLocalSearchType() {
        return localSearchType;
    }

    public void setLocalSearchType(String localSearchType) {
        this.localSearchType = localSearchType;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public double getCoolingRate() {
        return coolingRate;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    public int getMaxTempreture() {
        return maxTempreture;
    }

    public void setMaxTempreture(int maxTempreture) {
        this.maxTempreture = maxTempreture;
    }

    public int getMinTempreture() {
        return minTempreture;
    }

    public void setMinTempreture(int minTempreture) {
        this.minTempreture = minTempreture;
    }

    public double getRFactor() {
        return rFactor;
    }

    public void setRFactor(double rFactor) {
        this.rFactor = rFactor;
    }

    
}
