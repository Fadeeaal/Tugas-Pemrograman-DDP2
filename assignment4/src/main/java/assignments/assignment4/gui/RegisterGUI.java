package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel welcomeLabel = new JLabel("Halo, silahkan daftarkan akun kalian ^^");
    private JLabel nameLabel = new JLabel("Nama lengkap");
    private JTextField nameTextField = new JTextField(20);
    private JLabel phoneLabel = new JLabel("Nomor handphone");
    private JTextField phoneTextField = new JTextField(20);
    private JLabel passwordLabel =  new JLabel("Password");
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton registerButton = new JButton("Daftar");
    private JButton backButton = new JButton("Kembali");
    private JCheckBox checkPass = new JCheckBox("Tampilkan password");
    private JLabel registerLabel = new JLabel("<html><u><font color='blue' size='2'>Sudah punya akun? Masuk</font></u></html>");
    private LoginManager loginManager;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout
        this.loginManager = loginManager;

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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 20, 40, 0); // Add bottom padding
        gbc.anchor = GridBagConstraints.CENTER;

        // Create the label
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font and size
        mainPanel.add(welcomeLabel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 72); // Add right padding
        mainPanel.add(nameLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(nameTextField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 10); // Add right padding
        mainPanel.add(phoneLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(phoneTextField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 10); // Add right padding
        mainPanel.add(passwordLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
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

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(25, 0, 0, 0); // Add top padding
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleToLogin();
            }
        });
        mainPanel.add(registerLabel, gbc);
    }

    /**
     * Method untuk kembali ke halaman home yang akan dipanggil jika pengguna menekan "backButton"
     **/
    private void handleBack() {
        clearFields();
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem dan akan dipanggil jika pengguna menekan "registerButton"
    **/
    private void handleRegister() {
        String nama = nameTextField.getText().trim();
        String noHp = phoneTextField.getText();
        String password = new String(passwordField.getPassword());
        Member registeredMember = loginManager.register(nama, noHp, password);
        if (nama.isEmpty() || noHp.isEmpty() || password.isEmpty()){ //Apabila semua field belum diisi
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", 
                                    "Invalid Field", JOptionPane.ERROR_MESSAGE);
        }
        else if (!isNumeric(noHp)){ //Apabila no Hp tidak valid (ada karakter selain angka)
            JOptionPane.showMessageDialog(null, "Nomor Handphone harus berisi angka!", 
                                    "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            phoneTextField.setText("");
        }
        else if (registeredMember == null) { //Apabila member sudah pernah terdaftar
            JOptionPane.showMessageDialog(null, "User dengan nama " + nama + " dan nomor hp " + noHp + " sudah ada!", 
                                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
            clearFields();
            MainFrame.getInstance().navigateTo(HomeGUI.KEY);
        } 
        else { //Apabila member belum terdaftar
            JOptionPane.showMessageDialog(null, "Berhasil membuat user dengan ID " + registeredMember.getId() + "!",
                                    "Registration Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            MainFrame.getInstance().navigateTo(HomeGUI.KEY);;
        }        
    }

    //Dipanggil untuk shortcut apabila ia ingin login melalui RegisterGUI
    private void handleToLogin(){
        clearFields();
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }

    //Method yang dipanggil untuk mengecek no Hp
    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    //Method untuk membersihkan seluruh field
    private void clearFields(){
        nameTextField.setText(""); phoneTextField.setText(""); checkPass.setSelected(false);
        passwordField.setEchoChar('\u2022');passwordField.setText("");
    }
}