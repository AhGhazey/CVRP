/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator;

import Tools.Point;

/**
 *
 * @author Mostafa
 */
public interface CellUpdate {

    public void reset();
    public Point getNext();
    public boolean hasNext();
}
