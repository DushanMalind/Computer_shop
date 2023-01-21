package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.StockBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.StockDAO;
import lk.ijse.computer_Shop.entity.Stock;
import lk.ijse.computer_Shop.model.OrdersDTO;
import lk.ijse.computer_Shop.model.StockDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockBOImpl implements StockBO {
    StockDAO stockDAO = (StockDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCK);
    @Override
    public ArrayList<StockDTO> getAllStock() throws SQLException, ClassNotFoundException {
        ArrayList<StockDTO>allStock=new ArrayList<>();
        ArrayList<Stock> allEntity= stockDAO.getAll();
        for (Stock s:allEntity){
            allStock.add(new StockDTO(s.getStockId(),s.getSupId(),s.getDate()));
        }
        return allStock;
    }
}
