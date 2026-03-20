package viniciusNunes_locadora;
import java.sql.*;
import java.util.List;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class DAOVeiculo {
    
    private static final String URL = "jdbc:mysql://localhost:3306/Locadora_Vinicius";
    private static final String USER = "root";
    private static final String PASSWORD = "vinicinsql";
    
    
    public DAOVeiculo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void inserir(Veiculo veiculo) throws SQLException {
        String sql;
        
        // Construa a consulta SQL baseada no tipo de veículo
        if (veiculo instanceof Onibus) {
            sql = "INSERT INTO veiculos (marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, tipo, capacidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else if (veiculo instanceof Moto) {
            sql = "INSERT INTO veiculos (marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, tipo, cilindrada) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else if (veiculo instanceof Carro) {
            sql = "INSERT INTO veiculos (marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, tipo, categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else if (veiculo instanceof Caminhao) {
            sql = "INSERT INTO veiculos (marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, tipo, capacidadeCarga) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            throw new SQLException("Tipo de veículo desconhecido.");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Configura parâmetros comuns a todos os veículos
            statement.setString(1, veiculo.getMarca());
            statement.setString(2, veiculo.getModelo());
            statement.setInt(3, veiculo.getAnoDeFabricacao());
            statement.setDouble(4, veiculo.getValorAvaliado());
            statement.setDouble(5, veiculo.getValorDiaria());
            statement.setString(6, veiculo.getPlaca());

            // Configura o tipo de veículo
            if (veiculo instanceof Onibus) {
                statement.setInt(7, 4); // Tipo 4 para Ônibus
                statement.setInt(8, ((Onibus) veiculo).getCapacidadePassageiros()); // Capacidade específica para ônibus
            } else if (veiculo instanceof Moto) {
                statement.setInt(7, 1); // Tipo 2 para Moto
                statement.setInt(8, ((Moto) veiculo).getCilindrada()); // Cilindrada específica para motos
            } else if (veiculo instanceof Carro) {
                statement.setInt(7, 2); // Tipo 1 para Carro
                statement.setInt(8, ((Carro) veiculo).getTipo()); // Categoria específica para carros
            } else if (veiculo instanceof Caminhao) {
                statement.setInt(7, 3); // Tipo 3 para Caminhão
                statement.setDouble(8, ((Caminhao) veiculo).getCapacidadeCarga()); // Capacidade de carga específica para caminhões
            }

            statement.executeUpdate();
        }
    }


    public Veiculo pesquisar(String placa) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE placa = ?";
        Veiculo veiculo = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, placa);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    int anoFabricacao = resultSet.getInt("anoFabricacao");
                    double valorAvaliado = resultSet.getDouble("valorAvaliado");
                    double valorDiaria = resultSet.getDouble("valorDiaria");
                    int tipo = resultSet.getInt("tipo");

                    // Criar a instância do tipo correto de veículo com base no tipo armazenado
                    if (tipo == 1) {
                        veiculo = new Carro(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, tipo);
                    } else if (tipo == 2) {
                        veiculo = new Moto(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, 0);
                    } else if (tipo == 3) {
                        veiculo = new Caminhao(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, 0);
                    } else if (tipo == 4) {
                        veiculo = new Onibus(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, 0);
                    }
                }
            }
        }

        return veiculo;
    }
    
    


    public ArrayList<Veiculo> pesquisarOnibus(int capacidade) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE capacidade >= ?";
        ArrayList<Veiculo> onibusList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, capacidade);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    int anoFabricacao = resultSet.getInt("anoFabricacao");
                    double valorAvaliado = resultSet.getDouble("valorAvaliado");
                    double valorDiaria = resultSet.getDouble("valorDiaria");
                    String placa = resultSet.getString("placa");
                    int capacidadeOnibus = resultSet.getInt("capacidade");

                    Veiculo onibus = new Onibus(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, capacidadeOnibus);
                    onibusList.add(onibus);
                }
            }
        }

        return onibusList;
    }
    
    public ArrayList<Veiculo> pesquisarMoto(int cilindrada) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE  cilindrada >= ?";
        ArrayList<Veiculo> motoList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cilindrada);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    int anoFabricacao = resultSet.getInt("anoFabricacao");
                    double valorAvaliado = resultSet.getDouble("valorAvaliado");
                    double valorDiaria = resultSet.getDouble("valorDiaria");
                    String placa = resultSet.getString("placa");
                    int cilindradaMoto = resultSet.getInt("cilindrada");

                    Veiculo moto = new Moto(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, cilindradaMoto);
                    motoList.add(moto);
                }
            }
        }

        return motoList;
    }
    
    
    public ArrayList<Veiculo> pesquisarCaminhao(double capacidadeCarga) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE  capacidadeCarga >= ?";
        ArrayList<Veiculo> caminhaoList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, capacidadeCarga);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    int anoFabricacao = resultSet.getInt("anoFabricacao");
                    double valorAvaliado = resultSet.getDouble("valorAvaliado");
                    double valorDiaria = resultSet.getDouble("valorDiaria");
                    String placa = resultSet.getString("placa");
                    double capacidadeCargaCaminhao = resultSet.getDouble("capacidadeCarga");

                    Veiculo caminhao = new Caminhao(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, capacidadeCargaCaminhao);
                    caminhaoList.add(caminhao);
                }
            }
        }

        return caminhaoList;
    }
    
    public ArrayList<Veiculo> pesquisarCarro(int categoria) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE categoria = ?";
        ArrayList<Veiculo> carroList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoria);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    int anoFabricacao = resultSet.getInt("anoFabricacao");
                    double valorAvaliado = resultSet.getDouble("valorAvaliado");
                    double valorDiaria = resultSet.getDouble("valorDiaria");
                    String placa = resultSet.getString("placa");
                    int categoriaCarro = resultSet.getInt("categoria");

                    Veiculo carro = new Carro(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placa, categoriaCarro);
                    carroList.add(carro);
                }
            }
        }

        return carroList;
    }
    
    public static Veiculo obterVeiculoPorPlaca(String placa) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE placa = ?";
        Veiculo veiculo = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, placa);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    int anoFabricacao = resultSet.getInt("anoFabricacao");
                    double valorAvaliado = resultSet.getDouble("valorAvaliado");
                    double valorDiaria = resultSet.getDouble("valorDiaria");
                    String placaVeiculo = resultSet.getString("placa");
                    int tipo = resultSet.getInt("tipo");
                    
                    switch (tipo) {
                        case 2: // Carro
                        	
                        	
                            int categoriaCarro = resultSet.getInt("categoria");
                            veiculo = new Carro(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placaVeiculo, categoriaCarro);
                            break;
                            
                            
                        case 1: // Moto
                        	
                            int cilindradaMoto = resultSet.getInt("cilindrada");
                            veiculo = new Moto(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placaVeiculo, cilindradaMoto);
                            break;
                            
                        case 3: // Caminhão
                        	
                        	
                            double capacidadeCarga = resultSet.getDouble("capacidadeCarga");
                            veiculo = new Caminhao(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placaVeiculo, capacidadeCarga);
                            break;
                            
                            
                        case 4: // Ônibus
                            int capacidadeOnibus = resultSet.getInt("capacidade");
                            veiculo = new Onibus(marca, modelo, anoFabricacao, valorAvaliado, valorDiaria, placaVeiculo, capacidadeOnibus);
                            break;
                        default:
                            throw new SQLException("Tipo de veículo desconhecido.");
                    }
                }
            }
        }

        return veiculo;
    }
    
 
    


    



    




    
    
    
}
