
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdatePatient extends JFrame {

    JTextField id, name, age, phone, disease;

    public UpdatePatient() {

        setTitle("Update Patient Details");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        id = new JTextField();
        name = new JTextField();
        age = new JTextField();
        phone = new JTextField();
        disease = new JTextField();

        panel.add(new JLabel("Patient ID:"));
        panel.add(id);

        panel.add(new JLabel("Patient Name:"));
        panel.add(name);

        panel.add(new JLabel("Age:"));
        panel.add(age);

        panel.add(new JLabel("Phone:"));
        panel.add(phone);

        panel.add(new JLabel("Disease:"));
        panel.add(disease);

        JButton update = new JButton("Update Patient");
        panel.add(new JLabel(""));
        panel.add(update);

        update.addActionListener(e -> updatePatient());

        add(panel);
        setVisible(true);
    }

    private void updatePatient() {

        String sql = "UPDATE patients SET patient_name=?, age=?, phone=?, disease=? WHERE patient_id=?";

        try {
            if (id.getText().trim().isEmpty() ||
                name.getText().trim().isEmpty() ||
                age.getText().trim().isEmpty() ||
                phone.getText().trim().isEmpty() ||
                disease.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                    "Please fill all details!");
                return;
            }

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, name.getText().trim());
                ps.setInt(2, Integer.parseInt(age.getText().trim()));
                ps.setString(3, phone.getText().trim());
                ps.setString(4, disease.getText().trim());
                ps.setInt(5, Integer.parseInt(id.getText().trim()));

                int result = ps.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Patient Updated Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Patient ID Not Found!");
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Enter valid Patient ID and Age!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}
