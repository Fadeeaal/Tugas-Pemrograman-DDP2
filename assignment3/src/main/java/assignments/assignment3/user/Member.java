package assignments.assignment3.user;

import assignments.assignment3.nota.Nota;

public class Member {
    protected String id;
    protected String password;
    protected String nama;
    protected Nota[] notaList = new Nota[0];

    public Member(String nama, String id, String password) {
        this.nama = nama;
        this.id = id;
        this.password = password;
    }

    public boolean login(String id, String password) {
        return id.equals(this.id) && authenticate(password);
    }

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public Nota[] getNotaList() {
        return notaList;
    }

    public void addNota(Nota nota){
        int n = notaList.length;
        Nota[] newarr = new Nota[n + 1];
        System.arraycopy(notaList, 0, newarr, 0, n);

        newarr[n] = nota;
        notaList = newarr;
    }

    protected boolean authenticate(String password) {
        return this.password.equals(password);
    }
}
