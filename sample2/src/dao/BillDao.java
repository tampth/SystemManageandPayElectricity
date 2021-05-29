package dao;

import model.Bill;

import java.sql.SQLException;
import java.util.List;

public interface BillDao {

	List<Bill> all() throws SQLException;
}
