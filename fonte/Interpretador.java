
/* Código copiado dos arquivos disponibilizados pelo professor via moodle
 *
 * Este código faz a leitura do arquivo fonte a ser interpretado,
 * e chama os métodos que fazem o reconhecimento dos comandos da linguagem .dibre
 * *
 */

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.*;

class Interpretador {
    private ArrayList<String> codigo;
    public static final String espacoEmBranco = "[ \b\t\n\f\r]";
	public ArrayList<Variavel> variavel = new ArrayList<Variavel>();
    ArrayList<String> tokens = new ArrayList<String>();
	Stack<Escopo> pilha = new Stack<Escopo>();

    /*
     * Expressões regulares usadas para interpretação de cada linha do código
     * As expressões dirão como funcionará cada tipo de comando
    */

    //String comandoDeSaida ="\\s{0,}MOSTRAR\\s{1,}(\\#\\s{0,}(\\w{1,}\\s{1,}){0,}\\#|[a-zA-Z]{1,}\\w{0,}|\\s{0,}\\-?\\d{1,}\\.?\\d{0,})\\s{1,}]{1,}\\s{0,};\\s{0,}";
    /// atribuicao com expressao aceitando varios operadores e operandos
    //"\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}=[[\\s{0,}\\-?\\d{1,}\\.?\\d{0,}]|[\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}]][\\s{0,}[SOMA|SUBTRAI|MULTIPLICA|DIVIDE][\\s{0,}\\-?\\d{1,}\\.?\\d{0,}\\s{0,}]|[\\s{0,}[\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}]]]{0,}\\s{0,};\\s{0,}";
    //"\\s{0,}[a-zA-Z]{1,}\\w{0,}\\s{0,}=\\s{0,}[[\\-?\\d{1,}\\.?\\d{0,}\\s{0,}]|[[a-zA-Z]{1,}\\w{0,}\\s{0,}]]\\+|\\-|\\*\\/\\s{0,}[[\\-?\\d{1,}\\.?\\d{0,}\\s{0,}]|[[a-zA-Z]{1,}\\w{0,}\\s{0,}]]\\s{0,};\\s{0,}";

	String inicioCodigo = "\\s{0,}INICIO\\s{0,}";
	String fimEscopo = "\\s{0,}FIM\\s{0,}";
	String tipoDeVariavel = "varReal";
	String nomeDeVariavel = "[a-zA-Z]{1,}\\w{0,}";
    String valor = "\\-?\\d{1,}\\.?\\d{0,}";
    String pontoEVirgula = "\\s{0,};\\s{0,}";
    String operadoresAritmeticos = "(SOMA|SUBTRAI|MULTIPLICA|DIVIDE)";
    String operadoresRelacionais = "(MAIORQUE|MENORQUE|IGUAL|MAIORouIGUAL|MENORouIGUAL|DIFERENTE)";
    String operadoresLogicos = "(OU|E)";
    String operacaoAritmetica = "(" + nomeDeVariavel + "|" + valor + ")\\s{1,}" + operadoresAritmeticos + "\\s{1,}(" + nomeDeVariavel + "|" + valor + ")\\s{0,}";
    String operacaoRelacional = "(" + nomeDeVariavel + "|" + valor + ")\\s{1,}" + operadoresRelacionais + "\\s{1,}(" + nomeDeVariavel + "|" + valor + ")\\s{0,}";
    String operacaoLogica = "(" + operacaoAritmetica + "|" + operacaoRelacional + ")\\s{1,}" + operadoresLogicos + "\\s{1,}(" + operacaoAritmetica + "|" + operacaoRelacional + ")\\s{0,}";
    String declaracaoDeVariavel = "\\s{0,}" + tipoDeVariavel + "\\s{1,}" + nomeDeVariavel + "[,\\s{0,}" + nomeDeVariavel + "]{0,}" + pontoEVirgula;
    String declaracaoDeVariavelComAtribuicao = "\\s{0,}" + tipoDeVariavel + "\\s{1,}" + nomeDeVariavel + "\\s{0,}=\\s{0,}" + "(" + valor + "|" + nomeDeVariavel + ")" + pontoEVirgula;
    String atribuicaoSimples = "\\s{0,}" + nomeDeVariavel + "\\s{0,}=\\s{0,}" + "(" + nomeDeVariavel + "|" + valor + ")" + pontoEVirgula;
    String atribuicaoComExpressao = "\\s{0,}" + nomeDeVariavel + "\\s{0,}=\\s{0,}" + operacaoAritmetica + pontoEVirgula;
    String atribuicao = atribuicaoSimples + "|" + atribuicaoComExpressao ;
    String IF = "\\s{0,}IF\\s{1,}(" + operacaoAritmetica + "|" + operacaoRelacional + "|" + atribuicaoComExpressao + ")\\s{1,}FAÇA\\s{0,}";
    String ELSEIF = "\\s{0,}ELSEIF\\s{1,}(" + operacaoAritmetica + "|" + operacaoRelacional + ")\\s{1,}FAÇA\\s{0,}";
    String ELSE = "\\s{0,}ELSE\\s{1,}FAÇA";
    String laco = "\\s{0,}ENQUANTO\\s{0,}(" + operacaoLogica + ")\\s{0,}FAÇA\\s{0,}";
    String comandoDeSaida = "\\s{0,}MOSTRAR\\s{1,}[(\\$" + nomeDeVariavel + "|" + valor + "|\\w{1}\\s{0,}|\\S|\n)\\s{1,}]{0,}" + pontoEVirgula ;
    String comandoDeEntrada = "";

