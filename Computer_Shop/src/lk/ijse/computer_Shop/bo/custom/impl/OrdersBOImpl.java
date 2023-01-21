package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.OrdersBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.custom.OrdersDAO;
import lk.ijse.computer_Shop.entity.Orders;
import lk.ijse.computer_Shop.model.OrdersDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrdersBOImpl implements OrdersBO {
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    @Override
    public ArrayList<OrdersDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<OrdersDTO> allOrders=new ArrayList<>();
        ArrayList<Orders>all= ordersDAO.getAll();
        for (Orders o :all){
            allOrders.add(new OrdersDTO(o.getOrdId(),LocalDate.now(),o.getCusId()));
        }
        return allOrders;
    }
}
