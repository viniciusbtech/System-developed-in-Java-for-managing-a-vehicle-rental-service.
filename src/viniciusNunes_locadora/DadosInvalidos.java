package viniciusNunes_locadora;


public class DadosInvalidos extends Exception 

{
	public DadosInvalidos()
	{
		super("Dados inválidos. Números não podem ser negativos e strings não podem ser nulas ou vazias.");
	}
}
