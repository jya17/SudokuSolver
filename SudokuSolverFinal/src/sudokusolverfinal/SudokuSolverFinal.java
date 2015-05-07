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
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** TODO:
 * At beginning of while loop in main, clear all the boards holding old test case values
 * XAdd stuff for nonominos
 * XChange to only read in nonominos file when color = true
 */
public class SudokuSolverFinal {
    
    private static square [][] board;
    private static square [][] origBoard;
    private static Map<Integer,List<Integer>> conflictsList;
    private static Map<Integer,List<Integer>> sortedConflictsList;
    private static SudokuSolverFinal ssf1 = new SudokuSolverFinal();
    private static JFrame frame; //allows these to be changed outside the method
    private static JPanel panel;
    private static Board b;
    private static Board b2;
    private static JButtonClass jb;
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
    
    public class node{
        private node left;
        private node right;
        private node up;
        private node down;
        private int val;
        
        public node(node left, node right, node up, node down){
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
        }
        public node(node left, node right, node up, node down, int val){
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
            this.val = val;
        }
        public node getLeft(){
            return left;
        }
        public void setLeft(node left){
            this.left = left;
        }
        public node getRight(){
            return right;
        }
        public void setRight(node right){
            this.right = right;
        }
        public node getUp(){
            return up;
        }
        public void setUp(node up){
            this.up = up;
        }
        public node getDown(){
            return down;
        }
        public void setDown(node down){
            this.down = down;
        }
        public int getVal(){
            return val;
        }
        public void setVal(int val){
            this.val = val;
        }
    }
    public class baseNode extends node implements Comparable<baseNode>{ //make all col and row headers as base nodes
        private int cand;
        private boolean sat;
        private String name;
        private int numNodes;
        private int rowPos;
        private int colPos;
        public baseNode(node left, node right, node up, node down, int cand, boolean sat, String name, int numNodes, int rowPos, int colPos){
            super(left, right, up, down);
            this.cand = cand;
            this.sat = sat;
            this.name = name;
            this.numNodes = numNodes;
            this.rowPos = rowPos;
            this.colPos = colPos;
        }        
        public baseNode(node left, node right, node up, node down, int val, int cand, boolean sat, String name, int numNodes){
            super(left, right, up, down, val);
            this.cand = cand;
            this.sat = sat;
            this.name = name;
            this.numNodes = numNodes;
        }     
        public int getCand() {
            return cand;
        }
        public void setCand(int cand) {
            this.cand = cand;
        }
        public boolean getSat() {
            return sat;
        }
        public void setSat(boolean sat) {
            this.sat = sat;
        }
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
        public int getNumNodes(){
            return numNodes;
        }
        public void setNumNodes(int numNodes){
            this.numNodes = numNodes;
        }
        public int getRowPos(){
            return rowPos;
        }
        public void setRowPos(int rowPos){
            this.rowPos = rowPos;
        }
        public int getColPos(){
            return colPos;
        }
        public void setColPos(int colPos){
            this.colPos = colPos;
        }
        public int compareTo(baseNode other){
            return numNodes - other.numNodes;
        }
    }
    
    public class square implements Comparable<square>{
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
        public int compareTo(square other){
            int result = options.size() - other.options.size();
            if(result != 0){
                return result;
            }
            result = other.value - value;
            return result;
        }
        public int getColor() { ////for nonominos
            return c;
        }
        public void setColor(int index) {
            this.c = index;
        }
    }
    
    public class boolSquare{
        private List<square> valuesList;
        private boolean updateResult;
        
        public boolSquare(List<square> valuesList, boolean updateResult){
            this.valuesList = valuesList;
            this.updateResult = updateResult;
        }
        public List<square> getValuesList(){
            return valuesList;
        }
        public void setValuesList(List<square> valuesList){
            this.valuesList = valuesList;
        }
        public boolean getUpdateResult(){
            return updateResult;
        }
        public void setUpdateResult(boolean updateResult){
            this.updateResult = updateResult;
        }
    }


    public static List<baseNode> solve(List<baseNode> sortedList){
        List<baseNode> solNodes = new ArrayList<baseNode>(); //will hold all of the baseNodes for a row for the solution
        int[] satConts = new int[(sortedList.size()-1)/4];
        
        for(int i = 0; i < (sortedList.size()-1)/4; i++){
            baseNode dummyNode = ssf1.new baseNode(null, null, null, null, -72, false, "dummy node", 0, 0, 0);
            //solNodes.add(dummyNode);
            satConts[i] = 0;
        }
        
        
        System.out.println(((baseNode)sortedList.get(1)).getNumNodes());
        
        solNodes = solve_recur(sortedList, solNodes, satConts, 1);
        
        System.out.println("bouta try and print solNodes");
            for(int i = 0; i < solNodes.size(); i++){
                System.out.print(solNodes.get(i).getCand() + " ");
                if(i%9 == 9-1){
                    System.out.println();
                }
            }
        System.out.println("finished printing solNodes");
        
        return solNodes;
    }
    
