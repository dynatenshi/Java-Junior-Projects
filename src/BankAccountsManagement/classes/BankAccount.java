package BankAccountsManagement.classes;

import BankAccountsManagement.exceptions.InsufficientFundsException;
import BankAccountsManagement.exceptions.InvalidAmountException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BankAccount {
    private final String accountNumber;
    private final String owner;
    private double balance;
    private final LocalDateTime creationDate;

    public BankAccount(String accountNumber, String owner) {
        this(accountNumber, owner, 0);
    }

    public BankAccount(String accountNumber, String owner, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance < 0 ? 0 : balance;
        this.creationDate = LocalDateTime.now();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public boolean deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Error: Amount must be greater than 0");

        balance+= amount;
        System.out.printf("Deposited to %s: %.2f | Balance : %.2f%n", getAccountNumber(), amount, getBalance());
        return true;
    }

    public boolean withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) throw new InsufficientFundsException("Error: Not enough balance");

        balance-= amount;
        System.out.printf("Withdraw from %s: %.2f | Balance : %.2f%n", getAccountNumber(), amount, getBalance());
        return true;
    }

    public boolean transfer(BankAccount toAccount, double amount) {
        if (withdraw(amount)) {
            return toAccount.deposit(amount);
        }
        System.out.println("Transfer failed");
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber(), getOwner(), getBalance(), getCreationDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BankAccount acc)) return false;

        return Objects.equals(getAccountNumber(), acc.getAccountNumber());
    }

    @Override
    public String toString() {
        return String.format("Account number: %s | Owner: %s | Balance: %.2f | Created at: %s",
                getAccountNumber(), getOwner(), getBalance(),
                getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }

}
