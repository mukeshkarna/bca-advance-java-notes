import javax.swing.*;

public class ImageIconDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame("ImageIcon Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create an ImageIcon
    ImageIcon icon = new ImageIcon("sample.jpg");

    // Create a JLabel with the ImageIcon
    JLabel label = new JLabel(icon);

    // Add the label to the frame
    frame.add(label);

    // Pack the frame to fit the image size
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}