    public static List<baseNode> solve_recur(List<baseNode> sortedList, List<baseNode> solNodes, int[] satConts, int curPos){
        while(sortedList.get(curPos).getSat() == true){ //finds a column that has not been satisfied yet
            curPos++;
        }
        if(sortedList.get(curPos).getDown() == null){ //failed somewhere. need to go back up a level
            
            System.out.println("inside of if");
            
            List<baseNode> tempFail = new ArrayList<baseNode>(); //will hold temp possible solutions. or indication that we failed
            for(int j = 0; j < 1; j++){
                baseNode dummyNode = ssf1.new baseNode(null, null, null, null, 0, false, "dummy node", 0, 0, 0);
                tempFail.add(dummyNode);
            }
            tempFail.get(0).setVal(-10);
            return tempFail;
        } else{ //didnt fail. try adding the row to the sols
            
            System.out.println("inside of else");
            
            sortedList.get(curPos).setSat(true);
            baseNode colNode = sortedList.get(curPos); //baseNode of the column we are about to cover
            node rowNode = colNode;
            int tempPossibs = sortedList.get(curPos).getNumNodes();
            for(int i = 0; i < tempPossibs; i++){
                
                System.out.println("inside of for");
                
            //if(){
                rowNode = rowNode.getDown(); //keeps track of which row we are in for the chosen const col
                
//CHECK HERE IF FAIL
                
                System.out.println("?!?!?!");
                
                
                node rowEle = rowNode; //going to be the basenode of the row we are adding to the sol
                baseNode rowSol;// = rowNode;
                while(rowEle.getLeft() != null){ //sets rowEle = the baseNode of a given row
                    rowEle = rowEle.getLeft();
                }
                rowSol = (baseNode)rowEle.getRight().getLeft();

                solNodes.add(rowSol);//adds the row to the solutions
                System.out.println("adding a row");
                


                node tempRowNode = rowEle; //holds the position in the row that we are adding to the solution set
                node tempColNode = rowEle; // holds the position in the col of one of the eles in the row above
                node otherColNode = rowEle;
                baseNode temp;
                node sameConst = rowEle;
                node otherSameConst = rowEle;
                baseNode sameConstTop;
                
                removeVert(rowEle);                     //this might be mucking up the nodes declared above
                while(rowEle.getRight() != null){
                    rowEle = rowEle.getRight();
                    removeVert(rowEle);
                }
                
                while(tempRowNode.getRight() != null){ //this should remove all the rows that satisfy the same cols as rowEle
                    tempColNode = tempRowNode.getRight();    //= 1 at (1,1) first col
                    otherColNode = tempRowNode.getRight();   //= 1 at (1,1) first col
                    tempRowNode = tempRowNode.getRight();    //= 1 at (1,1) first col  //we are moving this across chosen row
                    while(otherColNode.getUp() != null){
                        otherColNode = otherColNode.getUp(); //= top of the first col
                        tempColNode = tempColNode.getUp();   //= top of the first col
                    }
                    temp = (baseNode)otherColNode;           //= top of the first col
                    temp.setSat(true);
                    temp.setNumNodes(0);

                    while(tempColNode.getDown() != null){
                        sameConst = tempColNode.getDown();    //= 2 at (1,1) first col
                        tempColNode = tempColNode.getDown();  //= 2 at (1,1) first col
                        while(sameConst.getLeft() != null){
                            sameConst = sameConst.getLeft();  //= 2 at (1,1) far left
                        }
                        removeVert(sameConst);                  //= remove 1 at (1,1) far left
                        while(sameConst.getRight() != null){
                            otherSameConst = sameConst.getRight();
                            sameConst = sameConst.getRight();
                            removeVert(sameConst);
                            while(otherSameConst.getUp() != null){
                                otherSameConst = otherSameConst.getUp();
                            }
                            sameConstTop = (baseNode)otherSameConst;
                            if(sameConstTop.getNumNodes() != 0)
                                sameConstTop.setNumNodes(sameConstTop.getNumNodes()-1);
                            else
                                sameConstTop.setNumNodes(0);
                        }
                    }
                }
                List<baseNode> tempSols = new ArrayList<baseNode>(); //will hold temp possible solutions. or indication that we failed
                for(int j = 0; j < (sortedList.size()-1)/4; j++){
                    baseNode dummyNode = ssf1.new baseNode(null, null, null, null, 0, false, "dummy node", 0, 0, 0);
                    tempSols.add(dummyNode);
                }
                if(curPos == sortedList.size()-1){
                    return solNodes;
                } else{
                    tempSols = solve_recur(sortedList, solNodes, satConts, curPos+1);
                    if(tempSols.get(0).getVal() == -10){ //the recursive call didnt work, meaning we have to uncover everything and then try the next option


                        node rowPutBack = solNodes.remove(solNodes.size()-1);
                        System.out.println("removing a row");

                        node tempRowNode2 = rowPutBack; //holds the position in the row that we are adding to the solution set
                        node tempColNode2 = rowPutBack; // holds the position in the col of one of the eles in the row above
                        node tempColNode3 = rowPutBack;
                        node otherColNode2 = rowPutBack;
                        baseNode temp2;
                        node sameConst2 = rowPutBack;
                        node sameConst3 = rowPutBack;

                        addVert(rowPutBack);                     //adds the baseRowNode back in

                        while(rowPutBack.getRight() != null){
                            rowPutBack = rowPutBack.getRight();
                            addVert(rowPutBack);
                        }

                        while(tempRowNode2.getRight() != null){ //this should remove all the rows that satisfy the same cols as rowEle
                            tempColNode2 = tempRowNode2.getRight();    //= 1 at (1,1) first col
                            tempColNode3 = tempRowNode2.getRight();
                            otherColNode2 = tempRowNode2.getRight();   //= 1 at (1,1) first col
                            tempRowNode2 = tempRowNode2.getRight();    //= 1 at (1,1) first col  //we are moving this across chosen row
                            while(otherColNode2.getUp() != null){
                                otherColNode2 = otherColNode2.getUp(); //= top of the first col
                                //tempColNode2 = tempColNode2.getUp();   //= top of the first col
                            }
                            temp2 = (baseNode)otherColNode2;           //= top of the first col
                            temp2.setSat(false);
         //MIGHT NOT HAVE TO DEAL WITH FIXING THIS. SHOULD NOT BE SETTING IT TO 0. SHOULD BE SETTING TO THE OLD VALUE
                            temp2.setNumNodes(0);


                            while(tempColNode3.getUp() != null){
                                sameConst3 = tempColNode3.getUp();
                                tempColNode3 = tempColNode3.getUp();
                                while(sameConst3.getLeft() != null){
                                    sameConst3 = sameConst3.getLeft();
                                }
                                addVert(sameConst3);
                                while(sameConst3.getRight() != null){
                                    sameConst3 = sameConst3.getRight();
                                    addVert(sameConst3);
                                }
                            }
                            while(tempColNode2.getDown() != null){
                                sameConst2 = tempColNode2.getDown();    //= 2 at (1,1) first col
                                tempColNode2 = tempColNode2.getDown();  //= 2 at (1,1) first col
                                while(sameConst2.getLeft() != null){
                                    sameConst2 = sameConst2.getLeft();  //= 2 at (1,1) far left
                                }
                                addVert(sameConst2);                  //= remove 1 at (1,1) far left
                                while(sameConst2.getRight() != null){
                                    sameConst2 = sameConst2.getRight();
                                    addVert(sameConst2);
                                }
                            }
                        }
                    } else{ //the recursive call worked, then we done.
                        return tempSols;
                    }
                }
            }
            
        }
        
        
        System.out.println("uhm, were done?");
        
        return solNodes;
    }
    
    public static void removeVert(node nodeToRemove){
//        System.out.println("going in remove");
//        System.out.println("with value: " + nodeToRemove.getVal());
        
        if(nodeToRemove.getUp() == null && nodeToRemove.getDown() == null){
        } else {
            if(nodeToRemove.getUp() == null){
                nodeToRemove.getDown().setUp(null);
            } else if(nodeToRemove.getDown() == null){
                nodeToRemove.getUp().setDown(null);
            } else{
                nodeToRemove.getUp().setDown(nodeToRemove.getDown());
                nodeToRemove.getDown().setUp(nodeToRemove.getUp());
            }
        }
        
//        System.out.println("leaving remove");
    }
    
