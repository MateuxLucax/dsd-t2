package presentation.view;

import presentation.adapter.WorldTableModel;
import presentation.adapter.WorldCellRender;
import domain.controller.SimulatorController;
import domain.model.Cell;
import java.awt.Dimension;

public class SimulatorView extends javax.swing.JFrame implements UpdatableSimulatorView {
    private final SimulatorController controller;
    private final WorldTableModel tableModel = new WorldTableModel();
  
    public SimulatorView() {
        initComponents();
        this.controller = new SimulatorController(SimulatorView.this);
        applyDefaults();
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
    }
    
    @Override
    public void updateView() {
        updateTable();
    }
    
    private void updateTable() {
        jTable1.repaint();
    }
    
    public int getCarCount() {
        return (Integer) jSqtdMax.getValue();
    }
    
    public int getInterval() {
        return (Integer) jSintervalo.getValue();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSqtdMax = new javax.swing.JSpinner();
        jBstart = new javax.swing.JButton();
        jBstop = new javax.swing.JButton();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jSintervalo = new javax.swing.JSpinner();
        jBstopAndWait = new javax.swing.JButton();

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

        jLabel2.setText("Intervalo");

        jBstopAndWait.setText("<html>Encerrar e<br>Aguardar</html>");
        jBstopAndWait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBstopAndWaitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSqtdMax, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSintervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jBstart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBstop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBstopAndWait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSqtdMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jSintervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBstart)
                            .addComponent(jBstop)
                            .addComponent(jBstopAndWait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(jTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBstartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBstartActionPerformed
        this.controller.handleStart(getCarCount(), getInterval());
    }//GEN-LAST:event_jBstartActionPerformed

    private void jBstopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBstopActionPerformed
        this.controller.handleStop();
    }//GEN-LAST:event_jBstopActionPerformed

    private void jBstopAndWaitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBstopAndWaitActionPerformed
        this.controller.handleStopAndWait();
    }//GEN-LAST:event_jBstopAndWaitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBstart;
    private javax.swing.JButton jBstop;
    private javax.swing.JButton jBstopAndWait;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jSintervalo;
    private javax.swing.JSpinner jSqtdMax;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
