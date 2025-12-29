// WithdrawTransaction.java
// Polymorphism - WithdrawTransaction implements Transaction
public class WithdrawTransaction implements Transaction {
    private Account account;
    private double amount;
    private boolean success;
    
    public WithdrawTransaction(Account account, double amount) {
        this.account = account;
        this.amount = amount;
        this.success = false;
    }
    
    @Override
    public boolean execute() {
        if (amount > account.getWithdrawalLimit()) {
            return false;
        }
        success = account.withdraw(amount);
        return success;
    }
    
    @Override
    public String getTransactionDetails() {
        return String.format("Withdrawal: $%.2f - %s", amount, success ? "Success" : "Failed");
    }
    
    @Override
    public String getType() {
        return "WITHDRAW";
    }
}