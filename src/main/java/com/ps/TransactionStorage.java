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
        this.transactionList = new ArrayList<>();
    }

    // Adds a transaction to an ArrayList of transactions
    public void addTransactionToList(Transaction transaction) {
        transactionList.add(transaction);
    }

    // Clears every transaction from the system/ArrayList
    public void clearTransactionList() {
        transactionList.clear();
    }

// Ledger/Report Menu filtering methods (from the newest transaction to oldest)
    // Displays a list of ALL transactions within the transactionList ArrayList
    public void displayAllTransactions() {
        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            System.out.println("\n                            All Transaction Entries                            ");
            System.out.println("                              -- Transactions --                              \n");
            System.out.println("(Newest - Oldest transaction)");
            System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                System.out.println(transactionList.get(i));
            }
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }

    }

    // Displays a list of all DEPOSITS within the transactionList ArrayList
    public void displayDeposits() {
        boolean anyDeposits = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            System.out.println("\n                               Deposit Entries                                ");
            System.out.println("                              -- Transactions --                              \n");
            System.out.println("(Newest - Oldest transaction)");
            System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
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
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }
    }

    // Displays a list of all PAYMENTS (DEBITS) within the transactionList ArrayList
    public void displayPayments() {
        boolean anyPayments = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            System.out.println("\n                             Payment/Debit Entries                                ");
            System.out.println("                              -- Transactions --                              \n");
            System.out.println("(Newest - Oldest transaction)");
            System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
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
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }
    }

    // Displays transactions from the 1st day of the current MONTH to CURRENT DATE (e.g. April 1st to NOW)
    public void monthToDate() {
        boolean transactionInMonth = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                Transaction transaction = transactionList.get(i); // A single transaction
                LocalDate transactionDate = transaction.getDate(); // Date of a transaction
                LocalDate dateNow = LocalDate.now();

                int transactionMonth = transactionDate.getMonthValue(); // Month of transaction in integers
                int transactionYear = transactionDate.getYear(); // Year of transaction
                int monthNow = dateNow.getMonthValue(); // Current month in integers
                int yearNow = dateNow.getYear(); // Current year

                if (transactionMonth == monthNow && transactionYear == yearNow) {
                    System.out.println(transactionList.get(i));

                    transactionInMonth = true;
                }
            }
            if (!transactionInMonth) { // If there are NO transactions made this current month, THIS will happen.
                System.out.println("No transactions have been made this month.");
            }
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }
    }

    // Displays all transactions from the previous month (e.g. March 1st - March 31st)
    public void previousMonth() {
        boolean transactionInPreviousMonth = false;

//        int monthNowInteger;

        // Find previous month
        /*LocalDate dateNow = LocalDate.now(); // Current date
        int monthNowInteger = dateNow.getMonthValue(); // Current month in numbers (= 4)
        int previousMonthInteger = 0;
        if (monthNowInteger > 1) { // If current month is AFTER January
            previousMonthInteger = monthNowInteger-1;
        } else if (monthNowInteger == 1) { // If current month is January
            previousMonthInteger = 12; // 12 = December
        }
            // Figure out how many days are in that month

                // Iterate through each DAY
                    // Prints transactions from newest to oldest*/

        // Part1: Display transactions from previous month
//        System.out.println("----------------------------");
//        System.out.println("Previous month transactions");
//        System.out.println("----------------------------\n");
        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                Transaction transaction = transactionList.get(i); // A single transaction
                LocalDate transactionDate = transaction.getDate(); // Date of a transaction
                LocalDate dateNow = LocalDate.now(); // Current date

                int transactionMonth = transactionDate.getMonthValue(); // Month of transaction in integers
                int transactionYear = transactionDate.getYear(); // Year of transaction
                int monthNow = dateNow.getMonthValue(); // Current month in integers
                int yearNow = dateNow.getYear(); // Current year
                int previousMonth = -1; // Previous month in integers (set to a default number).
                int previousYear = yearNow - 1; // Previous year

                if (monthNow > 1) { // If current month is AFTER January
                    previousMonth = monthNow - 1;
                    if (transactionMonth == previousMonth && transactionYear == yearNow) {
                        System.out.println(transactionList.get(i));

                        transactionInPreviousMonth = true;
                    }
                } else if (monthNow == 1) { // If current month is January
                    previousMonth = 12; // 12 = December
                    if (transactionMonth == previousMonth && transactionYear == previousYear) {
                        System.out.println(transactionList.get(i));

                        transactionInPreviousMonth = true;
                    }
                }
            }
            if (!transactionInPreviousMonth) { // If there are NO transactions made in the previous month, THIS will happen.
                System.out.println("No transactions have been made in the previous month.");
            }
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }
    }

    // Displays transactions from the 1st day of the current YEAR to CURRENT DATE (e.g. January 1st to NOW)
    public void yearToDate() {
        boolean transactionInCurrentYear = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                Transaction transaction = transactionList.get(i); // A single transaction
                LocalDate transactionDate = transaction.getDate(); // Date of a transaction
                LocalDate dateNow = LocalDate.now(); // Current date

                int transactionYear = transactionDate.getYear(); // Year of transaction
                int yearNow = dateNow.getYear(); // Current year

                if (transactionYear == yearNow) {
                    System.out.println(transactionList.get(i));

                    transactionInCurrentYear = true;
                }
            }
            if (!transactionInCurrentYear) {
                System.out.println("No transactions have been made this year.");
            }
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }
    }

    // Displays all transactions from the previous YEAR (e.g. January 1st of Previous year to Dec 31st)
    public void previousYear() {
        boolean transactionInPreviousYear = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                Transaction transaction = transactionList.get(i); // A single transaction
                LocalDate transactionDate = transaction.getDate(); // Date of a transaction
                LocalDate dateNow = LocalDate.now(); // Current date

                int transactionYear = transactionDate.getYear(); // Year of transaction
                int yearNow = dateNow.getYear(); // Current year
                int previousYear = yearNow - 1; // Previous year

                if (transactionYear == previousYear) {
                    System.out.println(transactionList.get(i));

                    transactionInPreviousYear = true;
                }
            }
            if (!transactionInPreviousYear) { // If there are NO transactions made in the previous year, THIS will happen.
                System.out.println("No transactions have been made in the previous year.");
            }
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }
    }

    // Display all transactions registered to a specific vendor (DON'T use spaces n stuff, keep it simple)
    public void searchByVendor(String inputVendor) {
        boolean vendorMadeTransaction = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                Transaction transaction = transactionList.get(i);
                String transactionVendor = transaction.getVendor();

                if (transactionVendor.equalsIgnoreCase(inputVendor)) {
                    if (i == transactionList.size() - 1) { // Want this to only print ONCE
                        System.out.println("\n                            Search by Vendor Report                          ");
                        System.out.println("                             -- Transactions --                             \n");
                        System.out.println("(Newest - Oldest transaction)");
                        System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");
                    }
                    System.out.println(transaction);

                    vendorMadeTransaction = true;
                }
            }
            if (!vendorMadeTransaction) { // If there are NO transactions made by the searched vendor from user input, THIS will happen.
                System.out.println("\nThe vendor entered is not registered in our system.");
            }
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }
    }

// Challenge/Bonus tasks
    // Custom Search


}
