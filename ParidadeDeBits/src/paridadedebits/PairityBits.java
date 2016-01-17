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
    protected String nome;

    public PairityBits(String file) throws FileNotFoundException {
        this.nome = file;
        this.fileReader = new FileInputStream(file);
    }

    protected List<Byte> makeArrayBytes() throws IOException {//Pega os bytes do arquivo e trsnfere para uma lista de bytes
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

    private byte[] getBlockOfBytes(List<Byte> bytes, int i) {//Pega blocos de 8 bytes.
        byte[] bytesArray = new byte[8];
        int blockId = i * 8;
        for (int k = 0; k < 8; k++) {
            bytesArray[k] = bytes.get(k + blockId);
        }
        return bytesArray;
    }

    protected byte makeRowParity(byte[] bytes) {//Faz a paridade por linha e retorna esse novo Byte.
        String rowByte = "";
        int rowCount = 0;
        for (int i = 0; i < bytes.length; i++) {//Percorre Cada byte do bloco
            byte aByte = bytes[i];
            for (int j = 7; j >= 0; j--) {//Percorre cada bit de um byte do bloco
                if (((int) (((byte) (aByte >>> j)) & 1)) == 1) {
                    rowCount++;
                }
            }
            if (rowCount % 2 == 0) {//verifica se o número de bits "1" é par ou não.
                rowByte += "0";
            } else {
                rowByte += "1";
            }
            rowCount = 0;
        }
        //System.out.println(rowByte);
        byte b = (byte) (Integer.parseInt(rowByte, 2));//Converte a String final da paridade das linhas para Byte
        return b;
    }

    protected byte makeColParity(byte[] bytes) {//Faz a paridade das colunas e retorna esse novo Byte.
        String colByte = "";
        int colCount = 0;
        byte aByte;
        for (int i = 7; i >= 0; i--) {//Percorre Cada bit coluna de cada byte do bloco            
            for (int j = 7; j >= 0; j--) {
                aByte = bytes[j];
                if (((int) (((byte) (aByte >>> i)) & 1)) == 1) {
                    colCount++;
                }
            }
            if (colCount % 2 == 0) {//verifica se o número de bits "1" é par ou não.
                colByte += "0";
            } else {
                colByte += "1";
            }
            colCount = 0;
        }
        //System.out.println(colByte);
        byte b = (byte) (Integer.parseInt(colByte, 2));//Converte a String final da paridade das colunas para Byte
        return b;
    }
}
