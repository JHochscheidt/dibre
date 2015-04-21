
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
    public static final String espacoEmBranco = "[ \t\n\f\r]";
    
	public ArrayList<Variavel> variavel = new ArrayList<Variavel>();
    // string que define com expressão regular, como se dará a declaração de uma variável
    String declaracaoDeVariavel = "\\s{0,}varReal\\s{1,}[a-zA-Z]{1,}\\w{0,}\\s{0,};\\s{0,}";
    String declaracaoDeVariavelComAtribuicao = "\\s{0,}varReal\\s{1,}[a-zA-Z]{1,}\\w{0,}\\s{0,}=\\s{0,}\\-?\\d{1,}\\.?\\d{0,}\\s{0,};\\s{0,}";
    String atribuicaoComExpressao =
    	"\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}=\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}"; //|\\-?\\d{1,}\\.?\\d{0,}\\s{0,}]" ;
    	//\\s{0,}SOMA|SUBTRAI|MULTIPLICA|DIVIDE\\s{0,}" ; /*+ //operador
    	//"[\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}]|\\s{0,}\\-?\\d{1,}\\.?\\d{0,}\\s{0,}" ;/* + //operando1
    	//"[[\\s{0,}\\-?\\d{1,}\\.?\\d{0,}]|[\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}]]" + // operando2
    	//"\\s{0,};\\s{0,}";
    		
    		
    		
    
    /* atribuicao com expressao aceitando varios operadores e operandos
    "\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}=[[\\s{0,}\\-?\\d{1,}\\.?\\d{0,}]|[\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}]][\\s{0,}[SOMA|SUBTRAI|MULTIPLICA|DIVIDE][\\s{0,}\\-?\\d{1,}\\.?\\d{0,}\\s{0,}]|[\\s{0,}[\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}]]]{0,}\\s{0,};\\s{0,}";
    */
    //"\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}=\\s{0,}[[\\-?\\d{1,}\\.?\\d{0,}\\s{0,}]|[[a-zA-Z]{1,}\\w{0,}\\s{0,}]]\\+|\\-|\\*\\/\\s{0,}[[\\-?\\d{1,}\\.?\\d{0,}\\s{0,}]|[[a-zA-Z]{1,}\\w{0,}\\s{0,}]]\\s{0,};\\s{0,}";   
    
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
    	System.out.println("linha " + (i+1) + " -->");
    	boolean b = linha.matches(atribuicaoComExpressao);
    	System.out.println(b);
    	ArrayList<String> tokens = new ArrayList<String>();
    	if(linha.matches(declaracaoDeVariavel)){
    		System.out.println("declaracaoDeVariavel");
    		tokens.addAll(this.procuraTokens(linha, Interpretador.espacoEmBranco + "|varReal|;"));
    		if(this.declararVariavel(tokens.get(0),0, false) == null){
    			System.out.println("Erro na linha @@@ " + (i+1) + " @@@ Variavel ### " + tokens.get(0) + " ### já declarada");
    			System.exit(0);
    		}
    	}else if(linha.matches(declaracaoDeVariavelComAtribuicao)){
    		System.out.println("declaracaoDeVariavelComAtribuicao");
    		tokens.addAll(this.procuraTokens(linha, Interpretador.espacoEmBranco + "|varReal|=|;"));
    		if(this.declararVariavel(tokens.get(0),Double.parseDouble(tokens.get(1)),true) == null){
    			System.out.println("Erro na linha @@@ " + (i+1) + " @@@ Variavel ### " + tokens.get(0) + " ### já declarada");
    			System.exit(0);
    		}
    	}else if(linha.matches(atribuicaoComExpressao)){
    		System.out.println("atribuicaoComExpressao");
    		tokens.addAll(this.procuraTokens(linha, Interpretador.espacoEmBranco + "|^SOMA|^SUBTRAI|^MULTIPLICA|^DIVIDE|;|="));
    		System.out.println("tooooooooooookeeeeeeeeeeeeeeeeeeens \n\n\n");
    		for(int cont = 0; cont < tokens.size(); cont++){ System.out.println(cont + " '" + tokens.get(cont) + "'"); }
    		//this.imprimeVariaveis();
    		// verifica se a variavel a receber resultado da expressao existe
    		if(this.variavelJaExiste(tokens.get(0)) != null && tokens.get(2).equals("SOMA|SUBTRAI|MULTIPLICA|DIVIDE")){
    			System.out.println("aqui");
    			/*double op1 = 0;
    			double op2 = 0;
    			Variavel aux = this.variavelJaExiste(tokens.get(0));
    			//System.out.println("nome da variavel " + aux.getNome() + " valor " + aux.getValor());
    			ArrayList<String> subTokens = new ArrayList<String>();
    			subTokens = this.procuraTokens(linha,Interpretador.espacoEmBranco + "|[^SOMA|^SUBTRAI|^MULTIPLICA|^DIVIDE]");
    			//System.out.println("SUBTOKENS\n");
    			//for(int cont = 0; cont < subTokens.size(); cont++){ System.out.println(cont + " '" + subTokens.get(cont) + "'" +  " tamanho " + tokens.get(cont).length()); }
    			if(subTokens.size() == 1){
    				//System.out.println("subtokens = 1\n");
    				//System.out.println("TOKENS\n");
    	    		//for(int cont = 0; cont < tokens.size(); cont++){ System.out.println(cont + " '" + tokens.get(cont) + "'"); }
        			// verificacao se o token é o nome de uma variavel
    	    		for(int cont = 1; cont < tokens.size(); cont++){
    	    			if(tokens.get(cont).matches("[a-zA-Z]{1,}\\w{0,}")){
        					//System.out.println("tokens " + tokens.get(cont) + " eh variavel");
        					//verifica se a variavel a qual esta se pegando valor, existe
        					if(this.variavelJaExiste(tokens.get(cont)) == null){
        						System.out.println("Erro na linha @@@ " + (i+1) + " @@@ Variavel ### " + tokens.get(cont) + " ### não existe");
        		    			System.exit(0);
        					}else{
        						// verifica se a variavel a qual esta se pegando valor ja foi inicializada
        						if(this.variavelJaExiste(tokens.get(cont)).getInicializada()){
        							if(cont == 1)
        								op1 = this.variavelJaExiste(tokens.get(cont)).getValor();
        							else
        								op2 = this.variavelJaExiste(tokens.get(cont)).getValor();
        							//System.out.println("valor op1" + op1);
        						}else{
        							System.out.println("Erro na linha @@@ " + (i+1) + " @@@ Variavel ### " + tokens.get(cont) + " ### não inicializada");
            		    			System.exit(0);
        						}
        					}
    	    			}else if(tokens.get(cont).matches("\\-?\\d{1,}\\.?\\d{0,}")){
    	    				//System.out.println(tokens.get(cont) + " é um numero");
    	    				if(cont == 1)
    	    					op1 = Double.parseDouble(tokens.get(cont));
    	    				else
    	    					op2 = Double.parseDouble(tokens.get(cont));
    	    			}else{
    	    				System.out.println("operandos nao validos");
    	    			}
    	    		}
    	    		aux.setValor(Operacao.operacao(op1,op2,subTokens.get(0)));
    	    	}else{
    			System.out.println("variavel nao existe");
    			System.exit(0);
    	    	}
    			//System.out.println("operando 1 " + op1 + " operando 2 " + op2);
    			 */
    		}else{
    			System.out.println("erroooooooooooooo linha " + (i+1));
    		}
    	}else{
    		System.out.println("erro linha " + (i+1));
    	}
	}    
    
    // metodo que vai tentar declarar variavel sem atribuicao
    public Variavel declararVariavel(String nome, double valor, boolean inicializada){
    	//System.out.println("declarando variavel...\n ----->>>>>" + nome);
    	if(variavelJaExiste(nome) == null){
    		Variavel newVar = new Variavel();
    		newVar.setNome(nome);
    		newVar.setValor(valor);
    		newVar.setInicializada(inicializada);
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
    public Variavel variavelJaExiste(String var){
    	//System.out.println("verificando se variavel ### " + var + " ### ja existe");
		for(int cont = 0; cont < variavel.size(); cont++)
			if(var.equals(variavel.get(cont).getNome()))
				return variavel.get(cont);
		return null;
	}
    
    // percorre o ArrayList de Variavel imprimindo nome e valor de cada variavel
    public void imprimeVariaveis(){
    	System.out.println("\t###########################################");
    	System.out.println("\t########## Variáveis do programa ##########");
    	for(int indice = 0; indice < this.variavel.size(); indice++){
        	System.out.println("\t########## -->  " + this.variavel.get(indice).getNome() + " = " + this.variavel.get(indice).getValor());
        }
    	System.out.println("\t###########################################");
    }
}
