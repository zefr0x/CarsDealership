package carsdealership;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

class AdminPage extends JPanel {
    MainWindow parent;

    AdminPage(MainWindow parent) {
        this.parent = parent;

        this.add(new JLabel("Admin Page"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        this.add(logoutButton);

    }
}
