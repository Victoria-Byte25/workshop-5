package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DealershipFileManager {
    public Dealership getDealership() {
        Dealership dealership = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.csv"))) {
            // Read dealership info (first line)
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

        } catch (IOException e) {
            System.out.println("Error loading dealership: " + e.getMessage());
        }

        return dealership;
    }
}
