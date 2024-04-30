package com.ps;

import java.time.LocalDate;
import java.util.ArrayList;

/*
This class keeps track of all transactions.
 */

// No Getters nor Setters
public class TransactionStorage {
    private Transaction transaction; // A single transaction
    private ArrayList<Transaction> transactionList = new ArrayList<>(); // List of all transactions

    public TransactionStorage() {
        this.transactionList = new ArrayList<>();;
    }

    // Adds a transaction to an ArrayList of transactions
    public void addTransactionToList(Transaction transaction) {
        transactionList.add(transaction);
    }

// Ledger/Report Menu filtering methods (from the newest transaction to oldest)
    // Displays a list of ALL transactions within the transactionList ArrayList
    public void displayAllTransactions() {
        System.out.println("\n                            All Transaction Entries                            ");
        System.out.println("                              -- Transactions --                              \n");
        System.out.println("(Newest - Oldest transaction)");
        System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");
        for (int i = transactionList.size()-1; i > -1; i--) { // From newest to oldest transaction
            System.out.println(transactionList.get(i));
        }
    }

    // Displays a list of all DEPOSITS within the transactionList ArrayList
    public void displayDeposits() {
        boolean anyDeposits = false;

        System.out.println("\n                               Deposit Entries                                ");
        System.out.println("                              -- Transactions --                              \n");
        System.out.println("(Newest - Oldest transaction)");
        System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");
        for (int i = transactionList.size()-1; i > -1; i--) { // From newest to oldest transaction
            float transactionAmount = transactionList.get(i).getAmount();
            float absTransactionAmount = Math.abs(transactionAmount); // Absolute value of transaction amount
            if (transactionAmount == absTransactionAmount) {
                System.out.println(transactionList.get(i));

                anyDeposits = true;
            }
        }
        if (!anyDeposits) { // If there are NO deposits, THIS will happen.
            System.out.println("No deposits have been made.");
        }
    }

    // Displays a list of all PAYMENTS (DEBITS) within the transactionList ArrayList
    public void displayPayments() {
        boolean anyPayments = false;

        System.out.println("\n                             Payment/Debit Entries                                ");
        System.out.println("                              -- Transactions --                              \n");
        System.out.println("(Newest - Oldest transaction)");
        System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");
        for (int i = transactionList.size()-1; i > -1; i--) { // From newest to oldest transaction
            float transactionAmount = transactionList.get(i).getAmount();
            float absTransactionAmount = Math.abs(transactionAmount); // Absolute value of transaction amount
            if (transactionAmount != absTransactionAmount) {
                System.out.println(transactionList.get(i));

                anyPayments = true;
            }
        }
        if (!anyPayments) { // If there are NO payments, THIS will happen.
            System.out.println("No payments have been made.");
        }
    }

    // Displays transactions from the 1st day of the current MONTH to CURRENT DATE (e.g. April 1st to NOW)
    public void monthToDate() {
        boolean transactionInMonth = false;

        System.out.println("\n                            Month To Date Report                            ");
        System.out.println("                             -- Transactions --                             \n");
        System.out.println("(Newest - Oldest transaction)");
        System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

        for (int i = transactionList.size()-1; i > -1; i--) { // From newest to oldest transaction
            LocalDate transactionDate = transactionList.get(i).getDate();
            LocalDate dateNow = LocalDate.now();
            if (transactionDate.getMonthValue() == dateNow.getMonthValue()
                    && transactionDate.getYear() == dateNow.getYear()) {
                System.out.println(transactionList.get(i));

                transactionInMonth = true;
            }
        }
        if (!transactionInMonth) { // If there are NO transactions made this current month, THIS will happen.
            System.out.println("No transactions have been made this month.");
        }
    }

    // Displays all transactions from the previous month (e.g. March 1st - March 31st)
    public void previousMonth() {
        boolean transactionInMonth = false;
        int monthInInteger;

        System.out.println("\n                          Previous Month Report                          ");
        System.out.println("                             -- Transactions --                             \n");
        System.out.println("(Newest - Oldest transaction)");
        System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

        // Find previous month
        LocalDate dateNow = LocalDate.now(); // Current date
        int monthNowInteger = dateNow.getMonthValue(); // Current month in numbers (= 4)
        int previousMonthInteger = 0;
        if (monthNowInteger > 1) { // If current month is AFTER January
            previousMonthInteger = monthNowInteger-1;
        } else if (monthNowInteger == 1) { // If current month is January
            previousMonthInteger = 12; // 12 = December
        }
            // Figure out how many days are in that month

                // Iterate through each DAY
                    // Prints transactions from newest to oldest



        /*for (int i = transactionList.size()-1; i > -1; i--) { // From newest to oldest transaction
            LocalDate transactionDate = transactionList.get(i).getDate();
            LocalDate dateNow = LocalDate.now();

            int transactionMonthInteger = transactionDate.getMonthValue(); // = 4
            int dateNowInteger = dateNow.getMonthValue(); // = 4
            // Nested-for loop: As long as 'j' is LESS THAN current date but GREATER THAN '0', DECREMENT
            for (int j = dateNowInteger-1; j > 0; j--) { // j initialized as 3
                if () {
                    System.out.println(transactionList.get(i));

                    transactionInMonth = true;
                }
            }

        }*/
        if (!transactionInMonth) { // If there are NO transactions made this current month, THIS will happen.
            System.out.println("No transactions have been made in the previous month.");
        }
    }

    // Displays transactions from the 1st day of the current YEAR to CURRENT DATE (e.g. January 1st to NOW)
    public void yearToDate() {

    }

    // Displays all transactions from the previous YEAR (e.g. January 1st of Previous year to Dec 31st)
    public void previousYear() {

    }

    // Display all transactions registered to a specific vendor (DON'T use spaces n stuff, keep it simple)
    public void searchByVendor() {

    }


}
