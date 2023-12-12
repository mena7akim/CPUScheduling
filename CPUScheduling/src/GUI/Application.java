package GUI;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;


public class Application {
    static boolean darkMode = true;
    final static String[] PANELS = {"Main", "Schedulers"};
    public static void main(String[] args) {
        FlatLaf.registerCustomDefaultsSource("GUI");
        FlatMacDarkLaf.setup();

        JFrame frame = new JFrame("CPU Scheduler");
        JPanel panel = new JPanel();
        panel.setLayout(new CardLayout());
        frame.setLayout(new GridBagLayout());
        JButton button = new CustomButton("Change Theme", "414141");
        JButton backButton = new CustomButton("Back", "414141");
        button.setPreferredSize(new Dimension(124, 48));
        backButton.setPreferredSize(new Dimension(124, 48));
        button.addActionListener(e -> {
            if(darkMode){
                FlatMacLightLaf.setup();
                button.setBackground(Color.decode("#EEEEEE"));
                backButton.setBackground(Color.decode("#EEEEEE"));
                button.setForeground(Color.BLACK);
                backButton.setForeground(Color.BLACK);
                darkMode = false;
            }
            else{
                darkMode = true;
                button.setForeground(Color.WHITE);
                backButton.setForeground(Color.WHITE);
                backButton.setBackground(Color.decode("#414141"));
                button.setBackground(Color.decode("#414141"));
                FlatMacDarkLaf.setup();
            }
            SwingUtilities.updateComponentTreeUI(frame);
        });
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        frame.add(button, c);
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
        JPanel schedulersPanel = new SchedulersPanel();
        JPanel mainPanel = new MainPanel(run);
        panel.add(mainPanel, PANELS[0]);
        panel.add(schedulersPanel, PANELS[1]);
        CardLayout cl = (CardLayout) panel.getLayout();
        cl.show(panel, PANELS[1]);
        frame.add(panel, c);
        backButton.addActionListener(e -> {
            cl.show(panel, PANELS[0]);
        });
        run.addActionListener(e -> {
            cl.show(panel, PANELS[1]);
        });
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
