package GUI;

import javax.swing.*;
import java.awt.*;

public class ColorPickerButton extends JButton{
    Color pickedColor = Color.decode("#" + "FFFFFF");
    public ColorPickerButton(){
        super();
        this.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Pick a Color", Color.BLACK);
            if(color != null){
                this.setBackground(color);
                pickedColor = color;
            }
        });
        this.setBackground(Color.decode("#" + "FFFFFF"));
        this.setBorderPainted(false);
    }

    public Color getPickedColor(){
        return pickedColor;
    }

    public void setPickedColor(Color color){
        this.setBackground(color);
        pickedColor = color;
    }
}
