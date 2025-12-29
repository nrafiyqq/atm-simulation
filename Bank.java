import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Uses Java Collections API (HashMap, ArrayList)
public class Bank {
    private HashMap<String, Account> accounts;
    private HashMap<String, List<String>> transactionHistory;
    
    public Bank() {
        accounts = new HashMap<>();
        transactionHistory = new HashMap<>();
        initializeSampleAccounts();
    }
    
    private void initializeSampleAccounts() {
        // Add sample accounts
        Account acc1 = new SavingsAccount("1001", "John Doe", "1234", 5000.0);
        Account acc2 = new CurrentAccount("2001", "Jane Smith", "5678", 10000.0, 2000.0);
        Account acc3 = new SavingsAccount("1002", "Bob Wilson", "9999", 15000.0);
        
        accounts.put("1001", acc1);
        accounts.put("2001", acc2);
        accounts.put("1002", acc3);
        
        transactionHistory.put("1001", new ArrayList<>());
        transactionHistory.put("2001", new ArrayList<>());
        transactionHistory.put("1002", new ArrayList<>());
    }
    
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    public boolean validateAccount(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            return account.validatePin(pin);
        }
        return false;
    }
    
    public void addTransaction(String accountNumber, String transactionDetail) {
        if (transactionHistory.containsKey(accountNumber)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);
            transactionHistory.get(accountNumber).add(timestamp + " - " + transactionDetail);
        }
    }
    
    public List<String> getTransactionHistory(String accountNumber) {
        return transactionHistory.getOrDefault(accountNumber, new ArrayList<>());
    }
}