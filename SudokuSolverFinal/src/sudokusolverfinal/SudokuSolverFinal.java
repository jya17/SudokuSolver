/*
rian Spann (rbs4ba) & Jessica Ya (jy2fv), Final Theory Project
 */
package sudokusolverfinal;
import java.util.Scanner;
import java.io.File;


public class SudokuSolverFinal {
    
    public static void printBoard(int [][] board, int rows, int cols){
        System.out.println("- - - - - - - - - - -");
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(j == 0){
                    System.out.print("||");
                }
                if(j == cols-1){
                    System.out.print(board[i][j] + "||");
                } else if(j == 2 || j == 5){
                    System.out.print(board[i][j] + "|");
                } else{
                    System.out.print(board[i][j] + " ");
                }
            }
            if(i == 2 || i == 5){
                System.out.println();
                System.out.println("|| - - - - - - - - ||");
            } else{
                System.out.println();
            }
        }
        System.out.println("- - - - - - - - - - -");
    }

    public static void main(String[] args) {
        String fileName = "";
	try{
            fileName = args[0];
	} catch(Exception e){
            System.out.println("Missing a command-line argument. \n Exiting.");
            return;
	}
	System.out.println("Using the following text file as an input source: " + fileName + "\n");
		
	String tempLine = "";
	Scanner fileScnr = null;
	File theFile = new File(fileName);
	try{
            fileScnr = new Scanner(theFile);
	} catch(Exception e){
            System.out.println("Could not view the file: " + fileName + ". \nExiting");
            return;
        }
      
        
	int numRows = 0;
        int numCols = 0;
        int n = 0;
        int [][] board;
        int o = 0;
	
        while(fileScnr.hasNext()){
            //Detect board size
            if(fileScnr.hasNext()){
		tempLine = fileScnr.nextLine();
		tempLine = tempLine.trim();
            }
            while(tempLine.isEmpty()){
		tempLine = fileScnr.nextLine();
		tempLine = tempLine.trim();
            }
            n = Integer.parseInt(tempLine);
            if(n <= 0){
                System.out.println("\nA \"0\" was detected. \nExiting");
                return;
	    }
            
            //If didnt exit, then continue w/ the solver w/ a valid board
            
            numRows = n;
            numCols = n;
            board = new int [numRows][numCols];
            
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
                    if(fileScnr.hasNext()){  //might be able to remove this if stmt
                        tempLine = fileScnr.next();
                        tempLine = tempLine.trim();
                    }
                    while(tempLine.isEmpty()){
                        tempLine = fileScnr.next();
                        tempLine = tempLine.trim();
                    }
                    board[i][j]= Integer.parseInt(tempLine);
                }
            }

            printBoard(board, numRows, numCols);
            
        }
    }
}
