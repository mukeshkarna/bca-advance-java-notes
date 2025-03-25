# Database Programming - Unit 2 Notes

## 2.1 The Design of JDBC

JDBC (Java Database Connectivity) follows a design similar to Microsoft's ODBC:
- Programs written with the API communicate with a driver manager
- The driver manager uses specific drivers to talk to actual databases
- Database vendors can provide their own drivers to plug into the driver manager
- Simple mechanism exists for registering third-party drivers

### 2.1.1 JDBC Driver Types

There are four types of JDBC drivers:

1. **Type 1: JDBC-ODBC Bridge Driver**
   - Translates JDBC to ODBC and relies on ODBC drivers
   - **Advantages**: Easy to use, can connect to any database
   - **Disadvantages**: Performance degradation, ODBC driver needs installation on client machine
   - No longer supported from Java 8 by Oracle

2. **Type 2: Native-API Driver (Partially Java Driver)**
   - Uses client-side libraries of the database
   - Converts JDBC method calls into native calls of database API
   - Not written entirely in Java
   - **Advantages**: Better performance than JDBC-ODBC bridge
   - **Disadvantages**: Native driver and vendor client library need installation on each client machine

3. **Type 3: Network Protocol Driver (Fully Java Driver)**
   - Uses middleware (application server) to convert JDBC calls into vendor-specific database protocol
   - Fully written in Java
   - **Advantages**: No client-side library required; application server can perform tasks like auditing, load balancing, logging
   - **Disadvantages**: Requires network support on client machine, database-specific coding in middle tier, costly maintenance

4. **Type 4: Thin Driver (Fully Java Driver)**
   - Converts JDBC calls directly into vendor-specific database protocol
   - Fully written in Java
   - **Advantages**: Best performance among all drivers, no software required at client or server side
   - **Disadvantages**: Drivers are database-dependent

### 2.1.2 Typical Uses of JDBC

Two common models:

1. **Traditional Client/Server Model**
   - Rich GUI on client, database on server
   - JDBC driver deployed on client

2. **Three-Tier Model** (more common today)
   - Client application doesn't make direct database calls
   - Client calls middleware layer on server
   - Middleware layer makes database queries
   - **Advantages**: Separates visual presentation (client), business logic (middle tier), and raw data (database)
   - Client-middle tier communication typically via HTTP
   - JDBC manages communication between middle tier and back-end database

## 2.2 The Structured Query Language

JDBC lets you communicate with databases using SQL, the standard command language for modern relational databases.

**Example SQL Statements:**
```sql
-- Select statements
SELECT Books.Title, Books.Publisher_Id, Books.Price, Publishers.Name, Publishers.URL FROM Books, Publishers WHERE Books.Publisher_Id = Publishers.Publisher_Id
SELECT * FROM Books
SELECT ISBN, Price, Title FROM Books
SELECT ISBN, Price, Title FROM Books WHERE Price <= 29.95
SELECT ISBN, Price, Title FROM Books WHERE Title NOT LIKE '%n_x%'
SELECT Title FROM Books WHERE Title LIKE '%''%'

-- Joins
SELECT * FROM Books, Publishers
SELECT * FROM Books, Publishers WHERE Books.Publisher_Id = Publishers.Publisher_Id

-- Update, Delete, Insert
UPDATE Books SET Price = Price - 5.00 WHERE Title LIKE '%C++%'
DELETE FROM Books WHERE Title LIKE '%C++%'
INSERT INTO Books VALUES ('A Guide to the SQL Standard', '0-201-96426-0', '0201', 47.95)

-- Create Table
CREATE TABLE Books (
    Title CHAR(60),
    ISBN CHAR(13),
    Publisher_Id CHAR(6),
    Price DECIMAL(10,2)
)
```

## 2.3 JDBC Configuration

### 2.3.1 Database URLs

When connecting to a database, you must use database-specific parameters:
- General syntax: `jdbc:subprotocol:other stuff`
- Examples:
  - `jdbc:derby://localhost:1527/COREJAVA;create=true`
  - `jdbc:postgresql:COREJAVA`

### 2.3.2 Driver JAR Files

