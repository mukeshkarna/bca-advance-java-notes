import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

/**
 * Library Management System using JDBC and Swing for CRUD operations
 * Database tables: Books, Authors, Publishers, BooksAuthors
 */
public class LibraryManagementSystem extends JFrame {

    // JDBC Connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3308/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // UI Components
    private JTabbedPane tabbedPane;
    private Connection connection;

    // Book panel components
    private JTable booksTable;
    private DefaultTableModel booksTableModel;
    private JTextField bookIdField, bookTitleField, bookIsbnField, bookYearField, bookPublisherIdField;

    // Author panel components
    private JTable authorsTable;
    private DefaultTableModel authorsTableModel;
    private JTextField authorIdField, authorFirstNameField, authorLastNameField;

    // Publisher panel components
    private JTable publishersTable;
    private DefaultTableModel publishersTableModel;
    private JTextField publisherIdField, publisherNameField, publisherAddressField;

    // BookAuthor panel components
    private JTable bookAuthorsTable;
    private DefaultTableModel bookAuthorsTableModel;
    private JTextField bookAuthorIdField, baBookIdField, baAuthorIdField;

    public LibraryManagementSystem() {
        super("Library Management System");

        // Initialize database connection
        initializeConnection();

        // Initialize UI
        initializeUI();

        // Set JFrame properties
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeConnection() {
        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection established");

            // Create tables if they don't exist
            createTablesIfNotExist();

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "JDBC Driver not found: " + e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage());
            System.exit(1);
        }
    }

    private void createTablesIfNotExist() throws SQLException {
        Statement stmt = connection.createStatement();

        // Create Publishers table
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Publishers (" +
                        "publisher_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(100) NOT NULL, " +
                        "address VARCHAR(200)" +
                        ")"
        );

        // Create Authors table
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Authors (" +
                        "author_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "first_name VARCHAR(50) NOT NULL, " +
                        "last_name VARCHAR(50) NOT NULL" +
                        ")"
        );

        // Create Books table
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Books (" +
                        "book_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(100) NOT NULL, " +
                        "isbn VARCHAR(20) UNIQUE, " +
                        "publication_year INT, " +
                        "publisher_id INT, " +
                        "FOREIGN KEY (publisher_id) REFERENCES Publishers(publisher_id)" +
                        ")"
        );

        // Create BooksAuthors table
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS BooksAuthors (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "book_id INT, " +
                        "author_id INT, " +
                        "FOREIGN KEY (book_id) REFERENCES Books(book_id), " +
                        "FOREIGN KEY (author_id) REFERENCES Authors(author_id), " +
                        "UNIQUE(book_id, author_id)" +
                        ")"
        );

        stmt.close();
    }

    private void initializeUI() {
        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Initialize panels for each entity
        tabbedPane.addTab("Books", createBooksPanel());
        tabbedPane.addTab("Authors", createAuthorsPanel());
        tabbedPane.addTab("Publishers", createPublishersPanel());
        tabbedPane.addTab("Books-Authors", createBookAuthorsPanel());

        // Add tabbed pane to frame
        getContentPane().add(tabbedPane);
    }

    // BOOKS PANEL
    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table model for books
        String[] bookColumns = {"ID", "Title", "ISBN", "Publication Year", "Publisher ID"};
        booksTableModel = new DefaultTableModel(bookColumns, 0);
        booksTable = new JTable(booksTableModel);
        JScrollPane tableScrollPane = new JScrollPane(booksTable);

        // Form panel for book data entry
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        formPanel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        bookIdField.setEditable(false);  // ID is auto-generated
        formPanel.add(bookIdField);

        formPanel.add(new JLabel("Title:"));
        bookTitleField = new JTextField();
        formPanel.add(bookTitleField);

        formPanel.add(new JLabel("ISBN:"));
        bookIsbnField = new JTextField();
        formPanel.add(bookIsbnField);

        formPanel.add(new JLabel("Publication Year:"));
        bookYearField = new JTextField();
        formPanel.add(bookYearField);

        formPanel.add(new JLabel("Publisher ID:"));
        bookPublisherIdField = new JTextField();
        formPanel.add(bookPublisherIdField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addBookButton = new JButton("Add");
        addBookButton.addActionListener(e -> addBook());

        JButton updateBookButton = new JButton("Update");
        updateBookButton.addActionListener(e -> updateBook());

        JButton deleteBookButton = new JButton("Delete");
        deleteBookButton.addActionListener(e -> deleteBook());

        JButton clearBookButton = new JButton("Clear");
        clearBookButton.addActionListener(e -> clearBookFields());

        JButton refreshBookButton = new JButton("Refresh");
        refreshBookButton.addActionListener(e -> loadBooks());

        buttonPanel.add(addBookButton);
        buttonPanel.add(updateBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(clearBookButton);
        buttonPanel.add(refreshBookButton);

        // Selection listener for table
        booksTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && booksTable.getSelectedRow() != -1) {
                int selectedRow = booksTable.getSelectedRow();
                bookIdField.setText(booksTable.getValueAt(selectedRow, 0).toString());
                bookTitleField.setText(booksTable.getValueAt(selectedRow, 1).toString());
                bookIsbnField.setText(booksTable.getValueAt(selectedRow, 2).toString());
                bookYearField.setText(booksTable.getValueAt(selectedRow, 3).toString());
                bookPublisherIdField.setText(booksTable.getValueAt(selectedRow, 4).toString());
            }
        });

        // Add components to panel
        panel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        // Load initial data
        loadBooks();

        return panel;
    }

    // BOOK CRUD OPERATIONS
    private void loadBooks() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");

            // Clear existing data
            booksTableModel.setRowCount(0);

            // Add data to table model
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("book_id"));
                row.add(rs.getString("title"));
                row.add(rs.getString("isbn"));
                row.add(rs.getInt("publication_year"));
                row.add(rs.getInt("publisher_id"));
                booksTableModel.addRow(row);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
        }
    }

    private void addBook() {
        String title = bookTitleField.getText().trim();
        String isbn = bookIsbnField.getText().trim();
        String yearText = bookYearField.getText().trim();
        String publisherIdText = bookPublisherIdField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title is required");
            return;
        }

        try {
            String sql = "INSERT INTO Books (title, isbn, publication_year, publisher_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, title);
            pstmt.setString(2, isbn.isEmpty() ? null : isbn);
            pstmt.setInt(3, yearText.isEmpty() ? 0 : Integer.parseInt(yearText));
            pstmt.setInt(4, publisherIdText.isEmpty() ? 0 : Integer.parseInt(publisherIdText));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Book added successfully");
                loadBooks();
                clearBookFields();
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + e.getMessage());
        }
    }

    private void updateBook() {
        String bookIdText = bookIdField.getText().trim();
        if (bookIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a book to update");
            return;
        }

        String title = bookTitleField.getText().trim();
        String isbn = bookIsbnField.getText().trim();
        String yearText = bookYearField.getText().trim();
        String publisherIdText = bookPublisherIdField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title is required");
            return;
        }

        try {
            String sql = "UPDATE Books SET title = ?, isbn = ?, publication_year = ?, publisher_id = ? WHERE book_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, title);
            pstmt.setString(2, isbn.isEmpty() ? null : isbn);
            pstmt.setInt(3, yearText.isEmpty() ? 0 : Integer.parseInt(yearText));
            pstmt.setInt(4, publisherIdText.isEmpty() ? 0 : Integer.parseInt(publisherIdText));
            pstmt.setInt(5, Integer.parseInt(bookIdText));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Book updated successfully");
                loadBooks();
                clearBookFields();
            } else {
                JOptionPane.showMessageDialog(this, "Book not found");
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating book: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + e.getMessage());
        }
    }

    private void deleteBook() {
        String bookIdText = bookIdField.getText().trim();
        if (bookIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete");
            return;
        }

        try {
            // First delete from BooksAuthors table (due to foreign key constraints)
            String deleteBooksAuthors = "DELETE FROM BooksAuthors WHERE book_id = ?";
            PreparedStatement pstmtBA = connection.prepareStatement(deleteBooksAuthors);
            pstmtBA.setInt(1, Integer.parseInt(bookIdText));
            pstmtBA.executeUpdate();
            pstmtBA.close();

            // Now delete the book
            String sql = "DELETE FROM Books WHERE book_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(bookIdText));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully");
                loadBooks();
                clearBookFields();
                // Refresh BookAuthors table as well
                loadBookAuthors();
            } else {
                JOptionPane.showMessageDialog(this, "Book not found");
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting book: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + e.getMessage());
        }
    }

    private void clearBookFields() {
        bookIdField.setText("");
        bookTitleField.setText("");
        bookIsbnField.setText("");
        bookYearField.setText("");
        bookPublisherIdField.setText("");
        booksTable.clearSelection();
    }

    // AUTHORS PANEL
    private JPanel createAuthorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table model for authors
        String[] authorColumns = {"ID", "First Name", "Last Name"};
        authorsTableModel = new DefaultTableModel(authorColumns, 0);
        authorsTable = new JTable(authorsTableModel);
        JScrollPane tableScrollPane = new JScrollPane(authorsTable);

        // Form panel for author data entry
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        formPanel.add(new JLabel("Author ID:"));
        authorIdField = new JTextField();
        authorIdField.setEditable(false);  // ID is auto-generated
        formPanel.add(authorIdField);

        formPanel.add(new JLabel("First Name:"));
        authorFirstNameField = new JTextField();
        formPanel.add(authorFirstNameField);

        formPanel.add(new JLabel("Last Name:"));
        authorLastNameField = new JTextField();
        formPanel.add(authorLastNameField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addAuthorButton = new JButton("Add");
        addAuthorButton.addActionListener(e -> addAuthor());

        JButton updateAuthorButton = new JButton("Update");
        updateAuthorButton.addActionListener(e -> updateAuthor());

        JButton deleteAuthorButton = new JButton("Delete");
        deleteAuthorButton.addActionListener(e -> deleteAuthor());

        JButton clearAuthorButton = new JButton("Clear");
        clearAuthorButton.addActionListener(e -> clearAuthorFields());

        JButton refreshAuthorButton = new JButton("Refresh");
        refreshAuthorButton.addActionListener(e -> loadAuthors());

        buttonPanel.add(addAuthorButton);
        buttonPanel.add(updateAuthorButton);
        buttonPanel.add(deleteAuthorButton);
        buttonPanel.add(clearAuthorButton);
        buttonPanel.add(refreshAuthorButton);

        // Selection listener for table
        authorsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && authorsTable.getSelectedRow() != -1) {
                int selectedRow = authorsTable.getSelectedRow();
                authorIdField.setText(authorsTable.getValueAt(selectedRow, 0).toString());
                authorFirstNameField.setText(authorsTable.getValueAt(selectedRow, 1).toString());
                authorLastNameField.setText(authorsTable.getValueAt(selectedRow, 2).toString());
            }
        });

        // Add components to panel
        panel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        // Load initial data
        loadAuthors();

        return panel;
    }

    // AUTHOR CRUD OPERATIONS
    private void loadAuthors() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Authors");

            // Clear existing data
            authorsTableModel.setRowCount(0);

            // Add data to table model
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("author_id"));
                row.add(rs.getString("first_name"));
                row.add(rs.getString("last_name"));
                authorsTableModel.addRow(row);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading authors: " + e.getMessage());
        }
    }

    private void addAuthor() {
        String firstName = authorFirstNameField.getText().trim();
        String lastName = authorLastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name and last name are required");
            return;
        }

        try {
            String sql = "INSERT INTO Authors (first_name, last_name) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Author added successfully");
                loadAuthors();
                clearAuthorFields();
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding author: " + e.getMessage());
        }
    }

    private void updateAuthor() {
        String authorIdText = authorIdField.getText().trim();
        if (authorIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an author to update");
            return;
        }

        String firstName = authorFirstNameField.getText().trim();
        String lastName = authorLastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name and last name are required");
            return;
        }

        try {
            String sql = "UPDATE Authors SET first_name = ?, last_name = ? WHERE author_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, Integer.parseInt(authorIdText));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Author updated successfully");
                loadAuthors();
                clearAuthorFields();
            } else {
                JOptionPane.showMessageDialog(this, "Author not found");
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating author: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + e.getMessage());
        }
    }

    private void deleteAuthor() {
        String authorIdText = authorIdField.getText().trim();
        if (authorIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an author to delete");
            return;
        }

        try {
            // First delete from BooksAuthors table (due to foreign key constraints)
            String deleteBooksAuthors = "DELETE FROM BooksAuthors WHERE author_id = ?";
            PreparedStatement pstmtBA = connection.prepareStatement(deleteBooksAuthors);
            pstmtBA.setInt(1, Integer.parseInt(authorIdText));
            pstmtBA.executeUpdate();
            pstmtBA.close();

            // Now delete the author
            String sql = "DELETE FROM Authors WHERE author_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(authorIdText));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Author deleted successfully");
                loadAuthors();
                clearAuthorFields();
                // Refresh BookAuthors table as well
                loadBookAuthors();
            } else {
                JOptionPane.showMessageDialog(this, "Author not found");
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting author: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + e.getMessage());
        }
    }

    private void clearAuthorFields() {
        authorIdField.setText("");
        authorFirstNameField.setText("");
        authorLastNameField.setText("");
        authorsTable.clearSelection();
    }

    // PUBLISHERS PANEL
    private JPanel createPublishersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table model for publishers
        String[] publisherColumns = {"ID", "Name", "Address"};
        publishersTableModel = new DefaultTableModel(publisherColumns, 0);
        publishersTable = new JTable(publishersTableModel);
        JScrollPane tableScrollPane = new JScrollPane(publishersTable);

        // Form panel for publisher data entry
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        formPanel.add(new JLabel("Publisher ID:"));
        publisherIdField = new JTextField();
        publisherIdField.setEditable(false);  // ID is auto-generated
        formPanel.add(publisherIdField);

        formPanel.add(new JLabel("Name:"));
        publisherNameField = new JTextField();
        formPanel.add(publisherNameField);

        formPanel.add(new JLabel("Address:"));
        publisherAddressField = new JTextField();
        formPanel.add(publisherAddressField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addPublisherButton = new JButton("Add");
        addPublisherButton.addActionListener(e -> addPublisher());

        JButton updatePublisherButton = new JButton("Update");
        updatePublisherButton.addActionListener(e -> updatePublisher());

        JButton deletePublisherButton = new JButton("Delete");
        deletePublisherButton.addActionListener(e -> deletePublisher());

        JButton clearPublisherButton = new JButton("Clear");
        clearPublisherButton.addActionListener(e -> clearPublisherFields());

        JButton refreshPublisherButton = new JButton("Refresh");
        refreshPublisherButton.addActionListener(e -> loadPublishers());

        buttonPanel.add(addPublisherButton);
        buttonPanel.add(updatePublisherButton);
        buttonPanel.add(deletePublisherButton);
        buttonPanel.add(clearPublisherButton);
        buttonPanel.add(refreshPublisherButton);

        // Selection listener for table
        publishersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && publishersTable.getSelectedRow() != -1) {
                int selectedRow = publishersTable.getSelectedRow();
                publisherIdField.setText(publishersTable.getValueAt(selectedRow, 0).toString());
                publisherNameField.setText(publishersTable.getValueAt(selectedRow, 1).toString());
                Object addressObj = publishersTable.getValueAt(selectedRow, 2);
                publisherAddressField.setText(addressObj == null ? "" : addressObj.toString());
            }
        });

        // Add components to panel
        panel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        // Load initial data
        loadPublishers();

        return panel;
    }

    // PUBLISHER CRUD OPERATIONS
    private void loadPublishers() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Publishers");

            // Clear existing data
            publishersTableModel.setRowCount(0);

            // Add data to table model
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("publisher_id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("address"));
                publishersTableModel.addRow(row);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading publishers: " + e.getMessage());
        }
    }

    private void addPublisher() {
        String name = publisherNameField.getText().trim();
        String address = publisherAddressField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Publisher name is required");
            return;
        }

        try {
            String sql = "INSERT INTO Publishers (name, address) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, address.isEmpty() ? null : address);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Publisher added successfully");
                loadPublishers();
                clearPublisherFields();
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding publisher: " + e.getMessage());
        }
    }

    private void updatePublisher() {
        String publisherIdText = publisherIdField.getText().trim();
        if (publisherIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a publisher to update");
            return;
        }

        String name = publisherNameField.getText().trim();
        String address = publisherAddressField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Publisher name is required");
            return;
        }

        try {
            String sql = "UPDATE Publishers SET name = ?, address = ? WHERE publisher_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, address.isEmpty() ? null : address);
            pstmt.setInt(3, Integer.parseInt(publisherIdText));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Publisher updated successfully");
                loadPublishers();
                clearPublisherFields();
            } else {
                JOptionPane.showMessageDialog(this, "Publisher not found");
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating publisher: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + e.getMessage());
        }
    }

    private void deletePublisher() {
        String publisherIdText = publisherIdField.getText().trim();
        if (publisherIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a publisher to delete");
            return;
        }

        try {
            // First update Books to set publisher_id to null
            String updateBooks = "UPDATE Books SET publisher_id = NULL WHERE publisher_id = ?";
            PreparedStatement pstmtBooks = connection.prepareStatement(updateBooks);
            pstmtBooks.setInt(1, Integer.parseInt(publisherIdText));
            pstmtBooks.executeUpdate();
            pstmtBooks.close();

            // Now delete the publisher
            String sql = "DELETE FROM Publishers WHERE publisher_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(publisherIdText));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Publisher deleted successfully");
                loadPublishers();
                clearPublisherFields();
                // Refresh Books table
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Publisher not found");
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting publisher: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + e.getMessage());
        }
    }

    private void clearPublisherFields() {
        publisherIdField.setText("");
    }


    private JPanel createBookAuthorsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Book Authors");
        panel.add(label);

        bookAuthorsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookAuthorsTable);
        panel.add(scrollPane);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadBookAuthors());
        panel.add(refreshButton);

        return panel;
    }

    private void loadBookAuthors() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Books.title, Authors.first_name, Authors.last_name FROM BookAuthors " +
                    "JOIN Books ON BookAuthors.book_id = Books.book_id " +
                    "JOIN Authors ON BookAuthors.author_id = Authors.author_id");

            // Clear existing data
            bookAuthorsTableModel.setRowCount(0);

            // Add data to table model
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("title"));
                row.add(rs.getString("first_name"));
                row.add(rs.getString("last_name"));
                bookAuthorsTableModel.addRow(row);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading book authors: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryManagementSystem lms = new LibraryManagementSystem();
            lms.loadPublishers();
            lms.loadBooks();
        });
    }
}
