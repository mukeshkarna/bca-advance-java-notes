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
