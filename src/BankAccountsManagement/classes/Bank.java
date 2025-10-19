package BankAccountsManagement.classes;

import BankAccountsManagement.exceptions.AccountNotFoundException;

import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;

public class Bank {
    private final int MAX_ID = 99999;
    private final String bankName;
    private final TreeMap<String, BankAccount> accounts;

   public Bank(String bankName) {
       this(bankName, new TreeMap<>());
   }

   public Bank(String bankName, Map<String, BankAccount> accounts) {
       this.bankName = bankName;
       this.accounts = new TreeMap<>(accounts);
   }

   public String getBankName() {
       return bankName;
   }

   public BankAccount createAccount(String ownerName, double initialBalance) {
       if (accounts.size() == MAX_ID+1) {
           System.out.println("No more accounts can be created");
           return null;
       }

       int randomNum;
       String key;
       do {
           randomNum = (int)Math.round(Math.random()*MAX_ID);
           key = String.format("ACC-%d", randomNum);
       } while (accounts.get(key) != null);

       BankAccount account = new BankAccount(key, ownerName, initialBalance);
       accounts.put(key, account);
       return account;
   }

   public BankAccount findAccount(String accountNumber) throws AccountNotFoundException {
       BankAccount account = accounts.get(accountNumber);
       if (account == null) throw new AccountNotFoundException("Error: Account not found");
       return account;
   }

   public BankAccount[] findAccountsByOwner(String owner) {
       ArrayList<BankAccount> accountList = new ArrayList<>();
       for (BankAccount account : accounts.values()) {
           if (account.getOwner().equals(owner)) {
               accountList.add(account);
           }
       }
       return accountList.toArray(new BankAccount[0]);
   }

   public boolean closeAccount(String accountNumber) throws AccountNotFoundException {
       if (accounts.remove(accountNumber) == null) throw new AccountNotFoundException("Error: Account not found");

       return true;
   }

   public double getTotalBalance() {
       double total = 0;
       for (BankAccount account : accounts.values()) {
            total+= account.getBalance();
       }
       return total;
   }

   public int getAccountCount() {
       return accounts.size();
   }

   public boolean transfer(String fromAccount, String toAccount, double amount) {
       BankAccount from = findAccount(fromAccount);
       BankAccount to = findAccount(toAccount);

       return from.transfer(to, amount);
   }

   public void listAllAccounts() {
       for (BankAccount account : accounts.values()) {
           System.out.println(account);
       }
   }

   @Override
   public int hashCode() {
       int result = getBankName().hashCode();

       for (BankAccount acc : accounts.values()) {
           result += acc.hashCode();
       }

       return result;
   }

   @Override
   public boolean equals(Object obj) {
       if (this == obj) return true;
       if (!(obj instanceof Bank bank)) return false;

       return (getBankName().equals(bank.getBankName()) && accounts.equals(bank.accounts));
   }

   @Override
   public String toString() {
       return String.format("Bank name: %s | Number of accounts: %d | Total balance: %.2f",
               getBankName(), getAccountCount(), getTotalBalance());
   }
}
