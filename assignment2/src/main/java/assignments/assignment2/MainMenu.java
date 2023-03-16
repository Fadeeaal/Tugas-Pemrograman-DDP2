/*Nama - NPM : Rakha Fadil Atmojo - 2206082985 
 *Kelas - Kode Asdos : DDP 2-F - DIV
 *Tugas Pemrograman 02*/

//PENTING !!! UNTUK FILE NotaGenerator SAYA SAMAKAN DENGAN SOLUSI TP1 YANG DIBERIKAN OLEH ASDOS MELALUI SCELE!!!

package assignments.assignment2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    //Membuat attributes yang diperlukan dahulu
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<>();
    private static ArrayList<Member> memberList = new ArrayList<>();
    private static int idCounter = 0;

    //Program main menunya
    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            //Menampilkan pilihan yang tersedia serta meminta user untuk memasukkan instruksi
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
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali."); //Menghasilkan output ini apabila pilihan user tidak ada di pilihan program
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    //Method untuk handle generate user
    private static void handleGenerateUser() {
        String nama, noHp;
        boolean isExist = false;

        //Memnita data user berupa nama dan No HP
        System.out.println("Masukkan nama Anda:");
        nama = input.nextLine();

        System.out.println("Masukkan nomor handphone Anda:");
        while(true){
            //Melakukan pemeriksaan, apakah no HP user sudah sesuai dengan ketentuan?
            try{
                noHp = input.nextLine();
                Long.parseLong(noHp);
                break;
            }catch(Exception e){
                System.out.println("Field nomor hp hanya menerima digit.");
            }
        }

        Member member = new Member(nama, noHp); //Masuk ke class member untuk membuat ID nya

        //Untuk memeriksa, apakah ID yang baru dibuat sudah ada di list membernya?
        for(Member m : memberList){
            if(m.getId().equals(member.getId())){
                isExist = true;
                break;
            }
        }

        //Jika belum ada, maka ID user terbaru akan dimasukkan. Jika sudah ada, maka ID tidak akan diduplikat
        if(!isExist){
            System.out.printf("Berhasil membuat member dengan ID %s!", member.getId()).println();
            memberList.add(member);
        }
        else{
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!", nama, noHp).println();
            return;
        }
    }

    //Method untuk membuat nota
    private static void handleGenerateNota() {
        String id, paket;
        Member member = null;
        int berat, hariKerja;
        long harga;
        boolean isDiskon = false;
        Calendar tanggalSelesai = Calendar.getInstance();

        //Meminta id member
        System.out.println("Masukkan ID member:");
        id = input.nextLine();

        //Memeriksa apakah member sudah terdaftar? dan jika sudah terdaftar, apakah diskon akan diberikan?
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

        //Jika member terdaftar, akan diperbolehkan untuk generate nota dan akan menolak generate nota jika terjadi sebaliknya
        if(member != null){

            //Meminta data-data laundry (paket yangg dipilih dan beratnya) dan menghitung
            while(true){
                System.out.println("Masukkan paket laundry:");
                paket = input.nextLine();

                if(paket.equals("?")){
                    showPaket();
                }
                else{
                    harga = getHargaPaket(paket.toLowerCase());
                    if (harga == -1) {
                        System.out.println("Paket tidak ditemukan! Input ? untuk mengetahui daftar paket!");
                    } else {
                        hariKerja = getHariPaket(paket.toLowerCase());
                        break;
                    }
                }
            }

            while(true){
                try{
                    System.out.println("Masukkan berat cucian Anda [Kg]:");
                    berat = Integer.parseInt(input.nextLine());

                    if(berat > 0){
                        break;
                    }else{
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

            //Memasukkan ke class nota untuk memproses notanya
            Nota nota = new Nota(member, paket, berat, fmt.format(cal.getTime()));
            nota.setIdNota(idCounter++);
            nota.setSisaHariPengerjaan(hariKerja);

            try{
                tanggalSelesai.setTime(fmt.parse(fmt.format(cal.getTime())));
                tanggalSelesai.add(Calendar.DATE, hariKerja);
            }catch(Exception e){
                System.out.println("Tanggal tidak valid!");
                return;
            }

            //Program menghasilkan nota (termasuk kapan laundry bisa diambil) dan apabila menerima diskon, akan memotong harga laundrynya sebanyak 50%
            System.out.println("Berhasil menambahkan nota!");
            System.out.printf("[ID Nota = %d]", nota.getIdNota()).println();
            System.out.printf("ID     : %s", member.getId()).println();;
            System.out.printf("Paket  : %s",  paket).println();
            System.out.println("Harga  :");
            System.out.printf("%d kg x %d = %d", berat, harga, (long)berat*harga);
            if(isDiskon){
                System.out.printf(" = %d %s", ((harga * (long)berat) - ((harga * (long)berat / 100) * 50)), "(Discount member 50%!!!)").println();
            }else{
                System.out.println();
            }
            System.out.printf("Tanggal Terima  : %s", nota.getTanggalMasuk()).println();
            System.out.printf("Tanggal Selesai : %s", fmt.format(tanggalSelesai.getTime())).println();
            System.out.printf("Status          : %s", (nota.getIsReady() ? "Sudah dapat diambil!" : "Belum bisa diambil :(")).println();;
            notaList.add(nota);

        }else{
            System.out.printf("Member dengan ID %s tidak ditemukan!", id).println();
        }
    }

    //Method untuk mencetak list nota pada sistem
    private static void handleListNota() {
        System.out.printf("Terdaftar %d nota dalam sistem.", notaList.size()).println();
        for(Nota n : notaList){
            System.out.println("- [" + n.getIdNota() + "] Status\t: " + (n.getIsReady() ? "Sudah dapat diambil!" : "Belum bisa diambil :("));
        }
    }

    //Method untuk mencetak list user pada sistem
    private static void handleListUser() {
        System.out.printf("Terdaftar %d member dalam sistem.", memberList.size()).println();
        for(Member m : memberList){
            System.out.printf("- %s : %s", m.getId(), m.getNama()).println();
        }
    }

    //Method untuk handle user mengambil cucian
    private static void handleAmbilCucian() {
        String inputIdNota, laundryCondition = "";
        int idNota, indexNota = -1;
        
        //Meminta user untuk memasukkan ID nota yang akan diambil dan harus berupa angka//
        System.out.println("Masukkan ID nota yang akan diambil:");
        while(true){
            try{
                inputIdNota = input.nextLine();
                idNota = Integer.parseInt(inputIdNota);
                break;
            }catch(Exception e){
                System.out.println("ID nota berbentuk angka!");
            }
        }

        //*Apabila ID nota ada dalam list, maka laundrynya bisa diambil
        for(int i=0; i<notaList.size(); i++){
            if(notaList.get(i).getIdNota() == idNota){
                indexNota = i;
                break;
            }
        }

        if(indexNota != -1){
            if(notaList.get(indexNota).getIsReady()){
                laundryCondition = "berhasil diambil!";
                notaList.remove(indexNota);
            }else{
                laundryCondition = "gagal diambil!";
            }
        }else{
            laundryCondition = "tidak ditemukan!";
        }
        System.out.printf("Nota dengan ID %s %s", inputIdNota, laundryCondition).println();
    }

    //Method untuk handle ganti hari di sistem
    private static void handleNextDay() {
        cal.setTime(cal.getTime());
        cal.add(Calendar.DATE, 1);

        System.out.println("Dek Depe tidur hari ini... zzz...");
        for(int i=0; i < notaList.size(); i++){
            Nota n = notaList.get(i);
            
            n.setSisaHariPengerjaan(n.getSisaHariPengerjaan() - 1);

            if(n.getSisaHariPengerjaan() <= 0){
                n.setIsReady(true);
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!", n.getIdNota()).println();
            }
            notaList.set(i, n);
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    //Method untuk mencetak pilihan main menu
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