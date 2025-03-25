package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PriceClient extends JFrame {

    private JTextField costPriceField;
    private JTextField discountField;
    private JLabel resultLabel;
    private PriceCalculator calculator;

    public PriceClient() {
        super("Price Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new BorderLayout(10, 10));

        try {
            // Get the registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the remote object
            calculator = (PriceCalculator) registry.lookup("PriceCalculator");

            // Build the UI
            initializeUI();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error connecting to server: " + e.getMessage(),
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeUI() {
        // Create panels
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Set padding
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create components
        JLabel costPriceLabel = new JLabel("Cost Price (Rs.):");
        costPriceField = new JTextField("5000", 10);

        JLabel discountLabel = new JLabel("Discount Amount (Rs.):");
        discountField = new JTextField("50", 10);

        JButton calculateButton = new JButton("Calculate Selling Price");
        resultLabel = new JLabel("Selling Price will appear here");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Build the UI structure
        inputPanel.add(costPriceLabel);
        inputPanel.add(costPriceField);
        inputPanel.add(discountLabel);
        inputPanel.add(discountField);

        buttonPanel.add(calculateButton);
        resultPanel.add(resultLabel);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add action listener to the calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePrice();
            }
        });
    }

    private void calculatePrice() {
        try {
            // Parse input values
            double costPrice = Double.parseDouble(costPriceField.getText());
            double discountAmount = Double.parseDouble(discountField.getText());

            // Call the remote method
            double sellingPrice = calculator.calculateSellingPrice(costPrice, discountAmount);

            // Format the result
            DecimalFormat df = new DecimalFormat("#,##0.00");

            // Display the result
            resultLabel.setText("Selling Price: Rs. " + df.format(sellingPrice));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for cost price and discount.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error calculating price: " + e.getMessage(),
                    "Calculation Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create client on EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PriceClient();
            }
        });
    }
}
