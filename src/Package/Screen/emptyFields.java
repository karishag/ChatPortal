package Package.Screen;

import javax.swing.*;

import static Package.Screen.SignUpScreen.*;

public class emptyFields extends Exception {

    public emptyFields(JButton b){
        System.out.println("Fill all the entries");
        b.setEnabled(false);
        JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>You must enter all the details to proceed further.</FONT></HTML>");
        JOptionPane.showMessageDialog(null,errorFields);
        userNameTextField.setText("");
        PasswordTextField.setText("");
        DOBTextField.setText("");
        emailTextField.setText("");

        b.setEnabled(true);
    }
}
