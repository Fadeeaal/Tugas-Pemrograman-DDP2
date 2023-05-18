package assignments.assignment4.gui.member;

import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AbstractMemberGUI extends JPanel implements Loginable{
    private JLabel welcomeLabel;
    private JLabel loggedInAsLabel;
    protected Member loggedInMember;
    private final SystemCLI systemCLI;

    String classUser;

    public AbstractMemberGUI(SystemCLI systemCLI) {
        super(new GridBagLayout());
        this.systemCLI = systemCLI;
    
        // Set up welcome label
        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
    
        // Set up footer
        loggedInAsLabel = new JLabel("", SwingConstants.CENTER);
    
        // Initialize buttons
        JPanel buttonsPanel = initializeButtons();
    
        // Membuat objek GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 10, 10, 10);
    
        // Menambahkan welcomeLabel ke panel utama
        add(welcomeLabel, gbc);
    
        // Mengatur posisi berikutnya
        gbc.gridy++;
    
        // Menambahkan buttonsPanel ke panel utama
        add(buttonsPanel, gbc);
    
        // Mengatur posisi berikutnya
        gbc.gridy++;
    
        // Menambahkan loggedInAsLabel ke panel utama
        add(loggedInAsLabel, gbc);
    }
    
    

    /**
     * Membuat panel button yang akan ditampilkan pada Panel ini.
     * Buttons dan ActionListeners akan disupply oleh method createButtons() & createActionListeners() respectively.
     * <p> Feel free to make any changes. Be creative and have fun!
     *
     * @return JPanel yang di dalamnya berisi Buttons.
     * */
    protected JPanel initializeButtons() {
        JButton[] buttons = createButtons();
        ActionListener[] listeners = createActionListeners();
    
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // Mengubah nilai weighty menjadi 0.0
        gbc.insets = new Insets(5, 5, 35, 5);
    
        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            button.addActionListener(listeners[i]);
            buttonsPanel.add(button, gbc);
            // Mengatur jarak antara tombol
            gbc.insets = new Insets(0, 5, 35, 5);
        }
    
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> MainFrame.getInstance().logout());
        buttonsPanel.add(logoutButton, gbc);
    
        return buttonsPanel;
    }
    
    /**
     * Method untuk login pada panel.
     * Method ini akan melakukan pengecekan apakah ID dan passowrd yang telah diberikan dapat login
     * pada panel ini.
     * Jika bisa, member akan login pada panel ini, method akan:
     *  - mengupdate Welcome & LoggedInAs label.
     *  - mengupdate LoggedInMember sesuai dengan instance pemilik ID dan password.
     *
     * @param id -> ID anggota yang akan diautentikasi.
     * @param password -> password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     **/
    public boolean login(String id, String password) {
        Member authMember = systemCLI.authUser(id, password);
        if (authMember != null) {
            loggedInMember = authMember;
            if (loggedInMember.getClass().getSimpleName().equals("Member")){
                classUser = " as a Member";
            }
            else if (loggedInMember.getClass().getSimpleName().equals("Employee")){
                classUser = " as an Employee";
            }
            welcomeLabel.setText("Welcome, " + loggedInMember.getNama() + "!");
            loggedInAsLabel.setText("Logged in as " + loggedInMember.getId() + classUser);
            return true;
        }
        return false;
    }

    /**
     * Method untuk logout pada panel ini.
     * Akan mengubah loggedInMemberMenjadi null.
     **/
    public void logout() {
        loggedInMember = null;
    }

    /**
     * Method ini mensupply buttons apa saja yang akan dimuat oleh panel ini.
     * Button yang disediakan method ini belum memiliki ActionListener.
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     **/
    protected abstract JButton[] createButtons();

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons().
     * Harus diimplementasikan sesuai class yang menginherit class ini.
     * @return Array of ActionListener.
     **/
    protected abstract ActionListener[] createActionListeners();
}
