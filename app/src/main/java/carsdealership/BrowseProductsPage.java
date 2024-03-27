package carsdealership;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class BrowseProductsPage extends JPanel {
    MainWindow parent;

    BrowseProductsPage(MainWindow parent) {
        this.parent = parent;
        //Layout and title
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel TitleLabel = new JLabel("Products Browser", SwingConstants.CENTER);
        TitleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        //Go back button
        JButton logoutButton = new JButton("Go Back");
        logoutButton.addActionListener(e -> parent.container.remove(this));
        //add back button and title
        topPanel.add(TitleLabel, BorderLayout.CENTER);
        topPanel.add(logoutButton, BorderLayout.WEST);
        // Add the topPanel to the NORTH position of the BorderLayout
        add(topPanel, BorderLayout.NORTH);

        //Search Bar Box
        Box bodyBox = Box.createVerticalBox();
        bodyBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(bodyBox);
        bodyBox.add(Box.createVerticalStrut(10));
        Box searching = Box.createHorizontalBox();
        searching.setAlignmentX(Component.LEFT_ALIGNMENT);
        //image and button
        ImageIcon normal = new ImageIcon(getClass().getResource("/normal.png"));
        ImageIcon pressed = new ImageIcon(getClass().getResource("/pressed.png"));
        JButton button = new JButton(normal);
        button.setPreferredSize(new Dimension(30, 30));
        button.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed()){
                    button.setIcon(pressed);
                }
                else {
                    button.setIcon(normal);
                }
            }
        });
        //searchbar components
        JTextField searchbar = new JTextField();
        searchbar.setMaximumSize(new Dimension(140, 30));
        searchbar.setPreferredSize(new Dimension(140,30));
        JLabel searchlabel = new JLabel("Product Name:");
        searchlabel.setFont(new Font("Serf", Font.PLAIN, 18));
        //adding components to body box
        bodyBox.add(searching);
        searching.add(searchlabel);
        searching.add(searchbar);
        searching.add(button);


        //new box for filter
        bodyBox.add(Box.createVerticalStrut(10));
        Box filter = Box.createHorizontalBox();
        filter.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Vehicle Type Filter
        JLabel product = new JLabel("Vehicle Type: ");
        product.setFont(new Font("Serif", Font.PLAIN, 18));
        JComboBox<String> type = new JComboBox<>(new String[]{"Car", "Caravan", "Bus"});
        type.setMaximumSize(new Dimension(100, 30));
        type.setPreferredSize(new Dimension(100, 30));
        //adding Types
        bodyBox.add(filter);
        filter.add(product);
        filter.add(type);

        //Colour Box
        bodyBox.add(Box.createVerticalStrut(10));
        Box colour = Box.createHorizontalBox();
        colour.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Vehicle Colour
        JLabel colr_label = new JLabel("Colour: ");
        colr_label.setFont(new Font("Serf", Font.PLAIN, 18));
        JComboBox<String> colourList = new JComboBox<>(new String[]{"Black", "Red", "Navy Blue", "Dark Brown", "Silver", "White"});
        colourList.setMaximumSize(new Dimension(100, 30));
        colourList.setPreferredSize(new Dimension(100, 30));
        //adding Colours
        bodyBox.add(colour);
        colour.add(colr_label);
        colour.add(colourList);

        //new box for fuel type
        bodyBox.add(Box.createVerticalStrut(10));
        Box fuel = Box.createHorizontalBox();
        fuel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Fuel types
        JLabel fuelName = new JLabel("Fuel Type: ");
        fuelName.setFont(new Font("Serf", Font.PLAIN, 18));
        JCheckBox gas1 = new JCheckBox("91 ");
        gas1.setFont(new Font("Serf", Font.PLAIN, 14));
        JCheckBox gas2 = new JCheckBox("95 ");
        gas2.setFont(new Font("Serf", Font.PLAIN, 14));
        JCheckBox gas3 = new JCheckBox("Diesel ");
        gas3.setFont(new Font("Serf", Font.PLAIN, 14));
        //add fuels
        bodyBox.add(fuel);
        fuel.add(fuelName);
        fuel.add(gas1);
        fuel.add(gas2);
        fuel.add(gas3);

        //new box for date of production
        bodyBox.add(Box.createVerticalStrut(10));
        Box proDate = Box.createHorizontalBox();
        proDate.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Date of production
        JLabel date = new JLabel("Date of Production: ");
        date.setFont(new Font("Serf", Font.PLAIN, 18));
        JLabel fromDate = new JLabel(" From:");
        fromDate.setFont(new Font("Serf", Font.PLAIN, 14));
        JTextField from = new JTextField();
        from.setMaximumSize(new Dimension(45, 20));
        from.setPreferredSize(new Dimension(45,20));
        JLabel toDate = new JLabel(" To:");
        toDate.setFont(new Font("Serf", Font.PLAIN, 14));
        JTextField to = new JTextField();
        to.setMaximumSize(new Dimension(45, 20));
        to.setPreferredSize(new Dimension(45,20));
        //add textfields
        bodyBox.add(proDate);
        proDate.add(date);
        proDate.add(fromDate);
        proDate.add(from);
        proDate.add(toDate);
        proDate.add(to);

        //new box for manfuccturer type
        bodyBox.add(Box.createVerticalStrut(10));
        Box manuf = Box.createHorizontalBox();
        manuf.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Manufacturer texting
        JLabel manuName = new JLabel("Manufacturer: ");
        manuName.setFont(new Font("Serf", Font.PLAIN, 18));
        JTextField manUp = new JTextField("Look Up Manufacturer maybe its in our data base");
        manUp.setMaximumSize(new Dimension(200, 30));
        //add that box
        bodyBox.add(manuf);
        manuf.add(manuName);
        manuf.add(manUp);

        }
    }


