# Java RMI (Remote Method Invocation) - Teaching Notes

## 1. Introduction to RMI

Remote Method Invocation (RMI) is an API that provides a mechanism to create distributed applications in Java. It allows an object to invoke methods on an object running in another Java Virtual Machine (JVM), which could be on the same computer or a different one across a network.

### Key Concepts:

- **Remote Object**: An object whose methods can be invoked from another Virtual Machine
- **Remote Interface**: A Java interface that declares the methods of a remote object
- **Remote Method Invocation**: The action of invoking a method of a remote interface on a remote object

## 2. The Roles of Client and Server in Distributed Systems

In traditional client/server models:
1. The client translates the request to an intermediary transmission format
2. The client sends the request data to the server
3. The server parses the request format, computes the response, and formats it for transmission
4. The client parses the response and displays it to the user

### Proxy-Based Solution in RMI:

RMI installs proxies on both the client and server sides:
- **Client Side**: Client makes a regular method call to a proxy, which handles communication with the server
- **Server Side**: Server proxy communicates with client proxy and makes regular method calls to the server object


### Communication Mechanisms Between Proxies:

1. **RMI**: Supports method calls between distributed Java objects
2. **CORBA**: Supports method calls between objects of any programming language using IIOP
3. **SOAP**: Uses XML-based communication formats for web services

## 3. Remote Method Calls

When a client makes a remote method call:
1. Method parameters must be shipped to the remote machine
2. The server must execute the method
3. The return value must be shipped back to the client

### Terminology:
- **Client Object**: The object that makes the remote call
- **Server Object**: The remote object that processes the call
- **Client**: The computer running Java code that calls the remote method
- **Server**: The computer hosting the object that processes the call

### Registration Process:
1. A server registers its remote objects with a naming service (rmiregistry)
2. Each registered remote object gets a unique URL
3. A client can use the naming service to look up a URL and obtain a remote object reference

## 4. Stubs and Skeletons

### Stubs (Client Side):
When client code invokes a remote method, it actually calls an ordinary method on a proxy object called a **stub**.

**Stub Functions:**
- Initiates a call to the remote object
- Provides description of the method to be called
- Marshals (packages and encodes) the parameters for transport
- Unmarshals the return value or exception from the server
- Returns value to the caller

### Skeletons (Server Side):
A proxy object on the server side is called a **skeleton**.

**Skeleton Functions:**
- Unmarshals the parameters
- Locates the object to be called
- Calls the desired method
- Captures and marshals the return value or exception of the call
- Returns marshalled data back to the stub

### Parameter Marshalling:
The process of encoding parameters into a format suitable for transport from one machine to another.

## 5. The RMI Programming Model

### 5.1 Interfaces and Implementations

Remote objects capabilities are expressed in interfaces shared between client and server:

```java
// Remote interface definition
import java.rmi.*;

public interface Warehouse extends Remote {
    double getPrice(String description) throws RemoteException;
}
```

```java
// Implementation of the remote interface
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class WarehouseImpl extends UnicastRemoteObject implements Warehouse {
    private Map<String, Double> prices;
    
    public WarehouseImpl() throws RemoteException {
        prices = new HashMap<>();
        prices.put("Blackwell Toaster", 24.95);
        prices.put("ZapXpress Microwave Oven", 49.95);
    }
    
    public double getPrice(String description) throws RemoteException {
        Double price = prices.get(description);
        return price == null ? 0 : price;
    }
}
```

### 5.2 The RMI Registry

The RMI Registry provides a bootstrap service for locating the first remote object:

- **URL Format**: `rmi://hostname:port/objectname`
- **Default Values**: hostname=localhost, port=1099

```java
// Server-side registration
WarehouseImpl centralWarehouse = new WarehouseImpl();
Context namingContext = new InitialContext();
namingContext.bind("rmi:central_warehouse", centralWarehouse);
```

```java
// Client-side lookup
String url = "rmi://localhost/central_warehouse";
Warehouse centralWarehouse = (Warehouse) namingContext.lookup(url);
```

### 5.3 Complete Example

#### Server Program:

```java
import javax.naming.*;
import java.rmi.*;

public class WarehouseServer {
    public static void main(String[] args) throws RemoteException, NamingException {
        System.out.println("Constructing server implementation...");
        WarehouseImpl centralWarehouse = new WarehouseImpl();
        
        System.out.println("Binding server implementation to registry...");
        Context namingContext = new InitialContext();
        namingContext.bind("rmi:central_warehouse", centralWarehouse);
        
        System.out.println("Waiting for invocations from clients...");
    }
}
```

#### Client Program:

