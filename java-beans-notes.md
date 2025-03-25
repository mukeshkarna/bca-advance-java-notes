# Java Beans

## 1. Introduction to Java Beans

A **Java Bean** is a reusable software component that can be used in a variety of different environments. Java Beans are designed according to a specific architecture that allows them to work together as building blocks for creating larger applications.

### Key Characteristics:
- A Java Bean is a special type of class that encapsulates several objects into a single object
- They follow specific design conventions
- Can perform simple functions (like spell checking) or complex operations (like portfolio forecasting)
- They allow for building complex systems from modular components
- Java Beans can be supplied by you or different vendors

### Core Conventions of Java Beans:
- Must have a public no-argument constructor
- Should provide getter and setter methods for properties
- Should implement `java.io.Serializable` interface to save/restore state

## 2. Components of Java Beans

Java Beans consist of four main components:

### 2.1 Properties (Data Members)
Properties are named attributes of a bean that determine its appearance, behavior, and state.
- Examples: color, label, font, size
- Properties can be read, write, read-only, or write-only

### 2.2 Methods
Methods in Java Beans are the same as normal Java methods in a class.
- No specific naming conventions (except for property accessors and mutators)
- All properties should have accessor (getter) and mutator (setter) methods

### 2.3 Events
Events in Java Beans follow the same mechanism as in SWING/AWT event handling.
- Beans can generate events and send them to other objects

### 2.4 Persistence
- Implemented using the Serializable interface
- Enables beans to store their state

## 3. Advantages of Java Beans

- Benefits from Java's "write-once, run-anywhere" paradigm
- Properties, events, and methods can be exposed to other applications
- Easy reuse of software components
- Can be used in different environments
- Dynamic and customizable
- Can be deployed in network systems
- Can register to receive and generate events
- Configuration settings can be saved and restored later

## 4. Disadvantages of Java Beans

- Java Beans are mutable, so they can't take advantage of immutable objects
- Creating setter and getter methods for each property separately may lead to boilerplate code

## 5. Introspection

Introspection is the process by which builder tools discover which properties, methods, and events a bean supports.

### Two Methods of Introspection:
1. **Simple naming conventions**: Using standardized naming patterns that allow automatic discovery
2. **BeanInfo interface**: Explicitly supplying information through a class that extends the BeanInfo interface

## 6. Design Patterns for Properties

### 6.1 Simple Properties
A simple property has a single value and follows these patterns:
```java
public T getN()
public void setN(T arg)
```
Where:
- N is the name of the property
- T is its type

**Example:**
```java
private double depth, height, width;

public double getDepth() {
    return depth;
}

public void setDepth(double depth) {
    this.depth = depth;
}

public double getHeight() {
    return height;
}

public void setHeight(double height) {
    this.height = height;
}

public double getWidth() {
    return width;
}

public void setWidth(double width) {
    this.width = width;
}
```

### 6.2 Indexed Properties
An indexed property consists of multiple values and follows these patterns:
```java
public T getN(int index);
public void setN(int index, T value);
public T[] getN();
public void setN(T values[]);
```

**Example:**
```java
private double data[];

public double getData(int index) {
    return data[index];
}

public void setData(int index, double value) {
    data[index] = value;
}

public double[] getData() {
    return data;
}

public void setData(double[] values) {
    data = new double[values.length];
    System.arraycopy(values, 0, data, 0, values.length);
}
```

## 7. Design Patterns for Events

Beans can generate events and send them to other objects using these patterns:
```java
public void addTListener(TListener eventListener)
public void addTListener(TListener eventListener) throws java.util.TooManyListenersException
public void removeTListener(TListener eventListener)
```

**Example:**
```java
public void addTemperatureListener(TemperatureListener tl) {
    // Implementation
}

public void removeTemperatureListener(TemperatureListener tl) {
    // Implementation
}
```

## 8. Using the BeanInfo Interface

