package com.ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    // Static global variable for transactions
    static TransactionStorage transactionList = new TransactionStorage();

    public static void main(String[] args) {
        /*
        2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50 <---- This is a payment (notice number is negative; subtracting)
        2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00 <---- This is a deposit. ("Joe," the vendor, is being paid)
        Negative --> Payment
        Positive --> Deposit
         */
        // Create class for storing "transactions"

        // Read from File first
        inputData();
        transactionList.displayTransactionList();

        // *Home Menu*
            // Option D: Add Deposit
            // Option P: Make Payment (Debit)
            // Option L: Ledger Menu
            // Option X: Exit

            // *Add Deposit*
                // Ask user for deposit info
                // Save info to transactions
                // "...Will auto-redirect to Home menu once transaction is complete..." <-- Might not do this.


            // *Make Payment (Debit)*
                // Ask user for payment/debit info
                // Save info to transactions
                // "...Will auto-redirect to Home menu once transaction is complete..." <-- Might not do this.

            // **Ledger Menu**
                // Option A:
                // Option D:
                // Option P:
                // Option R: ***Report Menu***
                    // Option 1) Month To Date
                    // Option 2) Previous Month
                    // Option 3) Year To Date
                    // Option 4) Previous Year
                    // Option 5) Search by Vendor
                    // Option 0) Back
                // Option H: Home




    }

    // InputData method to read from File
    public static void inputData() {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("transactions.txt"));

            String line;
            while ((line = bufReader.readLine()) != null) {
                String[] splitLine = line.split("\\|");
                LocalDate date = LocalDate.parse(splitLine[0]);
                LocalTime time = LocalTime.parse(splitLine[1]);
                String description = splitLine[2];
                String vendor = splitLine[3];
                float amount = Float.parseFloat(splitLine[4]);
                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactionList.addTransactionToList(transaction);
            }

            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // OutputData method to write to File

    // Static methods for Menus

}