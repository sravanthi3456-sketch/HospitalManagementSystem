
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SearchMedicalHistory extends JFrame {

    JTextField patientName;
    DefaultTableModel model;

    public SearchMedicalHistory() {
        setTitle("Search Medical History");
        setSize(850, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel top = new JPanel(new FlowLayout());

        patientName = new JTextField(20);
        JButton search = new JButton("Search");

        top.add(new JLabel("Patient Name:"));
        top.add(patientName);
        top.add(search);

        String[] columns = {
            "History ID", "Patient Name", "Diagnosis",
            "Treatment", "Visit Date"
        };

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        search.addActionListener(e -> searchHistory());

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }

    private void searchHistory() {

        model.setRowCount(0);

        String sql = "SELECT * FROM medical_history WHERE patient_name LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + patientName.getText().trim() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("history_id"),
                        rs.getString("patient_name"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getDate("visit_date")
                    });
                }
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "No Medical History Found!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}