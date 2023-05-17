package assignments.assignment3.user.menu;

import assignments.assignment3.user.Member;

import java.util.Scanner;

public abstract class SystemCLI {
   protected Member[] memberList = new Member[0];
   protected Member loginMember;
   protected Scanner in;

    public void login(Scanner in, String inputId, String inputPassword){
        Member authMember = authUser(inputId, inputPassword);

        if (authMember != null) {
            this.in = in;
            System.out.println("Login successful!");
            run(in, authMember);
            return;
        }

        System.out.println("Invalid ID or password.");
    }

    public Member authUser(String id, String pass) {
        for (Member user : memberList) {
            if (!user.getId().equals(id)) {
                continue;
            }
            if(user.login(id, pass)){
                return user;
            }
            return null;
        }
        return null;
    }

    public boolean isMemberExist(String id){
        for (Member member:
                memberList) {
            if(member.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public void run(Scanner in, Member member){
        loginMember = member;
        boolean logout = false;
        while (!logout) {
            displayMenu();
            int choice = in.nextInt();
            in.nextLine();
            logout = processChoice(choice);
        }
        loginMember = null;
        System.out.println("Logging out...");

    }

    protected void displayMenu(){
        System.out.printf("\nLogin as : %s\nSelamat datang %s!\n\n", loginMember.getId(), loginMember.getNama());
        displaySpecificMenu();
        System.out.print("Apa yang ingin Anda lakukan hari ini? ");
    }

    protected abstract boolean processChoice(int choice);

    protected abstract void displaySpecificMenu();
}
