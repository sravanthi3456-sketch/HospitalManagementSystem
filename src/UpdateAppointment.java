import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateAppointment extends JFrame {

    JTextField appointmentId;
    JComboBox<String> statusBox;

    public UpdateAppointment() {
        setTitle("Update Appointment Status");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        appointmentId = new JTextField();

        statusBox = new JComboBox<>(
            new String[]{"Pending", "Confirmed", "Completed", "Cancelled"}
        );

        JButton update = new JButton("Update Status");

        panel.add(new JLabel("Appointment ID:"));
        panel.add(appointmentId);

        panel.add(new JLabel("New Status:"));
        panel.add(statusBox);

        panel.add(new JLabel(""));
        panel.add(update);

        update.addActionListener(e -> updateStatus());

        add(panel);
        setVisible(true);
    }

    private void updateStatus() {
        try {
            int id = Integer.parseInt(appointmentId.getText().trim());

            String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, (String) statusBox.getSelectedItem());
                ps.setInt(2, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Appointment Status Updated Successfully!");
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