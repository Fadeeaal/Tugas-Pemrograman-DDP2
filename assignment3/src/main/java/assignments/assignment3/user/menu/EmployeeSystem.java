package assignments.assignment3.user.menu;

import assignments.assignment3.user.Employee;

import static assignments.assignment3.nota.NotaManager.notaList;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        Employee.employeeCount = 0;
        memberList.add(new Employee("Dek Depe", "akuDDP"));
        Employee.employeeCount += 1;
        memberList.add(new Employee("Depram", "musiktualembut"));
        Employee.employeeCount += 1;
        memberList.add(new Employee("Lita Duo", "gitCommitPush"));
        Employee.employeeCount += 1;
        memberList.add(new Employee("Ivan Hoshimachi", "SuamiSahSuisei"));
        Employee.employeeCount += 1;
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu
     * specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice){
            case 1 -> beginNyuci();
            case 2 -> displayNota();
            case 3 -> logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }

    //Method yang akan dipanggil jika employee akan memulai untuk mencuci/menyetrika/mengantar
    protected void beginNyuci(){
        System.out.printf("Stand back! %s beginning to nyuci!", loginMember.getNama()).println();
        for (int i = 0; i < notaList.size(); i++) {
            System.out.println("Nota " + i + " : " + notaList.get(i).kerjakan());
        }
    }

    //Method untuk menampilkan progres nota, apakah sudah selesai atau belum
    protected void displayNota(){
        if (notaList.size() == 0) {
            System.out.println("Belum ada nota di dalam sistem!");
        } else {
            for (int i = 0; i < notaList.size(); i++) {
                System.out.print("Nota " + i + " : ");
                if (notaList.get(i).isDone()) {
                    System.out.println("Sudah selesai.");
                } else {
                    System.out.println("Belum selesai.");
                }
            }
        }
    }
}