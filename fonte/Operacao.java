class Operacao{

	public static double operacaoAritmetica(double operando1, double operando2, String operacao){
		if(operacao.equals("SUBTRAI"))
			return operando1 - operando2;
		else if(operacao.equals("SOMA"))
			return operando1 + operando2;
		else if(operacao.equals("MULTIPLICA"))
			return operando1 * operando2;
		else if(operacao.equals("DIVIDE")){
			if(operando2 != 0){
				return operando1/operando2;
			}else{
				System.out.println("impossivel dividir por zero");
				System.exit(0);
				return 0;
			}
		}else{
			return 0;
		}
	}

	public static boolean operacaoRelacional(double operando1, double operando2, String operacao){
		if(operacao.equals("MAIOR"))
			return operando1 > operando2;
		else if(operacao.equals("MENOR"))
			return operando1 < operando2;
		else if(operacao.equals("IGUAL"))
			return operando1 == operando2;
		else if(operacao.equals("MAIORouIGUAL"))
			return operando1 >= operando2;
		else if(operacao.equals("MENORouIGUAL"))
			return operando1 <= operando2;
		else if(operacao.equals("DIFERENTE"))
			return operando1 != operando2;
		else
			return false;
	}


}
