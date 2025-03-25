
// File: JDBCDemo.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Simple JDBC Demo to show database connection and operations
 * This example uses MySQL database
 */
public class JDBCDemo {
  // Database connection parameters
  private static final String DB_URL = "jdbc:mysql://localhost:3308/students_db";
  private static final String USER = "root";
  private static final String PASS = ""; // Change this to your MySQL password

  // Connection object
  private static Connection connection = null;

  public static void main(String[] args) {
    try {
      // Step 1: Connect to database
      System.out.println("Connecting to database...");
      connectToDatabase();

      // Step 2: Create database and table structure
      System.out.println("Setting up database...");
      setupDatabase();

      // Step 3: Show menu for demo operations
      runDemo();

    } catch (SQLException e) {
      System.out.println("Database error occurred:");
      e.printStackTrace();
    } finally {
      // Always close the connection
      try {
        if (connection != null) {
          connection.close();
          System.out.println("Database connection closed.");
        }
      } catch (SQLException e) {
        System.out.println("Error closing connection:");
        e.printStackTrace();
      }
    }
  }

  /**
   * Connect to MySQL database
   */
  private static void connectToDatabase() throws SQLException {
    // Register JDBC driver (optional for newer JDBC drivers)
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      System.out.println("MySQL JDBC Driver registered.");
    } catch (ClassNotFoundException e) {
      System.out.println("MySQL JDBC Driver not found. Include it in your library path!");
      throw new SQLException("JDBC Driver not found", e);
    }

