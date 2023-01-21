package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.StockDAO;
import lk.ijse.computer_Shop.entity.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockDAOImpl implements StockDAO {
    @Override
    public ArrayList<Stock> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Stock entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO `stock` (stockId,supId,date) VALUES (?,?,?)",entity.getStockId(),entity.getSupId(),entity.getDate());
    }

    @Override
    public boolean update(Stock dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT stockId FROM `stock` WHERE stockId=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT stockId FROM `stock` ORDER BY stockId DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("stockId").replace("OID-", "")) + 1)) : "OID-001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Stock search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
