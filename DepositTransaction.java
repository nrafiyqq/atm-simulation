// DepositTransaction.java
// Polymorphism - DepositTransaction implements Transaction
public class DepositTransaction implements Transaction {
    private Account account;
    private double amount;
    private boolean success;
    
    public DepositTransaction(Account account, double amount) {
        this.account = account;
        this.amount = amount;
        this.success = false;
    }
    
    @Override
    public boolean execute() {
        success = account.deposit(amount);
        return success;
    }
    
    @Override
    public String getTransactionDetails() {
        return String.format("Deposit: $%.2f - %s", amount, success ? "Success" : "Failed");
    }
    
    @Override
    public String getType() {
        return "DEPOSIT";
    }
}