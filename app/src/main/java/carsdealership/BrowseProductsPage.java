package carsdealership;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

class BrowseProductsPage extends JPanel {
    MainWindow parent;

    JTextField nameField;
    JTextField manufacturerField;
    JComboBox<String> vehicleType;
    JCheckBox fuelGasoline91;
    JCheckBox fuelGasoline95;
    JCheckBox fuelDiesel;
    Box resultBox;

    BrowseProductsPage(MainWindow parent) {
        this.parent = parent;

        // Layout and title
        this.setLayout(new BorderLayout());
        JLabel TitleLabel = new JLabel("Products Browser", SwingConstants.CENTER);
        TitleLabel.setFont(new Font("Serif", Font.PLAIN, 30));

        // Go back button
        JButton logoutButton = new JButton("Go Back");
        logoutButton.addActionListener(e -> parent.container.remove(this));

        // add back button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(TitleLabel, BorderLayout.CENTER);
        topPanel.add(logoutButton, BorderLayout.WEST);

        // Add the topPanel to the NORTH position of the BorderLayout
        add(topPanel, BorderLayout.NORTH);

        // Search Bar Box
        Box bodyBox = Box.createVerticalBox();
        bodyBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(bodyBox);

        Box searching = Box.createHorizontalBox();
        bodyBox.add(searching);

        // searchbar components
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(140, 30));
        nameField.setPreferredSize(new Dimension(140, 30));
        JLabel searchlabel = new JLabel("Product Name:");
        searchlabel.setFont(new Font("Serf", Font.PLAIN, 18));

        // Manufacturer texting
        manufacturerField = new JTextField();
        manufacturerField.setMaximumSize(new Dimension(140, 30));
        manufacturerField.setPreferredSize(new Dimension(140, 30));
        JLabel manuName = new JLabel("Manufacturer: ");
        manuName.setFont(new Font("Serf", Font.PLAIN, 18));
        // add that box

        // adding components to body box
        searching.add(searchlabel);
        searching.add(nameField);
        searching.add(manuName);
        searching.add(manufacturerField);

