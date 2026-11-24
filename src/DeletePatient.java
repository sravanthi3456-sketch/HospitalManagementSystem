
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeletePatient extends JFrame {

    JTextField patientId;

    public DeletePatient() {

        setTitle("Delete Patient");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        patientId = new JTextField();

        JButton delete = new JButton("Delete Patient");

        panel.add(new JLabel("Enter Patient ID:"));
        panel.add(patientId);
        panel.add(new JLabel(""));
        panel.add(delete);

        delete.addActionListener(e -> deletePatient());

        add(panel);
        setVisible(true);
    }

    private void deletePatient() {

        try {
            int id = Integer.parseInt(patientId.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this patient?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String sql = "DELETE FROM patients WHERE patient_id = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int result = ps.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Patient Deleted Successfully!");
                    patientId.setText("");
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Patient ID Not Found!");
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Enter a valid Patient ID!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}
