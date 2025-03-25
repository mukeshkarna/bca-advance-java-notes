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
