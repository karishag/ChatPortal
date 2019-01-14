package Package.Screen;

import Package.Demo;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;
import java.sql.SQLException;
import java.sql.Statement;

import static Package.Demo.currentScreen;
import static Package.Demo.panel;

public class SignUpScreen extends Screen{

    public static JTextField userNameTextField;
    public static JTextField emailTextField;
    public static JTextField DOBTextField;
    public static JTextField PasswordTextField;
    public static JButton SignUP;
    public static int f=0;


    public SignUpScreen() {

    }

    public SignUpScreen(JPanel p){
        super(p);
        System.out.println("HI");
        p.removeAll();
        p.revalidate();
        p.repaint();
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        JLabel userNameLabel = new JLabel("USERNAME : ");
        p.add(userNameLabel,gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        userNameTextField = new JTextField(20);
        p.add(userNameTextField,gridConstraints);
        userNameTextField.requestFocus();


        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        JLabel emailLabel = new JLabel("EMAIL : ");
        p.add(emailLabel,gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        emailTextField = new JTextField(20);
        p.add(emailTextField,gridConstraints);


        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        JLabel DOBLabel = new JLabel("DATE OF BIRTH : ");
        p.add(DOBLabel,gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        DOBTextField = new JTextField(20);
        p.add(DOBTextField,gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        JLabel PasswordLabel = new JLabel("PASSWORD : ");
        p.add(PasswordLabel,gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        PasswordTextField = new JPasswordField(20);
        p.add(PasswordTextField,gridConstraints);

        gridConstraints.fill = GridBagConstraints.SOUTHEAST;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 10;
        SignUP = new JButton("Sign Up");
        p.add(SignUP,gridConstraints);
        SignUP.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    signup();
                    if(f!=1)
                        currentScreen = new ChatScreen(p);
                } catch (Exception e1) {
                    new SignUpScreen(panel);
                    //e1.printStackTrace();
                }
            }

        });
    }

    @Override
    public void onKeyPress() {
        if(SignUP.isSelected()) {
            try {
                signup();
            } catch (Exception e1) {
                new SignUpScreen(panel);
                //e1.printStackTrace();
            }
        }
        System.out.println("fsg");
        draw(panel);

    }

    public static void signup() throws Exception{
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            String name1 = userNameTextField.getText();
            String email = emailTextField.getText();
            String dob = DOBTextField.getText();
            String password = PasswordTextField.getText();


            if(name1.equals("") || email.equals("") || dob.equals("") || password.equals("")) {
                throw new emptyFields(SignUP);
            }
            else{
                String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                stmt.execute("Insert into `Users`(`Username`,`Password`,`DOB`,`Email`) VALUES ('"+name1+"','"+hashed+"','"+dob+"','"+email+"')");
            }

        } catch (SQLException e) {
            f=1;
            e.printStackTrace();
        }
    }

}
