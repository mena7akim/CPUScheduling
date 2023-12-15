package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AddProcessFrame extends JFrame {
    public AddProcessFrame(DefaultTableModel tableModel){
        super("Add Process");
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);

        JLabel processNameLabel = new JLabel("Process Name:");
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(processNameLabel, c);

        JTextField processNameField = new JTextField();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(processNameField, c);

        JLabel burstTimeLabel = new JLabel("Burst Time:");
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(burstTimeLabel, c);

        JTextField burstTimeField = new JTextField();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(burstTimeField, c);

        JLabel arrivalTimeLabel = new JLabel("Arrival Time:");
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(arrivalTimeLabel, c);

        JTextField arrivalTimeField = new JTextField();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 2;
        mainPanel.add(arrivalTimeField, c);

        JLabel priorityLabel = new JLabel("Priority:");
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 3;
        mainPanel.add(priorityLabel, c);

        JTextField priorityField = new JTextField();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 3;
        mainPanel.add(priorityField, c);

        JLabel colorLabel = new JLabel("Color:");
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 4;
        mainPanel.add(colorLabel, c);

        ColorPickerButton colorPickerButton = new ColorPickerButton();
        colorPickerButton.setPreferredSize(new Dimension(100, 30));
        colorPickerButton.setSize(new Dimension(100, 30));
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 4;
        mainPanel.add(colorPickerButton, c);


        JButton addButton = new CustomButton("Add", "2C74B3");
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;

        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addButton, c);

        addButton.addActionListener(e -> {
            if(processNameField.getText().equals("") || arrivalTimeField.getText().equals("") || burstTimeField.getText().equals("") || priorityField.getText().equals("") || colorPickerButton.getPickedColor() == null){
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String processName = processNameField.getText();
            int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
            int burstTime = Integer.parseInt(burstTimeField.getText());
            int priority = Integer.parseInt(priorityField.getText());
            Color color = colorPickerButton.getPickedColor();
            tableModel.addRow(new Object[]{processName, burstTime, arrivalTime, priority, colorPickerButton});
            this.dispose();
        });

        this.add(mainPanel);

    }

}
