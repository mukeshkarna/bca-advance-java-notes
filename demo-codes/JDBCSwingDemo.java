import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JDBCSwingDemo extends JFrame {
    private JTextField txtName, txtAge, txtId;
    private JButton btnConnect, btnInsert, btnUpdate, btnDelete, btnView;
    private JTable tblData;
    private DefaultTableModel tableModel;
    private Connection connection;
    private JLabel statusLabel;

    public JDBCSwingDemo() {
        // Set up the JFrame
        setTitle("JDBC Swing Demo");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        inputPanel.add(txtAge);

        // Create button panel
        btnConnect = new JButton("Connect to DB");
        btnInsert = new JButton("Insert");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("View All");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnConnect);
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);

        // Combine input and button panels into a control panel
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        // Table for displaying data
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "Age"}
        );
        tblData = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblData);
        scrollPane.setPreferredSize(new Dimension(650, 200));

        // Status label
        statusLabel = new JLabel("Not connected to database");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Add components to frame
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Add action listeners
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToDatabase();
            }
        });

        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRecord();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllRecords();
            }
        });

        // Initially disable database operation buttons
        setDatabaseButtonsEnabled(false);
    }

    private void setDatabaseButtonsEnabled(boolean enabled) {
        btnInsert.setEnabled(enabled);
        btnUpdate.setEnabled(enabled);
        btnDelete.setEnabled(enabled);
        btnView.setEnabled(enabled);
    }

    private void connectToDatabase() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database URL, username, and password
            // Replace these with your actual database info
            String url = "jdbc:mysql://localhost:3308/demo_db";
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(url, username, password);

            // Create table if it doesn't exist
            createTable();

            statusLabel.setText("Connected to database");
            setDatabaseButtonsEnabled(true);
            JOptionPane.showMessageDialog(this, "Connected to database successfully!");
        } catch (ClassNotFoundException e) {
            showError("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            showError("Database connection error: " + e.getMessage());
        }
    }

    private void createTable() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id INT PRIMARY KEY, " +
                    " name VARCHAR(100), " +
                    " age INT)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            showError("Error creating table: " + e.getMessage());
        }
    }

    private void insertRecord() {
        try {
            if (validateInput()) {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());

                String sql = "INSERT INTO users (id, name, age) VALUES (?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setInt(3, age);

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();

                if (rowsAffected > 0) {
                    statusLabel.setText("Record inserted successfully");
                    clearFields();
                    viewAllRecords();
                }
            }
        } catch (SQLException e) {
            showError("Error inserting record: " + e.getMessage());
        }
    }

    private void updateRecord() {
        try {
            if (validateInput()) {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());

                String sql = "UPDATE users SET name = ?, age = ? WHERE id = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setInt(2, age);
                pstmt.setInt(3, id);

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();

                if (rowsAffected > 0) {
                    statusLabel.setText("Record updated successfully");
                    clearFields();
                    viewAllRecords();
                } else {
                    statusLabel.setText("No record found with ID: " + id);
                }
            }
        } catch (SQLException e) {
            showError("Error updating record: " + e.getMessage());
        }
    }

    private void deleteRecord() {
        try {
            String idText = txtId.getText().trim();
            if (idText.isEmpty()) {
                showError("Please enter an ID to delete");
                return;
            }

            int id = Integer.parseInt(idText);
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            if (rowsAffected > 0) {
                statusLabel.setText("Record deleted successfully");
                clearFields();
                viewAllRecords();
            } else {
                statusLabel.setText("No record found with ID: " + id);
            }
        } catch (SQLException e) {
            showError("Error deleting record: " + e.getMessage());
        }
    }

    private void viewAllRecords() {
        try {
            // Clear the table
            tableModel.setRowCount(0);

            String sql = "SELECT * FROM users";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Add data to table
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");

                tableModel.addRow(new Object[]{id, name, age});
            }

            rs.close();
            stmt.close();

            if (tableModel.getRowCount() == 0) {
                statusLabel.setText("No records found in database");
            } else {
                statusLabel.setText(tableModel.getRowCount() + " record(s) found");
            }

            // Force table to repaint
            tblData.repaint();
        } catch (SQLException e) {
            showError("Error retrieving records: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    private boolean validateInput() {
        try {
            // Validate ID
            String idText = txtId.getText().trim();
            if (idText.isEmpty()) {
                showError("ID cannot be empty");
                return false;
            }
            Integer.parseInt(idText);

            // Validate Name
            String nameText = txtName.getText().trim();
            if (nameText.isEmpty()) {
                showError("Name cannot be empty");
                return false;
            }

            // Validate Age
            String ageText = txtAge.getText().trim();
            if (ageText.isEmpty()) {
                showError("Age cannot be empty");
                return false;
            }
            Integer.parseInt(ageText);

            return true;
        } catch (NumberFormatException e) {
            showError("ID and Age must be valid numbers");
            return false;
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JDBCSwingDemo().setVisible(true);
            }
        });
    }
}