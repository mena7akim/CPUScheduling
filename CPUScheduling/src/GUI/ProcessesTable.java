package GUI;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;


public class ProcessesTable extends JTable{

    public ProcessesTable(Object[][] data, String[] columnNames) {
        super(new DefaultTableModel(data, columnNames)); // Use DefaultTableModel in the constructor
        this.setDefaultRenderer(Object.class, new CustomTableCellRenderer("dee2e6","ced4da"));
        this.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background");
    }

}
