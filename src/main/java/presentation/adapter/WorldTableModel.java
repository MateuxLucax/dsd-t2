package presentation.adapter;

import domain.model.Cell;
import data.datasource.Database;
import domain.model.World;
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
