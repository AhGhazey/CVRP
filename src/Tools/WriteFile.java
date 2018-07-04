/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramy
 */
public class WriteFile {

    FileWriter w ;
    BufferedWriter bw;
    public WriteFile(String fileName,int ind) {
        try {
            //try{
            w = new FileWriter(fileName);
        } catch (IOException ex) {
            Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }
            bw = new BufferedWriter(w);
            try {
                if(ind == 0)
                {

                        bw.write("Gener_No,BestIndividual");

                    bw.newLine();
                }
                else
                {
                    bw.write("Gener_No,BestFit,AvgFit,maxFitValue");
                    bw.newLine();
                }
            } catch (IOException ex) {
                    Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
                }
       
    }
    public void write(String val)
    {
        try {
            bw.write(val);
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
