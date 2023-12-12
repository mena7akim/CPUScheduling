package GUI;

import javax.swing.*;
import java.awt.*;

public class SchedulersPanel extends JPanel {
    public SchedulersPanel() {
        JButton[] buttons = new CustomButton[4];
        buttons[0] = new CustomButton("FCFS", "FFBB5C");
        buttons[1] = new CustomButton("SJF", "FF9B50");
        buttons[2] = new CustomButton("Priority", "E25E3E");
        buttons[3] = new CustomButton("Round Robin", "C63D2F");
        this.setLayout(new GridLayout(2, 2, 10, 10));
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            this.add(button);
        }
    }
}
