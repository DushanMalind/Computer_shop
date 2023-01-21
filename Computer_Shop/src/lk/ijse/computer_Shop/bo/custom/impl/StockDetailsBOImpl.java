package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.StockDetailsBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.StockDetailsDAO;
import lk.ijse.computer_Shop.entity.StockDetails;
import lk.ijse.computer_Shop.model.StockDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockDetailsBOImpl implements StockDetailsBO {
    StockDetailsDAO stockDetailsDAO = (StockDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKDEATAILS);
    @Override
    public ArrayList<StockDetailDTO> getAllStockDetails() throws SQLException, ClassNotFoundException {
        ArrayList<StockDetailDTO>allStockDetails=new ArrayList<>();
        ArrayList<StockDetails>all=stockDetailsDAO.getAll();
        for (StockDetails s:all){
            allStockDetails.add(new StockDetailDTO(s.getStockId(),s.getSupId(),s.getQty(),s.getUnitPrice()));
        }
        return allStockDetails;
    }
}
