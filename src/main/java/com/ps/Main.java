package com.ps;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
        readFromFile();
        transactionList.displayTransactionList();

        for (int i = 0; i < 2; i++) {
            addDepositOption();
        }


        // Do-while loop
            // *Home Menu*
                // Switch statement
                    // Option D: Add Deposit
                    // Option P: Make Payment (Debit)
                    // Option L: Ledger Menu
                    // Option X: Exit

                // *Add Deposit*
                    // Ask user for deposit info
                        // Description
                        // Vendor
                        // Deposit amount
                    // Save info to transactions
                    // "...Will auto-redirect to Home menu once transaction is complete..." <-- Might not do this.

                // *Make Payment (Debit)*
                    // Ask user for payment/debit info
                        // Description
                        // Vendor
                        // Payment/debit amount
                    // Save info to transactions
                    // "...Will auto-redirect to Home menu once transaction is complete..." <-- Might not do this.

            // Do-while loop
                // **Ledger Menu**
                    // Switch statement
                        // Option A: All
                        // Option D: Deposits
                        // Option P: Payments
                    // Do-while loop
                        // Option R: ***Report Menu***
                            // Switch statement
                                // Option 1) Month To Date
                                // Option 2) Previous Month
                                // Option 3) Year To Date
                                // Option 4) Previous Year
                                // Option 5) Search by Vendor
                                // Option 0) Back
                    // End of Report's Do-while loop
                        // Option H: Home
            // End of Ledger Menu Do-while loop




    }

    // readFromFile method to read from File
    public static void readFromFile() {
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

    // writeToFile method to write to File
    public static void writeToFile(Transaction transaction) {
        // Format Date and Time

        try {
            // Have to append data to the end of the .txt file so that it doesn't overwrite the previous output(s) to the file.
            // The append argument is set to "true" for the FileWriter.
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.txt", true));
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = transaction.getDate().format(dateFormatter);
            String formattedTime = transaction.getTime().format(timeFormatter);

            bufWriter.write(
                    formattedDate + "|" +
                    formattedTime + "|" +
                    String.format("%s|%s|%.2f\n",
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount()
                    ));

            System.out.printf("%s|%s|%s|%s|%.2f\n",
                    formattedDate,
                    formattedTime,
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());

            System.out.println("Printed Successfully......");

            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static methods for Menus
    public static void addDepositOption() {
        String description;
        String vendor;
        float amount;

        Scanner scanner = new Scanner(System.in);
        // *Add Deposit*
            // Ask user for deposit info
                // Description
        System.out.println("Enter a description of the deposit (e.g. \"Invoice 1001 paid\"): ");
        description = scanner.nextLine();
                // Vendor
        System.out.println("Enter name of vendor you're depositing to (e.g. Joe): ");
        vendor = scanner.nextLine();
                // Deposit amount
        System.out.println("Enter deposit amount: ");
        amount = scanner.nextFloat();
        System.out.println();
            // Save info to transactions
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            // Add transaction to transactions file
        writeToFile(transaction);
            // "...Will auto-redirect to Home menu once transaction is complete..." <-- Might not do this.
    }

    public static void makePaymentOption() {

    }

}