package com.pluralsight;


public class SalesContract extends Contract {
    private boolean finance;

    public SalesContract(String date, String name, String email, Vehicle vehicle, boolean finance) {
        super(date, name, email, vehicle);
        this.finance = finance;
    }

    public boolean isFinance() { return finance; }

    @Override
    public double getTotalPrice() {
        double salesTax = vehicle.getPrice() * 0.05;
        double recordingFee = 100;
        double processingFee = vehicle.getPrice() < 10000 ? 295 : 495;
        return vehicle.getPrice() + salesTax + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (!finance) return 0;

        double totalPrice = getTotalPrice();
        double interestRate = vehicle.getPrice() >= 10000 ? 0.0425 : 0.0525;
        int months = vehicle.getPrice() >= 10000 ? 48 : 24;

        double monthlyInterestRate = interestRate / 12;
        return (totalPrice * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -months));
    }

    @Override
    public String getContractType() {
        return "SALE";
    }
}