    // Open a connection
    connection = DriverManager.getConnection(DB_URL, USER, PASS);
    System.out.println("Successfully connected to MySQL database!");
  }

  /**
   * Set up the database and tables for the demo
   */
  private static void setupDatabase() throws SQLException {
    Statement stmt = connection.createStatement();

    // Create students_db database if it doesn't exist
    // Note: We're already connected to students_db, but this ensures it exists
    stmt.execute("CREATE DATABASE IF NOT EXISTS students_db");
    stmt.execute("USE students_db");

    // Create students table
    String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
        "id INT AUTO_INCREMENT PRIMARY KEY," +
        "name VARCHAR(100) NOT NULL," +
        "email VARCHAR(100) UNIQUE," +
        "gpa DECIMAL(3,2)" +
        ")";
    stmt.execute(createTableSQL);

    // Add some sample data if the table is empty
    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students");
    rs.next();
    int count = rs.getInt(1);

    if (count == 0) {
      String[] sampleNames = { "John Smith", "Maria Garcia", "Wei Chen", "Priya Patel", "Mohammed Ali" };
      String[] sampleEmails = { "john@example.com", "maria@example.com", "wei@example.com",
          "priya@example.com", "mohammed@example.com" };
      double[] sampleGpas = { 3.5, 4.0, 3.2, 3.9, 3.7 };

      String insertSQL = "INSERT INTO students (name, email, gpa) VALUES (?, ?, ?)";
      PreparedStatement pstmt = connection.prepareStatement(insertSQL);

      for (int i = 0; i < sampleNames.length; i++) {
        pstmt.setString(1, sampleNames[i]);
        pstmt.setString(2, sampleEmails[i]);
        pstmt.setDouble(3, sampleGpas[i]);
        pstmt.executeUpdate();
      }

      System.out.println("Sample data added to students table.");
      pstmt.close();
    }

    stmt.close();
  }

  /**
   * Run the interactive demo
   */
  private static void runDemo() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    boolean exit = false;

    while (!exit) {
      System.out.println("\n===== JDBC Demo Menu =====");
      System.out.println("1. View all students");
      System.out.println("2. Add a new student");
      System.out.println("3. Search for a student by name");
      System.out.println("4. Update student GPA");
      System.out.println("5. Delete a student");
      System.out.println("6. Execute custom SQL query");
      System.out.println("0. Exit");
      System.out.print("Enter your choice: ");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline

      switch (choice) {
        case 0:
          exit = true;
          break;
        case 1:
          viewAllStudents();
          break;
        case 2:
          addNewStudent(scanner);
          break;
        case 3:
          searchStudent(scanner);
          break;
        case 4:
          updateStudentGpa(scanner);
          break;
        case 5:
          deleteStudent(scanner);
          break;
        case 6:
          executeCustomQuery(scanner);
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }

    scanner.close();
  }

  /**
   * Display all students in the database
   */
  private static void viewAllStudents() throws SQLException {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM students ORDER BY id");

    System.out.println("\n----- All Students -----");
    System.out.printf("%-5s %-20s %-25s %-5s\n", "ID", "Name", "Email", "GPA");
    System.out.println("----------------------------------------------------------");

    while (rs.next()) {
      int id = rs.getInt("id");
      String name = rs.getString("name");
      String email = rs.getString("email");
      double gpa = rs.getDouble("gpa");

      System.out.printf("%-5d %-20s %-25s %.2f\n", id, name, email, gpa);
    }

    rs.close();
    stmt.close();
  }

  /**
   * Add a new student to the database
   */
  private static void addNewStudent(Scanner scanner) throws SQLException {
    System.out.println("\n----- Add New Student -----");

    System.out.print("Enter student name: ");
    String name = scanner.nextLine();

    System.out.print("Enter student email: ");
    String email = scanner.nextLine();

    System.out.print("Enter student GPA: ");
    double gpa = scanner.nextDouble();
    scanner.nextLine(); // Consume newline

    // Use prepared statement to prevent SQL injection
    String sql = "INSERT INTO students (name, email, gpa) VALUES (?, ?, ?)";
    PreparedStatement pstmt = connection.prepareStatement(sql);
    pstmt.setString(1, name);
    pstmt.setString(2, email);
    pstmt.setDouble(3, gpa);

    int rowsAffected = pstmt.executeUpdate();
    System.out.println(rowsAffected + " student added successfully.");

    pstmt.close();
  }

  /**
   * Search for a student by name
   */
  private static void searchStudent(Scanner scanner) throws SQLException {
    System.out.println("\n----- Search Student -----");

    System.out.print("Enter student name to search (partial names OK): ");
    String searchName = scanner.nextLine();

    String sql = "SELECT * FROM students WHERE name LIKE ?";
    PreparedStatement pstmt = connection.prepareStatement(sql);
    pstmt.setString(1, "%" + searchName + "%");

    ResultSet rs = pstmt.executeQuery();

    System.out.printf("%-5s %-20s %-25s %-5s\n", "ID", "Name", "Email", "GPA");
    System.out.println("----------------------------------------------------------");

    boolean found = false;
    while (rs.next()) {
      found = true;
      int id = rs.getInt("id");
      String name = rs.getString("name");
      String email = rs.getString("email");
      double gpa = rs.getDouble("gpa");

      System.out.printf("%-5d %-20s %-25s %.2f\n", id, name, email, gpa);
    }

    if (!found) {
      System.out.println("No students found with that name.");
    }

    rs.close();
    pstmt.close();
  }

  /**
   * Update a student's GPA
   */
  private static void updateStudentGpa(Scanner scanner) throws SQLException {
    System.out.println("\n----- Update Student GPA -----");

    System.out.print("Enter student ID to update: ");
    int id = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    // First check if student exists
    String checkSql = "SELECT name FROM students WHERE id = ?";
    PreparedStatement checkStmt = connection.prepareStatement(checkSql);
    checkStmt.setInt(1, id);
    ResultSet rs = checkStmt.executeQuery();

    if (rs.next()) {
      String name = rs.getString("name");
      System.out.println("Updating GPA for student: " + name);

      System.out.print("Enter new GPA: ");
      double newGpa = scanner.nextDouble();
      scanner.nextLine(); // Consume newline

      String updateSql = "UPDATE students SET gpa = ? WHERE id = ?";
      PreparedStatement updateStmt = connection.prepareStatement(updateSql);
      updateStmt.setDouble(1, newGpa);
      updateStmt.setInt(2, id);

      int rowsAffected = updateStmt.executeUpdate();
      System.out.println(rowsAffected + " student record updated.");

      updateStmt.close();
    } else {
      System.out.println("No student found with ID: " + id);
    }

    rs.close();
    checkStmt.close();
  }

  /**
   * Delete a student from the database
   */
  private static void deleteStudent(Scanner scanner) throws SQLException {
    System.out.println("\n----- Delete Student -----");

    System.out.print("Enter student ID to delete: ");
    int id = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    // First check if student exists
    String checkSql = "SELECT name FROM students WHERE id = ?";
    PreparedStatement checkStmt = connection.prepareStatement(checkSql);
    checkStmt.setInt(1, id);
    ResultSet rs = checkStmt.executeQuery();

    if (rs.next()) {
      String name = rs.getString("name");
      System.out.println("Are you sure you want to delete student: " + name + "? (y/n)");
      String confirm = scanner.nextLine();

      if (confirm.equalsIgnoreCase("y")) {
        String deleteSql = "DELETE FROM students WHERE id = ?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);

        int rowsAffected = deleteStmt.executeUpdate();
        System.out.println(rowsAffected + " student deleted successfully.");

        deleteStmt.close();
      } else {
        System.out.println("Delete operation cancelled.");
      }
    } else {
      System.out.println("No student found with ID: " + id);
    }

    rs.close();
    checkStmt.close();
  }

  /**
   * Execute a custom SQL query (for demonstration purposes)
   */
  private static void executeCustomQuery(Scanner scanner) throws SQLException {
    System.out.println("\n----- Execute Custom SQL Query -----");
    System.out.println("Warning: This is for demonstration only. Be careful with what you enter.");
    System.out.print("Enter SQL query: ");
    String sql = scanner.nextLine();

    try {
      Statement stmt = connection.createStatement();

      // Check if it's a SELECT query
      boolean isResultSet = stmt.execute(sql);

      if (isResultSet) {
        // Handle SELECT results
        ResultSet rs = stmt.getResultSet();
        int columnCount = rs.getMetaData().getColumnCount();

        // Print column headers
        for (int i = 1; i <= columnCount; i++) {
          System.out.print(rs.getMetaData().getColumnName(i) + "\t");
        }
        System.out.println();

        // Print results
        while (rs.next()) {
          for (int i = 1; i <= columnCount; i++) {
            System.out.print(rs.getString(i) + "\t");
          }
          System.out.println();
        }

        rs.close();
      } else {
        // Handle UPDATE, INSERT, DELETE results
        int updateCount = stmt.getUpdateCount();
        System.out.println(updateCount + " rows affected.");
      }

      stmt.close();
    } catch (SQLException e) {
      System.out.println("Error executing SQL: " + e.getMessage());
    }
  }
}