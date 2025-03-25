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
