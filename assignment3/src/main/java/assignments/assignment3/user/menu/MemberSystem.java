package assignments.assignment3.user.menu;
import java.util.Arrays;

import assignments.assignment3.user.Member;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        // TODO
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        // TODO
        Member[] temp = Arrays.copyOf(memberList, memberList.length + 1); // create a new array with the size increased by 1
        temp[temp.length - 1] = member; // add new member to the last element of the array
        memberList = temp; // assign the new array to memberList
        System.out.println(memberList);
    }
}