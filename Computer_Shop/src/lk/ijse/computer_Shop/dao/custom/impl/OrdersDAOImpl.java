package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.custom.OrderDetailsDAO;
import lk.ijse.computer_Shop.dao.custom.OrdersDAO;
import lk.ijse.computer_Shop.entity.OrderDetails;
import lk.ijse.computer_Shop.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Orders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Orders dto) throws SQLException, ClassNotFoundException {
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
    public Orders search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