```java
import java.rmi.*;
import java.util.*;
import javax.naming.*;

public class WarehouseClient {
    public static void main(String[] args) throws NamingException, RemoteException {
        Context namingContext = new InitialContext();
        
        // List available RMI services
        System.out.print("RMI registry bindings: ");
        Enumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
        while (e.hasMoreElements())
            System.out.println(e.nextElement().getName());
        
        // Look up remote warehouse object
        String url = "rmi://localhost/central_warehouse";
        Warehouse centralWarehouse = (Warehouse)namingContext.lookup(url);
        
        // Call remote method
        String descr = "Blackwell Toaster";
        double price = centralWarehouse.getPrice(descr);
        System.out.println(descr + ": " + price);
    }
}
```

### 5.4 Deploying an RMI Application

Directory structure for deployment:
```
server/
  WarehouseServer.class
  Warehouse.class
  WarehouseImpl.class
  
client/
  WarehouseClient.class
  Warehouse.class
  
download/
  Warehouse.class
```

### 5.5 Logging RMI Activity

To enable basic logging:
```
-Djava.rmi.server.logCalls=true
```

For additional logging, create a `logging.properties` file:
```
handlers=java.util.logging.ConsoleHandler.level=FINE
java.util.logging.ConsoleHandler.level=FINE
java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter
```

## 6. Parameters and Return Values in Remote Methods

When values pass between virtual machines, there are two cases:

### 6.1 Transferring Remote Objects
- Contains network address and unique identifier for the remote object
- Information is encapsulated in a stub object
- Method calls on remote references are slower and potentially less reliable than local calls

### 6.2 Transferring Nonremote Objects
Two mechanisms are used:
1. Objects implementing the Remote interface are transferred as remote references
2. Objects implementing the Serializable interface (but not Remote) are copied using serialization

Example of enhanced remote interface:
```java
import java.util.*;

public interface Warehouse extends Remote {
    double getPrice(String description) throws RemoteException;
    Product getProduct(List<String> keywords) throws RemoteException;
}
```

## 7. Exercise: Building a Simple RMI Application

### Task:
Create a simple calculator application where the client can send two numbers to the server, and the server performs operations (add, subtract, multiply, divide) and returns the result.

### Step 1: Define the Remote Interface
```java
import java.rmi.*;

public interface Calculator extends Remote {
    double add(double a, double b) throws RemoteException;
    double subtract(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;
}
```

### Step 2: Implement the Remote Interface
```java
import java.rmi.*;
import java.rmi.server.*;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    public CalculatorImpl() throws RemoteException {
        super();
    }
    
    public double add(double a, double b) throws RemoteException {
        System.out.println("Adding " + a + " and " + b);
        return a + b;
    }
    
    public double subtract(double a, double b) throws RemoteException {
        System.out.println("Subtracting " + b + " from " + a);
        return a - b;
    }
    
    public double multiply(double a, double b) throws RemoteException {
        System.out.println("Multiplying " + a + " by " + b);
        return a * b;
    }
    
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) throw new RemoteException("Division by zero");
        System.out.println("Dividing " + a + " by " + b);
        return a / b;
    }
}
```

### Step 3: Create the Server
```java
import java.rmi.*;
import javax.naming.*;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            System.out.println("Creating Calculator implementation...");
            CalculatorImpl calculator = new CalculatorImpl();
            
            System.out.println("Binding to registry...");
            Context namingContext = new InitialContext();
            namingContext.bind("rmi:calculator", calculator);
            
            System.out.println("Server ready to accept requests");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
```

### Step 4: Create the Client
```java
import java.rmi.*;
import javax.naming.*;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Context namingContext = new InitialContext();
            String url = "rmi://localhost/calculator";
            
            System.out.println("Looking up calculator...");
            Calculator calculator = (Calculator)namingContext.lookup(url);
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter first number: ");
            double a = scanner.nextDouble();
            System.out.print("Enter second number: ");
            double b = scanner.nextDouble();
            
            System.out.println("Addition result: " + calculator.add(a, b));
            System.out.println("Subtraction result: " + calculator.subtract(a, b));
            System.out.println("Multiplication result: " + calculator.multiply(a, b));
            
            try {
                System.out.println("Division result: " + calculator.divide(a, b));
            } catch (RemoteException e) {
                System.out.println("Error in division: " + e.getMessage());
            }
            
            scanner.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
```

## 8. Summary

- **RMI** enables method calls between Java objects in different JVMs
- The mechanism uses **stubs** on the client side and **skeletons** on the server side
- Objects are passed either as **remote references** or **serialized copies**
- The **RMI Registry** provides a naming service for bootstrapping remote objects
- Remote interfaces must extend the **Remote interface** and throw **RemoteException**
- Implementations typically extend **UnicastRemoteObject**

## 9. Additional Resources

- Oracle's Java RMI Tutorial: https://docs.oracle.com/javase/tutorial/rmi/
- JavaTpoint RMI Tutorial: https://www.javatpoint.com/RMI
- EJB Tutorial on RMI: http://www.ejbtutorial.com/java-rmi/new-easy-tutorial-for-java-rmi-using-eclipse
