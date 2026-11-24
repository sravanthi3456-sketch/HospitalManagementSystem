
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    JTextField username;
    JPasswordField password;

    public Login() {
        setTitle("Hospital Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("HOSPITAL LOGIN");
        title.setHorizontalAlignment(JLabel.CENTER);

        username = new JTextField();
        password = new JPasswordField();

        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(username);

        panel.add(new JLabel("Password:"));
        panel.add(password);

        panel.add(new JLabel(""));
        panel.add(loginButton);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String user = username.getText();
            String pass = new String(password.getPassword());

            if (user.equals("admin") && pass.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        });

        setVisible(true);
    }
}
