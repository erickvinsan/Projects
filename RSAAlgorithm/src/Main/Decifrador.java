/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author erick
 */
public class Decifrador {
    private int n, d;

    public Decifrador(int n, int d) {
        this.n = n;
        this.d = d;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }
    
    

    /*
2 * Argumentos:
3 * - b: base da exponenciação.
4 * - e: expoente.
5 * - n: módulo (n > 1).
6 * Retorna: b^e (mod n).
7 */
    public int binExp(int b, int n, int d) {
        long res = b;
        long y = 1;

        /* Caso base. */
        if (d == 0) {
            return (1);
        }
        while (d > 1) {
            if ((d & 1) == 1) {
                /*
21 * Caso especial: expoente é ímpar.
22 * Acumular uma potência de ’res’ em ’y’.
23 */
                y = (y * res) % (long) n;
                d = d - 1;
            }
            /*
29 * Elevamos ’res’ ao quadrado, dividimos expoente por 2.
30 */
            res = (res * res) % (long) n;
            d = d / 2;
        }
        return ((int) ((res * y) % n));
    }
    
}
