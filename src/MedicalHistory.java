
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MedicalHistory extends JFrame {

    JTextField patientName, diagnosis, treatment, visitDate;

    public MedicalHistory() {
        setTitle("Patient Medical History");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        patientName = new JTextField();
        diagnosis = new JTextField();
        treatment = new JTextField();
        visitDate = new JTextField();

        JButton save = new JButton("Save History");

        panel.add(new JLabel("Patient Name:"));
        panel.add(patientName);

        panel.add(new JLabel("Diagnosis:"));
        panel.add(diagnosis);

        panel.add(new JLabel("Treatment:"));
        panel.add(treatment);

        panel.add(new JLabel("Visit Date (YYYY-MM-DD):"));
        panel.add(visitDate);

        panel.add(new JLabel(""));
        panel.add(save);

        save.addActionListener(e -> saveHistory());

        add(panel);
        setVisible(true);
    }

    private void saveHistory() {

        String sql = "INSERT INTO medical_history " +
            "(patient_name, diagnosis, treatment, visit_date) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patientName.getText().trim());
            ps.setString(2, diagnosis.getText().trim());
            ps.setString(3, treatment.getText().trim());
            ps.setDate(4, java.sql.Date.valueOf(
                visitDate.getText().trim()));

            if (patientName.getText().trim().isEmpty()
                    || diagnosis.getText().trim().isEmpty()
                    || treatment.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill all details!");
                return;
            }

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Medical History Saved Successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}
