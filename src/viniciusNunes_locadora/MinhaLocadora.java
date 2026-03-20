package viniciusNunes_locadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class MinhaLocadora extends Locadora {
	
	
	private DAOVeiculo daoVeiculo = new DAOVeiculo();
    private DAOCliente daoCliente;
    private DAOAluguel daoAluguel;
    private List<Veiculo> veiculos = new ArrayList<>();
    
    
    private static final String URL = "jdbc:mysql://localhost:3306/Locadora_Vinicius";
    private static final String USER = "root";
    private static final String PASSWORD = "vinicinsql";

	
	
	
    public MinhaLocadora() {
        daoVeiculo = new DAOVeiculo();
        daoAluguel = new DAOAluguel();
        removerTodos();
        // Outras inicializações, se necessário
    }
    

    public ArrayList<Veiculo> pesquisarOnibus(int capacidade) throws SQLException {
        return daoVeiculo.pesquisarOnibus(capacidade);
    }
	
	
	

    public void removerTodos() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Locadora_Vinicius", "root", "vinicinsql");
             Statement statement = connection.createStatement()) {
             
            // Desabilitar checagem de chaves estrangeiras para evitar erros durante a exclusão
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            
            // Executar os comandos para limpar as tabelas
            statement.execute("DELETE FROM aluguel");
            statement.execute("DELETE FROM veiculos");
            statement.execute("DELETE FROM cliente");
            // Adicione outros comandos DELETE se houver outras tabelas

            // Habilitar checagem de chaves estrangeiras novamente
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratar exceções adequadamente
        }
    }
	

    @Override
    public void inserir(Veiculo v) throws VeiculoJaCadastrado, SQLException {
 

        // Verificar se o veículo já está cadastrado no banco de dados
        if (daoVeiculo.pesquisar(v.getPlaca()) != null) {
            throw new VeiculoJaCadastrado();
        }

        // Inserir o veículo no banco de dados
        daoVeiculo.inserir(v);

        // Adicionar o veículo à lista local (se necessário)
        veiculos.add(v);
    }
    
    
    @Override
    public ArrayList<Veiculo> pesquisarMoto(int cilindrada) throws DadosInvalidos, SQLException {
    	
        return daoVeiculo.pesquisarMoto(cilindrada);

    }
    
    @Override
    public ArrayList<Veiculo> pesquisarCaminhao(int capacidadeCarga) throws DadosInvalidos, SQLException {
        if (capacidadeCarga <= 0) {
            throw new DadosInvalidos();
        }
        return daoVeiculo.pesquisarCaminhao(capacidadeCarga);


    }
    
    @Override
    public ArrayList<Veiculo> pesquisarCarro(int tipoCarro) throws DadosInvalidos, SQLException {
        if (tipoCarro < 0 || tipoCarro > 3) {
            throw new DadosInvalidos();
        }
        return daoVeiculo.pesquisarCarro(tipoCarro);



    }
    

    
    
    @Override
    public double calcularAluguel(String placa, int dias) throws VeiculoInexistente, SQLException {
        // Recupera o veículo da base de dados
        Veiculo veiculo = DAOVeiculo.obterVeiculoPorPlaca(placa);
        
        if (veiculo == null) {
            throw new VeiculoInexistente("Veículo com a placa informada não existe.");
        }
        
        double valorDiaria = veiculo.getValorDiaria();
        double valorAvaliado = veiculo.getValorAvaliado();
        double seguroDiario;
        
        // Calcula o seguro diário com base no tipo de veículo
        if (veiculo instanceof Moto) {
            seguroDiario = (valorAvaliado * 0.11) / 365;
        } else if (veiculo instanceof Caminhao) {
            seguroDiario = (valorAvaliado * 0.08) / 365;
        } else if (veiculo instanceof Carro) {
            seguroDiario = (valorAvaliado * 0.03) / 365;
        } else if (veiculo instanceof Onibus) {
            seguroDiario = (valorAvaliado * 0.20) / 365;
        } else {
            throw new IllegalArgumentException("Tipo de veículo desconhecido.");
        }
        
        // Calcula o valor do aluguel
        double valorAluguel = (valorDiaria + seguroDiario) * dias;
        return valorAluguel;
        

    }
 
    
    @Override
    public Veiculo pesquisar(String placa) throws VeiculoInexistente, SQLException  {
        Veiculo veiculo = daoVeiculo.pesquisar(placa);
        
        if (veiculo == null) {
            throw new VeiculoInexistente("Veículo com placa " + placa + " não encontrado.");
        }
        
        return veiculo;

    }
    


    public boolean registrarAluguel(String placa, Date data, int dias, int cpf)
            throws VeiculoInexistente, ClienteInexistente, DadosInvalidos, VeiculoJaAlugado, SQLException {

        if (placa == null || placa.isEmpty() || dias <= 0) {
            throw new DadosInvalidos();
        }

        // Obter a data atual sem o componente de tempo
        Calendar calAtual = Calendar.getInstance();
        calAtual.set(Calendar.HOUR_OF_DAY, 0);
        calAtual.set(Calendar.MINUTE, 0);
        calAtual.set(Calendar.SECOND, 0);
        calAtual.set(Calendar.MILLISECOND, 0);
        Date dataAtual = calAtual.getTime();

        // Obter a data do aluguel sem o componente de tempo
        Calendar calAluguel = Calendar.getInstance();
        calAluguel.setTime(data);
        calAluguel.set(Calendar.HOUR_OF_DAY, 0);
        calAluguel.set(Calendar.MINUTE, 0);
        calAluguel.set(Calendar.SECOND, 0);
        calAluguel.set(Calendar.MILLISECOND, 0);
        Date dataAluguel = calAluguel.getTime();

        if (dataAluguel.before(dataAtual)) {
            throw new DadosInvalidos();
        }
        
        DAOCliente daoCliente = new DAOCliente(); // Criar uma instância de DAOCliente
        Cliente cliente = daoCliente.obterClientePorCpf(cpf); // Chamada do método a partir da instância
        if (cliente == null) {
            throw new ClienteInexistente(cpf);
        }


        // Verificar se o veículo existe
        Veiculo veiculo = DAOVeiculo.obterVeiculoPorPlaca(placa);        
        if (veiculo == null) {
            throw new VeiculoInexistente("Veículo com a placa " + placa + " não existe.");
        }

        // Verificar se o veículo já está alugado
        if (daoAluguel.isVeiculoAlugado(placa)) {
            throw new VeiculoJaAlugado("Veículo com a placa " + placa + " já está alugado.");
        }

        // Registrar o aluguel
        Aluguel aluguel = new Aluguel(veiculo, cliente, data, dias);
        daoAluguel.inserirAluguel(aluguel);

        return true;
    }
    
    
    @Override
    public boolean registrarDevolucao(String placa) throws VeiculoNaoAlugado, VeiculoInexistente, SQLException {
        DAOAluguel daoAluguel = new DAOAluguel();
        DAOVeiculo daoVeiculo = new DAOVeiculo();
        
        // Verificar se o veículo existe
        Veiculo veiculo = daoVeiculo.obterVeiculoPorPlaca(placa);
        if (veiculo == null) {
            throw new VeiculoInexistente("Veículo com a placa " + placa + " não existe.");
        }
        
        // Verificar se o veículo está alugado
        if (!daoAluguel.isVeiculoAlugado(placa)) {
            throw new VeiculoNaoAlugado("Veículo com a placa " + placa + " não está atualmente alugado.");
        }
        
        // Registrar a devolução do veículo
        daoAluguel.registrarDevolucao(placa);
        
        return true;
    }
    
    @Override
    public void aumentarDiaria(int tipoVeiculo, double percentual) throws SQLException {
        // Verifica se o percentual é válido
        if (percentual < 0) {
            throw new IllegalArgumentException("Percentual não pode ser negativo.");
        }

        // Percentual para aumento (ex.: 10% seria 0.10)
        double fatorAumento = (1 + percentual);

        // Prepara a consulta SQL
        String sql;
        if (tipoVeiculo == 0) {
            sql = "UPDATE veiculos SET valorDiaria = valorDiaria * ?";
        } else {
            sql = "UPDATE veiculos SET valorDiaria = valorDiaria * ? WHERE tipo = ?";
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Locadora_Vinicius", "root", "vinicinsql");
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Configura os parâmetros da consulta
            statement.setDouble(1, fatorAumento);
            if (tipoVeiculo != 0) {
                statement.setInt(2, tipoVeiculo);
            }

            // Executa a atualização
            int rowsUpdated = statement.executeUpdate();

            // Verifica se alguma linha foi atualizada
            if (rowsUpdated == 0) {
                System.out.println("Nenhum veículo atualizado. Verifique o tipo de veículo.");
            }
        }
    }



  
    
    public void depreciarVeiculos(int tipo, double taxaDepreciacao) throws DadosInvalidos, SQLException {
        if (tipo < 0 || tipo > 4) {
            throw new DadosInvalidos();
        }
        if (taxaDepreciacao < 0) {
            throw new DadosInvalidos();
        }

        // Construa a consulta SQL inicial
        String sql = "UPDATE veiculos SET valorAvaliado = valorAvaliado * (1 - ?)";

        // Adiciona condição de tipo se não for 0 (depreciar todos os veículos)
        if (tipo > 0) {
            sql += " WHERE tipo = ?";
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Configura o parâmetro da taxa de depreciação
            statement.setDouble(1, taxaDepreciacao);

            // Se o tipo for maior que 0, adicione o tipo como parâmetro na consulta
            if (tipo > 0) {
                statement.setInt(2, tipo); // Define o tipo do veículo no segundo parâmetro
            }

            // Executa a atualização
            int rowsUpdated = statement.executeUpdate();
            
      
        }
    }


    
    public double faturamentoTotal(int tipoVeiculo, Date dataInicio, Date dataFim) throws SQLException {
        double faturamentoTotal = 0.0;

        // Converter datas para o formato SQL
        java.sql.Date sqlDataInicio = new java.sql.Date(dataInicio.getTime());
        java.sql.Date sqlDataFim = new java.sql.Date(dataFim.getTime());

        // Construa a consulta SQL
        String sql = "SELECT v.valorDiaria, v.valorAvaliado, v.tipo, a.dias FROM aluguel a "
                   + "JOIN veiculos v ON a.placa = v.placa "
                   + "WHERE a.datas >= ? AND a.datas <= ?";

        if (tipoVeiculo > 0) {
            sql += " AND v.tipo = ?";
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Configura os parâmetros
            statement.setDate(1, sqlDataInicio);
            statement.setDate(2, sqlDataFim);
            if (tipoVeiculo > 0) {
                statement.setInt(3, tipoVeiculo);
            }

            ResultSet resultSet = statement.executeQuery();

            // Calcula o faturamento total
            while (resultSet.next()) {
                double valorDiaria = resultSet.getDouble("valorDiaria");
                double valorAvaliado = resultSet.getDouble("valorAvaliado");
                int dias = resultSet.getInt("dias");
                
                // Calcule o seguro diário
                double seguroDiario;
                int tipo = resultSet.getInt("tipo");
                if (tipo == 1) { // Moto
                    seguroDiario = (valorAvaliado * 0.11) / 365;
                } else if (tipo == 2) { // Carro
                    seguroDiario = (valorAvaliado * 0.03) / 365;
                } else if (tipo == 3) { // Caminhão
                    seguroDiario = (valorAvaliado * 0.08) / 365;
                } else if (tipo == 4) { // Ônibus
                    seguroDiario = (valorAvaliado * 0.20) / 365;
                } else {
                    continue; // Tipo desconhecido, ignorar
                }

                double valorAluguel = (valorDiaria + seguroDiario) * dias;
                faturamentoTotal += valorAluguel;
            }
        }

        return faturamentoTotal;
    }
    
    public int quantidadeTotalDeDiarias(int tipoVeiculo, Date dataInicio, Date dataFim) throws SQLException {
        int totalDiarias = 0;

        // Converter datas para o formato SQL
        java.sql.Date sqlDataInicio = new java.sql.Date(dataInicio.getTime());
        java.sql.Date sqlDataFim = new java.sql.Date(dataFim.getTime());

        // Construa a consulta SQL
        String sql = "SELECT SUM(a.dias) AS totalDiarias FROM aluguel a "
                   + "JOIN veiculos v ON a.placa = v.placa "
                   + "WHERE a.datas >= ? AND a.datas <= ?";

        if (tipoVeiculo > 0) {
            sql += " AND v.tipo = ?";
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Configura os parâmetros
            statement.setDate(1, sqlDataInicio);
            statement.setDate(2, sqlDataFim);
            if (tipoVeiculo > 0) {
                statement.setInt(3, tipoVeiculo);
            }

            ResultSet resultSet = statement.executeQuery();

            // Calcula a quantidade total de diárias
            if (resultSet.next()) {
                totalDiarias = resultSet.getInt("totalDiarias");
            }
        }

        return totalDiarias;
    }
    
    
    
    
    

}
