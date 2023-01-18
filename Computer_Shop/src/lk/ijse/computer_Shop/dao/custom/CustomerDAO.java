package lk.ijse.computer_Shop.dao.custom;

import lk.ijse.computer_Shop.dao.CrudDAO;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.entity.Customer;
import lk.ijse.computer_Shop.model.CustomerDTO;

public interface CustomerDAO extends SuperDAO,CrudDAO<Customer> {
}
