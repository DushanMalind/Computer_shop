package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.OrderDetailsDAO;
import lk.ijse.computer_Shop.dao.custom.OrdersDAO;
import lk.ijse.computer_Shop.entity.OrderDetails;
import lk.ijse.computer_Shop.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> allOrders=new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT *FROM Orders");
        while (rst.next()){
            Orders orders=new Orders(rst.getString("ordId"), LocalDate.now(),rst.getString("cusId"));
            allOrders.add(orders);
        }
        return allOrders;

    }

    @Override
    public boolean add(Orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO `Orders`(ordId,date,cusId) VALUES(?,?,?)",entity.getOrdId(),entity.getDate(),entity.getCusId());
    }

    @Override
    public boolean update(Orders dto) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("this feature");
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT ordId FROM `Orders` WHERE ordID=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT ordId FROM `Orders` ORDER BY ordId DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("ordId").replace("OID-", "")) + 1)): "OID-001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
