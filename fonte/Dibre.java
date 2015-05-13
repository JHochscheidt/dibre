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
import java.util.ArrayList;

class Dibre {

    public static void main(String args[]) throws Exception {
        File f;
        Scanner s;
        Interpretador b;
        ArrayList<String> linhas = new ArrayList<String>();
		ArrayList<String> erros = new ArrayList<String>();
        // args[0] conterá o caminho para o arquivo que serah interpretado.
        f = new File(args[0]);
        // Mandamos o Scanner ler a partir do arquivo.
        s = new Scanner(f);
        // Instanciamos o interpretador.
        b = new Interpretador();

        // Lemos todas as linhas do arquivo para dentro do
        // ArrayList "linhas" OBS: joga apenas linhas que não sejam vazias.
        while(s.hasNext()) {
            //String aux = s.nextLine();
            //System.out.println("aux" + aux + aux.length());
            //if(aux.length() == 0){}
            //else{}
            linhas.add(s.nextLine());
        }
        //for(int cont = 0; cont < linhas.size() ; cont++){ System.out.println(linhas.get(cont)); }


		//erros = b.procuraErros(linhas);
		//b.imprimeErros(erros);



        // Inicializamos o interpretador com o vetor de linhas. A partir
        // desse ponto, o objeto "b" irá interpretar o código lido do arquivo.
        b.verificaEscopos(linhas);
        b.interpreta(linhas);

        //System.out.println("imprimindo variaveis");
        b.imprimeVariaveis();

    }
}
