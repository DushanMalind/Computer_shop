package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.SupplyerBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.SupplyerDAO;
import lk.ijse.computer_Shop.entity.Supplyer;
import lk.ijse.computer_Shop.model.SupplyerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplyerBOImpl implements SupplyerBO {
    SupplyerDAO supplyerDAO = (SupplyerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLYER);
    @Override
    public ArrayList<SupplyerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
       ArrayList<SupplyerDTO>allSupplyerDTO=new ArrayList<>();
       ArrayList<Supplyer>all=supplyerDAO.getAll();
       for (Supplyer s:all){
           allSupplyerDTO.add(new SupplyerDTO(s.getSupId(),s.getName(),s.getUnitPrice(),s.getModel()));
       }
        return allSupplyerDTO;
    }

    @Override
    public boolean addCustomer(SupplyerDTO dto) throws SQLException, ClassNotFoundException {
        return supplyerDAO.add(new Supplyer(dto.getSupId(),dto.getName(),dto.getUnitPrice(),dto.getModel()));
    }

    @Override
    public boolean upDateCustomer(SupplyerDTO dto) throws SQLException, ClassNotFoundException {
        return supplyerDAO.update(new Supplyer(dto.getSupId(),dto.getName(),dto.getUnitPrice(),dto.getModel()));
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return supplyerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return supplyerDAO.delete(id);
    }

    @Override
    public String genaRateNewIdSupplyer() throws SQLException, ClassNotFoundException {
        return supplyerDAO.generateNewID();
    }
}
