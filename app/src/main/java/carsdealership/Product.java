package carsdealership;

import java.util.UUID;

enum FuelType {
    Gasoline,
    Diesel,
    CompressedNatural,
    Ethanol,
}

enum ProductType {
    Car,
    Carvan,
    Bus,
}

class Product {
    private String id;
    private String productName;
    private double price;
    private int availableCount; // number of available products

    Product(String productName, double productPrice, int productAvailableCount) {
        setProductName(productName);
        setPrice(productPrice);
        setAvailableCount(productAvailableCount);

        this.id = UUID.randomUUID().toString();
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
    private FuelType fuelType;

    Vehicle(String vehicleName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        super(vehicleName, price, availableCount);

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

    public FuelType getFuelType() {
        return this.fuelType;
    }
}

class Car extends Vehicle {
    private boolean hasSencsors;
    private boolean hasCameras;
    private boolean hasBlindSpotRadar;
    private String shifterType;

    Car(String carName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType, boolean hasSencsors, boolean hasCameras,
            boolean hasBlindSpotRadar, String shifterType) {
        super(carName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.hasSencsors = hasSencsors;
        this.hasCameras = hasCameras;
        this.hasBlindSpotRadar = hasBlindSpotRadar;
        this.shifterType = shifterType;
    }

    public boolean getHasSencsors() {
        return this.hasSencsors;
    }

    public boolean getHasCameras() {
        return this.hasCameras;
    }

    public boolean getHasBlindSportRadar() {
        return this.hasBlindSpotRadar;
    }

    public String getShifterType() {
        return this.shifterType;
    }
}

class Carvan extends Vehicle {
    private int numberOfRooms;
    private boolean hasKitchen;
    private boolean hasBathroom;
    private double waterCapacity; // In Litres

    Carvan(String carvanName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, int numberOfRooms, boolean hasKitchen, boolean hasBathroom,
            double waterCapacity, FuelType fuelType) {
        super(carvanName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.numberOfRooms = numberOfRooms;
        this.hasKitchen = hasKitchen;
        this.hasBathroom = hasBathroom;
        this.waterCapacity = waterCapacity;
    }

    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    public boolean getHasKitchen() {
        return this.hasKitchen;
    }

    public boolean getHasBathroom() {
        return this.hasBathroom;
    }

    public double getWaterCapacity() {
        return this.waterCapacity;
    }
}

class Bus extends Vehicle {
    private int passengerCapacity;
    private boolean isDoubleDecker;
    private boolean hasWifi;
    private boolean hasBathroom;

    Bus(String busName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, int passengerCapacity, boolean isDoubleDecker, boolean hasWifi,
            boolean hasBathroom, FuelType fuelType) {
        super(busName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.passengerCapacity = passengerCapacity;
        this.isDoubleDecker = isDoubleDecker;
        this.hasWifi = hasWifi;
        this.hasBathroom = hasBathroom;
    }

    public int getPassengerCapacity() {
        return this.passengerCapacity;
    }

    public boolean getIsDoubleDecker() {
        return this.isDoubleDecker;
    }

    public boolean getHasWifi() {
        return this.hasWifi;
    }

    public boolean getHasBathroom() {
        return this.hasBathroom;
    }
}
