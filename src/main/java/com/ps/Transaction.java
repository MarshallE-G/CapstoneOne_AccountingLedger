package com.ps;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    // date|time|description|vendor|amount
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private float amount;
    private ColorsAndGrapics style = new ColorsAndGrapics();

    // Constructor
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, float amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    // ToString() method
    @Override
    public String toString() {
        if (amount == Math.abs(amount)) {
            return date + " | " + time + " | " + description + " | " + vendor + " | " + style.GREEN + "$" + amount + style.ENDCOLOR;
        } else if (amount != Math.abs(amount)) {
            return date + " | " + time + " | " + description + " | " + vendor + " | " + style.RED + "-$" + Math.abs(amount) + style.ENDCOLOR;
        }
        return null;
    }
}
