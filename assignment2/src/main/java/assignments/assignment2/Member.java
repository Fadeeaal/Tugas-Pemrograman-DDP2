package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private String noHp;
    private String id;
    private int counterDiscount;
    
    public Member(String nama, String noHp) {
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.noHp = noHp;
        this.id = NotaGenerator.generateId(nama, noHp);
        this.counterDiscount = 0;
    }

    // Getter untuk atribut nama
    public String getNama() {
        return nama;
    }
    
    // Getter untuk atribut noHp
    public String getNoHp() {
        return noHp;
    }

    // Getter untuk atribut id
    public String getId() {
        return this.id;
    }
    
    // Setter untuk atribut nama
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Setter untuk atribut noHp
    public void setNoHp(String noHp) {
    this.noHp = noHp;
    }
    
    public void resetDiscount(){
        this.counterDiscount = 0;
    }

    public boolean cekDiscount(){
        this.counterDiscount += 1;
        if (this.counterDiscount != 3){
            return false;
        }
        resetDiscount();
        return true;
    }
    // TODO: tambahkan methods yang diperlukan untuk class ini
}
