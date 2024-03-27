package carsdealership;

import javax.swing.*;


import java.awt.*;

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

        JLabel title = new JLabel("Salesman Page");
        title.setFont(new Font("Serf", Font.BOLD, 20));
        headerPanel.add(title);


        bodyBox.add(Box.createVerticalStrut(20));
        bodyBox.add(new JLabel("Customer related buttons"));
        Box salesManagementBox = Box.createHorizontalBox();
        salesManagementBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(salesManagementBox);

        JButton addNewCustomer = new JButton("Add new customer");
        addNewCustomer.addActionListener(
                e-> {}
        );
        salesManagementBox.add(addNewCustomer);


        JButton createNewTransaction = new JButton("Create new transaction");
        createNewTransaction.addActionListener(
                e->{}
        );
        salesManagementBox.add(createNewTransaction);


        bodyBox.add(Box.createVerticalStrut(20));
        bodyBox.add(new JLabel("Searching related buttons"));
        Box searchManagementBox = Box.createHorizontalBox();
        searchManagementBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(searchManagementBox);


        JLabel searchLabel = new JLabel("search for customers: ");
        searchManagementBox.add(searchLabel);

        JTextField searchCustomers = new JTextField();
        searchCustomers.setMaximumSize(new Dimension(150, 30));
        searchCustomers.setPreferredSize(new Dimension(150, 30));
        searchCustomers.addActionListener(
                e->{}
        );
        searchManagementBox.add(searchCustomers);


        JButton passwordChanger = new JButton("Change password");
        passwordChanger.addActionListener(
                e->{}
        );
        footerPanel.add(passwordChanger);


        JButton deleteAccount = new JButton("Delete account");
        deleteAccount.addActionListener(
                e->{}
        );
        footerPanel.add(deleteAccount);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        footerPanel.add(logoutButton);
    }
}
