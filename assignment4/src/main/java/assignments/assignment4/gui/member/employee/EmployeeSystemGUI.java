package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini belum memiliki ActionListener.
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        JButton nyuciButton = new JButton("It's nyuci time");
        JButton checkNotaButton = new JButton("Display List Nota");
        return new JButton[]{
            nyuciButton, checkNotaButton
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     **/
    private void cuci() {
        JOptionPane.showMessageDialog(null, "Stand back! " + loggedInMember.getNama() + " beginning to nyuci!",
                                    "Employee on the duty!", JOptionPane.INFORMATION_MESSAGE);

        if (NotaManager.notaList.length == 0){ //Jika belum ada nota sama sekali
            JOptionPane.showMessageDialog(this, "Belum ada pesanan apapun :(",
                                     "Tidak ada pesanan", JOptionPane.INFORMATION_MESSAGE);
        }
        else{ //Sudah ada nota yang didaftarkan
            String doAllNota = "";
            for (Nota nota : NotaManager.notaList){
                doAllNota += nota.kerjakan() + "\n";
            }
            JTextArea detailNota = new JTextArea(doAllNota);    //Membuat text Area dan scroll pane
            detailNota.setEditable(false);
            detailNota.setLineWrap(true);
            detailNota.setWrapStyleWord(true);

            JScrollPane scrollAble = new JScrollPane(detailNota);
            scrollAble.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollAble.setPreferredSize(new Dimension(300, 200));
            JOptionPane.showMessageDialog(null, scrollAble, "List Nota", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     **/
    private void displayNota() {
        if (NotaManager.notaList.length == 0){  //Jika belum ada nota sama sekali
            JOptionPane.showMessageDialog(this, "Belum ada pesanan apapun :(", "Tidak ada pesanan", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            String allNota = "";
            for (Nota nota : NotaManager.notaList){
                allNota += nota.getNotaStatus() + "\n";
            }
            JTextArea detailNota = new JTextArea(allNota);  //Membuat text Area dan scroll pane
            detailNota.setEditable(false);
            detailNota.setLineWrap(true);
            detailNota.setWrapStyleWord(true);

            JScrollPane scrollAble = new JScrollPane(detailNota);
            scrollAble.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollAble.setPreferredSize(new Dimension(300, 200));
            JOptionPane.showMessageDialog(null, scrollAble, "Detail nota", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}