- Obtain JAR file containing driver for your database
- Include driver JAR file on the classpath when running programs
- Command line example: `java -classpath driverPath:. ProgramName`

### 2.3.3 Starting the Database

Database server must be started before connection. Example for Derby:
1. Change to directory for database files
2. Locate `derbyrun.jar`
3. Run `java -jar derby/lib/derbyrun.jar server start`
4. Test with interactive scripting tool (ij)
5. Stop server with `java -jar derby/lib/derbyrun.jar server shutdown`

### 2.3.4 Registering the Driver Class

Two methods to register drivers:

1. **Automatic Registration**
   - Many JDBC JAR files automatically register the driver class
   - JAR file contains `META-INF/services/java.sql.Driver`

2. **Manual Registration**
   - Load driver class: `Class.forName("org.postgresql.Driver");`
   - Set jdbc.drivers property:
     - Command line: `java -Djdbc.drivers=org.postgresql.Driver ProgramName`
     - In code: `System.setProperty("jdbc.drivers", "org.postgresql.Driver");`
   - Multiple drivers can be specified with colons

### 2.3.5 Connecting to the Database

```java
String url = "jdbc:postgresql:COREJAVA";
String username = "dbuser";
String password = "secret";
Connection conn = DriverManager.getConnection(url, username, password);
```

The driver manager iterates through registered drivers to find one that can use the specified subprotocol.

## 2.4 Working with JDBC Statements

### 2.4.1 Executing SQL Statements

Process:
1. Create Statement object:
   ```java
   Statement stat = conn.createStatement();
   ```

2. Prepare SQL command:
   ```java
   String command = "UPDATE Books " + 
                    "SET Price = Price - 5.00 " + 
                    "WHERE Title NOT LIKE '%Introduction%'";
   ```

3. Execute statement:
   - For updates/modifications: `stat.executeUpdate(command);` returns row count
   - For queries: `ResultSet rs = stat.executeQuery("SELECT * FROM Books");`

4. Process ResultSet:
   ```java
   while (rs.next()) {
       String isbn = rs.getString(1);
       double price = rs.getDouble("Price");
       // Process row data
   }
   ```

### 2.4.2 Managing Connections, Statements, and Result Sets

- Every Connection can create multiple Statement objects
- Same Statement can be used for multiple unrelated commands
- A statement has at most one open result set
- Close ResultSet, Statement, and Connection when done
- Use try-with-resources for automatic resource management:
  ```java
  try (Connection conn = ...) {
      Statement stat = conn.createStatement();
      ResultSet result = stat.executeQuery(queryString);
      // Process results
  }
  ```

### 2.4.3 Analyzing SQL Exceptions

- SQLException contains a chain of exceptions retrieved with getNextException
- Use enhanced for loop:
  ```java
  for (Throwable t : sqlException) {
      // Handle exception
  }
  ```
- Call getSQLState and getErrorCode for further analysis
- Retrieve warnings from connections, statements, and result sets:
  ```java
  SQLWarning w = stat.getWarning();
  while (w != null) {
      // Process warning
      w = w.nextWarning();
  }
  ```

### 2.4.4 Populating a Database

Example using ExecSQL program:
1. Connect to database using properties file
2. Open file with SQL statements
3. Execute each statement
4. Print result sets
5. Handle SQL exceptions
6. Close database connection

## 2.5 Query Execution

### 2.5.1 Prepared Statements

For parameterized queries:
1. Create prepared statement with ? placeholders:
   ```java
   String publisherQuery = "SELECT Books.Price, Books.Title " +
                          "FROM Books, Publishers " +
                          "WHERE Books.Publisher_Id = Publishers.Publisher_Id " +
                          "AND Publishers.Name = ?";
   PreparedStatement stat = conn.prepareStatement(publisherQuery);
   ```

2. Bind variables to values:
   ```java
   stat.setString(1, publisher); // 1 represents first ?
   ```

3. Execute statement:
   ```java
   ResultSet rs = stat.executeQuery();
   // or
   int r = stat.executeUpdate();
   ```

### 2.5.2 Reading and Writing LOBs (Large Objects)

