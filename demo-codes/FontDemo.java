import javax.swing.*;
import java.awt.*;
import java.awt.font.*;

public class FontDemo extends JPanel {
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Enable anti-aliasing for smoother text
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // Plain font
    Font plainFont = new Font("SansSerif", Font.PLAIN, 18);
    g2d.setFont(plainFont);
    g2d.drawString("Plain SansSerif Font", 50, 50);

    // Bold font
    Font boldFont = new Font("Serif", Font.BOLD, 18);
    g2d.setFont(boldFont);
    g2d.drawString("Bold Serif Font", 50, 80);

    // Italic font
    Font italicFont = new Font("Monospaced", Font.ITALIC, 18);
    g2d.setFont(italicFont);
    g2d.drawString("Italic Monospaced Font", 50, 110);

    // Bold and Italic
    Font boldItalicFont = new Font("Dialog", Font.BOLD + Font.ITALIC, 18);
    g2d.setFont(boldItalicFont);
    g2d.drawString("Bold and Italic Dialog Font", 50, 140);

    // Larger font with color
    Font largeFont = new Font("SansSerif", Font.BOLD, 24);
    g2d.setFont(largeFont);
    g2d.setColor(Color.BLUE);
    g2d.drawString("Large Blue Text", 50, 180);

    // Get available font families
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] fontNames = ge.getAvailableFontFamilyNames();
    System.out.println("Number of available fonts: " + fontNames.length);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Font Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new FontDemo());
    frame.setSize(400, 250);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
