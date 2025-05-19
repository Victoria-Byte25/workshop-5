package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ContractManager {

    public void saveContract(Contract contract) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("contracts.csv", true))) {
            StringBuilder line = new StringBuilder();

            line.append(contract.getContractType()).append("|")
                    .append(contract.getDate()).append("|")
                    .append(contract.getCustomerName()).append("|")
                    .append(contract.getCustomerEmail()).append("|")
                    .append(contract.getVehicle().toFileString()).append("|");

            if (contract instanceof SalesContract sale) {
                double salesTax = sale.getVehicle().getPrice() * 0.05;
                double recordingFee = 100;
                double processingFee = sale.getVehicle().getPrice() < 10000 ? 295 : 495;

                line.append(String.format("%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                        salesTax,
                        recordingFee,
                        processingFee,
                        sale.getTotalPrice(),
                        sale.isFinance() ? "YES" : "NO",
                        sale.getMonthlyPayment()
                ));
            } else if (contract instanceof LeaseContract lease) {
                double expectedEndValue = lease.getVehicle().getPrice() * 0.50;
                double leaseFee = lease.getVehicle().getPrice() * 0.07;

                line.append(String.format("%.2f|%.2f|%.2f|%.2f",
                        expectedEndValue,
                        leaseFee,
                        lease.getTotalPrice(),
                        lease.getMonthlyPayment()
                ));
            }

            writer.println(line);

        } catch (IOException e) {
            System.out.println("Error saving contract: " + e.getMessage());
        }
    }
}