    public static void addVert(node nodeToAdd){
//        System.out.println("going in add");
//        System.out.println("with value: " + nodeToAdd.getVal());
        
        if(nodeToAdd.getUp() == null && nodeToAdd.getDown() == null){
        } else {
            if(nodeToAdd.getUp() == null){
                nodeToAdd.getDown().setUp(nodeToAdd);
            } else if(nodeToAdd.getDown() == null){
                nodeToAdd.getUp().setDown(nodeToAdd);
            } else{
                nodeToAdd.getDown().setUp(nodeToAdd);
                nodeToAdd.getUp().setDown(nodeToAdd);
            }
        }
        
//        System.out.println("leaving add");
    }
    
    
    public static void removeHoriz(node nodeToRemove){
        if(nodeToRemove.getRight() == null){
            nodeToRemove.getLeft().setRight(null);
        } else if(nodeToRemove.getLeft() == null){
            nodeToRemove.getRight().setLeft(null);
        } else{
            nodeToRemove.getRight().setLeft(nodeToRemove.getLeft());
            nodeToRemove.getLeft().setRight(nodeToRemove.getRight());
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
    
    public static List<Integer> possibleValues(square[][] valBoard, int row, int col, int n, Map<Integer,List<Integer>> conflicts){
        List<Integer> listOfOptions = new ArrayList<Integer>();   //list of possible values for a given cell to return
        List<Integer> valsOfConflicts = new ArrayList<Integer>(); //list of values of conflicts
        List<Integer> conflictsOfVal = conflicts.get(row*n+col);  //list of positions of conflicts
        int i = 0;
        int j = 0;
        int singleVal = 0;
        
        for(int x = 0; x < conflictsOfVal.size(); x++){
            i = conflictsOfVal.get(x)/n;
            j = conflictsOfVal.get(x)%n;
            if((valBoard[i][j].options).size() == 1){
                singleVal = valBoard[i][j].options.get(0);
                if(!valsOfConflicts.contains(singleVal)){
                    valsOfConflicts.add(singleVal);
                }
            }
        }
        for(int y = 1; y < n+1; y++){
            if(!valsOfConflicts.contains(y)){
                listOfOptions.add(y);
            }
        }
        return listOfOptions;
    }
    

    
    
    
//    public static int[] solve(List<square> sortedList){
//        int [] solVals = new int[sortedList.size()]; //function as visited
//        int curPos = 0;
//        //finds all of the vals that were already given
//        for(int i = 0; i < sortedList.size(); i++){
//            if(sortedList.get(i).getValue() != 0){
//                solVals[i] = sortedList.get(i).getValue();
//                curPos = i;
//            } else{
//                solVals[i] = 0;
//            }
//        }
//        
//        solVals = solve_recur(sortedList, solVals, curPos+1);
//        return solVals;
//    }
//    
//    public static int[] solve_recur(List<square> sortedList, int[] solVals, int curPos){
//        List<Integer> possibleVals = new ArrayList<Integer>();  
//        possibleVals = sortedList.get(curPos).getOptions();     //list of possible values for the current cell
//        List<Integer> oldOptions = new ArrayList<Integer>();
//        int[] tempSols = new int[solVals.length];               //copy of current solutions
//        for(int i = 0; i < solVals.length; i++){
//            tempSols[i] = solVals[i];
//        }
//        oldOptions.clear();
//        oldOptions = sortedList.get(curPos).getOptions();
//        
//        for(int x: possibleVals){
//            List<Integer> tryVal = new ArrayList<Integer>();        //an arraylist with a single value used to check the possibleVals
//            tryVal.clear();
//            tryVal.add(x);
//            sortedList.get(curPos).setOptions(tryVal);
//            //update options for all of the conflicts with curPos; this will return a boolean. if it fails,
//            
//            boolSquare updateRes = updateOptions(sortedList, curPos);
//            //if curPos = sortedList.size() && update=true; we have a solution
//            
//            if(updateRes.updateResult == true){
//                solVals[curPos] = x;
//                if(curPos == sortedList.size()-1){
//                    return solVals;
//                } else{
//                    tempSols = solve_recur(updateRes.getValuesList(), solVals, curPos+1);//sortedList, solVals, curPos+1);//
//                    if(tempSols[0] == -1){//means we failed
//                    } else{
//                        solVals = tempSols;
//                        return solVals;
//                    }
//                }
//            } else{
//            }
//        }
//        
//        sortedList.get(curPos).setOptions(oldOptions);
//        int[] failedVals = new int[solVals.length];
//        failedVals[0] = -1;
//        return failedVals;
//    }
//    
//    public static boolSquare updateOptions(List<square> sortedList, int curPos){
//        //what we want to do
//        //we want to update all of the possibleOptions for all of the conflicts of sortedList(curpos).position
//
//        boolean result = true;                                  //
//        List<square> tempList = new ArrayList<square>();        //function as a copy of sortedList
//        List<Integer> tempInts = new ArrayList<Integer>();      //just a pointless list used to create dummy square
//        for(int i = 0; i < sortedList.size(); i++){
//            tempInts.add(-1);
//        }
//        for(int i = 0; i < sortedList.size(); i++){
//            square tempS = ssf1.new square(-1,-1,tempInts);
//            tempList.add(tempS);
//            tempList.get(i).setValue(sortedList.get(i).getValue());
//            tempList.get(i).setPosition(sortedList.get(i).getPosition());
//            tempList.get(i).setOptions(sortedList.get(i).getOptions());
//        }
//        
//        List<Integer> conflictsOfVal = sortedConflictsList.get(sortedList.get(curPos).getPosition());//conflictsList.get(sortedList.get(curPos).getPosition());  //list of positions of conflicts (NOT THE SAME AS CURPOS)
//        int singleVal = sortedList.get(curPos).getOptions().get(0);         //the single int value of what we are trying for the cur cell
//        
//        for(int x: conflictsOfVal){  //for every conflict's sorted position
//            List<Integer> listOfOptions = new ArrayList<Integer>();             //list of possible values for a given cell to return
//            List<Integer> valsOfConflicts = new ArrayList<Integer>();           //list of values of conflicts 
//            listOfOptions.clear();
//            valsOfConflicts.clear();
//            
//            for(int i = 0; i < sortedList.get(x).getOptions().size(); i++){  //getting the all of the possible values for a single conflicts
//                valsOfConflicts.add(sortedList.get(x).getOptions().get(i));  //of the current cell
//            }
//            for(int y = 0; y < valsOfConflicts.size(); y++){
//                if(valsOfConflicts.get(y) != singleVal){
//                    listOfOptions.add(valsOfConflicts.get(y));
//                }
//            }
//            tempList.get(x).setOptions(listOfOptions);
//            
//            if(listOfOptions.size() == 0){
//                result = false;
//            } else{
//            }
//        }
//
//        boolSquare bS1 = ssf1.new boolSquare(tempList, result);
//        return bS1;
//    }

    public static void main(String[] args) {
        
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
            
            int regionCap = (int) Math.sqrt(n);
            int sectionCap = (int) (Math.pow(n, 2)); 
            int colsCap = sectionCap * 4;//DL
            int rowsCap = (int) (Math.pow(n, 3));
            int[][] gridDL = new int[rowsCap][colsCap];
            for(int i = 0; i < rowsCap; i++){
                for(int j = 0; j < colsCap; j++){
                    gridDL[i][j] = 0; //initialize to 0s
                }  
            }
            
            for(int col = 0; col < sectionCap; col++){ //Section 1
                for(int row = col*n; row < (col*n) + n; row++) {
                    gridDL[row][col] = 1;
                }
            }

            for(int col = sectionCap; col < (sectionCap*2); col++) { //Section 2
                for(int row = ((col - sectionCap)/n)*sectionCap; row < ((col-sectionCap)/n)*sectionCap + sectionCap; row++) {
                    if(row%n == col%n) { 
                        gridDL[row][col] = 1;
                    }

                }
            }
            
            for(int col = sectionCap*2; col < sectionCap*3; col++) { //Section 3
                for(int count = 0; count < n; count++) {
                    gridDL[(sectionCap*count) + (col-(2*sectionCap))][col] = 1;
                }
            }
            if(color == false) { //Section 4 of 2Darray
                for(int i = 0; i < regionCap; i++) {
                    for(int j = 0; j < regionCap; j++) {
                        for(int k = 0; k < regionCap; k++) {
                            for(int l = 0; l < regionCap; l++) {
                                for(int m = 0; m < n; m++) {
                                    gridDL[i*(regionCap*sectionCap) + (j*sectionCap) + (k*regionCap*n) + (l*n) + m][(i*regionCap*n) + (k*n) + m + (regionCap*sectionCap)] = 1;
                                }   
                            }
                        }
                    }
                }
            }
            //PRINTING gridDL
            /*
            for(int row = 0; row < rowsCap; row++) {
                for(int col = 0; col < sectionCap*4; col++) {
                    //System.out.print(gridDL[row][col] + " ");
                }
                //System.out.println();
            }
            */
                        
            //Create row of baseNodes
            baseNode start = ssf1.new baseNode(null, null, null, null, -99, false, "start", 0, 0, 0); //DL
            //public baseNode(node left, node right, node up, node down, int cand, boolean sat, String name, int numNodes)
            baseNode tempNode = ssf1.new baseNode(null, null, null, null, 0, false, "start", 0, 0, 0);
            tempNode.setLeft(start);
            for(int i = 0; i < colsCap; i++) {
                baseNode newNode = ssf1.new baseNode(null, null, null, null, 0, false, "internal", 0, 0, 0);
                newNode.setLeft(tempNode.getLeft());
                tempNode.getLeft().setRight(newNode);
                tempNode.setLeft(newNode);
            }
            //Create column of baseNodes
            tempNode = ssf1.new baseNode(null, null, null, null, 0, false, "start", 0, 0, 0);
            tempNode.setUp(start);
            int tempRow = 0;
            int tempCol = 0;
            for(int i = 0; i < rowsCap; i++) {
                tempRow = i/sectionCap;
                tempCol = i/n%n;
                baseNode newNode = ssf1.new baseNode(null, null, null, null, (i%n)+1, false, "internal", 0, tempRow, tempCol);
                newNode.setUp(tempNode.getUp());
                tempNode.getUp().setDown(newNode);
                tempNode.setUp(newNode);
            }

            //Create internal nodes for gridDL
            baseNode tempR = ssf1.new baseNode(null, null, null, null, 0, false, "row", 0, 0, 0);
            tempR.setDown(start.getDown());
            baseNode tempC = ssf1.new baseNode(null, null, null, null, 0, false, "col", 0, 0, 0);
            tempC.setRight(start.getRight());
            baseNode temp = ssf1.new baseNode(null, null, null, null, 0, false, "temp", 0, 0, 0);
            temp.setLeft(start);
            baseNode temp2 = ssf1.new baseNode(null, null, null, null, 0, false, "temp2", 0, 0, 0);
            temp2.setUp(start);
            for(int i = 0; i < rowsCap; i++) {
                temp.setLeft(tempR.getDown());
                for(int j = 0; j < colsCap; j++) {
                    temp2.setDown(tempC.getRight());//setUp(tempC.getRight());
                    if(gridDL[i][j] == 1) {
                        node tempN = ssf1.new node(null, null, null, null);
                        tempN.setLeft(temp.getLeft());
                        temp.getLeft().setRight(tempN);
                        temp.setLeft(tempN);
                        
                        while(temp2.getDown() != null) {
                            temp2.setRight(temp2.getDown());
                            temp2.setDown(temp2.getDown().getDown());// = (baseNode) temp2.getDown();
                        }
                        
                        tempN.setUp(temp2.getRight());
                        temp2.getRight().setDown(tempN);
                        //temp2.setUp(tempN);
                    }
                    tempC.setRight(tempC.getRight().getRight());
                }
                tempR.setDown(tempR.getDown().getDown());
                tempC.setRight(start.getRight());
            }
            
            
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
            sortedConflictsList = new HashMap<Integer, List<Integer>>();

             
            
            //gets positions for conflicts of a given cell
            int rowQuad = -1; 
            int colQuad = -1;//
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
                    List<Integer> tempConflicts = new ArrayList<Integer>();
                    tempConflicts.clear();
                    //adds row and col as conflicts
                    for(int k = 0; k < numCols; k++){
                        if(j != k)
                            tempConflicts.add(i*numCols + k);
                        if(i != k)
                            tempConflicts.add(k*numRows + j);
                    } 
                    if(color == false) { //normal board
                        rowQuad = (i/sqrtN);
                        colQuad = (j/sqrtN);
                        
                        for(int k = rowQuad*sqrtN; k < (rowQuad+1)*sqrtN; k++){
                            for(int l = colQuad*sqrtN; l < (colQuad+1)*sqrtN; l++){
                                if(i != k && j != l)
                                    tempConflicts.add(k*numCols + l);
                            }   
                        }
                    }
                    conflictsList.put(i*numCols+j, tempConflicts);
                }
            }
            
            baseNode tempBaseR = ssf1.new baseNode(null, null, null, null, 0, false, "start", 0, 0, 0);
            tempBaseR.setDown(start.getDown());
            Map<Integer, Integer> colorMap = new HashMap<Integer, Integer>();
            
            
            node current = ssf1.new node(null, null, null, null);
            current.setDown(start.getDown());
            node tempCurRow = ssf1.new node(null, null, null, null);
            tempCurRow.setDown(start.getDown());
            //Added code for origBoard
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
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
                    if(index != -1) { 
                        String num = tempLine.substring(0, index); 
                        curColor = Integer.parseInt(tempLine.substring(index+1)); 
                        curVal = Integer.parseInt(num);
                        board[i][j].setColor(curColor);
                        origBoard[i][j].setColor(curColor); 
                        colorMap.put((i*n) + j, curColor);
                    } else {
                        curVal = Integer.parseInt(tempLine); //normal board
                    }
                    board[i][j].setValue(curVal);
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
                    
                    for(int k = 1; k < n+1; k++) {
                        tempBaseR = (baseNode) tempBaseR.getDown();
                        tempBaseR.setCand(k);
                    }
                    
                    int readIn = (i*n) + j; //rows*n + cols = # of cells read in so far, including current cell
                    int given = 0;
                    if(curVal != 0) { //read in value is a given
                        for(int s = 1; s < n+1; s++) { //set every cand row that != curVal to -1
                            if(s != curVal) {
                                for(int t = 0; t < colsCap; t++) {
                                    gridDL[(readIn*n) - (given*(n-1)) + s][t] = -1;
                                }
                            }
                        }
                        given++;
                    }
//                        for(int u = 0; u < ((readIn*n) - (given*(n-1))); u++) { //could optimize by not restarting from head
//                            current = current.getDown();
//                            tempCurRow = tempCurRow.getDown();
//                        }
                    for(int v = 1; v < n+1; v++) {
                        tempCurRow = current.getDown();
                        current = current.getDown();
                        if(curVal == 0){
                        } else {
                            if(v != curVal) {
                                removeVert(current); //check removeVert
                                //REMOVE MORE NODES
                                while(tempCurRow.getRight() != null) {
                                    tempCurRow = tempCurRow.getRight();
                                    removeVert(tempCurRow); 
                                }
                            }
                        }
                    } 
                    
                    
                    
                } //for cols
            } //for rows
            
