package Package.Screen;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Package.Demo.currentScreen;
import static Package.Demo.panel;

public class LoginScreen extends Screen {

    public static JButton Login;
    public static String name;

    public LoginScreen(JPanel p) {
        super(p);
        p.removeAll();
        p.revalidate();
        p.repaint();
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        JLabel userNameLabel = new JLabel("USERNAME : ");
        p.add(userNameLabel, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        userNameTextField = new JTextField(20);
        p.add(userNameTextField, gridConstraints);
        //userNameTextField.requestFocus();

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        JLabel PasswordLabel = new JLabel("PASSWORD : ");
        p.add(PasswordLabel, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        PasswordTextField = new JPasswordField(20);
        p.add(PasswordTextField, gridConstraints);

        gridConstraints.fill = GridBagConstraints.RELATIVE;
        //gridConstraints.gridx = 0;
        gridConstraints.gridy = 5;
        Login = new JButton("Login");
        p.add(Login, gridConstraints);
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int c = 0;
                try {
                    c = verify();
                    if (c == 1) {
                        //System.out.println(c);
                        currentScreen = new ChatScreen(panel);
                    } else {
                        System.out.println("Invalid details");
                    }
                } catch (Exception e1) {
                    new LoginScreen(panel);
                }
            }
        });
    }

    @Override
    public void onKeyPress() {
        draw(panel);

    }

    public static int verify() throws Exception {
        name = userNameTextField.getText();
        String password = PasswordTextField.getText();
        System.out.println(name);

        if (name.equals("") || password.equals("")) {
            throw new emptyFields(Login);
        }
        int c = 0;



        try {
            Statement stmt = conn.createStatement();
            ResultSet r = stmt.executeQuery("SELECT `Password` from `Users` WHERE `Username` = '" + name + "'");
            while (r.next()) {
                //String n = r.getString("Username");
                String p = r.getString("Password");

                if (BCrypt.checkpw(password,p)) {
                    System.out.println("It matches");
                    c = 1;
                    break;
                }
                else
                    System.out.println("It does not match");

                /*if (name.equals(n) && password.equals(p)) {
                    c = 1;
                    break;
                }*/
            }
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }


}
