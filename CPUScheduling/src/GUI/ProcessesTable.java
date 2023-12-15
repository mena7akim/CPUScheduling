package GUI;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

import Schedulers.Process;


public class ProcessesTable extends JTable{

    public ProcessesTable(Object[][] data, String[] columnNames) {
        super(new DefaultTableModel(data, columnNames));
        this.setDefaultRenderer(Object.class, new CustomTableCellRenderer("dee2e6","ced4da"));
        this.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        this.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
        this.getColumnModel().getColumn(4).setMaxWidth(60);
        this.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background");
    }

    public Process[] getProcesses(){
        TableModel model = this.getModel();
        Process[] processes = new Process[model.getRowCount()];
        for(int i = 0; i < model.getRowCount(); i++){
            Color color;
            if(model.getValueAt(i, 4) instanceof JButton)
                color = ((JButton) model.getValueAt(i, 4)).getBackground();
            else if (model.getValueAt(i, 4) instanceof Color)
                color = (Color) model.getValueAt(i, 4);
            else
                color = Color.WHITE;
            processes[i] = new Process((String) model.getValueAt(i, 0), color, (int) model.getValueAt(i, 1), (int) model.getValueAt(i, 2), (int) model.getValueAt(i, 3));
            System.out.println(processes[i].getName() + ' ' +  processes[i].getArrivalTime() + ' ' + processes[i].getBurstTime() + ' ' + processes[i].getPriority());
        }
        return processes;
    }

}
