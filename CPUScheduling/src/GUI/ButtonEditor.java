package GUI;

import javax.swing.*;
import java.awt.*;

public class ButtonEditor extends DefaultCellEditor {
    JButton button;
    Color currentColor = Color.WHITE;
    boolean clicked;
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.addActionListener(e -> {
            fireEditingStopped();
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(value instanceof JButton)
            this.button.setBackground(((JButton) value).getBackground());
        else if(value instanceof Color)
            this.button.setBackground((Color) value);
        else
            this.button.setBackground(Color.WHITE);
        clicked = true;
        return button;
    }

    public Object getCellEditorValue() {
        if(clicked){
            Color selectedColor = JColorChooser.showDialog(null, "Choose a Color", button.getBackground());
            if (selectedColor != null) {
                button.setBackground(selectedColor);
                currentColor = selectedColor;
            }
        }
        clicked = false;
        return new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
    }
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
