package paridadedebits;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author erick
 */
public class Decodificator extends PairityBits {

    private final List<Byte> originalBytes;

    public Decodificator(String file) throws FileNotFoundException, IOException {
        super(file);
        originalBytes = readFile();
    }

    public void makeDecodification() throws IOException {
        int errSum;
        List<Byte> finalBytes = new ArrayList<>();
        List<Byte> bytes;
        byte[] dataBytes;
        List<Integer> colErrors, rowErrors;
        Byte rowParityCod, colParityCod, rowParityDecod, colParityDecod;

        this.fileWriter = new FileOutputStream("pacote.pck");

        for (int i = 0; i < (originalBytes.size() / 10); i++) {

            bytes = readBlock(originalBytes, i);//Lê bloco de 10 bytes

            colParityCod = bytes.remove(0);//9 elementos. (Byte de paridade das colunas)
            rowParityCod = bytes.remove(0);//8 elementos. (Byte de paridade das linhas)

            dataBytes = getArrayDataBytes(bytes);//Tranforma em array de bytes.

            rowParityDecod = (byte) this.makeRowParity(dataBytes);
            colParityDecod = (byte) this.makeColParity(dataBytes);

            colErrors = compareParityBytes(colParityCod, colParityDecod);
            rowErrors = compareParityBytes(rowParityCod, rowParityCod);
            errSum = colErrors.get(0) + rowErrors.get(0);
            switch (errSum) {
                case 0://Nenhum Erro encontrado
                    System.out.println("Quadro correto!");
                    break;
                case 2://1 erro encontrado = corrigir o erro.
                    String auxByte = "";
                    byte newByte = dataBytes[rowErrors.get(1)];
                    for (int j = 0; j < 8; j++) {
                        if (j != colErrors.get(1)) {
                            auxByte += (newByte >>> j);
                        } else {
                            auxByte += ((newByte >>> j) == 0) ? 1 : 0;
                        }
                    }
                    dataBytes[rowErrors.get(1)] = (byte) (Integer.parseInt(auxByte) & 0xFF);
                    break;
                default:// Mais de 1 erro econtrado.
                    System.out.println("Mais de 1 erro encontrado!");//TODO mostrar erros.
                    break;
            }
            copyListToArray(finalBytes, dataBytes);
        }
        this.fileWriter.write(getArrayDataBytes(finalBytes));
    }

    private List<Byte> copyListToArray(List<Byte> list, byte[] bytes) {
        //Arrays.asList(bytes); REFACTOR

        for (int i = 0; i < bytes.length; i++) {
            list.add(bytes[i]);
        }
        return list;
    }

    private byte[] getArrayDataBytes(List<Byte> listBytes) {
        //return listBytes.toArray(); REFACTOR

        byte[] dataBytes = new byte[listBytes.size()];
        for (int i = 0; i < dataBytes.length; i++) {
            dataBytes[i] = listBytes.get(i);
        }
        return dataBytes;
    }

    /**
     * Lê o arquivo e armazena os bytes lidos.
     *
     * @return Bytes lidos.
     * @throws IOException
     */
    private List<Byte> readFile() throws IOException {
        //MakeArrayBytes ==  readFile? REFACTOR
        byte aux;
        List<Byte> bytes = new ArrayList<>();
        aux = (byte) super.fileReader.read();
        while (aux != -1) {
            bytes.add(aux);
            aux = (byte) super.fileReader.read();
        }
        return bytes;
    }

    /**
     *
     * @param originalBytes
     * @param i
     * @return
     */
    private List<Byte> readBlock(List<Byte> originalBytes, int i) {//Retorna um bloco de 10 bytes.
        List<Byte> bytes = new ArrayList<>();
        int blockId = i * 10;
        for (int j = 0; j < 10; j++) {
            bytes.add(originalBytes.get(j + blockId));
        }
        return bytes;//1ª = byte coluna; 2ª = byte linha; [3,10] = bytes de conteúdo.
    }

    /**
     * Gera uma lista encabeçada pela quantidade de erros e depois as posições
     * dos erros.
     *
     * @param bt1 Primeiro byte de paridade.
     * @param bt2 Segundo byte de paridade.
     * @return lista com erros e posições dos mesmos.
     */
    private List<Integer> compareParityBytes(Byte bt1, Byte bt2) {//List[count, List[Posições]]
        int count = 0;
        List<Integer> errors = new ArrayList<>();
        for (int i = 0; i < 8; i++) {//compara os bytes coluna.
            if (((int) (bt1 >>> i)) != ((int) (bt2 >>> i))) {
                System.out.println("Bit incorreto detectado na posição:" + i);
                count++;
                errors.add(i);
            }
        }
        errors.add(0, count);
        return errors;
    }
}
