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
        Negative --> Payment (debits)
        Positive --> Deposit (credits)
         */
        // Create class for storing "transactions"
        Scanner scanner = new Scanner(System.in);

        // Read from File first
        readFromFile();
        transactionList.displayAllTransactions();
        transactionList.monthToDate();

        String menuSelection;

        // Do-while loop
            // *Home Menu*
                // Switch statement
                    // Option D: Add Deposit
                    // Option P: Make Payment (Debit)
                    // Option L: Ledger Menu
                    // Option X: Exit
        displayHomeOptions();

        System.out.println();

        addDepositOption();
        makePaymentOption();

//        System.out.println("\nEnter selection here:");
//        menuSelection = scanner.next();
//
//        switch (menuSelection.toUpperCase()) { // Making every input uppercase acts as a make-shift ignore-case.
//            case "D":
//                System.out.println("Option D selected");
//                System.out.println("Printed successfully....");
//                break;
//            case "P":
//                System.out.println("Option P selected");
//                System.out.println("Printed successfully....");
//                break;
//            case "L":
//                System.out.println("Option L selected");
//                System.out.println("Printed successfully....");
//                break;
//            case "X":
//                System.out.println("Option X selected");
//                System.out.println("Printed successfully....");
//                break;
//            default:
//                System.out.println(menuSelection);
//                System.out.println("ERROR: Must type D, P, L, or X!");
//                System.out.println("Printed successfully....");
//                break;
//        }

            // Do-while loop
                // **Ledger Menu**
                    // Switch statement
                        // Option A: All
                        // Option D: Deposits
                        // Option P: Payments
                    // Do-while loop
                        // Option R: ***Report Menu*** (display transactions based on filter options)
                            // Switch statement
                                // Option 1) Month To Date - first of the month that you're in (e.g. April 1st to NOW)
                                // Option 2) Previous Month - Everything in the previous month (e.g. March)
                                // Option 3) Year To Date - January 1st to NOW
                                // Option 4) Previous Year - January 1st of Previous year to NOW
                                // Option 5) Search by Vendor - Ask for vendor's name. (DON'T use spaces n stuff, keep it simple)
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

            System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");
            if (transaction.getAmount() == Math.abs(transaction.getAmount())) { // If credit: prints NO negative sign
                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        formattedDate,
                        formattedTime,
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            } else if (transaction.getAmount() != Math.abs(transaction.getAmount())) { // If debit: prints negative sign
                System.out.printf("%s | %s | %s | %s | -$%.2f\n",
                        formattedDate,
                        formattedTime,
                        transaction.getDescription(),
                        transaction.getVendor(),
                        Math.abs(transaction.getAmount()));
            }

            System.out.println("Printed Successfully......");

            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static methods for Menus
    public static void displayHomeOptions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select from the following menu options:");

        // Option D: Add Deposit
        System.out.println("\tEnter D to add a deposit");
        // Option P: Make Payment (Debit)
        System.out.println("\tEnter P to make a payment (debit)");
        // Option L: Ledger Menu
        System.out.println("\tEnter L for the Ledger Menu");
        // Option X: Exit
        System.out.println("\tEnter X to EXIT");
    }

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
        System.out.println("Enter name of vendor (e.g. Joe): ");
        vendor = scanner.nextLine();
                // Deposit amount
        System.out.println("Enter deposit amount: ");
        amount = scanner.nextFloat();
        System.out.println();
            // Save info to transactions
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            // Output transaction to transactions file
        writeToFile(transaction);

        System.out.println("Auto-redirecting to Home menu...\n\n");
    }

    public static void makePaymentOption() {
        // *Make Payment (Debit)*
        String description;
        String vendor;
        float amount;

        Scanner scanner = new Scanner(System.in);

            // Ask user for payment/debit info
                // Description
        System.out.println("Enter a description of the payment/debit (e.g. \"ergonomic keyboard\"): ");
        description = scanner.nextLine();
                // Vendor
        System.out.println("Enter name of vendor (e.g. Amazon): ");
        vendor = scanner.nextLine();
                // Payment/debit amount
        System.out.println("Enter payment/debit amount: ");
        amount = -1 * scanner.nextFloat(); // Multiply inputted amount by -1 to get negative value (debit)
        System.out.println();
            // Save info to transactions
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            // Output transaction to transactions file
        writeToFile(transaction);

        System.out.println("Auto-redirecting to Home menu...\n\n");
    }

}