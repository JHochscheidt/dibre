/* Código copiado dos arquivos disponibilizados pelo professor via moodle
 * 
 * Este código faz a leitura do arquivo fonte a ser interpretado,
 * e chama os métodos que fazem o reconhecimento dos comandos da linguagem .dibre
 * 
 * 
 */ 
import java.util.ArrayList;
class Interpretador {
    public String linhas[];
	ArrayList<Variavel> variavel = new ArrayList<Variavel>();
		
    public void interpreta(String l[]) {
        this.linhas = l;
        for(int i = 0; i < this.linhas.length; i++) {
            if(this.linhas[i] != null) {
                // TODO: interpretar a linha
                System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
                this.interpretaLinha(this.linhas[i]);
			}
        }   
    }
    
    // método que interpreta uma linha específica
    public void interpretaLinha(String linha){
		String tokens[];
		tokens = linha.split("double");
		
		// garante que tenha somente 2 strings na linha ???????????????????
		if(tokens.length == 2){
			System.out.println("DECLARAÇÃO");
			Variavel newVar = new Variavel();
			newVar.validarDeclaracao(tokens);
			//fazer metodo que verifica se realmente é uma declaracao e se está correta			
		}
		
		
	}    
    
    
    
}
