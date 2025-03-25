# Advanced Java
## Unit 1: GUI Programming

---

## Lecture 1: Introduction to Swing

### What is Swing?
- Java's GUI toolkit for creating desktop applications
- Part of Java Foundation Classes (JFC)
- Platform-independent GUI components
- Built on top of the Abstract Window Toolkit (AWT)
- Offers more flexibility and richer components than AWT

### Swing vs. AWT
- AWT: Uses native platform components (heavyweight)
- Swing: Pure Java components (lightweight)
- Swing has consistent look and feel across platforms
- Swing components have "J" prefix (JButton, JFrame, etc.)

### MVC Architecture in Swing
- Model: Data and business logic
- View: Visual representation
- Controller: Handles user interactions
- Swing components follow this pattern internally

---

## Lecture 2: Creating a Frame

### JFrame Basics
- JFrame is the main container for Swing applications
- Represents a window with a title and border
- Can contain other Swing components

### Creating a Basic Swing Application

```java
import javax.swing.*;

public class SimpleFrame {
    public static void main(String[] args) {
        // Create a new frame
        JFrame frame = new JFrame("My First Swing App");
        
        // Set frame size
        frame.setSize(400, 300);
        
        // Set close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Center the frame on screen
        frame.setLocationRelativeTo(null);
        
        // Make the frame visible
        frame.setVisible(true);
    }
}
```

### JFrame Properties and Methods
- `setTitle(String)`: Sets the title of the frame
- `setSize(int, int)`: Sets the width and height
- `setLocation(int, int)`: Sets the position
- `setBounds(int, int, int, int)`: Sets position and size
- `setDefaultCloseOperation(int)`: Defines what happens when window closes
- `setResizable(boolean)`: Allows/prevents resizing
- `setVisible(boolean)`: Shows or hides the frame

---

## Lecture 3: Displaying Information in Components 

### Common Components
- JLabel: Display text or images
- JTextField: Single-line text input
- JTextArea: Multi-line text input
- JButton: Clickable button
- JPanel: Container for organizing components

### Basic Component Example

```java
import javax.swing.*;
import java.awt.*;

public class ComponentDemo {
    public static void main(String[] args) {
        // Create frame
        JFrame frame = new JFrame("Component Demo");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a panel with FlowLayout
        JPanel panel = new JPanel(new FlowLayout());
        
        // Add components
        JLabel label = new JLabel("Enter your name:");
        JTextField textField = new JTextField(15);
        JButton button = new JButton("Submit");
        
        // Add components to panel
        panel.add(label);
        panel.add(textField);
        panel.add(button);
        
        // Add panel to frame
        frame.add(panel);
        
        // Display the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### Content Panes
- JFrame contains a content pane where components are added
- `frame.getContentPane()` returns the content pane
- Modern Java allows adding directly to the frame

---

## Lecture 4: Working with 2D Shapes

### Java 2D API
- Enhanced graphics capabilities
- Part of the `java.awt.geom` package
- Enables complex shape drawing
- Supports lines, rectangles, ellipses, arcs, and custom shapes

### Basic Shapes

```java
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class ShapesDemo extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw a line
        g2d.draw(new Line2D.Double(50, 50, 200, 50));
        
        // Draw a rectangle
        g2d.draw(new Rectangle2D.Double(50, 70, 150, 70));
        
        // Draw an ellipse
        g2d.draw(new Ellipse2D.Double(50, 170, 150, 70));
        
        // Draw a rounded rectangle
        g2d.draw(new RoundRectangle2D.Double(250, 70, 150, 70, 20, 20));
        
        // Draw an arc
        g2d.draw(new Arc2D.Double(250, 170, 150, 70, 0, 135, Arc2D.PIE));
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shapes Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ShapesDemo());
        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### Custom Shapes
- Create custom shapes using `GeneralPath`
- Combine multiple shapes with `Area` class
- Transform shapes with `AffineTransform`

---

## Lecture 5: Using Color

### Working with Colors
- Java represents colors with the `Color` class
- RGB, RGBA, HSB color models supported
- Predefined color constants: `Color.RED`, `Color.BLUE`, etc.

### Setting Colors

