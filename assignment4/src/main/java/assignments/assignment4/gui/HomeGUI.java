package assignments.assignment4.gui;

import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;
import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JPanel mainPanel;
    private JLabel titleLabel = new JLabel("Selamat Datang di CuciCuci System!");
    private JLabel dateLabel = new JLabel("Hari ini: " + fmt.format(cal.getTime()));
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JButton toNextDayButton = new JButton("To Next Day");

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout

        // Set up main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     **/
    private void initGUI() {
        // Set up title label
        GridBagConstraints gbc = new GridBagConstraints();
        
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 40, 0);
        mainPanel.add(titleLabel, gbc);
    
        // Set up login button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0); 
        loginButton.setPreferredSize(new Dimension(120, 30)); 
        loginButton.addActionListener(e -> handleToLogin());    
        mainPanel.add(loginButton, gbc);
    
        // Set up register button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 10, 0); 
        registerButton.setPreferredSize(new Dimension(120, 30));
        registerButton.addActionListener(e -> handleToRegister());
        mainPanel.add(registerButton, gbc);
    
        // Set up to next day button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 0, 10, 0); 
        toNextDayButton.setPreferredSize(new Dimension(120, 30));
        toNextDayButton.addActionListener(e -> {
            handleNextDay();
            JOptionPane.showMessageDialog(null, "Kamu tidur hari ini... zzz...", 
                                        "Skip hari", JOptionPane.INFORMATION_MESSAGE);
        });
        
        mainPanel.add(toNextDayButton, gbc);
    
        // Set up date label
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 0, 0); 
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(dateLabel, gbc);
    }    

    /**
     * Method untuk pergi ke halaman register yang akan dipanggil jika pengguna menekan "registerButton"
     **/
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login dan akan dipanggil jika pengguna menekan "loginButton"
     **/
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari yang akan dipanggil jika pengguna menekan "toNextDayButton"
     **/
    private void handleNextDay() {
        toNextDay();
        dateLabel.setText("Hari ini: " + fmt.format(cal.getTime()));
    }
}