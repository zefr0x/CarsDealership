package carsdealership;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
        Box usersManagementBox = Box.createHorizontalBox();
        usersManagementBox.setBorder(BorderFactory.createTitledBorder("System Users Management"));
        usersManagementBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(usersManagementBox);

        JButton newManagerButton = new JButton("Add New Admin");
        newManagerButton.addActionListener(
                e -> {
                    CreateAdminAccountDialog dialog = new CreateAdminAccountDialog(parent, true);
                    dialog.setVisible(true);

                    if (!dialog.operationCanceled) {
                        try {
                            Database db = new Database();

                            AdminAccount account = new AdminAccount(UUID.randomUUID().toString(),
                                    dialog.userNameField.getText(),
                                    BCrypt.hashpw(dialog.userPasswordField.getText(), BCrypt.gensalt()),
                                    dialog.firstName.getText(),
                                    dialog.lastName.getText(), dialog.branch.getText(),
                                    Double.parseDouble(dialog.salary.getText()),
                                    false);
                            db.createAdminAccount(account);

                            db.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
        usersManagementBox.add(newManagerButton);

        JButton newSellerButton = new JButton("Add New SalesMan Account");
        newSellerButton.addActionListener(e -> {
            CreateSalesManAccountDialog dialog = new CreateSalesManAccountDialog(parent, true);
            dialog.setVisible(true);

            if (!dialog.operationCanceled) {
                try {
                    Database db = new Database();

                    SalesManAccount account = new SalesManAccount(UUID.randomUUID().toString(),
                            dialog.userNameField.getText(),
                            BCrypt.hashpw(dialog.userPasswordField.getText(), BCrypt.gensalt()),
                            dialog.firstName.getText(),
                            dialog.lastName.getText(), dialog.branch.getText(),
                            Double.parseDouble(dialog.salary.getText()));
                    db.createSalesManAccount(account);

                    db.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        usersManagementBox.add(newSellerButton);

        // Products managment section
        bodyBox.add(Box.createVerticalStrut(20));
        Box productsManagementBox = Box
                .createHorizontalBox();
        productsManagementBox.setBorder(BorderFactory.createTitledBorder("Products Management"));
        productsManagementBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(productsManagementBox);

        JButton newItemButton = new JButton("Create New Product");
        newItemButton.addActionListener(e -> {
            CreateProductDialog dialog = new CreateProductDialog(parent, true);
            dialog.setVisible(true);

            if (!dialog.operationCanceled) {
                try {
                    Database db = new Database();

                    if (dialog.productType.getSelectedItem().toString().equals("Car")) {
                        Car product = new Car(UUID.randomUUID().toString(), dialog.vehicleName.getText(),
                                Double.parseDouble(dialog.basePrice.getText()),
                                Integer.parseInt(dialog.avilableCount.getText()),
                                Integer.parseInt(dialog.year.getText()),
                                dialog.modle.getText(), dialog.vehicleId.getText(), dialog.color.getText(),
                                dialog.manufacturer.getText(), dialog.fuelType.getSelectedItem().toString(),
                                dialog.hasSencsors.isSelected(),
                                dialog.hasCameras.isSelected());

                        db.createCar(product);
                    } else if (dialog.productType.getSelectedItem().toString().equals("Carvan")) {
                        Carvan product = new Carvan(UUID.randomUUID().toString(), dialog.vehicleName.getText(),
                                Double.parseDouble(dialog.basePrice.getText()),
                                Integer.parseInt(dialog.avilableCount.getText()),
                                Integer.parseInt(dialog.year.getText()),
                                dialog.modle.getText(), dialog.vehicleId.getText(), dialog.color.getText(),
                                dialog.manufacturer.getText(), Integer.parseInt(dialog.numberOfRooms.getText()),
                                dialog.hasBathroom.isSelected(), dialog.fuelType.getSelectedItem().toString());

                        db.createCarvan(product);
                    } else if (dialog.productType.getSelectedItem().toString().equals("Bus")) {
                        Bus product = new Bus(UUID.randomUUID().toString(), dialog.vehicleName.getText(),
                                Double.parseDouble(dialog.basePrice.getText()),
                                Integer.parseInt(dialog.avilableCount.getText()),
                                Integer.parseInt(dialog.year.getText()),
                                dialog.modle.getText(), dialog.vehicleId.getText(), dialog.color.getText(),
                                dialog.manufacturer.getText(), Integer.parseInt(dialog.passengerCapacity.getText()),
                                dialog.isDoubleDecker.isSelected(), dialog.fuelType.getSelectedItem().toString());

                        db.createBus(product);
                    }

                    db.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        productsManagementBox.add(newItemButton);

        JButton updateItemButton = new JButton("Update Product Quantity");
        updateItemButton.addActionListener(e -> {
            UpdateProductQuentityDialog dialog = new UpdateProductQuentityDialog(parent, true);
            dialog.setVisible(true);
        });
        productsManagementBox.add(updateItemButton);

        JButton addDiscountButton = new JButton("Apply Discount to Product");
        addDiscountButton.addActionListener(e -> {
            ProductDiscountDialog dialog = new ProductDiscountDialog(parent, true);
            dialog.setVisible(true);
        });
        productsManagementBox.add(addDiscountButton);

        JButton exportProducts = new JButton("Export All Products To File");
        exportProducts.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify distnation file to save to");

            int userSelection = fileChooser.showSaveDialog(parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try (FileOutputStream fos = new FileOutputStream(fileToSave);
                        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    try {
                        Database db = new Database();

                        for (Car car : db.getCarsList()) {
                            oos.writeObject(car);
                        }
                        for (Carvan carvan : db.getCarvansList()) {
                            oos.writeObject(carvan);
                        }
                        for (Bus bus : db.getBussList()) {
                            oos.writeObject(bus);
                        }

                        db.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(null, "File saved successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error saving file",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        productsManagementBox.add(exportProducts);

        JButton importProducts = new JButton("Import Products From File");
        importProducts.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify file to open and read");

            int userSelection = fileChooser.showOpenDialog(parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                int newProductsCount = 0;
                int doublecatedProductsCount = 0;

                try (FileInputStream fos = new FileInputStream(fileToSave);
                        ObjectInputStream oos = new ObjectInputStream(fos)) {
                    List<Product> inputList = new ArrayList<>();

                    try {
                        while (true) {
                            inputList.add((Product) oos.readObject());
                        }
                    } catch (EOFException ex) {
                        if (inputList.size() > 0) {
                            try (Database db = new Database()) {
                                for (Product product : inputList) {
                                    if (db.idProductExist(product.getId())) {
                                        doublecatedProductsCount++;
                                    } else {
                                        newProductsCount++;

                                        if (product instanceof Car) {
                                            db.createCar((Car) product);
                                        } else if (product instanceof Carvan) {
                                            db.createCarvan((Carvan) product);
                                        } else if (product instanceof Bus) {
                                            db.createBus((Bus) product);
                                        }
                                    }
                                }
                            } catch (SQLException exp) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, exp.getMessage(), "SQLException",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        JOptionPane.showMessageDialog(null,
                                "File read successfully. " + newProductsCount + " new products were added, and "
                                        + doublecatedProductsCount + " doublecated products skiped.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid objec type. Terminating.",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error reading from file. Terminating.",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error reading file",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        productsManagementBox.add(importProducts);

        // Reports section
        bodyBox.add(Box.createVerticalStrut(20));
        Box reportsBox = Box
                .createHorizontalBox();
        reportsBox.setBorder(BorderFactory.createTitledBorder("System Reports"));
        reportsBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyBox.add(reportsBox);

        JButton salesReportButton = new JButton("Sales Report");
        salesReportButton.addActionListener(e -> {
            SalesReportDialog dialog = new SalesReportDialog(parent, true);
            dialog.setVisible(true);
        });
        reportsBox.add(salesReportButton);

        JButton goodsReportButton = new JButton("Products Report");
        goodsReportButton.addActionListener(e -> {
            ProductsReportDialog dialog = new ProductsReportDialog(parent, true);
            dialog.setVisible(true);
        });
        reportsBox.add(goodsReportButton);

        JButton usersReportButton = new JButton("System Users Report");
        usersReportButton.addActionListener(e -> {
            UsersReportDialog dialog = new UsersReportDialog(parent, true);
            dialog.setVisible(true);
        });
        reportsBox.add(usersReportButton);

        JButton changePasswordButton = new JButton("Change My Password");
        changePasswordButton.addActionListener(e -> {
            ChangePasswordDialog dialog = new ChangePasswordDialog(parent, true);
            dialog.setVisible(true);
        });
        footerPanel.add(changePasswordButton);

        JButton logoutButton = new JButton(
                "Logout");
        logoutButton.addActionListener(e -> parent.container.remove(this));
        footerPanel.add(logoutButton);
    }
}

class CreateAdminAccountDialog extends JDialog {
    JTextField userNameField;
    JPasswordField userPasswordField;
    JTextField firstName;
    JTextField lastName;
    JTextField salary;
    JTextField branch;

    boolean operationCanceled = true;

    CreateAdminAccountDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Create New Admin");
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

        this.salary = new JTextField();
        this.add(new JLabel("Salary"));
        this.add(this.salary);

        this.branch = new JTextField();
        this.add(new JLabel("Branch"));
        this.add(this.branch);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new CreateButtonListener());
        this.add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            this.setVisible(false);
        });
        this.add(cancelButton);
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // TODO: Validate data.
            CreateAdminAccountDialog.this.setVisible(false);
            CreateAdminAccountDialog.this.operationCanceled = false;
        }
    }

    // TODO: Implement getters and setters.
}

class CreateSalesManAccountDialog extends JDialog {
    JTextField userNameField;
    JPasswordField userPasswordField;
    JTextField firstName;
    JTextField lastName;
    JTextField branch;
    JTextField salary;

    boolean operationCanceled = true;

    CreateSalesManAccountDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Create new SalesMan");
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

        this.salary = new JTextField();
        this.add(new JLabel("Salary"));
        this.add(this.salary);

        this.branch = new JTextField();
        this.add(new JLabel("Branch"));
        this.add(this.branch);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new CreateButtonListener());
        this.add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            this.setVisible(false);
        });
        this.add(cancelButton);
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // TODO: Validate data.
            CreateSalesManAccountDialog.this.setVisible(false);
            CreateSalesManAccountDialog.this.operationCanceled = false;
        }
    }
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

    JCheckBox hasSencsors;
    JCheckBox hasCameras;

    JTextField passengerCapacity;
    JCheckBox isDoubleDecker;

    JCheckBox hasBathroom;
    JTextField numberOfRooms;

    boolean operationCanceled = true;

    CreateProductDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Add New Product");
        this.setMinimumSize(new java.awt.Dimension(300, 500));
        this.setLayout(new GridLayout(17, 2));

        String[] productTypes = { "Car", "Carvan", "Bus" };
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
        this.add(new JLabel("Available Count"));
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

        hasSencsors = new JCheckBox();
        this.add(new JLabel("Has Sencsors"));
        this.add(this.hasSencsors);

        hasCameras = new JCheckBox();
        this.add(new JLabel("Has Cameras"));
        this.add(this.hasCameras);

        passengerCapacity = new JTextField();
        this.add(new JLabel("Passenger Capacity"));
        this.add(this.passengerCapacity);

        isDoubleDecker = new JCheckBox();
        this.add(new JLabel("Is Double Decker"));
        this.add(this.isDoubleDecker);

        hasBathroom = new JCheckBox();
        this.add(new JLabel("Has Bathroom"));
        this.add(this.hasBathroom);

        numberOfRooms = new JTextField();
        this.add(new JLabel("Number Of Rooms"));
        this.add(this.numberOfRooms);

        // Initialize with Car product type.
        CreateProductDialog.this.passengerCapacity.setEnabled(false);
        CreateProductDialog.this.isDoubleDecker.setEnabled(false);
        CreateProductDialog.this.hasBathroom.setEnabled(false);
        CreateProductDialog.this.numberOfRooms.setEnabled(false);
        // Disable and enable inputs depending on the product type.
        productType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();

                    if (selectedItem.equals("Car")) {
                        CreateProductDialog.this.hasSencsors.setEnabled(true);
                        CreateProductDialog.this.hasCameras.setEnabled(true);
                        CreateProductDialog.this.passengerCapacity.setEnabled(false);
                        CreateProductDialog.this.isDoubleDecker.setEnabled(false);
                        CreateProductDialog.this.hasBathroom.setEnabled(false);
                        CreateProductDialog.this.numberOfRooms.setEnabled(false);
                    } else if (selectedItem.equals("Carvan")) {
                        CreateProductDialog.this.hasSencsors.setEnabled(false);
                        CreateProductDialog.this.hasCameras.setEnabled(false);
                        CreateProductDialog.this.passengerCapacity.setEnabled(false);
                        CreateProductDialog.this.isDoubleDecker.setEnabled(false);
                        CreateProductDialog.this.hasBathroom.setEnabled(true);
                        CreateProductDialog.this.numberOfRooms.setEnabled(true);
                    } else if (selectedItem.equals("Bus")) {
                        CreateProductDialog.this.hasSencsors.setEnabled(false);
                        CreateProductDialog.this.hasCameras.setEnabled(false);
                        CreateProductDialog.this.passengerCapacity.setEnabled(true);
                        CreateProductDialog.this.isDoubleDecker.setEnabled(true);
                        CreateProductDialog.this.hasBathroom.setEnabled(false);
                        CreateProductDialog.this.numberOfRooms.setEnabled(false);
                    }
                }
            }
        });

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new CreateButtonListener());
        this.add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            this.setVisible(false);
        });
        this.add(cancelButton);
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // TODO: Validate data.
            CreateProductDialog.this.setVisible(false);
            CreateProductDialog.this.operationCanceled = false;
        }
    }

    // TODO: Implement getters and setters.
}

