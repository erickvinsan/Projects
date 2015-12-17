package paridadedebits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erick
 */
public class Codificador {

    private final InputStream fileReader;

    public Codificador(String fileName) throws FileNotFoundException, IOException {
        fileReader = new FileInputStream(fileName);
    }
    
    public List<Byte> makeArrayBytes() throws IOException{
        List<Byte> fileBytes = new ArrayList<>();
        int aux;
        do {
            aux = fileReader.read();
            fileBytes.add((byte)aux);
        } while(aux != -1);
        return fileBytes;
    }
}
