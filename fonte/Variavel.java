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
	
	
	public void declararVariavel(String linha){
		String[] tokens;
		System.out.println(linha);
		tokens = linha.split("[ \t\nx0B\f\r]"); // quebra a linha com qualquer tipo de espaço em branco
		if(tokens.length > 0){
			for(int indice = 0;indice < tokens.length; indice++){
				//se entrar neste if, significa que é o nome da variavel
				//System.out.println(indice + "'"+tokens[indice]+"'"+  "\ttamanho" + tokens[indice].length());
				if(tokens[indice].length() != 0 && !tokens[indice].equals("double") && !tokens[indice].equals(";")){
					this.setNome(tokens[indice]);
					//System.out.println(tokens[indice]);	// imprime o nome da variavel
				}
			}
				
		}
	}
}
