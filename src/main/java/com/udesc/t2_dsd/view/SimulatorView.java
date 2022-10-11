package com.udesc.t2_dsd.view;

import com.udesc.t2_dsd.view.adapter.WorldTableModel;
import com.udesc.t2_dsd.view.adapter.WorldCellRender;
import com.udesc.t2_dsd.controller.SimulatorController;
import com.udesc.t2_dsd.model.Cell;
import com.udesc.t2_dsd.util.Refresh;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimulatorView extends javax.swing.JFrame {
    private SimulatorController controller;
    private WorldTableModel tableModel = new WorldTableModel();
    private Refresh refresh;
  
    public SimulatorView() {
        this.controller = new SimulatorController(SimulatorView.this);
        initComponents();
        applyDefaults();
        refresh = new Refresh(jTable1);
        refresh.start();
    }

    private void applyDefaults() {
        jTable1.setTableHeader(null);
        jTable1.setModel(tableModel);
        jTable1.setDefaultRenderer(Cell.class, new WorldCellRender());   
        
        int columnCount = tableModel.getColumnCount();
        int rowCount = tableModel.getRowCount();
        
        Dimension dimension = new Dimension((columnCount*30)+40, (rowCount*30));
        Dimension dimension1 = new Dimension((columnCount*30)+40, (rowCount*30)+150);
        this.setPreferredSize(dimension1);
        this.setSize(dimension1);
        this.setMinimumSize(dimension1);
        this.setMaximumSize(dimension1);
        jTable1.setPreferredSize(dimension);
        jTable1.setSize(dimension);
        jTable1.setMinimumSize(dimension);
        jTable1.setMaximumSize(dimension);
        

        // TODO table is too big for 1366x768

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                refresh.stop();
            }
        });
    }
    
    public void updateTable() {
        jTable1.repaint();
    }
    
    public int getCarCount() {
        return (Integer) jSqtdMax.getValue();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSqtdMax = new javax.swing.JSpinner();
        jBstart = new javax.swing.JButton();
        jBstop = new javax.swing.JButton();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("<html>Qtd. Máx <br>Veículos</html>");

        jBstart.setText("Iniciar");
        jBstart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBstartActionPerformed(evt);
            }
        });

        jBstop.setText("Encerrar");
        jBstop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBstopActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setPreferredSize(new java.awt.Dimension(1000, 1000));
        jTable1.setRowHeight(30);
        jTable1.setTableHeader(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSqtdMax, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                        .addComponent(jBstart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBstop)))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSqtdMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBstart)
                            .addComponent(jBstop)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBstartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBstartActionPerformed
        this.controller.handleStart();
    }//GEN-LAST:event_jBstartActionPerformed

    private void jBstopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBstopActionPerformed
        this.controller.handleStop();
    }//GEN-LAST:event_jBstopActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBstart;
    private javax.swing.JButton jBstop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSpinner jSqtdMax;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
