# Basic Swing Components
## Advanced Java - Chapter 1
### Duration: 12 Hours

---

## Overview of Swing

* Java Swing is a GUI (Graphical User Interface) toolkit for Java
* Part of Java Foundation Classes (JFC)
* Built on top of AWT (Abstract Window Toolkit)
* Platform-independent and lightweight
* Follows MVC (Model-View-Controller) architecture

---

## Setting Up a Swing Application

### Basic Structure

```java
import javax.swing.*;

public class SwingApplication {
    public static void main(String[] args) {
        // Create and set up the window
        JFrame frame = new JFrame("Swing Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Add components here
        
        // Display the window
        frame.setVisible(true);
    }
}
```

---

## JFrame

* Primary container for Swing applications
* Represents a window with title bar and borders
* Can contain other Swing components

### Key Methods
* `setSize(width, height)` - Sets the window dimensions
* `setTitle(String)` - Sets the window title
* `setDefaultCloseOperation(int)` - Defines what happens when the window is closed
* `setLayout(LayoutManager)` - Sets the layout manager
* `setVisible(boolean)` - Shows or hides the window

---

## Layout Managers

Control how components are arranged within containers

* **FlowLayout** - Components in a row, wrapping to next line as needed
* **BorderLayout** - Divides container into five areas: North, South, East, West, Center
* **GridLayout** - Divides container into rows and columns of equal-sized cells
* **BoxLayout** - Arranges components in a single row or column
* **GridBagLayout** - Flexible grid layout with variable cell sizes

---

## Example: Using Different Layouts

```java
// FlowLayout Example
JPanel panel = new JPanel();
panel.setLayout(new FlowLayout());
panel.add(new JButton("Button 1"));
panel.add(new JButton("Button 2"));

// BorderLayout Example
JPanel panel2 = new JPanel(new BorderLayout());
panel2.add(new JButton("North"), BorderLayout.NORTH);
panel2.add(new JButton("South"), BorderLayout.SOUTH);
panel2.add(new JButton("East"), BorderLayout.EAST);
panel2.add(new JButton("West"), BorderLayout.WEST);
panel2.add(new JButton("Center"), BorderLayout.CENTER);
```

---

## JLabel

* Displays text or images
* Can be used to provide information or description for other components

```java
// Creating a simple text label
JLabel textLabel = new JLabel("This is a text label");

// Creating a label with an image
ImageIcon icon = new ImageIcon("image.png");
JLabel imageLabel = new JLabel(icon);

// Label with both text and image
JLabel combinedLabel = new JLabel("Image Label", icon, JLabel.CENTER);
```

---

## JButton

* Clickable button that triggers an action when pressed
* Can display text, image, or both

```java
// Simple text button
JButton button = new JButton("Click Me");

// Button with an image
ImageIcon icon = new ImageIcon("icon.png");
JButton imageButton = new JButton(icon);

// Adding an action listener
button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button clicked!");
    }
});

// Using lambda (Java 8+)
button.addActionListener(e -> System.out.println("Button clicked!"));
```

---

## Action Listeners

* Interface for handling component events
* Essential for making interactive applications

```java
// Traditional approach
JButton button = new JButton("Save");
button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // Code to execute when button is clicked
        saveData();
    }
});

// Lambda approach (Java 8+)
JButton button = new JButton("Save");
button.addActionListener(e -> saveData());
```

---

## JTextField

* Single-line text input component
* Used for getting short text input from users

```java
// Create a text field with 20 columns
JTextField textField = new JTextField(20);

// Text field with initial text
JTextField nameField = new JTextField("Enter your name", 20);

// Getting text from a text field
String input = textField.getText();

// Setting text programmatically
textField.setText("New text");

// Handling text changes
textField.addActionListener(e -> processInput(textField.getText()));
```

---

## JTextArea

* Multi-line text input component
* Used for longer text input

```java
// Create a text area with 5 rows and 20 columns
JTextArea textArea = new JTextArea(5, 20);

// Setting and getting text
textArea.setText("Initial text\nwith multiple lines");
String content = textArea.getText();

// Making the text area scrollable
JScrollPane scrollPane = new JScrollPane(textArea);
frame.add(scrollPane); // Add scrollPane to container instead of textArea

// Enable/disable line wrapping
textArea.setLineWrap(true);
textArea.setWrapStyleWord(true); // Wrap at word boundaries
```

