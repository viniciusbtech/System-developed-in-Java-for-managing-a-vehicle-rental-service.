package viniciusNunes_locadora;

public class Caminhao extends Veiculo {
	
    private double capacidadeCarga;

    public Caminhao(String marca,String modelo, int anoFabricacao,double valorAvaliado, double valorDiaria, String placa, double capacidadeCarga) {
    	 super(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa);
        this.capacidadeCarga = capacidadeCarga;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public void setCapacidadeCarga(double capacidadeCarga) {
        this.capacidadeCarga = capacidadeCarga;
    }
    @Override
    public double calcularSeguro() {
        return (getValorAvaliado() * 0.08) / 365;
    }
    

}
