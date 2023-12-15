package GUI;

import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.jfree.layout.CenterLayout;


public class Application {
    final static String[] PANELS = {"Main", "Schedulers"};
    public static void main(String[] args) {
        FlatLaf.registerCustomDefaultsSource("GUI");
        FlatMacLightLaf.setup();

        JFrame frame = new JFrame("CPU Scheduler");
        JPanel panel = new JPanel();
        panel.setLayout(new CardLayout());
        frame.setLayout(new GridBagLayout());
        JButton backButton = new CustomButton("Back", "EEEEEE");
        backButton.setForeground(Color.BLACK);
        backButton.setPreferredSize(new Dimension(124, 48));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        frame.add(backButton, c);
        JButton run = new CustomButton("Run", "eb5e28");
        run.setPreferredSize(new Dimension(100, 40));
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.9;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        Object[][] data = new Object[4][5];
        data[0] = new Object[]{"P1", 17, 0, 4, null};
        data[1] = new Object[]{"P2", 6, 3, 9, null};
        data[2] = new Object[]{"P3", 10, 4, 3, null};
        data[3] = new Object[]{"P4", 4, 29, 8, null};
        for(int i = 0; i < data.length; i++){
            data[i][4] = new ColorPickerButton();
        }
        String[] columnNames = {"Process Name", "Burst Time", "Arrival Time", "Priority", "Color"};
        JTable table = new ProcessesTable(data, columnNames);
        JPanel schedulersPanel = new SchedulersPanel(table);
        JPanel mainPanel = new MainPanel(run, table);
        panel.add(mainPanel, PANELS[0]);
        panel.add(schedulersPanel, PANELS[1]);
        CardLayout cl = (CardLayout) panel.getLayout();
        cl.show(panel, PANELS[0]);
        frame.add(panel, c);
        backButton.setVisible(false);
        backButton.addActionListener(e -> {
            cl.show(panel, PANELS[0]);
            backButton.setVisible(false);
        });
        run.addActionListener(e -> {
            cl.show(panel, PANELS[1]);
            backButton.setVisible(true);
        });
        frame.setPreferredSize(new Dimension(1000, 650));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
