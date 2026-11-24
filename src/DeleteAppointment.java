
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteAppointment extends JFrame {

    JTextField appointmentId;

    public DeleteAppointment() {
        setTitle("Delete Appointment");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        appointmentId = new JTextField();
        JButton delete = new JButton("Delete Appointment");

        panel.add(new JLabel("Appointment ID:"));
        panel.add(appointmentId);
        panel.add(new JLabel(""));
        panel.add(delete);

        delete.addActionListener(e -> deleteAppointment());

        add(panel);
        setVisible(true);
    }

    private void deleteAppointment() {
        try {
            int id = Integer.parseInt(appointmentId.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this appointment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String sql = "DELETE FROM appointments WHERE appointment_id = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Appointment Deleted Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Appointment ID Not Found!");
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Enter a valid Appointment ID!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}