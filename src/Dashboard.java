import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Hospital Management System - Dashboard");
        setSize(700, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("HOSPITAL MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setHorizontalAlignment(JLabel.CENTER);

        JButton patients = new JButton("Patient Management");
        JButton doctors = new JButton("Doctor Management");
        JButton appointments = new JButton("Appointments");
        JButton billing = new JButton("Billing");
        JButton records = new JButton("View Records");
        JButton searchPatient = new JButton("Search Patient");
        JButton updatePatient = new JButton("Update Patient");
        JButton deletePatient = new JButton("Delete Patient");
        JButton updateDoctor = new JButton("Update Doctor");
        JButton deleteDoctor = new JButton("Delete Doctor");
        JButton advanced = new JButton("Advanced Dashboard");
        JButton updateAppointment = new JButton("Update Appointment");
        JButton deleteAppointment = new JButton("Delete Appointment");
        JButton history = new JButton("Appointment History");
        JButton medical = new JButton("Medical History");
        JButton viewMedical = new JButton("View Medical History");
        JButton searchMedical = new JButton("Search Medical History");
        JButton updateMedical = new JButton("Update Medical History");

        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        panel.add(patients);
        panel.add(doctors);
        panel.add(appointments);
        panel.add(billing);
        panel.add(records);
        panel.add(searchPatient);
        panel.add(updatePatient);
        panel.add(deletePatient);
        panel.add(updateDoctor);
        panel.add(deleteDoctor);
        panel.add(updateAppointment);
        panel.add(deleteAppointment);
        panel.add(history);
        panel.add(medical);
        panel.add(viewMedical);
        panel.add(searchMedical);
        panel.add(updateMedical);
        panel.add(advanced);

        patients.addActionListener(e -> new Patient());
        doctors.addActionListener(e -> new Doctor());
        appointments.addActionListener(e -> new Appointment());
        billing.addActionListener(e -> new Billing());
        records.addActionListener(e -> new ViewRecords());
        searchPatient.addActionListener(e -> new SearchPatient());
        updatePatient.addActionListener(e -> new UpdatePatient());
        deletePatient.addActionListener(e -> new DeletePatient());
        updateDoctor.addActionListener(e -> new UpdateDoctor());
        deleteDoctor.addActionListener(e -> new DeleteDoctor());
        updateAppointment.addActionListener(e -> new UpdateAppointment());
        deleteAppointment.addActionListener(e -> new DeleteAppointment());
        history.addActionListener(e -> new AppointmentHistory());
        medical.addActionListener(e -> new MedicalHistory());
        viewMedical.addActionListener(e -> new ViewMedicalHistory());
        searchMedical.addActionListener(e -> new SearchMedicalHistory());
        updateMedical.addActionListener(e -> new UpdateMedicalHistory());
        advanced.addActionListener(e -> new AdvancedDashboard());;

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}