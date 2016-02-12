package Main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.script.ScriptEngineManager;

/**
 *
 * @author erick
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner leitor = new Scanner(System.in);
        
        System.out.println("Modo Debug?"
                + "\n1 - SIM"
                + "\n0 - NAO");
        int debug = leitor.nextInt();
        int n = 0, e = 0, d = 0;
        String arqEntrada = null, arqSaida = null;

        System.out.println("---------------------------------------------------");
        if (debug == 1) {
            System.out.println("Entre com o nome do arquivo que deseja cifrar: debug.cif");
            arqEntrada = "debug.cif";//leitor.nextLine();
            try (FileOutputStream fOut = new FileOutputStream(arqEntrada)) {
                System.out.println("Entre com o nome do arquivo para receber o conteudo decifrado: debug.decif");
                arqSaida = "debug.decif";

                leitor.nextLine();
                System.out.println("Entre com a mensagem do novo arquivo:");
                String textoEntrada = leitor.nextLine();
                fOut.write(textoEntrada.getBytes());
            }

            System.out.println("Entre com o valor de N: 1073");
            n = 2881;

            System.out.println("Entre com o valor de E: 71");
            e = 1625;

            System.out.println("Entre com o valor de D: 1079");
            d = 29;

        } else if (debug == 0) {
            System.out.println(
                    "Entre com o nome do arquivo que deseja cifrar:");
            leitor.nextLine();
            arqEntrada = leitor.nextLine();

            System.out.println(
                    "Entre com o nome do arquivo para receber o conteudo decifrado:");
            arqSaida = leitor.nextLine();

            System.out.println(
                    "Deseja adicionar uma mensagem ao arquivo?"
                    + "\n1- SIM (Arquivo vazio => usuário preenche.)"
                    + "\n2- NAO (Arquivo já preenchido.)");
            int opcao = leitor.nextInt();
            switch (opcao) {
                case 1:
                    FileOutputStream fOut = new FileOutputStream(arqEntrada);
                    System.out.println("Entre com a mensagem do novo arquivo:");
                    leitor.nextLine();
                    String textoEntrada = leitor.nextLine();
                    fOut.write(textoEntrada.getBytes());
                    fOut.close();
                    break;
                case 2:
                    leitor.nextLine();
                    break;
            }

            System.out.println("Entre com o valor de N: ");
            n = leitor.nextInt();

            System.out.println(
                    "Entre com o valor de E:");
            e = leitor.nextInt();

            System.out.println("Entre com o valor de D:");
            d = leitor.nextInt();
        }

        Controlador c = new Controlador(arqEntrada, arqSaida, n, e, d);
        c.cifrarArquivo();
        c.decifrarArquivo();
        System.out.println("---------------------------------------------------");

    }
}
