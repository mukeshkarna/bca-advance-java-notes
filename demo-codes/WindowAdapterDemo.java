import javax.swing.*;
import java.awt.event.*;

public class WindowAdapterDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame("Window Adapter Demo");
    frame.setSize(300, 200);

    // Add a WindowListener using WindowAdapter
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.out.println("Window is closing");
        frame.dispose();
      }

      @Override
      public void windowOpened(WindowEvent e) {
        System.out.println("Window opened");
      }

      @Override
      public void windowIconified(WindowEvent e) {
        System.out.println("Window minimized");
      }
    });

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
