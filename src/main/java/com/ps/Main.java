package com.ps;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    static TransactionStorage transactionList = new TransactionStorage();
    private static ColorsAndGrapics style = new ColorsAndGrapics();

    public static void main(String[] args) {
        /*
        2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50 <---- This is a payment (notice number is negative; subtracting)
        2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00 <---- This is a deposit. ("Joe," the vendor, is being paid)
        Negative --> Payment (debits)
        Positive --> Deposit (credits)
         */
        Scanner scanner = new Scanner(System.in);

        readFromFile();

        String menuSelection;
        do {
            homeMenu(); // ONLY displays Home menu options

            System.out.println("\n" + style.ITALIC + "Enter selection here:" + style.END_ITALIC);
            menuSelection = scanner.next();

            switch (menuSelection.toUpperCase()) { // Making every input uppercase acts as a make-shift ignore-case.
                case "D":
                    System.out.println("\nAdd " + style.BRIGHT_GREEN + "Deposit " + style.END_COLOR + "selected.\n");

                    addDeposit();
                    addNewDataToList();
                    break;
                case "P":
                    System.out.println("\nMake a " + style.BRIGHT_RED + "Payment (debit) " + style.END_COLOR + "selected.\n");

                    makePayment();
                    addNewDataToList();
                    break;
                case "L":
                    System.out.println("\n" + style.BRIGHT_YELLOW + "Ledger Menu " + style.END_COLOR + "selected.\n");

                    ledgerMenu(scanner);
                    break;
                case "X":
                    System.out.println(style.ITALIC + style.BOLD + "Exiting..." + style.END_ITALIC + style.END_BOLD);
                    break;
                default:
                    System.out.println(style.BOLD + style.RED + style.BLACK_BACKGROUND + "ERROR: Must type either D, P, L, or X!" + style.END_BOLD + style.END_COLOR);

                    backButton(scanner);
                    break;
            }
        } while (!menuSelection.equalsIgnoreCase("X"));
        scanner.close();
    }

    private static void readFromFile() {
        try {
            BufferedReader bufReader1 = new BufferedReader(new FileReader("transactions.txt")); // bufReader2 is in isFileEmpty() method

            String line;
            while ((line = bufReader1.readLine()) != null) {
                if (!line.isBlank()) {
                    String[] splitLine = line.split("\\|");
                    LocalDate date = LocalDate.parse(splitLine[0]);
                    LocalTime time = LocalTime.parse(splitLine[1]);
                    String description = splitLine[2];
                    String vendor = splitLine[3];
                    float amount = Float.parseFloat(splitLine[4]);
                    Transaction transaction = new Transaction(date, time, description, vendor, amount);

                    transactionList.addTransactionToList(transaction);
                }
            }
            bufReader1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads from the File, but clears the ArrayList then re-creates it using the (updated) transactions.txt file.
    private static void addNewDataToList() {
        try {
            BufferedReader bufReader1 = new BufferedReader(new FileReader("transactions.txt")); // bufReader2 is in isFileEmpty() method

            transactionList.clearTransactionList(); // Removes every element from the transactionsList ArrayList in order to add newly inputted elements.
            String line;
            while ((line = bufReader1.readLine()) != null) {
                if (!line.isBlank()) {
                    String[] splitLine = line.split("\\|");
                    LocalDate date = LocalDate.parse(splitLine[0]);
                    LocalTime time = LocalTime.parse(splitLine[1]);
                    String description = splitLine[2];
                    String vendor = splitLine[3];
                    float amount = Float.parseFloat(splitLine[4]);
                    Transaction transaction = new Transaction(date, time, description, vendor, amount);

                    transactionList.addTransactionToList(transaction);
                }
            }

            bufReader1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(Transaction transaction) {
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
                System.out.printf("Added transaction: %s | %s | %s | %s | " + style.BRIGHT_GREEN + "$%.2f" + style.END_COLOR + "\n", // For when you add a transaction (credit/debit)
                        formattedDate,
                        formattedTime,
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            } else if (transaction.getAmount() != Math.abs(transaction.getAmount())) { // If debit: prints negative sign
                System.out.printf("Added transaction: %s | %s | %s | %s | " + style.BRIGHT_RED + "-$%.2f" + style.END_COLOR + "\n", // For when you add a transaction (credit/debit)
                        formattedDate,
                        formattedTime,
                        transaction.getDescription(),
                        transaction.getVendor(),
                        Math.abs(transaction.getAmount()));
            }

            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Checks to see if transactions.txt file is empty or not.
    public static boolean isFileEmpty() {
        try {
            BufferedReader bufReader3 = new BufferedReader(new FileReader("transactions.txt"));

            String line;
            while ((line = bufReader3.readLine()) != null) {
                if (!line.isBlank()) {
                    bufReader3.close();

                    return false; // If file is NOT empty, returns "false"
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true; // Just added this cuz I had to
    }

// Static methods for Menus

    // Home Menu display
    private static void homeMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(style.BRIGHT_WHITE + "* * * * * * *");
        System.out.println("* Home Menu *");
        System.out.println("* * * * * * *" + style.END_COLOR + "\n");
        System.out.println(style.UNDERLINE + "Please select from the following menu options:" + style.END_UNDERLINE);

        // Option D: Add Deposit
        System.out.println("\t" + style.GREEN_BACKGROUND + style.BRIGHT_WHITE + "Enter D" + style.END_COLOR + " to add a " +
                style.BRIGHT_GREEN + "Deposit" + style.END_COLOR);
        // Option P: Make Payment (Debit)
        System.out.println("\t" + style.RED_BACKGROUND + style.BRIGHT_WHITE + "Enter P" + style.END_COLOR + " to make a " +
                style.BRIGHT_RED + "Payment (debit)" + style.END_COLOR);
        // Option L: Ledger Menu
        System.out.println("\t" + style.YELLOW_BACKGROUND + style.BRIGHT_WHITE + "Enter L" + style.END_COLOR + " for the " +
                style.BRIGHT_YELLOW + "Ledger Menu" + style.END_COLOR);
        // Option X: Exit
        System.out.println("\t" + style.BLACK_BACKGROUND + style.BRIGHT_WHITE + "Enter X" + style.END_COLOR + " to " +
                style.BLACK + "EXIT" + style.END_COLOR);
    }

    private static void addDeposit() {
        String description;
        String vendor;
        float amount;

        Scanner scanner = new Scanner(System.in);
        // *Add Deposit*
            // Ask user for deposit info
                // Description
        System.out.println("Enter a description of the " + style.BRIGHT_GREEN + "deposit" + style.END_COLOR +
                " (e.g. \"Invoice 1001 paid\"): ");
        description = scanner.nextLine();
                // Vendor
        System.out.println("Enter name of vendor (e.g. Joe): ");
        vendor = scanner.nextLine();
                // Deposit amount
        System.out.println("Enter " + style.BRIGHT_GREEN + "deposit" + style.END_COLOR + " amount: ");
        amount = scanner.nextFloat();
        System.out.println();
            // Save info to transactions
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            // Output transaction to transactions file
        writeToFile(transaction);

        System.out.println("\n" + style.ITALIC + style.BOLD + "Auto-redirecting to Home menu..." + style.END_ITALIC + style.END_BOLD + "\n\n");
    }

    private static void makePayment() {
        // *Make Payment (Debit)*
        String description;
        String vendor;
        float amount;

        Scanner scanner = new Scanner(System.in);

            // Ask user for payment/debit info
                // Description
        System.out.println("Enter a description of the " + style.BRIGHT_RED + "payment/debit" + style.END_COLOR +
                " (e.g. \"ergonomic keyboard\"): ");
        description = scanner.nextLine();
                // Vendor
        System.out.println("Enter name of vendor (e.g. Amazon): ");
        vendor = scanner.nextLine();
                // Payment/debit amount
        System.out.println("Enter " + style.BRIGHT_RED + "payment/debit" + style.END_COLOR + " amount: ");
        amount = -1 * scanner.nextFloat(); // Multiply inputted amount by -1 to get negative value (debit)
        System.out.println();
            // Save info to transactions
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            // Output transaction to transactions file
        writeToFile(transaction);

        System.out.println("\n" + style.ITALIC + style.BOLD + "Auto-redirecting to Home menu..." + style.END_ITALIC + style.END_BOLD + "\n\n");
    }

    private static void ledgerMenu(Scanner scanner) { // Creating multiple scanners messes up the application.


        String ledgerMenuSelection;
        do {
            System.out.println(style.BRIGHT_YELLOW + "* * * * * * * *");
            System.out.println("* Ledger Menu *");
            System.out.println("* * * * * * * *" + style.END_COLOR + "\n");
            System.out.println(style.UNDERLINE + "Please select from the following menu options:" + style.END_UNDERLINE);

            System.out.println("\t" + style.BLUE_BACKGROUND + style.BLACK + "Enter A" + style.END_COLOR + " to view "
                    + style.BRIGHT_BLUE + "All Transaction Entries" + style.END_COLOR);
            System.out.println("\t" + style.GREEN_BACKGROUND + style.BLACK + "Enter D" + style.END_COLOR + " to view "
                    + style.BRIGHT_GREEN + "Deposit Entries" + style.END_COLOR);
            System.out.println("\t" + style.RED_BACKGROUND + style.BLACK + "Enter P" + style.END_COLOR + " to view "
                    + style.BRIGHT_RED + "Payment/Debit Entries" + style.END_COLOR);
            System.out.println("\t" + style.MAGENTA_BACKGROUND + style.BLACK + "Enter R" + style.END_COLOR + " for "
                    + style.BRIGHT_MAGENTA + "Reports Menu " + style.END_COLOR);
            System.out.println("\t" + style.WHITE_BACKGROUND + style.BLACK + "Enter H" + style.END_COLOR + " to GO BACK to "
                    + style.BRIGHT_WHITE + "Home Menu " + style.END_COLOR);

            System.out.println("\n" + style.ITALIC + "Enter selection here:" + style.END_ITALIC);
            ledgerMenuSelection = scanner.next();

            String backButton;
            switch (ledgerMenuSelection.toUpperCase()) { // Making every input uppercase acts as a make-shift ignore-case.
                case "A": // All Transactions

                    transactionList.displayAllTransactions();

                    // "Back" button
                    backButton(scanner);
                    break;
                case "D": // Deposits
                    transactionList.displayDeposits();

                    // "Back" button
                    backButton(scanner);
                    break;
                case "P": // Payments
                    transactionList.displayPayments();

                    // "Back" button
                    backButton(scanner);
                    break;
                case "R": // Reports Menu
                    System.out.println("\n" + style.BRIGHT_MAGENTA + "Reports Menu " + style.END_COLOR + "selected.\n");

                    reportsMenu(scanner);

                    break;
                case "H": // Go back to Home
                    System.out.println("\n" + style.ITALIC + style.BOLD + "Returning to " + style.BRIGHT_WHITE + "Home Menu" +
                            style.END_COLOR + "..." + style.END_ITALIC + style.END_BOLD + "\n\n");
                    return; // Using this because it works better for the SYSTEM to "return" to the last point (this being the previous menu)
                default:
                    System.out.println(style.BOLD + style.RED + style.BLACK_BACKGROUND + "ERROR: Must type either A, D, P, R, or H!"
                            + style.END_BOLD + style.END_COLOR);

                    backButton(scanner);
                    break;
            }
        } while (!ledgerMenuSelection.equalsIgnoreCase("H"));
    }

    private static void reportsMenu(Scanner scanner) {

        String reportsMenuSelection;
        do {
            System.out.println(style.BRIGHT_MAGENTA + "*  *  *  *  *  *");
            System.out.println("* Reports Menu *");
            System.out.println("*  *  *  *  *  *" + style.END_COLOR + "\n");

            System.out.println(style.UNDERLINE + "Please select from the following menu options:" + style.END_UNDERLINE);
            System.out.println("\t" + style.WHITE_BACKGROUND + style.BLACK + "Enter 1" + style.END_COLOR + " to view transaction entries from "
                    + style.BRIGHT_WHITE + "Month to Date" + style.END_COLOR);
            System.out.println("\t" + style.WHITE_BACKGROUND + style.BLACK + "Enter 2" + style.END_COLOR + " to view transaction entries from "
                    + style.BRIGHT_WHITE + "Previous Month" + style.END_COLOR);
            System.out.println("\t" + style.WHITE_BACKGROUND + style.BLACK + "Enter 3" + style.END_COLOR + " to view transaction entries from "
                    + style.BRIGHT_WHITE + "Year to Date" + style.END_COLOR);
            System.out.println("\t" + style.WHITE_BACKGROUND + style.BLACK + "Enter 4" + style.END_COLOR + " to view transaction entries from "
                    + style.BRIGHT_WHITE + "Previous Year" + style.END_COLOR);
            System.out.println("\t" + style.BLACK_BACKGROUND + style.BRIGHT_WHITE + "Enter 5" + style.END_COLOR + " to " + style.BLACK + "Search by Vendor");
            System.out.println("\t" + style.YELLOW_BACKGROUND + style.BRIGHT_WHITE + "Enter 0" + style.END_COLOR + " to GO BACK to "
                    + style.BRIGHT_YELLOW + "Ledger Menu " + style.END_COLOR);

            System.out.println("\n" + style.ITALIC + "Enter selection here:" + style.END_ITALIC);
            reportsMenuSelection = scanner.next();

            String backButton;
            // Switch statement
            switch (reportsMenuSelection) {
                case "1": // Month to Date
                    System.out.println("\n" + style.BOLD + style.WHITE_BACKGROUND + style.BLACK +
                            "                            Month To Date Report                            " + style.END_BOLD + style.END_COLOR);
                    System.out.println("                             -- Transactions --                             \n");
                    System.out.println(style.ITALIC + "(Newest - Oldest transaction)" + style.END_ITALIC);
                    System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                    transactionList.monthToDate();

                    backButton(scanner);
                    break;
                case "2": // Previous Month
                    System.out.println("\n" + style.BOLD + style.WHITE_BACKGROUND + style.BLACK +
                            "                           Previous Month Report                          " + style.END_BOLD + style.END_COLOR);
                    System.out.println("                             -- Transactions --                             \n");
                    System.out.println(style.ITALIC + "(Newest - Oldest transaction)" + style.END_ITALIC);
                    System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                    transactionList.previousMonth();

                    backButton(scanner);
                    break;
                case "3": // Year to Date
                    System.out.println("\n" + style.BOLD + style.WHITE_BACKGROUND + style.BLACK +
                            "                            Year to Date Report                          " + style.END_BOLD + style.END_COLOR);
                    System.out.println("                             -- Transactions --                             \n");
                    System.out.println(style.ITALIC + "(Newest - Oldest transaction)" + style.END_ITALIC);
                    System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                    transactionList.yearToDate();

                    backButton(scanner);
                    break;
                case "4": // Previous Year
                    System.out.println("\n" + style.BOLD + style.WHITE_BACKGROUND + style.BLACK +
                            "                            Previous Year Report                          " + style.END_BOLD + style.END_COLOR);
                    System.out.println("                             -- Transactions --                             \n");
                    System.out.println(style.ITALIC + "(Newest - Oldest transaction)" + style.END_ITALIC);
                    System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                    transactionList.previousYear();

                    backButton(scanner);
                    break;
                case "5": // Search by Vendor
                    String vendor;

                    System.out.println("Enter the name of a vendor to see transactions registered to them.");
                    System.out.println("Enter here:");
                    vendor = scanner.next();
                    vendor += scanner.nextLine(); // Had to do this because .next() wasn't reading the full line and caused whitespace/skipping issue.

                    transactionList.searchByVendor(vendor); // Moved page header to inside the searchByVendor() method.

                    backButton(scanner);
                    break;
                case "0": // Go back to Ledger
                    System.out.println("\n" + style.ITALIC + style.BOLD + "Returning to " + style.BRIGHT_YELLOW + "Ledger Menu" +
                            style.END_COLOR + "..." + style.END_ITALIC + style.END_BOLD + "\n\n");
                    return; // Using this because it works better for the SYSTEM to "return" to the last point (this being the previous menu)
                default:
                    System.out.println(style.BOLD + style.RED + style.BLACK_BACKGROUND + "ERROR: Must type either 1, 2, 3, 4, 5, or 0!"
                            + style.END_BOLD + style.END_COLOR);

                    backButton(scanner);
                    break;
            }
        } while (!reportsMenuSelection.equals("0"));
    }

    private static void backButton(Scanner scanner) {
        String backButton;

        do {
            System.out.println("\nEnter " + style.WHITE_BACKGROUND + style.BLACK + "B" + style.END_COLOR + " to leave page:");
            backButton = scanner.next();
            if (backButton.equalsIgnoreCase("B")) {
                break;
            } else {
                System.out.println("\nERROR: Must type " + style.WHITE_BACKGROUND + style.BLACK + "B" + style.END_COLOR +
                        " and press 'Enter' to leave page.");
            }
        } while (!backButton.equalsIgnoreCase("B"));

    }

}