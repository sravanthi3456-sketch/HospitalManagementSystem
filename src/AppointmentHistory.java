
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AppointmentHistory extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AppointmentHistory() {
        setTitle("Appointment History");
        setSize(850, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
            "ID", "Patient", "Doctor", "Specialization",
            "Date", "Time", "Status"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JButton refresh = new JButton("Refresh");

        refresh.addActionListener(e -> loadAppointments());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(refresh, BorderLayout.SOUTH);

        loadAppointments();

        setVisible(true);
    }

    private void loadAppointments() {
        model.setRowCount(0);

        String sql = "SELECT * FROM appointments ORDER BY appointment_id DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("appointment_id"),
                    rs.getString("patient_name"),
                    rs.getString("doctor_name"),
                    rs.getString("specialization"),
                    rs.getDate("appointment_date"),
                    rs.getString("appointment_time"),
                    rs.getString("status")
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}