```java
import javax.swing.*;
import java.awt.*;

public class ColorDemo extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Using predefined colors
        g2d.setColor(Color.RED);
        g2d.fillRect(50, 50, 100, 50);
        
        // RGB color (Orange)
        g2d.setColor(new Color(255, 165, 0));
        g2d.fillRect(50, 120, 100, 50);
        
        // RGB with alpha (Semi-transparent blue)
        g2d.setColor(new Color(0, 0, 255, 128));
        g2d.fillRect(100, 85, 100, 50);
        
        // HSB color (Purple)
        g2d.setColor(Color.getHSBColor(0.75f, 1.0f, 0.8f));
        g2d.fillRect(200, 50, 100, 50);
        
        // Gradient background
        GradientPaint gradient = new GradientPaint(
            200, 120, Color.YELLOW, 300, 170, Color.GREEN);
        g2d.setPaint(gradient);
        g2d.fillRect(200, 120, 100, 50);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Color Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ColorDemo());
        frame.setSize(350, 220);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### Gradients and Textures
- Linear gradients: `GradientPaint`
- Radial gradients: `RadialGradientPaint`
- Texture fill: `TexturePaint`

---

## Lecture 6: Using Special Fonts for Text

### Font Basics
- Java represents fonts with the `Font` class
- Specify font name, style, and size
- Styles: `Font.PLAIN`, `Font.BOLD`, `Font.ITALIC`

### Working with Fonts

```java
import javax.swing.*;
import java.awt.*;
import java.awt.font.*;

public class FontDemo extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable anti-aliasing for smoother text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Plain font
        Font plainFont = new Font("SansSerif", Font.PLAIN, 18);
        g2d.setFont(plainFont);
        g2d.drawString("Plain SansSerif Font", 50, 50);
        
        // Bold font
        Font boldFont = new Font("Serif", Font.BOLD, 18);
        g2d.setFont(boldFont);
        g2d.drawString("Bold Serif Font", 50, 80);
        
        // Italic font
        Font italicFont = new Font("Monospaced", Font.ITALIC, 18);
        g2d.setFont(italicFont);
        g2d.drawString("Italic Monospaced Font", 50, 110);
        
        // Bold and Italic
        Font boldItalicFont = new Font("Dialog", Font.BOLD + Font.ITALIC, 18);
        g2d.setFont(boldItalicFont);
        g2d.drawString("Bold and Italic Dialog Font", 50, 140);
        
        // Larger font with color
        Font largeFont = new Font("SansSerif", Font.BOLD, 24);
        g2d.setFont(largeFont);
        g2d.setColor(Color.BLUE);
        g2d.drawString("Large Blue Text", 50, 180);
        
        // Get available font families
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        System.out.println("Number of available fonts: " + fontNames.length);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Font Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FontDemo());
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### Advanced Text Rendering
- Text attributes: `TextAttribute` class
- Font metrics: `FontMetrics` class
- Text layout: `TextLayout` class

---

## Lecture 7: Displaying Images

### Loading and Displaying Images
- Use `ImageIcon` for simple image display
- Use `Image` class for more control
- Load from files, URLs, or resources

### Basic Image Display

```java
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageDemo extends JPanel {
    private BufferedImage image;
    
    public ImageDemo() {
        try {
            // Load image from file
            // Replace with your own image path
            image = ImageIO.read(new File("sample.jpg"));
        } catch (IOException e) {
            // Create a blank image if file not found
            image = new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.RED);
            g2d.fillRect(0, 0, 200, 150);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Image Not Found", 50, 75);
            g2d.dispose();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Display original image
        g2d.drawImage(image, 50, 30, this);
        
        // Display scaled image
        g2d.drawImage(image, 50, 200, 100, 75, this);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ImageDemo());
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### Using ImageIcon with JLabel

```java
import javax.swing.*;

public class ImageIconDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ImageIcon Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an ImageIcon
        ImageIcon icon = new ImageIcon("sample.jpg");
        
        // Create a JLabel with the ImageIcon
        JLabel label = new JLabel(icon);
        
        // Add the label to the frame
        frame.add(label);
        
        // Pack the frame to fit the image size
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### Image Manipulation
- Scaling, rotating, and filtering images
- Using `BufferedImage` for pixel manipulation
- `ImageIO` for reading/writing image files

---

## Lecture 8: Event Handling Basics

### Understanding Events
- Events represent user actions (clicks, keypresses, etc.)
- Event objects contain information about the action
- Event sources generate events
- Event listeners respond to events

