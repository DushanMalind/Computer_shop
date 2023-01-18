package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.CustomerBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.CustomerDAO;
import lk.ijse.computer_Shop.entity.Customer;
import lk.ijse.computer_Shop.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTDAO);
    @Override
    public ArrayList<CustomerDTO> gelAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomer=new ArrayList<>();
        ArrayList<Customer>allEntity=customerDAO.getAll();
        for (Customer c : allEntity){
            allCustomer.add(new CustomerDTO(c.getCusId(),c.getName(),c.getAddress(),c.getContact()));
        }
        return allCustomer;
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getContact()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getContact()));
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String genaRateNewId() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }
}
