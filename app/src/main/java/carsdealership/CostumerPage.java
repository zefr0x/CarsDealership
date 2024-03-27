package carsdealership;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JLabel pageTitle = new JLabel("Costumer DashBoard");
        pageTitle.setFont(new Font("Serf", Font.BOLD, 20));
        headerPanel.add(pageTitle);

        //
        JButton ChooseAndBuy = new JButton("Select and Buy a Product");
        JButton ShowPreviousPurchases = new JButton("Previous Purchases");

        PreviousPurchasesButtonListener SPPListener = new PreviousPurchasesButtonListener();
        ShowPreviousPurchases.addActionListener(SPPListener);

        bodyBox.add(ChooseAndBuy);
        bodyBox.add(ShowPreviousPurchases);

        // Statstics section
        bodyBox.add(Box.createVerticalStrut(20));
        Box myStatisticsBox = Box.createHorizontalBox();
        myStatisticsBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        myStatisticsBox.setBorder(BorderFactory.createTitledBorder("My Statstics"));
        bodyBox.add(myStatisticsBox);

        myStatisticsBox.add(new JLabel("Count of Purchases: 0"));

        // Footer section
        JButton DeleteAccountButton = new JButton("Delete My Account");
        footerPanel.add(DeleteAccountButton);

        JButton ChangePassword = new JButton("Change My Password");
        footerPanel.add(ChangePassword);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        footerPanel.add(logoutButton);

    }

    private class PreviousPurchasesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JOptionPane.showMessageDialog(CostumerPage.this, "You have no previous purchases!");
        }
    }
}
