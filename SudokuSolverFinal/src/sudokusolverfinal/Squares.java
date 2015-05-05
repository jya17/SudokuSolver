/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolverfinal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
//import java.awt.Toolkit;
import javax.swing.BorderFactory;
//import javax.swing.JLabel;
import javax.swing.JTextField;
//import javax.swing.text.AttributeSet;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.PlainDocument;

/**
 *
 * @author Brian Spann (rbs4ba) & Jessica Ya (jy2fv), Final Theory Project
 * based off of Nicholas Dunn's grid
 */

/** TODO:
 * XInstead of inputting numbers, display number from a 2D array
 * XDisplay colors for nonominos
 * 
 */
public class Squares extends javax.swing.JPanel {

    //private JTextField nw, n, ne, e, c, w, sw, s, se;
    private JTextField[] txtFields;// = new JTextField[]{
        //nw, n, ne, e, c, w, sw, s, se
    //};

    private static final int BORDER_WIDTH = 2;
    /*private static final Color[] COLORS = { 
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
    */
    
    /**
     * Creates new form Squares
     * @param background
     * @param s
     */
    public Squares(Color background, int s) { //int s is the sqrt(n)
        
        txtFields = new JTextField[(int) Math.pow((double) s, 2)];

        setLayout(new GridLayout(s, s));
        initGUI((int) Math.pow((double) s, 2));
        setBackground(background);
    }

    private void initGUI(int n) {
        
        for (int i = 0; i < n; i++) {//for (int i = 0; i < txtFields.length; i++) {
        txtFields[i] = new JTextField(2);//(1); //changed to 2 to accept bigger #s
        txtFields[i].setText(" ");
        //txtFields[i].setBackground(COLORS[i]);
        //txtFields[i].setDocument(new NumericalDocument()); //extends PlainDocument
        txtFields[i].setEditable(false);
        add(txtFields[i]);  
    }
        setBorder(BorderFactory.createMatteBorder(BORDER_WIDTH,BORDER_WIDTH,BORDER_WIDTH,BORDER_WIDTH, Color.BLACK));
    }
    
    public Dimension getPrefDimension() {
        return new Dimension(100, 100); //try changing dimensions
    }
    /**
    public JTextField getJTextField(int index) {
        return txtFields[index];
    }
     * @return 
    */
    public JTextField[] getJTextFieldArray() {
        return txtFields;
    }
    /*
    public Color[] getColorArray() {
        return COLORS;
    }
    
    public Color getColor(int i) {
        return COLORS[i];
    }
    public void setColor(int i) {
        txtFields[i].setBackground(COLORS[i]);
    }
    */
    /**
    public static class NumericalDocument extends PlainDocument {
        String numbers = "0123456789"; //0 necessary?
        @Override
        public void insertString(int offset, String s, AttributeSet a) throws BadLocationException {
            if (getLength() == 0 && s.length() == 1 && numbers.contains(s)) {
                super.insertString(offset, s, a);
            }
            else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    */
    
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
