package presentation.view;

import domain.controller.StartController;
import presentation.adapter.Util;
import presentation.enums.SelectedLockable;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class StartView extends javax.swing.JFrame {
    private final StartController controller;
            
    public StartView() {
        this.controller = new StartController();
        Util.centerFrame(StartView.this);
        initComponents();
    }

    public JTextField getjTfile() {
        return selectMapInput;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectLockableGroup = new javax.swing.ButtonGroup();
        title = new javax.swing.JLabel();
        selectMapInput = new javax.swing.JTextField();
        selectMapBtn = new javax.swing.JButton();
        startBtn = new javax.swing.JButton();
        subtitle = new javax.swing.JLabel();
        monitorRadioBtn = new javax.swing.JRadioButton();
        semaphorRadioBtn = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18)); // NOI18N
        title.setText("Simulador de Tráfego");

        selectMapInput.setEditable(false);

        selectMapBtn.setText("Selecionar");
        selectMapBtn.addActionListener(evt -> selectMapBtnActionPerformed(evt));

        startBtn.setText("Iniciar Partida");
        startBtn.addActionListener(evt -> startBtnActionPerformed(evt));

        subtitle.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14)); // NOI18N
        subtitle.setText("Malha Viária");

        selectLockableGroup.add(monitorRadioBtn);
        selectLockableGroup.add(semaphorRadioBtn);
        semaphorRadioBtn.setText("Semáforo");
        monitorRadioBtn.setText("Monitor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(subtitle)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(monitorRadioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(91, 91, 91)
                                .addComponent(semaphorRadioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(selectMapInput, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(selectMapBtn))
                            .addComponent(startBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(48, 48, 48))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subtitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectMapInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectMapBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monitorRadioBtn)
                    .addComponent(semaphorRadioBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startBtn)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectMapBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectMapBtnActionPerformed
        String selectedFile = this.controller.handleSelectFile(this);
        getjTfile().setText(selectedFile);
    }//GEN-LAST:event_selectMapBtnActionPerformed

    private SelectedLockable getSelectedLockable() {
        if (monitorRadioBtn.isSelected()) {
            return SelectedLockable.MONITOR;
        } else {
            return SelectedLockable.SEMAPHOR;
        }
    }

    private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBtnActionPerformed
        this.controller.handleSelectedLockable(getSelectedLockable());
        this.controller.handleConfirm();
    }//GEN-LAST:event_startBtnActionPerformed

    public static void main(String args[]) {
         try {
             UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new StartView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton monitorRadioBtn;
    private javax.swing.ButtonGroup selectLockableGroup;
    private javax.swing.JButton selectMapBtn;
    private javax.swing.JTextField selectMapInput;
    private javax.swing.JRadioButton semaphorRadioBtn;
    private javax.swing.JButton startBtn;
    private javax.swing.JLabel subtitle;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
