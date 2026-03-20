package viniciusNunes_locadora;


//Source code is decompiled from a .class file using FernFlower decompiler.

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
private static final String URL = "jdbc:mysql://localhost:3306/dblocadora";
private static final String USER = "root";
private static final String PASSWORD = "03102003";

public Conexao() {
}

public static Connection conectar() {
   Connection con = null;

   try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dblocadora", "root", "03102003");
   } catch (Exception var2) {
      var2.printStackTrace();
   }

   return con;
}
}
