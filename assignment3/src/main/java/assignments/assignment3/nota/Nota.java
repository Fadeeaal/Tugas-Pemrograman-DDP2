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
    private String paket, tanggalMasuk, tanggalSelesai;
    private List<LaundryService> services;
    private long baseHarga;
    private int sisaHariPengerjaan, berat, id;
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
        services.add(service);
    }

    public String kerjakan() {
        String output = "";
        if (!services.get(0).isDone()) {
            output = services.get(0).doWork();
        }

        if (services.size() > 1 && !services.get(1).isDone() && output.equals("")) {
            output = services.get(1).doWork();
            
            if (services.get(1) instanceof AntarService) {
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
        sisaHariPengerjaan -= 1;
    }

    public long calculateHarga() {
        long total = baseHarga * berat;
        for (LaundryService service : services) {
            total += service.getHarga(berat);
        }
        return total;
    }

    public String getNotaStatus() {
        String output = "";
        if (sisaHariPengerjaan > 0) {
            output = "Sedang mencuci...";
        }

        if (services.size() > 1) {
            if (services.get(1) instanceof SetrikaService && !services.get(1).isDone() && output.equals("")) {
                output = "Sedang menyetrika...";
            } else if (services.get(1) instanceof AntarService && !services.get(1).isDone() && output.equals("")) {
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(tanggalMasuk.substring(6));
        int month = Integer.parseInt(tanggalMasuk.substring(3, 5)) - 1;
        int date = Integer.parseInt(tanggalMasuk.substring(0, 2));
        cal.set(year, month, date);
        cal.add(Calendar.DATE, getHariPaket(paket));
        long kompensasi, beda = 0;

        try{
            Date dateExpectSelesai = NotaManager.fmt.parse(formatter.format(cal.getTime())); Date dateSelesai;
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

        String output = "[ID Nota = " + id + "]\n" + 
        "ID     : " + member.getId() + "\n" + 
        "Paket  : " + paket + "\n" + 
        "Harga  :\n" + 
        berat + " kg x " + baseHarga + " = " + (baseHarga * berat) + "\n" + 
        "tanggal terima  : " + tanggalMasuk + "\n" + 
        "tanggal selesai : " + formatter.format(cal.getTime()) + "\n" + 
        "--- SERVICE LIST ---\n" + 
        "-Cuci @ Rp." + services.get(0).getHarga(berat) + "\n";

        if (services.size() > 1) {
            if (services.get(1) instanceof SetrikaService) {
                output += "-Setrika @ Rp." + services.get(1).getHarga(berat) + "\n";
            } else {
                output += "-Antar @ Rp." + services.get(1).getHarga(berat) + "\n";
            }
        }

        if (services.size() > 2) {
            output += "-Antar @ Rp." + services.get(2).getHarga(berat) + "\n";
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
