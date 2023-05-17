package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import static assignments.assignment1.NotaGenerator.*;
import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class MemberSystem extends SystemCLI {


    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice){
            case 1 -> createNota();
            case 2 -> showDetailNota();
            case 3 -> logout = true;
            default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
        }
        return logout;
    }

    private void showDetailNota() {
        for (Nota nota:
             loginMember.getNotaList()) {
            System.out.println(nota.toString() + "\n");
        }
    }

    private void createNota() {
        var paket = "";
        System.out.println("Masukan paket laundry: ");
        showPaket();
        paket = in.nextLine();
        System.out.println("Masukan berat cucian anda [Kg]: ");
        var beratS = in.nextLine();
        var berat = Integer.parseInt(beratS);

        if (berat < 2) {
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            berat = 2;
        }

        Nota nota = new Nota(loginMember, berat, paket, fmt.format(cal.getTime()));

        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
        System.out.println("Hanya tambah 1000 / kg :0");
        System.out.print("[Ketik x untuk tidak mau]: ");
        if(!in.nextLine().equalsIgnoreCase("x")){
            nota.addService(new SetrikaService());
        }
        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
        System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        if(!in.nextLine().equalsIgnoreCase("x")){
            nota.addService(new AntarService());
        }

        NotaManager.addNota(nota);
        loginMember.addNota(nota);

        System.out.println("Nota berhasil dibuat!");
    }

    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    public void addMember(Member member) {
        int n = memberList.length;
        Member[] newarr = new Member[n + 1];
        System.arraycopy(memberList, 0, newarr, 0, n);

        newarr[n] = member;
        memberList = newarr;
    }
}
