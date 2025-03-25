import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Model
class CounterModel {
  private int count = 0;

  public int getCount() {
    return count;
  }

  public void increment() {
    count++;
  }

  public void decrement() {
    count--;
  }

  public void reset() {
    count = 0;
  }
}

// View
class CounterView extends JPanel {
  private JLabel countLabel;
  private JButton incrementButton;
  private JButton decrementButton;
  private JButton resetButton;

  public CounterView() {
    setLayout(new FlowLayout());

    countLabel = new JLabel("Count: 0");
    incrementButton = new JButton("Increment");
    decrementButton = new JButton("Decrement");
    resetButton = new JButton("Reset");

    add(countLabel);
    add(incrementButton);
    add(decrementButton);
    add(resetButton);
  }

  public void updateCount(int count) {
    countLabel.setText("Count: " + count);
  }

  public JButton getIncrementButton() {
    return incrementButton;
  }

  public JButton getDecrementButton() {
    return decrementButton;
  }

  public JButton getResetButton() {
    return resetButton;
  }
}

// Controller
class CounterController {
  private CounterModel model;
  private CounterView view;

  public CounterController(CounterModel model, CounterView view) {
    this.model = model;
    this.view = view;

    // Add event listeners
    view.getIncrementButton().addActionListener(e -> {
      model.increment();
      view.updateCount(model.getCount());
    });

    view.getDecrementButton().addActionListener(e -> {
      model.decrement();
      view.updateCount(model.getCount());
    });

    view.getResetButton().addActionListener(e -> {
      model.reset();
      view.updateCount(model.getCount());
    });
  }
}

// Main application
public class MVCDemo {
  public static void main(String[] args) {
    // Create MVC components
    CounterModel model = new CounterModel();
    CounterView view = new CounterView();
    CounterController controller = new CounterController(model, view);

    // Create and set up frame
    JFrame frame = new JFrame("MVC Counter Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(view);
    frame.setSize(300, 100);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
