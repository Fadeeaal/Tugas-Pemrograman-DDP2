package assignments.assignment3.nota.service;

public class CuciService implements LaundryService {
    private boolean isWorked = false;

    @Override
    public String doWork() {
        // TODO
        if (isWorked == true) {
            return "Sudah selesai.";
        }
        isWorked = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return isWorked;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
