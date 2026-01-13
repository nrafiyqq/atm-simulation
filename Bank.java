// Bank.java 
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bank {
    private HashMap<String, Account> accounts;
    private HashMap<String, List<String>> transactionHistory;
    
    public Bank() {
        // Load data from files instead of creating in memory
        try {
            accounts = FileManager.loadAccounts();
            transactionHistory = FileManager.loadTransactionHistory();
            
            // Initialize empty history for accounts that don't have one
            for (String accountNumber : accounts.keySet()) {
                if (!transactionHistory.containsKey(accountNumber)) {
                    transactionHistory.put(accountNumber, new ArrayList<>());
                }
            }
            
            System.out.println("Bank initialized with " + accounts.size() + " accounts.");
            
        } catch (Exception e) {
            System.err.println("Error initializing bank: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback to empty data structures
            accounts = new HashMap<>();
            transactionHistory = new HashMap<>();
        }
    }
    
    public Account getAccount(String accountNumber) {
        try {
            return accounts.get(accountNumber);
        } catch (Exception e) {
            System.err.println("Error retrieving account: " + e.getMessage());
            return null;
        }
    }
    
    public boolean validateAccount(String accountNumber, String pin) {
        try {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                return account.validatePin(pin);
            }
            return false;
        } catch (NullPointerException e) {
            System.err.println("Null pointer error during validation: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error validating account: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public void addTransaction(String accountNumber, String transactionDetail) {
        try {
            if (transactionHistory.containsKey(accountNumber)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timestamp = LocalDateTime.now().format(formatter);
                transactionHistory.get(accountNumber).add(timestamp + " - " + transactionDetail);
                
                // Save to file after each transaction
                FileManager.saveTransactionHistory(transactionHistory);
            } else {
                throw new IllegalArgumentException("Account number not found: " + accountNumber);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid account: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error adding transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public List<String> getTransactionHistory(String accountNumber) {
        try {
            return transactionHistory.getOrDefault(accountNumber, new ArrayList<>());
        } catch (Exception e) {
            System.err.println("Error retrieving transaction history: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Save all data when shutting down
    public void shutdown() {
        try {
            System.out.println("Saving all data before shutdown...");
            FileManager.saveAccounts(accounts);
            FileManager.saveTransactionHistory(transactionHistory);
            System.out.println("All data saved successfully!");
        } catch (Exception e) {
            System.err.println("Error during shutdown: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /* Add new account (bonus feature)
    public boolean addAccount(Account account) {
        try {
            if (account == null) {
                throw new IllegalArgumentException("Account cannot be null");
            }
            
            if (accounts.containsKey(account.getAccountNumber())) {
                throw new IllegalArgumentException("Account number already exists");
            }
            
            accounts.put(account.getAccountNumber(), account);
            transactionHistory.put(account.getAccountNumber(), new ArrayList<>());
            
            // Save to file
            FileManager.saveAccounts(accounts);
            
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid account data: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error adding account: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
        */
}