/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

/**
 *
 * @author Mostafa
 */
public class Point {

    private int x, y;

    public Point(){}
    public Point(int x , int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    
    public void setXY(int x , int y) {//make constructor incase of methode setXY
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

}
