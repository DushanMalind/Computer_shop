package lk.ijse.computer_Shop.bo;

import lk.ijse.computer_Shop.bo.custom.impl.*;

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
        CUSTOMER,ITEM,PURCHERS,OREDERS,ORDERSDETAILS,SUPPLYERS,PURCHERSSTOCK
    }

    public SuperBO getBo(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PURCHERS:
                return new PurchaseBOImpl();
            case OREDERS:
                return new OrdersBOImpl();
            case ORDERSDETAILS:
                return new OrdersDetailsBOImpl();
            case SUPPLYERS:
                return new SupplyerBOImpl();
            case PURCHERSSTOCK:
                return new PurchaseSTOCKImpl();

        }
        return null;
    }
}

