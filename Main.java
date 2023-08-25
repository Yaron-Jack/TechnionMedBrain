package com.company;

public class Main {
    public static int MazeSum(int[][] a) {
        return MazeSum(a,a,a,a,a,a, 0, 0,0,0,0,0,0);
    }

    public static int MazeSum(int[][] a,int[][] changeMaze, int[][] rightMaze, int[][] leftMaze, int[][] downMaze, int[][] upMaze, int i, int j,int addChange,int addR,int addL,int addU,int addD) {
        final int BEEN_HERE = -10;
        final int PRIZE = 10;
        final int NO_PATH = 100;
        final int cancelRoute = 10000;

        if (i > a.length - 1 || j > a.length - 1 || i < 0 || j < 0 || a[i][j] == BEEN_HERE || a[i][j] == NO_PATH)
            return cancelRoute;

        if (i == a.length - 1 && j == a.length - 1) {
            int endTemp = a[i][j];
            a[i][j] =  PRIZE;
            return endTemp;
        }


        finalMaze(changeMaze,i,j,addChange);


        int temp = a[i][j];
        a[i][j] = BEEN_HERE;


        /**The more options the more the reward */
        int right = 1 + MazeSum(a,rightMaze,rightMaze,leftMaze,downMaze,upMaze ,i+1, j + 1,Math.min(i+1,PRIZE),addR,addL,addU,addD);
        int left = 1 + MazeSum(a,leftMaze,rightMaze,leftMaze,downMaze,upMaze ,i-1, j - 1,Math.min(Math.abs(j+1),PRIZE),addR,addL,addU,addD);
        int up = 1 + MazeSum(a,upMaze,rightMaze,leftMaze,downMaze,upMaze ,i-1, j+1,Math.min(Math.abs(i+1),PRIZE),addR,addL,addU,addD);
        int down = 1 + MazeSum(a,downMaze,rightMaze,leftMaze,downMaze,upMaze ,i+1, j-1,Math.min(j+1,PRIZE),addR,addL,addU,addD);


        a[i][j] = temp;


        int min = Math.min(Math.min(right, left), Math.min(up, down));
        if (min < cancelRoute && a[a.length-1][a.length-1] ==  PRIZE && a[0][0] != BEEN_HERE )
        {
            if (right == min) {
                printMaze(rightMaze, 0, 0);
                deleteBadPathsR(a);
            }
            if (left == min)
                printMaze(leftMaze, 0, 0);
            if (up == min)
                printMaze(upMaze, 0, 0);
            if (down == min)
                printMaze(downMaze, 0, 0);
        }


        return min;
    }
    public static boolean wereIveBeen(int[][] mat,int sum,int i,int j)
    {
        if(i == 0 && j == 0)
            System.out.println("**************\n" + sum + "\n**************");
        if(i == mat.length)
            return false;
        if(j == mat.length )
            return wereIveBeen (mat,sum,i+1,0);
        if(sum == mat[i][j] && i == mat.length-1 && j == mat[i].length-1)//where the prize is
        {
            //printMaze(mat, 0, 0);

            return true;
        }


        return wereIveBeen (mat,sum - mat[i][j],i,j+1) ||  wereIveBeen(mat,sum,i,j+1);//FIND A WAY TO SEND THE TRUE/FALSE PATH AND CHANGE THE NUMBERS
    }

    private static void printMaze(int[][] m, int row, int col) {
        if (row < m.length) {
            if (col < m.length) {
                System.out.print(m[row][col] + "\t");
                printMaze(m, row, col + 1);
            }
            if (col == m.length) {
                System.out.println("");
                printMaze(m, row + 1, 0);
            }
        } else
            System.out.println("");

    }
    private static void finalMaze(int[][] mat,int i,int j,int add)
    {
        mat[i][j] =  add;
    }

    private static void deleteBadPathsR(int[][] a)
    {
        for(int  i = 0;i < a.length;i++) {
            for (int j = 0; j < a.length; j++) {
                if (!(i == j))
                    a[i][j] = 1;
            }
        }
        printMaze(a,0,0);
    }



    public static void main(String[] args) {
        int[][] a = { { 1,1, 1, 1, 1,1 }, {1, 1, 1, 1 ,1,1 }, {1,1, 1, 1, 1 ,1 },{1,1, 1, 1, 1 ,1 },{1,1, 1, 1, 1 ,1 },{1, 1, 1, 1, 1, 10 }};
        //System.out.println("**************\n" + MazeSum(a) + "\n**************");
        System.out.println(wereIveBeen(a,MazeSum(a),0,0));
        //deleteBadPaths(a);
    }

}
/**
 * TODO understand what your'e sending to the function and how it changes so you can better work out how to create a good path that only changes in a certain direction
 * Also list of assumptions -
 * A path consists only of itself and no other
 * currently deleteBadPathsR function only works on the right path and will need duplicates for others or an extreme change to fit all 
 */