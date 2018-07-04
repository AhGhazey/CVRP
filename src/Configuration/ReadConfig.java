/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Configuration;

/**
 *
 * @author Mostafa
 */
public interface ReadConfig
{
    /*
     * parameter : the path of configuration file
     * return : object of parameters contain all parameters in configuration file
     */
    public Parameters getParameters(String filePath);
}
