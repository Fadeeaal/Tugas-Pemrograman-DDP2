package assignments.assignment4.gui;

import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;
import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JButton toNextDayButton = new JButton("To Next Day");

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // Set up title label
        GridBagConstraints gbc = new GridBagConstraints();
        
        titleLabel = new JLabel("Selamat Datang di CuciCuci System!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add bottom padding
        mainPanel.add(titleLabel, gbc);
    
        // Set up login button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0); // Add bottom padding
        loginButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        loginButton.addActionListener(e -> handleToLogin());    
        mainPanel.add(loginButton, gbc);
    
        // Set up register button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 10, 0); // Add bottom padding
        registerButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        registerButton.addActionListener(e -> handleToRegister());
        mainPanel.add(registerButton, gbc);
    
        // Set up to next day button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 0, 10, 0); // Add bottom padding
        toNextDayButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        toNextDayButton.addActionListener(e -> {
            handleNextDay();
            JOptionPane.showMessageDialog(null, "Kamu tidur hari ini... zzz...", 
                                        "Skip hari", JOptionPane.INFORMATION_MESSAGE);
        });
        
        mainPanel.add(toNextDayButton, gbc);
    
        // Set up date label
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 0, 0); // Add top padding
        dateLabel = new JLabel("Hari ini: " + fmt.format(cal.getTime()));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(dateLabel, gbc);
    }    

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        toNextDay();
        dateLabel.setText("Hari ini: " + fmt.format(cal.getTime()));
    }
}
