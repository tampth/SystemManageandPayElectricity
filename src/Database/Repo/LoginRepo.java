package Database.Repo;

import Database.DatabaseConnection;
import Model.Encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepo {
    public static boolean Login(String userName, String password) throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * FROM USER_ACCOUNT WHERE Username=? ";
        String pass = Encryption.SHA1(password);
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, userName);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            if(!rst.getString(1).equals(userName)){
                return false;
            }
            String pwd = rst.getString(2);
            if (pwd.equals(pass)) {
                return true;
            }
        }
        return false;

    }

    public static boolean AccountRole(String username)throws ClassNotFoundException,SQLException {
        String SQL = "SELECT * FROM USER_ACCOUNT WHERE Username=?";
        Integer role = 1;
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, username);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            Integer type = rst.getInt(3);
            if (type.equals(1)) {
                return true;
            }
        }
        return false;
    }
}
