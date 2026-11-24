
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMedicalHistory extends JFrame {

    DefaultTableModel model;

    public ViewMedicalHistory() {
        setTitle("View Medical History");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
            "History ID", "Patient Name", "Diagnosis",
            "Treatment", "Visit Date"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadHistory());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(refresh, BorderLayout.SOUTH);

        loadHistory();

        setVisible(true);
    }

    private void loadHistory() {
        model.setRowCount(0);

        String sql = "SELECT * FROM medical_history ORDER BY history_id DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("history_id"),
                    rs.getString("patient_name"),
                    rs.getString("diagnosis"),
                    rs.getString("treatment"),
                    rs.getDate("visit_date")
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}