import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SimpleInterest extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField t1, t2, t3, t4;
    JButton b1;

    public SimpleInterest() {
        l1 = new JLabel("Principal:");
        l1.setBounds(20, 10, 100, 20); //x, y, width, height

        t1 = new JTextField(10);
        t1.setBounds(120, 10, 100, 20);

        l2 = new JLabel("Time:");
        l2.setBounds(20, 40, 100, 20);

        t2 = new JTextField(10);
        t2.setBounds(120, 40, 100, 20);

        l3 = new JLabel("Rate:");
        l3.setBounds(20, 70, 100, 20);

        t3 = new JTextField(10);
        t3.setBounds(120, 70, 100, 20);

        b1 = new JButton("Simple Interest");
        b1.setBounds(20, 100, 80, 20);

        t4 = new JTextField(10);
        t4.setBounds(120, 100, 100, 20);

        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(l3);
        add(t3);
        add(b1);
        add(t4);

        b1.addActionListener(this);    //Registering event
        setSize(400, 300);
        setLayout(null);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e)         //Handle Event
    {
        if (e.getSource() == b1) {
            double P = Double.parseDouble(t1.getText());
            double T = Double.parseDouble(t2.getText());
            double R = Double.parseDouble(t3.getText());

            double SI = (P * T * R) / 100;

            t4.setText(String.valueOf(SI));
        }
    }

    public static void main(String args[])
    {
        new SimpleInterest();
    }
}
