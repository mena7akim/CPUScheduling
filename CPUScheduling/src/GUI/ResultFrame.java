package GUI;

import Schedulers.RoundRobinProcess;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame{
    public ResultFrame(String Title, Schedulers.Process[] processes){
        super(Title);
        this.setSize(1200, 1000);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        CustomGanttChart ganttChart = new CustomGanttChart(Title, processes);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.7;
        c.weightx = 1;
        this.add(ganttChart, c);
        Object[][] data = new Object[processes.length + 1][3];
        String[] columnNames = {"Process Name", "Waiting Time", "Turnaround Time"};
        for(int i = 0; i < processes.length; i++){
            data[i][0] = processes[i].getName();
            data[i][1] = processes[i].getWaitingTime();
            data[i][2] = processes[i].getTurnaroundTime();
        }
        data[processes.length][0] = "Average";
        data[processes.length][1] = Schedulers.Process.getAverageWaitingTime(processes);
        data[processes.length][2] = Schedulers.Process.getAverageTurnaroundTime(processes);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.3;
        c.weightx = 1;
        this.add(scrollPane, c);
        this.setVisible(true);
    }
}
