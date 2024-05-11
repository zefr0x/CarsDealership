package carsdealership;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Pattern;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.TitledBorder;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

class SalesManPage extends JPanel {
    MainWindow parent;

    JLabel salesByMeStat;

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

                    if (!dialog.operationCanceled) {
                        try (Database db = new Database()) {
                            CostomerAccount account = new CostomerAccount(UUID.randomUUID().toString(),
                                    dialog.userNameField.getText(),
                                    BCrypt.hashpw(dialog.userPasswordField.getText(), BCrypt.gensalt()),
                                    dialog.firstName.getText(),
                                    dialog.lastName.getText(), dialog.phoneNumber.getText(),
                                    dialog.emailAddress.getText());
                            db.createCostumerAccount(account);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
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
                    CreateSaleOperationDialog dialog = new CreateSaleOperationDialog(parent, true);
                    dialog.setVisible(true);

                    if (!dialog.operationCanceled) {
                        try (Database db = new Database()) {
                            SaleOperation operation = new SaleOperation(UUID.randomUUID().toString(),
                                    dialog.costumerId.getText(), parent.currentUserId, dialog.productId.getText(),
                                    dialog.paymentMethod.getSelectedItem().toString(), System.currentTimeMillis());
                            db.createSaleOperation(operation);

                            this.updateStatisticsBox();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
        salesManagementBox.add(createNewSell);

        JButton revertSell = new JButton("Revert a Purchase");
        revertSell.addActionListener(
                e -> {
                    RevertSaleOperationDialog dialog = new RevertSaleOperationDialog(parent, true);
                    dialog.setVisible(true);

                    if (!dialog.operationCanceled) {
                        try (Database db = new Database()) {
                            db.deleteSaleOperation(dialog.saleId.getText());

                            this.updateStatisticsBox();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
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
                    CostumersListDialog dialog = new CostumersListDialog(parent, true);
                    dialog.setVisible(true);
                });
        searchManagementBox.add(costumersList);

        JButton salesList = new JButton("Sales List Dialog");
        salesList.addActionListener(
                e -> {
                    SaleOperationsListDialog dialog = new SaleOperationsListDialog(parent, true);
                    dialog.setVisible(true);
                });
        searchManagementBox.add(salesList);

        // Statistics section
        bodyBox.add(Box.createVerticalStrut(20));
        Box myStatisticsBox = Box.createHorizontalBox();
        myStatisticsBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        myStatisticsBox.setBorder(BorderFactory.createTitledBorder("My Statistics"));
        bodyBox.add(myStatisticsBox);

        salesByMeStat = new JLabel("Sales by Me: 0");
        myStatisticsBox.add(salesByMeStat);
        myStatisticsBox.add(Box.createRigidArea(new Dimension(20, 0)));
        this.updateStatisticsBox();

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
                    try (Database db = new Database()) {
                        db.deleteUserAccount(parent.currentUserId);
                        parent.container.remove(this);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
        footerPanel.add(deleteAccount);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        footerPanel.add(logoutButton);
    }

    void updateStatisticsBox() {
        try (Database db = new Database()) {
            salesByMeStat.setText("Sales by Me: " + db.getSalesCountBySalesManId(parent.currentUserId));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

class CreateCostumerAccountDialog extends JDialog {
    JTextField userNameField;
    JPasswordField userPasswordField;
    JTextField firstName;
    JTextField lastName;
    JTextField phoneNumber;
    JTextField emailAddress;

    boolean operationCanceled = false;

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
        cancelButton.addActionListener(e -> {
            this.setVisible(false);
            this.operationCanceled = true;
        });
        this.add(cancelButton);
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            final String phoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
            final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            Pattern phonePat = Pattern.compile(phoneRegex);
            Pattern emailPat = Pattern.compile(emailRegex);

            if (phonePat.matcher(CreateCostumerAccountDialog.this.phoneNumber.getText()).matches()) {
                if (emailPat.matcher(CreateCostumerAccountDialog.this.emailAddress.getText()).matches()) {
                    CreateCostumerAccountDialog.this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Email Address is invalid.", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Phone Number is invalid.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    // TODO: Implement getters and setters.
}

class CreateSaleOperationDialog extends JDialog {
    JTextField costumerId;
    JTextField productId;
    JComboBox<String> paymentMethod;

    boolean operationCanceled = true;

    CreateSaleOperationDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Create New Sale Operation");
        this.setMinimumSize(new java.awt.Dimension(400, 250));
        this.setLayout(new GridLayout(4, 2));

        this.costumerId = new JTextField();
        this.add(new JLabel("Costumer ID"));
        this.add(this.costumerId);

        this.productId = new JTextField();
        this.add(new JLabel("Product ID"));
        this.add(this.productId);

        String[] paymentMethods = { "Mada", "Cache", "Visa" };
        this.paymentMethod = new JComboBox<>(paymentMethods);
        this.add(new JLabel("Payment Method"));
        this.add(this.paymentMethod);

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
            try (Database db = new Database()) {
                if (db.isCostumerExist(CreateSaleOperationDialog.this.costumerId.getText())) {
                    if (db.isProductExist(CreateSaleOperationDialog.this.productId.getText())) {
                        CreateSaleOperationDialog.this.setVisible(false);
                        CreateSaleOperationDialog.this.operationCanceled = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Product ID doesn't exist in the system.", "Invalid Input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Costumer ID doesn't exist in the system.", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // TODO: Implement getters and setters.
}

class RevertSaleOperationDialog extends JDialog {
    JTextField saleId;

    boolean operationCanceled = true;

    RevertSaleOperationDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Revert Sale Operation");
        this.setMinimumSize(new java.awt.Dimension(400, 100));
        this.setLayout(new GridLayout(2, 2));

        this.saleId = new JTextField();
        this.add(new JLabel("Operation ID"));
        this.add(this.saleId);

        JButton createButton = new JButton("Revert");
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
            try (Database db = new Database()) {
                if (db.isSaleOperationExist(RevertSaleOperationDialog.this.saleId.getText())) {
                    RevertSaleOperationDialog.this.setVisible(false);
                    RevertSaleOperationDialog.this.operationCanceled = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Sale Operation ID doesn't exist in the system.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // TODO: Implement getters and setters.
}
