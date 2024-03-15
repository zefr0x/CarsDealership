package carsdealership;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

class SalesManPage extends JPanel {
    MainWindow parent;

    SalesManPage(MainWindow parent) {
        this.parent = parent;

        this.add(new JLabel("SalesMan Page"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        this.add(logoutButton);

    }
}
