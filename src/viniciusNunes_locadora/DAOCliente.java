package viniciusNunes_locadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOCliente {

    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/Locadora_Vinicius";
    private static final String USER = "root";
    private static final String PASSWORD = "vinicinsql";

    public DAOCliente() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirCliente(Cliente cliente) throws SQLException {
        // Primeiro, verifica se o cliente já existe no banco de dados
        Cliente clienteExistente = obterClientePorCpf(cliente.getCpf());
        
        if (clienteExistente != null) {
            throw new SQLException("Cliente já cadastrado no banco de dados. CPF: " + cliente.getCpf());
        }

        String query = "INSERT INTO cliente (cpf, nome) VALUES (?, ?)";

        // Verifique e estabeleça a conexão se necessário
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Falha ao estabelecer conexão com o banco de dados.", e);
            }
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cliente.getCpf());
            statement.setString(2, cliente.getNome());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir o cliente no banco de dados.", e);
        }
    }
    

    public Cliente pesquisarCliente(int cpf) {
       Cliente cliente = null;

       try {
          Connection con = Conexao.conectar();
          Statement stmt = con.createStatement();
          String comando = "SELECT * FROM clientes WHERE cpf = " + cpf;
          System.out.println(comando);
          ResultSet rs = stmt.executeQuery(comando);
          if (rs.next()) {
             String nome = rs.getString("nome");
             cliente = new Cliente(cpf, nome);
          }

          stmt.close();
          con.close();
       } catch (Exception var8) {
          var8.printStackTrace();
       }

       return cliente;
    }

    
    

    public Cliente obterClientePorCpf(int cpf) throws SQLException {
        String query = "SELECT * FROM cliente WHERE cpf = ?";

        // Verifique e estabeleça a conexão se necessário
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Falha ao estabelecer conexão com o banco de dados.", e);
            }
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cpf);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    return new Cliente(cpf, nome);
                } else {
                    return null; // Cliente não encontrado
                }
            }
        }
    }
    
    public void inserir(Cliente cliente) {
        try {
           Connection con = Conexao.conectar();
           Statement stmt = con.createStatement();
           String var10000 = cliente.getNome();
           String comando = "INSERT INTO clientes (nome, cpf) VALUES ('" + var10000 + "', '" + cliente.getCpf() + "')";
           System.out.println(comando);
           stmt.execute(comando);
           stmt.close();
           con.close();
        } catch (Exception var5) {
           var5.printStackTrace();
        }

     }

}