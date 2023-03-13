package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    private int idNota;
    private Member member;
    private String paket;
    private String tanggalMasuk;
    private int berat;
    private boolean isReady;

    // TODO: tambahkan attributes yang diperlukan untuk class ini
    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        // TODO: buat constructor untuk class ini
        this.idNota += 1;
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.isReady = false;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
}
