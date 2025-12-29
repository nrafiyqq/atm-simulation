// BalanceInquiry.java
// Polymorphism - BalanceInquiry implements Transaction
public class BalanceInquiry implements Transaction {
    private Account account;
    
    public BalanceInquiry(Account account) {
        this.account = account;
    }
    
    @Override
    public boolean execute() {
        return true;
    }
    
    @Override
    public String getTransactionDetails() {
        return String.format("Balance Inquiry: $%.2f", account.getBalance());
    }
    
    @Override
    public String getType() {
        return "BALANCE";
    }
}