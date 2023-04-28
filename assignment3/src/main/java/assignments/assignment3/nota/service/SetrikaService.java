package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService {
    private boolean isWorked = false;

    @Override
    public String doWork() {
        if (isWorked == true) {
            return "Sudah selesai.";
        }
        isWorked = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return isWorked;
    }

    @Override
    public long getHarga(int berat) {
        return berat * 1000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
