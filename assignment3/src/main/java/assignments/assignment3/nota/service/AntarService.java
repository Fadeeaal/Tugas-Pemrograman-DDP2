package assignments.assignment3.nota.service;

public class AntarService implements LaundryService{
    private boolean isDone = false;
    @Override
    public String doWork() {
        isDone = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        if(berat < 4){
            berat = 4;
        }
        return 500L * berat;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
