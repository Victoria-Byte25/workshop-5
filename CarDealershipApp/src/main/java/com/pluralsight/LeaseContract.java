package com.pluralsight;

public class LeaseContract extends Contract {
    public LeaseContract(String date, String name, String email, Vehicle vehicle) {
        super(date, name, email, vehicle);
    }

    @Override
    public double getTotalPrice() {
        double expectedEndValue = vehicle.getPrice() * 0.50;
        double leaseFee = vehicle.getPrice() * 0.07;
        return expectedEndValue + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        double leasePrice = getTotalPrice();
        double monthlyInterestRate = 0.04 / 12;
        int months = 36;
        return (leasePrice * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -months));
    }

    @Override
    public String getContractType() {
        return "LEASE";
    }
}

