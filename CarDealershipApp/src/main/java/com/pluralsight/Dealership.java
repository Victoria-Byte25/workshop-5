package com.pluralsight;

import java.util.ArrayList;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public Dealership() {
        this.inventory = new ArrayList<>();
    }

    public void setInventory(ArrayList<Vehicle> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Vehicle> getInventory() {
        return inventory;
    }

    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
    }

    // Filter by mileage
    public ArrayList<Vehicle> getVehiclesByMileage(int min, int max) {
        ArrayList<Vehicle> results = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getOdometer() >= min && v.getOdometer() <= max) {
                results.add(v);
            }
        }
        return results;
    }

    // Filter by type
    public ArrayList<Vehicle> getVehiclesByType(String type) {
        ArrayList<Vehicle> results = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getType().equalsIgnoreCase(type)) {
                results.add(v);
            }
        }
        return results;
    }

    // Optional: Getters for name, address, phone
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
}
