package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.PurchaseBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.CustomerDAO;
import lk.ijse.computer_Shop.dao.custom.ItemDAO;
import lk.ijse.computer_Shop.dao.custom.OrderDetailsDAO;
import lk.ijse.computer_Shop.dao.custom.OrdersDAO;
import lk.ijse.computer_Shop.db.DBConnection;
import lk.ijse.computer_Shop.entity.Customer;
import lk.ijse.computer_Shop.entity.Item;
import lk.ijse.computer_Shop.entity.OrderDetails;
import lk.ijse.computer_Shop.entity.Orders;
import lk.ijse.computer_Shop.model.CustomerDTO;
import lk.ijse.computer_Shop.model.ItemDTO;
import lk.ijse.computer_Shop.model.OrderDetailDTO;
import lk.ijse.computer_Shop.model.OrdersDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseBOImpl implements PurchaseBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMS);
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETALIS);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTDAO);
    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c=customerDAO.search(id);
        return new CustomerDTO(c.getCusId(),c.getName(),c.getAddress(),c.getContact());
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        Item i=itemDAO.search(id);
        return new ItemDTO(i.getItemId(),i.getDescription(),i.getUnitPrice(),i.getQtyOnhand());
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateNewOrderIdItem() throws SQLException, ClassNotFoundException {
        return ordersDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> loadAllCustomerId() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomer=new ArrayList<>();
        ArrayList<Customer>allEntity=customerDAO.getAll();
        for (Customer c : allEntity){
            allCustomer.add(new CustomerDTO(c.getCusId(),c.getName(),c.getAddress(),c.getContact()));
        }
        return allCustomer;
    }

    @Override
    public ArrayList<ItemDTO> loadAllItemId() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allDTOItem=new ArrayList<>();
        ArrayList<Item> allEntitiyData= itemDAO.getAll();
        for (Item i :allEntitiyData){
            allDTOItem.add(new ItemDTO(i.getItemId(),i.getDescription(),i.getUnitPrice(),i.getQtyOnhand()));
        }
        return allDTOItem;
    }

    @Override
    public ItemDTO serachItem(String code) throws SQLException, ClassNotFoundException {
        Item i=itemDAO.search(code);
        return new ItemDTO(i.getItemId(),i.getDescription(),i.getUnitPrice(),i.getQtyOnhand());
    }

    @Override
    public boolean purchesOrder(OrdersDTO dto) {
        Connection connection=null;
        try {
            connection= DBConnection.getDbConnection().getConnection();

            boolean b1=ordersDAO.exist(dto.getOrderId());

            if (b1){
                return false;
            }

            connection.setAutoCommit(false);

            //Save the Order to the order table

            boolean b2=ordersDAO.add(new Orders(dto.getOrderId(),dto.getOrderDate(),dto.getCustomerId()));

            if (!b2){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            // add data to the Order Details table

            for(OrderDetailDTO d : dto.getOrderDetails()){
                OrderDetails orderDetails=new OrderDetails(d.getOid(),d.getItemCode(),d.getQty(),d.getUnitPrice());
                boolean b3=orderDetailsDAO.add(orderDetails);

                if (!b3){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                ItemDTO itemDTO=searchItem(d.getItemCode());
                itemDTO.setQtyOnHand(itemDTO.getQtyOnHand() - d.getQty());

                boolean b4=itemDAO.update(new Item(itemDTO.getCode(),itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand()));

                if (!b4){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return  false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        }catch (SQLException e){

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
