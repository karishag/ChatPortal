package Package;

import Package.Screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Demo extends JFrame {

    public static Screen currentScreen;
    public static JPanel panel;

    public static void main(String[] args) {
        new Demo();

    }

    public Demo(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(600,400));
        panel = new JPanel();

        panel.setFocusable(true);
        panel.requestFocus();
        currentScreen = new Screen(panel);
        currentScreen.draw(panel);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
