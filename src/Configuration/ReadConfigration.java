/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Configuration;

/**
 *
 * @author Wolf
 */
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import operator.*;
public class ReadConfigration implements ReadConfig{
    //public CellUpdate cell;
    public Parameters p = new Parameters();
    
    public double [][] arr , tmpCoord;
    public double [] cDemand , droptime;

    @Override
    public Parameters getParameters(String filePath) {
        try{
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(filePath)); 
            doc.getDocumentElement ().normalize ();
            Element root = doc.getDocumentElement();
            NodeList children = root.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                   Node child = children.item(i);
                   if(child.getNodeName().equals("Updatepolicy")){
                       p.setUpdatePolicy(child.getTextContent());
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("Population")){
                       p.setPopSize(child.getTextContent());
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }
                   else if(child.getNodeName().equals("EvaluationLimit")){
                       p.setEvaluationLimit(Integer.parseInt(child.getTextContent()));
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("Neighborhood")){
                       p.setNeighborhood(child.getTextContent());
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("Mutation")){
                       NodeList children2 = child.getChildNodes();
                       System.out.println(child.getNodeName());
                       for (int j = 0; j < children2.getLength(); j++) {
                           Node child2 = children2.item(j);
                           
                           if(child2.getNodeName().equals("Type")) {
                               p.setMutation(child2.getTextContent());
                               System.out.println("  "+child2.getNodeName()+" "+child2.getTextContent());
                           }
                           else if(child2.getNodeName().equals("MutationProbability")){
                               p.setPm(Double.parseDouble(child2.getTextContent()));
                               System.out.println("  "+child2.getNodeName()+" "+child2.getTextContent());
                           }
                       }
                   }
                   else if(child.getNodeName().equals("Crossover")){
                       NodeList children2 = child.getChildNodes();
                       System.out.println(child.getNodeName());
                       for (int j = 0; j < children2.getLength(); j++) {
                           Node child2 = children2.item(j);
                           if(child2.getNodeName().equals("Type")) {
                               p.setCrossover(child2.getTextContent());
                               System.out.println("  "+child2.getNodeName()+" "+child2.getTextContent());
                           }
                           else if(child2.getNodeName().equals("CrossoverProbability")){
                               p.setPc(Double.parseDouble(child2.getTextContent()));
                               System.out.println("  "+child2.getNodeName()+" "+child2.getTextContent());
                           }
                       }
                   }

                   else if(child.getNodeName().equals("SelectionParent1")){
                       p.setSelectParent1(child.getTextContent());
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("SelectionParent2")){
                       p.setSelectParent2(child.getTextContent());
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("Replacement")){
                       System.out.println(child.getNodeName());
                       NodeList children2 = child.getChildNodes();
                       for (int j = 0; j < children2.getLength(); j++) {
                           Node child2 = children2.item(j);
                           if(child2.getNodeName().equals("ReplacementType")) {
                                p.setReplacement(child2.getTextContent());
                                System.out.println("  "+child2.getNodeName()+" "+child2.getTextContent());
                           }
                           else if(child2.getNodeName().equals("RFactor")){
                                p.setRFactor(Double.parseDouble(child2.getTextContent()));
                                System.out.println("  "+child2.getNodeName()+" "+child2.getTextContent());
                           }
                       }
                       
                   }

                   else if(child.getNodeName().equals("NumberOfVehicles")){
                       p.setnVehicle(Integer.parseInt(child.getTextContent()));
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("overTime")){
                       p.setOverTime(Double.parseDouble(child.getTextContent()));
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("overCapacity")){
                       p.setOverCapacity(Double.parseDouble(child.getTextContent()));
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("NumberOfCustomers")){
                       p.setnCustomer(Integer.parseInt(child.getTextContent()));
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }

                   else if(child.getNodeName().equals("costTable")){
                        if(!child.getTextContent().equalsIgnoreCase("null")){
                            //System.out.println("msh byd5ol hna ");
                           readCostTable(child.getTextContent());
                           p.setCost(arr);
                           /*for(int g = 0 ; g < arr.length; g++){
                               for(int h = 0; h < arr[g].length; h++){
                                    System.out.print(arr[g][h]+" ");
                               }
                               System.out.println();
                           }*/
                       }
                   }
                   else if(child.getNodeName().equals("coordinate")){
                       
                        if(!child.getTextContent().equalsIgnoreCase("null")){
                           readCoordinates(child.getTextContent());
                           convertCoord();
                           /*for(int g = 0 ; g < arr.length; g++){
                               for(int h = 0; h < arr[g].length; h++){
                                    System.out.print(arr[g][h]+" ");
                               }
                               System.out.println();
                           }*/
                           p.setCost(arr);
                       }
                   }
                   else if(child.getNodeName().equals("CustomersDemand")){
                       cDemand = tokenize(child.getTextContent());
                       p.setDemand(cDemand);
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }
                   else if(child.getNodeName().equals("DropTime")){
                       droptime = tokenize(child.getTextContent());
                       p.setDropTime(droptime);
                       System.out.println(child.getNodeName()+" "+child.getTextContent());
                   }
                   else if(child.getNodeName().equals("LocalSearch")){
                       NodeList children2 = child.getChildNodes();
                       System.out.println(child.getNodeName());
                       for (int j = 0; j < children2.getLength(); j++) {
                           Node child2 = children2.item(j);
                           if(child2.getNodeName().equals("Type")) {
                                p.setLocalSearchType(child2.getTextContent());
                                System.out.println("  "+child2.getNodeName()+" "+child2.getTextContent());
                                
                           }
                           else if(child2.getNodeName().equals("MaxSteps")) {
                                p.setMaxSteps(Integer.parseInt(child2.getTextContent()));
                                System.out.println("  "+child2.getNodeName()+" "+ child2.getTextContent());
                           }
                           else if(child2.getNodeName().equals("MaxTempreture")){
                                p.setMaxTempreture(Integer.parseInt(child2.getTextContent()));
                                System.out.println("  "+child2.getNodeName()+" "+ child2.getTextContent());
                           }//max tempreture

                           else if(child2.getNodeName().equals("MinTempreture")){
                                p.setMinTempreture(Integer.parseInt(child2.getTextContent()));
                                System.out.println("  "+child2.getNodeName()+" "+ child2.getTextContent());
                           }//min tempreture
                           else if(child2.getNodeName().equals("CoolingRate")){
                                p.setCoolingRate(Double.parseDouble(child2.getTextContent()));
                                System.out.println("  "+child2.getNodeName()+" "+ child2.getTextContent());
                           }
                           //cooling rate
                       }
                   }
                   
            }
       //System.out.println(child.getNodeName()+ child.getTextContent());            
        }
        catch (SAXParseException err) {
        }
        catch (SAXException e) {
        }
        catch (Throwable t) {
            t.printStackTrace ();
        }
        return p;
    }
    
    public void readCostTable(String fname ){
         //System.out.println("fana hna");
        File f1 = new File (fname);
        int g=0,h=0, m=0, n=0;
        m = p.getnCustomer() + 1;
        n = m ; 

        arr = new double [m][n];
        try {
            FileReader fr =null;
            try {
                fr = new FileReader(f1);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            }
            BufferedReader br = new BufferedReader(fr);
            String line  = br.readLine(),text = "";
           
            while(line!=null){
                //text += line;
                StringTokenizer stLine = new StringTokenizer(line,",");
                while(stLine.hasMoreTokens()){
                    arr[g][h] = Double.parseDouble(stLine.nextToken());
                    h++;
                    if(h==n)
                    {
                    h=0;
                    g++;
                    }
                }
                line = br.readLine();
            }

            
        } catch (IOException ex) {
            System.out.println("I/O Exception");
        }
    }
    public void readCoordinates(String fname){
    //    System.out.println("anahna a");
        File f1 = new File (fname);
        int g=0,h=0,m=0,n=0;
        m = p.getnCustomer() + 1;
        n = 3 ;

        tmpCoord = new double [m][n];
        try {
            FileReader fr =null;
            try {
                fr = new FileReader(f1);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            }
            BufferedReader br = new BufferedReader(fr);
            String line  = br.readLine(),text = "";

            while(line!=null){
                //text += line;
                StringTokenizer stLine = new StringTokenizer(line," ");
                while(stLine.hasMoreTokens()){
                    tmpCoord[g][h] = Double.parseDouble(stLine.nextToken());
                    h++;
                    if(h==n)
                    {
                    h=0;
                    g++;
                    }
                }
                line = br.readLine();
            }
           /* for(int i=0;i<tmpCoord.length;i++){
                for(int j=0;j<tmpCoord[0].length;j++)
                    System.out.print(tmpCoord[i][j]+" ");
                System.out.println("");
            }*/

        } catch (IOException ex) {
            System.out.println("I/O Exception");
        }
    }
    public void convertCoord(){
        arr = new double [tmpCoord.length][tmpCoord.length];
        for(int i = 0 ; i< arr.length; i++){
            for(int j = 0 ; j< arr[i].length; j++){
                if (i==j){ arr[i][j]=0; continue;}
                arr[i][j] = Math.sqrt(Math.pow((tmpCoord[j][1]-tmpCoord[i][1]),2)+
                            Math.pow((tmpCoord[j][2]-tmpCoord[i][2]),2) );
                arr[j][i] = arr[i][j];

            }
        }
       /* for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr[0].length;j++)
                System.out.print(arr[i][j]+ " ");
            System.out.println("");
        }*/
    }
    public double [] tokenize (String list){
        ArrayList<Double> aL = new ArrayList<Double>();
        Object [] temp;
        double [] arr2 ;
        int n2 = 0;
        StringTokenizer st2 = new StringTokenizer(list,",");
        while(st2.hasMoreTokens()){
        aL.add(Double.parseDouble(st2.nextToken()));
        }
        temp = aL.toArray();
        arr2 = new double[temp.length];
        for(int i = 0 ; i< temp.length;i++){
            arr2[i] = (Double)temp[i];
        }
        return arr2;
    }
    public static void main(String[] args) {
        ReadConfigration re = new ReadConfigration();
        re.getParameters("config.xml");
    }
}
