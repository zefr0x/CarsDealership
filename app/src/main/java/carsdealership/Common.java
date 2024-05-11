package carsdealership;

import java.util.Date;
import java.util.List;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.Box;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Common {
    public static String getSaleDateString(long unixTimeStamp) {
        return new Date(unixTimeStamp).toString();
    }

}

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
            try (Database db = new Database()) {
                db.updatePassword(ChangePasswordDialog.this.userId,
                        BCrypt.hashpw(ChangePasswordDialog.this.newPassword.getText(), BCrypt.gensalt()));
                JOptionPane.showMessageDialog(null, "Your password was updated!", "Password Updated!",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class CostumersListDialog extends JDialog {
    CostumersListDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Costumers List");
        this.setMinimumSize(new java.awt.Dimension(800, 250));

        Box mainBox = Box.createVerticalBox();
        this.add(mainBox);

        try (Database db = new Database()) {
            List<CostomerAccount> list = db.getCostumersList();

            // Create table
            String[] columnNames = { "Id", "Username", "FirstName", "LastName", "PhoneNumber", "EmailAddress" };
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Disallow editing for all cells
                    return false;
                }
            };
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            mainBox.add(scrollPane);

            // Insert values
            for (CostomerAccount costumer : list) {
                JPanel panel = new JPanel(new GridLayout(1, 6));
                mainBox.add(panel);

                tableModel.addRow(new Object[] { costumer.getId(), costumer.getUserName(), costumer.getFirstName(),
                        costumer.getLastName(), costumer.getPhoneNumber(), costumer.getEmailAddress() });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Footer layout
        Box footerBox = Box.createHorizontalBox();
        mainBox.add(footerBox);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            this.setVisible(false);
        });
        footerBox.add(closeButton);
    }
}

class SaleOperationsListDialog extends JDialog {
    SaleOperationsListDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Sales Operations List");
        this.setMinimumSize(new java.awt.Dimension(800, 250));

        Box mainBox = Box.createVerticalBox();
        this.add(mainBox);

        try (Database db = new Database()) {
            List<SaleOperation> list = db.getSaleOperationsList();

            // Create table
            String[] columnNames = { "Id", "costumerId", "salesmanId", "paymentMethod", "time" };
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Disallow editing for all cells
                    return false;
                }
            };
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            mainBox.add(scrollPane);

            // Insert values
            for (SaleOperation saleOperation : list) {
                JPanel panel = new JPanel(new GridLayout(1, 6));
                mainBox.add(panel);

                tableModel.addRow(new Object[] { saleOperation.getId(), saleOperation.getCostomerId(),
                        saleOperation.getSalesManId(), saleOperation.getPaymentMethod(),
                        Common.getSaleDateString(saleOperation.getSaleTime()) });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Footer layout
        Box footerBox = Box.createHorizontalBox();
        mainBox.add(footerBox);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            this.setVisible(false);
        });
        footerBox.add(closeButton);
    }
}

class PreviousPurchasesListDialog extends JDialog {
    PreviousPurchasesListDialog(final JFrame parent, boolean modal, String costumerId) {
        super(parent, modal);
        this.setTitle("Sales Operations List");
        this.setMinimumSize(new java.awt.Dimension(800, 250));

        Box mainBox = Box.createVerticalBox();
        this.add(mainBox);

        try (Database db = new Database()) {
            List<SaleOperation> list = db.getPreviousPurchasesList(costumerId);

            // Create table
            String[] columnNames = { "Id", "costumerId", "salesmanId", "paymentMethod", "time" };
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Disallow editing for all cells
                    return false;
                }
            };
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            mainBox.add(scrollPane);

            // Insert values
            for (SaleOperation saleOperation : list) {
                JPanel panel = new JPanel(new GridLayout(1, 6));
                mainBox.add(panel);

                tableModel.addRow(new Object[] { saleOperation.getId(), saleOperation.getCostomerId(),
                        saleOperation.getSalesManId(), saleOperation.getPaymentMethod(),
                        Common.getSaleDateString(saleOperation.getSaleTime()) });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Footer layout
        Box footerBox = Box.createHorizontalBox();
        mainBox.add(footerBox);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            this.setVisible(false);
        });
        footerBox.add(closeButton);
    }
}