### Event Handling Model
1. Component generates an event
2. Event object is created
3. Event is passed to registered listeners
4. Listener's methods are called

### Simple Event Handling Example

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EventDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        JPanel panel = new JPanel();
        JButton button = new JButton("Click Me");
        JLabel label = new JLabel("No clicks yet");
        
        // Add ActionListener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("Button was clicked!");
            }
        });
        
        panel.add(button);
        panel.add(label);
        frame.add(panel);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### Using Lambda Expressions (Java 8+)

```java
import javax.swing.*;
import java.awt.*;

public class LambdaEventDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lambda Event Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        JPanel panel = new JPanel();
        JButton button = new JButton("Click Me");
        JLabel label = new JLabel("No clicks yet");
        
        // Using lambda expression for ActionListener
        button.addActionListener(e -> label.setText("Button was clicked!"));
        
        panel.add(button);
        panel.add(label);
        frame.add(panel);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

---

## Lecture 9: Event Classes and Listeners

### Common Event Classes
- `ActionEvent`: Button clicks, menu selections
- `MouseEvent`: Mouse actions
- `KeyEvent`: Keyboard actions
- `WindowEvent`: Window state changes
- `FocusEvent`: Component focus changes

### Common Listener Interfaces
- `ActionListener`: Responds to action events
- `MouseListener`: Responds to mouse events
- `KeyListener`: Responds to keyboard events
- `WindowListener`: Responds to window events
- `FocusListener`: Responds to focus events

### MouseListener Example

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MouseEventDemo extends JPanel implements MouseListener {
    private String message = "Mouse events will be displayed here";
    
    public MouseEventDemo() {
        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.WHITE);
        addMouseListener(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(message, 50, 50);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        message = "Mouse clicked at (" + e.getX() + ", " + e.getY() + ")";
        repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        message = "Mouse pressed at (" + e.getX() + ", " + e.getY() + ")";
        repaint();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        message = "Mouse released at (" + e.getX() + ", " + e.getY() + ")";
        repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        message = "Mouse entered panel";
        repaint();
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        message = "Mouse exited panel";
        repaint();
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mouse Event Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MouseEventDemo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### KeyListener Example

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KeyEventDemo extends JPanel implements KeyListener {
    private String message = "Type something (panel must have focus)";
    
    public KeyEventDemo() {
        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(message, 50, 50);
        g.drawString("Click on panel to give it focus", 50, 80);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        message = "Key typed: " + e.getKeyChar();
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        message = "Key pressed: " + KeyEvent.getKeyText(e.getKeyCode());
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        message = "Key released: " + KeyEvent.getKeyText(e.getKeyCode());
        repaint();
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Key Event Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new KeyEventDemo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

---

## Lecture 10: Adapter Classes

### Adapter Classes
- Empty implementations of listener interfaces
- Allow you to override only the methods you need
- Examples: `MouseAdapter`, `KeyAdapter`, `WindowAdapter`

### Using MouseAdapter

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdapterDemo extends JPanel {
    private String message = "Mouse events using MouseAdapter";
    
    public AdapterDemo() {
        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.WHITE);
        
        // Using MouseAdapter instead of implementing MouseListener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                message = "Mouse clicked at (" + e.getX() + ", " + e.getY() + ")";
                repaint();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                message = "Mouse entered panel";
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                message = "Mouse exited panel";
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(message, 50, 50);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Adapter Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AdapterDemo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### WindowAdapter Example

```java
import javax.swing.*;
import java.awt.event.*;

public class WindowAdapterDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Window Adapter Demo");
        frame.setSize(300, 200);
        
        // Add a WindowListener using WindowAdapter
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Window is closing");
                frame.dispose();
            }
            
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("Window opened");
            }
            
            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("Window minimized");
            }
        });
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

---

## Lecture 11: Swing and MVC Design Pattern

### MVC Pattern in Swing
- **Model**: Holds data and business logic
- **View**: Visual representation of data
- **Controller**: Handles user input and updates model/view

### Benefits of MVC
- Separation of concerns
- Code reusability
- Easier maintenance
- Parallel development

### Custom MVC Example

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Model
class CounterModel {
    private int count = 0;
    
    public int getCount() {
        return count;
    }
    