//            for(int row = 0; row < rowsCap; row++) {
//                for(int col = 0; col < sectionCap*4; col++) {
//                    if(gridDL[row][col] != -1)
//                    System.out.print(gridDL[row][col] + " ");
//                }
//                System.out.println();
//            }
            
            
            if(color == true) { //Section 4 of 2Darray
                for(int i = 0; i < regionCap; i++) {
                    for(int j = 0; j < regionCap; j++) {
                        for(int k = 0; k < regionCap; k++) {
                            for(int l = 0; l < regionCap; l++) {
                                for(int m = 0; m < n; m++) {
                                    //gridDL[i*(regionCap*sectionCap) + (j*sectionCap) + (k*regionCap*n) + (l*n) + m][(i*regionCap*n) + (k*n) + m + (regionCap*sectionCap)] = 1;
                                   //if statement to see if color matches
                                    gridDL[i][regionCap*sectionCap + k*n + i%n] = 1;
                                }   
                            }
                        }
                    }
                }
            }
            //PRINT
            /*
            for(int row = 0; row < rowsCap; row++) {
                for(int col = 0; col < sectionCap*4; col++) {
                    System.out.print(gridDL[row][col] + " ");
                }
                System.out.println();
            }
            */
            //Count the number in a column
            baseNode countCol = ssf1.new baseNode(null, null, null, null, 0, false, "count", 0, 0, 0);
            countCol.setRight(start.getRight());
            int counter = 0;
            for(int i = 0; i < colsCap; i++) {
                counter = 0;
                for(int j = 0; j < rowsCap; j++) {
                    if(gridDL[j][i] == 1) {
                        counter++;
                    }
                }
                ((baseNode)countCol.getRight()).setNumNodes(counter);
                countCol.setRight(countCol.getRight().getRight());
            }
            
            //add same colored as conflicts
            if(color == true) { //nonomino board
                for(int i = 0; i < numRows; i++){
                    for(int j = 0; j < numCols; j++){
                        for(int k = 0; k < numRows; k++) {
                            for(int l = 0; l < numCols; l++) {
                                if(i != k && j !=l && (board[i][j].getColor() == board[k][l].getColor())) { //if colors are the same
                                    conflictsList.get(i*numCols+j).add(k*numCols + l);
                                    //printConflicts(conflictsList, numRows, numCols);
                                }
                            }
                        }
                    }
                }
            }
            
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){                    
                    if(board[i][j].options.size() != 1){ 
                        tempList = possibleValues(board, i, j, numCols, conflictsList);
                        board[i][j].setOptions(tempList);
                        origBoard[i][j].setOptions(tempList);
                    }
                }
            }

            
            
            createGUI(origBoard, numCols, "SOLVE", color);
            
            //ActionListener for button clicked
            //while(!jb.clicked()) {
                //System.out.print("SOLVING!");
            //}
            //System.out.println("CLICKED!");  
            
            
            //START TIMER HERE
            long startTime = System.currentTimeMillis();
            
            
            /*
            List<square> sortedList = new ArrayList<square>();
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
                    sortedList.add(board[i][j]);
                }
            }
            
            Collections.sort(sortedList);
            int tempPos2 = -1;
            for(int i = 0; i < sortedList.size(); i++){  //for every val in sortedList, gonna get the sorted conflicts
                List<Integer> tempConf2 = new ArrayList<Integer>();
                tempConf2.clear();
                tempPos2 = sortedList.get(i).getPosition(); //position of current ele in NORMAL list we looking at
                for(int j = 0; j < conflictsList.get(tempPos2).size(); j++){ //for every NORMAL conflict of tempPos2
                    for(int k = 0; k < sortedList.size(); k++){
                        if(conflictsList.get(tempPos2).get(j) == sortedList.get(k).getPosition()){
                            tempConf2.add(k);
                            break;
                        }
                    }
                }
                sortedConflictsList.put(tempPos2, tempConf2);
            }
            
            int[] solvedVals = new int[sortedList.size()];
            solvedVals = solve(sortedList);
            
            for(int i = 0; i < sortedList.size(); i++){
                sortedList.get(i).setValue(solvedVals[i]);
            }
            
            int tempPos = 0;
            int tempI = 0;
            int tempJ = 0;
            for(int i = 0; i < sortedList.size(); i++){
                tempPos = sortedList.get(i).getPosition();
                tempI = tempPos/numCols;
                tempJ = tempPos%numCols;
                board[tempI][tempJ].setValue(sortedList.get(i).getValue());
            }
            
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
            }*/
            
            //createGUI(board, numCols, "SOLUTION"); //again
            
            //STOP TIMER HERE
            
            
            baseNode h = ssf1.new baseNode(null, null, null, null, 0, 0, false, "h", 0);
            baseNode bnc1 = ssf1.new baseNode(null, null, null, null, -1, 0, false, "bnc1", -1);
            baseNode bnc2 = ssf1.new baseNode(null, null, null, null, -2, 0, false, "bnc2", -2);
            baseNode bnc3 = ssf1.new baseNode(null, null, null, null, -3, 0, false, "bnc3", -3);
            baseNode bnc4 = ssf1.new baseNode(null, null, null, null, -4, 0, false, "bnc4", -4);
            baseNode bnc5 = ssf1.new baseNode(null, null, null, null, -5, 0, false, "bnc5", -5);
            
            baseNode bnr1 = ssf1.new baseNode(null, null, null, null, -6, 0, false, "bnr1", -6);
            baseNode bnr2 = ssf1.new baseNode(null, null, null, null, -7, 0, false, "bnr2", -7);
            baseNode bnr3 = ssf1.new baseNode(null, null, null, null, -8, 0, false, "bnr3", -8);
            baseNode bnr4 = ssf1.new baseNode(null, null, null, null, -9, 0, false, "bnr4", -9);
            baseNode bnr5 = ssf1.new baseNode(null, null, null, null, -10, 0, false, "bnr5", -10);
            
            node valN1 = ssf1.new node(null, null, null, null, 1);
            node valN2 = ssf1.new node(null, null, null, null, 2);
            node valN3 = ssf1.new node(null, null, null, null, 3);
            node valN4 = ssf1.new node(null, null, null, null, 4);
            node valN5 = ssf1.new node(null, null, null, null, 5);
            node valN6 = ssf1.new node(null, null, null, null, 6);
            node valN7 = ssf1.new node(null, null, null, null, 7);
            node valN8 = ssf1.new node(null, null, null, null, 8);
            node valN9 = ssf1.new node(null, null, null, null, 9);
            node valN10 = ssf1.new node(null, null, null, null, 10);
            
            h.setRight(bnc1);
            bnc1.setRight(bnc2);
            bnc2.setRight(bnc3);
            bnc3.setRight(bnc4);
            bnc4.setRight(bnc5);
            
            bnc5.setLeft(bnc4);
            bnc4.setLeft(bnc3);
            bnc3.setLeft(bnc2);
            bnc2.setLeft(bnc1);
            bnc1.setLeft(h);
            
            h.setDown(bnr1);
            bnr1.setDown(bnr2);
            bnr2.setDown(bnr3);
            bnr3.setDown(bnr4);
            bnr4.setDown(bnr5);
            
            bnr5.setUp(bnr4);
            bnr4.setUp(bnr3);
            bnr3.setUp(bnr2);
            bnr2.setUp(bnr1);
            bnr1.setUp(h);
            
            bnr1.setRight(valN1);
            valN1.setLeft(bnr1);
            valN1.setUp(bnc1);
            bnc1.setDown(valN1);
            
            bnr2.setRight(valN2);
            valN2.setLeft(bnr2);
            valN2.setUp(bnc2);
            bnc2.setDown(valN2);
            valN2.setRight(valN3);
            valN3.setLeft(valN2);
            valN3.setUp(bnc3);
            bnc3.setDown(valN3);
            valN2.setDown(valN5);
            valN5.setUp(valN2);
            
            bnr3.setRight(valN4);
            valN4.setLeft(bnr3);
            valN4.setUp(bnc4);
            bnc4.setDown(valN4);
            
            bnr4.setRight(valN5);
            valN5.setLeft(bnr4);
            valN5.setRight(valN6);
            valN6.setLeft(valN5);
            valN6.setUp(bnc5);
            bnc5.setDown(valN6);
            valN6.setDown(valN7);
            valN7.setUp(valN6);
            
            bnr5.setRight(valN7);
            valN7.setLeft(bnr5);
            
            bnc1.setNumNodes(1);
            bnc2.setNumNodes(2);
            bnc3.setNumNodes(1);
            bnc4.setNumNodes(1);
            bnc5.setNumNodes(2);
            
            List<baseNode> listOfColNodes = new ArrayList<baseNode>();
            listOfColNodes.add(h);
            listOfColNodes.add(bnc1);
            listOfColNodes.add(bnc2);
            listOfColNodes.add(bnc3);
            listOfColNodes.add(bnc4);
            listOfColNodes.add(bnc5);
            
            List<baseNode> listOfColNodes1 = new ArrayList<baseNode>();
            baseNode tempTop = ssf1.new baseNode(null, null, null, null, 0, false, "top_of_col", 0, 0, 0);
            tempTop.setRight(start.getRight());
            tempTop.setLeft(start.getRight().getLeft());
            listOfColNodes1.add((baseNode)tempTop.getLeft());
            
            for(int i = 0; i < colsCap; i++){
                listOfColNodes1.add((baseNode)tempTop.getRight());
                tempTop.setRight(tempTop.getRight().getRight());
            }
            
            /*
            System.out.println("gonna print out everything");
            System.out.println("-------------------------------------------------");
            System.out.println(listOfColNodes.get(0).getName());
            System.out.println(listOfColNodes.get(0).getNumNodes());
            System.out.println(listOfColNodes.get(0).getSat());
            System.out.println(listOfColNodes.get(0).getDown().getDown().getDown().getDown().getDown().getRight().getUp().getVal());
            System.out.println("-------------------------------------------------");
            System.out.println(listOfColNodes.get(1).getName());
            System.out.println(listOfColNodes.get(1).getNumNodes());
            System.out.println(listOfColNodes.get(1).getSat());
            System.out.println(listOfColNodes.get(1).getDown().getDown());//.getVal());
            System.out.println("-------------------------------------------------");
            System.out.println(listOfColNodes.get(2).getName());
            System.out.println(listOfColNodes.get(2).getNumNodes());
            System.out.println(listOfColNodes.get(2).getSat());
            System.out.println(listOfColNodes.get(2).getDown().getDown().getDown());//.getVal());
            System.out.println("-------------------------------------------------");
            System.out.println(listOfColNodes.get(3).getName());
            System.out.println(listOfColNodes.get(3).getNumNodes());
            System.out.println(listOfColNodes.get(3).getSat());
            System.out.println(listOfColNodes.get(3).getDown().getDown());//.getVal());
            System.out.println("-------------------------------------------------");
            System.out.println(listOfColNodes.get(4).getName());
            System.out.println(listOfColNodes.get(4).getNumNodes());
            System.out.println(listOfColNodes.get(4).getSat());
            System.out.println(listOfColNodes.get(4).getDown().getDown());//.getVal());
            System.out.println("-------------------------------------------------");
            System.out.println(listOfColNodes.get(5).getName());
            System.out.println(listOfColNodes.get(5).getNumNodes());
            System.out.println(listOfColNodes.get(5).getSat());
            System.out.println(listOfColNodes.get(5).getDown().getDown().getDown());//.getVal());
            System.out.println("-------------------------------------------------");
            */
            
            
            
            
            //Collections.sort(listOfColNodes);
            Collections.sort(listOfColNodes1);
            System.out.println("wejustsorted\n");
            

            
            
            System.out.println("bouta try and solve\n");
            List<baseNode> solutions = solve(listOfColNodes1);//listOfColNodes);
            System.out.println("\nlel we made it out of solve");
            
            baseNode tempLookThru = listOfColNodes1.get(0);
            
            for(int i = 0; i < solutions.size(); i++){
                board[solutions.get(i).getRowPos()][solutions.get(i).getColPos()].setValue(solutions.get(i).getCand());
            }
            
            
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
            
            /*for(int i = 0; i < 561; i++){
                tempLookThru = (baseNode)tempLookThru.getDown();
                System.out.println(tempLookThru.getCand());
            }*/
            long endTime = System.currentTimeMillis();
            
            
