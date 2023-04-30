package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService {
    private boolean isWorked = false;

    @Override
    //Method untuk melakukan pekerjaan (menyetrika)
    public String doWork() {
        if (isWorked == true) {
            return "Sudah selesai.";
        }
        isWorked = true;
        return "Sedang menyetrika...";
    }

    //Method untuk tanda apakah sudah selesai
    @Override
    public boolean isDone() {
        return isWorked;
    }

    //Method untuk menghitung harga di list service tambahan (biaya tambahan untuk menyetrika)
    @Override
    public long getHarga(int berat) {
        return berat * 1000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
