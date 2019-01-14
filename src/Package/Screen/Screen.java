package Package.Screen;

import Package.Demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Package.Demo.*;
import static Package.Demo.currentScreen;


public class Screen implements ActionListener, KeyListener {

    public static GridBagConstraints gridConstraints;

    public static JTextField userNameTextField;
    public static JTextField PasswordTextField;
    public static JButton SignUp;
    public static JButton Login;
    public static Connection conn;

    public Screen() {

    }

    public Screen(JPanel p) {

        p.setLayout(new GridBagLayout());

        gridConstraints = new GridBagConstraints();
    }

    public static void draw(JPanel p) {

        p.removeAll();
        p.revalidate();
        p.repaint();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 1;

        // Number of rows the component takes up

        gridConstraints.gridheight = 1;

        gridConstraints.weightx = 50;

        gridConstraints.weighty = 50;

        // Defines padding top, left, bottom, right

        gridConstraints.insets = new Insets(5, 5, 5, 5);


        gridConstraints.anchor = GridBagConstraints.CENTER;


        //gridConstraints.fill = GridBagConstraints.BOTH;

        SignUp = new JButton("Sign Up");
        p.add(SignUp, gridConstraints);
        Login = new JButton("Login ");
        gridConstraints.gridheight = 2;
        gridConstraints.gridwidth = 1;
        gridConstraints.weightx = 50;

        gridConstraints.weighty = 50;

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        p.add(Login, gridConstraints);

        Screen s = new Screen();
        SignUp.addActionListener(s);
        Login.addActionListener(s);
        p.addKeyListener(s);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:src/Package/mydatabase.sqlite");
        } catch (ClassNotFoundException e1) {
            System.out.println("Driver file needs to be downloaded ");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        if (e.getSource() == this.SignUp) {
            currentScreen = new SignUpScreen(panel);
        } else {
            currentScreen = new LoginScreen(panel);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void onKeyPress() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            currentScreen.onKeyPress();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("dfg");
            currentScreen.onKeyPress();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
