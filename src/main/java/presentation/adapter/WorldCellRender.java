package presentation.adapter;

import domain.model.Car;
import domain.model.Cell;
import domain.model.Position;
import data.datasource.Database;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class WorldCellRender extends DefaultTableCellRenderer {
    private Border border;
    private Database db;
    
    public WorldCellRender() {
        this.border = BorderFactory.createLineBorder(Color.BLACK);
        this.db = Database.getInstance();
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        Cell cell = (Cell) value;
        setBackground(cell.toColor());
        setFont(new Font("Dialog", Font.BOLD, 14));
        setBorder(border);
        
        Car car = db.getCar(new Position(row, column));
        if (car != null) {
            setFont(new Font("Serif", Font.BOLD, 24));
            setText("🚗");
            setForeground(car.getColor());
        } else {
            setFont(new Font("Serif", Font.BOLD, 18));
            setForeground(Color.GRAY);
        }
        
        Dimension dimension = new Dimension(30, 30);
        setPreferredSize(dimension);
        setSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        return component;
    }
}
