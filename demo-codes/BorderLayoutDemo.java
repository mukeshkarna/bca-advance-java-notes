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