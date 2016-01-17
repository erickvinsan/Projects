package paridadedebits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author erick
 */
public class ParidadeDeBits {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("Iniciando Codificacao...");
        System.out.println("Lendo Arquivo...");
        System.out.println("Entre com o nome do arquivo de entrada:");
        Scanner leitor = new Scanner(System.in);
        String nome = leitor.nextLine();
        try (FileOutputStream f = new FileOutputStream(nome)) {
            for (int i = 1; i <= 33; i++) {
                f.write((byte) (i % 45));
            }
            f.flush();
        }

        Codificator c = new Codificator(nome, 1, 2, 1);
        c.makeCodification();

        try (FileInputStream fin = new FileInputStream(nome)) {//Mostrar Arquivo codificado com os bits de Paridade.
            byte aux;
            int count = 0;
            System.out.println("\n" + "Quadro Codificado: ");
            System.out.println("-------------------------------------------------");
            do {
                aux = (byte) fin.read();
                count++;
                for (int i = 7; i >= 0; i--) {
                    System.out.print((int) ((aux >>> i) & 1));
                }
                System.out.print(" ");
                if (count % 10 == 0) {
                    System.out.println("");
                }
            } while (aux != -1);
        }
        System.out.println("\n-------------------------------------------------");

        System.out.println("Entre com o nome do arquivo de saída:");
        String nomeSaida = leitor.nextLine();
        Decodificator d = new Decodificator(nome, nomeSaida);
        d.makeDecodification();

        try (FileInputStream fin = new FileInputStream(nomeSaida)) {//Mostra o Pacote decodificado.
            //List<Byte> list = c.makeArrayBytes();
            byte aux = 0;
            int count = 0;
            System.out.println("\n" + "Quadro Decodificado: ");
            System.out.println("-------------------------------------------------");
            do {
                aux = (byte) fin.read();
                count++;
                for (int i = 7; i >= 0; i--) {
                    System.out.print((int) ((aux >>> i) & 1));
                }
                System.out.print(" ");
                if (count % 8 == 0) {
                    System.out.println("");
                }
            } while (aux != -1);
            System.out.println("\n-------------------------------------------------");
        }
    }
}
