import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageDemo extends JPanel {
  private BufferedImage image;

  public ImageDemo() {
    try {
      // Load image from file
      // Replace with your own image path
      image = ImageIO.read(new File("sample.jpg"));
    } catch (IOException e) {
      // Create a blank image if file not found
      image = new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB);
      Graphics2D g2d = image.createGraphics();
      g2d.setColor(Color.RED);
      g2d.fillRect(0, 0, 200, 150);
      g2d.setColor(Color.BLACK);
      g2d.drawString("Image Not Found", 50, 75);
      g2d.dispose();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Display original image
    g2d.drawImage(image, 50, 30, this);

    // Display scaled image
    g2d.drawImage(image, 50, 200, 100, 75, this);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Image Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new ImageDemo());
    frame.setSize(400, 350);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
