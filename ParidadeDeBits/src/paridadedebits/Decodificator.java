package paridadedebits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

    public void makeDecodification() {
        List<Byte> bytes;
        byte[] dataBytes;
        Byte rowParityCod, colParityCod, rowParityDecod, colParityDecod;
        for (int i = 0; i < (originalBytes.size() / 10); i++) {
            bytes = readBlock(originalBytes, i);
            colParityCod = bytes.remove(0);//9 elementos.
            rowParityCod = bytes.remove(0);//8 elementos.
            dataBytes = getArrayDataBytes(bytes);
            rowParityDecod = (byte) this.makeRowParity(dataBytes);
            colParityDecod = (byte) this.makeColParity(dataBytes);
            compareParityBytes(colParityCod, rowParityCod, rowParityDecod, colParityDecod);
        }

    }
 
    private byte[] getArrayDataBytes(List<Byte> listBytes) {
        byte[] dataBytes = new byte[8];
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

    private int compareParityBytes(Byte colParityCod, Byte rowParityCod, Byte rowParityDecod, Byte colParityDecod) {
        byte col, row;
        for (int i = 0; i < 8; i++) {//compara os bytes coluna.
            if(((byte) (colParityCod >>> i)) != ((byte) (colParityDecod >>> i))){
                System.out.println("Bit incorreto detectado!");
                
            }
        }
        return 0;
    }
}
