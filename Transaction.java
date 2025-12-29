public interface Transaction {
    boolean execute();
    String getTransactionDetails();
    String getType();
}