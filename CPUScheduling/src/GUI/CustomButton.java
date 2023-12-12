package GUI;
import java.awt.*;
import javax.swing.*;
public class CustomButton extends JButton {
    public CustomButton(String text, String code) {
        super(text);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.decode('#' + code));
    }
}
