/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Ramy
 */
public class Changing_Loc {

    Random r;

    public Changing_Loc(Random r) {
        this.r = r;
    }

    public void swap(int r1,int r2,Vector<Integer> chromosome)
    {
        int tmp =  chromosome.get(r1);
        chromosome.set(r1 , chromosome.get(r2));
        chromosome.set(r2,tmp);
    }
     public void relocate(int i, int j,Vector<Integer> chromosome)
    {
        int aux, v;
        if (i == j)
            return;

        aux = i;
        v = chromosome.get(i);

        if (i < j)
            while (aux < j)
            {
                chromosome.set(aux, chromosome.get(aux+1));
                aux++;
            }
        else
            while (aux > j)
            {
                chromosome.set(aux, chromosome.get(aux-1));
                aux--;
            }
        chromosome.set(j,v);
    }
}
