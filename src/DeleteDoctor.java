import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteDoctor extends JFrame {

    JTextField id;

    public DeleteDoctor() {
        setTitle("Delete Doctor");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        id = new JTextField();

        panel.add(new JLabel("Doctor ID:"));
        panel.add(id);

        JButton delete = new JButton("Delete Doctor");
        panel.add(new JLabel(""));
        panel.add(delete);

        delete.addActionListener(e -> deleteDoctor());

        add(panel);
        setVisible(true);
    }

    private void deleteDoctor() {
        try {
            int doctorId = Integer.parseInt(id.getText().trim());

            int choice = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to delete this doctor?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) return;

            String sql = "DELETE FROM doctors WHERE doctor_id=?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, doctorId);
                int result = ps.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Doctor Deleted Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Doctor ID Not Found!");
                }
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