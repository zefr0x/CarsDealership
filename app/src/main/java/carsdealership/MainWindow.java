package carsdealership;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    static final String MAIN_PANEL = "MAIN_PANEL";
    static final String ADMIN_MAIN_PANEL = "ADMIN_MAIN_PANEL";
    static final String SALESMAN_MAIN_PANEL = "SALESMAN_MAIN_PANEL";
    static final String COSTUMER_MAIN_PANEL = "COSTUMER_MAIN_PANEL";
    static final String PRODUCTS_BROWSER_PANEL = "PRODUCTS_BROWSER_PANEL";

    java.awt.Container container;
    CardLayout cardLayout;

    MainWindow() {
        super("Cars Dealership");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new java.awt.Dimension(700, 500));

        container = this.getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        JPanel mainPage = new JPanel();
        container.add(mainPage, MAIN_PANEL);

        JButton loginButton = new JButton("Login to User Account");
        loginButton.addActionListener(new LoginButtonListener());
        mainPage.add(loginButton);

        JButton openBrowserButton = new JButton("Browse Avialable Products");
        openBrowserButton.addActionListener(e -> {
            this.add(new BrowseProductsPage(this), PRODUCTS_BROWSER_PANEL);
            this.cardLayout.show(this.container, PRODUCTS_BROWSER_PANEL);
        });
        mainPage.add(openBrowserButton);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            LoginDialog dialog = new LoginDialog(MainWindow.this, true);
            dialog.setVisible(true);

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