    // metodo que recebe as linhas do arquivo
    public void interpreta(ArrayList<String> l) {
        this.codigo = l;
        //percorre as linhas do arquivo
        for(int linha = 0; linha < this.codigo.size(); linha++) {
        	//verifica se a linha está vazia
            //System.out.println(linhas.get(i) + "tamanho" + linhas.get(i).length());
        	if(this.codigo.get(linha).length() != 0){
                if(pilha.empty() && codigo.get(linha).length() > 0 && !codigo.get(linha).matches(inicioCodigo)){
                    System.out.println("erro antes de iniciar o programa");
                    System.exit(0);
                }else{
                    //System.out.println("interpreta linha");
                    this.interpretaLinha(this.codigo, linha);
                }
            }
        }
    }

    // método que interpreta uma linha específica
    // todas as linhas serão interpretadas através de expressão regular
    public void interpretaLinha(ArrayList<String> codigo, int linha) throws NumberFormatException{
    	if(codigo.get(linha).matches(inicioCodigo)){
    		System.out.println("inicio do codigo");
    		Escopo programa = new Escopo("programa", (linha+1));
    		pilha.push(programa);
            //System.out.println(pilha.peek().getInicio());
    	}
    	else if(codigo.get(linha).matches(declaracaoDeVariavel)){
    		System.out.println("declaracaoDeVariavel");
    		tokens.addAll(this.procuraTokens(codigo.get(linha), Interpretador.espacoEmBranco + "|" + tipoDeVariavel + "|,|;"));
    		for(int cont = 0; cont < tokens.size(); cont++) System.out.println("'" + tokens.get(cont) + "'");
            for(int cont = 0; cont < tokens.size(); cont++){
                if(this.declararVariavel(tokens.get(cont),0, false) == null){
                    System.out.println("Erro na linha @@@ " + (linha+1) + " @@@ Variavel ### " + tokens.get(cont) + " ### já declarada");
                    System.exit(0);
                }
            }
            tokens.clear();
    	}
        else if(codigo.get(linha).matches(declaracaoDeVariavelComAtribuicao)){
    		System.out.println("declaracaoDeVariavelComAtribuicao");
    		tokens.addAll(this.procuraTokens(codigo.get(linha), Interpretador.espacoEmBranco + "|varReal|=|;"));
            //for(int cont = 0; cont < tokens.size(); cont++) System.out.println("'" + tokens.get(cont) + "'");
            try{
                Double.parseDouble(tokens.get(1));
            }catch(NumberFormatException e){
                imprimeVariaveis();
                System.out.println("é nome de variavel");
                System.out.println(tokens.get(1));
                if(variavelJaExiste(tokens.get(1)) != null){
                    System.out.println("variavel existe");
                    if(this.variavelJaExiste(tokens.get(1)).getInicializada()){
                        System.out.println("variavel ja existe");
                        tokens.set(1, String.valueOf(this.variavelJaExiste(tokens.get(1)).getValor()));
                        System.out.println(tokens.get(1));
                    }else{
                        System.out.println("variavel nao inicializada, impossivel imprimir");
                        System.exit(0);
                    }
                }else{
                    System.out.println("variavel nao existe");
                    System.exit(0);
                }
            }finally{
                if(this.declararVariavel(tokens.get(0),Double.parseDouble(tokens.get(1)),true) == null){
                    System.out.println("Erro na linha @@@ " + (linha+1) + " @@@ Variavel ### " + tokens.get(0) + " ### já declarada");
			        System.exit(0);
                }
                tokens.clear();
            }
        }
        else if(codigo.get(linha).matches(atribuicaoComExpressao)){
    		System.out.println("atribuicaoComExpressao");
    		tokens.addAll(this.procuraTokens(codigo.get(linha), Interpretador.espacoEmBranco + "|^" + operadoresAritmeticos + "|;|="));
    		for(int cont = 0; cont < tokens.size(); cont++){ System.out.println(cont + " '" + tokens.get(cont) + "'" + " size " + tokens.get(cont).length()); }
    		//this.imprimeVariaveis();
    		// verifica se a variavel a receber resultado da expressao existe
    		if(this.variavelJaExiste(tokens.get(0)) != null){
    			String operador = tokens.get(2);
    			// verifica se o operador é valido
    			if(operador.equals("SOMA") || operador.equals("SUBTRAI") || operador.equals("MULTIPLICA") || operador.equals("DIVIDE")){
                	//System.out.println("aqui");
	    			Variavel aux = this.variavelJaExiste(tokens.get(0));
	    			double operando1 = 0, operando2 = 0;
	    			//System.out.println("nome da variavel " + aux.getNome() + " valor " + aux.getValor());
	                for(int cont = 1; cont < tokens.size(); cont++){
	                	// se cont for impar, significa que é um operando, else, é operador
	                	if(cont%2 == 1){
	                		// codigo para operando
	                		// verifica se é uma variavel ou um valor
	                		if(tokens.get(cont).matches("[a-zA-Z]{1,}\\w{0,}")){
	                			//System.out.println("é variavel");
	                			//System.out.println(tokens.get(cont));
	                			//verifica se existe variavel com esse nome, e se ja foi inicializada
	                			if(this.variavelJaExiste(tokens.get(cont)) != null && this.variavelJaExiste(tokens.get(cont)).getInicializada()){
	                				if(cont == 1){
	                					operando1 = this.variavelJaExiste(tokens.get(cont)).getValor();
	                					System.out.println("valor operador 1 " + operando1);
	                				}else{
	                					operando2 = this.variavelJaExiste(tokens.get(cont)).getValor();
	                					//System.out.println("valor operador 1 " + operando2);
                                        if(operador.equals("DIVIDE") && operando2 == 0.0){
                                            System.out.println("operando 2 igual a zero impossivel dividir");
                                            System.exit(0);
                                        }
	                				}
	                			}else{
	                				System.out.println("Erro na linha @@@ " + (linha+1) + " @@@ Variavel ### " + tokens.get(cont) + " ### não existe ou não foi inicializada");
	        		    			System.exit(0);
	                			}
	                		}else if(tokens.get(cont).matches("\\-?\\d{1,}\\.?\\d{0,}")){
	                			System.out.println("é valor");
	                			if(cont == 1){
	    	    					operando1 = Double.parseDouble(tokens.get(cont));
	                			}else{
	    	    					operando2 = Double.parseDouble(tokens.get(cont));
                                    if(operador.equals("DIVIDE") && operando2 == 0){
                                        System.out.println("operando 2 igual a zero impossivel dividir");
                                        System.exit(0);
                                    }
	                			}
	                		}else{
	                			System.out.println("operadores invalidos");
	                		}
	                	}
	                } // for de verificacao de variaveis ou valores
	                aux.setValor(Operacao.operacao(operando1,operando2,tokens.get(2)));
                    aux.setInicializada(true);
                    //System.out.println(aux.getInicializada() + " " + aux.getValor() + " " + aux.getNome());
	    	    }else{
	    			System.out.println("operador nao existe");
	    			System.exit(0);
	    	    }// else verifica se operador é valido
    		}else{
    				System.out.println("variavel nao existe");
                    System.exit(0);
    		} // else verifica se variavel existe
            tokens.clear();


        }
        else if(codigo.get(linha).matches(IF)){
        	System.out.println("IF");
            Escopo controleDeFluxo = new Escopo("IF", linha+1);
            int contLinha = 0;
            for(contLinha = linha; contLinha < codigo.size();contLinha++){
                if(codigo.get(contLinha).matches(fimEscopo)){
                    controleDeFluxo.setFim(contLinha+1);
                    pilha.push(controleDeFluxo);
                    break;
                }
            }
            if(contLinha == codigo.size()){
                System.out.println("erro no codigo");
            }
            tokens.addAll(this.procuraTokens(codigo.get(linha),Interpretador.espacoEmBranco + "|IF|FAÇA"));

            for(int cont = 0; cont < tokens.size(); cont++) System.out.println("'" + tokens.get(cont) + "'");

        }
        else if(codigo.get(linha).matches(laco)){
        	//codigo para laço
        	System.out.println("laço");
        }
        else if(codigo.get(linha).matches(ELSE)){
            System.out.println("ELSE");
        }
        else if(codigo.get(linha).matches(fimEscopo)){

        }
        else if(codigo.get(linha).matches(comandoDeEntrada)){

        }
        else if(codigo.get(linha).matches(comandoDeSaida)){
            System.out.println("comando de saida");
            tokens.addAll(this.procuraTokens(codigo.get(linha), Interpretador.espacoEmBranco + "|;"));
            if(tokens.get(0).equals("MOSTRAR")){
                System.out.println("comando valido");
                //for(int cont = 1; cont < tokens.size(); cont++){ System.out.print(tokens.get(cont) + " "); }
                for(int cont = 1; cont < tokens.size(); cont++){
                    if(tokens.get(cont).startsWith("$")){
                        //System.out.println(cont + " é variavel" + tokens.get(cont).length());
                        String aux = tokens.get(cont).replace("$","");
                        //System.out.println(aux + aux.length());
                        if(this.variavelJaExiste(aux) != null){
                            if(this.variavelJaExiste(aux).getInicializada()){
                                System.out.println("variavel ja existe");
                                tokens.set(cont, String.valueOf(this.variavelJaExiste(aux).getValor()));
                                System.out.println(tokens.get(cont));
                            }else{
                                System.out.println("variavel nao inicializada, impossivel imprimir");
                                System.exit(0);
                            }
                        }else{
                            System.out.print("variavel nao existe");
                            System.exit(0);
                        }
                    }
                }
                for(int cont = 1; cont < tokens.size(); cont++){
                    System.out.print(tokens.get(cont) + " ");
                }
                System.out.println();
            }
        }
        else{
    		System.out.println("erro linha " + (linha+1));
            System.exit(0);
    	} // else verificacao de qual expressao regular se trata
	} // metodo interpretaLinha

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
    	//if(tokens.length > 1)
		// percorre o vetor de tokens, procurando por espacos em branco novamente
		for(int indice = 0; indice < tokens.length; indice++){
			if(tokens[indice].length() > 0){
                subTokens.add(tokens[indice]);
			}
        }
		//depuracao
		//for(int i = 0; i < subTokens.size(); i++){ System.out.println("'" + subTokens.get(i) + "'" + " tamanho" + subTokens.get(i).length());}
		return subTokens;
    }
    // retorna true se variavel ja existe, else retorna falso
    public Variavel variavelJaExiste(String var){
    	//System.out.println("verificando se variavel ### " + var + " ### ja existe");
		for(int cont = 0; cont < variavel.size(); cont++)
			if(var.equals(variavel.get(cont).getNome()))
				return variavel.get(cont);
		return null;
	}
    // percorre o ArrayList de Variavel imprimindo nome e valor de cada variavel
    public void imprimeVariaveis(){
    	System.out.println("\n\t###########################################");
    	System.out.println("\t########## Variáveis do programa ##########");
    	for(int indice = 0; indice < this.variavel.size(); indice++){
        	System.out.println("\t########## -->  " + this.variavel.get(indice).getNome() + " = " + this.variavel.get(indice).getValor());
        }
    	System.out.println("\t###########################################");
    }

    // metodo que procura os escopos do programa
    public void procuraEscopos(ArrayList<String> arquivo){
        for(int cont = 0; cont < arquivo.size(); cont++){

        }
    }





    //percorre o arquivo procurando laços, controladores de fluxo, operações, comandos de saída...
    public ArrayList<String> procuraErros(ArrayList<String> arquivo){
        ArrayList<String> erro = new ArrayList<String>();
        for(int cont = 0; cont < arquivo.size(); cont++){
            if(arquivo.get(cont).matches(inicioCodigo)){
                Escopo newEscopo = new Escopo("programa", cont+1);
                pilha.push(newEscopo);
            }else if(arquivo.get(cont).matches(fimEscopo)){
                    pilha.peek().setFim(cont+1);
            }else if(arquivo.get(cont).matches(IF)){
                Escopo newEscopo = new Escopo("IF", cont+1);
                pilha.push(newEscopo);
            }else if(arquivo.get(cont).matches(laco)){
                Escopo newEscopo = new Escopo("laço", cont+1);
                pilha.push(newEscopo);
            }else if(   arquivo.get(cont).matches(declaracaoDeVariavel) ||
                        arquivo.get(cont).matches(declaracaoDeVariavelComAtribuicao) ||
                        arquivo.get(cont).matches(atribuicaoComExpressao) ||
                        arquivo.get(cont).matches(comandoDeSaida) ||
                        arquivo.get(cont).matches(comandoDeEntrada)
                    ){

            }else{
                erro.add("erro na linha " + (cont+1));
            }
        }
        return erro;
    }
    public void imprimeErros(ArrayList<String> erro){
        for(int cont = 0; cont < erro.size(); cont++){
            System.out.println(erro.get(cont));
        }
        System.exit(0);
    }
    public ArrayList<String> separaPalavrasArquivo(ArrayList<String> arquivo){
        ArrayList<String> palavrasArquivo = new ArrayList<String>();
        for(int cont = 0; cont < arquivo.size(); cont++){
            System.out.println(arquivo.get(cont));
            palavrasArquivo.addAll(this.procuraTokens(arquivo.get(cont), Interpretador.espacoEmBranco));
        }
        return palavrasArquivo;
    }
    public void imprimePalavras(ArrayList<String> palavra){
        for(int cont = 0; cont < palavra.size(); cont++){
            System.out.println(palavra.get(cont));
        }
    }
    public String concatenaPalavrasArquivo(ArrayList<String> arquivo){
        String s = "";
        for(int cont = 0; cont < arquivo.size(); cont++){
            s = s + arquivo.get(cont);
        }
        return s;
    }
    public void imprimelinhasConcatenadas(String arquivo){
    	System.out.println(arquivo);
    }
    public String concatenaLinhasArquivo(ArrayList<String> arquivo){
        String s = "";
        for(int cont = 0; cont < arquivo.size(); cont++){
            s = s + " " + arquivo.get(cont);
        }
        return s;
    }
}
