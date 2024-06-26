package carsdealership;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

class Database implements AutoCloseable {
    final static String DATABASE_URL = "jdbc:sqlite:carsdealership.sqlite3";

    Connection connection;

    Database() throws SQLException {
        // NOTE: Security disaster.
        this.connection = DriverManager.getConnection(DATABASE_URL, "user", "password");

        Statement stmt = this.connection.createStatement();

        stmt.execute("PRAGMA foreign_keys=ON");

        stmt.addBatch("""
                    CREATE TABLE IF NOT EXISTS Users (
                        id TEXT UNIQUE NOT NULL,
                        username TEXT UNIQUE NOT NULL,
                        password TEXT NOT NULL,
                        fName TEXT NOT NULL,
                        lName TEXT NOT NULL,
                        userType TEXT CHECK(userType IN ('employee', 'costumer')) NOT NULL,
                        salary DECIMAL,
                        employeeType TEXT CHECK(employeeType IN ('admin', 'salesman')),
                        isOwner BOOLEAN,
                        branch TEXT,
                        phone TEXT,
                        email TEXT,
                        loyalityPoints INT,
                        PRIMARY KEY (id)
                    );
                """);
        stmt.addBatch("""
                    CREATE TABLE IF NOT EXISTS AdminManagesSalesMan (
                        adminId TEXT,
                        salesManId TEXT,
                        FOREIGN KEY (adminId) REFERENCES Users(id) ON DELETE CASCADE,
                        FOREIGN KEY (salesManId) REFERENCES Users(id) ON DELETE CASCADE
                    );
                """);
        // TODO: Implement logic for the Offices table.
        stmt.addBatch("""
                    CREATE TABLE IF NOT EXISTS Offices (
                        id INT NOT NULL,
                        adminId TEXT,
                        FOREIGN KEY (adminId) REFERENCES Users(id) ON DELETE SET NULL
                    );
                """);
        stmt.addBatch("""
                    CREATE TABLE IF NOT EXISTS SaleOperation (
                        id TEXT UNIQUE NOT NULL,
                        costumerId TEXT NOT NULL,
                        salesmanId TEXT,
                        paymentMethod TEXT CHECK(paymentMethod IN ('Visa', 'Mada', 'Cache')) NOT NULL,
                        time DATETIME NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (costumerId) REFERENCES Users(id) ON DELETE CASCADE
                        FOREIGN KEY (salesmanId) REFERENCES Users(id) ON DELETE SET NULL
                    );
                """);
        stmt.addBatch("""
                    CREATE TABLE IF NOT EXISTS Products (
                        id TEXT UNIQUE NOT NULL,
                        name TEXT NOT NULL,
                        basePrice FLOAT NOT NULL,
                        availableCount INT NOT NULL,
                        productType TEXT CHECK(productType IN ('vehicle')) NOT NULL,
                        model TEXT,
                        year INT,
                        vehicleId TEXT UNIQUE,
                        color TEXT,
                        manufacturer TEXT,
                        fuelType TEXT CHECK(fuelType IN ('Gasoline95', 'Gasoline91', 'Diesel')),
                        vehicleType TEXT CHECK(vehicleType IN ('car', 'bus', 'carvan')),
                        hasSencsors BOOLEAN,
                        hasCameras BOOLEAN,
                        passengerCapacity INT,
                        isDoubleDecker BOOLEAN,
                        hasBathroom BOOLEAN,
                        numberOfRooms INT,
                        PRIMARY KEY (id)
                    );
                """);
        stmt.addBatch("""
                    CREATE TABLE IF NOT EXISTS ProductLinkedWithSaleOperations (
                        POId TEXT NOT NULL,
                        productId TEXT NOT NULL,
                        FOREIGN KEY (POId) REFERENCES SaleOperation(id) ON DELETE CASCADE,
                        FOREIGN KEY (productId) REFERENCES Products(id)
                    );
                """);

        stmt.executeBatch();

        stmt.close();
    }