    public void increment() {
        count++;
    }
    
    public void decrement() {
        count--;
    }
    
    public void reset() {
        count = 0;
    }
}

// View
class CounterView extends JPanel {
    private JLabel countLabel;
    private JButton incrementButton;
    private JButton decrementButton;
    private JButton resetButton;
    
    public CounterView() {
        setLayout(new FlowLayout());
        
        countLabel = new JLabel("Count: 0");
        incrementButton = new JButton("Increment");
        decrementButton = new JButton("Decrement");
        resetButton = new JButton("Reset");
        
        add(countLabel);
        add(incrementButton);
        add(decrementButton);
        add(resetButton);
    }
    
    public void updateCount(int count) {
        countLabel.setText("Count: " + count);
    }
    
    public JButton getIncrementButton() {
        return incrementButton;
    }
    
    public JButton getDecrementButton() {
        return decrementButton;
    }
    
    public JButton getResetButton() {
        return resetButton;
    }
}

// Controller
class CounterController {
    private CounterModel model;
    private CounterView view;
    
    public CounterController(CounterModel model, CounterView view) {
        this.model = model;
        this.view = view;
        
        // Add event listeners
        view.getIncrementButton().addActionListener(e -> {
            model.increment();
            view.updateCount(model.getCount());
        });
        
        view.getDecrementButton().addActionListener(e -> {
            model.decrement();
            view.updateCount(model.getCount());
        });
        
        view.getResetButton().addActionListener(e -> {
            model.reset();
            view.updateCount(model.getCount());
        });
    }
}

// Main application
public class MVCDemo {
    public static void main(String[] args) {
        // Create MVC components
        CounterModel model = new CounterModel();
        CounterView view = new CounterView();
        CounterController controller = new CounterController(model, view);
        
        // Create and set up frame
        JFrame frame = new JFrame("MVC Counter Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

---

## Lecture 12: Layout Management

### Layout Managers
- Control the size and position of components
- Adapt to different screen sizes and resolutions
- Common layout managers:
  - FlowLayout
  - BorderLayout
  - GridLayout
  - BoxLayout
  - GridBagLayout

### FlowLayout

```java
import javax.swing.*;
import java.awt.*;

public class FlowLayoutDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("FlowLayout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        
        // Create panel with FlowLayout
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        // Add buttons
        panel.add(new JButton("Button 1"));
        panel.add(new JButton("Button 2"));
        panel.add(new JButton("Button 3"));
        panel.add(new JButton("Long Button 4"));
        panel.add(new JButton("Button 5"));
        
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### BorderLayout

```java
import javax.swing.*;
import java.awt.*;

public class BorderLayoutDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BorderLayout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // BorderLayout is the default for JFrame's content pane
        Container contentPane = frame.getContentPane();
        
        // Add components to different regions
        contentPane.add(new JButton("North"), BorderLayout.NORTH);
        contentPane.add(new JButton("South"), BorderLayout.SOUTH);
        contentPane.add(new JButton("East"), BorderLayout.EAST);
        contentPane.add(new JButton("West"), BorderLayout.WEST);
        contentPane.add(new JButton("Center"), BorderLayout.CENTER);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### GridLayout

```java
import javax.swing.*;
import java.awt.*;

public class GridLayoutDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GridLayout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Create a panel with GridLayout (3 rows, 2 columns)
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        // Add buttons
        for (int i = 1; i <= 6; i++) {
            panel.add(new JButton("Button " + i));
        }
        
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### Nested Layouts

```java
import javax.swing.*;
import java.awt.*;

public class NestedLayoutDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nested Layout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Use BorderLayout for the main frame
        frame.setLayout(new BorderLayout(10, 10));
        
        // North panel with FlowLayout
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        northPanel.add(new JLabel("Header"));
        northPanel.add(new JButton("Login"));
        northPanel.setBackground(Color.LIGHT_GRAY);
        
        // Center panel with GridLayout
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.add(new JButton("1"));
        centerPanel.add(new JButton("2"));
        centerPanel.add(new JButton("3"));
        centerPanel.add(new JButton("4"));
        
        // South panel with FlowLayout
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        southPanel.add(new JButton("Cancel"));
        southPanel.add(new JButton("OK"));
        southPanel.setBackground(Color.LIGHT_GRAY);
        
        // Add panels to the frame
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```