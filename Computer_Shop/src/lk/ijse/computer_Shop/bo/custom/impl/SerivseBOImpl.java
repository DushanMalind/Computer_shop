package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.SerivseBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.ServiseDAO;
import lk.ijse.computer_Shop.entity.Servise;
import lk.ijse.computer_Shop.model.ServiseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SerivseBOImpl implements SerivseBO {
    ServiseDAO serviseDAO = (ServiseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SERIVSE);
    @Override
    public ArrayList<ServiseDTO> loadAllSerivse() throws SQLException, ClassNotFoundException {
       ArrayList<Servise>allEntityData=serviseDAO.getAll();
       ArrayList<ServiseDTO>allSerivse=new ArrayList<>();
       for (Servise s : allEntityData){
           allSerivse.add(new ServiseDTO(s.getServiseId(),s.getName(),s.getEmpId(),s.getPrice()));
       }
        return allSerivse;
    }

    @Override
    public boolean updateSerivse(ServiseDTO dto) throws SQLException, ClassNotFoundException {
        return serviseDAO.update(new Servise(dto.getServiseId(),dto.getName(),dto.getEmpId(),dto.getPrice()));
    }

    @Override
    public boolean deleteSerivse(String code) throws SQLException, ClassNotFoundException {
        return serviseDAO.delete(code);
    }

    @Override
    public boolean saveSerivse(ServiseDTO dto) throws SQLException, ClassNotFoundException {
        return serviseDAO.add(new Servise(dto.getServiseId(),dto.getName(),dto.getEmpId(),dto.getPrice()));
    }

    @Override
    public boolean existSerivse(String code) throws SQLException, ClassNotFoundException {
        return serviseDAO.exist(code);
    }

    @Override
    public String generateNewIdSerivse() throws SQLException, ClassNotFoundException {
        return serviseDAO.generateNewID();
    }
}
