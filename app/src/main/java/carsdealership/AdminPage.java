package carsdealership;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

class AdminPage extends JPanel {
    MainWindow parent;

    AdminPage(MainWindow parent) {
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
        JLabel pageTitle = new JLabel("Admin DashBoard");
        pageTitle.setFont(new Font("Serf", Font.BOLD, 20));
        headerPanel.add(pageTitle);

        // Users managment section
        bodyBox.add(Box.createVerticalStrut(20));
        Box usersManagmentBox = Box.createHorizontalBox();
        usersManagmentBox.setBorder(BorderFactory.createTitledBorder("System Users Managment"));
        usersManagmentBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(usersManagmentBox);

        JButton newManagerButton = new JButton("Add New Manager");
        newManagerButton.addActionListener(
                e -> {

                });
        usersManagmentBox.add(newManagerButton);

        JButton newSellerButton = new JButton("Add New SalesMan Account");
        newSellerButton.addActionListener(
                e -> {

                });
        usersManagmentBox.add(newSellerButton);

        // Products managment section
        bodyBox.add(Box.createVerticalStrut(20));
        Box productsManagmentBox = Box.createHorizontalBox();
        productsManagmentBox.setBorder(BorderFactory.createTitledBorder("Products Managment"));
        productsManagmentBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(productsManagmentBox);

        JButton newItemButton = new JButton("Create New Product");
        newItemButton.addActionListener(
                e -> {

                });
        productsManagmentBox.add(newItemButton);

        JButton updateItemButton = new JButton("Update Product Quantity");
        updateItemButton.addActionListener(
                e -> {

                });
        productsManagmentBox.add(updateItemButton);

        JButton addDiscountButton = new JButton("Apply Discount to Product");
        addDiscountButton.addActionListener(
                e -> {

                });
        productsManagmentBox.add(addDiscountButton);

        // Reports section
        bodyBox.add(Box.createVerticalStrut(20));
        Box reportsBox = Box.createHorizontalBox();
        reportsBox.setBorder(BorderFactory.createTitledBorder("System Reports"));
        reportsBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(reportsBox);

        JButton salesReportButton = new JButton("Sales Report");
        salesReportButton.addActionListener(
                e -> {

                });
        reportsBox.add(salesReportButton);

        JButton goodsReportButton = new JButton("Products Report");
        goodsReportButton.addActionListener(
                e -> {

                });
        reportsBox.add(goodsReportButton);

        JButton usersReportButton = new JButton("System Users Report");
        usersReportButton.addActionListener(
                e -> {

                });
        reportsBox.add(usersReportButton);

        JButton changePasswordButton = new JButton("Change My Password");
        changePasswordButton.addActionListener(
                e -> {

                });
        footerPanel.add(changePasswordButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        footerPanel.add(logoutButton);
    }
}
