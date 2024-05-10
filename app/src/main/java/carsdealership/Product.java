package carsdealership;

import java.io.Serializable;

enum FuelType {
    Gasoline95,
    Gasoline91,
    Diesel,
}

enum ProductType {
    Car,
    Carvan,
    Bus,
}

class Product implements Serializable {
    private String id;
    private String productName;
    private double price;
    private int availableCount; // number of available products

    Product(String id, String productName, double productPrice, int productAvailableCount) {
        setProductName(productName);
        setPrice(productPrice);
        setAvailableCount(productAvailableCount);

        this.id = id;
    }

    private void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public void incrementAvailableCount() {
        this.availableCount++;
    }

    public void decrementAvailableCount() {
        this.availableCount--;
    }

    public String getId() {
        return this.id;
    }

    public String getProductName() {
        return this.productName;
    }

    public double getPrice() {
        return this.price;
    }

    public int getAvailableCount() {
        return this.availableCount;
    }
}

class Vehicle extends Product {
    private int year;
    private String model;
    private String vehicleIdentificationNumber;
    private String color;
    private String manufacturer;
    private String fuelType;

    Vehicle(String id, String vehicleName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, String fuelType) {
        super(id, vehicleName, price, availableCount);

        this.year = year;
        this.model = model;
        this.vehicleIdentificationNumber = vehicleId;
        this.color = color;
        this.manufacturer = manufacturer;
        this.fuelType = fuelType;
    }

    public int getYear() {
        return this.year;
    }

    public String getModel() {
        return this.model;
    }

    public String getVehicleIdentificationNumber() {
        return this.vehicleIdentificationNumber;
    }

    public String getColor() {
        return this.color;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getFuelType() {
        return this.fuelType;
    }
}

class Car extends Vehicle {
    private boolean hasSencsors;
    private boolean hasCameras;

    Car(String id, String carName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, String fuelType, boolean hasSencsors, boolean hasCameras) {
        super(id, carName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.hasSencsors = hasSencsors;
        this.hasCameras = hasCameras;
    }

    public boolean getHasSencsors() {
        return this.hasSencsors;
    }

    public boolean getHasCameras() {
        return this.hasCameras;
    }
}

class Carvan extends Vehicle {
    private int numberOfRooms;
    private boolean hasBathroom;

    Carvan(String id, String carvanName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, int numberOfRooms, boolean hasBathroom, String fuelType) {
        super(id, carvanName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.numberOfRooms = numberOfRooms;
        this.hasBathroom = hasBathroom;
    }

    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    public boolean getHasBathroom() {
        return this.hasBathroom;
    }
}

class Bus extends Vehicle {
    private int passengerCapacity;
    private boolean isDoubleDecker;

    Bus(String id, String busName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, int passengerCapacity, boolean isDoubleDecker, String fuelType) {
        super(id, busName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.passengerCapacity = passengerCapacity;
        this.isDoubleDecker = isDoubleDecker;
    }

    public int getPassengerCapacity() {
        return this.passengerCapacity;
    }

    public boolean getIsDoubleDecker() {
        return this.isDoubleDecker;
    }
}
