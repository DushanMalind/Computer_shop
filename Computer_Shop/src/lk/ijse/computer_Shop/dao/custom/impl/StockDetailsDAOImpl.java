package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.StockDetailsDAO;
import lk.ijse.computer_Shop.entity.Stock;
import lk.ijse.computer_Shop.entity.StockDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockDetailsDAOImpl implements StockDetailsDAO {

    @Override
    public ArrayList<StockDetails> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<StockDetails>allStock=new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT *FROM stockdetails");
        while (rst.next()){
            StockDetails stockDetails=new StockDetails(rst.getString("stockId"),rst.getString("supId"),rst.getInt("qty"),rst.getDouble("unitPrice"));
            allStock.add(stockDetails);
        }
        return allStock;
    }

    @Override
    public boolean add(StockDetails dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO stockdetails(stockId,supId,qty,unitPrice) VALUES (?,?,?,?)", dto.getStockId(),dto.getSupId(),dto.getQty(),dto.getUnitPrice());
    }

    @Override
    public boolean update(StockDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StockDetails search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
