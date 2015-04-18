// CÓDIGO COPIADO DOS ARQUIVOS DISPONIBILIZADOS VIA MOODLE PELO PROFESSOR FERNANDO

/*
 * 
 * Universidade Federal da Fronteira Sul 
 * Curso : Ciência da Computação
 * Componente Curricular : Programação I
 * Professor : Fernando Bevilacqua
 * Acadêmicos : Jackson Henrique Hochscheidt	e-mail : jackson94h@gmail.com
 * 				Maikiel Roos					e-mail : 
 * 	
*/

/*
 * 
 * Implementar um programa em Java que interprete uma linguagem de
 * programação. O interpretador deve ser capaz de analisar e reagir corretamente às seguintes
 * situações no arquivo fonte que ele esteja interpretando:
 * Declaração de variáveis;
 * Atribuição de valor a variável;
 * Expressões com no mínimo dois operandos (ex.: a + b);
 * Operações de adição, subtração, divisão e multiplicação;
 * Laço;
 * Comando de saída (ex.: mostrar algo na tela);
 * Controlador de fluxo (ex.: if);
 * Aninhamento de comandos (ex.: ifs dentro de ifs, laços dentro de laços).
 * 
 */


import java.util.Scanner;
import java.io.*;

class Dibre {
    public static void main(String args[]) throws Exception {
        File f;
        Scanner s;
        Interpretador b;
        String linhas[] = new String[2000]; // arquivo pode ter, no máximo, 2000 linhas.

        // args[0] conterá o caminho para o arquivo que serah interpretado.
        f = new File(args[0]);
        // Mandamos o Scanner ler a partir do arquivo.
        s = new Scanner(f);
        // Instanciamos o interpretador.
        b = new Interpretador();

        // Lemos todas as linhas do arquivo para dentro do
        // vetor "linhas".
        int i = 0;
        while(s.hasNext()) {
            linhas[i] = s.nextLine();
            i++;
        }

        // Inicializamos o interpretador com o vetor de linhas. A partir
        // desse ponto, o objeto "b" irá interpretar o código lido do arquivo.
        b.interpreta(linhas);
        
        //System.out.println("imprimindo variaveis");
        //b.imprimeVariaveis();
        
    }
}
