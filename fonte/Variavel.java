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
	
	// apenas declaracao de variavel
	public void declararVariavel(String linha, ArrayList<Variavel> variavel){
		String[] tokens;
		System.out.println(linha);
		tokens = linha.split(Interpretador.espacoEmBranco); // quebra a linha com qualquer tipo de espaço em branco
		if(tokens.length > 1){
			//System.out.println("aqui");
			for(int indice = 0;indice < tokens.length; indice++){
				//se entrar neste if, significa que é o nome da variavel
				//System.out.println(indice + "'"+tokens[indice]+"'"+  "\ttamanho" + tokens[indice].length());
				if(tokens[indice].length() != 0 && !tokens[indice].equals("double") && !tokens[indice].equals(";")){
					Variavel newVar = new Variavel();
					newVar.setNome(tokens[indice]);
					variavel.add(newVar);
					//System.out.println(tokens[indice]);	// imprime o nome da variavel
				}
			}	
		}
	}
	
	// declaracao com atribuicao de valor a variavel
	public void declararVariavelComAtribuicao(String linha, ArrayList<Variavel> variavel){
		String[] tokens;
		System.out.println(linha);
		tokens = linha.split(Interpretador.espacoEmBranco);
		ArrayList<String>subTokens = new ArrayList<String>();		
		if(tokens.length > 1){
			int indice, indIndice;
			// percorre a linha procurando por tokens diferente de caracteres e palavras reservadas e joga pra dentro de um arraylist
			for(indice = 0; indice < tokens.length; indice++){
				//System.out.println("valor  " + indice + "    " + tokens[indice] + "tamanho   " +  tokens[indice].length()	);
				if(tokens[indice].length() > 0 && !tokens[indice].equals("double") && !tokens[indice].equals("=") && !tokens[indice].equals(";")){
					subTokens.add(tokens[indice]);
				}
			}
			// percorre o arraylist onde estao o nome das variaveis e seus valores
			for(int cont = 0; cont < subTokens.size();cont = cont+2){
				System.out.println(subTokens.get(cont));
				System.out.println(subTokens.get(cont+1));
				Variavel newVar = new Variavel();
				newVar.setNome(subTokens.get(cont));
				newVar.setValor(Double.parseDouble(subTokens.get(cont+1)));
				variavel.add(newVar);
				System.out.println("Nome " + newVar.getNome() + "Valor" + newVar.getValor());	
			}
		}
	}
}
