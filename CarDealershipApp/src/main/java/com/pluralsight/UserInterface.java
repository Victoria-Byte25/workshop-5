package com.pluralsight;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserInterface {
    private List<Vehicle> inventory;
    private Scanner scanner;

    public UserInterface() {
        inventory = loadInventory();
        scanner = new Scanner(System.in);
    }

    public void display() {
        String choice;
        do {
            System.out.println("\n=== DEALERSHIP MENU ===");
            System.out.println("1. Browse All Vehicles");
            System.out.println("2. Search by Price Range");
            System.out.println("3. Search by Make & Model");
            System.out.println("4. Search by Year Range");
            System.out.println("5. Sell or Lease a Vehicle");
            System.out.println("6. Admin Mode");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showAllVehicles();
                case "2" -> searchByPrice();
                case "3" -> searchByMakeModel();
                case "4" -> searchByYear();
                case "5" -> sellOrLeaseVehicle();
                case "6" -> adminMenu();
                case "0" -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }
        } while (!choice.equals("0"));
    }

    // === Inventory Functions ===

    private List<Vehicle> loadInventory() {
        List<Vehicle> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.csv"))) {
            String line;
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

                list.add(new Vehicle(vin, year, make, model, type, color, odometer, price));
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file: " + e.getMessage());
        }
        return list;
    }

    private void saveInventory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("inventory.csv"))) {
            for (Vehicle v : inventory) {
                writer.println(v.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    private Vehicle getVehicleByVin(int vin) {
        return inventory.stream()
                .filter(v -> v.getVin() == vin)
                .findFirst()
                .orElse(null);
    }

    private void removeVehicle(int vin) {
        inventory.removeIf(v -> v.getVin() == vin);
        saveInventory();
    }

    // === Menu Actions ===

    private void showAllVehicles() {
        if (inventory.isEmpty()) {
            System.out.println("No vehicles available.");
        } else {
            inventory.forEach(System.out::println);
        }
    }

    private void searchByPrice() {
        System.out.print("Enter min price: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter max price: ");
        double max = Double.parseDouble(scanner.nextLine());

        inventory.stream()
                .filter(v -> v.getPrice() >= min && v.getPrice() <= max)
                .forEach(System.out::println);
    }

    private void searchByMakeModel() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        inventory.stream()
                .filter(v -> v.getMake().equalsIgnoreCase(make) && v.getModel().equalsIgnoreCase(model))
                .forEach(System.out::println);
    }

    private void searchByYear() {
        System.out.print("Enter start year: ");
        int start = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter end year: ");
        int end = Integer.parseInt(scanner.nextLine());

        inventory.stream()
                .filter(v -> v.getYear() >= start && v.getYear() <= end)
                .forEach(System.out::println);
    }

    private void sellOrLeaseVehicle() {
        System.out.print("Enter date (YYYYMMDD): ");
        String date = scanner.nextLine();
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        System.out.print("Enter VIN: ");
        int vin = Integer.parseInt(scanner.nextLine());

        Vehicle vehicle = getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Sale or Lease? (S/L): ");
        String type = scanner.nextLine();
        Contract contract;

        if (type.equalsIgnoreCase("S")) {
            System.out.print("Finance? (yes/no): ");
            boolean finance = scanner.nextLine().equalsIgnoreCase("yes");
            contract = new SalesContract(date, name, email, vehicle, finance);
        } else if (type.equalsIgnoreCase("L")) {
            if (2025 - vehicle.getYear() > 3) {
                System.out.println("Can't lease vehicles over 3 years old.");
                return;
            }
            contract = new LeaseContract(date, name, email, vehicle);
        } else {
            System.out.println("Invalid option.");
            return;
        }

        new ContractManager().saveContract(contract);
        removeVehicle(vin);
        System.out.println("Contract saved. Vehicle removed.");
    }

    private void adminMenu() {
        System.out.print("Enter admin password: ");
        if (!scanner.nextLine().equals("admin123")) {
            System.out.println("Access denied.");
            return;
        }

        System.out.println("1. List all contracts");
        System.out.println("2. List last 10 contracts");
        String option = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("contracts.csv"))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            if (option.equals("1")) {
                lines.forEach(System.out::println);
            } else if (option.equals("2")) {
                lines.stream().skip(Math.max(0, lines.size() - 10)).forEach(System.out::println);
            } else {
                System.out.println("Invalid option.");
            }
        } catch (IOException e) {
            System.out.println("Error reading contracts file: " + e.getMessage());
        }
    }
}
