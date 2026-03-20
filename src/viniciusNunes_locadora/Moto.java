package viniciusNunes_locadora;

public class Moto extends Veiculo {
    private int cilindrada;

    public Moto(String marca,String modelo, int anoFabricacao,double valorAvaliado, double valorDiaria, String placa, int cilindrada) {
        super(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }
    
    @Override
    public double calcularSeguro() {
        return (getValorAvaliado() * 0.11) / 365;
    }
}
