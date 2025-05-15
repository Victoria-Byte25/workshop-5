package com.pluralsight;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DealershipFileManager {
    public Dealership getDealership() {
        Dealership dealership = null;

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("inventory.csv");

            if (inputStream == null) {
                System.out.println("ERROR: inventory.csv not found in resources folder.");
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();

            if (line != null) {
                String[] info = line.split("\\|");
                dealership = new Dealership(info[0], info[1], info[2]);
            }

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                int vin = Integer.parseInt(data[0]);
                int year = Integer.parseInt(data[1]);
                String make = data[2];
                String model = data[3];
                String type = data[4];
                String color = data[5];
                int odometer = Integer.parseInt(data[6]);
                double price = Double.parseDouble(data[7]);

                Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
                dealership.addVehicle(vehicle);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading inventory.csv: " + e.getMessage());
        }

        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        System.out.println("Saving is not supported with classpath resources.");
    }
}
