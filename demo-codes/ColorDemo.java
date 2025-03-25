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