//            for(int i = 0; i < solutions.size(); i++){
//                System.out.print(solutions.get(i).getCand() + " ");
//                if(i%n == n-1){
//                    System.out.println();
//                }
//            }
            
            System.out.println("time taken: " + (endTime - startTime)/1000.0 + " seconds");
            
        }
    }
}


//    public static int[] solve(List<square> sortedList){
//        int [] solVals = new int[sortedList.size()]; //function as visited
//        int curPos = 0;
//        //finds all of the vals that were already given
//        for(int i = 0; i < sortedList.size(); i++){
//            if(sortedList.get(i).getValue() != 0){
//                solVals[i] = sortedList.get(i).getValue();
//                curPos = i;
//            }
//        }
//        
//        /*System.out.println("this is in \"solve\". this is the current contents of solVals. Should be given eles.");
//        for(int i = 0; i < curPos+1; i++){
//            System.out.println("@: " + sortedList.get(i).getPosition() + "= " + solVals[i]);
//        }*/
//        
//        solVals = solve_recur(sortedList, solVals, curPos+1);
//        
//        /*System.out.println("this is in \"solve\". this is the final contents of solVals.");
//        for(int i = 0; i < solVals.length; i++){
//            System.out.println("@: " + sortedList.get(i).getPosition() + "= " + solVals[i]);
//        }*/
//        
//        return solVals;
//    }
    
