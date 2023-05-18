package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private static String[] paket = {"Express", "Fast", "Reguler"};
    private JLabel paketLabel = new JLabel("Paket Laundry:");
    private JComboBox<String> paketComboBox = new JComboBox<>(paket);
    private JButton showPaketButton = new JButton("Show Paket");
    private JLabel beratLabel = new JLabel("Berat Cucian (Kg):");
    private JTextField beratTextField = new JTextField(20);
    private JCheckBox setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000 / Kg)");
    private JCheckBox antarCheckBox = new JCheckBox("Tambah Antar Service (2000 / 4 Kg, kemudian 500 / Kg)");
    private JButton createNotaButton = new JButton("Buat Nota");
    private JButton backButton = new JButton("Kembali");

    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    String selectedPaket; int berat;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel
        initGUI();
    }

    //Method untuk menginisialisasi GUI
    private void initGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding untuk komponen
    
        // Paket Laundry
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(paketLabel, gbc);
    
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(paketComboBox, gbc);
    
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        showPaketButton.addActionListener(e -> showPaket());
        add(showPaketButton, gbc);
    
        // Berat Cucian
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(beratLabel, gbc);
    
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(beratTextField, gbc);
    
        // Tambah Setrika Service
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(setrikaCheckBox, gbc);
    
        // Tambah Antar Service
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Menggunakan 2 kolom
        gbc.anchor = GridBagConstraints.LINE_START; // Rata kiri
        add(antarCheckBox, gbc);
    
        // Tombol Buat Nota
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3; // Menggunakan 2 kolom
        gbc.anchor = GridBagConstraints.CENTER; // Tengah
        gbc.fill = GridBagConstraints.HORIZONTAL; // Memanjangkan secara horizontal
        createNotaButton.addActionListener(e -> createNota());
        add(createNotaButton, gbc);
    
        // Tombol Kembali
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3; // Menggunakan 2 kolom
        gbc.anchor = GridBagConstraints.CENTER; // Tengah
        gbc.fill = GridBagConstraints.HORIZONTAL; // Memanjangkan secara horizontal
        backButton.addActionListener(e -> handleBack());
        add(backButton, gbc);
    }    
    

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        selectedPaket = paketComboBox.getSelectedItem().toString();
        while (true) {
            try {
                berat = Integer.parseInt(beratTextField.getText());
                if (berat > 0) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Harap masukkan berat cucian Anda dalam bentuk bilangan positif",
                            "Input Error", JOptionPane.WARNING_MESSAGE);
                    beratTextField.setText("");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Harap masukkan berat cucian Anda dalam bentuk bilangan bulat",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
                        beratTextField.setText("");
                return;
            }
        }
        if (berat < 2) {
            berat = 2;
            JOptionPane.showMessageDialog(null, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg",
                    "", JOptionPane.WARNING_MESSAGE);
        }
        Member loginMember = memberSystemGUI.getLoggedInMember();
        Nota nota = new Nota(loginMember, berat, selectedPaket, fmt.format(cal.getTime()));

        if (setrikaCheckBox.isSelected()){
            nota.addService(new SetrikaService());
        }

        if (antarCheckBox.isSelected()){
            nota.addService(new AntarService());
        }

        NotaManager.addNota(nota); loginMember.addNota(nota);
        JOptionPane.showMessageDialog(null, "Nota berhasil dibuat!", "Pemesanan berhasil!", 
                                    JOptionPane.INFORMATION_MESSAGE); 
        clearAllFields();
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        clearAllFields();
        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
    }

    private void clearAllFields() {
        paketComboBox.setSelectedIndex(0); beratTextField.setText(""); 
        setrikaCheckBox.setSelected(false); antarCheckBox.setSelected(false);
    }
}