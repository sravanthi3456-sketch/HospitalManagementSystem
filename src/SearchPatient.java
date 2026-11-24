
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchPatient extends JFrame {

    JTextField patientId;

    public SearchPatient() {

        setTitle("Search Patient");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        patientId = new JTextField();

        JButton search = new JButton("Search Patient");

        panel.add(new JLabel("Enter Patient ID:"));
        panel.add(patientId);
        panel.add(new JLabel(""));
        panel.add(search);

        search.addActionListener(e -> searchPatient());

        add(panel);
        setVisible(true);
    }

    private void searchPatient() {

        try {
            int id = Integer.parseInt(patientId.getText().trim());

            String sql = "SELECT * FROM patients WHERE patient_id = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(this,
                        "Patient ID: " + rs.getInt("patient_id") +
                        "\nName: " + rs.getString("patient_name") +
                        "\nAge: " + rs.getInt("age") +
                        "\nPhone: " + rs.getString("phone") +
                        "\nDisease: " + rs.getString("disease"));

                } else {
                    JOptionPane.showMessageDialog(this,
                        "Patient Not Found!");
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid Patient ID!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}
