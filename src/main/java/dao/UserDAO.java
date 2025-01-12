package dao;

import db.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isExist(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();   // Get Database Connection
        PreparedStatement ps = connection.prepareStatement("select * from user where email = ?"); // Prepared Statement is used to prepare a SQL Query so that we can execute it and also save from SQL Injection
        ps.setString(1 , email); //1 to refers to the placeholder in the prepared statement
        ResultSet rs = ps.executeQuery();  //Execution of the SQL Query
        while (rs.next()) {
            String mail = rs.getString(3); // While rs.next() is true fetch the mail using rs.getString
            if(mail.equals(email)) {
                return true; // Return true if the database value and this value is available
            }
        }
        return false;
    }

    public static int saveUser(User user) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("insert into user values(default,?,?)"); // If User is not available then save it in the Database using Prepared Statement
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        return ps.executeUpdate();  // Used for Insertion
    }
}
