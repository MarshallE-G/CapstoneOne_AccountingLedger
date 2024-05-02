package com.ps;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Static global variable for transactions
    static TransactionStorage transactionList = new TransactionStorage();
    static ArrayList<Integer> numOfFileLines = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    // ANSI code
    static final String GREEN = "\u001B[32m";
    static final String RED = "\u001B[31m";
    static final String END = "\u001B[0m";

    public static void main(String[] args) {
        /*
        2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50 <---- This is a payment (notice number is negative; subtracting)
        2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00 <---- This is a deposit. ("Joe," the vendor, is being paid)
        Negative --> Payment (debits)
        Positive --> Deposit (credits)
         */
        Scanner scanner = new Scanner(System.in);


        readFromFile();
        readAndOverwrite(transactionList);
        //length = how many elements are in the array.

        String menuSelection; // For Home Menu options
        do {
            homeMenu(); // ONLY displays Home menu options

            System.out.println("\nEnter selection here:");
            menuSelection = scanner.next();

            switch (menuSelection.toUpperCase()) { // Making every input uppercase acts as a make-shift ignore-case.
                case "D":
                    System.out.println("\nAdd deposit selected.\n");

                    addDeposit();
                    addNewDataToList();
                    break;
                case "P":
                    System.out.println("\nMake a payment (debit) selected.\n");

                    makePayment();
                    addNewDataToList();
                    break;
                case "L":
                    System.out.println("\nLedger Menu selected.\n");

                    ledgerMenu(scanner);
                    break;
                case "X":
                    break;
                default:
                    System.out.println("ERROR: Must type either D, P, L, or X!");

                    String backButton;
                    // "Back" button
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equals("B"));
                    break;
            }
        } while (!menuSelection.equalsIgnoreCase("X"));
        scanner.close();
    }

    public static void readAndOverwrite(TransactionStorage transactionList) {
        try {
            BufferedReader bufReader3 = new BufferedReader(new FileReader("transactions.txt")); // bufReader2 is in isFileEmpty() method
            BufferedWriter bufWriter2 = new BufferedWriter(new FileWriter("transactions.txt", true));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            Transaction transaction;

            String line;
            int lineNum = 1;
            while ((line = bufReader3.readLine()) != null) {
                if (line.isBlank() && !transactionList.isTransactionListEmpty()
                        && transactionList.transactionListSize() >= lineNum) { // If the ArrayList is NOT empty
                    transaction = transactionList.getTransaction(lineNum-1);
                    String formattedDate = transaction.getDate().format(dateFormatter);
                    String formattedTime = transaction.getTime().format(timeFormatter);
                    line = line.replaceAll(line,
                            formattedDate + "|" +
                                    formattedTime + "|" +
                                    String.format("%s|%s|%.2f\n",
                                            transaction.getDescription(),
                                            transaction.getVendor(),
                                            transaction.getAmount()
                                    ));
                    bufWriter2.write(line);
                }
                lineNum++;
            }
            bufReader3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // readFromFile method to read from File
    public static void readFromFile() {
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
    public static void addNewDataToList() {
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

    // writeToFile method to write to File
    public static void writeToFile(Transaction transaction) {
        // Format Date and Time

        try {
            // Have to append data to the end of the .txt file so that it doesn't overwrite the previous output(s) to the file.
            // The append argument is set to "true" for the FileWriter.
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.txt", true));
//            BufferedWriter bufWriterNoAppend = new BufferedWriter(new FileWriter("transactions.txt"));
//            BufferedReader bufReader = new BufferedReader(new FileReader("transactions.txt"));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = transaction.getDate().format(dateFormatter);
            String formattedTime = transaction.getTime().format(timeFormatter);

//            String line;
//            line = bufReader.readLine();
//            if (line != null) {
//                if (line.isBlank()) {
//                    line = line.trim();
//                }
//            }


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
                System.out.printf("Added transaction: %s | %s | %s | %s | $%.2f\n", // For when you add a transaction (credit/debit)
                        formattedDate,
                        formattedTime,
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            } else if (transaction.getAmount() != Math.abs(transaction.getAmount())) { // If debit: prints negative sign
                System.out.printf("Added transaction: %s | %s | %s | %s | -$%.2f\n", // For when you add a transaction (credit/debit)
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
            BufferedReader bufReader2 = new BufferedReader(new FileReader("transactions.txt"));

            String line;
            while ((line = bufReader2.readLine()) != null) {
                if (!line.isBlank()) {
                    bufReader2.close();

                    return false; // If file is NOT empty, returns "false"
                } else {
                    bufReader2.close();

                    return true; // If file IS empty, returns "true"
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Just added this cuz I had to
    }

// Static methods for Menus

    // Home Menu display
    public static void homeMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("* * * * * * *");
        System.out.println("* Home Menu *");
        System.out.println("* * * * * * *\n");
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

    // Add Deposit option
    public static void addDeposit() {
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

        System.out.println("\nAuto-redirecting to Home menu...\n\n");
    }

    // Make a Payment option
    public static void makePayment() {
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

    // Ledger Menu option
    public static void ledgerMenu(Scanner scanner) { // Creating multiple scanners messes up the application.


        String ledgerMenuSelection;
        // Do-while loop
        do {
            // **Ledger Menu**
            System.out.println("* * * * * * * *");
            System.out.println("* Ledger Menu *");
            System.out.println("* * * * * * * * \n");

            // Option A: All
            System.out.println("Enter A to view All Transaction Entries");
            // Option D: Deposits
            System.out.println("Enter D to view Deposit Entries");
            // Option P: Payments
            System.out.println("Enter P to view Payment/Debit Entries");
            // Option R: ***Report Menu*** (display transactions based on filter options)
            System.out.println("Enter R for Reports Menu");
            // Option H: Home
            System.out.println("Enter H to GO BACK to Home Menu");

            System.out.println("\nEnter selection here: ");
            ledgerMenuSelection = scanner.next();

            String backButton;
            // Switch statement
            switch (ledgerMenuSelection.toUpperCase()) { // Making every input uppercase acts as a make-shift ignore-case.
                case "A": // All Transactions

                    transactionList.displayAllTransactions();

                    // "Back" button
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equalsIgnoreCase("B"));
                    break;
                case "D": // Deposits
                    transactionList.displayDeposits();

                    // "Back" button
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equalsIgnoreCase("B"));
                    break;
                case "P": // Payments
                    transactionList.displayPayments();

                    // "Back" button
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equalsIgnoreCase("B"));
                    break;
                case "R": // Reports Menu

                    reportsMenu(scanner);

                    break;
                case "H": // Go back to Home
                    System.out.println("\nReturning to Home Menu...\n");
                    return; // Using this because it works better for the SYSTEM to "return" to the last point (this being the previous menu)
                default:
                    System.out.println("ERROR: Must type either A, D, P, R, or H!");
                    break;
            }
        } while (!ledgerMenuSelection.equalsIgnoreCase("H"));
        // End of Ledger Menu Do-while loop
    }

    // Reports Menu option
    public static void reportsMenu(Scanner scanner) {

        String reportsMenuSelection;
        // Do-while loop
        do {
            // ***Report Menu*** (display transactions based on filter options)
            System.out.println("*  *  *  *  *  *");
            System.out.println("* Reports Menu *");
            System.out.println("*  *  *  *  *  *\n");

            // Option 1) Month To Date - first of the month that you're in (e.g. April 1st to NOW)
            System.out.println("Enter 1 to view transaction entries from Month to Date");
            // Option 2) Previous Month - Everything in the previous month (e.g. March)
            System.out.println("Enter 2 to view transaction entries from Previous Month");
            // Option 3) Year To Date - January 1st to NOW
            System.out.println("Enter 3 to view transaction entries from Year to Date");
            // Option 4) Previous Year - January 1st of Previous year to NOW
            System.out.println("Enter 4 to view transaction entries from Previous Year");
            // Option 5) Search by Vendor - Ask for vendor's name. (DON'T use spaces n stuff, keep it simple)
            System.out.println("Enter 5 to Search by Vendor");
            // Option 0) Back
            System.out.println("Enter 0 to GO BACK to Ledger Menu");

            System.out.println("\nEnter selection here:");
            reportsMenuSelection = scanner.next();

            String backButton;
            // Switch statement
            switch (reportsMenuSelection) {
                case "1": // Month to Date
                    System.out.println("\n                            Month To Date Report                            ");
                    System.out.println("                             -- Transactions --                             \n");
                    System.out.println("(Newest - Oldest transaction)");
                    System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                    transactionList.monthToDate();

                    // Do this to leave this report page
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equalsIgnoreCase("B"));
                    break;
                case "2": // Previous Month
                    System.out.println("\n                           Previous Month Report                          ");
                    System.out.println("                             -- Transactions --                             \n");
                    System.out.println("(Newest - Oldest transaction)");
                    System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                    transactionList.previousMonth();

                    // Do this to leave this report page
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equalsIgnoreCase("B"));
                    break;
                case "3": // Year to Date
                    System.out.println("\n                            Year to Date Report                          ");
                    System.out.println("                             -- Transactions --                             \n");
                    System.out.println("(Newest - Oldest transaction)");
                    System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                    transactionList.yearToDate();

                    // Do this to leave this report page
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equalsIgnoreCase("B"));
                    break;
                case "4": // Previous Year
                    System.out.println("\n                            Previous Year Report                          ");
                    System.out.println("                             -- Transactions --                             \n");
                    System.out.println("(Newest - Oldest transaction)");
                    System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                    transactionList.previousYear();

                    // Do this to leave this report page
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equalsIgnoreCase("B"));
                    break;
                case "5": // Search by Vendor
                    String vendor;

                    System.out.println("Enter the name of a vendor to see transactions registered to them.");
                    System.out.println("Enter here:");
                    vendor = scanner.next();

                    // Moved page header to inside the searchByVendor() method.

                    transactionList.searchByVendor(vendor);

                    // Do this to leave this report page
                    do {
                        System.out.println("\nEnter B to leave page:");
                        backButton = scanner.next();
                        if (backButton.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            System.out.println("\nERROR: Must type B and press 'Enter' to leave page.\n");
                        }
                    } while (!backButton.equalsIgnoreCase("B"));
                    break;
                case "0": // Go back to Ledger
                    System.out.println("\nReturning to Ledger Menu...\n");
                    return; // Using this because it works better for the SYSTEM to "return" to the last point (this being the previous menu)
                default:
                    System.out.println("ERROR: Must type either 1, 2, 3, 4, 5, or 0!");
                    break;
            }
        } while (!reportsMenuSelection.equals("0"));
        // End of Do-while loop
    }

}