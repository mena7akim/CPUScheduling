package GUI;

import javax.swing.*;
import java.awt.*;

import Schedulers.*;
import Schedulers.Process;

public class SchedulersPanel extends JPanel {
    public SchedulersPanel(JTable table) {
        JButton[] buttons = new CustomButton[4];
        buttons[0] = new CustomButton("SJF", "FFBB5C");
        buttons[1] = new CustomButton("SRTF", "FF9B50");
        buttons[2] = new CustomButton("Priority", "E25E3E");
        buttons[3] = new CustomButton("Round Robin", "C63D2F");
        this.setLayout(new GridLayout(2, 2, 10, 10));
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            this.add(button);
        }
        buttons[0].addActionListener(e -> {
            Process[] processes = ((ProcessesTable) table).getProcesses();
            JFrame frame = new JFrame("Context Switch Time");
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(10, 10, 10, 10);
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 0.5;
            c.weightx = 1;
            JLabel label = new JLabel("Enter Context Switch Time:");
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            frame.add(label, c);
            JTextField textField = new JTextField();
            textField.setFont(new Font("Arial", Font.PLAIN, 20));
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 0.5;
            c.weightx = 1;
            frame.add(textField, c);
            JButton submit = new CustomButton("Submit", "C63D2F");
            submit.setFont(new Font("Arial", Font.PLAIN, 20));
            c.gridx = 0;
            c.gridy = 2;
            c.weighty = 0.5;
            c.weightx = 1;
            frame.add(submit, c);
            frame.setVisible(true);

            submit.addActionListener(e1 -> {
                try {
                    int contextSwitchTime = Integer.parseInt(textField.getText());
                    SJFScheduler sjf = new SJFScheduler(processes, contextSwitchTime);
                    sjf.runScheduler();
                    ResultFrame chart = new ResultFrame("SJF Scheduler", processes);
                    frame.dispose();
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        buttons[1].addActionListener(e -> {
            Process[] processes = ((ProcessesTable) table).getProcesses();
            SRTFScheduler srtf = new SRTFScheduler(processes);
            srtf.runScheduler();
            ResultFrame chart = new ResultFrame("SRTF Scheduler", processes);
        });
        buttons[2].addActionListener(e -> {
            Process[] processes = ((ProcessesTable) table).getProcesses();
            PriorityScheduler priority = new PriorityScheduler(processes);
            priority.runScheduler();
            ResultFrame chart = new ResultFrame("Priority Scheduler", processes);
        });
        buttons[3].addActionListener(e -> {
            Process[] processes = ((ProcessesTable) table).getProcesses();

            JFrame frame = new JFrame("Quantum Time");
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(10, 10, 10, 10);
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 0.5;
            c.weightx = 1;
            JLabel label = new JLabel("Enter Quantum Time:");
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            frame.add(label, c);
            JTextField textField = new JTextField();
            textField.setFont(new Font("Arial", Font.PLAIN, 20));
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 0.5;
            c.weightx = 1;
            frame.add(textField, c);
            JButton submit = new CustomButton("Submit", "C63D2F");
            submit.setFont(new Font("Arial", Font.PLAIN, 20));
            c.gridx = 0;
            c.gridy = 2;
            c.weighty = 0.5;
            c.weightx = 1;
            frame.add(submit, c);
            frame.setVisible(true);

            submit.addActionListener(e1 -> {
                try {
                    int quantumTime = Integer.parseInt(textField.getText());
                    RoundRobinScheduler roundRobin = new RoundRobinScheduler(quantumTime, processes);
                    roundRobin.runScheduler();
                    RoundRobinProcess[] roundRobinProcesses = roundRobin.getProcesses();
                    RoundRobinResultFrame chart = new RoundRobinResultFrame("Round Robin Scheduler", roundRobinProcesses);
                    frame.dispose();
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }
}
