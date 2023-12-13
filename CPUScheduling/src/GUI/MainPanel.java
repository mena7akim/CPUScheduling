package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.FlatClientProperties;

public class MainPanel extends JPanel {
    public MainPanel(JButton run){
        setLayout(new GridBagLayout());
        JButton add = new CustomButton("Add", "2C74B3");
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        add.setPreferredSize(new Dimension(100, 40));
        JButton delete = new CustomButton("Delete", "ba181b");
        delete.setPreferredSize(new Dimension(100, 40));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(add, c);
        c.gridx = 1;
        c.gridy = 0;
        buttonPanel.add(delete, c);
        c.gridx = 3;
        c.gridy = 0;
        buttonPanel.add(run, c);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = .1;
        c.anchor = GridBagConstraints.LINE_END;
        add(buttonPanel, c);
        c.insets = new Insets(0, 0, 0, 10);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = .9;
        c.anchor = GridBagConstraints.PAGE_START;

        Object[][] data = new Object[5][4];
        data[0] = new Object[]{"P1", 0, 5, 1};
        data[1] = new Object[]{"P2", 1, 3, 2};
        data[2] = new Object[]{"P3", 2, 8, 3};
        data[3] = new Object[]{"P4", 3, 6, 4};
        data[4] = new Object[]{"P5", 4, 4, 5};
        String[] columnNames = {"Process Name", "Arrival Time", "Burst Time", "Priority"};
        JTable table = new ProcessesTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        table.setBorder(BorderFactory.createEmptyBorder());
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, c);
        add.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            AddProcessFrame addProcessFrame = new AddProcessFrame(model);
            addProcessFrame.setVisible(true);
        });
        delete.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                model.removeRow(selectedRow);
            }
            else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


    }
}
