
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
    private ArrayList<String> linhas;
    public static final String espacoEmBranco = "[ \t\nxB\f\r]";
    
	public ArrayList<Variavel> variavel = new ArrayList<Variavel>();
    // string que define com expressão regular, como se dará a declaração de uma variável
    String declaracaoDeVariavel = "\\s{0,}varDouble\\s{1,}[a-zA-Z]{1,}\\w{0,}\\s{0,}\\s{0,};\\s{0,}";
    String declaracaoDeVariavelComAtribuicao = "\\s{0,}varDouble\\s{1,}[a-zA-Z]{1,}\\w{0,}\\s{0,}=\\s{0,}\\-?\\d{1,}\\.?\\d{0,}\\s{0,};\\s{0,}";
    String atribuicaoComExpressao = "\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}=[[a-zA-Z]{1,}\\w{0,}|\\d{1,}\\.?\\d{0,}]\\s{0,};\\s{0,}";
    
    
    
    // metodo que recebe as linhas do arquivo
    public void interpreta(ArrayList<String> l) {
        this.linhas = l;
        //percorre as linhas do arquivo
        for(int i = 0; i < this.linhas.size(); i++) {
        	//verifica se a linha está vazia
            if(this.linhas.get(i) != null)
                this.interpretaLinha(this.linhas.get(i), i);
        }   
    }
    
    // método que interpreta uma linha específica
    // todas as linhas serão interpretadas através de expressão regular
    public void interpretaLinha(String linha, int i){
    	//boolean b = linha.matches(); //System.out.println(b);
    	ArrayList<String> tokens = new ArrayList<String>();
    	if(linha.matches(declaracaoDeVariavel)){
    		tokens.addAll(this.procuraTokens(linha, Interpretador.espacoEmBranco + "|varDouble|;"));
    		if(this.declararVariavel(tokens.get(0),0) == null){
    			System.out.println("Erro na linha @@@ " + (i+1) + " @@@ Variavel ### " + tokens.get(0) + " ### já declarada");
    			System.exit(0);
    		}		
    	}else if(linha.matches(declaracaoDeVariavelComAtribuicao)){
    		System.out.println("declarando variavel com atribuicao");
    		tokens.addAll(this.procuraTokens(linha, Interpretador.espacoEmBranco + "|varDouble|=|;"));
    		for(int cont = 0; cont < tokens.size(); cont++){
    			System.out.println("'" + tokens.get(cont) + "'");
    		}
    		if(this.declararVariavel(tokens.get(0),Double.parseDouble(tokens.get(1))) == null){
    			System.out.println("Erro na linha @@@ " + (i+1) + " @@@ Variavel ### " + tokens.get(0) + " ### já declarada");
    			System.exit(0);
    		}
    	}else if(linha.matches(atribuicaoComExpressao)){
    		System.out.println("atribuicao com expressao");
    	}else{
    		System.out.println("erroooooooooooooo");
    	}
	}    
    
    // metodo que vai tentar declarar variavel sem atribuicao
    public Variavel declararVariavel(String nome, double valor){
    	//System.out.println("declarando variavel...\n ----->>>>>" + nome);
    	if(!verificaSeExisteVariavel(nome)){
    		Variavel newVar = new Variavel();
    		newVar.setNome(nome);
    		newVar.setValor(valor);
    		variavel.add(newVar);
    		return newVar;
    	}
    	System.out.println("variavel ja existe");
    	return null;
    }    
 
    // metodo que separa a linha por tokens
     public ArrayList<String> procuraTokens(String linha, String separadores){
    	//System.out.print("procurando tokens na linha...\n------>>>>>>>>>" + linha);
    	String[] tokens;
    	tokens = linha.split(separadores); // separa a linha por espacos em branco
    	//depuracao
    	//for(int cont = 0; cont < tokens.length; cont++){ System.out.println("'" + tokens[cont] + "'" +  " tamanho " + tokens[cont].length()); }
    	ArrayList<String>subTokens = new ArrayList<String>();
    	if(tokens.length > 1)
    		// percorre o vetor de tokens, procurando por espacos em branco novamente
    		for(int indice = 0; indice < tokens.length; indice++)
    			if(tokens[indice].length() > 0)
    				subTokens.add(tokens[indice]);
    		//depuracao
    		//for(int i = 0; i < subTokens.size(); i++){ System.out.println("'" + subTokens.get(i) + "'" + " tamanho" + subTokens.get(i).length());}
       	return subTokens;
    }    
        
    // retorna true se variavel ja existe, senao retorna falso
    public boolean verificaSeExisteVariavel(String var){
    	System.out.println("verificando se variavel ### " + var + " ### ja existe");
		for(int cont = 0; cont < this.variavel.size(); cont++)
			if(var.equals(this.variavel.get(cont).getNome()))
				return true;
		return false;
	}
    
    // percorre o ArrayList de Variavel imprimindo nome e valor de cada variavel
    public void imprimeVariaveis(){
    	System.out.println("\t###########################################");
    	System.out.println("\t########## Variáveis do programa ##########\n");
    	for(int indice = 0; indice < this.variavel.size(); indice++){
        	System.out.println("\t########## -->  " + this.variavel.get(indice).getNome() + " = " + this.variavel.get(indice).getValor());
        }
    	System.out.println("\n\t###########################################\n");
    }
}
