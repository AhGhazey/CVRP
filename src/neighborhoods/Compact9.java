
/**
 * @author Sergio Romero Leiva
 * 
 * 
 * C9 Neighborhood implementation
 * 
 */
   
package neighborhoods;

import operator.Neighborhood;



public class Compact9 extends Neighborhood
{
	// This constructor defines the translation sequence to apply
	// to the considered position for getting the whole neighborhood
   public Compact9()
   {
      super(9);  // Calls the base constructor to set the size
      moves[0].setXY(0,0);
      moves[1].setXY(1,0);
      moves[2].setXY(0,1);
      moves[3].setXY(-1,0);
      moves[4].setXY(0,-1);
      moves[5].setXY(1,1);
      moves[6].setXY(-1,1);
      moves[7].setXY(-1,-1);
      moves[8].setXY(1,-1);
   }
}
