package assignments.assignment3.nota;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotaManager {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    public static Calendar cal = Calendar.getInstance();
    static public Nota[] notaList = new Nota[0];

    public static void toNextDay(){
        cal.add(Calendar.DATE, 1);
        for (Nota nota:
             notaList) {
            nota.toNextDay();
        }
    }

    public static void addNota(Nota nota){
        int n = notaList.length;
        Nota[] newarr = new Nota[n + 1];
        System.arraycopy(notaList, 0, newarr, 0, n);

        newarr[n] = nota;
        notaList = newarr;
    }
}
