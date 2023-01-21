package lk.ijse.computer_Shop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.computer_Shop.model.CartDeatilStock;
import lk.ijse.computer_Shop.model.ItemDTO;
import lk.ijse.computer_Shop.model.PlaceStock;
import lk.ijse.computer_Shop.model.SupplyerDTO;
import lk.ijse.computer_Shop.model2.PlaceStockModel;
import lk.ijse.computer_Shop.model2.StockModel;
import lk.ijse.computer_Shop.view.tdm.CartTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class PlaceStockFromController {

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<String> cmdItemIds;

    @FXML
    private JFXComboBox<String> cmdSupplyer;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblStockId;

    @FXML
    private Label lblTotal;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CartTm> tblSupplyerPlace;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQTY;

    @FXML
    private TextField txtQtyItem;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtUnitPriceItem;

    public void initialize()  {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setItemDeatils();
        setSupplyerDeatils();
        setDate();
        loadNextOrderId();

        try {
            loardAllItems();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            loardAllSupplyer();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmdItemIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setItemDeatils();
            }
        });

        cmdSupplyer.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setSupplyerDeatils();
            }
        }));
    }

    private void setItemDeatils() {
        try {
            for (ItemDTO i:ItemFromController.getAllItem()
            ) {
                if (i.getCode().equals(cmdItemIds.getValue())){
                    txtDescription.setText(i.getDescription());
                    txtUnitPriceItem.setText(String.valueOf(i.getUnitPrice()));
                    txtQtyItem.setText(String.valueOf(i.getQtyOnHand()));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setSupplyerDeatils() {
        try {
            for (SupplyerDTO c:SupplyerFromController.getAllItem()
            ) {
                if (c.getSupId().equals(cmdSupplyer.getValue())){
                    txtName.setText(c.getName());
                    txtUnitPrice.setText(String.valueOf(c.getUnitPrice()));
                    txtModel.setText(c.getModel());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loardAllItems() throws SQLException, ClassNotFoundException {
        for (ItemDTO i:ItemFromController.getAllItem()
        ) {
            cmdItemIds.getItems().add(i.getCode());
        }
    }

    private void loardAllSupplyer() throws SQLException, ClassNotFoundException {
        for (SupplyerDTO c:SupplyerFromController.getAllItem()
        ) {
            cmdSupplyer.getItems().add(c.getSupId());
        }
    }

    private void setDate(){
        lblStockId.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    @FXML
    void PlaceOrdeOnAction(ActionEvent event) {
        String orderId = lblStockId.getText();
        String suId = cmdSupplyer.getValue();

        ArrayList<CartDeatilStock> cartDetails = new ArrayList<>();


        for (int i = 0; i < tblSupplyerPlace.getItems().size(); i++) {
            CartTm tm = obList.get(i);
            cartDetails.add(new CartDeatilStock(orderId,tm.getSupId(),tm.getQty(),tm.getModel(),tm.getUnitPrice()));
        }

        PlaceStock placeOrder = new PlaceStock(orderId,suId,cartDetails);
        try {
            boolean isPlaced = PlaceStockModel.placeOrder(placeOrder);
            if (isPlaced) {
                obList.clear();
                loadNextOrderId();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<CartTm> obList= FXCollections.observableArrayList();
    @FXML
    void addToCartOnAction(ActionEvent event) {
        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQTY.getText());
        double total=qty*unitPrice;
        Button button=new Button("Delete");

        int row=isAlredyExists(cmdSupplyer.getValue());
        if (row==-1){
            CartTm tm=new CartTm(cmdSupplyer.getValue(),txtName.getText(),txtModel.getText(),unitPrice,qty,total,button);
            obList.add(tm);
            tblSupplyerPlace.setItems(obList);
        }else {
            int tempQty=obList.get(row).getQty()+qty;
            double tempTotal=unitPrice*tempQty;
            obList.get(row).setQty(tempQty);
            obList.get(row).setTotal(tempTotal);
            tblSupplyerPlace.refresh();
        }
        calculateTotal();
        clearFileds();
        cmdSupplyer.requestFocus();

        button.setOnAction(event1 -> {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure Delete Record",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get()==ButtonType.YES){
                for (CartTm tm:obList
                ) {
                    if (tm.getSupId().equals(tm.getSupId())){
                        obList.remove(tm);
                        calculateTotal();
                        tblSupplyerPlace.refresh();
                        return;
                    }
                }
            }
        });

    }

    private void clearFileds() {
        txtName.clear();
        txtModel.clear();
        txtDescription.clear();
        txtModel.clear();
        txtQTY.clear();
        txtDescription.clear();
        txtUnitPriceItem.clear();
        txtModel.clear();
    }

    private int isAlredyExists(String code) {
        for (int i = 0; i < obList.size(); i++) {
            if (obList.get(i).getSupId().equals(code)){
                return i;
            }
        }
        return -1;
    }

    private void calculateTotal(){
        double total=0.00;
        for (CartTm tm:obList
        ) {
            total+=tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void loadNextOrderId() {
        try {
            String orderId = StockModel.generateNextOrderId();
            lblStockId.setText(orderId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/computer_Shop/view/main_from.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

}
