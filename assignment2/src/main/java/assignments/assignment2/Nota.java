package assignments.assignment2;

public class Nota {
    //menambahkan attributes yang diperlukan untuk class ini
    private Member member;
    private String paket;
    private String tanggalMasuk;
    private int idNota;
    private int berat;
    private int sisaHariPengerjaan;
    private boolean isReady;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        //membuat constructor untuk class ini
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = 0;
        this.isReady = false;
    }

    //menambahkan methods (setter dan getter) yang diperlukan untuk class ini
    public int getIdNota(){
        return this.idNota;
    }

    public String getPaket(){
        return this.paket;
    }

    public int getBerat(){
        return this.berat;
    }

    public String getTanggalMasuk(){
        return this.tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return this.sisaHariPengerjaan;
    }

    public boolean getIsReady(){
        return this.isReady;
    }

    public Member getMember(){
        return this.member;
    }

    public void setIdNota(int idNota){
        this.idNota = idNota;
    }

    public void setPaket(String paket){
        this.paket = paket;
    }

    public void setBerat(int berat){
        this.berat = berat;
    }

    public void setTanggalMasuk(String tanggalMasuk){
        this.tanggalMasuk = tanggalMasuk;
    }

    public void setSisaHariPengerjaan(int sisaHariPengerjaan){
        this.sisaHariPengerjaan = sisaHariPengerjaan;
    }

    public void setIsReady(boolean isReady){
        this.isReady = isReady;
    }

    public void setMember(Member member){
        this.member = member;
    }
}