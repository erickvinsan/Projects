package paridadedebits;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author erick
 */
public class Codificator extends PairityBits {

    private OutputStream writer;

    public Codificator(String fileName) throws FileNotFoundException{
        super(fileName);
    }

    private byte[] getBlockOfBytes(List<Byte> bytes, int i) {//Pega blocos de 8 bytes.
        byte[] bytesArray = new byte[8];
        int blockId = i * 8;
        for (int k = 0; k <  8; k++) {
            bytesArray[k] = bytes.get(k + blockId);
        }
        return bytesArray;
    }

    public void makeCodification() throws IOException {
        List<Byte> listOfBytes = makeArrayBytes();
        System.out.println("Quantidade de bytes: " + (listOfBytes.size()));
        writer = new FileOutputStream("pacote.pck");
        byte[] bytes;
        System.out.println("");
        Byte row;
        Byte col;
        int mult = listOfBytes.size() / 8;
        System.out.println("Grupos de 8 bytes: " + mult + "\n");
        byte[] finalBytes = new byte[10];
        byte auxConst = 0;
        
        if ((listOfBytes.size() % 8) != 0) {//Número de bytes != múltiplo de 8. Preenche com 0's.
            int rest = listOfBytes.size() - (mult * 8);
            System.out.println("Resto = " + rest + "\n");
            for (int i = 0; i < (8 - rest); i++) {//Preenche com 0's
                listOfBytes.add(auxConst);
            }
            mult++;
        }
        
        for (int i = 0; i < mult; i++) {// Codifica a Parte inteira da quantidade de bytes
            bytes = getBlockOfBytes(listOfBytes, i);
            System.out.println("Bloco = " + i);
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + bytes[j]);
            }
            System.out.println("");
            row = this.makeRowParity(bytes);
            col = this.makeColParity(bytes);
            finalBytes[0] = col;
            finalBytes[1] = row;
            System.arraycopy(bytes, 0, finalBytes, 2, 8);
            writer.write(finalBytes);
            writer.flush();
        }
        
        writer.close();
    }

    
}
