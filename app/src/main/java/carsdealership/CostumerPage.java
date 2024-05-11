package carsdealership;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Component;

class CostumerPage extends JPanel {
    MainWindow parent;

    CostumerPage(MainWindow parent) {
        this.parent = parent;

        this.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.add(headerPanel, BorderLayout.NORTH);

        Box bodyBox = Box.createVerticalBox();
        bodyBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(bodyBox);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        this.add(footerPanel, BorderLayout.SOUTH);

        // Page Title
        JLabel pageTitle = new JLabel("Costumer DashBoard (" + parent.currentUsername +")");
        pageTitle.setFont(new Font("Serf", Font.BOLD, 20));
        headerPanel.add(pageTitle);

        JButton ShowPreviousPurchases = new JButton("Show Previous Purchases");

        PreviousPurchasesButtonListener SPPListener = new PreviousPurchasesButtonListener();
        ShowPreviousPurchases.addActionListener(SPPListener);

        bodyBox.add(ShowPreviousPurchases);

        // Statstics section
        bodyBox.add(Box.createVerticalStrut(20));
        Box myStatisticsBox = Box.createHorizontalBox();
        myStatisticsBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        myStatisticsBox.setBorder(BorderFactory.createTitledBorder("My Statstics"));
        bodyBox.add(myStatisticsBox);

        try (Database db = new Database()) {
            myStatisticsBox
                    .add(new JLabel("Count of Purchases: " + db.getPurchasesCountByCostumerId(parent.currentUserId)));
            parent.container.remove(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Footer section
        JButton DeleteAccountButton = new JButton("Delete My Account");
        DeleteAccountButton.addActionListener(
                e -> {
                    try (Database db = new Database()) {
                        db.deleteUserAccount(parent.currentUserId);
                        parent.container.remove(this);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
        footerPanel.add(DeleteAccountButton);

        JButton ChangePassword = new JButton("Change My Password");
        ChangePassword.addActionListener(e -> {
            ChangePasswordDialog dialog = new ChangePasswordDialog(parent, true);
            dialog.setVisible(true);
        });
        footerPanel.add(ChangePassword);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        footerPanel.add(logoutButton);

    }

    private class PreviousPurchasesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            PreviousPurchasesListDialog dialog = new PreviousPurchasesListDialog(parent, true, parent.currentUserId);
            dialog.setVisible(true);
        }
    }
}
