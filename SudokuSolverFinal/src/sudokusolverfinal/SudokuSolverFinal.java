/*
 * Brian Spann (rbs4ba) & Jessica Ya (jy2fv), Final Theory Project
 */
package sudokusolverfinal;
import java.awt.Color;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** TODO:
 * At beginning of while loop in main, clear all the boards holding old test case values
 * XAdd stuff for nonominos
 * XChange to only read in nonominos file when color = true
 */
public class SudokuSolverFinal {
    
    public static square [][] board;
    public static square [][] origBoard;
    public static Map<Integer,List<Integer>> conflictsList;
    public static JFrame frame; //allows these to be changed outside the method
    public static JPanel panel;
    public static Board b;
    public static Board b2;
    public static JButtonClass jb;
    private static final Color[] COLORS = { 
        new Color(255,255,255), new Color(255,51,51), new Color(51,255,255), 
        new Color(255,153,51), new Color(51,153,255), new Color(255,255,51), 
        new Color(51,51,255), new Color(153,255,51), new Color(153,51,255), 
        new Color(51,255,51), new Color(255,51,255), new Color(51,255,153), 
        new Color(255,51,153), new Color(255,153,153), new Color(153,255,255), 
        new Color(255,204,153), new Color(153,204,255), new Color(255,255,153), 
        new Color(153,153,255), new Color(204,255,153), new Color(204,153,255), 
        new Color(153,255,153), new Color(255,153,255), new Color(153,255,204), 
        new Color(255,153,204), new Color(204,0,0), new Color(0,204,204), 
        new Color(204,102,0), new Color(0,102,204), new Color(204,102,0), 
        new Color(0,102,204), new Color(204,204,0), new Color(0,0,204), 
        new Color(102,204,0), new Color(102,0,204), new Color(0,204,0), 
        new Color(204,0,204), new Color(0,204,102), new Color(204,0,102), 
        new Color(255,102,102), new Color(102,255,255), new Color(255,178,102), 
        new Color(102,178,255), new Color(255,255,102), new Color(102,102,255), 
        new Color(178,255,102), new Color(178,102,255), new Color(102,255,102), 
        new Color(255,102,255), new Color(102,255,178), new Color(255,102,178), 
        new Color(153,0,0), new Color(0,153,153), new Color(153,76,0), 
        new Color(0,76,153), new Color(153,153,0), new Color(0,0,153), 
        new Color(76,153,0), new Color(76,0,153), new Color(0,153,0), 
        new Color(153,0,153), new Color(0,153,76), new Color(153,0,76), 
        new Color(255,0,0), new Color(0,255,255), new Color(255,128,0), 
        new Color(0,128,255), new Color(255,255,0), new Color(0,0,255), 
        new Color(128,255,0), new Color(127,0,255), new Color(0,255,0), 
        new Color(255,0,255), new Color(0,255,128), new Color(255,0,127), 
        new Color(255,204,204), new Color(204,255,255), new Color(255,229,204), 
        new Color(204,229,255), new Color(255,255,204), new Color(204,204,255), 
        new Color(229,255,204), new Color(229,204,255), new Color(204,255,204), 
        new Color(255,204,255), new Color(204,255,229), new Color(255,205,229) 
    };
    
