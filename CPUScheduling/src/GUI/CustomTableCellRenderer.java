package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    public CustomTableCellRenderer(String color1, String color2) {
        super();
        this.color1 = color1;
        this.color2 = color2;
        this.setFont(new Font("Arial", Font.PLAIN, 14));
    }
    String color1;
    String color2;
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setFont(new Font("Arial", Font.PLAIN, 14));
        cell.setForeground(Color.BLACK);
        if(row % 2 == 0){
            this.setBackground(Color.decode("#" + color1));
        }
        else{
            this.setBackground(Color.decode("#" + color2));
        }
        if(isSelected){
            cell.setBackground(Color.decode("#" + "39A7FF"));
            return cell;
        }
        return cell;
    }
}
