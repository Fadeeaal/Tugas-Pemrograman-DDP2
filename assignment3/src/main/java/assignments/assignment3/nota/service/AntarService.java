package assignments.assignment3.nota.service;

public class AntarService implements LaundryService {
    private boolean isWorked = false;

    @Override
    public String doWork() {
        // TODO
        if (isWorked == true) {
            return "Sudah selesai.";
        }
        isWorked = true;
        return "sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return isWorked;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        if (berat * 500 >= 2000) {
            return berat * 500;
        }
        return 2000;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
