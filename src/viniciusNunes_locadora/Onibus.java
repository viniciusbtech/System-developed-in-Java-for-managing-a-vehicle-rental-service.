package viniciusNunes_locadora;

public class Onibus extends Veiculo {
    private int capacidadePassageiros;

    public Onibus(String marca,String modelo, int anoFabricacao,double valorAvaliado, double valorDiaria, String placa, int capacidadePassageiros) {
        super(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa);
        this.capacidadePassageiros = capacidadePassageiros;
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    public void setCapacidadePassageiros(int capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }
    
    @Override
    public double calcularSeguro() {
        return (getValorAvaliado() * 0.20) / 365;
    }


}
