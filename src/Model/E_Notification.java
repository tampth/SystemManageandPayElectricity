package Model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.sql.ResultSet;
import java.sql.SQLException;

public class E_Notification {
    private String ID = "";
    private String Detail = "";
    private LocalDate DateCreated = LocalDate.from(Instant.EPOCH);
    private LocalDate DateSent = LocalDate.from(Instant.EPOCH);
    private String EditBy="";


    public E_Notification() {
    }

    public E_Notification(String ID, LocalDate dateCreated, LocalDate dateSent, String editBy, String detail) {
        this.ID = ID;
        this.Detail = detail;
        this.EditBy = editBy;
        this.DateCreated = dateCreated;
        this.DateSent = dateSent;
    }
    public static E_Notification fromResultSet(ResultSet resultSet) throws SQLException {
        E_Notification notification = new E_Notification();
        notification.ID = resultSet.getString("NO_ID");
        notification.DateCreated = resultSet.getDate("DateCreated").toLocalDate();
        notification.DateCreated = resultSet.getDate("DateSent").toLocalDate();
        notification.Detail = resultSet.getString("Detail");
        notification.EditBy = resultSet.getString("NameAD");
        return notification;
    }
    public String getID() {
        return ID;
    }

    public String getDetail() {
        return Detail;
    }

    public String getEditBy() {
        return EditBy;
    }

    public LocalDate getDateCreated() {
        return DateCreated;
    }

    public LocalDate getDateSent() {
        return DateSent;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public void setEditdBy(String edittedBy) {
        EditBy = edittedBy;
    }

    public void setDateCreated(LocalDate dateCreated) {
        DateCreated = dateCreated;
    }

    public void setDateSent(LocalDate dateSent) {
        DateSent = dateSent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof E_Notification)) return false;
        E_Notification that = (E_Notification) o;
        return getID().equals(that.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }
}
