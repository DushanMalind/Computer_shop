package lk.ijse.computer_Shop.bo;

import lk.ijse.computer_Shop.bo.custom.impl.CustomerBOImpl;
import lk.ijse.computer_Shop.bo.custom.impl.ItemBOImpl;
import lk.ijse.computer_Shop.bo.custom.impl.PurchaseBOImpl;

public class Factory {
    private static Factory factory;

    private  Factory(){
    }

    public static Factory getFactory(){
        if (factory==null){
            factory=new Factory();
        }
        return factory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,PURCHERS
    }

    public SuperBO getBo(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PURCHERS:
                return new PurchaseBOImpl();
        }
        return null;
    }
}

