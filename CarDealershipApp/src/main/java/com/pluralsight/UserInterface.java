package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;

    public void display() {
        init();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> processAllVehiclesRequest();
                case 2 -> processVehicleByPriceRequest();
                case 3 -> processVehicleByMakeModelRequest();
                case 4 -> processVehicleByYearRequest();
                case 5 -> processVehicleByColorRequest();
                case 6 -> processVehicleByMileageRequest();
                case 7 -> processVehicleByTypeRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 0 -> {
                    System.out.println("Thanks for searching, see you soon!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
    }

    private void displayMenu() {
        System.out.println("\n=== Car Dealership Menu ===");
        System.out.println("1 - List all vehicles");
        System.out.println("2 - Find vehicles by price");
        System.out.println("3 - Find vehicles by make/model");
        System.out.println("4 - Find vehicles by year range");
        System.out.println("5 - Find vehicles by color");
        System.out.println("6 - Find vehicles by mileage");
        System.out.println("7 - Find vehicles by type");
        System.out.println("8 - Add a vehicle");
        System.out.println("9 - Remove a vehicle");
        System.out.println("0 - Quit");
    }

    private void displayVehicles(ArrayList<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle v : vehicles) {
                System.out.println(v);
            }
        }
    }

    private void processAllVehiclesRequest() {
        displayVehicles(dealership.getAllVehicles());
    }

    private void processVehicleByPriceRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Min price: ");
        double min = scanner.nextDouble();
        System.out.print("Max price: ");
        double max = scanner.nextDouble();
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }

    private void processVehicleByMakeModelRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    private void processVehicleByYearRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Min year: ");
        int min = scanner.nextInt();
        System.out.print("Max year: ");
        int max = scanner.nextInt();
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    private void processVehicleByColorRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Color: ");
        String color = scanner.nextLine();
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    private void processVehicleByMileageRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Min mileage: ");
        int min = scanner.nextInt();
        System.out.print("Max mileage: ");
        int max = scanner.nextInt();
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }

    private void processVehicleByTypeRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type (e.g., SUV, truck, car): ");
        String type = scanner.nextLine();
        displayVehicles(dealership.getVehiclesByType(type));
    }

    private void processAddVehicleRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("VIN: ");
        int vin = scanner.nextInt();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // clear
        System.out.print("Make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Odometer: ");
        int odometer = scanner.nextInt();
        System.out.print("Price: ");
        double price = scanner.nextDouble();

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(vehicle);

        DealershipFileManager fileManager = new DealershipFileManager();
        fileManager.saveDealership(dealership);

        System.out.println("Vehicle added successfully.");
    }

    private void processRemoveVehicleRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter VIN to remove: ");
        int vin = scanner.nextInt();
        Vehicle toRemove = null;

        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                toRemove = v;
                break;
            }
        }

        if (toRemove != null) {
            dealership.removeVehicle(toRemove);
            DealershipFileManager fileManager = new DealershipFileManager();
            fileManager.saveDealership(dealership);
            System.out.println("Vehicle removed.");
        } else {
            System.out.println("Vehicle not found.");
        }
    }
}

