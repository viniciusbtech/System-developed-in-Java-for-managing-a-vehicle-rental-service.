package viniciusNunes_locadora;

public class Carro extends Veiculo {
	
	
	    private int tipo;

	    public Carro(String marca,String modelo, int anoFabricacao,double valorAvaliado, double valorDiaria, String placa, int categoria) {
	        super(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa);
	        this.tipo = categoria;
	    }

	    public int getTipo() {
	        return tipo;
	    }

	    @Override
	    public double calcularSeguro() {
	        return (getValorAvaliado() * 0.03) / 365;
	    }
    
    
    

}
