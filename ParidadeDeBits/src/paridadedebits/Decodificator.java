package paridadedebits;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erick
 */
public class Decodificator extends PairityBits {

    private final List<Byte> originalBytes;
    private final String fileOut;

    public Decodificator(String fileIn, String fileOut) throws FileNotFoundException, IOException {
        super(fileIn);
        this.fileOut = fileOut;
        originalBytes = readFile();
    }

    public void makeDecodification() throws IOException {
        this.fileWriter = new FileOutputStream(this.fileOut);
        int errSum;
        List<Byte> finalBytes = new ArrayList<>();
        List<Byte> bytes;
        byte[] dataBytes;
        List<Integer> colErrors, rowErrors;
        byte rowParityCod, colParityCod, rowParityDecod, colParityDecod;

        int blocosCompletos = (originalBytes.size() / 10);
        int qtdZeros = 0;

        if ((originalBytes.size() % 10) != 0) {
            qtdZeros = 10 - (originalBytes.size() - (blocosCompletos * 10));
            blocosCompletos++;
            for (int i = 0; i < qtdZeros; i++) {
                originalBytes.add((byte) 0);
            }
        }

        for (int i = 0; i < blocosCompletos; i++) {
            System.out.println("");
            bytes = readBlock(originalBytes, i);//Lê bloco de 10 bytes           

            colParityCod = bytes.remove(0);//9 elementos. (Byte de paridade das colunas)            
            rowParityCod = bytes.remove(0);//8 elementos. (Byte de paridade das linhas)

            dataBytes = getArrayDataBytes(bytes);//Tranforma em array de bytes.

            rowParityDecod = (byte) this.makeRowParity(dataBytes);
            colParityDecod = (byte) this.makeColParity(dataBytes);

            rowErrors = compareParityBytes(rowParityCod, rowParityDecod);
            colErrors = compareParityBytes(colParityCod, colParityDecod);

            errSum = colErrors.get(0) + rowErrors.get(0);
//            errSum = 3;
            switch (errSum) {
                case 0://Nenhum Erro encontrado
                    System.out.println("Bloco " + i + " correto!");
                    break;
                case 2://1 erro encontrado = corrigir o erro.
                    System.out.println("Bloco " + i + " incorreto!");
                    System.out.println("Erro Corrigível encontrado!");
                    String auxByte = "";
                    byte newByte = dataBytes[7 - rowErrors.get(1)];
                    System.out.println("Byte incorreto na posição: Byte: " + (7 - rowErrors.get(1)) + ", Bit: " + colErrors.get(1));
                    for (int j = 7; j >= 0; j--) {
                        if (j != colErrors.get(1)) {
                            auxByte += (int) ((newByte >>> j) & 1);
                        } else {
                            auxByte += ((int) ((newByte >>> j) & 1) == 0) ? 1 : 0;
                        }
                    }
                    dataBytes[7 - rowErrors.get(1)] = (byte) (Integer.parseInt(auxByte, 2));
                    break;
                default:// Mais de 1 erro econtrado.
                    System.out.println("Mais de 1 erro encontrado!");//TODO mostrar erros.
                    System.exit(-1);
            }
            byte[] newByte = new byte[8 - qtdZeros];
            if (qtdZeros != 0 && (i == (blocosCompletos - 1))) {
                System.arraycopy(dataBytes, 0, newByte, 0, newByte.length);
                copyListToArray(finalBytes, newByte);
            } else {
                copyListToArray(finalBytes, dataBytes);
            }
        }
        this.fileWriter.write(getArrayDataBytes(finalBytes));
    }

    private List<Byte> copyListToArray(List<Byte> list, byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            list.add(bytes[i]);
        }
        return list;
    }

    private byte[] getArrayDataBytes(List<Byte> listBytes) {
        byte[] dataBytes = new byte[listBytes.size()];
        for (int i = 0; i < dataBytes.length; i++) {
            dataBytes[i] = listBytes.get(i);
        }
        return dataBytes;
    }

    private List<Byte> readFile() throws IOException {//Lê o arquivo e armazena os bytes lidos.
        byte aux;
        List<Byte> bytes = new ArrayList<>();
        aux = (byte) this.fileReader.read();
        while (aux != -1) {
            bytes.add(aux);
            aux = (byte) this.fileReader.read();
        }//Estou assumindo que ele não grava o -1.
        return bytes;
    }

    private List<Byte> readBlock(List<Byte> originalBytes, int i) {//Retorna um bloco de 10 bytes.
        List<Byte> bytes = new ArrayList<>();
        int blockId = i * 10;
        for (int j = 0; j < 10; j++) {
            bytes.add(originalBytes.get(j + blockId));
        }
        return bytes;//1ª = byte coluna; 2ª = byte linha; [3,10] = bytes de conteúdo.
    }

    private List<Integer> compareParityBytes(byte bt1, byte bt2) {//List[count, List[Posições]]
        int count = 0;
        List<Integer> errors = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (((int) (bt1 >>> i) & 1) != ((int) (bt2 >>> i) & 1)) {
                count++;
                errors.add(i);
            }
        }
        errors.add(0, count);
        return errors; //Retorna Lista encabeçada pela quantidade de erros e depois as posições dos erros.
    }
}
