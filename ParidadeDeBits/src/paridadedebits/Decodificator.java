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
        byte[] bytes = new byte[10];
        Byte byteAux;
        for (int i = 0; i < (originalBytes.size() / 10); i++) {
            bytes = readBlock(originalBytes, i);
            byteAux = (byte) this.makeRowParity(bytes);
            
        }

    }

    private List<Byte> readFile() throws IOException {
        byte aux = 0;
        List<Byte> bytes = new ArrayList<>();
        aux = (byte) this.fileReader.read();
        while (aux != -1) {
            bytes.add(aux);
            aux = (byte) this.fileReader.read();
        }//Estou assumindo que ele não grava o -1.
        return bytes;
    }

    private byte[] readBlock(List<Byte> originalBytes, int i) {
        byte[] bytes = new byte[10];
        int blockId = i * 10;
        for (int j = 0; j < 10; j++) {
            bytes[j] = originalBytes.get(j + blockId);
        }
        return bytes;
    }
}
