package com.ps;

public class Main {
    public static void main(String[] args) {
        /*
        2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50 <---- This is a payment (notice number is negative; subtracting)
        2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00 <---- This is a deposit. ("Joe," the vendor, is being paid)
        Negative --> Payment
        Positive --> Deposit
         */
        // Create class for storing "transactions"

        // Read from File first

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

    // OutputData method to write to File

    // Static methods for Menus

}