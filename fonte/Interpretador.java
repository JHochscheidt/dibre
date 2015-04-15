
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
    public static final String espacoEmBranco = "[ \t\nxB\f\r]";
	public ArrayList<Variavel> variavel = new ArrayList<Variavel>();
    // string que define com expressão regular, como se dará a declaração de uma variável
    String declaracaoDeVariavel = "\\s{0,}double\\s{1,}[[a-zA-Z]\\w{0,}\\s{0,}]{1,}\\s{0,};\\s{0,}";
    String declaracaoDeVariavelComAtribuicao = "\\s{0,}double\\s{1,}[a-zA-Z]\\w{0,}\\s{0,}=\\s{0,}\\d{1,}\\.?\\d{0,}\\s{0,};\\s{0,}";
    
	   
    
    
    
    //"\\s{0,}double\\s{0,}[[a-zA-Z]\\w{0,}\\s{0,}=\\s{0,}[0-9]{1,}.{0,1}[0-9]{0,}\\s{0,};\\s{0,}";
    		
    		//"\\s{0,}double\\s{1,}[[a-zA-Z]\\w{0,}\\s{0,}=\\s{0,}\\d{1,}\\.*\\d{0,}\\s{0,}]{1,}\\s{0,};\\s{0,}";
    		
    		
    
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
    	//boolean b = linha.matches(); //System.out.println(b);
    
    	if(linha.matches(declaracaoDeVariavel)){ // caso seja somente a declaração da variavel
    		Variavel newVar = new Variavel(); //System.out.println("chama metodo construtor de uma variavel");
    		newVar.declararVariavel(linha, variavel);
    		//System.out.println("'" + variavel.get(0).getNome() + "'");	//imprime o nome da variavel
    	}else if(linha.matches(declaracaoDeVariavelComAtribuicao)){ // caso seja declaracao com atribuicao de variavel
    		System.out.println("chama metodo construtor de uma variavel com atribuicao");
    		Variavel newVar = new Variavel();
    		newVar.declararVariavelComAtribuicao( linha, variavel);    		
    	}else{
    		System.out.println("nao esta entrando nos ifelseifs");
    	}		
	}    
    
    
    public void imprimeVariaveis(){
    	for(int indice = 0; indice < this.variavel.size(); indice++){
        	System.out.println("variavel     " + this.variavel.get(indice).getNome());
        	System.out.println("varlor       " + this.variavel.get(indice).getValor());
        }
    }
    
    
    
    
    
}