        // image and button
        ImageIcon normal = new ImageIcon(getClass().getResource("/search.png"));
        ImageIcon pressed = new ImageIcon(getClass().getResource("/mungus.png"));
        JButton button = new JButton(normal);
        button.setPreferredSize(new Dimension(30, 30));
        button.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed()) {
                    button.setIcon(pressed);
                } else {
                    button.setIcon(normal);
                }
            }
        });
        button.addActionListener(new SearchButtonListener());
        searching.add(button);

        // new box for filter
        Box filter = Box.createHorizontalBox();
        // Vehicle Type Filter
        JLabel product = new JLabel("Vehicle Type: ");
        product.setFont(new Font("Serif", Font.PLAIN, 18));
        vehicleType = new JComboBox<>(new String[] { "car", "carvan", "bus" });
        vehicleType.setMaximumSize(new Dimension(100, 30));
        vehicleType.setPreferredSize(new Dimension(100, 30));
        // adding Types
        bodyBox.add(filter);
        filter.add(product);
        filter.add(vehicleType);

        // Fuel types
        JLabel fuelName = new JLabel("Fuel Type: ");
        fuelName.setFont(new Font("Serf", Font.PLAIN, 18));
        fuelGasoline91 = new JCheckBox("Gasoline91 ");
        fuelGasoline91.setFont(new Font("Serf", Font.PLAIN, 14));
        fuelGasoline91.setSelected(true);
        fuelGasoline95 = new JCheckBox("Gasoline95 ");
        fuelGasoline95.setFont(new Font("Serf", Font.PLAIN, 14));
        fuelGasoline95.setSelected(true);
        fuelDiesel = new JCheckBox("Diesel ");
        fuelDiesel.setFont(new Font("Serf", Font.PLAIN, 14));
        fuelDiesel.setSelected(true);
        // add fuels
        filter.add(fuelName);
        filter.add(fuelGasoline91);
        filter.add(fuelGasoline95);
        filter.add(fuelDiesel);

        this.resultBox = Box.createVerticalBox();
        bodyBox.add(this.resultBox);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try (Database db = new Database()) {

                // Create table
                DefaultTableModel tableModel;

                String vehicleType = BrowseProductsPage.this.vehicleType.getSelectedItem().toString();
                if (vehicleType.equals("car")) {
                    String[] columnNames = { "Id", "Name", "Manufacturer", "Model", "Year", "VID", "Fuel Type",
                            "Has Sencsors", "Has Cameras", "Base Price", "Available Count" };
                    tableModel = new DefaultTableModel(columnNames, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            // Disallow editing for all cells
                            return false;
                        }
                    };
                    JTable table = new JTable(tableModel);
                    JScrollPane scrollPane = new JScrollPane(table);

                    BrowseProductsPage.this.resultBox.removeAll();
                    BrowseProductsPage.this.resultBox.add(scrollPane);
                    BrowseProductsPage.this.revalidate();

                    List<Car> list = db.searchCars(BrowseProductsPage.this.nameField.getText(),
                            BrowseProductsPage.this.fuelGasoline91.isSelected(),
                            BrowseProductsPage.this.fuelGasoline95.isSelected(),
                            BrowseProductsPage.this.fuelDiesel.isSelected(),
                            BrowseProductsPage.this.manufacturerField.getText());

                    // Insert values
                    for (Car car : list) {
                        JPanel panel = new JPanel(new GridLayout(1, 6));
                        resultBox.add(panel);

                        tableModel.addRow(
                                new Object[] { car.getId(), car.getProductName(), car.getManufacturer(), car.getModel(),
                                        car.getYear(), car.getVehicleIdentificationNumber(), car.getFuelType(),
                                        car.getHasSencsors(), car.getHasCameras(), car.getPrice(),
                                        car.getAvailableCount() });
                    }
                } else if (vehicleType.equals("carvan")) {
                    String[] columnNames = { "Id", "Name", "Manufacturer", "Model", "Year", "VID", "Fuel Type",
                            "Has Bathroom", "Number of Rooms", "Base Price", "Available Count" };
                    tableModel = new DefaultTableModel(columnNames, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            // Disallow editing for all cells
                            return false;
                        }
                    };
                    JTable table = new JTable(tableModel);
                    JScrollPane scrollPane = new JScrollPane(table);

                    BrowseProductsPage.this.resultBox.removeAll();
                    BrowseProductsPage.this.resultBox.add(scrollPane);
                    BrowseProductsPage.this.revalidate();

                    List<Carvan> list = db.searchCarvans(BrowseProductsPage.this.nameField.getText(),
                            BrowseProductsPage.this.fuelGasoline91.isSelected(),
                            BrowseProductsPage.this.fuelGasoline95.isSelected(),
                            BrowseProductsPage.this.fuelDiesel.isSelected(),
                            BrowseProductsPage.this.manufacturerField.getText());

                    // Insert values
                    for (Carvan carvan : list) {
                        JPanel panel = new JPanel(new GridLayout(1, 6));
                        resultBox.add(panel);

                        tableModel.addRow(
                                new Object[] { carvan.getId(), carvan.getProductName(), carvan.getManufacturer(),
                                        carvan.getModel(),
                                        carvan.getYear(), carvan.getVehicleIdentificationNumber(), carvan.getFuelType(),
                                        carvan.getHasBathroom(), carvan.getNumberOfRooms(), carvan.getPrice(),
                                        carvan.getAvailableCount() });
                    }
                } else if (vehicleType.equals("bus")) {
                    String[] columnNames = { "Id", "Name", "Manufacturer", "Model", "Year", "VID", "Fuel Type",
                            "Dbouble Decker", "Passenger Capacity", "Base Price", "Available Count" };
                    tableModel = new DefaultTableModel(columnNames, 0) {

                        @Override
                        public boolean isCellEditable(int row, int column) {
                            // Disallow editing for all cells
                            return false;
                        }
                    };

                    JTable table = new JTable(tableModel);
                    JScrollPane scrollPane = new JScrollPane(table);

                    BrowseProductsPage.this.resultBox.removeAll();
                    BrowseProductsPage.this.resultBox.add(scrollPane);
                    BrowseProductsPage.this.revalidate();

                    List<Bus> list = db.searchBuses(BrowseProductsPage.this.nameField.getText(),
                            BrowseProductsPage.this.fuelGasoline91.isSelected(),
                            BrowseProductsPage.this.fuelGasoline95.isSelected(),
                            BrowseProductsPage.this.fuelDiesel.isSelected(),
                            BrowseProductsPage.this.manufacturerField.getText());

                    // Insert values
                    for (Bus bus : list) {
                        JPanel panel = new JPanel(new GridLayout(1, 6));
                        resultBox.add(panel);

                        tableModel.addRow(
                                new Object[] { bus.getId(), bus.getProductName(), bus.getManufacturer(), bus.getModel(),
                                        bus.getYear(), bus.getVehicleIdentificationNumber(), bus.getFuelType(),
                                        bus.getPassengerCapacity(), bus.getIsDoubleDecker(), bus.getPrice(),
                                        bus.getAvailableCount() });
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
