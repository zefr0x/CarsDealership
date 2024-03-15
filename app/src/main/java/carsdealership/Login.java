package carsdealership;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

enum UserType {
    Admin,
    SalesMan,
    Costumer,
}

class LoginDialog extends JDialog {
    JTextField userNameField;
    JPasswordField userPasswordField;
    UserType userType;

    LoginDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setMinimumSize(new java.awt.Dimension(300, 200));
        this.setLayout(new GridLayout(3, 2));

        this.userNameField = new JTextField();
        this.add(new JLabel("Username"));
        this.add(this.userNameField);

        this.userPasswordField = new JPasswordField();
        this.add(new JLabel("Password"));
        this.add(this.userPasswordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        this.add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> this.setVisible(false));
        this.add(cancelButton);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // TODO: Validate username and password.

            // WARN: This code is just for testing, tell database logic implemented.
            if (userNameField.getText().equals("admin")) {
                LoginDialog.this.userType = UserType.Admin;
                LoginDialog.this.setVisible(false);
            } else if (userNameField.getText().equals("salesman")) {
                LoginDialog.this.userType = UserType.SalesMan;
                LoginDialog.this.setVisible(false);
            } else if (userNameField.getText().equals("costumer")) {
                LoginDialog.this.userType = UserType.Costumer;
                LoginDialog.this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid login credentials", "Error: Invalid login credentials",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public UserType getUserType() {
        return this.userType;
    }

    public String getUserName() {
        return this.userNameField.getText();
    }

    public char[] getUserPassword() {
        return this.userPasswordField.getPassword();
    }
}
