
package operator.Recombinations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphCycles {



    private void deleteUnCycleNodes(boolean[] arr , boolean mat[][])
    {
        boolean flag=false;
        do
        {
            flag=false;
            for(int i=0 ; i<arr.length ; i++)
            {
               if(!arr[i])
               {
                    boolean to=false , from=false;
                    for(int j=0 ; j<arr.length && !(to&&from)  ; j++)
                    {
                        to = to||mat[i][j];
                        from = from||mat[j][i];
                    }
                    arr[i]= !(to&&from);
                    flag=arr[i];
               }
            }
        }
        while(flag);
    }

    public ArrayList<List<Integer>> setLists(boolean mat[][])
    {

        ArrayList<LinkedList<Integer>> lists = new ArrayList<LinkedList<Integer>>();
        ArrayList<List<Integer>> cycles = new ArrayList<List<Integer>>();

        boolean []visit = new boolean [mat.length];
        deleteUnCycleNodes(visit , mat);

        for(int i=0 ; i<visit.length ; i++)
        {
            if(!visit[i])
            {
                visit[i] = true;
                LinkedList<Integer> temp = new LinkedList<Integer>();
                temp.add(i);
                lists.add(temp);
                while(!lists.isEmpty())
                {   int j=0;
                    LinkedList<Integer> cycle = lists.get(j);
                    for(int k=0 ; k<visit.length ; k++)
                    {

                        if(cycle.get(cycle.size()-1)!=k)
                        {
                            if(mat[cycle.get(cycle.size()-1)][k])
                            {
                                visit[k]=true;
                                LinkedList<Integer> genCycle = (LinkedList<Integer>)cycle.clone();
                                genCycle.add(k);
                                boolean flag=true;
                                for(int m=0 ; m<genCycle.size()-2 && flag ; m++)
                                {
                                    if(genCycle.get(m) == k)
                                    {
                                        cycles.add(genCycle.subList(m, genCycle.size()));
                                        flag=false;
                                    }
                                }
                                if(flag)lists.add(genCycle);

                            }
                        }
                    }
                    lists.remove(0);
                }
            }
        }
        return cycles;
    }


    public static void main(String[] args)
    {
        boolean [][] graph = new boolean[11][11];
        graph[6][2]=true;
        graph[6][4]=true;

        graph[1][6]=true;

        graph[2][3]=true;

        graph[3][1]=true;

        graph[4][5]=true;
        graph[4][10]=true;

        graph[5][0]=true;

        graph[0][6]=true;

        graph[7][1]=true;

        graph[8][9]=true;
        graph[8][2]=true;

        graph[10][5]=true;


        GraphCycles g = new GraphCycles();


        ArrayList<List<Integer>> cycles = g.setLists(graph);
        for(int i=0 ; i<cycles.size() ; i++)
        {
            System.out.println(cycles.get(i));
            System.out.println("----------------------------");
        }


    }
}
