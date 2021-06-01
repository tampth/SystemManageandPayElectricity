package Repo;

import Model.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.E_Notification;
import java.sql.*;
import java.time.LocalDate;

public class NotificationsRepo {

    private final ObservableList<E_Notification> notifications = FXCollections.observableArrayList();

    private Connection connection = null;

    public NotificationsRepo() {
        try {
            loadNotificationsFromDB();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }


    private void loadNotificationsFromDB() throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String query = "SELECT NO_ID,DateCreated,DateSent, Detail,NameAD FROM E_NOTIFICATION JOIN E_ADMIN ON E_NOTIFICATION.EditedBy=E_ADMIN.AD_ID";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        notifications.clear();
        while (result.next()) {
            E_Notification resultNotification = E_Notification.fromResultSet(result);
            if (!notifications.contains(resultNotification)) {
                notifications.add(resultNotification);
            }
        }
        connection.close();
    }
    public ObservableList<E_Notification> getNotification() {
        return notifications;
    }

    public void addNotification(String id, LocalDate dCreated, LocalDate dSent, String editedBy, String detail) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO E_NOTIFICATION VALUES(?,?,?,?,?)");
        statement.setString(1, id);
        statement.setObject(2, dCreated.toString());
        statement.setObject(2, dSent.toString());
        statement.setString(4, editedBy);
        statement.setString(5,detail);
        statement.execute();
        connection.close();
        loadNotificationsFromDB();
    }

    public void deleteNotification(String notificationId) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM E_NOTIFICATION WHERE NO_ID=?");
        statement.setString(1, notificationId);
        statement.execute();
        connection.close();
        loadNotificationsFromDB();
    }
    public void updateNotification(E_Notification notification) throws SQLException, ClassNotFoundException {

        connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE E_NOTIFICATION SET DateSent=?,EditedBy=?,Detail=? WHERE NO_ID=?");
        statement.setObject(1, notification.getDateSent());
        statement.setString(2, notification.getEditBy());
        statement.setString(3, notification.getDetail());
        statement.setString(4, notification.getID());
        statement.execute();
        connection.close();

        loadNotificationsFromDB();
    }
    public E_Notification getNotificationById(String id) {
        for (E_Notification notification : notifications) {
            if (notification.getID().equals(id)) {
                return notification;
            }
        }
        return null;
    }
}
