import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationForm extends JFrame {

  private JTextField nameField;
  private JTextField emailField;
  private JPasswordField passwordField;
  private JComboBox<String> countryCombo;
  private JCheckBox termsCheckbox;

  public RegistrationForm() {
    // Set up the frame
    setTitle("User Registration");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));

    // Create a panel for form fields with GridLayout
    JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
    formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Add name field
    formPanel.add(new JLabel("Name:"));
    nameField = new JTextField(20);
    formPanel.add(nameField);

    // Add email field
    formPanel.add(new JLabel("Email:"));
    emailField = new JTextField(20);
    formPanel.add(emailField);

    // Add password field
    formPanel.add(new JLabel("Password:"));
    passwordField = new JPasswordField(20);
    formPanel.add(passwordField);

    // Add country selection
    formPanel.add(new JLabel("Country:"));
    String[] countries = { "USA", "Canada", "UK", "Australia", "India", "Other" };
    countryCombo = new JComboBox<>(countries);
    formPanel.add(countryCombo);

    // Add terms checkbox
    formPanel.add(new JLabel(""));
    termsCheckbox = new JCheckBox("I accept the terms and conditions");
    formPanel.add(termsCheckbox);

    // Add the form panel to the center
    add(formPanel, BorderLayout.CENTER);

    // Create a panel for buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton resetButton = new JButton("Reset");
    JButton submitButton = new JButton("Submit");

    // Add action listeners
    resetButton.addActionListener(e -> resetForm());
    submitButton.addActionListener(e -> submitForm());

    buttonPanel.add(resetButton);
    buttonPanel.add(submitButton);

    // Add the button panel to the bottom
    add(buttonPanel, BorderLayout.SOUTH);

    // Add a title at the top
    JLabel titleLabel = new JLabel("User Registration Form", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    add(titleLabel, BorderLayout.NORTH);
  }

  private void resetForm() {
    nameField.setText("");
    emailField.setText("");
    passwordField.setText("");
    countryCombo.setSelectedIndex(0);
    termsCheckbox.setSelected(false);
  }

  private void submitForm() {
    if (!termsCheckbox.isSelected()) {
      JOptionPane.showMessageDialog(this,
          "Please accept the terms and conditions",
          "Validation Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Validate fields (simplified)
    if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
        new String(passwordField.getPassword()).isEmpty()) {
      JOptionPane.showMessageDialog(this,
          "Please fill all required fields",
          "Validation Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Process form submission
    String message = "Registration successful!\n" +
        "Name: " + nameField.getText() + "\n" +
        "Email: " + emailField.getText() + "\n" +
        "Country: " + countryCombo.getSelectedItem();

    JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    resetForm();
  }

  public static void main(String[] args) {
    // Create and display the form using Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
      RegistrationForm form = new RegistrationForm();
      form.setVisible(true);
    });
  }
}