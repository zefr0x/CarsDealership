package carsdealership;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.GridLayout;

enum UserType {
    Admin,
    SalesMan,
    Costumer,
    Unknown,
}

class LoginDialog extends JDialog {
    JTextField userNameField;
    JPasswordField userPasswordField;
    UserType userType;
    String userId;

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
            UserType userType = null;
            String userId = null;
            String password = null;

            try (Database db = new Database()) {
                userId = db.getUserIdByUsername(LoginDialog.this.userNameField.getText());
                userType = db.getUserTypeById(userId);
                password = db.getUserPasswordById(userId);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "SQLException",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            if (password != null && BCrypt.checkpw(LoginDialog.this.userPasswordField.getText(), password)) {
                LoginDialog.this.userType = userType;
                LoginDialog.this.userId = userId;

                LoginDialog.this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Error: Invalid login credentials", "Invalid login credentials",
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
