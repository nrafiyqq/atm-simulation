// CurrentAccount.java
// Inheritance - CurrentAccount extends Account
public class CurrentAccount extends Account {
    private double overdraftLimit;
    private static final double WITHDRAWAL_LIMIT = 100000.0;
    
    public CurrentAccount(String accountNumber, String accountHolderName, String pin, double balance, double overdraftLimit) {
        super(accountNumber, accountHolderName, pin, balance);
        this.overdraftLimit = overdraftLimit;
    }
    
    public double getOverdraftLimit() { return overdraftLimit; }
    
    @Override
    public String getAccountType() {
        return "Current Account";
    }
    
    @Override
    public double getWithdrawalLimit() {
        return WITHDRAWAL_LIMIT;
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= (getBalance() + overdraftLimit)) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }
}