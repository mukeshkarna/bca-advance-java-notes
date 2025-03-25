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