    public int adminsCount() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS adminsCount FROM Users WHERE employeeType = 'admin';");

        return result.getInt(1);
    }

    public void createAdminAccount(AdminAccount account) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        INSERT INTO Users (id, username, password, fName, lName, userType, salary, branch, employeeType, isOwner)
                        VALUES (?, ?, ?, ?, ?, 'employee', ?, ?, 'admin', ?);
                            """);

        stmt.setString(1, account.getId());
        stmt.setString(2, account.getUserName());
        stmt.setString(3, account.getPassword());
        stmt.setString(4, account.getFirstName());
        stmt.setString(5, account.getLastName());
        stmt.setDouble(6, account.getSalary());
        stmt.setString(7, account.getBranch());
        stmt.setBoolean(8, account.getIsOwner());

        stmt.executeUpdate();
    }

    public void createSalesManAccount(SalesManAccount account, String managerId) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        INSERT INTO Users (id, username, password, fName, lName, userType, salary, branch, employeeType)
                        VALUES (?, ?, ?, ?, ?, 'employee', ?, ?, 'salesman');
                            """);

        stmt.setString(1, account.getId());
        stmt.setString(2, account.getUserName());
        stmt.setString(3, account.getPassword());
        stmt.setString(4, account.getFirstName());
        stmt.setString(5, account.getLastName());
        stmt.setDouble(6, account.getSalary());
        stmt.setString(7, account.getBranch());

        stmt.executeUpdate();

        this.setManagerForSalesMan(managerId, account.getId());
    }

    public void setManagerForSalesMan(String managerId, String salesmanId) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        INSERT INTO AdminManagesSalesMan (adminId, salesManId)
                        VALUES (?, ?);
                            """);

        stmt.setString(1, managerId);
        stmt.setString(2, salesmanId);

        stmt.executeUpdate();
    }

    public void createCostumerAccount(CostomerAccount account) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        INSERT INTO Users (id, username, password, fName, lName, userType, phone, email, loyalityPoints)
                        VALUES (?, ?, ?, ?, ?, 'costumer', ?, ?, ?);
                            """);

        stmt.setString(1, account.getId());
        stmt.setString(2, account.getUserName());
        stmt.setString(3, account.getPassword());
        stmt.setString(4, account.getFirstName());
        stmt.setString(5, account.getLastName());
        stmt.setString(6, account.getPhoneNumber());
        stmt.setString(7, account.getEmailAddress());
        stmt.setInt(8, account.getLoyalityPoints());

        stmt.executeUpdate();
    }

    public void createCar(Car product) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        INSERT INTO Products (id, name, basePrice, availableCount, productType, model, year, vehicleId, color, manufacturer, fuelType, vehicleType, hasSencsors, hasCameras)
                        VALUES (?, ?, ?, ?, 'vehicle', ?, ?, ?, ?, ?, ?, 'car', ?, ?);
                            """);

        stmt.setString(1, product.getId());
        stmt.setString(2, product.getProductName());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getAvailableCount());
        stmt.setString(5, product.getModel());
        stmt.setInt(6, product.getYear());
        stmt.setString(7, product.getVehicleIdentificationNumber());
        stmt.setString(8, product.getColor());
        stmt.setString(9, product.getManufacturer());
        stmt.setString(10, product.getFuelType());
        stmt.setBoolean(11, product.getHasSencsors());
        stmt.setBoolean(12, product.getHasCameras());

        stmt.executeUpdate();
    }

    public void createCarvan(Carvan product) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        INSERT INTO Products (id, name, basePrice, availableCount, productType, model, year, vehicleId, color, manufacturer, fuelType, vehicleType, hasBathroom, numberOfRooms)
                        VALUES (?, ?, ?, ?, 'vehicle', ?, ?, ?, ?, ?, ?, 'carvan', ?, ?);
                            """);

        stmt.setString(1, product.getId());
        stmt.setString(2, product.getProductName());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getAvailableCount());
        stmt.setString(5, product.getModel());
        stmt.setInt(6, product.getYear());
        stmt.setString(7, product.getVehicleIdentificationNumber());
        stmt.setString(8, product.getColor());
        stmt.setString(9, product.getManufacturer());
        stmt.setString(10, product.getFuelType().toString());
        stmt.setBoolean(11, product.getHasBathroom());
        stmt.setInt(12, product.getNumberOfRooms());

        stmt.executeUpdate();
    }

    public void createBus(Bus product) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        INSERT INTO Products (id, name, basePrice, availableCount, productType, model, year, vehicleId, color, manufacturer, fuelType, vehicleType, passengerCapacity, isDoubleDecker)
                        VALUES (?, ?, ?, ?, 'vehicle', ?, ?, ?, ?, ?, ?, 'bus', ?, ?);
                            """);

        stmt.setString(1, product.getId());
        stmt.setString(2, product.getProductName());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getAvailableCount());
        stmt.setString(5, product.getModel());
        stmt.setInt(6, product.getYear());
        stmt.setString(7, product.getVehicleIdentificationNumber());
        stmt.setString(8, product.getColor());
        stmt.setString(9, product.getManufacturer());
        stmt.setString(10, product.getFuelType().toString());
        stmt.setInt(11, product.getPassengerCapacity());
        stmt.setBoolean(12, product.getIsDoubleDecker());

        stmt.executeUpdate();
    }

    public void createSaleOperation(SaleOperation operation) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        INSERT INTO SaleOperation (id, costumerId, salesmanId, paymentMethod, time)
                        VALUES (?, ?, ?, ?, ?);
                            """);

        stmt.setString(1, operation.getId());
        stmt.setString(2, operation.getCostomerId());
        stmt.setString(3, operation.getSalesManId());
        stmt.setString(4, operation.getPaymentMethod());
        stmt.setLong(5, operation.getSaleTime());

        stmt.executeUpdate();

        this.decreaseProductAvilableCount(operation.getProductId());
        this.increaseCostumerLoyalityPoints(operation.getCostomerId());
        this.linkProductWithSaleOperation(operation.getProductId(), operation.getId());
    }

    public void deleteSaleOperation(String id) throws SQLException {
        this.increaseProductAvilableCount(id);

        PreparedStatement stmt = this.connection.prepareStatement(
                """
                        DELETE FROM SaleOperation WHERE id = ?;
                                """);

        stmt.setString(1, id);

        stmt.executeUpdate();

    }

    public void decreaseProductAvilableCount(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("UPDATE Products SET availableCount = availableCount - 1 WHERE id = ?");

        stmt.setString(1, id);

        stmt.executeUpdate();
    }

    public void increaseCostumerLoyalityPoints(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("UPDATE Users SET loyalityPoints = loyalityPoints + 10 WHERE id = ?");

        stmt.setString(1, id);

        stmt.executeUpdate();
    }

    public void increaseProductAvilableCount(String operationId) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement(
                        "UPDATE Products SET availableCount = availableCount + 1 WHERE id IN (SELECT productId FROM ProductLinkedWithSaleOperations WHERE POId = ?)");

        stmt.setString(1, operationId);

        stmt.executeUpdate();
    }

    public void linkProductWithSaleOperation(String productId, String saleOperationId) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("INSERT INTO ProductLinkedWithSaleOperations (POId, productId) VALUES (?, ?)");

        stmt.setString(1, saleOperationId);
        stmt.setString(2, productId);

        stmt.executeUpdate();
    }

    public UserType getUserTypeById(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT userType, employeeType FROM Users WHERE id = ?");

        stmt.setString(1, id);

        ResultSet result = stmt.executeQuery();
        String userType = result.getString("userType");
        String employeeType = result.getString("employeeType");

        if (userType != null) {
            if (userType.equals("employee")) {
                if (employeeType.equals("admin")) {
                    return UserType.Admin;
                } else {
                    return UserType.SalesMan;
                }
            } else {
                return UserType.Costumer;
            }
        } else {
            return UserType.Unknown;
        }
    }

    public String getUserIdByUsername(String username) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT id FROM Users WHERE username = ?");

        stmt.setString(1, username);

        ResultSet result = stmt.executeQuery();

        return result.getString("id");
    }

    public String getUserPasswordById(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT password FROM Users WHERE id = ?");

        stmt.setString(1, id);

        ResultSet result = stmt.executeQuery();

        return result.getString("password");
    }

    public boolean isProductExist(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT EXISTS (SELECT 1 FROM Products WHERE id = ?)");

        stmt.setString(1, id);

        return stmt.executeQuery().getBoolean(1);
    }

    public boolean isUserExist(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT EXISTS (SELECT 1 FROM Users WHERE id = ?)");

        stmt.setString(1, id);

        return stmt.executeQuery().getBoolean(1);
    }

    public boolean isCostumerExist(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT EXISTS (SELECT 1 FROM Users WHERE userType = 'costumer' AND id = ?)");

        stmt.setString(1, id);

        return stmt.executeQuery().getBoolean(1);
    }

    public boolean isSaleOperationExist(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT EXISTS (SELECT 1 FROM SaleOperation WHERE id = ?)");

        stmt.setString(1, id);

        return stmt.executeQuery().getBoolean(1);
    }

    public int getProductAvailableCount(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT availableCount FROM Products WHERE id = ?");

        stmt.setString(1, id);

        ResultSet result = stmt.executeQuery();

        return result.getInt("availableCount");
    }

    public void setProductAvailableCount(String id, int newAvailableCount) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("UPDATE Products SET availableCount = ? WHERE id = ?");

        stmt.setInt(1, newAvailableCount);
        stmt.setString(2, id);

        stmt.executeUpdate();
    }

    public Double getProductPrice(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT basePrice FROM Products WHERE id = ?");

        stmt.setString(1, id);

        ResultSet result = stmt.executeQuery();

        return result.getDouble("basePrice");
    }

    public void applyProductDiscount(String id, double discountPercentage) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("UPDATE Products SET basePrice = basePrice * ?/100 WHERE id = ?");

        stmt.setDouble(1, discountPercentage);
        stmt.setString(2, id);

        stmt.executeUpdate();
    }

    public void updatePassword(String id, String newPassword) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("UPDATE Users SET password = ? WHERE id = ?");

        stmt.setString(1, newPassword);
        stmt.setString(2, id);

        stmt.executeUpdate();
    }

    public void close() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }

    public Integer getSalesCount() throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT COUNT(*) AS Count FROM SaleOperation");

        int count = stmt.executeQuery().getInt("Count");

        return count;
    }

    public String getTopPaymentMethod() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery(
                "SELECT PaymentMethod, COUNT(*) AS Count FROM SaleOperation GROUP BY PaymentMethod ORDER BY Count DESC");

        result.next();

        String method = result.getString("PaymentMethod");

        return method;
    }

    public Integer getProductsCount() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS Count FROM Products");

        result.next();

        int count = result.getInt("Count");

        return count;
    }

    public Integer getTotalProductsPiecesCount() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT SUM(availableCount) AS Count FROM Products");

        result.next();

        int count = result.getInt("Count");

        return count;
    }

    public Integer getTotalUsersCount() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS Count FROM Users");

        result.next();

        int count = result.getInt("Count");

        return count;
    }

    public Integer getCostumersCount() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS Count FROM Users WHERE userType = 'costumer'");

        result.next();

        int count = result.getInt("Count");

        return count;
    }

    public Integer getAdminsCount() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS Count FROM Users WHERE employeeType = 'admin'");

        result.next();

        int count = result.getInt("Count");

        return count;
    }

    public Integer getSalesMenCount() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS Count FROM Users WHERE employeeType = 'salesman'");

        result.next();

        int count = result.getInt("Count");

        return count;
    }

    public Integer getSalesCountBySalesManId(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT COUNT(*) AS Count FROM SaleOperation WHERE salesmanId = ?");

        stmt.setString(1, id);

        int count = stmt.executeQuery().getInt("Count");

        return count;
    }

    public Integer getPurchasesCountByCostumerId(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT COUNT(*) AS Count FROM SaleOperation WHERE costumerId = ?");

        stmt.setString(1, id);

        int count = stmt.executeQuery().getInt("Count");

        return count;
    }

    public Integer getCostumerLoyalityPoints(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT loyalityPoints FROM Users WHERE id = ?");

        stmt.setString(1, id);

        int count = stmt.executeQuery().getInt("loyalityPoints");

        return count;
    }

    public void deleteUserAccount(String id) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("DELETE FROM Users WHERE id = ?");

        stmt.setString(1, id);

        stmt.executeUpdate();
    }

    public List<CostomerAccount> getCostumersList() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT * FROM Users WHERE userType = 'costumer'");

        List<CostomerAccount> list = new ArrayList<>();

        while (result.next()) {
            list.add(new CostomerAccount(result.getString("id"), result.getString("username"),
                    result.getString("password"), result.getString("fName"), result.getString("lName"),
                    result.getString("phone"), result.getString("email")));
        }

        return list;

    }

    public List<SaleOperation> getSaleOperationsList() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT * FROM SaleOperation");

        List<SaleOperation> list = new ArrayList<>();

        while (result.next()) {
            list.add(new SaleOperation(result.getString("id"), result.getString("costumerId"),
                    result.getString("salesmanId"), null, result.getString("paymentMethod"), result.getLong("time")));
        }

        return list;

    }

    public List<SaleOperation> getPreviousPurchasesList(String costumerId) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement("SELECT * FROM SaleOperation WHERE costumerId = ?");

        stmt.setString(1, costumerId);

        ResultSet result = stmt.executeQuery();

        List<SaleOperation> list = new ArrayList<>();

        while (result.next()) {
            list.add(new SaleOperation(result.getString("id"), result.getString("costumerId"),
                    result.getString("salesmanId"), null, result.getString("paymentMethod"), result.getLong("time")));
        }

        return list;

    }

    public List<Car> getCarsList() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT * FROM Products WHERE vehicleType = 'car'");

        List<Car> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Car(result.getString("id"), result.getString("name"), result.getDouble("basePrice"),
                    result.getInt("availableCount"), result.getInt("year"), result.getString("model"),
                    result.getString("vehicleId"), result.getString("color"), result.getString("manufacturer"),
                    result.getString("fuelType"), result.getBoolean("hasSencsors"), result.getBoolean("hasCameras")));
        }

        return list;

    }

    public List<Carvan> getCarvansList() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT * FROM Products WHERE vehicleType = 'carvan'");

        List<Carvan> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Carvan(result.getString("id"), result.getString("name"), result.getDouble("basePrice"),
                    result.getInt("availableCount"), result.getInt("year"), result.getString("model"),
                    result.getString("vehicleId"), result.getString("color"), result.getString("manufacturer"),
                    result.getInt("numberOfRooms"), result.getBoolean("hasBathroom"),
                    result.getString("fuelType")));
        }

        return list;

    }

    public List<Bus> getBussList() throws SQLException {
        Statement stmt = this.connection.createStatement();

        ResultSet result = stmt.executeQuery("SELECT * FROM Products WHERE vehicleType = 'bus'");

        List<Bus> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Bus(result.getString("id"), result.getString("name"), result.getDouble("basePrice"),
                    result.getInt("availableCount"), result.getInt("year"), result.getString("model"),
                    result.getString("vehicleId"), result.getString("color"), result.getString("manufacturer"),
                    result.getInt("passengerCapacity"), result.getBoolean("isDoubleDecker"),
                    result.getString("fuelType")));
        }

        return list;

    }

    public List<Car> searchCars(String name, Boolean fuelGasoline91, Boolean fuelGasoline95, Boolean fuelDiesel,
            String manufacturer) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement(
                        "SELECT * FROM Products WHERE vehicleType = 'car' AND name LIKE ? AND manufacturer LIKE ? AND fuelType IN (?, ?, ?)");

        stmt.setString(1, "%" + name + "%");
        stmt.setString(2, "%" + manufacturer + "%");
        stmt.setString(3, fuelGasoline91 ? "Gasoline91" : null);
        stmt.setString(4, fuelGasoline95 ? "Gasoline95" : null);
        stmt.setString(5, fuelDiesel ? "Diesel" : null);

        ResultSet result = stmt.executeQuery();

        List<Car> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Car(result.getString("id"), result.getString("name"), result.getDouble("basePrice"),
                    result.getInt("availableCount"), result.getInt("year"), result.getString("model"),
                    result.getString("vehicleId"), result.getString("color"), result.getString("manufacturer"),
                    result.getString("fuelType"), result.getBoolean("hasSencsors"), result.getBoolean("hasCameras")));
        }

        return list;

    }

    public List<Carvan> searchCarvans(String name, Boolean fuelGasoline91, Boolean fuelGasoline95, Boolean fuelDiesel,
            String manufacturer) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement(
                        "SELECT * FROM Products WHERE vehicleType = 'carvan' AND name LIKE ? AND manufacturer LIKE ? AND fuelType IN (?, ?, ?)");

        stmt.setString(1, "%" + name + "%");
        stmt.setString(2, "%" + manufacturer + "%");
        stmt.setString(3, fuelGasoline91 ? "Gasoline91" : null);
        stmt.setString(4, fuelGasoline95 ? "Gasoline95" : null);
        stmt.setString(5, fuelDiesel ? "Diesel" : null);

        ResultSet result = stmt.executeQuery();

        List<Carvan> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Carvan(result.getString("id"), result.getString("name"), result.getDouble("basePrice"),
                    result.getInt("availableCount"), result.getInt("year"), result.getString("model"),
                    result.getString("vehicleId"), result.getString("color"), result.getString("manufacturer"),
                    result.getInt("numberOfRooms"), result.getBoolean("hasBathroom"), result.getString("fuelType")));
        }

        return list;

    }

    public List<Bus> searchBuses(String name, Boolean fuelGasoline91, Boolean fuelGasoline95, Boolean fuelDiesel,
            String manufacturer) throws SQLException {
        PreparedStatement stmt = this.connection
                .prepareStatement(
                        "SELECT * FROM Products WHERE vehicleType = 'bus' AND name LIKE ? AND manufacturer LIKE ? AND fuelType IN (?, ?, ?)");

        stmt.setString(1, "%" + name + "%");
        stmt.setString(2, "%" + manufacturer + "%");
        stmt.setString(3, fuelGasoline91 ? "Gasoline91" : null);
        stmt.setString(4, fuelGasoline95 ? "Gasoline95" : null);
        stmt.setString(5, fuelDiesel ? "Diesel" : null);

        ResultSet result = stmt.executeQuery();

        List<Bus> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Bus(result.getString("id"), result.getString("name"), result.getDouble("basePrice"),
                    result.getInt("availableCount"), result.getInt("year"), result.getString("model"),
                    result.getString("vehicleId"), result.getString("color"), result.getString("manufacturer"),
                    result.getInt("passengerCapacity"), result.getBoolean("isDoubleDecker"),
                    result.getString("fuelType")));
        }

        return list;

    }
}