---

## JCheckBox

* Component that can be selected or deselected
* Represents a boolean state (on/off, true/false)

```java
// Create a checkbox
JCheckBox checkbox = new JCheckBox("Enable feature");

// Create a selected checkbox
JCheckBox selectedBox = new JCheckBox("Remember me", true);

// Check the state
boolean isSelected = checkbox.isSelected();

// Change the state programmatically
checkbox.setSelected(true);

// Handle state changes
checkbox.addItemListener(e -> {
    boolean selected = (e.getStateChange() == ItemEvent.SELECTED);
    toggleFeature(selected);
});
```

---

## JRadioButton

* Similar to checkbox but used in groups where only one can be selected
* Must be added to a ButtonGroup to enforce mutual exclusivity

```java
// Create radio buttons
JRadioButton option1 = new JRadioButton("Option 1");
JRadioButton option2 = new JRadioButton("Option 2");
JRadioButton option3 = new JRadioButton("Option 3");

// Create a button group and add the radio buttons
ButtonGroup group = new ButtonGroup();
group.add(option1);
group.add(option2);
group.add(option3);

// Set initial selection
option1.setSelected(true);

// Add to container (e.g., panel)
JPanel panel = new JPanel();
panel.add(option1);
panel.add(option2);
panel.add(option3);

// Handling selection
option1.addActionListener(e -> handleOption1());
```

---

## JComboBox

* Drop-down list that allows selecting from predefined options
* Can be editable or non-editable

```java
// Create a combo box with String items
String[] options = {"Option 1", "Option 2", "Option 3"};
JComboBox<String> comboBox = new JComboBox<>(options);

// Add more items dynamically
comboBox.addItem("Option 4");

// Get the selected item
String selected = (String) comboBox.getSelectedItem();
// or
int selectedIndex = comboBox.getSelectedIndex();

// Make the combo box editable
comboBox.setEditable(true);

// Handling selection changes
comboBox.addActionListener(e -> {
    String selectedOption = (String) comboBox.getSelectedItem();
    processSelection(selectedOption);
});
```

---

## JList

* Displays a list of items that can be selected
* Supports single or multiple selections

```java
// Create a list with String items
String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
JList<String> list = new JList<>(items);

// Set selection mode
list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Single selection
// or
list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Multiple selection

// Make the list scrollable
JScrollPane scrollPane = new JScrollPane(list);

// Get selected values
List<String> selectedValues = list.getSelectedValuesList(); // Java 7+

// Handling selection changes
list.addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) { // Only react when selection is finished
        List<String> selected = list.getSelectedValuesList();
        processSelections(selected);
    }
});
```

---

## JSlider

* Allows selecting a value from a range by sliding a knob
* Useful for visual selection of numeric values

```java
// Create a horizontal slider with range 0-100 and initial value 50
JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

// Customize appearance
slider.setMajorTickSpacing(20);
slider.setMinorTickSpacing(5);
slider.setPaintTicks(true);
slider.setPaintLabels(true);

// Get the current value
int value = slider.getValue();

// Set value programmatically
slider.setValue(75);

// Handle value changes
slider.addChangeListener(e -> {
    JSlider source = (JSlider) e.getSource();
    int newValue = source.getValue();
    updateDisplay(newValue);
});
```

---

## JProgressBar

* Visualizes the progress of a time-consuming task
* Can be determinate (known range) or indeterminate (unknown duration)

```java
// Create a horizontal progress bar
JProgressBar progressBar = new JProgressBar(JSlider.HORIZONTAL, 0, 100);

// Configure appearance
progressBar.setValue(0);
progressBar.setStringPainted(true); // Show percentage or value

// For tasks with unknown duration
progressBar.setIndeterminate(true);

// Updating progress
progressBar.setValue(50); // 50%

// For tasks completed in steps
// Usually updated from a worker thread
for (int i = 0; i <= 100; i += 10) {
    // In real applications, use SwingWorker for background tasks
    progressBar.setValue(i);
    try { Thread.sleep(1000); } catch (InterruptedException e) {}
}
```

