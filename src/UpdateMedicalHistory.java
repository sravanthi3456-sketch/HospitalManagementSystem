import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateMedicalHistory extends JFrame {

    JTextField historyId, diagnosis, treatment, visitDate;

    public UpdateMedicalHistory() {

        setTitle("Update Medical History");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        historyId = new JTextField();
        diagnosis = new JTextField();
        treatment = new JTextField();
        visitDate = new JTextField();

        JButton update = new JButton("Update History");

        panel.add(new JLabel("History ID:"));
        panel.add(historyId);

        panel.add(new JLabel("Diagnosis:"));
        panel.add(diagnosis);

        panel.add(new JLabel("Treatment:"));
        panel.add(treatment);

        panel.add(new JLabel("Visit Date (YYYY-MM-DD):"));
        panel.add(visitDate);

        panel.add(new JLabel(""));
        panel.add(update);

        update.addActionListener(e -> updateHistory());

        add(panel);
        setVisible(true);
    }

    private void updateHistory() {

        String sql = "UPDATE medical_history SET diagnosis=?, treatment=?, visit_date=? WHERE history_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, diagnosis.getText().trim());
            ps.setString(2, treatment.getText().trim());
            ps.setDate(3, Date.valueOf(visitDate.getText().trim()));
            ps.setInt(4, Integer.parseInt(historyId.getText().trim()));

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                    "Medical History Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this,
                    "History ID Not Found!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}