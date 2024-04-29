package com.ps;

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

    // Displays a list of all transactions within the transactionList ArrayList
    public void displayTransactionList() {
        System.out.println(transactionList);
    }
}
