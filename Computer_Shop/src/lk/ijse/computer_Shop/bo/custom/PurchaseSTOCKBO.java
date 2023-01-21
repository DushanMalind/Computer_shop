package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.ItemDTO;
import lk.ijse.computer_Shop.model.StockDTO;
import lk.ijse.computer_Shop.model.SupplyerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseSTOCKBO extends SuperBO {

    public SupplyerDTO searchSupplyer(String id) throws SQLException, ClassNotFoundException;

    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    public boolean existSupplyer(String id) throws SQLException, ClassNotFoundException;

    public boolean existItem(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<SupplyerDTO> loadAllSupplyer() throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDTO>loadAllItemId() throws SQLException, ClassNotFoundException;

    public String generateNewOrderIdStock() throws SQLException, ClassNotFoundException;

    public boolean purchesOrder(StockDTO dto);
}
