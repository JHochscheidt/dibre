import java.util.ArrayList;

/**
 * Class Variavel
 */
public class Variavel {

	//
	//	Atributos
	//
	private String nome;
	private double valor;
	
	//
	// 	Métodos
	//
	public void setNome (String n) { this.nome = n; }
	public String getNome () { return this.nome; }

	public void setValor (double d) { this.valor = d; }
	public double getValor () { return this.valor; }
	
	public void declararVariavel(String nome, double valor){
		this.setNome(nome);
		this.setValor(valor);		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	// apenas declaracao de variavel
	 /*public Variavel declararVariavel(ArrayList<String> tokens, ArrayList<Variavel> variavel){
		if(tokens.size() > 1){
			//System.out.println("aqui");
			for(int indice = 0;indice < tokens.size(); indice++){
				//se entrar neste if, significa que é o nome da variavel
				//System.out.println(indice + "'"+tokens[indice]+"'"+  "\ttamanho" + tokens[indice].length());
				if(tokens.get(indice).length() != 0 && !tokens.get(indice).equals("double") && !tokens.get(indice).equals(";")){
					/*if(this.verificaSeExisteVariavel(variavel,){
						System.out.println("variavel ja existe");
					}else{
						System.out.println("variavel ainda nao existe, pode declarar");
						Variavel newVar = new Variavel();
						newVar.setNome(tokens[indice]);
						variavel.add(newVar);
					}
					//System.out.println(tokens[indice]);	// imprime o nome da variavel
				}
			}	
		}
		return null;
	}
	
	
	// declaracao com atribuicao de valor a variavel
	public Variavel declararVariavelComAtribuicao(ArrayList<String> tokens, ArrayList<Variavel> variavel){
		if(tokens.size() > 1){
			int indice, indIndice;
			// percorre a linha procurando por tokens diferente de caracteres e palavras reservadas e joga pra dentro de um arraylist
			for(indice = 0; indice < tokens.size(); indice++){
				System.out.println("valor  " + indice + "    " + tokens.get(indice) + "tamanho   " +  tokens.get(indice).length()	);
				if( !tokens.get(indice).equals("double") && !tokens.get(indice).equals("=") && !tokens.get(indice).equals(";")){
					
				}
			}
			// percorre o arraylist onde estao o nome das variaveis e seus valores
			//for(int cont = 0; cont < subTokens.size();cont = cont+2){
				//System.out.println(subTokens.get(cont));
				//System.out.println(subTokens.get(cont+1));
				/*if(this.verificaSeExisteVariavel(variavel, subTokens.get(cont))){ // já existe variavel com subTokens.get(cont) nome
					return null;
				}else{
					Variavel newVar = new Variavel();
					newVar.setNome(subTokens.get(cont));
					newVar.setValor(Double.parseDouble(subTokens.get(cont+1)));
					return newVar;
				}
				//System.out.println("Nome " + newVar.getNome() + "Valor" + newVar.getValor());	
			//}
		}
		return null;
	}*/
}