The BeanInfo interface allows explicit control over what information about a Bean is available. The BeanInfo interface is a key part of the JavaBeans API. It provides a standardized way to expose information about a JavaBean’s properties, methods, and events.
The BeanInfo interface is a special interface that allows developers to provide metadata about their JavaBean to other software components. This metadata includes information about the bean’s properties, methods, and events, as well as its visual appearance and behavior. Developers can make their JavaBeans more usable and understandable by other software components.

### Benefits of using the BeanInfo interface
The benefits of using BeanInfo interface has several benefits, including:

- **Improved usability:** It makes it easier for other software components to work with the bean. Because it provides the metadata about a JavaBean’s properties, methods, and events,
- **Standardization:** It is part of the JavaBeans API. This means that it provides a standardized way for developers to provide metadata about their beans.
- **Customization:** Developers can easily customize the visual appearance and behavior of their JavaBeans. It makes them more user-friendly.
- **Flexibility:** It allows developers to provide different sets of metadata for different contexts. It makes their JavaBeans more flexible and adaptable to different use cases.

Key methods:
```java
PropertyDescriptor[] getPropertyDescriptors()
EventSetDescriptor[] getEventSetDescriptors()
MethodDescriptor[] getMethodDescriptors()
```

### Implement the BeanInfo interface
To implement the BeanInfo interface, we need to create a separate class that provides metadata about our JavaBean. This class should have the same name as our JavaBean class, but with the suffix BeanInfo. For example, if our JavaBean class is called MyBean, our BeanInfo class should be called MyBeanBeanInfo.

In our BeanInfo class, we can override several methods to provide metadata about our JavaBean. These methods include getPropertyDescriptors(), getMethodDescriptors(), and getEventSetDescriptors(), among others.
```java
public class MyBean {
    private String name;
    private int age;
    
    // Getters and setters for name and age properties
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}

public class MyBeanBeanInfo implements BeanInfo {
    private final PropertyDescriptor[] propertyDescriptors;
    
    public MyBeanBeanInfo() throws IntrospectionException {
        PropertyDescriptor nameDescriptor = new PropertyDescriptor("name", MyBean.class);
        PropertyDescriptor ageDescriptor = new PropertyDescriptor("age", MyBean.class);
        this.propertyDescriptors = new PropertyDescriptor[]{nameDescriptor, ageDescriptor};
    }
    
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return propertyDescriptors;
    }
    
    // Other BeanInfo interface methods can be implemented as needed
}
```
In the example above, we have a simple JavaBean called “MyBean” with two properties: “name” and “age”. We then create a separate class called “MyBeanBeanInfo” that implements the BeanInfo interface.

In the MyBeanBeanInfo class, we define two PropertyDescriptors for the “name” and “age” properties of the MyBean class. We then return an array containing these PropertyDescriptors from the getPropertyDescriptors() method, which is required by the BeanInfo interface.

Other methods of the BeanInfo interface can also be implemented in the MyBeanBeanInfo class as needed, such as getMethodDescriptors() and getEventSetDescriptors().


When creating a class that implements BeanInfo, you must name the class `bnameBeanInfo`, where `bname` is the name of the Bean.

## 9. Bound and Constrained Properties

### 9.1 Bound Properties
- Generate an event when the property is changed
- Inform listeners about changes in values
- Often used in graphical user interfaces (GUIs) to synchronize the state of components like buttons, text fields, or sliders.
- Implemented using the PropertyChangeSupport class
- Always registered with an external event listener

**Required methods:**
```java
public void addPropertyChangeListener(PropertyChangeListener p) {
    changes.addPropertyChangeListener(p);
}

public void removePropertyChangeListener(PropertyChangeListener p) {
    changes.removePropertyChangeListener(p);
}
```

Following is a simplified example of a bound property in a JavaBean:

```java
public class MyBean {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        int oldValue = this.value;
        this.value = newValue;
        
        // Notify listeners about the property change
        firePropertyChange("value", oldValue, newValue);
    }

    // Methods for adding and removing property change listeners
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        // Add listener logic here
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        // Remove listener logic here
    }

    // Method for firing property change events
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        // Fire event to registered listeners
        // Include propertyName, oldValue, and newValue in the event
    }
}
```

