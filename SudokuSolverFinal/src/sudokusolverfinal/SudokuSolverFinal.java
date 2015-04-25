/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolverfinal;

import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.JButton;
/**
 *
 * @author Jessica
 */
public class SudokuSolverFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        
        //class Board needed
        panel.add(new Board());
        //panel.add(new JButton(">")); //Check if JButton class required
        panel.add(new Board());
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
}
