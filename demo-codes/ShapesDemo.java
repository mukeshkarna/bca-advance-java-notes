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
