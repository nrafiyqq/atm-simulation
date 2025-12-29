//SavingsAccount.java
// Inheritance - SavingsAccount extends Account
public class SavingsAccount extends Account {
    private double interestRate;
    private static final double WITHDRAWAL_LIMIT = 10000.0;
    
    public SavingsAccount(String accountNumber, String accountHolderName, String pin, double balance) {
        super(accountNumber, accountHolderName, pin, balance);
        this.interestRate = 4.5; // 4.5% interest
    }
    
    public double getInterestRate() { return interestRate; }
    
    @Override
    public String getAccountType() {
        return "Savings Account";
    }
    
    @Override
    public double getWithdrawalLimit() {
        return WITHDRAWAL_LIMIT;
    }
    
    public void addInterest() {
        double interest = getBalance() * (interestRate / 100);
        deposit(interest);
    }
}