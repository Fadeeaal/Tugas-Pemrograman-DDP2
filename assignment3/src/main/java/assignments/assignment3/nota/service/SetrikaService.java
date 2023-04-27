package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService {
    private boolean isWorked = false;

    @Override
    public String doWork() {
        // TODO
        if (isWorked == true) {
            return "Sudah selesai.";
        }
        isWorked = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return isWorked;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        return berat * 1000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
