/* 
 * File:   main.cpp
 * Author: erick
 *
 * Created on 6 de Fevereiro de 2016, 10:26
 */

#include <iostream>
#include <string>
#include <fstream>
#include <vector>
using namespace std;

/*
 * Argumentos:
 * - b: base da exponenciação.
 * - e: expoente.
 * - n: módulo (n > 1).
 * Retorna: b^e (mod n).
 */
unsigned short binExp(unsigned short b, int e, unsigned short n) {
    unsigned int res = b;
    unsigned int y = 1;
    /* Caso base. */
    if (e == 0) return (1);
    while (e > 1) {
        if (e & 1) {
            /*
             * Caso especial: expoente é ímpar.
             * Acumular uma potência de ’res’ em ’y’.
             */
            y = (y * res) % (unsigned int) n;
            e = e - 1;
        }
        /*
         * Elevamos ’res’ ao quadrado, dividimos expoente por 2.
         */
        res = (res * res) % (unsigned int) n;
        e = e / 2;
    }
    return ((unsigned short) ((res * y) % n));
}

void decifrar(const string& arqSaida, const int& N, const int& D) {
    ifstream fin(arqSaida.c_str(), ios::in | ios::binary);

    short byte;
    vector<short> shortsLidos;

    //Lê os shorts do arquivo.
    cout << "Shorts Cifrados Lidos: " << endl;
    while (fin.read((char *) &byte, sizeof (short))) {
        cout << byte << " ";
        shortsLidos.push_back(byte);
    }
    int tam = shortsLidos.size();

    unsigned char decifrado[tam];
    cout << endl << endl;

    //Decifra e printa na tela o valor do Bytes decifrado.
    cout << "Bytes decifrados: " << endl;
    for (size_t i = 0; i < tam; i++) {
        decifrado[i] = (unsigned char) binExp(shortsLidos.at(i), D, N);
        cout << (int) decifrado[i] << " ";
    }
    cout << endl << endl;

    //Mostra a mensagem decifrada em Texto.
    cout << "Mensagem Decifrada: " << endl;
    for (int i = 0; i < tam; i++) {
        cout << decifrado[i];
    }
    cout << endl << endl;

    fin.close();
}

void cifrar(const string& arqEntrada, const string& arqSaida, const int& N, const int& E) {
    ifstream fin(arqEntrada.c_str(), ios::in | ios::binary);
    ofstream fout(arqSaida.c_str(), ios::out | ios::binary);

    char byte;
    vector<char> bytesLidos;

    cout << "Mensagem Original: " << endl;
    //Lê a mensagem arquivo.
    while (fin.read((char *) &byte, sizeof (char))) {
        cout << byte;
        bytesLidos.push_back(byte);
    }
    cout << endl << endl;
    int tam = bytesLidos.size();
    unsigned short cifrado[tam];

    //Printa os bytes lidos do arquivo.
    cout << "Bytes Lidos: " << endl;
    for (int i = 0; i < tam; i++) {
        cout << (int) bytesLidos.at(i) << " ";
    }
    cout << endl << endl;

    //Cifra e mostra os bytes cifrados.
    cout << "Bytes cifrados: " << endl;
    for (size_t i = 0; i < tam; i++) {
        cifrado[i] = binExp((unsigned short) bytesLidos.at(i), E, N);
        cout << cifrado[i] << " ";
    }
    cout << endl << endl;

    //Escreve os shorts no arquivo de saída.
    for (int i = 0; i < tam; i++) {
        fout.write((char *) &cifrado[i], sizeof (unsigned short));
    }

    fin.close();
    fout.close();
}

int main() {
    int N, D, E;
    cout << "Entre com o valor de N (255 < n < 65536): " << endl;
    N = 1073; //cin >> N;
    cout << "Entre com o valor de D: " << endl;
    E = 71; //cin >> D;
    cout << "Entre com o valor de E: " << endl;
    D = 1079; //cin >> E;

    string arqEntrada, arqSaida;
    cout << "Entre com o nome do arquivo que deseja cifrar: " << endl;
    arqEntrada = "teste.cif"; //cin >> arqEntrada;
    cout << "Entre com o nome do arquivo de saida:" << endl;
    arqSaida = "teste.decif"; //cin >> arqSaida;

    cifrar(arqEntrada, arqSaida, N, E);
    decifrar(arqSaida, N, D);

    return 0;
}