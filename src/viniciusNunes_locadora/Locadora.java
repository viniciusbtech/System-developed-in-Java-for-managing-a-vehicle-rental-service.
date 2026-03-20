package viniciusNunes_locadora;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public abstract class Locadora {
	
	ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
	ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	ArrayList<Aluguel> alugueis = new ArrayList<Aluguel>();
	
	
	
	
	public abstract void inserir(Veiculo v) throws VeiculoJaCadastrado, DadosInvalidos, SQLException;
	
	
	public void inserir(Cliente c) throws ClienteJaCadastrado, DadosInvalidos, SQLException {
	    try {
	        this.pesquisarCliente(c.getCpf());
	    } catch (ClienteInexistente e) {
	        // Adiciona o cliente na lista
	        this.clientes.add(c);

	        // Insere o cliente no banco de dados
	        DAOCliente daoCliente = new DAOCliente();
	        daoCliente.inserirCliente(c);

	        return;
	    }

	    throw new ClienteJaCadastrado(c.getCpf());
	}
	


	
	protected Cliente pesquisarCliente(int cpf) throws ClienteInexistente, DadosInvalidos {
		for (Cliente c : clientes) {
			if (c.getCpf() == cpf) {
				return c;
			}
		}
		throw new ClienteInexistente(cpf);
	}
	
    public abstract Veiculo pesquisar(String placa) throws VeiculoInexistente,DadosInvalidos, SQLException;
 
	public abstract ArrayList<Veiculo> pesquisarMoto(int cilindrada) throws DadosInvalidos, SQLException;
	public abstract ArrayList<Veiculo> pesquisarCarro(int tipoCarro) throws DadosInvalidos, SQLException;
	public abstract ArrayList<Veiculo> pesquisarCaminhao(int carga) throws DadosInvalidos, SQLException;
	public abstract ArrayList<Veiculo> pesquisarOnibus(int passageiros) throws DadosInvalidos, SQLException;
	public abstract double calcularAluguel(String placa, int dias) throws VeiculoInexistente, DadosInvalidos, SQLException;
	
	public abstract boolean registrarAluguel(String placa, Date data, int dias, int cpf)
			throws VeiculoInexistente, ClienteInexistente, DadosInvalidos, VeiculoJaAlugado, SQLException;
	
	public abstract boolean registrarDevolucao(String placa)
			throws VeiculoInexistente, VeiculoNaoAlugado, DadosInvalidos, SQLException;

	public abstract void depreciarVeiculos(int tipo, double taxaDepreciacao) throws DadosInvalidos, SQLException;
	public abstract void aumentarDiaria(int tipo, double taxaAumento) throws DadosInvalidos, SQLException;
	public abstract double faturamentoTotal(int tipo, Date inicio, Date fim) throws DadosInvalidos, SQLException;
	public abstract int quantidadeTotalDeDiarias(int tipo, Date inicio, Date fim) throws DadosInvalidos, SQLException;
	


		
		
	}
	


	
	
	
	
	
	
	


