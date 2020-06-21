package com.example.calculator.api.requests;

public class Data {
    private Integer creditAmount;
    private Double interestRate;
    private Integer quantityMonths;
    private String date;

    public Data() {
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getQuantityMonths() {
        return quantityMonths;
    }

    public void setQuantityMonths(int quantityMonths) {
        this.quantityMonths = quantityMonths;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
