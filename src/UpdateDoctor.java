
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateDoctor extends JFrame {

    JTextField id, name, specialization, phone;

    public UpdateDoctor() {

        setTitle("Update Doctor Details");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        id = new JTextField();
        name = new JTextField();
        specialization = new JTextField();
        phone = new JTextField();

        panel.add(new JLabel("Doctor ID:"));
        panel.add(id);

        panel.add(new JLabel("Doctor Name:"));
        panel.add(name);

        panel.add(new JLabel("Specialization:"));
        panel.add(specialization);

        panel.add(new JLabel("Phone Number:"));
        panel.add(phone);

        JButton update = new JButton("Update Doctor");
        panel.add(new JLabel(""));
        panel.add(update);

        update.addActionListener(e -> updateDoctor());

        add(panel);
        setVisible(true);
    }

    private void updateDoctor() {

        if (id.getText().trim().isEmpty() ||
            name.getText().trim().isEmpty() ||
            specialization.getText().trim().isEmpty() ||
            phone.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                "Please fill all details!");
            return;
        }

        String sql = "UPDATE doctors SET doctor_name=?, " +
            "specialization=?, phone=? WHERE doctor_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name.getText().trim());
            ps.setString(2, specialization.getText().trim());
            ps.setString(3, phone.getText().trim());
            ps.setInt(4, Integer.parseInt(id.getText().trim()));

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                    "Doctor Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this,
                    "Doctor ID Not Found!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Enter a valid Doctor ID!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}