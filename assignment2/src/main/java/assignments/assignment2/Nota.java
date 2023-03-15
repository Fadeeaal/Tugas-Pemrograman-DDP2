package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private Member member;
    private String paket;
    private String tanggalMasuk;
    private int idNota;
    private int berat;
    private int sisaHariPengerjaan;
    private boolean isReady;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        // TODO: buat constructor untuk class ini
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = 0;
        this.isReady = false;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public void setIdNota(int idNota){
        this.idNota = idNota;
    }

    public int getIdNota(){
        return this.idNota;
    }

    public void setPaket(String paket){
        this.paket = paket;
    }

    public String getPaket(){
        return this.paket;
    }

    public void setBerat(int berat){
        this.berat = berat;
    }

    public int getBerat(){
        return this.berat;
    }

    public void setTanggalMasuk(String tanggalMasuk){
        this.tanggalMasuk = tanggalMasuk;
    }

    public String getTanggalMasuk(){
        return this.tanggalMasuk;
    }

    public void setSisaHariPengerjaan(int sisaHariPengerjaan){
        this.sisaHariPengerjaan = sisaHariPengerjaan;
    }

    public int getSisaHariPengerjaan(){
        return this.sisaHariPengerjaan;
    }

    public void setIsReady(boolean isReady){
        this.isReady = isReady;
    }

    public boolean getIsReady(){
        return this.isReady;
    }

    public void setMember(Member member){
        this.member = member;
    }

    public Member getMember(){
        return this.member;
    }
}