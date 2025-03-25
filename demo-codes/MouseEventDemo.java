import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MouseEventDemo extends JPanel implements MouseListener {
  private String message = "Mouse events will be displayed here";

  public MouseEventDemo() {
    setPreferredSize(new Dimension(400, 300));
    setBackground(Color.WHITE);
    addMouseListener(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawString(message, 50, 50);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    message = "Mouse clicked at (" + e.getX() + ", " + e.getY() + ")";
    repaint();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    message = "Mouse pressed at (" + e.getX() + ", " + e.getY() + ")";
    repaint();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    message = "Mouse released at (" + e.getX() + ", " + e.getY() + ")";
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

  public static void main(String[] args) {
    JFrame frame = new JFrame("Mouse Event Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new MouseEventDemo());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
