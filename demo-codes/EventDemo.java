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