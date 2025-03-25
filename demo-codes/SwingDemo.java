import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingDemo {
  public static void main(String[] args) {
    // Create the frame on the event dispatching thread
    SwingUtilities.invokeLater(() -> createAndShowGUI());
  }

  private static void createAndShowGUI() {
    // Create and set up the window
    JFrame frame = new JFrame("Swing Demo Application");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);

    // Create a panel with a border layout
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    // Create a label
    JLabel label = new JLabel("Welcome to Swing!");
    label.setHorizontalAlignment(JLabel.CENTER);
    panel.add(label, BorderLayout.CENTER);

    // Create a button
    JButton button = new JButton("Click Me!");
    button.addActionListener(new ActionListener() {
      int count = 0;

      @Override
      public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Button clicked " + count + " times!");
      }
    });
    panel.add(button, BorderLayout.SOUTH);

    // Add panel to frame
    frame.add(panel);

    // Display the window
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
