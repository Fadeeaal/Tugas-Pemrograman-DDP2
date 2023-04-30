package assignments.assignment3.nota.service;

public class CuciService implements LaundryService {
    private boolean isWorked = false;

    @Override
    //Method untuk melakukan pekerjaan
    public String doWork() {
        if (isWorked == true) {
            return "Sudah selesai.";
        }
        isWorked = true;
        return "Sedang mencuci...";
    }

    //Method untuk tanda apakah sudah selesai
    @Override
    public boolean isDone() {
        return isWorked;
    }

    @Override
    //Method untuk menghitung harga di list service tambahan (harga mencuci)
    public long getHarga(int berat) {
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
