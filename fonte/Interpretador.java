
/* Código copiado dos arquivos disponibilizados pelo professor via moodle
 * 
 * Este código faz a leitura do arquivo fonte a ser interpretado,
 * e chama os métodos que fazem o reconhecimento dos comandos da linguagem .dibre
 * 
 * 
 */ 


import java.util.ArrayList;
import java.util.regex.*;

class Interpretador {
    public String linhas[];
	public ArrayList<Variavel> variavel = new ArrayList<Variavel>();
    // string que define com expressão regular, como se dará a declaração de uma variável
    String declaracaoDeVariavel = "\\s{0,}double\\s{1,}[a-zA-Z]\\w{0,}\\s{0,};\\s{0,}";
    String declaracaoDeVariavelComAtribuicao = 
    	"\\s{0,}double\\s{1,}[a-zA-Z]\\w{0,}\\s{0,}=\\s{0,}\\d{1,}\\.?\\d{0,}\\s{0,};\\s{0,}";
    
    // metodo que recebe as linhas do arquivo
    public void interpreta(String l[]) {
        this.linhas = l;
        //percorre as linhas do arquivo
        for(int i = 0; i < this.linhas.length; i++) {
        	//verifica se a linha está vazia
            if(this.linhas[i] != null) {
                // TODO: interpretar a linha
                //System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
                //chama metodo que interpreta cada linha em especifico
                this.interpretaLinha(this.linhas[i]);
			}
        }   
    }

    
    // método que interpreta uma linha específica
    // todas as linhas serão interpretadas através de expressão regular
    public void interpretaLinha(String linha){
    	boolean b = linha.matches(declaracaoDeVariavel);
    	//System.out.println(b);
    	if(linha.matches(declaracaoDeVariavel)){
    		//identificar qual o nome da variavel e depois chamar seu metodo construtor
    		Variavel newVar = new Variavel();
    		//System.out.println("chama metodo construtor de uma variavel");
    		newVar.declararVariavel(linha);
    		variavel.add(newVar);
    		//System.out.println("'" + variavel.get(0).getNome() + "'");	//imprime o nome da variavel
    	/*}else if(linha.matches(declaracaoDeVariavelComAtribuicao)){
    		//identificar qual o nome da variavel e depois chamar seu metodo construtor
    		System.out.println("chama metodo construtor de uma variavel com atribuicao");
    	*/
    	}else{
    		System.out.println("nao esta entrando nos ifelseifs");
    	}		
	}    
    
}
