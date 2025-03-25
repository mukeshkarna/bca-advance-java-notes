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
