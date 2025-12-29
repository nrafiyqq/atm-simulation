// ATMGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMGUI extends JFrame {
    private ATM atm;
    private Bank bank;
    
    // GUI Components
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Login Panel Components
    private JTextField accountField;
    private JPasswordField pinField;
    
    // Main Menu Components
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    
    public ATMGUI() {
        bank = new Bank();
        atm = new ATM(bank);
        
        setTitle("ATM Simulation System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createLoginPanel();
        createMainMenuPanel();
        createWithdrawPanel();
        createDepositPanel();
        createHistoryPanel();
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    private void createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("ATM SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        accountField = new JTextField(15);
        accountField.setMaximumSize(new Dimension(200, 30));
        
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pinField = new JPasswordField(15);
        pinField.setMaximumSize(new Dimension(200, 30));
        
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> handleLogin());
        
        JLabel infoLabel = new JLabel("<html><center>Sample Accounts:<br>1001/1234 | 2001/5678 | 1002/9999</center></html>");
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setForeground(Color.GRAY);
        
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(accountLabel);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(accountField);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(pinLabel);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(pinField);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(infoLabel);
        
        mainPanel.add(loginPanel, "LOGIN");
    }
    
    private void createMainMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        balanceLabel = new JLabel("Balance: RM0.00");
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(balanceLabel);
        
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton balanceButton = new JButton("Check Balance");
        JButton historyButton = new JButton("Transaction History");
        JButton logoutButton = new JButton("Logout");
        
        withdrawButton.addActionListener(e -> cardLayout.show(mainPanel, "WITHDRAW"));
        depositButton.addActionListener(e -> cardLayout.show(mainPanel, "DEPOSIT"));
        balanceButton.addActionListener(e -> handleBalanceInquiry());
        historyButton.addActionListener(e -> showHistory());
        logoutButton.addActionListener(e -> handleLogout());
        
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(balanceButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(logoutButton);
        
        menuPanel.add(topPanel, BorderLayout.NORTH);
        menuPanel.add(buttonPanel, BorderLayout.CENTER);
        
        mainPanel.add(menuPanel, "MENU");
    }
    
    private void createWithdrawPanel() {
        JPanel withdrawPanel = new JPanel();
        withdrawPanel.setLayout(new BoxLayout(withdrawPanel, BoxLayout.Y_AXIS));
        withdrawPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("Withdraw Money");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField amountField = new JTextField(15);
        amountField.setMaximumSize(new Dimension(200, 30));
        
        JButton submitButton = new JButton("Withdraw");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                Transaction transaction = new WithdrawTransaction(atm.getCurrentAccount(), amount);
                if (atm.performTransaction(transaction)) {
                    JOptionPane.showMessageDialog(this, "Withdrawal Successful!\nAmount: RM" + amount);
                    updateBalance();
                    amountField.setText("");
                    cardLayout.show(mainPanel, "MENU");
                } else {
                    JOptionPane.showMessageDialog(this, "Withdrawal Failed! Insufficient funds or exceeds limit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        withdrawPanel.add(titleLabel);
        withdrawPanel.add(Box.createVerticalStrut(30));
        withdrawPanel.add(new JLabel("Enter Amount:"));
        withdrawPanel.add(Box.createVerticalStrut(5));
        withdrawPanel.add(amountField);
        withdrawPanel.add(Box.createVerticalStrut(20));
        withdrawPanel.add(submitButton);
        withdrawPanel.add(Box.createVerticalStrut(10));
        withdrawPanel.add(backButton);
        
        mainPanel.add(withdrawPanel, "WITHDRAW");
    }
    
    private void createDepositPanel() {
        JPanel depositPanel = new JPanel();
        depositPanel.setLayout(new BoxLayout(depositPanel, BoxLayout.Y_AXIS));
        depositPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("Deposit Money");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField amountField = new JTextField(15);
        amountField.setMaximumSize(new Dimension(200, 30));
        
        JButton submitButton = new JButton("Deposit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                Transaction transaction = new DepositTransaction(atm.getCurrentAccount(), amount);
                if (atm.performTransaction(transaction)) {
                    JOptionPane.showMessageDialog(this, "Deposit Successful!\nAmount: RM" + amount);
                    updateBalance();
                    amountField.setText("");
                    cardLayout.show(mainPanel, "MENU");
                } else {
                    JOptionPane.showMessageDialog(this, "Deposit Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        depositPanel.add(titleLabel);
        depositPanel.add(Box.createVerticalStrut(30));
        depositPanel.add(new JLabel("Enter Amount:"));
        depositPanel.add(Box.createVerticalStrut(5));
        depositPanel.add(amountField);
        depositPanel.add(Box.createVerticalStrut(20));
        depositPanel.add(submitButton);
        depositPanel.add(Box.createVerticalStrut(10));
        depositPanel.add(backButton);
        
        mainPanel.add(depositPanel, "DEPOSIT");
    }
    
    private void createHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Transaction History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        historyPanel.add(titleLabel, BorderLayout.NORTH);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        historyPanel.add(backButton, BorderLayout.SOUTH);
        
        mainPanel.add(historyPanel, "HISTORY");
    }
    
    private void handleLogin() {
        String accountNumber = accountField.getText();
        String pin = new String(pinField.getPassword());
        
        if (atm.login(accountNumber, pin)) {
            Account account = atm.getCurrentAccount();
            welcomeLabel.setText("Welcome, " + account.getAccountHolderName() + "!");
            updateBalance();
            accountField.setText("");
            pinField.setText("");
            cardLayout.show(mainPanel, "MENU");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Account Number or PIN!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleBalanceInquiry() {
        Transaction transaction = new BalanceInquiry(atm.getCurrentAccount());
        atm.performTransaction(transaction);
        JOptionPane.showMessageDialog(this, 
            "Current Balance: RM" + String.format("%.2f", atm.getCurrentAccount().getBalance()) +
            "\nAccount Type: " + atm.getCurrentAccount().getAccountType());
    }
    
    private void showHistory() {
        java.util.List<String> history = atm.getTransactionHistory();
        JTextArea historyArea = new JTextArea();
        
        for (Component comp : mainPanel.getComponents()) {
            if (mainPanel.getComponent(mainPanel.getComponentCount() - 1) == comp) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    for (Component inner : panel.getComponents()) {
                        if (inner instanceof JScrollPane) {
                            JScrollPane scroll = (JScrollPane) inner;
                            historyArea = (JTextArea) scroll.getViewport().getView();
                            break;
                        }
                    }
                }
            }
        }
        
        if (history.isEmpty()) {
            historyArea.setText("No transactions yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String trans : history) {
                sb.append(trans).append("\n");
            }
            historyArea.setText(sb.toString());
        }
        
        cardLayout.show(mainPanel, "HISTORY");
    }
    
    private void updateBalance() {
        if (atm.getCurrentAccount() != null) {
            balanceLabel.setText("Balance: RM" + String.format("%.2f", atm.getCurrentAccount().getBalance()));
        }
    }
    
    private void handleLogout() {
        atm.logout();
        cardLayout.show(mainPanel, "LOGIN");
        JOptionPane.showMessageDialog(this, "Logged out successfully!");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMGUI gui = new ATMGUI();
            gui.setVisible(true);
        });
    }
}