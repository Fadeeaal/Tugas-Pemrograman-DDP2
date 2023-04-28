package assignments.assignment3.nota.service;

public class AntarService implements LaundryService {
    private boolean isWorked = false;

    @Override
    public String doWork() {
        if (isWorked == true) {
            return "Sudah selesai.";
        }
        isWorked = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return isWorked;
    }

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
