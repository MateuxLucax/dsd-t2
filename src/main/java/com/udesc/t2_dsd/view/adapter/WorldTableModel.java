package com.udesc.t2_dsd.view.adapter;

import com.udesc.t2_dsd.model.Cell;
import com.udesc.t2_dsd.infra.Database;
import com.udesc.t2_dsd.model.World;
import javax.swing.table.AbstractTableModel;

public class WorldTableModel extends AbstractTableModel {
    private World world;
    
    public WorldTableModel() {
        this.world = Database.getInstance().getWorld();
    }

    @Override
    public int getRowCount() {
        return world.rows();
    }

    @Override
    public int getColumnCount() {
        return world.cols();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cell cell = world.get(rowIndex, columnIndex);
        return cell;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Cell.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