### 9.2 Constrained Properties
- Generate an event when an attempt is made to change their value
- Implemented using the PropertyChangeEvent class
- Other objects can veto (reject) the proposed change
- Protected from being changed by other JavaBeans

**Method prototypes:**
```java
// Getter
public string get<ConstrainedPropertyName>()

// Setter
public string set<ConstrainedPropertyName>(String str) throws PropertyVetoException
```


For example, we might use constraints properties to specify that a numeric property must be within a certain range, or that a string property must follow a particular format.

Following is a simple example using annotations to define constraints for a JavaBean property:
```java
public class Employee {
    private int age;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must be less than 65")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

```
In this example, the @Min and @Max annotations are used to specify constraints on the age property, ensuring that it falls within a specific range.



## 10. Persistence

Persistence allows beans to save their properties and events to non-volatile storage and retrieve them later. Persistence refers to the ability of an application to store and retrieve data from a database. Java Beans Persistence is a technology that helps Java developers to achieve this task easily and efficiently.

### Two forms of persistence:
1. **Automatic persistence**: Uses Java's built-in serialization mechanism
2. **External persistence**: Uses custom classes to control how bean state is stored and retrieved

**Implementation:**
- If a bean inherits from Component class, it's automatically Serializable
- Use the `transient` keyword for data members that should not be serialized


### How does Persistence in Java Beans work?
Java Beans Persistence works by mapping Java objects to database tables. This is done using a process called object-relational mapping (ORM). ORM is a technique that maps the data between a relational database and an object-oriented programming language. it provides a set of annotations that developers can use to define the mapping between Java objects and database tables.

Following is an example of how Java Beans Persistence works:
```java
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String department;

    // getters and setters
}
```
In the code above, 
- we have defined an Employee class with three fields: id, name, and department. 
- The @Entity annotation specifies that this class should be mapped to a database table. 
- The @Table annotation specifies the name of the table in the database. 
- The @Id annotation specifies that the id field should be used as the primary key for the table.
- The @GeneratedValue annotation specifies that the id field should be generated automatically.


```java
// A serializable bean
public class UserPreferences implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private boolean darkMode;
    private int fontSize;
    private Map<String, String> customSettings = new HashMap<>();
    
    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public boolean isDarkMode() { return darkMode; }
    public void setDarkMode(boolean darkMode) { this.darkMode = darkMode; }
    
    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }
    
    public Map<String, String> getCustomSettings() { return customSettings; }
    public void setCustomSettings(Map<String, String> settings) { this.customSettings = settings; }
}

// Saving the bean
public void savePreferences(UserPreferences prefs, String filename) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
        out.writeObject(prefs);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Loading the bean
public UserPreferences loadPreferences(String filename) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
        return (UserPreferences) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        return new UserPreferences(); // Return default if loading fails
    }
}
```

### Benefits of Java bean persistence
Java Beans Persistence has many benefits some of the important are given below:

- **Simplified data access:** It provides a standard way to access data from a database. This means that developers can write less code and focus on their business logic.
- **Portability:** It is platform-independent, which means that it can run on any operating system that supports Java.
- **Increased productivity:** It reduces the amount of code that developers need to write, which increases productivity and reduces development time.
- **Easy maintenance:** It provides a simple and easy-to-maintain codebase.

## 11. Customizers
Customizers are an essential aspect of Java Beans, allowing developers to modify an object’s appearance and behavior. 
Customizers in Java Bean are classes that allow developers to modify the appearance and behavior of an object at design time. They provide a graphical user interface (GUI) that allows developers to modify an object’s properties and view the changes in real-time. They are an essential tool for creating dynamic user interfaces in Java Beans.

- Help other developers configure the Bean
- Can provide step-by-step guides for using the component in specific contexts
- Can include online documentation



