package assignments.assignment3.nota.service;

public class AntarService implements LaundryService {
    private boolean isWorked = false;

    @Override
    //Method untuk melakukan pekerjaan (mengantar)
    public String doWork() {
        if (isWorked == true) {
            return "Sudah selesai.";
        }
        isWorked = true;
        return "Sedang mengantar...";
    }

    //Method untuk tanda apakah sudah selesai
    @Override
    public boolean isDone() {
        return isWorked;
    }

    //Method untuk menghitung harga di list service tambahan (biaya antar laundry)
    @Override
    public long getHarga(int berat) {
        if (berat * 500 > 2000) {
            return berat * 500;
        }
        return 2000;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
