package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.OrderDetailsDAO;
import lk.ijse.computer_Shop.entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allOrderDeatils=new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT *FROM orderdetails");
        while (rst.next()){
            OrderDetails orderDetails=new OrderDetails(rst.getString("ordId"), rst.getString("itemId"),rst.getInt("qty"),rst.getDouble("unitPrice"));
            allOrderDeatils.add(orderDetails);
        }
        return allOrderDeatils;
    }

    @Override
    public boolean add(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orderdetails (ordId,itemId,qty,unitPrice) VALUES (?,?,?,?)",entity.getOrdId(),entity.getItemId(),entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
