package viniciusNunes_locadora;

import java.util.Date;

public class Aluguel {

    private Veiculo veiculo;
    private Cliente cliente;
    private Date data;
    private int dias;
    private boolean fechado;

    // Outros atributos e métodos

    public Aluguel(Veiculo veiculo, Cliente cliente) {
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.fechado = false; // Inicializa o aluguel como aberto
        // Inicializar outros atributos
    }

    public Aluguel(Veiculo veiculo, Cliente cliente, Date data, int dias) {
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.data = data;
        this.dias = dias;
        this.fechado = false; // Inicializa o aluguel como aberto
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Date getData() {
        return data;
    }

    public int getDias() {
        return dias;
    }

    public boolean isFechado() {
        return fechado;
    }

    public void setFechado(boolean fechado) {
        this.fechado = fechado;
    }

    public double calcularValorTotal() {
        double valorDiaria = veiculo.getValorDiaria();
        double seguro = calcularSeguro();

        double valorTotal = (valorDiaria + seguro) * dias;
        return valorTotal;
    }

    private double calcularSeguro() {
        double valorBem = veiculo.getValorAvaliado();
        double percentualSeguro = 0;

        if (veiculo instanceof Carro) {
            percentualSeguro = 0.03; // 3% para carros
        } else if (veiculo instanceof Caminhao) {
            percentualSeguro = 0.08; // 8% para caminhões
        } else if (veiculo instanceof Onibus) {
            percentualSeguro = 0.2; // 20% para ônibus
        } else if (veiculo instanceof Moto) {
            percentualSeguro = 0.11; // 11% para motos
        }

        return (valorBem * percentualSeguro) / 365;
    }
}
