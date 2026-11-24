
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Patient extends JFrame {

    JTextField name, age, phone, disease;

    public Patient() {
        setTitle("Patient Registration");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        name = new JTextField();
        age = new JTextField();
        phone = new JTextField();
        disease = new JTextField();

        panel.add(new JLabel("Patient Name:"));
        panel.add(name);

        panel.add(new JLabel("Age:"));
        panel.add(age);

        panel.add(new JLabel("Phone Number:"));
        panel.add(phone);

        panel.add(new JLabel("Disease:"));
        panel.add(disease);

        JButton save = new JButton("Register Patient");
        panel.add(new JLabel(""));
        panel.add(save);

        save.addActionListener(e -> savePatient());

        add(panel);
        setVisible(true);
    }

    private void savePatient() {
        if (name.getText().trim().isEmpty() ||
            age.getText().trim().isEmpty() ||
            phone.getText().trim().isEmpty() ||
            disease.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                "Please fill all details!");
            return;
        }

        String sql = "INSERT INTO patients " +
            "(patient_name, age, phone, disease) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                JOptionPane.showMessageDialog(this,
                    "Database connection failed!");
                return;
            }

            ps.setString(1, name.getText().trim());
            ps.setInt(2, Integer.parseInt(age.getText().trim()));
            ps.setString(3, phone.getText().trim());
            ps.setString(4, disease.getText().trim());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Patient Saved Successfully!");

            name.setText("");
            age.setText("");
            phone.setText("");
            disease.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid age!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}
