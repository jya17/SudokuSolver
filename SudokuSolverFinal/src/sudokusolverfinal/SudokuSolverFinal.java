/*
 * Brian Spann (rbs4ba) & Jessica Ya (jy2fv), Final Theory Project
 */
package sudokusolverfinal;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SudokuSolverFinal {
    
    public static square [][] board;
    public static Map<Integer,List<Integer>> conflictsList;
    
    
    public class square{
        private int value; //value for the square on the board
        private int position; //decimal val of the position
        private List<Integer> options;
        
        public square(int value, int position, List<Integer> options){
            this.value = value;
            this.position = position;
            this.options = options;
        }
        public int getValue(){
            return value;
        }
        public void setValue(int value){
            this.value = value;
        }
        public int getPosition(){
            return position;
        }
        public void setPosition(int position){
            this.position = position;
        }
        public List<Integer> getOptions(){
            return options;
        }
        public void setOptions(List<Integer> options){
            this.options = options;
        }
    }
    
    public static void printBoard(square [][] numBoard, int rows, int cols){
        System.out.println("-------------------");
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(j == 0){
                    System.out.print("|");
                }
                if(j == cols-1){
                    System.out.print(numBoard[i][j].value + "|");
                } else if(j == 2 || j == 5){
                    System.out.print(numBoard[i][j].value + "|");
                } else{
                    System.out.print(numBoard[i][j].value + " ");
                }
            }
            if(i == 2 || i == 5){
                System.out.println();
                System.out.println("|-----+-----+-----|");
            } else{
                System.out.println();
            }
        }
        System.out.println("-------------------");
    }
    
    public static void printConflicts(Map<Integer, List<Integer>> confList, int rows, int cols){
        List<Integer> confls = new ArrayList<Integer>();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                confls = confList.get(i*cols+j);
                System.out.println("conflicts for (" + i + ", " + j + "): " + confls);
                
                boolean tempCheck = false;
                System.out.println("-------------------");
                for(int k = 0; k < rows; k++){
                    for(int l = 0; l < cols; l++){
                        if(l == 0){
                            System.out.print("|");
                        }
                        if(l == cols-1){
                            for(int x: confls){
                                if(k*cols+l == x){
                                    System.out.print((k*cols+l) +"|");
                                    tempCheck = true;
                                }
                            }
                            if(tempCheck == false)
                                System.out.print("X|");
                            tempCheck = false;
                        } else if(l == 2 || l == 5){
                            for(int x: confls){
                                if(k*cols+l == x){
                                    System.out.print((k*cols+l) +"|");
                                    tempCheck = true;
                                }
                            }
                            if(tempCheck == false)
                                System.out.print("X|");
                            tempCheck = false;
                        } else{
                            for(int x: confls){
                                if(k*cols+l == x){
                                    System.out.print((k*cols+l) +" ");
                                    tempCheck = true;
                                }
                            }
                            if(tempCheck == false)
                                System.out.print("X ");
                            tempCheck = false;
                        }
                    }
                    if(k == 2 || k == 5){
                        System.out.println();
                        System.out.println("|-----+-----+-----|");
                    } else{
                        System.out.println();
                    }
                }
                System.out.println("-------------------");
            }
        }
    }
    
    /**
     * Creates the GUI Board
     * @param values 
     */
    public static void createGUI(square[][] values, int n) {
    //GUI CODE -----------------------------------------------------
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButtonClass jb = new JButtonClass("SOLVE");
        
        Board b = new Board(n);
        Board b2 = new Board(n);
        //put values from square[][] into Board
        panel.add(b);
        panel.add(jb);
        panel.add(b2);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SudokuSolverFinal ssf1 = new SudokuSolverFinal();
        
        //LOGIC CODE ---------------------------------------------------
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
            int sqrtN = (int)Math.sqrt(n);
            int curVal = 0;
            board = new square [numRows][numCols];
            List<Integer> tempList = new ArrayList<Integer>();
            for(int i = 0; i < numRows; i++){
                tempList.add(i);
            }
            conflictsList = new HashMap<Integer, List<Integer>>();

             
            
            
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
                    //System.out.print("i: " + i + ", j: " + j + " = ");
                    square s1 = ssf1.new square(-1,-1,tempList);
                    
                    board[i][j] = s1;
                    if(fileScnr.hasNext()){  //might be able to remove this if stmt
                        tempLine = fileScnr.next();
                        tempLine = tempLine.trim();
                    }
                    while(tempLine.isEmpty()){
                        tempLine = fileScnr.next();
                        tempLine = tempLine.trim();
                    }
                    curVal = Integer.parseInt(tempLine);
                    board[i][j].setValue(curVal);// = Integer.parseInt(tempLine);
                    board[i][j].setPosition(i*numCols + j);
                    if(curVal != 0){
                        List<Integer> tempList2 = new ArrayList<Integer>();
                        tempList2.clear();
                        tempList2.add(curVal);
                        board[i][j].setOptions(tempList2);
                    } else{
                        board[i][j].setOptions(tempList);
                    }
                }
                //System.out.println();
            }


            int rowQuad = -1;
            int colQuad = -1;
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
                    List<Integer> tempConflicts = new ArrayList<Integer>();
                    tempConflicts.clear();
                    for(int k = 0; k < numCols; k++){
                        if(j != k)
                            tempConflicts.add(i*numCols + k);
                        if(i != k)
                            tempConflicts.add(k*numRows + j);
                    }
                    rowQuad = (i/sqrtN);
                    colQuad = (j/sqrtN);
                    
                    for(int k = rowQuad*sqrtN; k < (rowQuad+1)*sqrtN; k++){
                        for(int l = colQuad*sqrtN; l < (colQuad+1)*sqrtN; l++){
                            if(i != k && j != l)
                                tempConflicts.add(k*numCols + l);
                        }
                    }
                    conflictsList.put(i*numCols+j, tempConflicts);
                }
            }
            
            printBoard(board, numRows, numCols);
            //printConflicts(conflictsList, numRows, numCols);
            
            createGUI(board, numCols);
        }
    }
}
