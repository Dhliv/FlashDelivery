package view;

import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {

        String driver = "org.postgresql.Driver";
        // resource.getString("driver");
        String url = "jdbc:postgresql://ec2-52-87-107-83.compute-1.amazonaws.com:5432/d622m7j3h054ts";
        // resource.getString("url");
        String usr = "ikwnggozhnxhvp";
        String pwd = "a933d68a3c21b7b24a2e05104117b487091c7b880a72fe25f4ae721fadbbae9a";

        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, usr, pwd);
        String sqlStatement = "SELECT * FROM Sede";
        /*
         * PreparedStatement instruccion = con.prepareStatement(sqlStatement); int
         * result = instruccion.executeUpdate();
         */
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sqlStatement);
        Object[] arr = new Object[3];

        while(rs.next()){
            arr[0] = rs.getInt(1);
            arr[1] = rs.getString(2);
            arr[2] = rs.getString(3); // This is da wae.
        }
    }
}
