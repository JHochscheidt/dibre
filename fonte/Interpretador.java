
/* Código copiado dos arquivos disponibilizados pelo professor via moodle
 * Este código faz a leitura do arquivo fonte a ser interpretado,
 * e chama os métodos que fazem o reconhecimento dos comandos da linguagem .dibre
 */

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.*;
import java.util.Scanner;

class Interpretador {
    private ArrayList<String> codigo;
    public static final String espacoEmBranco = "[ \b\t\n\f\r]";
	public ArrayList<Variavel> variavel = new ArrayList<Variavel>();
    ArrayList<String> tokens = new ArrayList<String>();
	Stack<Escopo> pilha = new Stack<Escopo>();
    Scanner leitor = new Scanner(System.in);
    /* Expressões regulares usadas para interpretação de cada linha do código
     * As expressões dirão como funcionará cada tipo de comando
     */

	String inicioCodigo = "\\s{0,}INICIO\\s{0,}";
	String fimEscopo = "\\s{0,}FIM\\s{0,}";
	String tipoDeVariavel = "varReal";
	String nomeDeVariavel = "[A-Za-z]{1}\\w{0,}";
    String valor = "\\-?\\d{1,}\\.?\\d{0,}";
    String pontoEVirgula = "\\s{0,};\\s{0,}";
    String operadoresAritmeticos = "(SOMA|SUBTRAI|MULTIPLICA|DIVIDE)";
    String operadoresRelacionais = "(MAIOR|MENOR|IGUAL|MAIORouIGUAL|MENORouIGUAL|DIFERENTE)";
    String operadoresLogicos = "(OU|E)";
    String operacaoAritmetica = "(" + nomeDeVariavel + "|" + valor + ")\\s{1,}" + operadoresAritmeticos + "\\s{1,}(" + nomeDeVariavel + "|" + valor + ")\\s{0,}";
    String operacaoRelacional = "(" + nomeDeVariavel + "|" + valor + ")\\s{1,}" + operadoresRelacionais + "\\s{1,}(" + nomeDeVariavel + "|" + valor + ")\\s{0,}";
    String operacaoLogica = "(" + operacaoAritmetica + "|" + operacaoRelacional + ")\\s{1,}" + operadoresLogicos + "\\s{1,}(" + operacaoAritmetica + "|" + operacaoRelacional + ")\\s{0,}";
    String declaracaoDeVariavel = "\\s{0,}" + tipoDeVariavel + "\\s{1,}" + nomeDeVariavel + pontoEVirgula;
    String declaracaoDeVariavelComAtribuicao = "\\s{0,}" + tipoDeVariavel + "\\s{1,}" + nomeDeVariavel + "\\s{0,}=\\s{0,}" + "(" + valor + "|" + nomeDeVariavel + ")" + pontoEVirgula;
    //String declaracao = declaracaoDeVariavel + "|" + declaracaoDeVariavelComAtribuicao ;
    String atribuicaoSimples = "\\s{0,}" + nomeDeVariavel + "\\s{0,}=\\s{0,}" + "(" + nomeDeVariavel + "|" + valor + ")" + pontoEVirgula;
    String atribuicaoComExpressao = "\\s{0,}" + nomeDeVariavel + "\\s{0,}=\\s{0,}" + operacaoAritmetica + pontoEVirgula;
    String atribuicao = atribuicaoSimples + "|" + atribuicaoComExpressao ;
    String se = "\\s{0,}IF\\s{1,}(" + operacaoAritmetica + "|" + operacaoRelacional + ")\\s{1,}FAÇA\\s{0,}";
    String senaoSe = "\\s{0,}ELSEIF\\s{1,}(" + operacaoAritmetica + "|" + operacaoRelacional + ")\\s{1,}FAÇA\\s{0,}";
    String senao = "\\s{0,}ELSE\\s{1,}FAÇA";
    String laco = "\\s{0,}ENQUANTO\\s{0,}(" + operacaoLogica + "|" + operacaoAritmetica + "|" + operacaoRelacional + ")\\s{0,}FAÇA\\s{0,}";
    String comandoDeSaida = "\\s{0,}MOSTRAR\\s{1,}[(\\$" + nomeDeVariavel + "|" + valor + "|\\w{1}\\s{0,}|\\S|\n)\\s{1,}]{0,}" + pontoEVirgula ;
    String comandoDeEntrada = "\\s{0,}LER\\s{1,}" + nomeDeVariavel + pontoEVirgula;
    String escopo = "(" + inicioCodigo + "|" + se + "|" + senaoSe + "|" + senao + "|" + laco + ")" ;
    String linhaVazia = "";
    // todas as linhas serão interpretadas através de expressão regular

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
    public void interpretaLinha(ArrayList<String> codigo, int linha) throws NumberFormatException{
    	//System.out.println("LINHA " + (linha+1));
        if(codigo.get(linha).matches(inicioCodigo)){
    		//System.out.println("inicio do codigo");
    		Escopo programa = new Escopo("programa", (linha+1));
    		pilha.push(programa);
    	}
    	else if(codigo.get(linha).matches(declaracaoDeVariavel)){
    		//System.out.println("declaracaoDeVariavel");
    		tokens.addAll(this.procuraTokens(codigo.get(linha),Interpretador.espacoEmBranco + "|" + tipoDeVariavel + "|,|;"));
    		//for(int cont = 0; cont < tokens.size(); cont++) System.out.println("'" + tokens.get(cont) + "'");
            for(int cont = 0; cont < tokens.size(); cont++){
                if(this.declararVariavel(tokens.get(cont),0, false) == null){
                    System.out.println("Erro na linha @@@ " + (linha+1) + " @@@ Variavel ### " + tokens.get(cont) + " ### já declarada");
                    System.exit(0);
                }
            }
            tokens.clear();
    	}
        else if(codigo.get(linha).matches(declaracaoDeVariavelComAtribuicao)){
    		//System.out.println("declaracaoDeVariavelComAtribuicao");
    		tokens.addAll(this.procuraTokens(codigo.get(linha), Interpretador.espacoEmBranco + "|varReal|=|;"));
            //for(int cont = 0; cont < tokens.size(); cont++) System.out.println("'" + tokens.get(cont) + "'");
            if(ehVariavel(tokens.get(1))){
                tokens.set(1, String.valueOf(this.variavelJaExiste(tokens.get(1)).getValor()));
            }
            if(this.declararVariavel(tokens.get(0),Double.parseDouble(tokens.get(1)),true) == null){
                System.out.println("Erro na linha @@@ " + (linha+1) + " @@@ Variavel ### " + tokens.get(0) + " ### já declarada");
			    System.exit(0);
            }
            tokens.clear();
        }
        else if(codigo.get(linha).matches(atribuicao)){
    		//System.out.println("atribuicao");
            tokens.addAll(this.procuraTokens(codigo.get(linha), Interpretador.espacoEmBranco + "|^" + operadoresAritmeticos + "|;|="));
            //for(int cont = 0; cont < tokens.size(); cont++){ System.out.println(cont + " '" + tokens.get(cont) + "'" + " size " + tokens.get(cont).length()); }
    		// verifica se a variavel a receber resultado da expressao existe
    	    if(this.variavelJaExiste(tokens.get(0)) != null){
                double operando1 = 0, operando2 = 0;
                Variavel aux = this.variavelJaExiste(tokens.get(0));
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
                					//System.out.println("valor operador 1 =" + operando1);
                				}else{
                					operando2 = this.variavelJaExiste(tokens.get(cont)).getValor();
                				}
                			}else{
                				System.out.println("Erro na linha @@@ " + (linha+1) + " @@@ Variavel ### " + tokens.get(cont) + " ### não existe ou não foi inicializada");
        		    			System.exit(0);
                			}
                		}else if(tokens.get(cont).matches("\\-?\\d{1,}\\.?\\d{0,}")){
                			//System.out.println("é valor");
                			if(cont == 1){
    	    					operando1 = Double.parseDouble(tokens.get(cont));
                			}else{
    	    					operando2 = Double.parseDouble(tokens.get(cont));
                			}
                		}else{
                			System.out.println("operadores invalidos");
                		}
                	}
                } // for de verificacao de variaveis ou valores
                if(tokens.size() > 2){
                    String operador = tokens.get(2);
                    if(operador.equals("SOMA") || operador.equals("SUBTRAI") || operador.equals("MULTIPLICA") || operador.equals("DIVIDE")){
                        aux.setValor(Operacao.operacaoAritmetica(operando1,operando2,operador));
                        aux.setInicializada(true);
                        //System.out.println(aux.getInicializada() + " " + aux.getValor() + " " + aux.getNome());
                    }else{
            			System.out.println("operador nao existe");
            			System.exit(0);
            	    }// else verifica se operador é valido
                }else{
                    aux.setValor(Operacao.operacaoAritmetica(operando1,operando2,"SOMA"));
                    aux.setInicializada(true);
                }
    		}else{
    			System.out.println("variavel @@@ " + tokens.get(0) + " @@@ nao existe");
                System.exit(0);
    		} // else verifica se variavel existe
            tokens.clear();
        }
        else if(codigo.get(linha).matches(se)){
            //System.out.println("\n\nIFFFFFFF\n\n");
            tokens.addAll(this.procuraTokens(codigo.get(linha),Interpretador.espacoEmBranco + "|IF|FAÇA"));
            //for(int cont = 0; cont < tokens.size(); cont++) System.out.println("'" + tokens.get(cont) + "'" + cont);
            if(verificaCondicao(tokens)){
                System.out.println("condicao válida");
            }
            tokens.clear();
        }
        else if(codigo.get(linha).matches(laco)){
        	//System.out.println("laço");
        }
        else if(codigo.get(linha).matches(senaoSe)){
            //System.out.println("ELSEIF");
        }
        else if(codigo.get(linha).matches(fimEscopo)){
            //System.out.println("fim escopo");
        }
        else if(codigo.get(linha).matches(comandoDeEntrada)){
            //System.out.println("comando de entrada");
            tokens.addAll(this.procuraTokens(codigo.get(linha), Interpretador.espacoEmBranco + "|;"));
            if(tokens.get(0).equals("LER")){
                //System.out.println("comando valido");
                if(ehVariavel(tokens.get(1))){
                    //System.out.println("variavel reconhecida");
                    String ler = leitor.nextLine();
                    System.out.println("leu " + ler);
                    try{
                        Double.parseDouble(ler);
                        Variavel aux = variavelJaExiste(tokens.get(1));
                        aux.setValor(Double.parseDouble(ler));
                        aux.setInicializada(true);
                    }catch(NumberFormatException e){
                        System.out.println("impossivel atribuir palavra a uma variavel varReal");
                        System.exit(0);
                    }
                }  /*
                    if(!ehVariavel(ler)){
                        Variavel aux = variavelJaExiste(tokens.get(1));
                        aux.setValor(Double.parseDouble(ler));
                        aux.setInicializada(true);
                    }*/
            }
            tokens.clear();
        }
        else if(codigo.get(linha).matches(comandoDeSaida)){
            //System.out.println("comando de saida");
            tokens.addAll(this.procuraTokens(codigo.get(linha), Interpretador.espacoEmBranco + "|;"));
            if(tokens.get(0).equals("MOSTRAR")){
                //System.out.println("comando valido");
                for(int cont = 1; cont < tokens.size(); cont++){
                    if(tokens.get(cont).startsWith("$")){
                        String aux = tokens.get(cont).replace("$","");
                        //System.out.println(aux + aux.length());
                        if(this.variavelJaExiste(aux) != null){
                            if(this.variavelJaExiste(aux).getInicializada()){
                                //System.out.println("variavel ja existe");
                                tokens.set(cont, String.valueOf(this.variavelJaExiste(aux).getValor()));
                                //System.out.println(tokens.get(cont));
                            }else{
                                System.out.println("variavel ### " + aux + " ### nao inicializada, impossivel imprimir");
                                System.exit(0);
                            }
                        }else{
                            System.out.println("variavel ### " + aux + " ### nao existe");
                            System.exit(0);
                        }
                    }
                }
                for(int cont = 1; cont < tokens.size(); cont++){
                    System.out.print(tokens.get(cont) + " ");
                }
                System.out.println();
            }
            tokens.clear();
        }
        else{
    		System.out.println("erro linha " + (linha+1));
            System.exit(0);
    	} // else verificacao de qual expressao regular se trata
	} // metodo interpretaLinha

    // metodo que verifica se os componentes de uma condicao (if, else if) estao corretas
    public boolean verificaCondicao(ArrayList<String> componente){
        if(componente.size() == 3){
            //System.out.println("verificando condicao...");
            for(int cont = 0; cont < componente.size(); cont++){
                if(cont % 2 == 0){
                    //System.out.printf("igual 0\n" + cont);
                    if(!ehVariavel(componente.get(cont))){
                        System.out.println(componente.get(cont) + " Não é variavel");
                        return false;
                    }
                }else if(cont % 2 == 1){
                    if(!(componente.get(cont).equals(operadoresAritmeticos) || componente.get(cont).equals(operadoresAritmeticos))){
                        return false;
                    }
                }
            }
        return true;
        }else{
            return false;
        }
    }

    // metodo que vai tentar declarar variavel
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

    // metodo que verifica se palavra é um valor ou uma variavel
    public boolean ehVariavel(String palavra) throws NumberFormatException{
        try{
            Double.parseDouble(palavra);
            return false; // retorna falso, pois é um valor e nao uma variavel
        }catch(NumberFormatException e){
            // é variavel
            //System.out.println("é nome de variavel");
            if(variavelJaExiste(palavra) != null && this.variavelJaExiste(palavra).getInicializada()){
                // variavel ja existe, pode usar
                return true;
                //System.out.println("variavel existe");
                //System.out.println("variavel ja inicializada, pode atribuir");
            }else{
                System.out.println("variavel @@@ " + palavra + " @@@ nao existe");
                System.exit(0);
                return false;
            }
        }
    }

    // metodo que verifica se os escopos do programa estao corretos
    public void verificaEscopos(ArrayList<String> arquivo){
        for(int cont = 0; cont < arquivo.size(); cont++){
            if(arquivo.get(cont).matches(escopo)){
                //System.out.println("é escopo. LINHA " + (cont+1));
                Escopo novo = new Escopo("Escopo", (cont+1));
                pilha.push(novo);
            }else if(arquivo.get(cont).matches(fimEscopo)){
                if(!pilha.isEmpty()){
                    pilha.pop();
                }else{
                    System.out.println("Fim de escopo sobrando");
                    System.exit(0);
                }
            }
        }
        if(!pilha.isEmpty()){
            System.out.println("Falta fechar escopo em alguma linha");
            System.exit(0);
        }
    }

    // metodo que procura erros no codigo
    public ArrayList<String> procuraErros(ArrayList<String> arquivo){
        ArrayList<String> erro = new ArrayList<String>();
        for(int cont = 0; cont < arquivo.size(); cont++){
            if(!(arquivo.get(cont).matches(inicioCodigo) || arquivo.get(cont).matches(fimEscopo) ||
                arquivo.get(cont).matches(declaracaoDeVariavel) || arquivo.get(cont).matches(declaracaoDeVariavelComAtribuicao) ||
                arquivo.get(cont).matches(atribuicaoComExpressao) || arquivo.get(cont).matches(comandoDeSaida) ||
                arquivo.get(cont).matches(comandoDeEntrada) || arquivo.get(cont).matches(atribuicao) ||
                arquivo.get(cont).matches(laco) || arquivo.get(cont).matches(se) || arquivo.get(cont).matches(senaoSe) ||
                arquivo.get(cont).matches(senao) || arquivo.get(cont).matches(linhaVazia)
            )){
                erro.add("erro na linha " + (cont+1)); // se entrar no if faz isso
            }
        }
        return erro;
    } // fim metodo

    //metodo percorre o arquivo mostrando na tela os erros do codigo
    public void imprimeErros(ArrayList<String> erro){
        if(erro.size() > 0){
            for(int cont = 0; cont < erro.size(); cont++){
                System.out.println(erro.get(cont));
            }
            System.exit(0);
        }
    }
} // fim da classe Interpretador
