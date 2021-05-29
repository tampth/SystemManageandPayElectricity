package dao;

import daoFactory.DaoFactory;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MysqlUserDao implements UserDao {


	private static final String 
	FIND_BY_LOGIN = "SELECT * FROM e_customer WHERE Username = ?";


	private static final String
	VALIDATE_LOGIN= "SELECT count(1) FROM user_account WHERE Username = ? AND Password_Login= ?";
	


	/**
	 * Method to find a user by login
	 * @param username the username that will be inserted
	 * @return user User find by the login, otherwise null
	 */
	public User findByLogin(String username) throws SQLException {
		Connection c = DaoFactory.getDatabase().getJDBCConnection();

		PreparedStatement pstmt = c.prepareStatement(FIND_BY_LOGIN);
		pstmt.setString(1, username);

		User user = null;
		ResultSet rset = pstmt.executeQuery();

		while (rset.next()) {
			user = createUser(rset);
		}

		pstmt.close();
		c.close();

		return user;
	}
	/**
	 * Method to valid a user by login
	 * @param user the user that will be enter
	 * @return true , otherwise null
	 */
	public boolean validateLogin(User user) throws SQLException{
		Connection c = DaoFactory.getDatabase().getJDBCConnection();

		PreparedStatement pstmt = c.prepareStatement(VALIDATE_LOGIN);
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPasswordLogin());

		ResultSet rset = pstmt.executeQuery();
		while(rset.next()){
			if(rset.getInt(1)==1){
				return true;
			}
		}
		return false;
	}
	
	/* method to create users **/
	private User createUser(ResultSet rset) throws SQLException{
		User user = new User(rset.getString("CUS_ID"));

		user.setUsername(rset.getString("Username"));
		user.setFullname(rset.getString("FullnameCUS"));
		user.setPhone(rset.getString("PhoneCUS"));
		user.setAddress(rset.getString("AddressCUS"));
		user.setNationalid(rset.getString("NationalID"));
		user.setGender(rset.getString("Gender"));
		user.setBirthday(rset.getDate("Birthday"));
		user.setRegion(rset.getString("Region"));
		user.setDateregister(rset.getDate("DateRegister"));
		
		return user;
	}
}
