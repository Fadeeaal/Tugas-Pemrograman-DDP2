/*Nama - NPM : Rakha Fadil Atmojo - 2206082985 
 *Kelas - Kode Asdos : DDP 2-F - DIV
 *Tugas Pemrograman 02*/

//PENTING !!! UNTUK FILE NotaGenerator SAYA SESUAIKAN DENGAN SOLUSI TP1 YANG DIBERIKAN OLEH ASDOS MELALUI SCELE!!!
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
    private static int idCounter = 0;

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
        String nama, noHp;
        boolean isExist = false;

        System.out.println("Masukkan nama Anda:");
        nama = input.nextLine();

        System.out.println("Masukkan nomor handphone Anda:");
        while(true){
            try{
                noHp = input.nextLine();
                Long.parseLong(noHp); 
                break;

            }catch(Exception e){
                System.out.println("Field nomor hp hanya menerima digit.");
            }
        }

        for(Member m : memberList){
            if(m.getNama().equals(nama) || m.getNoHp().equals(noHp)){
                isExist = true;
                break;
            }
        }

        if(!isExist){
            Member member = new Member(nama, noHp);
            System.out.println("Berhasil membuat member dengan ID " + member.getId());
            memberList.add(member);
        }
        else{
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!", nama, noHp).println();
        }
    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        Member member = null;
        String id, paket;
        int berat, harga, estimasi;
        boolean isDiskon = false;
        Calendar tanggalSelesai = Calendar.getInstance();

        System.out.println("Masukkan ID member:");
        id = input.nextLine();

        for(int i=0; i<memberList.size(); i++){
            if(memberList.get(i).getId().equals(id)){
                member = memberList.get(i);
                member.setBonusCounter(member.getBonusCounter() + 1);

                if(member.getBonusCounter() == 3){
                    isDiskon = true;
                    member.setBonusCounter(0);
                }
                memberList.set(i, member);
                break;
            }
        }

        if(member != null){
            while(true){
                System.out.println("Masukkan paket laundry:");
                paket = input.nextLine();

                if(paket.equals("?")){
                    showPaket();
                }else{
                    if(paket.equalsIgnoreCase("express") || paket.equalsIgnoreCase("fast") || paket.equalsIgnoreCase("reguler")){
                        if(paket.equalsIgnoreCase("express")){
                            harga = 12000;
                            estimasi = 1;
                        }else if(paket.equalsIgnoreCase("fast")){
                            harga = 10000;
                            estimasi = 2;
                        }else{
                            harga = 7000;
                            estimasi = 3;
                        }
                        break;
                    }else{
                        System.out.println("Paket tidak ditemukan! Input ? untuk mengetahui daftar paket!");
                    }
                }
            }

            while(true){
                try{
                    System.out.println("Masukkan berat cucian Anda [Kg]:");
                    berat = Integer.parseInt(input.nextLine());

                    if(berat > 0){
                        break;
                    }
                    else{
                        System.out.println("Berat tidak boleh kurang atau sama dengan 0!");
                    }
                }catch(Exception e){
                    System.out.println("Berat hanya menerima angka!");
                }
            }

            if(berat < 2){
                System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                berat = 2;
            }

            Nota nota = new Nota(member, paket, berat, fmt.format(cal.getTime()));
            nota.setIdNota(idCounter++);
            nota.setSisaHariPengerjaan(estimasi);

            System.out.println("Berhasil menambahkan nota!");
            System.out.println("[ID Nota = " + nota.getIdNota() + "]");
            System.out.println("ID     : " + member.getId());
            System.out.println("Paket  : " + paket);
            System.out.println("Harga  :");
            System.out.print(berat + " kg x " + harga + " = " + (berat * harga));

            if(isDiskon){
                System.out.println("= " + ((harga * berat) - ((harga * berat / 100) * 50)) + " (Discount member 50%!!!)");
            }else{
                System.out.println();
            }

            System.out.printf("Tanggal Terima  : %s", nota.getTanggalMasuk()).println();
            System.out.printf("Tanggal Selesai : %s", fmt.format(tanggalSelesai.getTime())).println();
            System.out.printf("Status          : %s",(nota.getIsReady() ? "Sudah dapat diambil!" : "Belum bisa diambil :(")).println();

            notaList.add(nota);
        }else{
            System.out.printf("Member dengan ID %s tidak ditemukan!", id).println();
        }
    }

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        System.out.printf("Terdaftar %d nota dalam sistem.", notaList.size()).println();
        for(Nota n : notaList){
            System.out.println("- [" + n.getIdNota() + "]\t: " + (n.getIsReady() ? "Sudah dapat diambil!" : "Belum bisa diambil :("));
        }
    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        System.out.printf("Terdaftar %d member dalam sistem.", memberList.size()).println();
        for(Member m : memberList){
            System.out.printf("- %s : %s", m.getId(), m.getNama()).println();
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
        int idNota, indexNota = -1;
        
        while(true){
            try{
                System.out.println("Masukkan ID nota yang akan diambil:");
                idNota = Integer.parseInt(input.nextLine());
                break;
            }catch(Exception e){
                System.out.println("ID nota berbentuk angka!");
            }
        }

        for(int i=0; i<notaList.size(); i++){
            if(notaList.get(i).getIdNota() == idNota){
                indexNota = i;
                break;
            }
        }

        System.out.print("Nota dengan ID " + idNota);
        if(indexNota != -1){
            if(notaList.get(indexNota).getIsReady()){
                System.out.println(" berhasil diambil!");
                notaList.remove(indexNota);
            }else{
                System.out.println(" gagal diambil!");
            }
        }else{
            System.out.println(" tidak ditemukan!");
        }
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
        cal.setTime(cal.getTime());
        cal.add(Calendar.DATE, 1);

        System.out.println("Dek Depe tidur hari ini... zzz...");
        for(int i=0; i<notaList.size(); i++){
            Nota n = notaList.get(i);
            
            n.setSisaHariPengerjaan(n.getSisaHariPengerjaan() - 1);

            if(n.getSisaHariPengerjaan() == 0){
                n.setIsReady(true);
                System.out.println("Laundry dengan nota ID " + n.getIdNota() + " sudah dapat diambil!");
            }
            notaList.set(i, n);
        }

        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
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