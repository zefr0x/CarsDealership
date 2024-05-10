package carsdealership;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

class SalesManPage extends JPanel {
    MainWindow parent;

    SalesManPage(MainWindow parent) {
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
        JLabel title = new JLabel("SalesMan Page");
        title.setFont(new Font("Serf", Font.BOLD, 20));
        headerPanel.add(title);

        // Costumer accounts section
        bodyBox.add(Box.createVerticalStrut(20));
        Box costumersManagementBox = Box.createHorizontalBox();
        costumersManagementBox.setBorder(BorderFactory.createTitledBorder("Customer Account Management"));
        costumersManagementBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(costumersManagementBox);

        JButton addNewCustomer = new JButton("Create New Customer Account");
        addNewCustomer.addActionListener(
                e -> {
                    CreateCostumerAccountDialog dialog = new CreateCostumerAccountDialog(parent, true);
                    dialog.setVisible(true);
                });
        costumersManagementBox.add(addNewCustomer);

        // Sales management section
        bodyBox.add(Box.createVerticalStrut(20));
        Box salesManagementBox = Box.createHorizontalBox();
        salesManagementBox.setBorder(BorderFactory.createTitledBorder("Sales Management"));
        salesManagementBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(salesManagementBox);

        JButton createNewSell = new JButton("Sell Product to Customer");
        createNewSell.addActionListener(
                e -> {
                });
        salesManagementBox.add(createNewSell);

        JButton revertSell = new JButton("Revert a Purchase");
        revertSell.addActionListener(
                e -> {
                });
        salesManagementBox.add(revertSell);

        // Database search and browser section
        bodyBox.add(Box.createVerticalStrut(20));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Search/Browse DataBase");
        Box searchManagementBox = Box.createHorizontalBox();
        searchManagementBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchManagementBox.setBorder(titledBorder);
        bodyBox.add(searchManagementBox);

        JButton costumersList = new JButton("Customers List Dialog");
        costumersList.addActionListener(
                e -> {
                });
        searchManagementBox.add(costumersList);

        JButton salesList = new JButton("Sales List Dialog");
        salesList.addActionListener(
                e -> {
                });
        searchManagementBox.add(salesList);

        // Statistics section
        bodyBox.add(Box.createVerticalStrut(20));
        Box myStatisticsBox = Box.createHorizontalBox();
        myStatisticsBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        myStatisticsBox.setBorder(BorderFactory.createTitledBorder("My Statistics"));
        bodyBox.add(myStatisticsBox);

        myStatisticsBox.add(new JLabel("Sales by Me: 0"));
        myStatisticsBox.add(Box.createRigidArea(new Dimension(20, 0)));
        myStatisticsBox.add(new JLabel("Related Customers: 0"));

        // Footer
        JButton passwordChanger = new JButton("Change Password");
        passwordChanger.addActionListener(
                e -> {
                    ChangePasswordDialog dialog = new ChangePasswordDialog(parent, true);
                    dialog.setVisible(true);
                });
        footerPanel.add(passwordChanger);

        JButton deleteAccount = new JButton("Delete Account");
        deleteAccount.addActionListener(
                e -> {
                });
        footerPanel.add(deleteAccount);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        footerPanel.add(logoutButton);
    }
}

class CreateCostumerAccountDialog extends JDialog {
    JTextField userNameField;
    JPasswordField userPasswordField;
    JTextField firstName;
    JTextField lastName;
    JTextField phoneNumber;
    JTextField emailAddress;

    CreateCostumerAccountDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Create New Customer");
        this.setMinimumSize(new java.awt.Dimension(300, 400));
        this.setLayout(new GridLayout(7, 2));

        this.userNameField = new JTextField();
        this.add(new JLabel("Username"));
        this.add(this.userNameField);

        this.userPasswordField = new JPasswordField();
        this.add(new JLabel("Password"));
        this.add(this.userPasswordField);

        this.firstName = new JTextField();
        this.add(new JLabel("First Name"));
        this.add(this.firstName);

        this.lastName = new JTextField();
        this.add(new JLabel("Last Name"));
        this.add(this.lastName);

        this.phoneNumber = new JTextField();
        this.add(new JLabel("Phone Number"));
        this.add(this.phoneNumber);

        this.emailAddress = new JTextField();
        this.add(new JLabel("Email Address"));
        this.add(this.emailAddress);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new CreateButtonListener());
        this.add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> this.setVisible(false));
        this.add(cancelButton);
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // TODO: Validate data than close dialog.
        }
    }

    // TODO: Implement getters and setters.
}
