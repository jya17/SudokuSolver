/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolverfinal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Brian Spann (rbs4ba) & Jessica Ya (jy2fv), Final Theory Project
 */
public class JButtonClass extends javax.swing.JPanel {
   
    private boolean b = false;
    private JButton button;
    /**
     * Creates new form JButton
     */
    public JButtonClass(String s) {
        button = new JButton(s);
        button.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            b = true;
            //System.out.println("Button clicked");
        }
    });
        add(button);
    }
    
    public boolean clicked() {
        return b;
    }
    /*
    public void changeText(String solved) {
        button.setText(solved);
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
