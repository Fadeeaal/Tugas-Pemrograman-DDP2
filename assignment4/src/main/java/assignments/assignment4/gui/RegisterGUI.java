package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel = new JLabel("Masukkan nama Anda:");
    private JTextField nameTextField = new JTextField(20);
    private JLabel phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
    private JTextField phoneTextField = new JTextField(20);
    private JLabel passwordLabel =  new JLabel("Masukkan password Anda:");
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton registerButton = new JButton("Register");
    private JButton backButton = new JButton("Back");
    private JCheckBox checkPass = new JCheckBox("Show password");
    private LoginManager loginManager;

    public RegisterGUI(LoginManager loginManager) {
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
        // TODO
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 10); // Add right padding
        mainPanel.add(nameLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(nameTextField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 10); // Add right padding
        mainPanel.add(phoneLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(phoneTextField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 10); // Add right padding
        mainPanel.add(passwordLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 5, 0); // Add left padding
        gbc.fill = GridBagConstraints.NONE;
        checkPass.addActionListener(e -> {
            passwordField.setEchoChar(checkPass.isSelected() ? '\u0000' : '\u2022');
        });
        mainPanel.add(checkPass, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 0, 10, 0); // Add bottom padding
        gbc.anchor = GridBagConstraints.CENTER;

        // Set button width to match label and text field
        int buttonWidth = Math.max(nameLabel.getPreferredSize().width, nameTextField.getPreferredSize().width);
        registerButton.setPreferredSize(new Dimension(buttonWidth, 25)); // Set button size
        registerButton.addActionListener(e -> handleRegister());
        mainPanel.add(registerButton, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 0, 0, 0); // Add bottom padding
        backButton.setPreferredSize(new Dimension(buttonWidth, 25)); // Set button size
        backButton.addActionListener(e -> handleBack());
        mainPanel.add(backButton, gbc);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        nameTextField.setText(""); phoneTextField.setText(""); passwordField.setText(""); checkPass.setSelected(false);
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // TODO
        String nama = nameTextField.getText().trim();
        String noHp = phoneTextField.getText();
        String password = new String(passwordField.getPassword());
        Member registeredMember = loginManager.register(nama, noHp, password);
        if (nama.isEmpty() || noHp.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", 
                                    "Invalid Field", JOptionPane.ERROR_MESSAGE);
        }
        else if (!isNumeric(noHp)){
            JOptionPane.showMessageDialog(null, "Nomor Handphone harus berisi angka!", 
                                    "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            phoneTextField.setText("");
        }
        else if (registeredMember == null) {
            JOptionPane.showMessageDialog(null, "User dengan nama " + nama + " dan nomor hp " + noHp + " sudah ada!", 
                                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
            nameTextField.setText(""); phoneTextField.setText(""); passwordField.setText(""); checkPass.setSelected(false);
            MainFrame.getInstance().navigateTo(HomeGUI.KEY);
        } 
        else {
            JOptionPane.showMessageDialog(null, "Berhasil membuat user dengan ID " + registeredMember.getId() + "!",
                                    "Registration Success", JOptionPane.INFORMATION_MESSAGE);
            nameTextField.setText(""); phoneTextField.setText(""); passwordField.setText(""); checkPass.setSelected(false);
            MainFrame.getInstance().navigateTo(HomeGUI.KEY);;
        }        
    }

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }
}