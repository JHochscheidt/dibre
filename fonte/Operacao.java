class Operacao{
		
	public static double operacao(double operando1, double operando2, String operacao){
		if(operacao.equals("SUBTRAI"))
			return operando1 - operando2;
		else if(operacao.equals("SOMA"))
			return operando1 + operando2;
		else if(operacao.equals("MULTIPLICA"))
			return operando1 * operando2;
		else if(operacao.equals("DIVIDE")){
			if(operando2 == 0){
				// retornar null ou false, pois operacao nao é possivel
			}
			return operando1/operando2;
		}
		else
			return 0;
		
	}
}