**Reading a BLOB (Binary Large Object):**
```java
PreparedStatement stat = conn.prepareStatement("SELECT Cover FROM BookCovers WHERE ISBN=?");
stat.set(1, isbn);
ResultSet result = stat.executeQuery();
if (result.next()) {
    Blob coverBlob = result.getBlob(1);
    Image coverImage = ImageIO.read(coverBlob.getBinaryStream());
}
```

**Writing a BLOB:**
```java
Blob coverBlob = connection.createBlob();
int offset = 0;
OutputStream out = coverBlob.setBinaryStream(offset);
ImageIO.write(coverImage, "PNG", out);
PreparedStatement stat = conn.prepareStatement("INSERT INTO Cover VALUES (?, ?)");
stat.set(1, isbn);
stat.set(2, coverBlob);
stat.executeUpdate();
```

### 2.5.3 SQL Escapes

JDBC provides escape syntax for database-specific features:
- Date and time literals:
  ```
  {d '2008-01-24'}           // DATE
  {t '23:59:59'}             // TIME
  {ts '2008-01-24 23:59:59.999'} // TIMESTAMP
  ```
- Scalar functions
- Stored procedures
- Outer joins
- Escape character in LIKE clauses

### 2.5.4 Multiple Results

For handling multiple result sets:
```java
boolean isResult = stat.execute(command);
boolean done = false;
while (!done) {
    if (isResult) {
        ResultSet result = stat.getResultSet();
        // Process result set
    } else {
        int updateCount = stat.getUpdateCount();
        if (updateCount >= 0)
            // Process update count
        else
            done = true;
    }
    if (!done) isResult = stat.getMoreResults();
}
```

### 2.5.5 Retrieving Autogenerated Keys

For retrieving automatically generated keys:
```java
stmt.executeUpdate(insertStatement, Statement.RETURN_GENERATED_KEYS);
ResultSet rs = stmt.getGeneratedKeys();
if (rs.next()) {
    int key = rs.getInt(1);
    // Use the key
}
```

## 2.6 Scrollable and Updatable Result Sets

In traditional JDBC, result sets are forward-only and read-only by default. Scrollable and updatable result sets extend this functionality, allowing for more flexible data manipulation.

### 2.6.1 Scrollable Result Sets
Scrollable result sets allow you to move the cursor in multiple directions through the result set, not just forward.

Creating scrollable result sets:
```java
Statement stat = conn.createStatement(
    ResultSet.TYPE_SCROLL_INSENSITIVE, 
    ResultSet.CONCUR_READ_ONLY);
```

Navigating scrollable result sets:
- `first()`: Moves to first row
- `last()`: Moves to last row
- `previous()`: Moves to previous row
- `next()`: Moves to next row
- `absolute(n)`: Moves to row n
- `relative(n)`: Moves n rows relative to current position

### Scrollable Result Set Navigation Methods
```java
ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

// Moving through the result set
rs.first();       // Move to first row
rs.last();        // Move to last row
rs.beforeFirst(); // Move to position before first row
rs.afterLast();   // Move to position after last row
rs.absolute(5);   // Move to the 5th row
rs.relative(2);   // Move 2 rows forward from current position
rs.relative(-3);  // Move 3 rows backward from current position
rs.previous();    // Move to previous row

// Checking position
boolean isFirst = rs.isFirst();     // Is this the first row?
boolean isLast = rs.isLast();       // Is this the last row?
boolean isBeforeFirst = rs.isBeforeFirst(); // Are we before first row?
boolean isAfterLast = rs.isAfterLast();     // Are we after last row?
int rowNum = rs.getRow();           // Get current row number
```

### 2.6.2 Updatable Result Sets
Updatable result sets allow you to modify the underlying database through the result set itself.

Creating updatable result sets:
```java
Statement stat = conn.createStatement(
    ResultSet.TYPE_SCROLL_INSENSITIVE, 
    ResultSet.CONCUR_UPDATABLE);

// The SQL must select from a single table and must include primary key columns
ResultSet rs = stmt.executeQuery("SELECT emp_id, first_name, last_name, salary FROM employees");
```

Updating rows:
```java
while (rs.next()) {
    if (/* condition */) {
        double increase = /* calculation */;
        double price = rs.getDouble("Price");
        rs.updateDouble("Price", price + increase);
        rs.updateRow(); // Commit changes to database
    }
}
```

