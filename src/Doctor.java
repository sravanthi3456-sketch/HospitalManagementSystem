
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Doctor extends JFrame {

    JTextField name, specialization, phone;

    public Doctor() {
        setTitle("Doctor Registration");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        name = new JTextField();
        specialization = new JTextField();
        phone = new JTextField();

        panel.add(new JLabel("Doctor Name:"));
        panel.add(name);

        panel.add(new JLabel("Specialization:"));
        panel.add(specialization);

        panel.add(new JLabel("Phone Number:"));
        panel.add(phone);

        JButton save = new JButton("Register Doctor");
        panel.add(new JLabel(""));
        panel.add(save);

        save.addActionListener(e -> saveDoctor());

        add(panel);
        setVisible(true);
    }

    private void saveDoctor() {

        if (name.getText().trim().isEmpty() ||
            specialization.getText().trim().isEmpty() ||
            phone.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                "Please fill all details!");
            return;
        }

        String sql = "INSERT INTO doctors " +
            "(doctor_name, specialization, phone) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                JOptionPane.showMessageDialog(this,
                    "Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, name.getText().trim());
                ps.setString(2, specialization.getText().trim());
                ps.setString(3, phone.getText().trim());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                    "Doctor Saved Successfully!");

                name.setText("");
                specialization.setText("");
                phone.setText("");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}
