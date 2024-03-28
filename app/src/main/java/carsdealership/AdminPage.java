package carsdealership;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
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

        JButton newManagerButton = new JButton("Add New Admin");
        newManagerButton.addActionListener(
                e -> {
                    CreateAdminAccountDialog dialog = new CreateAdminAccountDialog(parent, true);
                    dialog.setVisible(true);
                });
        usersManagmentBox.add(newManagerButton);

        JButton newSellerButton = new JButton("Add New SalesMan Account");
        newSellerButton.addActionListener(
                e -> {
                    CreateSalesManAccountDialog dialog = new CreateSalesManAccountDialog(parent, true);
                    dialog.setVisible(true);
                });
        usersManagmentBox.add(newSellerButton);

        JButton asociateBranchWithSalesMan = new JButton("Asociate Branch With SalesMan");
        asociateBranchWithSalesMan.addActionListener(
                e -> {

                });
        usersManagmentBox.add(asociateBranchWithSalesMan);

        JButton asociateProductTypeWithSalesMan = new JButton("Asociate Product Type With SalesMan");
        asociateProductTypeWithSalesMan.addActionListener(
                e -> {

                });
        usersManagmentBox.add(asociateProductTypeWithSalesMan);

        // Products managment section
        bodyBox.add(Box.createVerticalStrut(20));
        Box productsManagmentBox = Box.createHorizontalBox();
        productsManagmentBox.setBorder(BorderFactory.createTitledBorder("Products Managment"));
        productsManagmentBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(productsManagmentBox);

        JButton newItemButton = new JButton("Create New Product");
        newItemButton.addActionListener(
                e -> {
                    CreateProductDialog dialog = new CreateProductDialog(parent, true);
                    dialog.setVisible(true);
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

class CreateAdminAccountDialog extends JDialog {
    JTextField userNameField;
    JPasswordField userPasswordField;
    JTextField firstName;
    JTextField lastName;
    JTextField salary;
    JTextField nationality;
    JTextField officeFloorNumber;
    JTextField officeLobbyNumber;
    JTextField officeId;

    CreateAdminAccountDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setMinimumSize(new java.awt.Dimension(300, 400));
        this.setLayout(new GridLayout(10, 2));

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

        this.salary = new JTextField();
        this.add(new JLabel("Salary"));
        this.add(this.salary);

        this.nationality = new JTextField();
        this.add(new JLabel("Nationality"));
        this.add(this.nationality);

        this.officeFloorNumber = new JTextField();
        this.add(new JLabel("Office Floor"));
        this.add(this.officeFloorNumber);

        this.officeLobbyNumber = new JTextField();
        this.add(new JLabel("Office Lobby"));
        this.add(this.officeLobbyNumber);

        this.officeId = new JTextField();
        this.add(new JLabel("Office ID"));
        this.add(this.officeId);

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

class CreateSalesManAccountDialog extends JDialog {
    JTextField userNameField;
    JPasswordField userPasswordField;
    JTextField firstName;
    JTextField lastName;
    JTextField salary;
    JTextField nationality;

    CreateSalesManAccountDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setMinimumSize(new java.awt.Dimension(300, 400));
        this.setLayout(new GridLayout(10, 2));

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

        this.salary = new JTextField();
        this.add(new JLabel("Salary"));
        this.add(this.salary);

        this.nationality = new JTextField();
        this.add(new JLabel("Nationality"));
        this.add(this.nationality);

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

class CreateProductDialog extends JDialog {
    JComboBox<String> productType;
    JTextField vehicleName;
    JTextField basePrice;
    JTextField avilableCount;
    JTextField year;
    JTextField modle;
    JTextField vehicleId;
    JTextField color;
    JTextField manufacturer;
    JComboBox<String> fuelType;

    CreateProductDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setMinimumSize(new java.awt.Dimension(300, 400));
        this.setLayout(new GridLayout(11, 2));

        String[] productTypes = { "Car", "CarVan", "Bus" };
        this.productType = new JComboBox<>(productTypes);
        this.add(new JLabel("Product Type"));
        this.add(this.productType);

        this.vehicleName = new JTextField();
        this.add(new JLabel("Name"));
        this.add(this.vehicleName);

        this.basePrice = new JTextField();
        this.add(new JLabel("Base Price"));
        this.add(this.basePrice);

        this.avilableCount = new JTextField();
        this.add(new JLabel("Avialable Count"));
        this.add(this.avilableCount);

        this.year = new JTextField();
        this.add(new JLabel("Year"));
        this.add(this.year);

        this.modle = new JTextField();
        this.add(new JLabel("Modle"));
        this.add(this.modle);

        this.vehicleId = new JTextField();
        this.add(new JLabel("Vehicle ID"));
        this.add(this.vehicleId);

        this.color = new JTextField();
        this.add(new JLabel("Color"));
        this.add(this.color);

        this.manufacturer = new JTextField();
        this.add(new JLabel("Manufacturer"));
        this.add(this.manufacturer);

        String[] fuelTypes = { "Gasoline91", "Gasoline95", "Diesel" };
        this.fuelType = new JComboBox<>(fuelTypes);
        this.add(new JLabel("Fuel Type"));
        this.add(this.fuelType);

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
