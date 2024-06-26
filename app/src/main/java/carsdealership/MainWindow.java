package carsdealership;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.mindrot.jbcrypt.BCrypt;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.UUID;

public class MainWindow extends JFrame {
    static final String MAIN_PANEL = "MAIN_PANEL";
    static final String ADMIN_MAIN_PANEL = "ADMIN_MAIN_PANEL";
    static final String SALESMAN_MAIN_PANEL = "SALESMAN_MAIN_PANEL";
    static final String COSTUMER_MAIN_PANEL = "COSTUMER_MAIN_PANEL";
    static final String PRODUCTS_BROWSER_PANEL = "PRODUCTS_BROWSER_PANEL";

    java.awt.Container container;
    CardLayout cardLayout;
    JTextPane mainTextBoard;

    String currentUserId;
    String currentUsername;

    MainWindow() {
        super("Cars Dealership");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new java.awt.Dimension(1000, 700));

        container = this.getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        JPanel mainPage = new JPanel();
        mainPage.setLayout(new BorderLayout());
        container.add(mainPage, MAIN_PANEL);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPage.add(headerPanel, BorderLayout.NORTH);

        mainPage.add(new JLabel("", new ImageIcon(getClass().getResource("/logo.png")), JLabel.CENTER));

        mainTextBoard = new JTextPane();
        mainTextBoard.setContentType("text/html");
        mainTextBoard.setPreferredSize(new Dimension(0, 200));
        mainTextBoard.setEditable(false);
        mainTextBoard.setText("""
                        <html>
                            <body>
                                <h><b>Runtime Admin Notes:</b></h>
                                <p>Noting is written yet! (Admins only are able to edit this text)</p>
                            </body>
                        </html>
                """);
        mainPage.add(mainTextBoard, BorderLayout.SOUTH);

        JButton loginButton = new JButton("Login to User Account");
        loginButton.addActionListener(new LoginButtonListener());
        headerPanel.add(loginButton);

        JButton openBrowserButton = new JButton("Browse Available Products");
        openBrowserButton.addActionListener(e -> {
            this.add(new BrowseProductsPage(this), PRODUCTS_BROWSER_PANEL);
            this.cardLayout.show(this.container, PRODUCTS_BROWSER_PANEL);
        });
        headerPanel.add(openBrowserButton);

        // Check if there was any admin account and create initial one.
        try (Database db = new Database()) {
            if (db.adminsCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "There is no admin account in the system, you will be prompet to create a new one.",
                        "No Admin Account", JOptionPane.INFORMATION_MESSAGE);

                CreateAdminAccountDialog dialog = new CreateAdminAccountDialog(this, true);
                dialog.setVisible(true);

                if (!dialog.operationCanceled) {

                    AdminAccount account = new AdminAccount(UUID.randomUUID().toString(),
                            dialog.userNameField.getText(),
                            BCrypt.hashpw(dialog.userPasswordField.getText(), BCrypt.gensalt()),
                            dialog.firstName.getText(),
                            dialog.lastName.getText(), dialog.branch.getText(),
                            Double.parseDouble(dialog.salary.getText()),
                            true);
                    db.createAdminAccount(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "SQLException",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            LoginDialog dialog = new LoginDialog(MainWindow.this, true);
            dialog.setVisible(true);

            MainWindow.this.currentUserId = dialog.userId;
            MainWindow.this.currentUsername = dialog.userNameField.getText();

            if (dialog.getUserType() == UserType.Admin) {
                MainWindow.this.add(new AdminPage(MainWindow.this), ADMIN_MAIN_PANEL);
                MainWindow.this.cardLayout.show(MainWindow.this.container, ADMIN_MAIN_PANEL);
            } else if (dialog.getUserType() == UserType.SalesMan) {
                MainWindow.this.add(new SalesManPage(MainWindow.this), SALESMAN_MAIN_PANEL);
                MainWindow.this.cardLayout.show(MainWindow.this.container, SALESMAN_MAIN_PANEL);
            } else if (dialog.getUserType() == UserType.Costumer) {
                MainWindow.this.add(new CostumerPage(MainWindow.this), COSTUMER_MAIN_PANEL);
                MainWindow.this.cardLayout.show(MainWindow.this.container, COSTUMER_MAIN_PANEL);
            }
        }
    }
}
