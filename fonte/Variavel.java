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
	
	
	public void validarDeclaracao(String[] tokens){
		
		if(tokens[0] == null || tokens[0].trim().length() == 0){
			//validou parte anterior ao token DOUBLE - palavra reservada - 
			System.out.println("STRING vazia");
			tokens = tokens[1].split(" ");
			for(int cont = 0; cont < tokens.length; cont++){
				if(tokens[cont] == null || tokens[cont].trim().length() == 0){
					System.out.println(cont + "token vazio");
				}else{
					
				}
				System.out.println(cont + "'" + tokens[cont] + "'");
			}
		}
	}
}
