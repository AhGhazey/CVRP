/**
 * @author Sergio Romero Leiva
 *
 * 
 * Follows the Fixed Random Sweep policy for visiting the individuals 
 * 
 */

package CellUpdate;

import CGA.CProblem;
import CGA.Problem;
import Population.Population;
import Tools.Point;
import java.util.Vector;
import operator.CellUpdate;

public class FixedRandomSweep implements CellUpdate
{

    private Problem prob;
    private Vector<Integer> list;
    private Vector<Point> sequence;
    private int curIndex;

    public FixedRandomSweep() {

        list = new Vector<Integer>();
        sequence = new Vector<Point>();
        prob = CProblem.getCurrentInstance();
        curIndex = 0;
        
         for(int i=0; i<prob.getPop().popSize(); i++)
            list.add(i);
        reset();
    }


    public void reset() {

        if(sequence.size() != 0)
        {
            curIndex = 0;
        }

        else
        {
            int size = prob.getPop().popSize();
            Vector<Integer> tmp = (Vector<Integer>) list.clone();

            for(int i=0; i <size;i++)
            {
                int index = (int) (tmp.size() * Math.random());
                int pt_X = tmp.get(index)%prob.getPop().getDimX();
                int pt_Y = tmp.get(index)/prob.getPop().getDimX();
                sequence.add(new Point(pt_X,pt_Y));
                tmp.remove(index);
            }
        }
        
    }

    public Point getNext() {
        if(hasNext())
        {
            curIndex++;
            return sequence.get(curIndex-1);
        }
        System.out.println("Has No Next Point");
        return null;
    }

    public boolean hasNext() {

        if(curIndex < prob.getPop().popSize())
            return true;
        return false;
    }
}
