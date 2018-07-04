
/**
 * @author Sergio Romero Leiva
 * 
 * Modified by Bernabe Dorronsoro
 * 
 * L5 Neighborhood implementation
 * 
 */
   
package neighborhoods;


import operator.Neighborhood;

public class Linear5 extends Neighborhood
{
	// This constructor defines the translation sequence to apply
   //to the considered position for getting the whole neighborhood
   public Linear5()
   {

      super(5);  // Calls the base constructor to set the size
      moves[0].setXY(0,0);
      moves[1].setXY(1,0);
      moves[2].setXY(0,1);
      moves[3].setXY(-1,0);
      moves[4].setXY(0,-1);

   }
}
