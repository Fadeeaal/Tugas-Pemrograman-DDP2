package assignments.assignment3.nota;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;

import static assignments.assignment1.NotaGenerator.getHariPaket;
import static assignments.assignment1.NotaGenerator.getHargaPaket;

public class Nota {
    private Member member;
    private String paket;
    private List<LaundryService> services;
    private long baseHarga, finalHarga;
    private int sisaHariPengerjaan, berat, id;
    private String tanggalMasuk, tanggalSelesai;
    private boolean isDone;
    static public int totalNota;

    public Nota(Member member, int berat, String paket, String tanggal) {
        //Membuat constructor untuk Nota
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        isDone = false;
        id = NotaManager.notaList.size();
        baseHarga = getHargaPaket(paket);
        sisaHariPengerjaan = getHariPaket(paket);
        services = new ArrayList<>();
        services.add(new CuciService());
        tanggalSelesai = "";
    }

    //Method untuk memasukkan service yang digunakan oleh member
    public void addService(LaundryService service) {
        services.add(service);
    }

    /*Method untuk mengerjakan service yang digunakan oleh member
     *Setiap pesanan service akan dimasukkan ke array yang isinya mulai dari 1 (hanya cuci) hingga 3 service*/
    public String kerjakan() {
        String output = "";

        //Apabila service cucian belum selesai, maka akan masuk ke method doWork untuk dikerjakan
        if (!services.get(0).isDone()) {
            output = services.get(0).doWork();
            if(services.size() == 1){
                isDone = true;
                tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
            }
        }

        /*Apabila service > 1, akan diperiksa.
         *Apabila hanya 1 service tambahan, akan diperiksa service tambahan apa yang dipesan*/
        if (services.size() > 1 && !services.get(1).isDone() && output.equals("")) {
            output = services.get(1).doWork();
            if(services.get(1) instanceof AntarService){
                isDone = true;
                tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
            }else if(services.get(1) instanceof SetrikaService && services.size() == 2){
                isDone = true;
                tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
            }
        }

        if (services.size() > 2 && output.equals("") && !services.get(2).isDone()) {
            output = services.get(2).doWork();
            tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
            isDone = true;
        }

        //Dipanggil apabila sudah selesai
        if (output.equals("")) {
            output = "Sudah selesai.";
            if(!isDone){
                tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
                isDone = true;
            }
        }
        return output;
    }

    //Method untuk berpindah hari di notanya yang membuat sisa hari pengerjaannya berkurang
    public void toNextDay() {
        sisaHariPengerjaan -= 1;
    }

    //Method untuk menghitung harga
    public long calculateHarga() {
        long total = baseHarga * berat;
        for (LaundryService service : services) {
            total += service.getHarga(berat);
        }
        return total;
    }

    //Mengembalikan pesan nota
    public String getNotaStatus() {
        if (isDone){
            return "Sudah selesai.";
        }
        return "Belum selesai.";
    }

    //Akan dipanggil untuk mencetak format nota untuk member
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(tanggalMasuk.substring(6));
        int month = Integer.parseInt(tanggalMasuk.substring(3, 5)) - 1;
        int date = Integer.parseInt(tanggalMasuk.substring(0, 2));
        cal.set(year, month, date);
        cal.add(Calendar.DATE, getHariPaket(paket));
        long kompensasi;
        long beda = 0;

        try{
            Date dateExpectSelesai = NotaManager.fmt.parse(formatter.format(cal.getTime()));
            Date dateSelesai;
            if(tanggalSelesai.equals("")) dateSelesai = NotaManager.fmt.parse(formatter.format(NotaManager.cal.getTime()));
            else{
                dateSelesai = NotaManager.fmt.parse(tanggalSelesai);
            }

            if(dateSelesai.after(dateExpectSelesai)){
                beda = (dateSelesai.getTime() - dateExpectSelesai.getTime()) / (1000*60*60*24);
                kompensasi = 2000 * beda;
            }else{
                kompensasi = 0;
            }
        }catch(Exception e){
            kompensasi = 0;
        }

        String output = "[ID Nota = " + id + "]\n";
        output += "ID     : " + member.getId() + "\n";
        output += "Paket  : " + paket + "\n";
        output += "Harga  :\n";
        output += berat + " kg x " + baseHarga + " = " + (baseHarga * berat) + "\n";
        output += "tanggal terima  : " + tanggalMasuk + "\n";
        output += "Tanggal Selesai : " + formatter.format(cal.getTime()) + "\n";
        output += "--- SERVICE LIST ---\n";
        for (LaundryService service : services){
            output += "-" + service.getServiceName() + " @ Rp." + service.getHarga(berat) + "\n";
        }
        output += "Harga Akhir: ";

        //Kompensasi apabila ada keterlambatan pengerjaan. Jika kompensasi > harga akhir setelah pemotongan, akan mereturn nilai 0 (gratis)
        if(kompensasi > 0){
            finalHarga = calculateHarga() - kompensasi;
            if (finalHarga < 0) finalHarga = 0;
            output += finalHarga + " Ada kompensasi keterlambatan " + beda + " * 2000 hari\n";
        }else{
            output += calculateHarga() + "\n";
        }
        return output;
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan() {
        return sisaHariPengerjaan;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getTanggalSelesai(){
        return tanggalSelesai;
    }

    public List<LaundryService> getServices() {
        return services;
    }
}