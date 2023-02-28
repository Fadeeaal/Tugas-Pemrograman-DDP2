package assignments.assignment1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        //membuat boolean, dan variabel awal untuk nomor HP, nama pelanggan, dan berat laundry
        boolean terminateProgram = false; boolean isInteger = false;
        String nomorHandphone = ""; String namaPelanggan = ""; String paketLaundry; int beratLaundry = 0;
    
        //Membuat main program
        while (!terminateProgram){
            boolean isNumber = false;
            printMenu();
    
            //Meminta user untuk memasukkan pilihan user
            System.out.print("Pilihan : ");
            String pilihan = input.next();
            System.out.println("================================");
    
            switch (pilihan){
                //Apabila user memilih untuk membuat ID saja
                case "1":
                //Memasukkan nama user
                System.out.println("Masukkan nama Anda: ");
                namaPelanggan = input.next();
                input.nextLine();
    
                //Memasukkan nomor HP user
                System.out.println("Masukkan nomor handphone Anda: ");
                while (!isNumber) {              
                    nomorHandphone = input.next();
                    //Nomor handphone akan diperiksa isinya
                    if (nomorHandphone.matches("[0-9]+")) { //Apabila isi nomor handphone angka semua
                        isNumber = true;
                    } else { //Apabila isi nomor handphone tidak angka semua (ex : ada huruf ditengah)
                        System.out.println("Nomor hp hanya menerima digit");
                    }
                }
                System.out.println(generateId(namaPelanggan, nomorHandphone)); //Masuk ke method untuk membuat ID
                break;
    
                //Apabila user memilih untuk membuat nota
                case "2":
                //Memasukkan nama user
                System.out.println("Masukkan nama Anda: ");
                namaPelanggan = input.next();
                input.nextLine();
    
                //Memasukkan nomor HP user
                System.out.println("Masukkan nomor handphone Anda: ");
                while (!isNumber) {              
                    nomorHandphone = input.next();
                    //Nomor handphone akan diperiksa isinya
                    if (nomorHandphone.matches("[0-9]+")) { //Apabila isi nomor handphone angka semua
                        isNumber = true;
                    } else { //Apabila isi nomor handphone tidak angka semua (ex : ada huruf ditengah)
                        System.out.println("Nomor hp hanya menerima digit");
                    }
                }                
                
                //User memasukkan tanggal terima laundry dari pelanggan
                System.out.println("Masukkan tanggal terima: ");
                String tanggalTerima = input.next();
                input.nextLine();
    
                /*User diminta untuk memasukkan jenis paket laundry dan akan diulang terus hingga input sesuai 
                dengan jenis laundry yang ada*/ 
                do {
                    System.out.println("Masukkan paket laundry:");
                    paketLaundry = input.next().toLowerCase();
                    switch (paketLaundry) {
                        //Apabila user memasukkan input sesuai paket laundry yang tersedia
                        case "express": case "fast": case "reguler":
                        break;
                        
                        //Apabila user ingin mengetahui jenis paket laundry yang tersedia
                        case "?":
                        showPaket();
                        break;
    
                        //Jika user tidak memasukkan jenis paket laundry sesuai yang ada
                        default:
                        System.out.println("Paket " + paketLaundry + " tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]");
                        break;
                    }
                } while (!paketLaundry.equals("express") && !paketLaundry.equals("fast") && !paketLaundry.equals("reguler"));
                
                /*User diminta untuk memasukkan berat cucian laundry dan akan diulang terus hingga user memasukkan berat cucian
                 sesuai dengan data yang diminta (integer)*/
                System.out.println("Masukkan berat cucian anda [kg]: ");
                while (!isInteger) {
                    try {
                        //Program akan memeriksa apakah input user benar
                        beratLaundry = input.nextInt();                
                        //Jika input sudah benar
                        if (beratLaundry == 1) { //Jika user input berat laundry sejumlah 1 kg
                            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                            beratLaundry = 2;
                            isInteger = true;
                            break;
                            }   
                        else if (beratLaundry > 1){
                            isInteger = true;
                            break;
                        }  
                        else{
                            System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                            continue;
                        }        
                    } catch (Exception e) {
                        //Jika terjadi exception selain NumberFormatException
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                        input.nextLine();
                    }
                }

                //Masuk ke method generateNota untuk membuat nota user
                System.out.println("Nota Laundry");
                System.out.println(generateNota(generateId(namaPelanggan, nomorHandphone), paketLaundry, beratLaundry, tanggalTerima));
                break;
    
                //Apabila user memilih untuk keluar dari program
                case "0":
                System.out.println("Terima kasih telah menggunakan NotaGenerator!");
                terminateProgram = true;
                break;

                //Apabila user input pilihan selain yang tersedia
                default:
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                break;
            }
        }
        input.close();
    }

    //Method untuk print pilihan yang tersedia di main menu
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    //Method untuk menunjukkan (print) pilihan paket yang tersedia
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    //Method untuk membuat ID user
    public static String generateId(String nama, String nomorHP){
        //Membuat variabel awal untuk nama pelanggan, ID pelanggan, dan counter untuk menghitung check sum
        String namaPelanggan = "", idPelanggan = "";
        int checkSum = 0;

        for (int i = 0; i < nama.length(); i++){
            if (nama.charAt(i) == ' '){
                break;
            } else{
                namaPelanggan += nama.charAt(i);
            }
        }

        idPelanggan = namaPelanggan.toUpperCase() + "-" + nomorHP;
        for (int j = 0; j < idPelanggan.length(); j++){
            if (Character.isLetter(idPelanggan.charAt(j))){
                int hurufAngka = Character.getNumericValue(idPelanggan.charAt(j)) - 9;
                checkSum += hurufAngka;
            }
            else if (Character.isDigit(idPelanggan.charAt(j))){
                int angkaAngka = Character.getNumericValue(idPelanggan.charAt(j));
                checkSum += angkaAngka;
            }
            else{
                checkSum += 7;
            }
        }
        String kodeAngka = Integer.toString(checkSum);
        if (checkSum < 10){
            kodeAngka = "0" + kodeAngka;
            return (idPelanggan + "-" + kodeAngka);
        }
        else{
            if (kodeAngka.length() > 2){
                return idPelanggan + "-" + kodeAngka.substring(kodeAngka.length()-2, kodeAngka.length());
            }
            else{
                return idPelanggan + "-" + kodeAngka;
            }
        }
    }

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        int hargaPaket = 0;
        if (paket.equalsIgnoreCase("express")){
            hargaPaket = 12000;
        }
        else if (paket.equalsIgnoreCase("fast")){
            hargaPaket = 10000;
        }
        else if (paket.equalsIgnoreCase("reguler")){
            hargaPaket = 7000;
        }
        return
        "ID    : " + id + "\n" + 
        "Paket : " + paket + "\n" +
        "Harga : \n" +
        String.format("%d kg x %d = %d\n", berat, hargaPaket, berat * hargaPaket) +
        "Tanggal Terima  : " + tanggalTerima + "\n" +
        "Tanggal Selesai : " + generateDate(paket, tanggalTerima);
    }

    public static String generateDate(String jenisPaket, String tanggal){
        int daysToAdd = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(tanggal, formatter);
        
        if (jenisPaket.equalsIgnoreCase("Express")){
            daysToAdd = 1;
        }
        else if (jenisPaket.equalsIgnoreCase("Fast")){
            daysToAdd = 2;
        }
        else if (jenisPaket.equalsIgnoreCase("Reguler")){
            daysToAdd = 3;
        }
        LocalDate newDate = date.plusDays(daysToAdd);
        String newDateStr = newDate.format(formatter);
        return newDateStr;
    }
}
