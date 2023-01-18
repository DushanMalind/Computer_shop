package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.ItemBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.ItemDAO;
import lk.ijse.computer_Shop.entity.Item;
import lk.ijse.computer_Shop.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMS);
    @Override
    public ArrayList<ItemDTO> loadAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allDTOItem=new ArrayList<>();
        ArrayList<Item> allEntitiyData= itemDAO.getAll();
        for (Item i :allEntitiyData){
            allDTOItem.add(new ItemDTO(i.getItemId(),i.getDescription(),i.getUnitPrice(),i.getQtyOnhand()));
        }
        return allDTOItem;
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public String generateNewIdItem() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }

}
