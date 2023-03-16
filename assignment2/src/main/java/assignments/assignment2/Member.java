package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    //menambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;
    public Member(String nama, String noHp) {
        /*membuat constructor untuk class ini
         *untuk membuat id, menggunakan method dari NotaGenerator, yaitu generateId untuk mempermudah pengerjaan
        */
        this.id = NotaGenerator.generateId(nama, noHp);
        this.nama = nama;
        this.noHp = noHp;
        this.bonusCounter = 0;
    }

    //menambahkan methods (setter dan getter) yang diperlukan untuk class ini
    public String getNama() {
        return this.nama;
    }

    public String getNoHp() {
        return this.noHp;
    }

    public String getId() {
        return this.id;
    }

    public int getBonusCounter() {
        return this.bonusCounter;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBonusCounter(int bonusCounter) {
        this.bonusCounter = bonusCounter;
    }
}