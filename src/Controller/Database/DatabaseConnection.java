package Controller.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection extends DaoFactory {
    private static final String DB_DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "112367";


    public  Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url="jdbc:sqlserver://localhost:1433;"+ "databaseName=DBC;user=sa;password=112367";
        return DriverManager.getConnection(url);
    }

    @Override
    public CustomerDao getUserDao() {
        return new MysqlCustomerDao();
    }
    @Override
    public NotificationDao getNotificationDao() {return new MysqlNotificationDao();}
    @Override
    public BillDao getBillDao() {return new MysqlBillDao();}
}
