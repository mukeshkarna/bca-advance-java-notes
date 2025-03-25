import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KeyEventDemo extends JPanel implements KeyListener {
  private String message = "Type something (panel must have focus)";

  public KeyEventDemo() {
    setPreferredSize(new Dimension(400, 300));
    setBackground(Color.WHITE);
    setFocusable(true);
    addKeyListener(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawString(message, 50, 50);
    g.drawString("Click on panel to give it focus", 50, 80);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    message = "Key typed: " + e.getKeyChar();
    repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    message = "Key pressed: " + KeyEvent.getKeyText(e.getKeyCode());
    repaint();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    message = "Key released: " + KeyEvent.getKeyText(e.getKeyCode());
    repaint();
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Key Event Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new KeyEventDemo());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}