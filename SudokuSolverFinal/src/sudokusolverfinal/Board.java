/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolverfinal;

import java.awt.Color;
import java.awt.GridLayout;
/**
 *
 * @author Brian Spann (rbs4ba) & Jessica Ya (jy2fv), Final Theory Project
 * based off of Nicholas Dunn's grid
 */

public class Board extends javax.swing.JPanel {
    private Squares[] grid;// = new Squares[9];
    private Color[] backgrounds = {Color.darkGray, Color.lightGray};
    /**
     * Creates new form Board
     */
    public Board(int n) {
        System.out.println(";-;");
        
        grid = new Squares[n];
        int sqrtVal = (int) Math.sqrt((double)n);
        setLayout(new GridLayout(sqrtVal, sqrtVal));
        for(int i = 0; i < grid.length; i++) {
            grid[i] = new Squares(backgrounds[i%2], sqrtVal);
            add(grid[i]);
        }
        //initComponents();
    }
    
    public Squares getSquares(int index) {
        return grid[index];
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
