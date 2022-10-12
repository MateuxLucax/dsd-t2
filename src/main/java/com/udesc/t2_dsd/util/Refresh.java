package com.udesc.t2_dsd.util;

import javax.swing.JTable;

public class Refresh extends Thread {
    private JTable table;
    public Refresh(JTable table) {
        this.table = table;
    }

    @Override
    public void run() {
        while (true) {            
            try {
                Thread.sleep(Constants.refreshIntervalMs);
                table.repaint();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
