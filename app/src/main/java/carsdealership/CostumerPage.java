package carsdealership;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

class CostumerPage extends JPanel {
    MainWindow parent;

    CostumerPage(MainWindow parent) {
        this.parent = parent;

        this.add(new JLabel("Costumer Page"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                e -> parent.container.remove(this));
        this.add(logoutButton);

    }
}
