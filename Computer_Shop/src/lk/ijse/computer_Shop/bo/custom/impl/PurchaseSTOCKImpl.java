package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.PurchaseSTOCKBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.ItemDAO;
import lk.ijse.computer_Shop.dao.custom.StockDAO;
import lk.ijse.computer_Shop.dao.custom.StockDetailsDAO;
import lk.ijse.computer_Shop.dao.custom.SupplyerDAO;
import lk.ijse.computer_Shop.db.DBConnection;
import lk.ijse.computer_Shop.entity.Item;
import lk.ijse.computer_Shop.entity.Stock;
import lk.ijse.computer_Shop.entity.StockDetails;
import lk.ijse.computer_Shop.entity.Supplyer;
import lk.ijse.computer_Shop.model.ItemDTO;
import lk.ijse.computer_Shop.model.StockDTO;
import lk.ijse.computer_Shop.model.StockDetailDTO;
import lk.ijse.computer_Shop.model.SupplyerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseSTOCKImpl implements PurchaseSTOCKBO {
    SupplyerDAO supplyerDAO = (SupplyerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLYER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMS);
    StockDAO stockDAO = (StockDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCK);
    StockDetailsDAO stockDetailsDAO = (StockDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKDEATAILS);
    @Override
    public SupplyerDTO searchSupplyer(String id) throws SQLException, ClassNotFoundException {
        Supplyer s=supplyerDAO.search(id);
        return new SupplyerDTO(s.getSupId(),s.getName(),s.getUnitPrice(),s.getModel());
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        Item i=itemDAO.search(id);
        return new ItemDTO(i.getItemId(),i.getDescription(),i.getUnitPrice(),i.getQtyOnhand());
    }

    @Override
    public boolean existSupplyer(String id) throws SQLException, ClassNotFoundException {
        return supplyerDAO.exist(id);
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }

    @Override
    public ArrayList<SupplyerDTO> loadAllSupplyer() throws SQLException, ClassNotFoundException {
        ArrayList<Supplyer> allEntityData=supplyerDAO.getAll();
        ArrayList<SupplyerDTO>allDTO=new ArrayList<>();
        for (Supplyer s:allEntityData){
            allDTO.add(new SupplyerDTO(s.getSupId(),s.getName(),s.getUnitPrice(),s.getModel()));
        }

        return allDTO;
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
    public String generateNewOrderIdStock() throws SQLException, ClassNotFoundException {
        return stockDAO.generateNewID();
    }

    @Override
    public boolean purchesOrder(StockDTO dto) {
        Connection connection=null;
        try {
            connection= DBConnection.getDbConnection().getConnection();

            boolean b1=stockDAO.exist(dto.getStockId());

            if (b1){
                return false;
            }
            connection.setAutoCommit(false);

            boolean b2=stockDAO.add(new Stock(dto.getStockId(),dto.getSupId(),dto.getDate()));

            if (!b2){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            /*for (StockDetailDTO d: dto.getStockDetails()){
                StockDetails stockDetails=new StockDetails(d.getStockId(),d.getSupId(),d.getQty(),d.getUnitPrice());
                boolean b3=stockDetailsDAO.add(stockDetails);
                if (!b3){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }*/
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
