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
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // TODO
        JButton nyuciButton = new JButton("It's nyuci time");
        JButton checkButton = new JButton("Display List Nota");
        return new JButton[]{
            nyuciButton, checkButton
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
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
     * */
    private void cuci() {
        // TODO
        if (NotaManager.notaList.length == 0){
            JOptionPane.showMessageDialog(this, "Belum ada pesanan apapun :(", "Pesan", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            String doAllNota = "";
            for (Nota nota : NotaManager.notaList){
                doAllNota += nota.kerjakan() + "\n";
            }
            JOptionPane.showMessageDialog(null, "Stand back! " + loggedInMember.getNama() + " beginning to nyuci!",
                                                    "Employee on the duty!", JOptionPane.INFORMATION_MESSAGE);
            JTextArea detailNota = new JTextArea(doAllNota);
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
     * */
    private void displayNota() {
        // TODO
        if (NotaManager.notaList.length == 0){
            JOptionPane.showMessageDialog(this, "Belum ada pesanan apapun :(", "Tidak ada pesanan", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            String allNota = "";
            for (Nota nota : NotaManager.notaList){
                allNota += nota.getNotaStatus() + "\n";
            }
            JTextArea detailNota = new JTextArea(allNota);
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