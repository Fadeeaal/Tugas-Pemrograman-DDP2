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
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private String tanggalSelesai;
    private boolean isDone;
    static public int totalNota;

    public Nota(Member member, int berat, String paket, String tanggal) {
        // TODO
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

    public void addService(LaundryService service) {
        // TODO
        services.add(service);
    }

    public String kerjakan() {
        // TODO
        String output = "";
        if (!services.get(0).isDone()) {
            output = services.get(0).doWork();
        }

        if (services.size() > 1 && !services.get(1).isDone() && output.equals("")) {
            output = services.get(1).doWork();

            if(services.get(1).getClass() == AntarService.class){
                isDone = true;
                tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
            }
        }

        if (services.size() > 2 && output.equals("") && !services.get(2).isDone()) {
            output = services.get(2).doWork();
            tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
            isDone = true;
        }

        if (output.equals("")) {
            output = "Sudah selesai.";
            if(!isDone){
                tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
                isDone = true;
            }
        }

        return output;
    }

    public void toNextDay() {
        // TODO
        sisaHariPengerjaan -= 1;
    }

    public long calculateHarga() {
        // TODO
        long total = baseHarga * berat;
        for (LaundryService service : services) {
            total += service.getHarga(berat);
        }

        return total;
    }

    public String getNotaStatus() {
        // TODO
        String output = "";
        if (sisaHariPengerjaan > 0) {
            output = "Sedang mencuci...";
        }

        if (services.size() > 1) {
            if (services.get(1).getClass() == SetrikaService.class && !services.get(1).isDone() && output.equals("")) {
                output = "Sedang menyetrika...";
            } else if (services.get(1).getClass() == AntarService.class && !services.get(1).isDone()
                    && output.equals("")) {
                output = "Sedang mengantar...";
                if(!isDone){
                    isDone = true;
                    tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
                }
            }
        }

        if (services.size() > 2 && output.equals("") && !services.get(1).isDone()) {
            output = "Sedang mengantar...";
            if(!isDone){
                isDone = true;
                tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
            }
        }

        if (output.equals("")) {
            output = "Sudah selesai.";
            if(!isDone){
                tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
                isDone = true;
            }
        }

        return output;
    }

    @Override
    public String toString() {
        // TODO
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
            if(tanggalSelesai.equals("")){
                dateSelesai = NotaManager.fmt.parse(formatter.format(NotaManager.cal.getTime()));
            }else{
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
        output += "Cuci @ Rp." + services.get(0).getHarga(berat) + "\n";

        if (services.size() > 1) {
            if (services.get(1).getClass() == SetrikaService.class) {
                output += "Setrika @ Rp." + services.get(1).getHarga(berat) + "\n";
            } else {
                output += "Antar @ Rp." + services.get(1).getHarga(berat) + "\n";
            }
        }

        if (services.size() > 2) {
            output += "Antar @ Rp." + services.get(2).getHarga(berat) + "\n";
        }

        output += "Harga Akhir: ";

        if(kompensasi > 0){
            output += (calculateHarga() - kompensasi) + " Ada kompensasi keterlambatan " + beda + " * 2000 hari\n";
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
