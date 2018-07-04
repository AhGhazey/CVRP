/**
 * @author Sergio Romero Leiva
 *
 * 
 * Follows the Line Sweep policy for visiting the individuals, i.e., 
 * individuals are visited in the order they are stored in the pop. 
 * 
 */
   
package CellUpdate;

import CGA.CProblem;
import CGA.Problem;
import Tools.Point;
import operator.CellUpdate;


public class LineSweep implements  CellUpdate
{
   private int pos;
   private Problem prob;
   public LineSweep()
   {
      pos = 0;
      prob = CProblem.getCurrentInstance();
   }
   
   public Point getNext()
   {
     
       Point cell = null;
      
       if(hasNext())
       {
           cell = new Point(pos%prob.getPop().getDimX(),pos/prob.getPop().getDimX());
           pos = pos+1;
       }
       else
           System.out.println("Has No Next Point");

       return cell;
   }

    public void reset() {
        pos = 0;
    }

   

    public boolean hasNext() {
        if(pos < prob.getPop().popSize())
            return true;
        return false;
    }
}
