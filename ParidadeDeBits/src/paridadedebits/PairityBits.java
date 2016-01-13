package paridadedebits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erick
 */
public abstract class PairityBits {

    protected final InputStream fileReader;
    protected OutputStream fileWriter;

    public PairityBits(String file) throws FileNotFoundException {
        this.fileReader = new FileInputStream(file);
    }

    /**
     * Pega os bytes do arquivo e trasnfere para uma lista de bytes.
     *
     * @return lista de bytes lida do arquivo.
     * @throws IOException
     */
    protected List<Byte> makeArrayBytes() throws IOException {
        List<Byte> fileBytes = new ArrayList<>();
        int aux;
        do {
            aux = fileReader.read();
            fileBytes.add((byte) aux);
        } while (aux != -1);
        fileBytes.remove(fileBytes.size() - 1);
        fileReader.close();
        return fileBytes;
    }

    //Método inutilizado?
    private byte[] getBlockOfBytes(List<Byte> bytes, int i) {//Pega blocos de 8 bytes.
        byte[] bytesArray = new byte[8];
        int blockId = i * 8;
        for (int k = 0; k < 8; k++) {
            bytesArray[k] = bytes.get(k + blockId);
        }
        return bytesArray;
    }

    /**
     * Faz a paridade por linha e retorna esse novo Byte. Caso o numero de bits
     * "1" seja par, retorna "0", caso contrário, "1".
     *
     * @param bytes Bloco de bytes.
     * @return Byte de paridade.
     */
    protected byte makeRowParity(byte[] bytes) {
        String rowByte = "";
        int rowCount = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte aByte = bytes[i];
            //Percorre cada bit de um byte do bloco.
            for (int j = 7; j >= 0; j--) {
                if (((int) (((byte) (aByte >>> j)) & 1)) == 1) {
                    rowCount++;
                }
            }
            //Verifica se o número de bits "1" é par.
            if (rowCount % 2 == 0) {
                rowByte += "0";
            } else {
                rowByte += "1";
            }
            rowCount = 0;
        }
        //Converte a String final da paridade das linhas para Byte.
        byte b = (byte) (Integer.parseInt(rowByte, 2));
        return b;
    }

    /**
     * Faz a paridade por coluna e retorna esse novo Byte. Caso o numero de bits
     * "1" seja par, retorna "0", caso contrário, "1".
     *
     * @param bytes Bloco de bytes.
     * @return Byte de paridade.
     */
    protected byte makeColParity(byte[] bytes) {
        String colByte = "";
        int colCount = 0;
        byte aByte;
        for (int i = 7; i >= 0; i--) {
            //Percorre cada bit de um byte do bloco.
            for (int j = 7; j >= 0; j--) {
                aByte = bytes[j];
                if (((int) (((byte) (aByte >>> i)) & 1)) == 1) {
                    colCount++;
                }
            }
            //Verifica se o número de bits "1" é par.
            if (colCount % 2 == 0) {
                colByte += "0";
            } else {
                colByte += "1";
            }
            colCount = 0;
        }
        //Converte a String final da paridade das colunas para Byte.
        byte b = (byte) (Integer.parseInt(colByte, 2));
        return b;
    }
}
