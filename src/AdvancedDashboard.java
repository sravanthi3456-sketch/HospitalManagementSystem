
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdvancedDashboard extends JFrame {

    JLabel patients, doctors, appointments, revenue;

    public AdvancedDashboard() {
        setTitle("Hospital Management - Advanced Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("HOSPITAL MANAGEMENT DASHBOARD");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JPanel cards = new JPanel(new GridLayout(2, 2, 20, 20));
        cards.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        patients = new JLabel("0", JLabel.CENTER);
        doctors = new JLabel("0", JLabel.CENTER);
        appointments = new JLabel("0", JLabel.CENTER);
        revenue = new JLabel("Rs. 0", JLabel.CENTER);

        cards.add(createCard("Total Patients", patients));
        cards.add(createCard("Total Doctors", doctors));
        cards.add(createCard("Total Appointments", appointments));
        cards.add(createCard("Total Revenue", revenue));

        JButton refresh = new JButton("Refresh Dashboard");
        refresh.addActionListener(e -> loadStatistics());

        JPanel bottom = new JPanel();
        bottom.add(refresh);

        add(title, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadStatistics();
        setVisible(true);
    }

    private JPanel createCard(String heading, JLabel value) {
        JPanel card = new JPanel(new GridLayout(2, 1, 5, 5));
        card.setBackground(new Color(225, 240, 250));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel name = new JLabel(heading, JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 18));

        value.setFont(new Font("Arial", Font.BOLD, 28));

        card.add(name);
        card.add(value);

        return card;
    }

    private void loadStatistics() {
        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) {
                JOptionPane.showMessageDialog(this,
                    "Database connection failed!");
                return;
            }

            patients.setText(getCount(con, "patients"));
            doctors.setText(getCount(con, "doctors"));
            appointments.setText(getCount(con, "appointments"));

            String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM bills";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                if (rs.next()) {
                    revenue.setText("Rs. " + rs.getDouble(1));
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }

    private String getCount(Connection con, String table)
            throws SQLException {

        String sql = "SELECT COUNT(*) FROM " + table;

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return String.valueOf(rs.getInt(1));
            }
        }

        return "0";
    }
}