package Main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erick
 */
public class Controlador {

    private final Scanner leitorTeclado;
    private final String inFile;
    private final String outFile;
    private DataInputStream in;
    private DataOutputStream out;
    private final Cifrador cifrador;
    private final Decifrador decifrador;

    public Controlador(String fileIn, String fileOut, int n, int e, int d) {
        this.leitorTeclado = new Scanner(System.in);
        this.inFile = fileIn;
        this.outFile = fileOut;
        this.cifrador = new Cifrador(n, e);
        this.decifrador = new Decifrador(n, d);
    }

    public void cifrarArquivo() throws IOException {
        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("Inicio codificacao...");
        this.in = new DataInputStream(new BufferedInputStream(new FileInputStream(this.inFile)));
        this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(this.outFile)));

        List<Byte> textoPlano = readBytesInFile(this.inFile);
        System.out.println("\n Bytes originais: ");
        System.out.println(textoPlano);

        System.out.println("\nQuantidade de bytes lidos do arquivo texto: " + textoPlano.size());
        System.out.println("\nTexto Plano: " + converterListaDeBytesEmTexto(textoPlano));
        int byteCifrado;
        System.out.println("Bytes Cifrados: ");
        for (int i = 0; i < textoPlano.size(); i++) {
            byteCifrado = (int) cifrador.binExp(textoPlano.get(i), cifrador.getN(), cifrador.getE());
            System.out.print(byteCifrado + " ");
            this.out.writeShort(byteCifrado);
        }
        System.out.println("\nFim codificacao...");
        System.out.println("\n---------------------------------------------------------------------------");
        in.close();
        out.close();
    }

    public void decifrarArquivo() throws IOException {
        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("Iniciando Decodificao...");
        this.in = new DataInputStream(new BufferedInputStream(new FileInputStream(this.outFile)));
        this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("saidaDecifrada.decif")));

        List<Short> textoCifrado = readShortsInFile(this.outFile);
        System.out.println("\nQuantidade de Shorts do Arquivo Cifrado: " + textoCifrado.size());
        System.out.println("\nShorts Texto Cifrado: ");
        for (int i = 0; i < textoCifrado.size(); i++) {
            System.out.print(textoCifrado.get(i) + " ");
        }
        byte[] textoDecodificado = new byte[textoCifrado.size()];
        byte aux;
        System.out.println("\nShorts decifrados: ");
        for (int i = 0; i < textoCifrado.size(); i++) {
            aux = (byte) (decifrador.binExp(textoCifrado.get(i), decifrador.getN(), decifrador.getD()));
            System.out.print((byte) aux + " ");
            textoDecodificado[i] = aux;
            this.out.writeByte(textoDecodificado[i]);
        }
        System.out.println("\nFim decodificacao...");
        System.out.println("\n---------------------------------------------------------------------------");

        in.close();
        out.close();
        List<Byte> textoDecifrado = readBytesInFile("saidaDecifrada.decif");
        System.out.println("Texto Plano Decifrado: " + converterListaDeBytesEmTexto(textoDecifrado));

    }

    private String converteListaDeShortsEmTexto(List<Short> textoEntrada) throws IOException {
        String texto = "";
        List<Short> textoShorts = textoEntrada;

        for (int i = 0; i < textoShorts.size(); i++) {
            texto = texto + ((char) (textoShorts.get(i).intValue()));
        }
        return texto;
    }

    private String converterListaDeBytesEmTexto(List<Byte> textoEntrada) throws IOException {
        String texto = "";
        List<Byte> textoByte = textoEntrada;

        for (int i = 0; i < textoByte.size(); i++) {
            texto = texto + ((char) (textoByte.get(i).intValue()));
        }
        return texto;
    }

    private List<Byte> readBytesInFile(String nomeArquivo) throws IOException {//Lê o arquivo e armazena os bytes lidos.
        DataInputStream inByte = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArquivo)));
        List<Byte> bytes = new ArrayList<>();
        try {
            System.out.println("- INICIO DE ARQUIVO -");
            byte aux;
            while (true) {
                aux = inByte.readByte();
                bytes.add(aux);
            }
        } catch (EOFException e) {
            System.out.println("- FIM DE ARQUIVO -");
        }
        return bytes;
    }

    private List<Short> readShortsInFile(String nomeArquivo) throws IOException {//Lê o arquivo e armazena os bytes lidos.
        DataInputStream inShort = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArquivo)));
        List<Short> shorts = new ArrayList<>();
        try {
            System.out.println("- INICIO DE ARQUIVO -");
            short aux;
            while (true) {
                aux = inShort.readShort();
                shorts.add(aux);
            }
        } catch (EOFException e) {
            System.out.println("- FIM DE ARQUIVO -");
        }
        return shorts;
    }

}
