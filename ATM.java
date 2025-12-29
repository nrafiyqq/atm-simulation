// ATM.java
import java.util.ArrayList;
import java.util.List;

public class ATM {
    private Bank bank;
    private Account currentAccount;
    
    public ATM(Bank bank) {
        this.bank = bank;
        this.currentAccount = null;
    }
    
    public boolean login(String accountNumber, String pin) {
        if (bank.validateAccount(accountNumber, pin)) {
            currentAccount = bank.getAccount(accountNumber);
            return true;
        }
        return false;
    }
    
    public void logout() {
        currentAccount = null;
    }
    
    public boolean isLoggedIn() {
        return currentAccount != null;
    }
    
    public Account getCurrentAccount() {
        return currentAccount;
    }
    
    // Polymorphism in action - accepts any Transaction type
    public boolean performTransaction(Transaction transaction) {
        if (currentAccount == null) {
            return false;
        }
        
        boolean success = transaction.execute();
        bank.addTransaction(currentAccount.getAccountNumber(), transaction.getTransactionDetails());
        return success;
    }
    
    public List<String> getTransactionHistory() {
        if (currentAccount != null) {
            return bank.getTransactionHistory(currentAccount.getAccountNumber());
        }
        return new ArrayList<>();
    }
}