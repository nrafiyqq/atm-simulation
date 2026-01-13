// Account.java
// Abstract base class for Account - Inheritance & Encapsulation
public abstract class Account 
{
    private String accountNumber;
    private String accountHolderName;
    private String pin;
    private double balance;
    
    public Account(String accountNumber, String accountHolderName, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = balance;
    }
    
    // Encapsulation - Getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }
    
    // Encapsulation - Protected setters
    protected void setBalance(double balance) { this.balance = balance; }
    
    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }
    
    // Abstract method for inheritance
    public abstract String getAccountType();
    public abstract double getWithdrawalLimit();
}