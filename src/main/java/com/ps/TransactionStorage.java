package com.ps;

import java.time.LocalDate;
import java.util.ArrayList;

/*
This class keeps track of all transactions.
 */

// No Getters nor Setters
public class TransactionStorage {
    private ArrayList<Transaction> transactionList = new ArrayList<>(); // List of all transactions
    private ColorsAndGrapics style = new ColorsAndGrapics();

    public TransactionStorage() {
        this.transactionList = new ArrayList<>();
    }

    public void addTransactionToList(Transaction transaction) {
        transactionList.add(transaction);
    }

    // Clears every transaction from the transactionList ArrayList
    public void clearTransactionList() {
        transactionList.clear();
    }

// Ledger/Report Menu filtering methods
    public void displayAllTransactions() {
        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            System.out.println("\n" + style.BOLD + style.BRIGHT_BLUE_BACKGROUND + style.BLACK +
                    "                            All Transaction Entries                            " + style.END_BOLD + style.END_COLOR);
            System.out.println("                              -- Transactions --                              \n");
            System.out.println(style.ITALIC + "(Newest - Oldest transaction)" + style.END_ITALIC);
            System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                System.out.println(transactionList.get(i));
            }
        } else { // If the transactions.txt file IS empty, do this...
            System.out.println("\nThere are no transactions in the system.\n");
        }

    }

    public void displayDeposits() {
        boolean anyDeposits = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            System.out.println("\n" + style.BOLD + style.BRIGHT_GREEN_BACKGROUND + style.BLACK +
                    "                               Deposit Entries                                " + style.END_BOLD + style.END_COLOR);
            System.out.println("                              -- Transactions --                              \n");
            System.out.println(style.ITALIC + "(Newest - Oldest transaction)" + style.END_ITALIC);
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

    public void displayPayments() {
        boolean anyPayments = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            System.out.println("\n" + style.BOLD + style.RED_BACKGROUND + style.BLACK +
                    "                             Payment/Debit Entries                                " + style.END_BOLD + style.END_COLOR);
            System.out.println("                              -- Transactions --                              \n");
            System.out.println(style.ITALIC + "(Newest - Oldest transaction)" + style.END_ITALIC);
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

                int transactionMonth = transactionDate.getMonthValue();
                int transactionYear = transactionDate.getYear();
                int monthNow = dateNow.getMonthValue();
                int yearNow = dateNow.getYear();

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

    public void previousMonth() {
        boolean transactionInPreviousMonth = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                Transaction transaction = transactionList.get(i); // A single transaction
                LocalDate transactionDate = transaction.getDate();
                LocalDate dateNow = LocalDate.now();

                int transactionMonth = transactionDate.getMonthValue();
                int transactionYear = transactionDate.getYear();
                int monthNow = dateNow.getMonthValue();
                int yearNow = dateNow.getYear();
                int previousMonth = -1; // Previous month in integers (set to a default number).
                int previousYear = yearNow - 1;

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
                LocalDate dateNow = LocalDate.now();

                int transactionYear = transactionDate.getYear();
                int yearNow = dateNow.getYear();

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

    public void previousYear() {
        boolean transactionInPreviousYear = false;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                Transaction transaction = transactionList.get(i); // A single transaction
                LocalDate transactionDate = transaction.getDate();
                LocalDate dateNow = LocalDate.now();

                int transactionYear = transactionDate.getYear();
                int yearNow = dateNow.getYear();
                int previousYear = yearNow - 1;

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

    public void searchByVendor(String inputVendor) {
        boolean vendorMadeTransaction = false;
        boolean firstTransaction = true;

        if (!Main.isFileEmpty()) { // If the transactions.txt file is NOT empty, do this...
            for (int i = transactionList.size() - 1; i > -1; i--) { // From newest to oldest transaction
                Transaction transaction = transactionList.get(i);
                String transactionVendor = transaction.getVendor();

                if (transactionVendor.equalsIgnoreCase(inputVendor)) {
                    if (firstTransaction) { // Want this to only print ONCE
                        System.out.println("\n" + style.BOLD + style.BLACK_BACKGROUND + style.BRIGHT_WHITE +
                                "                            Search by Vendor Report                          " + style.END_BOLD + style.END_COLOR);
                        System.out.println("                             -- Transactions --                             \n");
                        System.out.println(style.ITALIC + "(Newest - Oldest transaction)" + style.END_ITALIC);
                        System.out.println("Format: \"Date | Time | Description | Vendor | Amount\"\n");

                        firstTransaction = false;
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

}
