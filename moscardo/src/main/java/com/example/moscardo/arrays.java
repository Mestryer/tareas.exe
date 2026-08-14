package com.example.moscardo;

public class arrays {

    public static int[] ejemplovector(){
        int[] v={12, 12, 4 , 7 ,19};
        System.out.println(v.length);
        v[2]=15;

        return v;
    }
    public static void print(int[] v){
        for (int i= 0; i < v.length; i++){
            System.out.println(v[i]+ "\t");
        }
    }
     public static void printmatriz(int [][] m){
        for (int f = 0; f < m.length; f++ ){
            for (int c = 0 ; c < m[0].length; c++){
                System.out.print(m[f][c]+ "\t");
            }
            System.out.println("");
        }
     }

     public static int[][] matrizF5 (int tam, int numI){
        int [][] m = new int [tam][tam];
        for (int i = 0; i< m.length; i++ ){
            for (int c= m[0].length-1;c >= m[0].length-1-i; c--){
                m[i][c]= numI;
                numI ++;
            }
        }
        return m;
     }

    public static void main(String[] args) {
        //print(ejemplovector());
        System.out.println("");
        //int [][] m = {{2,9}, {5,7}, {6,47}};
        printmatriz(matrizF5(5,0 ));
    }
}
