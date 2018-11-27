

/**
 *
 * @author Evan
 */
import java.util.Scanner;
import java.lang.Math;
public class MinimizeBandwidth {

    public static void main(String[] args) {
       
       Scanner sc = new Scanner(System.in);
       System.out.println("Enter number of vertices");
       int numVertices=sc.nextInt();
       System.out.println("enter number of edges");
       int numEdges=sc.nextInt();
       long startTime=System.currentTimeMillis();
       int[][] adjMatrix = new int[numVertices][numVertices];
       int[] numDegree = new int[numVertices];
       for (int i=0; i<numEdges; i++){
           int a=sc.nextInt();
           int b=sc.nextInt();
           numDegree[a-1]++;
           numDegree[b-1]++;
           adjMatrix[a-1][b-1]=1;
           adjMatrix[b-1][a-1]=1;
           //System.out.println(i);
       }
       int max=0;
 
       for (int i=0; i<numVertices; i++){
           if (numDegree[i]>max){
               max=numDegree[i];

           }

       }
       

       int[] verticeArr = new int[numVertices];
       int[] minVerticeArr = new int[numVertices];
       for (int i=0; i<numVertices; i++){
           verticeArr[i]=i;
           minVerticeArr[i]=i;
       }
        System.out.println("Computing...");
        if (max%2==1)
            max+=1;
        max/=2;
       int minBandwidth = computeBandwidth(adjMatrix, numVertices, Integer.MAX_VALUE,0,numVertices-1,verticeArr,max,minVerticeArr);
      
       System.out.println("\nMinimum bandwidth is " +minBandwidth+""
               + "\nTotal time: " +(double)(System.currentTimeMillis()-startTime)/1000+" seconds");
       System.out.println("Min bandwidth permutation:");
       for (int i=0; i<numVertices; i++){
           System.out.print(minVerticeArr[i]+1+" ");
       }
    }
    public static int  computeBandwidth(int[][] matrix, int n,int minCost,int a, int b,int[] verticeArr,int lowerBound,int[] minVerticeArr){

        if (minCost==lowerBound)/* the LOWER BOUND on the bandwidth of a graph is equal to the highest degree vertice over 2.
            This is because the max degree vertice would have to have, at best half that many nodes to go to in each direction.
            */
            return minCost;

      
        int cost=0;
        boolean toBreak=false;
        /*Loop to determine the bandwidth of this permutation*/
       if (verticeArr[0]<verticeArr[n-1]) //By symmetry, we can skip permutations where the first is less than the last
        for (int i=0; i<n-1; i++){
            for (int j=n-1; j>i; j--){
                if (connected(matrix,verticeArr[i],verticeArr[j])){
                    if (cost<Math.abs(i-j))
                        cost=Math.abs(i-j);
                    if (cost>=minCost){
                        toBreak=true;
                        break;
                        /*If we've already gone past the previous minimum               
            cost, no need to continue.
                    */
                    }
                }
            }
            if (toBreak)break;
        }
       else cost=minCost;
       /*If we've found a new minimum, update.*/
        if (minCost>cost){
       
           for (int i=0; i<n; i++){
               minVerticeArr[i]=verticeArr[i];
           }
            minCost=cost;
        }
        
        //next permutation
        if (a!=b){  
            for (int i=a; i<=b;i++){
                swap(verticeArr,a,i);
                minCost=computeBandwidth(matrix,n,minCost,a +1,b,verticeArr,lowerBound,minVerticeArr);
                if (minCost==lowerBound)
                    return minCost;
                swap(verticeArr,a,i);
            }
        }
        return minCost;        
    }
    public static void swap(int[] arr, int i, int j){
  
        try{
            
            int temp = arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
          
        }
      
    }
    public static boolean connected(int[][] adjMatrix, int a, int b){
        try{
        return adjMatrix[a][b]==1;
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("a is " + a + " b is " + b);
            return false;
        }
    }
}
