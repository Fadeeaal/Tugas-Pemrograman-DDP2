package assignments.assignment3.nota;

import assignments.assignment1.NotaGenerator;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;

import static assignments.assignment1.NotaGenerator.getHargaPaket;
import static assignments.assignment1.NotaGenerator.getHariPaket;

public class Nota {
    private final Member member;
    private final String paket;
    private LaundryService[] services;
    private final long baseHarga;
    private int sisaHariPengerjaan;
    private final int berat;
    private final int id;
    private final String tanggalMasuk;
    private boolean isDone;
    static public int totalNota;

    public Nota(Member member, int berat, String paket, String tanggal) {
        this.id = totalNota;
        this.paket = paket;
        this.member = member;
        this.berat = berat;
        this.baseHarga = getHargaPaket(paket);
        this.sisaHariPengerjaan = getHariPaket(paket);
        this.services = new LaundryService[]{new CuciService()};
        isDone = false;
        totalNota++;
        tanggalMasuk = tanggal;
    }

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }
    public void addService(LaundryService service){
        int n = services.length;
        LaundryService[] newarr = new LaundryService[n + 1];
        System.arraycopy(services, 0, newarr, 0, n);
        newarr[n] = service;
        services = newarr;
    }

    public LaundryService[] getServices(){
        return services;
    }

    public String kerjakan(){
        String message  = String.format("Nota %d : ", id);
        for (LaundryService service:
             services) {
            if(service.isDone()){
                continue;
            }
            message += service.doWork();
            if(service == services[services.length-1]){
                isDone = true;
            }
            return message;
        }
        return message + "Sudah selesai.";
    }
    public void toNextDay() {
        if(!isDone){
            sisaHariPengerjaan--;
        }
    }

    public long calculateHarga(){
        long totalHarga = baseHarga * berat;
        for (LaundryService service:
             services) {
            totalHarga += service.getHarga(berat);
        }
        if(sisaHariPengerjaan < 0){
            totalHarga += sisaHariPengerjaan * 2000L;
        }
        if(totalHarga < 0){
            return 0;
        }
        return totalHarga;
    }

    public String getNotaStatus(){
        String message  = String.format("Nota %d : ", id);
        if(isDone){
            return message + "Sudah selesai.";
        }
        return message + "Belum selesai.";
    }

    @Override
    public String toString(){
        StringBuilder nota = new StringBuilder(String.format("[ID Nota = %d]\n", id));

        nota.append(NotaGenerator.generateNota(
                String.valueOf(member.getId()),
                paket,
                berat,
                tanggalMasuk)
        );
        nota.append("\n--- SERVICE LIST ---\n");
        for (LaundryService service:
             services) {
            nota.append(String.format("-%s @ Rp.%d\n", service.getServiceName(), service.getHarga(berat)));
        }
        nota.append("Harga Akhir: ").append(calculateHarga());
        if(sisaHariPengerjaan < 0){
            nota.append(String.format(" Ada kompensasi keterlambatan %d * 2000 hari\n", sisaHariPengerjaan * -1));
        }
        else {
            nota.append("\n");
        }

        return nota.toString();
    }


}
