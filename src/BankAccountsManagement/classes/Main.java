package BankAccountsManagement.classes;

public class Main {
    public static void main(String[] args) {
        Bank sberbank = new Bank("Sberbank");

        BankAccount andrei = sberbank.createAccount("Andrei", 100);
        BankAccount gregory = sberbank.createAccount("Gregory", 159.5);
        BankAccount vyacheslav = sberbank.createAccount("Vyacheslav", 6.9);

        System.out.println(sberbank);
        sberbank.listAllAccounts();

        andrei.transfer(gregory, 10);
        System.out.println("\n" + andrei + "\n" + gregory + "\n");

        andrei.withdraw(andrei.getBalance()+10);
        vyacheslav.deposit(-10);
        andrei.withdraw(andrei.getBalance());
        andrei.transfer(vyacheslav, 10);

        sberbank.transfer(gregory.getAccountNumber(), andrei.getAccountNumber(), 20);
    }
}
