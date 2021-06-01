package Database.Repo;

import Database.DatabaseConnection;
import Model.Encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpRepo {
    private Connection connection = null;

    public boolean CheckUsername (String username) throws SQLException, ClassNotFoundException{
        PreparedStatement ps;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM USER_ACCOUNT WHERE Username =?";
            ps = DatabaseConnection.getConnection().prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next())
            {
                checkUser = true;
            }
        return checkUser;
    }
    public boolean addNewCustomer(String id, String name, String username, String password, String phoneNumber, String address, String nationalId, String gender, String birthday, String region) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO E_CUSTOMER VALUES(?,?,?,?,?,?,?,?,?)");
        statement.setString(1, id);
        statement.setString(2, name);
        statement.setString(3, username);
        statement.setString(4, phoneNumber);
        statement.setString(5, address);
        statement.setString(6, nationalId);
        statement.setString(7, gender);
        statement.setString(8, birthday);
        statement.setString(9, region);
        statement.execute();
        String pass= Encryption.SHA1(password);
        PreparedStatement statement1 = connection.prepareStatement("INSERT INTO USER_ACCOUNT VALUES(?,?,?)");
        statement1.setString(1, username);
        statement1.setString(2, pass);
        statement1.setInt(3, 1);
        statement1.execute();
        if(statement.executeUpdate()>0)
        {
            connection.close();
            return true;
        }
        return false;


    }

}
