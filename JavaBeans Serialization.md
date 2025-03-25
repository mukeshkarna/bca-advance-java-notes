# JavaBeans Serialization
Serialization for JavaBeans allows the state of a bean to be saved to storage and later restored. This enables persistence, distribution, and data transfer between different Java applications or systems.

## How JavaBeans Serialization Works
### 1. Requirements for Serialization
For a JavaBean to be serializable:

- The class must implement the java.io.Serializable interface
- All non-transient instance variables must either be serializable or marked as transient

```java
public class CustomerBean implements Serializable {
    // This will be serialized
    private String name;
    
    // This won't be serialized
    transient private TemporaryData tempData;
    
    // Serial version UID helps with version compatibility
    private static final long serialVersionUID = 1L;
    
    // Getters and setters...
}
```

### 2. The Serialization Process
When a JavaBean is serialized:

- The Java runtime creates an ObjectOutputStream connected to the output destination
- The writeObject() method is called to serialize the object
- The Java runtime writes the class description (metadata)
- It then recursively writes all non-transient fields
- Special hooks like writeObject() methods may be called if present

```java
// Writing a bean to a file
CustomerBean customer = new CustomerBean();
customer.setName("John Doe");

try (FileOutputStream fileOut = new FileOutputStream("customer.ser");
     ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
    out.writeObject(customer);
}
```

### 3. The Deserialization Process
When a JavaBean is deserialized:

- The Java runtime creates an ObjectInputStream connected to the input source
- The readObject() method is called to deserialize the object
- The class is loaded if not already available
- A new object is created without calling constructors
- All fields are populated with the serialized data
- Special hooks like readObject() methods may be called if present

```java
// Reading a bean from a file
CustomerBean customer;
try (FileInputStream fileIn = new FileInputStream("customer.ser");
     ObjectInputStream in = new ObjectInputStream(fileIn)) {
    customer = (CustomerBean) in.readObject();
}
```
## Practical Examples
 
### Basic Bean Serialization
```java
public class AddressBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String street;
    private String city;
    private String state;
    private String zipCode;
    
    // Getters and setters
    
    // Serialization example
    public static void main(String[] args) {
        AddressBean address = new AddressBean();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setZipCode("90210");
        
        // Save to file
        try {
            FileOutputStream fileOut = new FileOutputStream("address.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(address);
            out.close();
            fileOut.close();
            System.out.println("Address bean saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Load from file
        try {
            FileInputStream fileIn = new FileInputStream("address.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            AddressBean loadedAddress = (AddressBean) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Address loaded: " + loadedAddress.getStreet());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

### Serialization with Property Change Support
```java
public class ProductBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private double price;
    private transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    // Getters and setters with property change events
    public String getName() { return name; }
    
    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        pcs.firePropertyChange("name", oldValue, name);
    }
    
    public double getPrice() { return price; }
    
    public void setPrice(double price) {
        double oldValue = this.price;
        this.price = price;
        pcs.firePropertyChange("price", oldValue, price);
    }
    
    // Property change listener support
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    
    // Custom serialization to handle the transient field
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Perform default deserialization
        in.defaultReadObject();
        
        // Restore transient fields
        pcs = new PropertyChangeSupport(this);
    }
}
```

Serialization is a key feature of JavaBeans that enables persistence, distribution, and state transfer, making beans highly reusable across different applications and components.