```java
// The bean we want to customize
public class MessageBean implements Serializable {
    private String message = "Hello World";
    private int fontSize = 12;
    private boolean bold = false;
    
    // Getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }
    
    public boolean isBold() { return bold; }
    public void setBold(boolean bold) { this.bold = bold; }
}

// The customizer for MessageBean
public class MessageBeanCustomizer extends JPanel implements Customizer {
    private MessageBean bean;
    private JTextField messageField;
    private JSpinner fontSizeSpinner;
    private JCheckBox boldCheckbox;
    
    public MessageBeanCustomizer() {
        setLayout(new GridLayout(3, 2));
        
        // Create components
        messageField = new JTextField();
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 8, 72, 1));
        boldCheckbox = new JCheckBox("Bold");
        
        // Add components to panel
        add(new JLabel("Message:"));
        add(messageField);
        add(new JLabel("Font Size:"));
        add(fontSizeSpinner);
        add(new JLabel("Style:"));
        add(boldCheckbox);
        
        // Add action listeners
        messageField.addActionListener(e -> {
            bean.setMessage(messageField.getText());
        });
        
        fontSizeSpinner.addChangeListener(e -> {
            bean.setFontSize((Integer)fontSizeSpinner.getValue());
        });
        
        boldCheckbox.addActionListener(e -> {
            bean.setBold(boldCheckbox.isSelected());
        });
    }
    
    @Override
    public void setObject(Object obj) {
        this.bean = (MessageBean)obj;
        
        // Update UI components with bean's current values
        messageField.setText(bean.getMessage());
        fontSizeSpinner.setValue(bean.getFontSize());
        boldCheckbox.setSelected(bean.isBold());
    }
}

// To register the customizer with the bean
public class MessageBeanBeanInfo extends SimpleBeanInfo {
    @Override
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor descriptor = new BeanDescriptor(MessageBean.class, MessageBeanCustomizer.class);
        return descriptor;
    }
}
```

## 12. The Java Beans API

The Java Beans functionality is provided by classes and interfaces in the `java.beans` package. Key classes include:

### 12.1 Introspector
Provides static methods that support introspection, most notably `getBeanInfo()`:
```java
static BeanInfo getBeanInfo(Class<?> bean) throws IntrospectionException
```

```java
import java.beans.*;

public class BeanIntrospectionExample {
    public static void main(String[] args) {
        try {
            // Get BeanInfo for our bean class
            BeanInfo beanInfo = Introspector.getBeanInfo(UserPreferences.class);
            
            // Get property descriptors
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            
            System.out.println("Properties of UserPreferences bean:");
            for (PropertyDescriptor pd : propertyDescriptors) {
                String propertyName = pd.getName();
                Class<?> propertyType = pd.getPropertyType();
                Method readMethod = pd.getReadMethod();
                Method writeMethod = pd.getWriteMethod();
                
                System.out.println("Property: " + propertyName);
                System.out.println("  Type: " + propertyType.getName());
                System.out.println("  Getter: " + (readMethod != null ? readMethod.getName() : "none"));
                System.out.println("  Setter: " + (writeMethod != null ? writeMethod.getName() : "none"));
                System.out.println();
            }
            
            // Using the property descriptor to get and set values
            UserPreferences prefs = new UserPreferences();
            
            // Find the 'username' property descriptor
            PropertyDescriptor usernamePD = null;
            for (PropertyDescriptor pd : propertyDescriptors) {
                if ("username".equals(pd.getName())) {
                    usernamePD = pd;
                    break;
                }
            }
            
            if (usernamePD != null) {
                // Set value using the write method
                usernamePD.getWriteMethod().invoke(prefs, "johndoe");
                
                // Get value using the read method
                String username = (String) usernamePD.getReadMethod().invoke(prefs);
                System.out.println("Username set to: " + username);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 12.2 PropertyDescriptor
Describes characteristics of a Bean property with methods like:
- `isBound()`: Determines if a property is bound
- `isConstrained()`: Determines if a property is constrained
- `getName()`: Obtains the name of a property

### 12.3 EventSetDescriptor
Represents a set of Bean events with methods like:
- `getAddListenerMethod()`: Obtains the method used to add listeners
- `getRemoveListenerMethod()`: Obtains the method used to remove listeners
- `getListenerType()`: Obtains the type of a listener
- `getName()`: Obtains the name of an event set

### 12.4 MethodDescriptor
Represents a Bean method with methods like:
- `getName()`: Obtains the name of the method
- `getMethod()`: Obtains information about the method

## 13. Practical Example: Colors Bean

Here's a complete example demonstrating various Bean concepts:

### Colors.java (The Bean)
```java
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
public class Colors extends Canvas implements Serializable {
    transient private Color color;
    private boolean rectangular;
    
