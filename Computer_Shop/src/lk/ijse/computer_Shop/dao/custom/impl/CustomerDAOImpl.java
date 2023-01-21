package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.CustomerDAO;
import lk.ijse.computer_Shop.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomer=new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT *FROM customer");
        while (rst.next()){
            Customer customerDTO=new Customer(rst.getString("cusId"),rst.getString("name"),rst.getString("address"),rst.getString("contact"));
            allCustomer.add(customerDTO);
        }
        return allCustomer;
    }

    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer(cusId,name,address,contact) VALUES (?,?,?,?)",entity.getCusId(),entity.getName(),entity.getAddress(),entity.getContact());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Customer SET name=?, address=?, contact=? WHERE cusId=?",entity.getName(),entity.getAddress(),entity.getContact(),entity.getCusId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1;");
        if (rst.next()){
            String ids=rst.getString("cusId");
            int newCustomerId=Integer.parseInt(ids.replace("C00-",""))+1;
            return String.format("C00-%03d",newCustomerId);
        }else {
            return "C00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE cusId=?",id);
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }


}