---

## Complete Example: Form Application

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationForm extends JFrame {
    
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> countryCombo;
    private JCheckBox termsCheckbox;
    
    public RegistrationForm() {
        // Set up the frame
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Create a panel for form fields with GridLayout
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add name field
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        formPanel.add(nameField);
        
        // Add email field
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);
        
        // Add password field
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField);
        
        // Add country selection
        formPanel.add(new JLabel("Country:"));
        String[] countries = {"USA", "Canada", "UK", "Australia", "India", "Other"};
        countryCombo = new JComboBox<>(countries);
        formPanel.add(countryCombo);
        
        // Add terms checkbox
        formPanel.add(new JLabel(""));
        termsCheckbox = new JCheckBox("I accept the terms and conditions");
        formPanel.add(termsCheckbox);
        
        // Add the form panel to the center
        add(formPanel, BorderLayout.CENTER);
        
        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton resetButton = new JButton("Reset");
        JButton submitButton = new JButton("Submit");
        
        // Add action listeners
        resetButton.addActionListener(e -> resetForm());
        submitButton.addActionListener(e -> submitForm());
        
        buttonPanel.add(resetButton);
        buttonPanel.add(submitButton);
        
        // Add the button panel to the bottom
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add a title at the top
        JLabel titleLabel = new JLabel("User Registration Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
    }
    
    private void resetForm() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        countryCombo.setSelectedIndex(0);
        termsCheckbox.setSelected(false);
    }
    
    private void submitForm() {
        if (!termsCheckbox.isSelected()) {
            JOptionPane.showMessageDialog(this, 
                "Please accept the terms and conditions", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate fields (simplified)
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || 
            new String(passwordField.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill all required fields", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Process form submission
        String message = "Registration successful!\n" +
                         "Name: " + nameField.getText() + "\n" +
                         "Email: " + emailField.getText() + "\n" +
                         "Country: " + countryCombo.getSelectedItem();
                         
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        resetForm();
    }
    
    public static void main(String[] args) {
        // Create and display the form using Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            RegistrationForm form = new RegistrationForm();
            form.setVisible(true);
        });
    }
}
```

---

## Event Handling in Swing

* Event-driven programming paradigm
* Components generate events, listeners respond to them

Common event listeners:
* `ActionListener` - Button clicks, menu selections, etc.
* `ItemListener` - State changes in checkboxes, comboboxes
* `ChangeListener` - Value changes in sliders, spinners
* `KeyListener` - Keyboard input
* `MouseListener` - Mouse clicks and movements
* `WindowListener` - Window events (closing, minimizing)

---

## Exercise Ideas

1. Create a simple calculator with buttons and a display field
2. Build a temperature converter (Celsius to Fahrenheit)
3. Design a form with validation for student registration
4. Implement a to-do list application with add/remove functionality
5. Create a quiz application with radio buttons for multiple choice questions

---

## Best Practices in Swing Development

1. Use appropriate layout managers for complex UIs
2. Keep UI and logic separate (MVC pattern)
3. Use SwingWorker for long-running tasks to avoid UI freezing
4. Consider accessibility features
5. Handle exceptions properly in event listeners
6. Use JOptionPane for simple dialogs and messages
7. Group related components using panels
8. Apply consistent spacing and alignment

---

## Next Steps and Advanced Topics

* Custom components and renderers
* Swing Workers and multithreading
* Advanced layouts (MigLayout, FormLayout)
* Data binding
* Swing and databases
* Model-View-Controller with Swing
* JavaFX as modern alternative

---

## Resources for Further Learning

* Oracle's Java Tutorials: [https://docs.oracle.com/javase/tutorial/uiswing/](https://docs.oracle.com/javase/tutorial/uiswing/)
* Books: "Swing: A Beginner's Guide" by Herbert Schildt
* Open-source examples: SwingSet3, SwingX
* Practice projects on GitHub
* JavaDocs for Swing components
