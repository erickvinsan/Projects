Requisitos:
    - Java.
    - NetBeans 8.1.
    - SDK 8u65 (Última Versão).

Instruções de execução:
    - Na linha de comando executar:
        java -jar "CAMINHO DO PROJETO\dist\ParidadedeBits.jar"
        Ex: 
        java - jar C:\Users\mcrva\Desktop\ParidadeDeBits\dist\ParidadedeBits.jar

    - Durante a execução será pedido inicialmente o nome do arquivo de entrada
        e logo depois o nome do arquivo de saída.
        Ex:
        4096in.pct

    - Também é possível informar o diretório completo do arquivo,
        incluindo seu nome.
        Ex:
        C:\Users\mcrva\Desktop\ParidadeDeBits\4096in.pct

    - Caso não seja informado o diretório de entrada e saída,
        os arquivos serão lidos/escritos no diretório atual.

Fluxo de execução:
    - Após selecionar o arquivo de entrada serão informados os seguintes campos:
        - Quantidade de bytes: 
        Quantidade total de bytes lidos do arquivo de entrada.

        - Grupos de 8 bytes:
        Quantidade de grupos completos de 8 bytes gerados com o arquivo
        de entrada.

        - Resto(Apenas quando a quantidade de bytes não é múltipla de 8): 
        Quantidade de bytes no último bloco de bytes.

        - Bloco = (Número do bloco):
        Bytes do bloco exibidos em forma decimal.

    - Depois disso irá ocorrer o processo de codificação,
        sua saída será exibida separada por blocos.

        Cada bloco está no seguinte formato:
            - P.Coluna,P.Linha,B1,B2,...B8
        O último byte(11111111) indica o final do arquivo.
        
    - No fim da codificação são introduzidos erros no arquivo !!!REVER!!!

    - No início da etapa de decodificação é exibido o estado de cada bloco
        no seguinte modelo:
            Estado do bloco: Com erros ou sem.

            Estado do erro(caso exista): Corrigível ou não. 

            Posição do erro(caso exista): 
                Byte: Posição do byte dentro do bloco,
                    sendo 0 o byte logo após os bytes de paridade. 
                Bit: Número do bit dentro do byte contado a partir do valor
                    menos significativo(direita para esquerda).

        Em seguida será exibido o quadro com os blocos de bytes decodificados
            no seguinte formato:
                - B1,B2,...B8
            O último byte(11111111) indica o final do arquivo.    
              
        
        
        
