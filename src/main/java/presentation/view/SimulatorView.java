package presentation.view;

import domain.controller.SimulatorController;
import domain.model.enums.Cell;
import presentation.adapter.WorldCellRender;
import presentation.adapter.WorldTableModel;

import java.awt.*;

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
        
        Dimension dimension = new Dimension((columnCount * 30) + 40, (rowCount * 30));
        Dimension dimension1 = new Dimension((columnCount * 30) + 40, (rowCount * 30) + 150);
        
        int spacing = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (dimension.getHeight() > screenSize.getHeight()-spacing)
            dimension.setSize(dimension.getWidth(), screenSize.getHeight()-spacing);
        
        if (dimension.getWidth() > screenSize.getWidth() - spacing)
            dimension.setSize(screenSize.getWidth() - spacing, dimension.getWidth());
        
        if (dimension1.getHeight() > screenSize.getHeight() - spacing)
            dimension1.setSize(dimension1.getWidth(), screenSize.getHeight() - spacing);
        
        if (dimension1.getWidth() > screenSize.getWidth() - spacing)
            dimension1.setSize(screenSize.getWidth() - spacing, dimension1.getWidth());
        
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
        startBtn = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSintervalo = new javax.swing.JSpinner();
        jBstopAndWait = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("<html>Qtd. Máx <br>Veículos</html>");

        startBtn.setText("Iniciar");
        startBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBstartActionPerformed(evt);
            }
        });

        stopButton.setText("Encerrar");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBstopActionPerformed(evt);
            }
        });

        jLabel2.setText("Intervalo");

        jBstopAndWait.setText("<html>Encerrar e<br>Aguardar</html>");
        jBstopAndWait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBstopAndWaitActionPerformed(evt);
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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSqtdMax, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSintervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(startBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stopButton)
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
                            .addComponent(startBtn)
                            .addComponent(stopButton)
                            .addComponent(jBstopAndWait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBstartActionPerformed(java.awt.event.ActionEvent ignored) {
        this.controller.handleStart(getCarCount(), getInterval());
    }

    private void jBstopActionPerformed(java.awt.event.ActionEvent ignored) {
        this.startBtn.setEnabled(false);
        if (this.controller.handleStop()) {
            this.startBtn.setEnabled(true);
        }
    }

    private void jBstopAndWaitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBstopAndWaitActionPerformed
        this.startBtn.setEnabled(false);
        if (this.controller.handleStop()) {
            this.startBtn.setEnabled(true);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton startBtn;
    private javax.swing.JButton stopButton;
    private javax.swing.JButton jBstopAndWait;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSintervalo;
    private javax.swing.JSpinner jSqtdMax;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
