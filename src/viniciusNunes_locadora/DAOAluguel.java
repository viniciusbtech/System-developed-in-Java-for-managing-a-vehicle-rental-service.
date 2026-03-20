package viniciusNunes_locadora;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOAluguel {
    
    private Connection connection;
    
    private static final String URL = "jdbc:mysql://localhost:3306/Locadora_Vinicius";
    private static final String USER = "root";
    private static final String PASSWORD = "vinicinsql";
    
    
    public DAOAluguel() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


   

    public boolean isVeiculoAlugado(String placa) throws SQLException {
        String query = "SELECT COUNT(*) FROM aluguel WHERE placa = ? AND fechado = false";
        try ( 
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Locadora_Vinicius", "root", "vinicinsql");
        		PreparedStatement statement = connection.prepareStatement(query)) {
       
        	statement.setString(1, placa);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                } else {
                    return false;
                }
            }
        }
    }

    public void inserirAluguel(Aluguel aluguel) throws SQLException {
        String query = "INSERT INTO aluguel (placa, cpf_cliente, datas, dias, fechado) VALUES (?, ?, ?, ?, ?)";
        try ( Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Locadora_Vinicius", "root", "vinicinsql");
        		PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, aluguel.getVeiculo().getPlaca());
            statement.setInt(2, aluguel.getCliente().getCpf());
            statement.setDate(3, new java.sql.Date(aluguel.getData().getTime()));
            statement.setInt(4, aluguel.getDias());
            statement.setBoolean(5, aluguel.isFechado());
            statement.executeUpdate();
        }
    }
    
    
    public void registrarDevolucao(String placa) throws SQLException {
        String updateQuery = "UPDATE aluguel SET fechado = true WHERE placa = ? AND fechado = false";
        try (
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, placa);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Devolução registrada com sucesso para o veículo com placa: " + placa);
            } else {
                System.out.println("Não foi possível registrar a devolução. Veículo já pode ter sido devolvido ou não estava alugado.");
            }
        }
    }
    
    
    
}



