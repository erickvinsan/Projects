package paridadedebits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author erick
 */
public class ParidadeDeBits {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("Iniciando Codificacao...");
        System.out.println("Lendo Arquivo...");
        try (FileOutputStream f = new FileOutputStream("pacote.pck")) {
            for (int i = 1; i <= 16; i++) {
                f.write((byte) (i * Math.random()));
            }
            f.flush();
        }

        Codificator c = new Codificator("pacote.pck");
        c.makeCodification();
        //List<Byte> list = c.makeArrayBytes();
        try (FileInputStream fin = new FileInputStream("pacote.pck")) {
            //List<Byte> list = c.makeArrayBytes();
            byte aux = 0;
            System.out.println("\n" + "Arquivo completo: ");
            do {
                aux = (byte) fin.read();
                System.out.print(" " + aux);
            } while (aux != -1);
        }
//
//        String s = "01000001";
//        int i = Integer.parseInt(s, 2);
//        System.out.println((char) i);
//        byte b = (byte) (Integer.parseInt(s) & 0xFF);
//        System.out.println(b);
    }
}
