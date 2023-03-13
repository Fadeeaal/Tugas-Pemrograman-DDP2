//NotaGenerator menggunakan solusi TP-1!!!

package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<>();
    private static ArrayList<Member> memberList = new ArrayList<>();

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {
        // TODO: handle generate user
        boolean isUserExist = false;
        System.out.println("Masukkan nama Anda:");
        String nama = input.nextLine();

        System.out.println("Masukkan nomor handphone Anda:");
        String nomorHP = input.nextLine();
        while (!isNumeric(nomorHP)) {
            System.out.println("Nomor hp hanya menerima digit");
            nomorHP = input.nextLine();
        }

        Member newMember = new Member(nama, nomorHP);

        for (Member member : memberList) {
            if (member.getId().equals(newMember.getId())) {
                isUserExist = true;
                System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!", nama, nomorHP).println();
                break;
            }
        }

        if (!isUserExist) {
            memberList.add(newMember);
            System.out.printf("Berhasil membuat member dengan ID %s!", newMember.getId()).println();
        }
    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
    }

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        System.out.printf("Terdaftar %d member dalam sistem", memberList.size()).println();
        for (int i = 0; i < memberList.size(); i++) {
            System.out.printf("- %s : %s", memberList.get(i).getId(), memberList.get(i).getNama()).println();
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

}
