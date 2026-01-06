// FileManager.java
import java.io.*;
import java.util.*;

// Class to handle File Input/Output operations
public class FileManager {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    
    // Write all accounts to file
    public static void saveAccounts(HashMap<String, Account> accounts) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE));
            
            for (Account account : accounts.values()) {
                // Format: AccountType|AccountNumber|Name|PIN|Balance|Extra
                String line = "";
                
                if (account instanceof SavingsAccount) {
                    SavingsAccount sa = (SavingsAccount) account;
                    line = String.format("SAVINGS|%s|%s|%s|%.2f|%.2f",
                        account.getAccountNumber(),
                        account.getAccountHolderName(),
                        "****", // Don't save actual PIN for security
                        account.getBalance(),
                        sa.getInterestRate());
                } else if (account instanceof CurrentAccount) {
                    CurrentAccount ca = (CurrentAccount) account;
                    line = String.format("CURRENT|%s|%s|%s|%.2f|%.2f",
                        account.getAccountNumber(),
                        account.getAccountHolderName(),
                        "****",
                        account.getBalance(),
                        ca.getOverdraftLimit());
                }
                
                writer.write(line);
                writer.newLine();
            }
            
            System.out.println("Accounts saved successfully!");
            
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
    }
    
    // Read accounts from file
    public static HashMap<String, Account> loadAccounts() {
        HashMap<String, Account> accounts = new HashMap<>();
        BufferedReader reader = null;
        
        try {
            File file = new File(ACCOUNTS_FILE);
            
            // If file doesn't exist, return empty map with sample data
            if (!file.exists()) {
                System.out.println("Accounts file not found. Creating with sample data...");
                return createSampleAccounts();
            }
            
            reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    
                    if (parts.length < 6) {
                        System.err.println("Invalid line format: " + line);
                        continue;
                    }
                    
                    String type = parts[0];
                    String accountNumber = parts[1];
                    String name = parts[2];
                    String pin = "1234"; // Default PIN (in real app, would handle securely)
                    double balance = Double.parseDouble(parts[4]);
                    double extra = Double.parseDouble(parts[5]);
                    
                    Account account = null;
                    
                    if (type.equals("SAVINGS")) {
                        account = new SavingsAccount(accountNumber, name, pin, balance);
                    } else if (type.equals("CURRENT")) {
                        account = new CurrentAccount(accountNumber, name, pin, balance, extra);
                    }
                    
                    if (account != null) {
                        accounts.put(accountNumber, account);
                    }
                    
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing number in line: " + line);
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Incomplete data in line: " + line);
                    e.printStackTrace();
                }
            }
            
            System.out.println("Loaded " + accounts.size() + " accounts from file.");
            
        } catch (FileNotFoundException e) {
            System.err.println("Accounts file not found: " + e.getMessage());
            return createSampleAccounts();
        } catch (IOException e) {
            System.err.println("Error reading accounts: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
        
        return accounts;
    }
    
    // Save transaction history to file
    public static void saveTransactionHistory(HashMap<String, List<String>> history) {
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE));
            
            for (Map.Entry<String, List<String>> entry : history.entrySet()) {
                String accountNumber = entry.getKey();
                List<String> transactions = entry.getValue();
                
                for (String transaction : transactions) {
                    writer.write(accountNumber + "|" + transaction);
                    writer.newLine();
                }
            }
            
            System.out.println("Transaction history saved successfully!");
            
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
    }
    
    // Load transaction history from file
    public static HashMap<String, List<String>> loadTransactionHistory() {
        HashMap<String, List<String>> history = new HashMap<>();
        BufferedReader reader = null;
        
        try {
            File file = new File(TRANSACTIONS_FILE);
            
            if (!file.exists()) {
                System.out.println("Transaction file not found. Starting fresh.");
                return history;
            }
            
            reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|", 2);
                    
                    if (parts.length < 2) {
                        continue;
                    }
                    
                    String accountNumber = parts[0];
                    String transaction = parts[1];
                    
                    if (!history.containsKey(accountNumber)) {
                        history.put(accountNumber, new ArrayList<>());
                    }
                    
                    history.get(accountNumber).add(transaction);
                    
                } catch (Exception e) {
                    System.err.println("Error parsing transaction line: " + line);
                }
            }
            
            System.out.println("Loaded transaction history from file.");
            
        } catch (IOException e) {
            System.err.println("Error reading transactions: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
        
        return history;
    }
    
    // Create sample accounts if no file exists
    private static HashMap<String, Account> createSampleAccounts() {
        HashMap<String, Account> accounts = new HashMap<>();
        
        Account acc1 = new SavingsAccount("1001", "John Doe", "1234", 5000.0);
        Account acc2 = new CurrentAccount("2001", "Jane Smith", "5678", 10000.0, 2000.0);
        Account acc3 = new SavingsAccount("1002", "Bob Wilson", "9999", 15000.0);
        
        accounts.put("1001", acc1);
        accounts.put("2001", acc2);
        accounts.put("1002", acc3);
        
        // Save sample accounts to file
        saveAccounts(accounts);
        
        return accounts;
    }
}