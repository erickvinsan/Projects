package Main;

/**
 *
 * @author erick
 */
public class Cifrador {

    private int n, e;

    public Cifrador(int n, int e) {
        this.n = n;
        this.e = e;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    /*
2 * Argumentos:
3 * - b: base da exponenciação.
4 * - e: expoente.
5 * - n: módulo (n > 1).
6 * Retorna: b^e (mod n).
7 */
    public int binExp(int b, int n, int e) {
        long res = b;
        long y = 1;

        /* Caso base. */
        if (e == 0) {
            return (1);
        }
        while (e > 1) {
            if ((e & 1) == 1) {
                /*
21 * Caso especial: expoente é ímpar.
22 * Acumular uma potência de ’res’ em ’y’.
23 */
                y = (y * res) % (long) n;
                e = e - 1;
            }
            /*
29 * Elevamos ’res’ ao quadrado, dividimos expoente por 2.
30 */
            res = (res * res) % (long) n;
            e = e / 2;
        }
        return ((int) ((res * y) % n));
    }
}
