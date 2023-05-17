package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // TODO
        JButton laundryButton = new JButton("Saya ingin laundry");
        JButton checkNotaButton = new JButton("Lihat detail nota saya");
        return new JButton[]{
            laundryButton, checkNotaButton
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
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        // TODO
        MainFrame.getInstance().navigateTo(CreateNotaGUI.KEY);
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        // TODO
        if (loggedInMember.getNotaList().length == 0){
            JOptionPane.showMessageDialog(this, "Anda belum melakukan pemesanan apapun", "Pesan", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            String allNota = "";
            for (Nota nota : loggedInMember.getNotaList()){
                allNota += nota.toString() + "\n";
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
