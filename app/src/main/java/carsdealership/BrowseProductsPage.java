package carsdealership;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

class BrowseProductsPage extends JPanel {
    MainWindow parent;

    BrowseProductsPage(MainWindow parent) {
        this.parent = parent;

        this.add(new JLabel("Products Browser"));

        JButton logoutButton = new JButton("Go Back");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        this.add(logoutButton);

    }
}