    public class square{
        private int value; //value for the square on the board
        private int position; //decimal val of the position
        private List<Integer> options;
        private int c; ////for nonominos
        
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
        public int getColor() { ////for nonominos
            return c;
        }
        public void setColor(int index) {
            this.c = index;
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
     * @param n 
     * @param line 
     * Either frame.dispose()/frame.setVisible(false) OR update current frame
     */
    public static void createGUI(square[][] values, int n, String line, boolean nonominos) {
    //GUI CODE -----------------------------------------------------
        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        jb = new JButtonClass(line);
        int root = (int) Math.sqrt(n);
        
        b = new Board(n);
        Squares[] box = b.getGrid();
        for(int l = 0; l < root; l++){     //choose big row
            for(int i = 0; i < root; i ++) {     //chooses big col
                JTextField[] f = box[l*root+i].getJTextFieldArray();
                for (int j = 0; j < root; j++) {      //chooses little row
                    for (int k = 0; k < root; k++) {     //chooses little col
                        int pos = (l*root*n)+ (j*n) + (i*root) + k;
                        if(values[pos/n][pos%n].getValue() != 0) {
                            f[j*root+k].setText(Integer.toString(values[pos/n][pos%n].getValue()));
                        } else {
                            f[j*root+k].setText(" ");
                        }
                        if(nonominos == true) {
                            f[j*root+k].setBackground(COLORS[values[pos/n][pos%n].getColor()]);
                        }
                    }
                }
            }
        }
        
        b2 = new Board(n);
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
        String tempArr[] = {"0","0"}; //for nonominos
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
        boolean color = false; //for nonominos
	
        while(fileScnr.hasNext()){
            //Detect board size
            if(fileScnr.hasNext()){
		tempLine = fileScnr.next();
		tempLine = tempLine.trim();
            }
            
            while(tempLine.isEmpty()){
		tempLine = fileScnr.nextLine();
		tempLine = tempLine.trim();
            }
            n = Integer.parseInt(tempLine); //nonominos
            if(n <= 0){
                System.out.println("\nA \"0\" was detected. \nExiting");
                return;
	    }
            //Check if normal or nonomino board
            if(fileScnr.hasNext()){
		tempLine = fileScnr.next();
		tempLine = tempLine.trim();
            }
            
            while(tempLine.isEmpty()){
		tempLine = fileScnr.nextLine();
		tempLine = tempLine.trim();
            }
            
            if(Integer.parseInt(tempLine) == -2) { //for nonominos
                color = true;
            } else { 
               color = false;
            }
            
            //If didnt exit, then continue w/ the solver w/ a valid board
            
            numRows = n;
            numCols = n;
            int sqrtN = (int)Math.sqrt(n);
            int curVal = 0;
            int curColor = 0; //nonominos
            board = new square [numRows][numCols];
            origBoard = new square[numRows][numCols];
            List<Integer> tempList = new ArrayList<Integer>();
            for(int i = 0; i < numRows; i++){
                tempList.add(i);
            }
            conflictsList = new HashMap<Integer, List<Integer>>();

             
            
            //Added code for origBoard
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
                    //System.out.print("i: " + i + ", j: " + j + " = ");
                    square s1 = ssf1.new square(-1,-1,tempList);
                    square s2 = ssf1.new square(-1, -1, tempList);
                    
                    board[i][j] = s1;
                    origBoard[i][j] = s2;
                    if(fileScnr.hasNext()){  //might be able to remove this if stmt
                        tempLine = fileScnr.next();
                        tempLine = tempLine.trim();
                    }
                    while(tempLine.isEmpty()){
                        tempLine = fileScnr.next();
                        tempLine = tempLine.trim();
                    }
                    int index = tempLine.indexOf(","); //nonominos
                    if(index != -1) { //
                        String num = tempLine.substring(0, index); //
                        curColor = Integer.parseInt(tempLine.substring(index+1)); //
                        curVal = Integer.parseInt(num);//
                        board[i][j].setColor(curColor);//
                        origBoard[i][j].setColor(curColor); //
                    } else {
                        curVal = Integer.parseInt(tempLine);
                    }
                    board[i][j].setValue(curVal);// = Integer.parseInt(tempLine);
                    origBoard[i][j].setValue(curVal);
                    board[i][j].setPosition(i*numCols + j);
                    origBoard[i][j].setPosition(i*numCols + j);
                    if(curVal != 0){
                        List<Integer> tempList2 = new ArrayList<Integer>();
                        tempList2.clear();
                        tempList2.add(curVal);
                        board[i][j].setOptions(tempList2);
                        origBoard[i][j].setOptions(tempList2);
                    } else{
                        board[i][j].setOptions(tempList);
                        origBoard[i][j].setOptions(tempList);
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
            
            //printBoard(origBoard, numRows, numCols);
            
            createGUI(origBoard, numCols, "SOLVE", color);
            
            //ActionListener for button clicked
            while(!jb.clicked()) {
                //(run all the code to solve the problem)  
                System.out.println("SOLVING!");
            }
            System.out.println("CLICKED!");  
        
        //CODE TO SOLVE SUDOKU
            //jb.changeText("SOLVED");
            
        //THIS CODE changes the second box of the GUI after solving the puzzle.    
        int root = (int) Math.sqrt(n);    
        Squares[] box = b2.getGrid();
        for(int l = 0; l < root; l++){     //choose big row
            for(int i = 0; i < root; i ++) {     //chooses big col
                JTextField[] f = box[l*root+i].getJTextFieldArray();
                for (int j = 0; j < root; j++) {      //chooses little row
                    for (int k = 0; k < root; k++) {     //chooses little col
                        int pos = (l*root*n)+ (j*n) + (i*root) + k;
                        if(board[pos/n][pos%n].getValue() != 0) {
                            f[j*root+k].setText(Integer.toString(board[pos/n][pos%n].getValue()));
                        } else {
                            f[j*root+k].setText(" ");
                        }
                        if(color == true) {
                            f[j*root+k].setBackground(COLORS[board[pos/n][pos%n].getColor()]);
                        }
                    }
                }
            }
        }
            
            //createGUI(board, numCols, "SOLUTION"); //again
            
        }
    }
}