Inserting new rows:
```java
rs.moveToInsertRow();
rs.updateString("Title", title);
rs.updateString("ISBN", isbn);
rs.updateString("Publisher_Id", pubid);
rs.updateDouble("Price", price);
rs.insertRow();
rs.moveToCurrentRow();
```

Deleting rows:
```java
rs.deleteRow();
```

### Requirements for Updatable Result Sets
For a result set to be updatable, it must meet these conditions:

- The SQL query must select from only one table (no joins)
- The query must select the primary key column(s) of the table
- The query cannot use:

  - GROUP BY clauses
  - Aggregate functions (SUM, AVG, etc.)
  - DISTINCT keyword
  - Complex subqueries



### Performance Considerations

- **Memory usage:** Scrollable result sets may consume more memory as they need to cache results
Type sensitivity:

  - TYPE_SCROLL_INSENSITIVE: Doesn't see changes made by other transactions (better performance)
  - TYPE_SCROLL_SENSITIVE: Reflects changes from other transactions (more resource-intensive)

- **Database support:** Not all databases support all result set types and concurrency modes

## 2.7 Row Sets

Row Sets are an extension of the ResultSet interface that provide additional functionality and flexibility. While they share similarities with scrollable and updatable result sets, they offer some key differences and advantages.

### What Makes Row Sets Different


- **Disconnected Operation:** Unlike ResultSets which require an active database connection, certain types of RowSets can operate in a disconnected mode, allowing data manipulation without maintaining an open connection.
- **JavaBeans Component:** RowSets implement the JavaBeans component model, making them suitable for use in GUI applications and allowing property change notifications.
- **Serializable:** RowSets implement the Serializable interface, enabling them to be passed between different tiers of an application or stored for later use.
- **Simplified Programming Model:** RowSets often provide more intuitive methods and properties that simplify database operations.

### 2.7.1 Constructing Row Sets

Types of row sets (javax.sql.rowset package):
- `CachedRowSet`: Allows disconnected operation
- `WebRowSet`: Cached row set that can be saved to XML
- `FilteredRowSet` and `JoinRowSet`: Support lightweight operations (SQL SELECT and JOIN)
- `JdbcRowSet`: Thin wrapper around ResultSet

Creating row sets (Java 7+):
```java
RowSetFactory factory = RowSetProvider.newFactory();
CachedRowSet crs = factory.createCachedRowSet();
```

Alternative:
```java
CachedRowSet crs = new com.sun.rowset.CachedRowSetImpl();
```

### 2.7.2 Cached Row Sets

Populating from ResultSet:
```java
ResultSet result = /* query execution */;
CachedRowSet crs = new com.sun.rowset.CachedRowSetImpl();
crs.populate(result);
conn.close(); // Safe to close connection
```

Automatic connection:
```java
crs.setURL("jdbc:derby://localhost:1527/COREJAVA");
crs.setUsername("dbuser");
crs.setPassword("secret");
crs.setCommand("SELECT * FROM Books WHERE PUBLISHER = ?");
crs.setString(1, publisherName);
crs.execute(); // Connects, queries, populates, disconnects
```

Paging through large result sets:
```java
crs.setCommand(command);
crs.setPageSize(20);
crs.execute();
// Later, get next batch:
crs.nextPage();
```

Writing changes back to database:
```java
// With connection:
crs.acceptChanges(conn);
// or using connection properties:
crs.acceptChanges();
```

### 2.7.3 Key Advantages Over Regular ResultSets

- **Offline Data Processing:** CachedRowSet allows data to be processed without holding database connections.
- **Data Transfer:** RowSets (especially WebRowSet) can easily transfer data between application tiers or even different applications.
- **XML Integration:** WebRowSet provides built-in XML serialization and deserialization.
- **Complex Data Operations:** JoinRowSet and FilteredRowSet offer advanced data manipulation capabilities without complex SQL.
- **Event Notification:** RowSets can notify listeners when their data changes.

RowSets complement scrollable and updatable result sets rather than replacing them. While both provide data navigation and modification capabilities, RowSets add disconnected operation, serialization, and more sophisticated data manipulation features that are particularly valuable in distributed applications and those with limited connection resources.
