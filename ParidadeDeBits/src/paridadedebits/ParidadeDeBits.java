package paridadedebits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                f.write((byte) (i));
            }
            f.flush();
        }

        Codificator c = new Codificator("pacote.pck");
        c.makeCodification();
        
        try (FileInputStream fin = new FileInputStream("pacote.pck")) {//Mostrar Arquivo codificado com os bits de Paridade.
            //List<Byte> list = c.makeArrayBytes();
            byte aux = 0;
            System.out.println("\n" + "Quadro Codificado: ");
            do {
                aux = (byte) fin.read();
                for (int i = 7; i >= 0; i--) {
                    System.out.print((int)((aux >>> i) & 1));                    
                }
                System.out.print(" ");
            } while (aux != -1);
        }
        
        Decodificator d = new Decodificator("pacote.pck");
        d.makeDecodification();
        
        try (FileInputStream fin = new FileInputStream("pacote.pck")) {//Mostra o Pacote decodificado.
            //List<Byte> list = c.makeArrayBytes();
            byte aux = 0;
            System.out.println("\n" + "Quadro Decodificado: ");
            do {
                aux = (byte) fin.read();
                for (int i = 7; i >= 0; i--) {
                    System.out.print((int)((aux >>> i) & 1));                    
                }
                System.out.print(" ");
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
