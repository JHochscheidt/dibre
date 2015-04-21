class Escopo{

	private int inicio;
	private int fim;
	private String nome;

	
	public Escopo(String n, int i){
		this.nome = n;
		this.inicio = i;
	}
	
	public void setInicio(int i){
		this.inicio = i;
	}
	public int getInicio(){
		return this.inicio;
	}
	
	public void setFim(int f){
		this.fim = f;
	}
	public int getFim(){
		return this.fim;
	}
	
	public void setNome(String n){
		this.nome = n;
	}
	public String getNome(){
		return this.nome;
	}
	
}
