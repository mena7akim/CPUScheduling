package GUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        super();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value instanceof JButton)
            this.setBackground(((JButton) value).getBackground());
        else if(value instanceof Color)
            this.setBackground((Color) value);
        else
            this.setBackground(Color.WHITE);
        return this;
    }
}
