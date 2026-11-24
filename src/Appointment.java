
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Appointment extends JFrame {

    JTextField patientName, date, time;
    JComboBox<DoctorOption> doctorBox;

    static class DoctorOption {
        String name, specialization;

        DoctorOption(String name, String specialization) {
            this.name = name;
            this.specialization = specialization;
        }

        public String toString() {
            return name + " - " + specialization;
        }
    }

    public Appointment() {
        setTitle("Advanced Appointment Booking");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        patientName = new JTextField();
        date = new JTextField();
        time = new JTextField();

        doctorBox = new JComboBox<>();

        loadDoctors();

        JButton book = new JButton("Book Appointment");

        panel.add(new JLabel("Patient Name:"));
        panel.add(patientName);

        panel.add(new JLabel("Select Doctor:"));
        panel.add(doctorBox);

        panel.add(new JLabel("Appointment Date (YYYY-MM-DD):"));
        panel.add(date);

        panel.add(new JLabel("Appointment Time (HH:MM):"));
        panel.add(time);

        panel.add(new JLabel("Status: Pending"));
        panel.add(book);

        book.addActionListener(e -> bookAppointment());

        add(panel);
        setVisible(true);
    }

    private void loadDoctors() {

        String sql = "SELECT doctor_name, specialization FROM doctors";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                doctorBox.addItem(new DoctorOption(
                    rs.getString("doctor_name"),
                    rs.getString("specialization")
                ));
            }

            if (doctorBox.getItemCount() == 0) {
                doctorBox.addItem(
                    new DoctorOption("No doctors available", "")
                );
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Doctor Loading Error: " + ex.getMessage());
        }
    }

    private void bookAppointment() {

        String patient = patientName.getText().trim();
        String appointmentDate = date.getText().trim();
        String appointmentTime = time.getText().trim();

        DoctorOption doctor =
            (DoctorOption) doctorBox.getSelectedItem();

        if (patient.isEmpty() || appointmentDate.isEmpty()
                || appointmentTime.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill all details!");
            return;
        }

        if (doctor == null || doctor.name.equals("No doctors available")) {
            JOptionPane.showMessageDialog(this,
                "Please register a doctor first!");
            return;
        }

        if (!appointmentTime.matches("\\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this,
                "Enter time in HH:MM format!");
            return;
        }

        String sql = "INSERT INTO appointments " +
            "(patient_name, doctor_name, specialization, " +
            "appointment_date, appointment_time, status) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patient);
            ps.setString(2, doctor.name);
            ps.setString(3, doctor.specialization);
            ps.setDate(4, java.sql.Date.valueOf(appointmentDate));
            ps.setString(5, appointmentTime);
            ps.setString(6, "Pending");

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Appointment Booked Successfully!");

            patientName.setText("");
            date.setText("");
            time.setText("");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                "Enter date as YYYY-MM-DD and valid time!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }
}