//    public static int[] solve_recur(List<square> sortedList, int[] solVals, int curPos){
//        List<Integer> possibleVals = new ArrayList<Integer>();  
//        possibleVals = sortedList.get(curPos).getOptions();     //list of possible values for the current cell
//        List<Integer> tryVal = new ArrayList<Integer>();        //an arraylist with a single value used to check the possibleVals
//        List<Integer> oldOptions = new ArrayList<Integer>();
//        int[] tempSols = new int[solVals.length];               //copy of current solutions
//        for(int i = 0; i < curPos; i++){
//            tempSols[i] = solVals[i];
//        }
//        
//        //System.out.println("the possible values for " + sortedList.get(24).getPosition() + " are " + sortedList.get(24).getOptions());
//        
////        System.out.println("trying solve_recur");
////        System.out.println("curPos: " + curPos + "; at spot: " + sortedList.get(curPos).getPosition() + " in the board");
////        System.out.println("value = " + sortedList.get(curPos).getValue());
////        System.out.print("possibleVals for current = ");
////        System.out.println(sortedList.get(curPos).getOptions());
//        
//        /*System.out.println("##1##");
//        for(int i = curPos; i < sortedList.size(); i++){
//           System.out.print("possibleVals for " + i + " = ");
//           System.out.println(sortedList.get(i).getOptions()); 
//        }*/
//        
//        //###############################
//        //if(sortedList.get(curPos).getPosition() == 69 || sortedList.get(curPos).getPosition() == 10 || sortedList.get(curPos).getPosition() == 20)
//        //System.out.println("looking at " + possibleVals + " as vals for pos: " + sortedList.get(curPos).getPosition() + " at curPos: " + curPos);
//        //###############################
//        
//        oldOptions.clear();
//        oldOptions = sortedList.get(curPos).getOptions();
//        
//        for(int x: possibleVals){
//            tryVal.clear();
//            tryVal.add(x);
//            sortedList.get(curPos).setOptions(tryVal);
//            
//            /*System.out.println("##2##");
//            for(int i = curPos; i < sortedList.size(); i++){
//                System.out.print("possibleVals for " + i + " = ");
//                System.out.println(sortedList.get(i).getOptions()); 
//            }*/
//            
//            //tempSols[curPos] = x;
//            //update options for all of the conflicts with curPos; this will return a boolean. if it fails,
//            
//            //###############################
//            //if(sortedList.get(curPos).getPosition() == 69 || sortedList.get(curPos).getPosition() == 10 || sortedList.get(curPos).getPosition() == 20)
//            //System.out.println("among " + possibleVals + " we are trying value: " + x + " in updateOptions for pos: " + sortedList.get(curPos).getPosition());
//            //###############################
//            
//            boolSquare updateRes = updateOptions(sortedList, curPos);
//            //if curPos = sortedList.size() && update=true; we have a solution
//            
//            /*System.out.println("##3##");
//            for(int i = curPos; i < sortedList.size(); i++){
//                System.out.print("possibleVals for " + i + " = ");
//                System.out.println(sortedList.get(i).getOptions()); 
//            }*/
//            
//            //System.out.println("bouta go in the if with value: " + x);
//            //System.out.println(updateRes.updateResult);
//            /*for(int i = 0; i < updateRes.valuesList.size(); i++){
//                System.out.println(i + ": ");
//                System.out.println("value: " + updateRes.valuesList.get(i).value);
//                System.out.println("position: " + updateRes.valuesList.get(i).position);
//                System.out.println("options: " + updateRes.valuesList.get(i).options);
//            }*/
//            
//            if(updateRes.updateResult == true){
//                
//                //###############################
//                //if(sortedList.get(curPos).getPosition() == 69 || sortedList.get(curPos).getPosition() == 10 || sortedList.get(curPos).getPosition() == 20)
//                //System.out.println("we got a true result using : " + x + ", as the value for pos: " + sortedList.get(curPos).getPosition());
//                //###############################
//                
//                solVals[curPos] = x;
//                if(curPos == sortedList.size()-1){
//                    
//                    //###############################
//                    //if(sortedList.get(curPos).getPosition() == 69 || sortedList.get(curPos).getPosition() == 10 || sortedList.get(curPos).getPosition() == 20)
//                    //System.out.println("found a solution");
//                    //###############################
//                    
//                    return solVals;
//                } else{
//                    
//                    //###############################
//                    //if(sortedList.get(curPos).getPosition() == 69 || sortedList.get(curPos).getPosition() == 10 || sortedList.get(curPos).getPosition() == 20){
//                    //System.out.println("looking at next position which is: " + sortedList.get(curPos+1).getPosition());
//                    //System.out.println("the updateRes next position is: " + updateRes.getValuesList().get(curPos+1).getPosition());
//                    
//                    //System.out.println("conflicts of 21 are: ");
//                    //for(int i = 0; i < conflictsList.get(20).size(); i++){
//                        //System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//                        //System.out.println(updateRes.getValuesList().get(sortedConflictsList.get(20).get(i)).getOptions());
//                    //}
//                    
//                    //System.out.println("-------------------------");//}
//                    //###############################
//                    
//                    tempSols = solve_recur(updateRes.getValuesList(), solVals, curPos+1);//sortedList, solVals, curPos+1);//
//                    if(tempSols[0] == -1){//means we failed
//                        
//                        //###############################
//                        //if(sortedList.get(curPos).getPosition() == 69 || sortedList.get(curPos).getPosition() == 10 || sortedList.get(curPos).getPosition() == 20){
//                        //System.out.println("we failed with a -1");
//                        //System.out.println("-------------------------");//}
//                        //###############################
//                        
//                    } else{
//                        //System.out.println("we got a good recur call");
//                        solVals = tempSols;
//                        return solVals;
//                    }
//                }
//            } else{
//                
//                //###############################
//                //if(sortedList.get(curPos).getPosition() == 69 || sortedList.get(curPos).getPosition() == 10 || sortedList.get(curPos).getPosition() == 20)
//                //System.out.println("we got a false result using : " + x + ", as the value for pos: " + sortedList.get(curPos).getPosition());
//                //###############################
//                
//            }
//        }
//        
//        sortedList.get(curPos).setOptions(oldOptions);
//        
//        //###############################
//        //if(sortedList.get(curPos).getPosition() == 69 || sortedList.get(curPos).getPosition() == 10 || sortedList.get(curPos).getPosition() == 20){
//        //System.out.println("t.t....we failed and no value worked. means we need to go back up 1 level and try the next val");
//        //System.out.println("curPos that no value worked for: " + sortedList.get(curPos).getPosition() + "(" + curPos + ")");//}
//        //###############################
//        
//        int[] failedVals = new int[solVals.length];
//        failedVals[0] = -1;
//        return failedVals;
//    }
    
    
//    public static boolSquare updateOptions(List<square> sortedList, int curPos){
//        //what we want to do
//        //we want to update all of the possibleOptions for all of the conflicts of sortedList(curpos).position
//
//        /*System.out.println("THIS IS INSIDE OF UPDATE");
//        System.out.println("##1## \nconflicts of 21 are: ");
//        for(int i = 0; i < conflictsList.get(20).size(); i++){
//            System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//            System.out.println(sortedList.get(sortedConflictsList.get(20).get(i)).getOptions());
//        }*/
//        
//        
//        boolean result = true;                                  //
//        List<square> tempList = new ArrayList<square>();        //function as a copy of sortedList
//        List<Integer> tempInts = new ArrayList<Integer>();      //just a pointless list used to create dummy square
//        for(int i = 0; i < sortedList.size(); i++){
//            tempInts.add(-1);
//        }
//        for(int i = 0; i < sortedList.size(); i++){
//            square tempS = ssf1.new square(-1,-1,tempInts);
//            tempList.add(tempS);
//            tempList.get(i).setValue(sortedList.get(i).getValue());
//            tempList.get(i).setPosition(sortedList.get(i).getPosition());
//            tempList.get(i).setOptions(sortedList.get(i).getOptions());
//        }
//        
//        /*System.out.println("##2## \nconflicts of 21 are: ");
//        for(int i = 0; i < conflictsList.get(20).size(); i++){
//            System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//            System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//        }
//        
//        
//        if(curPos == 23){
//            System.out.println("we are about to update stuff for conflicts of: " + sortedList.get(23).getPosition());
//            System.out.println("the possible values of pos: " + tempList.get(24).getPosition() + " are: " + tempList.get(24).getOptions());
//        }*/
//
//        
//        List<Integer> conflictsOfVal = sortedConflictsList.get(sortedList.get(curPos).getPosition());//conflictsList.get(sortedList.get(curPos).getPosition());  //list of positions of conflicts (NOT THE SAME AS CURPOS)
//        int singleVal = sortedList.get(curPos).getOptions().get(0);         //the single int value of what we are trying for the cur cell
//        //int q = 0;
//        List<Integer> conflictsOfValNormal = conflictsList.get(sortedList.get(curPos).getPosition());
//        
//        for(int x: conflictsOfVal){  //for every conflict's sorted position
//            List<Integer> listOfOptions = new ArrayList<Integer>();             //list of possible values for a given cell to return
//            List<Integer> valsOfConflicts = new ArrayList<Integer>();           //list of values of conflicts 
//        
//            
//            /*System.out.println("LOOKING AT: " + x + " as a conflict with a real position of: " + conflictsOfValNormal.get(q));
//            System.out.println("##3.1## \nconflicts of 21 when looking at conflict: " + x + " are: ");
//            for(int i = 0; i < conflictsList.get(20).size(); i++){
//                System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//                System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//            }*/
//            
//            listOfOptions.clear();
//            
//            /*System.out.println("##3.111## \nconflicts of 21 when looking at conflict: " + x + " are: ");
//            for(int i = 0; i < conflictsList.get(20).size(); i++){
//                System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//                System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//            }*/
//            
//            valsOfConflicts.clear();
//            
//            /*System.out.println("##3.11## \nconflicts of 21 when looking at conflict: " + x + " are: ");
//            for(int i = 0; i < conflictsList.get(20).size(); i++){
//                System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//                System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//            }*/
//            
//            for(int i = 0; i < sortedList.get(x).getOptions().size(); i++){  //getting the all of the possible values for a single conflicts
//                valsOfConflicts.add(sortedList.get(x).getOptions().get(i));  //of the current cell
//            }
//            
//            /*System.out.println("##3.12## \nconflicts of 21 when looking at conflict: " + x + " are: ");
//            for(int i = 0; i < conflictsList.get(20).size(); i++){
//                System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//                System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//            }*/
//            
//            //System.out.println("the possible options of: " + x + " are: " + valsOfConflicts);
//            
//            for(int y = 0; y < valsOfConflicts.size(); y++){
//                if(valsOfConflicts.get(y) != singleVal){
//                    listOfOptions.add(valsOfConflicts.get(y));
//                }
//            }
//            
//            /*System.out.println("the listOfOptions of: " + x + " are: " + listOfOptions);
//            
//            System.out.println("##3.2## \nconflicts of 21 when looking at conflict: " + x + " are: ");
//            for(int i = 0; i < conflictsList.get(20).size(); i++){
//                System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//                System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//            }*/
//            
//            tempList.get(x).setOptions(listOfOptions);
//            
//            /*System.out.println("##3.3## \nconflicts of 21 when looking at conflict: " + x + " are: ");
//            for(int i = 0; i < conflictsList.get(20).size(); i++){
//                System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//                System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//            }
//            
//            if(curPos == 23){
//                System.out.println("we are looking at conflict: " + tempList.get(x).getPosition());
//                //System.out.println("the possible values of pos: " + tempList.get(24).getPosition() + " are: " + tempList.get(24).getOptions());
//                for(int i = 0; i < conflictsOfVal.size(); i++){
//                    System.out.println("the possible values of pos: " + tempList.get(conflictsOfVal.get(i)).getPosition() + " are: " + tempList.get(conflictsOfVal.get(i)).getOptions());
//                    
//                }
//            }*/
//            
//            if(listOfOptions.size() == 0){
//                
//                //###############################
//                //System.out.println("bool value for " + sortedList.get(x).getPosition() + " = " + "false");
//                //###############################
//                
//                result = false;
//            } else{
//                //System.out.println("bool value for " + x + " = true");
//            }
//            
//            /*System.out.println("##3.4## \nconflicts of 21 when looking at conflict: " + x + " are: ");
//            for(int i = 0; i < conflictsList.get(20).size(); i++){
//                System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//                System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//            }*/
//            //q++;
//        }
//        
//        /*System.out.println("##4## \nconflicts of 21 are: ");
//        for(int i = 0; i < conflictsList.get(20).size(); i++){
//            System.out.print("we are looking at conflict: " + conflictsList.get(20).get(i) + " ");
//            System.out.println(tempList.get(sortedConflictsList.get(20).get(i)).getOptions());
//        }
//        System.out.println("WE ARE LEAVING UPDATE");*/
//        
//        boolSquare bS1 = ssf1.new boolSquare(tempList, result);
//        return bS1;
//    }
