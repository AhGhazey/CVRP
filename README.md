# Capacitated Vehicle Routing Problem (CVRP)
Solving CVRP problem with genetic Algorithm 

# Introduction 
In this Manual you will know how to run the CVRP Program, The CVRP is built with Java,Install NetBeans, for running the application just click run icon in the IDE. 

It will takes around 20 minutes to run the case with current configuration file, you will find configuration file config.xml in the folder of the problem.

Second file is coordinate2.txt this file contains coordinates for different cities, the program generate 2 files (i) is the stat.csv contains statistics for each iteration (ii) is indivdual.txt it put the best individual in each iteration.



# Running specification 

  1.	Update Policy (Allowed values)
      I.	FixedRandomSweep
      II.	LineSweep
      III.	NewRandomSweep
      IV.	Example <Updatepolicy>NewRandomSweep</Updatepolicy>

  2.	Population Dimension (Allowed values)
      I.	It takes two Integers 
      II.	Example <Population>10,10</Population>
  3.	Neighborhood (Allowed values)
      I.	Compact13 
      II.	Compact9 
      III.	Linear5 
      IV.	Example  <Neighborhood>Compact13</Neighborhood>
      
  4.	Mutation 
      I.	Example
          <Mutation>
              <Type>InsertionMutation</Type>
              <MutationProbability>0.2</MutationProbability>
          </Mutation>
      II.	MutationProbability
          i.	It take value from 0.1 to 0.9 

      III.	 Mutation (Allowed Values) 
          i.	InsertionMutation 
          ii.	SwapMutation 
          iii.	InversionMutation 
          iv.	MixMutation


  5.	Crossover 
      I.	Example 
            <Crossover>
		              <Type>EAX</Type>
		              <CrossoverProbability>0.8</CrossoverProbability>
	          </Crossover>
      II.	CrossoverProbability
          i.	It take value from 0.1 to 0.9 

      III.	Crossover (Allowed Values)
          i.	EAX
  6.	SelectionParent1 and SelectionParent2
      I.	Example 
          i.	<SelectionParent1>TournamentSelection</SelectionParent1>
          ii.	<SelectionParent2>CenterSelection</SelectionParent2>
      II.	Allowed Values 
          i.	BestSelection
          ii.	CenterSelection
          iii.	LinearRankSelection
          iv.	RandomSelection
          v.	RouletteWheelSelection
          vi.	TournamentSelection
  7.	ReplacementType
      I.	Example 
            <Replacement>
                  <ReplacementType>ReplaceIfNonWorse</ReplacementType>
                  <RFactor>0.012</RFactor>
            </Replacement>
      II.	Allowed Values 
          i.	ReplaceAlways it need RFactor
          ii.	ReplaceIfBetter
          iii.	ReplaceIfNonWorse
  8.	LocalSearch
      I.	Example 
            <LocalSearch>
		            <Type>opt</Type>
		            <MaxSteps>5</MaxSteps>
		            <MaxTempreture>10</MaxTempreture>
		            <MinTempreture>1</MinTempreture>
		            <CoolingRate>0.8</CoolingRate>
            </LocalSearch>
      II.	Allowed Values 
            i.	Opt
            ii.	NonRepeated
            iii.	SimulatedAnnealingLS it require (MaxTemprature , MinTemprature, CoolingRate)
