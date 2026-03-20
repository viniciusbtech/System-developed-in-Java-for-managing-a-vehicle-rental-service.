package viniciusNunes_locadora;

public abstract class  Veiculo {
	
    private String marca;
    private String modelo;
    private int anoFabricacao;
    private double valorAvaliado;
    double valorDiaria;
    private String placa;
    protected int tipo;
    
    
    // public Veiculo(String marca,String modelo, int anoFabricacao,int placa, double valorDiaria,double valorAvaliado)
    public Veiculo(String marca,String modelo, int anoFabricacao,double valorAvaliado, double valorDiaria, String placa)
    {
    	this.marca=marca;
    	this.modelo=modelo;
    	this.anoFabricacao=anoFabricacao;
    	this.valorAvaliado=valorAvaliado;
    	this.valorDiaria=valorDiaria;
    	this.placa=placa;
    	
    }

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAnoDeFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(int anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public double getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(double valorDiaria) {
		this.valorDiaria = valorDiaria;
	}

	public double getValorAvaliado() {
		return valorAvaliado;
	}

	public void setValorAvaliado(double valorAvaliado) {
		this.valorAvaliado = valorAvaliado;
	}
    public abstract double calcularSeguro();

    public double calcularAluguel(int dias) {
        return (valorDiaria + calcularSeguro()) * dias;
    }

    public void aumentarDiaria(double percentual) {
        valorDiaria += valorDiaria * (percentual / 100);
    }

    public void reduzirDiaria(double percentual) {
        valorDiaria -= valorDiaria * (percentual / 100);
    }

    public void depreciarValor(double percentual) {
        valorAvaliado -= valorAvaliado * (percentual / 100);
    }

    public void aumentarValor(double percentual) {
        valorAvaliado += valorAvaliado * (percentual / 100);
    }
    
    public int getTipo() {
        return this.tipo;
     }

     public void setTipo(int tipo) {
        this.tipo = tipo;
     }
	
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    

}
