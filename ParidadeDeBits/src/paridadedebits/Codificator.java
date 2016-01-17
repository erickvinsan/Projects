package paridadedebits;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

/**
 *
 * @author erick
 */
public class Codificator extends PairityBits {

    private OutputStream writer;
    private final int arg, bt, bit;

    public Codificator(String fileName, int arg, int bt, int bit) throws FileNotFoundException {
        super(fileName);
        this.arg = arg;
        this.bt = bt;
        this.bit = bit;
    }

    private byte[] getBlockOfBytes(List<Byte> bytes, int i) {//Pega blocos de 8 bytes.
        byte[] bytesArray = new byte[8];
        int blockId = i * 8;
        for (int k = 0; k < 8; k++) {
            bytesArray[k] = bytes.get(k + blockId);
        }
        return bytesArray;
    }

    public void makeCodification() throws IOException {
        List<Byte> listOfBytes = makeArrayBytes();
        System.out.println("Quantidade de bytes: " + (listOfBytes.size()));
        writer = new FileOutputStream(this.nome);
        byte[] bytes;
        System.out.println("");
        Byte row;
        Byte col;
        int mult = listOfBytes.size() / 8;
        System.out.println("Grupos de 8 bytes: " + mult + "\n");
        byte[] finalBytes = new byte[10];
        byte auxConst = 0;
        int rest = listOfBytes.size() - (mult * 8);
        if ((listOfBytes.size() % 8) != 0) {//Número de bytes != múltiplo de 8. Preenche com 0's.            
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
            if (arg == 1) {
                makeCorruption(finalBytes, this.bt, this.bit);//Possibilidade ou não de criar um bit corrompido.
            }

            if (rest != 0 && (i == (mult - 1))) {
                byte[] finalBytesWithoutFill = new byte[2 + rest];
                System.arraycopy(finalBytes, 0, finalBytesWithoutFill, 0, (2 + rest));
                writer.write(finalBytesWithoutFill);
            } else {
                writer.write(finalBytes);
            }
            writer.flush();
        }

        writer.close();
    }

    private void makeCorruption(byte[] finalBytes, int bt, int bitError) {
        Random r = new Random();
        if (r.nextInt(100) > 50) {
            String finalByte = "";
            byte aux = finalBytes[bt];
            for (int i = 7; i >= 0; i--) {
                if (i == bitError) {
                    if ((((int) aux >>> i) & 1) == 0) {
                        finalByte += 1;
                    } else {
                        finalByte += 0;
                    }
                } else {
                    finalByte += (int) ((aux >>> i) & 1);
                }
            }
            finalBytes[bt] = (byte) (Integer.parseInt(finalByte, 2));
        }
    }

}
