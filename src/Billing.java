
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Billing extends JFrame {

    JTextField patientName, consultationFee, medicineCharges, otherCharges;

    public Billing() {

        setTitle("Hospital Billing System");
        setSize(450, 400);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Patient Name:"));
        patientName = new JTextField();
        add(patientName);

        add(new JLabel("Consultation Fee:"));
        consultationFee = new JTextField();
        add(consultationFee);

        add(new JLabel("Medicine Charges:"));
        medicineCharges = new JTextField();
        add(medicineCharges);

        add(new JLabel("Other Charges:"));
        otherCharges = new JTextField();
        add(otherCharges);

        JButton calculate = new JButton("Calculate & Save Bill");
        add(calculate);

        calculate.addActionListener(e -> calculateBill());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calculateBill() {

        try {
            String name = patientName.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please enter patient name!");
                return;
            }

            double fee = Double.parseDouble(consultationFee.getText());
            double medicine = Double.parseDouble(medicineCharges.getText());
            double other = Double.parseDouble(otherCharges.getText());

            if (fee < 0 || medicine < 0 || other < 0) {
                JOptionPane.showMessageDialog(this,
                    "Charges cannot be negative!");
                return;
            }

            double total = fee + medicine + other;

            String sql = "INSERT INTO bills " +
                "(patient_name, consultation_fee, medicine_charges, " +
                "other_charges, total_amount) VALUES (?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                if (con == null) {
                    JOptionPane.showMessageDialog(this,
                        "Database connection failed!");
                    return;
                }

                ps.setString(1, name);
                ps.setDouble(2, fee);
                ps.setDouble(3, medicine);
                ps.setDouble(4, other);
                ps.setDouble(5, total);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                    "Bill Saved Successfully!\n" +
                    "Patient: " + name +
                    "\nTotal Bill: Rs. " + total);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid charges!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}
