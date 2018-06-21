package com.app.lenovo.summerproject;
import android.util.Log;

import java.util.*;
class Algorithms
{
    private int V;
    class Pair
    {
        int v;
        double w;
        Pair(int V,double W)
        {
            w=W;
            v=V;
        }
    }
    private int max_comf=35;
    private LinkedList<Pair> adj[];
    Algorithms(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }
    void addEdge(int u, int x1,double x2)
    {
        Pair obj=new Pair(x1,x2);
        Pair obj2=new Pair(u,x2);
        adj[u].add(obj);
        adj[x1].add(obj2);
    }
    public static void main(String args[] ){
        Algorithms obj=new Algorithms(0);
        //obj.go2("");
    }
    public Algorithms go()
    {
        //Scanner sc=new Scanner(System.in);
        double data[][]={{24.3,56.3,20},{24.3,45.5,36},{24.3,45,25},{24.3,92,28.4},{46.2,60,25.7},{64.3,30.5,34.8},{24.3,60,42.4},{24.3,60,65.6},
                {44.3,60,25.8},{34.3,40.7,36.4},{24.3,36.8,36.8},{44.3,36,24},{24.3,60,15},{24.3,49,17.6},{24.3,60,26.3},{34.3,60,32.7}};
        int i=0,k=0;
        Algorithms g=new Algorithms(16);
        for(i=0;i<12;i++)
        {
            g.addEdge(i,i+4,5);
        }
        for(i=12;i<16;i++)
        {
            g.addEdge(i,i-12,20);
        }
        g.addEdge(15,0,80);
        for(i=0;i<15;i++)
        {
            if((i+1)%4!=0)
                g.addEdge(i,i+1,5);
            else
                g.addEdge(i,i-3,20);
        }
        //g.modifyEdgeCost(data,0,0);
        return g;
    }
    void display()
    {
        for(int i=0;i<V;i++)
        {
            Iterator itr=adj[i].iterator();
            while(itr.hasNext())
            {
                Pair temp2=(Pair)itr.next();
                System.out.println(i+": "+temp2.v+" "+temp2.w);
            }
        }
    }
    public Algorithms go2(String temp,String BP,boolean c1, boolean c2, boolean c3, boolean c4, boolean c5, boolean c6,String date)
    {
        //Rows are cities, columns are Temperatures and Humidity. Ignore column 3 for now. 4 is comfort locations
        double data[][]={{24.3,56.3,20,2},{24.3,45.5,36,3},{24.3,45,25,4},{24.3,92,28.4,2},{46.2,60,25.7,1},{34.3,30.5,34.8,5},{24.3,60,42.4,3}
        ,{24.3,60,65.6,6},{44.3,60,25.8,3},{34.3,40.7,36.4,5},{24.3,36.8,36.8,4},{44.3,36,24,3},{24.3,60,15,3},{24.3,49,17.6,2},{24.3,60,26.3,5}
        ,{34.3,60,32.7,5},{28,94,22,5},{26.57,94,22,3},{24.02,100,22,3},{29.97,68,22,3},{23.52,46,22,1},{31,66,22,2},{31,84,22,1}
        ,{31,79,22,2},{24,94,22,2}};
        Algorithms g=new Algorithms(25);
        int low=0,high=0;
        try {
            low = Integer.parseInt(temp.substring(0, 2));
            high = Integer.parseInt(temp.substring(3));
        }catch (Exception e)
        {
            low=25;
            high=35;
        }
        //Add for Kolkata[8]
        g.addEdge(8,0,2289.8);
        g.addEdge(8,1,1490.9);
        g.addEdge(8,2,1514.2);
        g.addEdge(8,3,991.1);
        g.addEdge(8,4,580.2);
        g.addEdge(8,5,1034.1);
        g.addEdge(8,6,2062.7);
        g.addEdge(8,7,1418);
        g.addEdge(8,9,2051.7);
        g.addEdge(8,10,441.4);
        g.addEdge(8,11,1628.9);
        g.addEdge(8,12,1493.7);
        g.addEdge(8,13,1858.7);
        g.addEdge(8,14,1673.1);
        g.addEdge(8,15,2385.9);
        g.addEdge(8,16,1265.7);
        g.addEdge(8,17,944.8);
        g.addEdge(8,18,2146.4);
        g.addEdge(8,19,1722);
        g.addEdge(8,20,1821.3);
        g.addEdge(8,21,434.5);
        g.addEdge(8,22,1118.6);
        g.addEdge(8,23,1566.4);
        g.addEdge(8,24,1509);

        //Add for Mumbai[9]
        g.addEdge(9,0,2176.4);
        g.addEdge(9,1,1420.2);
        g.addEdge(9,2,1153.1);
        g.addEdge(9,3,1446.7);
        g.addEdge(9,4,1795.7);
        g.addEdge(9,5,2828.4);
        g.addEdge(9,6,552.4);
        g.addEdge(9,7,776.1);
        g.addEdge(9,10,1776.1);
        g.addEdge(9,11,1591.9);
        g.addEdge(9,12,710.7);
        g.addEdge(9,13,983.9);
        g.addEdge(9,14,1336.9);
        g.addEdge(9,15,1675.8);
        g.addEdge(9,16,3060);
        g.addEdge(9,17,1101.8);
        g.addEdge(9,18,571.0);
        g.addEdge(9,17,1672.5);
        g.addEdge(9,20,1771.7);
        g.addEdge(9,21,1697.9);
        g.addEdge(9,22,2912.9);
        g.addEdge(9,23,3360.8);
        g.addEdge(9,24,3304.8);

        //Add for Srinagar[0]
        g.addEdge(0,1,812);
        g.addEdge(0,2,1095.9);
        g.addEdge(0,3,1348.6);
        g.addEdge(0,4,1881.7);
        g.addEdge(0,5,2728.8);
        g.addEdge(0,6,1625.5);
        g.addEdge(0,7,1574);
        g.addEdge(0,10,2512.1);
        g.addEdge(0,11,744.1);
        g.addEdge(0,12,2379.8);
        g.addEdge(0,13,2969);
        g.addEdge(0,14,3004);
        g.addEdge(0,16,2960.4);
        g.addEdge(0,17,2021.3);
        g.addEdge(0,18,2721.6);
        g.addEdge(0,19,566.6);
        g.addEdge(0,20,633.9);
        g.addEdge(0,21,2032.4);
        g.addEdge(0,22,2813.3);
        g.addEdge(0,23,3261.1);
        g.addEdge(0,24,3203.7);

        //Add for Roorkee[11]
        g.addEdge(11,1,200.5);
        g.addEdge(11,2,450.2);
        g.addEdge(11,3,690.9);
        g.addEdge(11,4,1224.1);
        g.addEdge(11,5,2071.1);
        g.addEdge(11,6,1100.1);
        g.addEdge(11,7,916.4);
        g.addEdge(11,10,1854.4);
        g.addEdge(11,12,1722.2);
        g.addEdge(11,13,2311.4);
        g.addEdge(11,14,2346.4);
        g.addEdge(11,15,2995.8);
        g.addEdge(11,16,2292.2);
        g.addEdge(11,17,1353.1);
        g.addEdge(11,18,2139.6);
        g.addEdge(11,19,183.8);
        g.addEdge(11,20,265.4);
        g.addEdge(11,21,1364.1);
        g.addEdge(11,22,2145.1);
        g.addEdge(11,23,2592.9);
        g.addEdge(11,24,2535.5);

        //Add for Delhi[1]
        g.addEdge(1,2,268.8);
        g.addEdge(1,3,554.1);
        g.addEdge(1,4,1087.1);
        g.addEdge(1,5,1934.3);
        g.addEdge(1,6,931);
        g.addEdge(1,7,779.5);
        g.addEdge(1,10,1717.6);
        g.addEdge(1,12,1585.3);
        g.addEdge(1,13,2174.5);
        g.addEdge(1,14,2209.5);
        g.addEdge(1,15,2858.9);
        g.addEdge(1,16,2165.9);

        //Add for Jaipur[2]
        g.addEdge(2,3,576.3);
        g.addEdge(2,4,1109.4);
        g.addEdge(2,5,1956.5);
        g.addEdge(2,6,663.1);
        g.addEdge(2,7,620.8);
        g.addEdge(2,10,1723.2);
        g.addEdge(2,12,1483.2);
        g.addEdge(2,13,2118.0);
        g.addEdge(2,14,2107.4);
        g.addEdge(2,15,2810.0);
        g.addEdge(2,16,2188.1);
        g.addEdge(2,17,1226.8);
        g.addEdge(2,18,1973.1);
        g.addEdge(2,19,246.7);
        g.addEdge(2,20,346.1);
        g.addEdge(2,21,1237.9);
        g.addEdge(2,22,2018.8);
        g.addEdge(2,23,2466.6);
        g.addEdge(2,24,2409.2);

        //Add for Lucknow[3]
        g.addEdge(3,4,534.5);
        g.addEdge(3,5,1381.6);
        g.addEdge(3,6,1154.4);
        g.addEdge(3,7,669.4);
        g.addEdge(3,10,1217.3);
        g.addEdge(3,12,1432.9);
        g.addEdge(3,13,2022.1);
        g.addEdge(3,14,2057.1);
        g.addEdge(3,15,2706.5);
        g.addEdge(3,16,1613.2);
        g.addEdge(3,17,849.6);
        g.addEdge(3,18,1889.20);
        g.addEdge(3,19,785.8);
        g.addEdge(3,20,885.1);
        g.addEdge(3,21,737.6);
        g.addEdge(3,22,1466.1);
        g.addEdge(3,23,1913.9);
        g.addEdge(3,24,1856.5);

        //Add for Patna[4]
        g.addEdge(4,5,934.2);
        g.addEdge(4,6,1689.6);
        g.addEdge(4,7,1016.6);
        g.addEdge(4,10,806.1);
        g.addEdge(4,12,1526.1);
        g.addEdge(4,13,2115.3);
        g.addEdge(4,14,2037.8);
        g.addEdge(4,15,2799.7);
        g.addEdge(4,16,1165.8);
        g.addEdge(4,17,771.6);
        g.addEdge(4,18,2127.3);
        g.addEdge(4,19,1319.3);
        g.addEdge(4,20,1418.6);
        g.addEdge(4,21,326.5);
        g.addEdge(4,22,1018.7);
        g.addEdge(4,23,1466.6);
        g.addEdge(4,24,1409.2);

        //Add for Dispur[5]
        g.addEdge(5,6,2356.9);
        g.addEdge(5,7,2051.9);
        g.addEdge(5,10,1449.8);
        g.addEdge(5,12,2754.6);
        g.addEdge(5,13,2867.0);
        g.addEdge(5,14,2681.4);
        g.addEdge(5,15,3394.2);
        g.addEdge(5,16,316.2);

        //Add for Gandhinagar[6]
        g.addEdge(6,7,604.4);
        g.addEdge(6,10,1680);
        g.addEdge(6,12,1241.9);
        g.addEdge(6,13,1515.1);
        g.addEdge(6,14,1868.1);
        g.addEdge(6,15,2207.1);
        g.addEdge(6,16,2771.3);

        //Add for Bhopal[7]
        g.addEdge(7,10,1176.4);
        g.addEdge(7,12,851.5);
        g.addEdge(7,13,1440.7);
        g.addEdge(7,14,1475.7);
        g.addEdge(7,15,2125.1);
        g.addEdge(7,16,2282.8);

        //Add for Bhubaneswar[10]
        g.addEdge(10,12,1053.3);
        g.addEdge(10,13,1418.2);
        g.addEdge(10,14,1232.7);
        g.addEdge(10,15,1945.4);
        g.addEdge(10,16,1681.2);

        //Add for Hyderabad[12]
        g.addEdge(12,13,575.5);
        g.addEdge(12,14,627.2);
        g.addEdge(12,13,1259.9);
        g.addEdge(12,16,2712.6);

        //Add for Bangalore[13]
        g.addEdge(13,14,345.7);
        g.addEdge(13,15,686.4);
        g.addEdge(13,16,3098.1);

        //Add for Chennai[14]
        g.addEdge(14,15,725.4);
        g.addEdge(14,16,2908.3);

        //Add for Thiruvananthapuram[15]
        g.addEdge(15,16,3622.0);

        g.modifyEdgeCost(data,low,high,BP,c1,c2,c3,c4,c5,c6,date);
        return g;
    }
    void modifyEdgeCost(double data[][],int l,int h,String BP,boolean c1, boolean c2, boolean c3, boolean c4, boolean c5, boolean c6,
    String date)
    {
        int i;
        double weight;
        for(i=0;i<V;i++)
        {
            ListIterator itr=adj[i].listIterator();
            while(itr.hasNext())
            {
                Pair temp2=(Pair)itr.next();
                weight=temp2.w;
                if(data[temp2.v][0]<l) //Rule 1:Temperature less than min_comf, edge cost incremented by 50*DISCOMFORT
                {
                    weight = (l-data[temp2.v][0]) * 50 + weight;
                }
                else if(data[temp2.v][0]>h)//Rule 2:Temperature greater than max_comf, edge cost incremented by param(initially 50)*DISCOMFORT
                {
                    int param=50;
                    if(c1)//Rule 2.1:If history of heatstroke,param increased by 70
                        param+=70;
                    if(c2)//Rule 2.2:If history of faintings,param increased by 50
                        param+=50;
                    if(c5)//Rule 2.3:Extra penalty for afflicted persons
                        param+=40;
                    weight = (data[temp2.v][0] - h) * param + weight;
                }
                else//Rule 3:Penalizing heatstroke prone persons in a similar, yet lessser magnitude,even within comfort zone
                {
                    int param=0;
                    if(c1)//Rule 3.1:If history of heatstroke,param increased by 50
                        param+=50;
                    if(c2)//Rule 3.2:If history of faintings,param increased by 30
                        param+=30;
                    if(c5)//Extra penalty for afflicted persons
                        param+=20;
                    weight = (data[temp2.v][0] - max_comf) * param + weight;
                }
                if(data[temp2.v][1]<40) //Rule 4:Low humidity is penalised.by 20*DISCOMFORT
                {
                    weight = (40 - data[temp2.v][1]) * 20 + weight;
                    if(c4)//Rule 4.1:Extra penalty for afflicted persons
                        weight=(40 - data[temp2.v][1]) * 30 + weight;
                }
                else if(data[temp2.v][1]>70)
                {
                    weight = (data[temp2.v][1]-70) * 20 + weight;
                }
                weight=weight-20*data[temp2.v][3];//Rule 5: Decrease cost based on relative comforts
                try
                {
                    if(!date.equals(""))
                    {
                        String str=date.substring(date.lastIndexOf("/")+1);
                        int k=Integer.parseInt(str);
                        Calendar currentTime = Calendar.getInstance();
                        currentTime.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                        int x=currentTime.get(Calendar.YEAR);
                        if((x-k)>=60)
                            weight=weight-30*data[temp2.v][3];//Rule 5.1:Comfort evden more impotant for elderly
                    }
                }catch (Exception e)
                {
                    Log.e("Date exception",e.getMessage());
                }
                temp2.w=weight;
                itr.remove();
                itr.add(temp2);
            }
        }
    }
    @SuppressWarnings("unchecked")
    void shortestPath(int u,int dest,double dist[],int par[])
    {
        PriorityQueue<Pair> pq = new PriorityQueue(V,new Comparator<Pair>()
        {
            public int compare(Pair a1, Pair a2)
            {
                return (a1.w-a2.w)>0?1:-1;
            }
        }
        );
        //double dist[]=new double[V];
        int i;
       /* for(i=0;i<V;i++)
        {
            Iterator itr=adj[i].iterator();
            while(itr.hasNext())
            {
                Pair temp2=(Pair)itr.next();
                //System.out.println(i+": "+temp2.v+" "+temp2.w);
            }
        }*/
        for(i=0;i<V;i++)
            dist[i]=Integer.MAX_VALUE;
        dist[u]=0;
        pq.add(new Pair(u,0));
        while(!pq.isEmpty())
        {
            Pair temp=pq.poll();
            int vertex=temp.v,v1;
            double weight=temp.w,w1;
            //System.out.println(vertex+" "+weight);
            Iterator itr=adj[vertex].iterator();
            while(itr.hasNext())
            {
                Pair temp2=(Pair)itr.next();
                v1=temp2.v;
                w1=temp2.w;
                if(dist[v1]>w1+dist[vertex])
                {
                    dist[v1]=w1+dist[vertex];
                    pq.add(new Pair(v1,dist[v1]));
                    par[v1]=vertex;
                }
            }
        }
        for(i=0;i<V;i++)
            if(dist[i]==Integer.MAX_VALUE)
                System.out.print("1000000000 ");
            else
                System.out.print(dist[i]+" ");
        //printSolution(dist);
    }
    public double getDistance(int arr[])
    {
        int i,v1;
        double w1,dist=0.0;
        for(i=0;i<arr.length-1;i++) {
            Iterator itr = adj[arr[i]].iterator();
            while (itr.hasNext())
            {
                Pair temp2=(Pair)itr.next();
                v1=temp2.v;
                w1=temp2.w;
                if(v1==arr[i+1])
                {
                    dist+=w1;
                    break;
                }
            }
        }
        return dist;
    }
}