
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
    String declaracaoDeVariavel = "\\s{0,}double\\s{1,}[a-zA-Z]\\w{0,}\\s{0,}\\s{0,};\\s{0,}";
    String declaracaoDeVariavelComAtribuicao = "\\s{0,}double\\s{1,}[a-zA-Z]\\w{0,}\\s{0,}=\\s{0,}\\d{1,}\\.?\\d{0,}\\s{0,};\\s{0,}";
        
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
    	ArrayList<String> tokens = new ArrayList<String>(); 
    	tokens.addAll(this.procuraTokens(linha));
    	//depuracao
    	for(int i=0; i < tokens.size(); i++) System.out.println("'" + tokens.get(i) + "'");
    	
    	
    	/*if(linha.matches(declaracaoDeVariavel)){ // caso seja somente a declaração da variavel
    		if(!this.declararVariavel(tokens))
    			System.out.println("Erro na linha, variavel ja existe");
    	}*/
	}    
    
    // metodo que tenta fazer a declaracao de uma variavel, retorna true se sim, e false se nao conseguir declarar
    public boolean declararVariavel(ArrayList<String> tokens){
    	if(tokens.size() > 1){
			for(int indice = 0;indice < tokens.size(); indice++){
				//se entrar neste if, significa que é o nome da variavel
				//System.out.println(indice + "'"+tokens[indice]+"'"+  "\ttamanho" + tokens[indice].length());
				if(!tokens.get(indice).equals("double") && !tokens.get(indice).equals(";")){
					if(this.verificaSeExisteVariavel(tokens.get(indice))){
						System.out.println("variavel ja existe");
						return false;
					}else{
						System.out.println("variavel ### " + tokens.get(indice) + " ### ainda nao existe, pode declarar");
						Variavel newVar = new Variavel();
						newVar.setNome(tokens.get(indice));
						variavel.add(newVar);
						return true;
					}
					//System.out.println(tokens[indice]);	// imprime o nome da variavel
				}
			}	
		}
		return false;
    }
        
    // metodo que separa a linha por tokens
     public ArrayList<String> procuraTokens(String linha){
    	//System.out.println("procurando tokens na linha...\n------>>>>>>>>>");
    	System.out.println(linha);
    	String[] tokens;
    	tokens = linha.split(Interpretador.espacoEmBranco); // separa a linha por espacos em branco
    	//depuracao
    	//for(int cont = 0; cont < tokens.length; cont++){ System.out.println("'" + tokens[cont] + "'" +  " tamanho " + tokens[cont].length()); }
    	ArrayList<String>subTokens = new ArrayList<String>();
    	if(tokens.length > 1) 
    		// percorre o vetor de tokens, procurando por espacos em branco novamente
    		for(int indice = 0; indice < tokens.length; indice++)
    			if(tokens[indice].length() > 0){
    				System.out.println("aqui");
    				//subTokens.add(tokens[indice]);
    				if(tokens[indice].equals("\\w{1,}[\\;\\s{0,})$")){
    					//System.out.println("termina com ;");
    				}
    			}
    				//subTokens.add(tokens[indice]); 
    		//depuracao
    		//for(int i = 0; i < subTokens.size(); i++){ System.out.println("'" + subTokens.get(i) + "'" + " tamanho" + subTokens.get(i).length());}
       	return subTokens;
    }    
        
    // retorna true se variavel ja existe, senao retorna falso
    public boolean verificaSeExisteVariavel(String var){
		for(int cont = 0; cont < this.variavel.size(); cont++){
			if(var.equals(this.variavel.get(cont).getNome())){
				System.out.println("variavel ja existe");
				return true;
			}
		}
		return false;
	}
    
    // percorre o ArrayList de Variavel imprimindo nome e valor de cada variavel
    public void imprimeVariaveis(){
    	System.out.println("\t###########################################");
    	System.out.println("\t#  Variáveis do programa");
    	for(int indice = 0; indice < this.variavel.size(); indice++){
        	System.out.println("\t#" + this.variavel.get(indice).getNome() + " = " + this.variavel.get(indice).getValor());
        }
    	System.out.println("\n\t##########################################\n");
    }
     
}
