import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdapterDemo extends JPanel {
  private String message = "Mouse events using MouseAdapter";

  public AdapterDemo() {
    setPreferredSize(new Dimension(400, 300));
    setBackground(Color.WHITE);

    // Using MouseAdapter instead of implementing MouseListener
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        message = "Mouse clicked at (" + e.getX() + ", " + e.getY() + ")";
        repaint();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        message = "Mouse entered panel";
        repaint();
      }

      @Override
      public void mouseExited(MouseEvent e) {
        message = "Mouse exited panel";
        repaint();
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawString(message, 50, 50);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Adapter Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new AdapterDemo());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
