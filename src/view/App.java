package view;

import java.sql.*;

public class App {
    public static void main2(String[] args) throws Exception {

        String driver = "org.postgresql.Driver";
        // resource.getString("driver");
        String url = "jdbc:postgresql://ec2-52-87-107-83.compute-1.amazonaws.com:5432/d622m7j3h054ts";
        // resource.getString("url");
        String usr = "ikwnggozhnxhvp";
        String pwd = "a933d68a3c21b7b24a2e05104117b487091c7b880a72fe25f4ae721fadbbae9a";

        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, usr, pwd);
        String sqlStatement = "SELECT * FROM Cliente";
        /*
         * PreparedStatement instruccion = con.prepareStatement(sqlStatement); int
         * result = instruccion.executeUpdate();
         */
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Sede");

        System.out.println("Hello, World!");
    }
}
