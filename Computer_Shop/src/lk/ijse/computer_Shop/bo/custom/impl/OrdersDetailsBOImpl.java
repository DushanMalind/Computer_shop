package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.OrderDetailsBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.custom.OrderDetailsDAO;
import lk.ijse.computer_Shop.entity.OrderDetails;
import lk.ijse.computer_Shop.entity.Orders;
import lk.ijse.computer_Shop.model.OrderDetailDTO;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrdersDetailsBOImpl implements OrderDetailsBO {
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETALIS);
    @Override
    public ArrayList<OrderDetailDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException {
            ArrayList<OrderDetailDTO> allOrders=new ArrayList<>();
            ArrayList<OrderDetails>all= orderDetailsDAO.getAll();
            for (OrderDetails o :all){
                allOrders.add(new OrderDetailDTO(o.getOrdId(),o.getItemId(),o.getQty(),o.getUnitPrice()));
            }
            return allOrders;
        }
    }

