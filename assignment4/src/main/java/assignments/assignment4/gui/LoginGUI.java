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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel welcomeLabel = new JLabel("Halo, silahkan masukkan akun kalian ^^");
    private JLabel idLabel = new JLabel ("ID User");
    private JTextField idTextField = new JTextField(20);
    private JLabel passwordLabel = new JLabel ("Password");
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("Masuk");
    private JButton backButton = new JButton("Kembali");
    private JCheckBox checkPass = new JCheckBox("Tampilkan password");
    private JLabel registerLabel = new JLabel("<html><u><font color='blue' size='2'>Belum punya akun? Daftar</font></u></html>");
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout
        this.loginManager = loginManager;

        // Set up main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    //Method untuk menginisialisasi GUI
    private void initGUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 40, 0); // Add bottom padding
        gbc.anchor = GridBagConstraints.CENTER;

        // Create the label
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font and size
        mainPanel.add(welcomeLabel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 111); // Add right padding
        mainPanel.add(idLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0); // Reset padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(idTextField, gbc);
        
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
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 0, 10, 0); // Add bottom padding
        gbc.anchor = GridBagConstraints.CENTER;
    
        // Set button width to match label and text field
        int buttonWidth = Math.max(idLabel.getPreferredSize().width, idTextField.getPreferredSize().width);
        loginButton.setPreferredSize(new Dimension(buttonWidth, 25));
        loginButton.addActionListener(e -> handleLogin());
        mainPanel.add(loginButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 0, 0); // Add top padding
    
        // Set button width to match label and text field
        backButton.setPreferredSize(new Dimension(buttonWidth, 25));
        backButton.addActionListener(e -> handleBack());
        mainPanel.add(backButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(25, 0, 0, 0); // Add top padding
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleToRegist();
            }
        });
        mainPanel.add(registerLabel, gbc);
    }    
    
    //Method untuk kembali ke halaman home jika pengguna menekan "backButton"
    private void handleBack() {
        clearFields();
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    //Method untuk login pada sistem jika pengguna menekan "loginButton"
    private void handleLogin() {
        String inputId = idTextField.getText();
        String inputPassword = new String(passwordField.getPassword());
        if (inputId.isEmpty() || inputPassword.isEmpty()){ //Jika semua field belum keisi
            JOptionPane.showMessageDialog(null, "ID atau Password masih kosong.", 
                                "Login Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            SystemCLI user = loginManager.getSystem(inputId);
            if (user != null) {
                Member userLogin = user.authUser(inputId, inputPassword); 
                if (user == null || userLogin == null){ //Jika ID tak sesuai dengan password
                    JOptionPane.showMessageDialog(null, "ID atau Password salah.", 
                                                "Login Error", JOptionPane.ERROR_MESSAGE);
                    clearFields();
                }
                else { //Jika ID dan password sudah valid
                    MainFrame.getInstance().login(inputId, inputPassword);
                    clearFields();
                    if (user instanceof EmployeeSystem) { //Jika user adalah employee
                        MainFrame.getInstance().navigateTo(EmployeeSystemGUI.KEY);
                    }
                    else if (user instanceof MemberSystem){ //Jika user adalah member
                        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);   
                    }
                }
            }
            else{ //Jika ID belum ada
                JOptionPane.showMessageDialog(null, "ID atau Password salah.",
                                             "Login Error", JOptionPane.ERROR_MESSAGE);
                clearFields();
            }
        }
    }
    
    //Dipanggil untuk shortcut apabila ia ingin register melalui LoginGUI
    private void handleToRegist(){
        clearFields();
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    //Method untuk membersihkan seluruh field
    private void clearFields(){
        idTextField.setText(""); passwordField.setText(""); checkPass.setSelected(false);
    }
}