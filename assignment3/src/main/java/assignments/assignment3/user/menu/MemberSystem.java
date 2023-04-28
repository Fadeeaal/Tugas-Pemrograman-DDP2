package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import static assignments.assignment1.NotaGenerator.showPaket;

import java.util.List;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu
     * specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice){
            case 1 -> wantToLaundry();
            case 2 -> displayNota();
            case 3 -> logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        memberList.add(member);
    }

    protected void wantToLaundry(){
        String confirm = "";

        showPaket();
        System.out.println("Masukkan paket laundry:");
        String paket = in.nextLine();
        System.out.println("Masukkan berat cucian Anda [Kg]: ");
        String beratInput = in.nextLine();
        int berat = Integer.parseInt(beratInput);
        if (berat < 2) {
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            berat = 2;
        }
        Nota nota = new Nota(loginMember, berat, paket, NotaManager.fmt.format(NotaManager.cal.getTime()));

        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami? Hanya tambah 1000 / kg :0");
        System.out.print("[Ketik x untuk tidak mau]: ");
        confirm = in.nextLine();
        if (!confirm.equalsIgnoreCase("x")) {
            nota.addService(new SetrikaService());
        }

        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan! Cuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        confirm = in.nextLine();
        if (!confirm.equalsIgnoreCase("x")) {
            nota.addService(new AntarService());
        }

        System.out.println("Nota berhasil dibuat!");
        loginMember.addNota(nota); NotaManager.addNota(nota);
    }

    protected void displayNota(){
        if (loginMember.getNotaList().size() == 0) {
            System.out.println("Tidak ada nota yang dibuat");
        } else {
            List<Nota> notaList = loginMember.getNotaList();
            for (int i = 0; i < notaList.size(); i++) {
                System.out.print(notaList.get(i).toString());
                if (i < notaList.size() - 1) {
                    System.out.println();
                }
            }
        }
    }
}