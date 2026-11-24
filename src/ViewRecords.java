
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewRecords extends JFrame {

    public ViewRecords() {

        setTitle("Hospital Records");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton patients = new JButton("View Patients");
        JButton doctors = new JButton("View Doctors");
        JButton appointments = new JButton("View Appointments");
        JButton bills = new JButton("View Bills");

        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panel.add(patients);
        panel.add(doctors);
        panel.add(appointments);
        panel.add(bills);

        patients.addActionListener(e ->
            showRecords("SELECT * FROM patients", "Patient Records"));

        doctors.addActionListener(e ->
            showRecords("SELECT * FROM doctors", "Doctor Records"));

        appointments.addActionListener(e ->
            showRecords("SELECT * FROM appointments", "Appointment Records"));

        bills.addActionListener(e ->
            showRecords("SELECT * FROM bills", "Billing Records"));

        add(panel);
        setVisible(true);
    }

    private void showRecords(String sql, String title) {

        StringBuilder data = new StringBuilder();

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();

            for (int i = 1; i <= columns; i++) {
                data.append(meta.getColumnName(i)).append("\t");
            }

            data.append("\n\n");

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    data.append(rs.getString(i)).append("\t");
                }
                data.append("\n");
            }

            if (data.length() == 0) {
                data.append("No records found.");
            }

            JTextArea area = new JTextArea(data.toString());
            area.setEditable(false);
            area.setFont(new Font("Monospaced", Font.PLAIN, 13));

            JScrollPane scroll = new JScrollPane(area);

            JOptionPane.showMessageDialog(this, scroll, title,
                JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}
