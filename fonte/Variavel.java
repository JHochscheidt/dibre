import java.util.ArrayList;

/**
 * Class Variavel
 */
public class Variavel {

	private String nome;
	private double valor;
	private boolean inicializada;
	
	public void setNome (String n){
		this.nome = n; 
	}
	public String getNome (){
		return this.nome; 
	}

	public void setValor (double d){
		this.valor = d; 
	}
	public double getValor (){
		return this.valor; 
	}
	
	public void setInicializada(boolean b){
		this.inicializada = b;
	}
	public boolean getInicializada(){
		return this.inicializada;
	}
	
}
