package Package;

import Package.Screen.Screen;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public static Screen currentScreen;

    public Panel() {
        //this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        this.setFocusable(true);
        //this.addKeyListener();

        JButton SignUp = new JButton("Sign Up");


    }

    @Override
    public void addNotify() {
        super.addNotify();

        this.requestFocus();

    }
}
