package carsdealership;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JPasswordField;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class ChangePasswordDialog extends JDialog {
    String userId;
    JPasswordField newPassword;

    ChangePasswordDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Product Discount");
        this.setMinimumSize(new java.awt.Dimension(400, 100));
        this.setLayout(new GridLayout(2, 2));

        MainWindow m = (MainWindow) parent;
        userId = m.currentUserId;

        this.newPassword = new JPasswordField();
        this.add(new JLabel("New Password"));
        this.add(this.newPassword);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ApplyButtonListener());
        this.add(applyButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            this.setVisible(false);
        });
        this.add(closeButton);
    }

    private class ApplyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                Database db = new Database();

                db.updatePassword(ChangePasswordDialog.this.userId,
                        BCrypt.hashpw(ChangePasswordDialog.this.newPassword.getText(), BCrypt.gensalt()));
                JOptionPane.showMessageDialog(null, "Your password was updated!", "Password Updated!",
                        JOptionPane.INFORMATION_MESSAGE);

                db.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