class UpdateProductQuentityDialog extends JDialog {
    JTextField productId;
    JTextField newQuentity;

    UpdateProductQuentityDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Update Product Quentity");
        this.setMinimumSize(new java.awt.Dimension(400, 100));
        this.setLayout(new GridLayout(3, 2));

        this.productId = new JTextField();
        this.add(new JLabel("Product ID"));
        this.add(this.productId);

        this.newQuentity = new JTextField();
        this.add(new JLabel("New Quentity"));
        this.add(this.newQuentity);

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

                int oldCount = db.getProductAvailableCount(UpdateProductQuentityDialog.this.productId.getText());

                if (db.idProductExist(UpdateProductQuentityDialog.this.productId.getText())) {
                    try {
                        db.setProductAvailableCount(UpdateProductQuentityDialog.this.productId.getText(),
                                Integer.parseInt(UpdateProductQuentityDialog.this.newQuentity.getText()));
                        JOptionPane.showMessageDialog(null,
                                "Product available count updated from " + oldCount + " to "
                                        + db.getProductAvailableCount(
                                                UpdateProductQuentityDialog.this.productId.getText()),
                                "Product Count Updated",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Product count should be integer number.",
                                "Error Not Integer",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Product ID doesn't exist.", "Error Product ID",
                            JOptionPane.ERROR_MESSAGE);
                }

                db.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class ProductDiscountDialog extends JDialog {
    JTextField productId;
    JTextField discountPercentage;

    ProductDiscountDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Product Discount");
        this.setMinimumSize(new java.awt.Dimension(400, 100));
        this.setLayout(new GridLayout(3, 2));

        this.productId = new JTextField();
        this.add(new JLabel("Product ID"));
        this.add(this.productId);

        this.discountPercentage = new JTextField();
        this.add(new JLabel("Discount percentage %"));
        this.add(this.discountPercentage);

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

                double oldPrice = db.getProductPrice(ProductDiscountDialog.this.productId.getText());

                if (db.idProductExist(ProductDiscountDialog.this.productId.getText())) {
                    try {
                        db.applyProductDiscount(ProductDiscountDialog.this.productId.getText(),
                                Double.parseDouble(ProductDiscountDialog.this.discountPercentage.getText()));
                        JOptionPane.showMessageDialog(null,
                                "Product price updated from " + oldPrice + " to "
                                        + db.getProductPrice(
                                                ProductDiscountDialog.this.productId.getText()),
                                "Product Count Updated",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Discount should be double number.",
                                "Error Not Integer",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Product ID doesn't exist.", "Error Product ID",
                            JOptionPane.ERROR_MESSAGE);
                }

                db.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class SalesReportDialog extends JDialog {
    JTextField salesCount;
    JTextField topPaymentMethod;
    JFrame parent;

    SalesReportDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        this.setTitle("Sales Report");
        this.setMinimumSize(new java.awt.Dimension(350, 100));
        this.setLayout(new GridLayout(3, 2));

        this.salesCount = new JTextField();
        this.salesCount.setEditable(false);
        this.add(new JLabel("Total Sale Operations Count"));
        this.add(this.salesCount);

        this.topPaymentMethod = new JTextField();
        this.topPaymentMethod.setEditable(false);
        this.add(new JLabel("Top Payment Method"));
        this.add(this.topPaymentMethod);

        try {
            Database db = new Database();

            Integer salesCount = db.getSalesCount();

            this.salesCount.setText(salesCount.toString());
            if (salesCount > 0)
                topPaymentMethod.setText(db.getTopPaymentMethod());

            db.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }

        JButton saveToFile = new JButton("Save To File");
        saveToFile.addActionListener(new SaveButtonListener());
        this.add(saveToFile);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            this.setVisible(false);
        });
        this.add(closeButton);
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify distnation file to save to");

            int userSelection = fileChooser.showSaveDialog(SalesReportDialog.this.parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try {
                    FileWriter writer = new FileWriter(fileToSave);
                    writer.write("Total Sales Operations Count: " + SalesReportDialog.this.salesCount.getText()
                            + "\nTop Payment Method Used: " + SalesReportDialog.this.topPaymentMethod.getText());
                    writer.close();
                    JOptionPane.showMessageDialog(null, "File saved successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error saving file",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}

class ProductsReportDialog extends JDialog {
    JTextField productsCount;
    JTextField totalProductsPiecesCount;

    JFrame parent;

    ProductsReportDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        this.setTitle("Products Report");
        this.setMinimumSize(new java.awt.Dimension(350, 100));
        this.setLayout(new GridLayout(3, 2));

        this.productsCount = new JTextField();
        this.productsCount.setEditable(false);
        this.add(new JLabel("Products Count"));
        this.add(this.productsCount);

        this.totalProductsPiecesCount = new JTextField();
        this.totalProductsPiecesCount.setEditable(false);
        this.add(new JLabel("Total Products Pieces Count"));
        this.add(this.totalProductsPiecesCount);

        try {
            Database db = new Database();

            productsCount.setText(db.getProductsCount().toString());
            totalProductsPiecesCount.setText(db.getTotalProductsPiecesCount().toString());

            db.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }

        JButton saveToFile = new JButton("Save To File");
        saveToFile.addActionListener(new SaveButtonListener());
        this.add(saveToFile);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            this.setVisible(false);
        });
        this.add(closeButton);
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify distnation file to save to");

            int userSelection = fileChooser.showSaveDialog(ProductsReportDialog.this.parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try {
                    FileWriter writer = new FileWriter(fileToSave);
                    writer.write("Products Count: " + ProductsReportDialog.this.productsCount.getText()
                            + "\nTotal Products Pieces Count: "
                            + ProductsReportDialog.this.totalProductsPiecesCount.getText());
                    writer.close();
                    JOptionPane.showMessageDialog(null, "File saved successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error saving file",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}

class UsersReportDialog extends JDialog {
    JTextField totalUsersCount;
    JTextField costumersCount;
    JTextField adminsCount;
    JTextField salesMenCount;

    JFrame parent;

    UsersReportDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        this.setTitle("Users Report");
        this.setMinimumSize(new java.awt.Dimension(340, 300));
        this.setLayout(new GridLayout(5, 2));

        this.totalUsersCount = new JTextField();
        this.totalUsersCount.setEditable(false);
        this.add(new JLabel("Total Users Count"));
        this.add(this.totalUsersCount);

        this.costumersCount = new JTextField();
        this.costumersCount.setEditable(false);
        this.add(new JLabel("Costumers Count"));
        this.add(this.costumersCount);

        this.adminsCount = new JTextField();
        this.adminsCount.setEditable(false);
        this.add(new JLabel("Admins Count"));
        this.add(this.adminsCount);

        this.salesMenCount = new JTextField();
        this.salesMenCount.setEditable(false);
        this.add(new JLabel("SalesMen Count"));
        this.add(this.salesMenCount);

        try {
            Database db = new Database();

            totalUsersCount.setText(db.getTotalUsersCount().toString());
            costumersCount.setText(db.getCostumersCount().toString());
            adminsCount.setText(db.getAdminsCount().toString());
            salesMenCount.setText(db.getSalesMenCount().toString());

            db.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }

        JButton saveToFile = new JButton("Save To File");
        saveToFile.addActionListener(new SaveButtonListener());
        this.add(saveToFile);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            this.setVisible(false);
        });
        this.add(closeButton);
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify distnation file to save to");

            int userSelection = fileChooser.showSaveDialog(UsersReportDialog.this.parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try {
                    FileWriter writer = new FileWriter(fileToSave);
                    writer.write(
                            "Total Users Count: " + UsersReportDialog.this.totalUsersCount.getText()
                                    + "\nCostumers Count: " + UsersReportDialog.this.costumersCount.getText()
                                    + "\nAdmins Count: " + UsersReportDialog.this.adminsCount.getText()
                                    + "\nSalesMen Count: " + UsersReportDialog.this.salesMenCount.getText());
                    writer.close();
                    JOptionPane.showMessageDialog(null, "File saved successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error saving file",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
