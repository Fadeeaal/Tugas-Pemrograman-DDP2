package assignments.assignment1;

import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        boolean runningProgram = false;
        String nomorHandphone = ""; String namaPelanggan = "";
        
        while (!runningProgram){
            boolean isNumber = false;
            printMenu();

            System.out.print("Pilihan : ");
            String pilihan = input.nextLine();
            System.out.println("================================");
            switch (pilihan){
                case "1":
                //Generate ID
                System.out.println("Masukkan nama Anda: ");
                namaPelanggan = input.nextLine();
                
                System.out.println("Masukkan nomor handphone Anda: ");
                while (!isNumber){
                    if (input.hasNextLong()){
                        nomorHandphone = Long.toString(input.nextLong());
                        isNumber = true;
                    }
                    else{
                        System.out.println("Nomor hp hanya menerima digit");
                        input.nextLine();
                    }
                }
                System.out.println(generateId(namaPelanggan, nomorHandphone));
                break;

                case "2":
                //Generate Nota
                String paketLaundry;
                System.out.println("Masukkan nama Anda: ");
                namaPelanggan = input.next();
                input.nextLine();

                System.out.println("Masukkan nomor handphone Anda: ");
                while (!isNumber) {              
                    if(input.hasNextLong()) {
                        nomorHandphone = Long.toString(input.nextLong());
                        isNumber = true;
                    } 
                    else {
                        System.out.println("Nomor hp hanya menerima digit");
                        input.nextLine();
                    }
                  }

                System.out.println("Masukkan tanggal terima: ");
                String tanggalTerima = input.next();
                input.nextLine();

                do {
                    System.out.println("Masukkan paket laundry:");
                    paketLaundry = input.nextLine().toLowerCase();
                    switch (paketLaundry) {
                        case "express": case "fast": case "reguler":
                        break;
                        
                        case "?":
                        showPaket();
                        break;

                        default:
                        System.out.println("Paket " + paketLaundry + " tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]");
                        break;
                    }
                } while (!paketLaundry.equals("express") && !paketLaundry.equals("fast") && !paketLaundry.equals("reguler"));
                
                System.out.println("Masukkan berat cucian anda [kg]: ");
                int beratCucian = input.nextInt();
                input.nextLine();
                if (beratCucian < 2){
                    System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                    beratCucian = 2;
                }
                System.out.println(generateNota(generateId(namaPelanggan, nomorHandphone), paketLaundry, beratCucian, tanggalTerima));
                break;

                case "0":
                System.out.print("Terima kasih telah menggunakan NotaGenerator!");
                runningProgram = true;
                break;

                default:
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                break;
            }
        }
    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP){
        String namaPelanggan = "", idPelanggan;
        int checkSum = 0;

        for (int i = 0; i < nama.length(); i++){
            if (nama.charAt(i) == ' '){
                break;
            } else{
                namaPelanggan += nama.charAt(i);
            }
        }

        if (nomorHP.substring(0, 1).equals("0")){
            nomorHP = "0" + nomorHP;
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
                return (idPelanggan + "-" + kodeAngka.substring(kodeAngka.length()-2, kodeAngka.length()));
            }
            else{
                return (idPelanggan + "-" + kodeAngka);
            }
        }
    }

    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        // TODO: Implement generate nota sesuai soal.
        return null;
    }
}
