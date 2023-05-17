package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel = new JLabel ("Masukkan ID Anda:");
    private JTextField idTextField = new JTextField(20);
    private JLabel passwordLabel = new JLabel ("Masukkan pasword Anda:");
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("Login");
    private JButton backButton = new JButton("Back");
    private JCheckBox checkPass = new JCheckBox("Show password");
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 10); // Add right padding
        mainPanel.add(idLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(idTextField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 10); // Add right padding
        mainPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(passwordField, gbc);
    
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 5, 0); // Add left padding
        gbc.fill = GridBagConstraints.NONE;
        checkPass.addActionListener(e -> {
            passwordField.setEchoChar(checkPass.isSelected() ? '\u0000' : '\u2022');
        });
        mainPanel.add(checkPass, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 0, 10, 0); // Add bottom padding
        gbc.anchor = GridBagConstraints.CENTER;
    
        // Set button width to match label and text field
        int buttonWidth = Math.max(idLabel.getPreferredSize().width, idTextField.getPreferredSize().width);
        loginButton.setPreferredSize(new Dimension(buttonWidth, 25));
        loginButton.addActionListener(e -> handleLogin());
        mainPanel.add(loginButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 0, 0); // Add top padding
    
        // Set button width to match label and text field
        backButton.setPreferredSize(new Dimension(buttonWidth, 25));
        backButton.addActionListener(e -> handleBack());
        mainPanel.add(backButton, gbc);
    }    
    
    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        idTextField.setText(""); passwordField.setText("");
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        // TODO
        String inputId = idTextField.getText();
        String inputPassword = new String(passwordField.getPassword());
        if (inputId.isEmpty() || inputPassword.isEmpty()){
            JOptionPane.showMessageDialog(null, "ID atau Password masih kosong.", 
                                "Login Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            SystemCLI user = loginManager.getSystem(inputId);
            if (user != null) {
                Member userLogin = user.authUser(inputId, inputPassword); 
                if (user == null || userLogin == null){
                    JOptionPane.showMessageDialog(null, "ID atau Password salah.", 
                                                "Login Error", JOptionPane.ERROR_MESSAGE);
                    idTextField.setText(""); passwordField.setText(""); checkPass.setSelected(false);
                }
                else {
                    MainFrame.getInstance().login(inputId, inputPassword);
                    idTextField.setText(""); passwordField.setText(""); checkPass.setSelected(false);
                    if (user instanceof EmployeeSystem) {
                        MainFrame.getInstance().navigateTo(EmployeeSystemGUI.KEY);
                    }
                    else if (user instanceof MemberSystem){
                        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);   
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "ID atau Password salah.",
                                             "Login Error", JOptionPane.ERROR_MESSAGE);
                idTextField.setText(""); passwordField.setText(""); checkPass.setSelected(false);
            }
        }
    }
}