    // Constructor initializes the bean
    public Colors() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                change();
            }
        });
        
        rectangular = false;
        setSize(200, 100);
        change();
    }
    
    // Getter for the rectangular property
    public boolean getRectangular() {
        return rectangular;
    }
    
    // Setter for the rectangular property
    public void setRectangular(boolean flag) {
        this.rectangular = flag;
        repaint();
    }
    
    // Method to change the color randomly
    public void change() {
        color = randomColor();
        repaint();
    }
    
    // Helper method to generate random colors
    private Color randomColor() {
        int r = (int) (255 * Math.random());
        int g = (int) (255 * Math.random());
        int b = (int) (255 * Math.random());
        return new Color(r, g, b);
    }
    
    // Override the paint method to draw either a rectangle or oval
    public void paint(Graphics g) {
        Dimension d = getSize();
        int h = d.height;
        int w = d.width;
        g.setColor(color);
        if (rectangular) {
            g.fillRect(0, 0, w - 1, h - 1);
        } else {
            g.fillOval(0, 0, w - 1, h - 1);
        }
    }
}
```

### ColorsBeanInfo.java (The BeanInfo class)
```java
import java.beans.*;

public class ColorsBeanInfo extends SimpleBeanInfo {
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor rectangular = new PropertyDescriptor("rectangular", Colors.class);
            PropertyDescriptor pd[] = {rectangular};
            return pd;
        } catch (Exception e) {
            System.out.println("Exception caught. " + e);
        }
        return null;
    }
}
```

### IntrospectorDemo.java (Demonstrating introspection)
```java
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
public class IntrospectorDemo {
    public static void main(String args[]) {
        try {
            // Load the Colors class
            Class c = Class.forName("Colors");
            
            // Get information about the bean
            BeanInfo beanInfo = Introspector.getBeanInfo(c);
            
            // Display property information
            System.out.println("Properties:");
            PropertyDescriptor propertyDescriptor[] = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptor.length; i++) {
                System.out.println("\t" + propertyDescriptor[i].getName());
            }
            
            // Display event information
            System.out.println("Events:");
            EventSetDescriptor eventSetDescriptor[] = beanInfo.getEventSetDescriptors();
            for (int i = 0; i < eventSetDescriptor.length; i++) {
                System.out.println("\t" + eventSetDescriptor[i].getName());
            }
        } catch (Exception e) {
            System.out.println("Exception caught. " + e);
        }
    }
}
```

Output
```bash
Properties:
    rectangular
    background
    foreground
    font
    ...
Events:
    mouseListener
    componentListener
    keyListener
    ...
```

### Using the Colors Bean
In NetBeans:
1. Go to Tools → Palette
2. Click "Add from JAR" and add the "Colors" class
3. Create a new Frame (FrameColors.java)
4. In the left side "Palette", look for the "Beans" option
5. Choose the Colors Bean and drag it to your Frame

## 14. Java Beans vs. Other Java Objects

- **POJO (Plain Old Java Object)**: Regular Java objects without special functions or inheritance
- **DTO (Data Transfer Object)**: Objects created specifically to transport data, must be serializable
- **Entity**: Objects stored in a database, without business logic
- **DAO (Data Access Object)**: Used to save objects to and retrieve them from a database

## 15. Rules for Writing a JavaBean

1. The class must be public
2. It must have a no-argument default constructor
3. It must implement the Serializable interface
