package lk.ijse.computer_Shop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import lk.ijse.computer_Shop.bo.Factory;
import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.bo.custom.PurchaseSTOCKBO;
import lk.ijse.computer_Shop.model.*;
import lk.ijse.computer_Shop.view.tdm.OrderDetailTm;
import lk.ijse.computer_Shop.view.tdm.StockDetailsTm;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaceStockFromController {

    public TableView<StockDetailsTm>tblStock;
    public TableColumn colSupId;
    public TableColumn colName;
    public TableColumn colModel;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colOption;
    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<String> cmdItemIds;

    @FXML
    private JFXComboBox<String> cmdSupplyer;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblStockId;

    @FXML
    private Label lblTotal;

    @FXML
    private AnchorPane root;

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
    private String orderId;

    PurchaseSTOCKBO purchaseSTOCKBO = (PurchaseSTOCKBO) Factory.getFactory().getBo(Factory.BOTypes.PURCHERSSTOCK);

    public void initialize(){
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<StockDetailsTm, Button> lastCol = (TableColumn<StockDetailsTm, Button>) tblStock.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblStock.getItems().remove(param.getValue());
                tblStock.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId=generateNewOrderId();
        lblStockId.setText("Order ID"+orderId);
        lblDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtQTY.setOnAction(event -> btnSave.fire());
        txtQTY.setEditable(false);
        btnSave.setDisable(true);


        cmdItemIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newCusID) -> {
            enableOrDisablePlaceOrderButton();

            if (newCusID != null) {
                try {

                    if (!existItem(newCusID + "")) {
//                            "There is no such customer associated with the id " + id
                        new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newCusID + "").show();
                    }
                    ItemDTO customerDTO = purchaseSTOCKBO.searchItem(newCusID + "");
                    txtDescription.setText(customerDTO.getDescription());
                    txtUnitPriceItem.setText(String.valueOf(customerDTO.getUnitPrice()));
                    txtQtyItem.setText(String.valueOf(customerDTO.getQtyOnHand()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException  | NullPointerException e) {
//                    e.printStackTrace();
                }
            } else {
                txtName.clear();
            }
        });

        cmdSupplyer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQTY.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    if (!existSupplyer(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }
                    //Search Item
                    SupplyerDTO supplyer = purchaseSTOCKBO.searchSupplyer(newItemCode + "");

                    txtName.setText(supplyer.getName());
                    txtUnitPrice.setText(String.valueOf(supplyer.getUnitPrice()));
                    txtModel.setText(String.valueOf(supplyer.getModel()));


//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<StockDetailsTm> optOrderDetail = tblStock.getItems().stream().filter(detail -> detail.getSupId().equals(newItemCode)).findFirst();
//                    txtQTY.setText((optOrderDetail.isPresent() ? - optOrderDetail.get().getQty());

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtUnitPrice.clear();
            }
        });
        tblStock.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmdSupplyer.setDisable(true);
                cmdSupplyer.setValue(selectedOrderDetail.getSupId());
                btnSave.setText("Update");
                /*txtQty.setText(Integer.parseInt(txtQty.getText()) + selectedOrderDetail.getQty() + "");
                txtQtyOnHand.setText(selectedOrderDetail.getQty() + "");*/
            } else {
                btnSave.setText("Add");
                cmdSupplyer.setDisable(false);
                cmdSupplyer.getSelectionModel().clearSelection();
                txtQTY.clear();
            }

        });

        loadAllItemCodes();
        loadAllSupplyer();
    }

    private boolean existSupplyer(String code) throws SQLException, ClassNotFoundException {
        return purchaseSTOCKBO.existSupplyer(code);
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseSTOCKBO.existItem(code);
    }

    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> allItem= purchaseSTOCKBO.loadAllItemId();
            for (ItemDTO i:allItem){
                cmdItemIds.getItems().add(i.getCode());
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllSupplyer(){
        try {
            ArrayList<SupplyerDTO>allSupplyer= purchaseSTOCKBO.loadAllSupplyer();
            for (SupplyerDTO s:allSupplyer){
                cmdSupplyer.getItems().add(s.getSupId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String generateNewOrderId() {
        try {
            return purchaseSTOCKBO.generateNewOrderIdStock();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }


    @FXML
    void addToCartOnAction(ActionEvent event) {
        String supId=cmdSupplyer.getSelectionModel().getSelectedItem();
        String name=txtName.getText();
        String model=txtModel.getText();
        double unitPrice= Double.parseDouble(txtUnitPrice.getText());
        int qty= Integer.parseInt(txtQTY.getText());
        double total=unitPrice*(qty);

        boolean exists = tblStock.getItems().stream().anyMatch(detail -> detail.getSupId().equals(supId));

        if (exists){
            StockDetailsTm  stockDetailsTm=tblStock.getItems().stream().filter(detail -> detail.getSupId().equals(supId)).findFirst().get();

            if (btnSave.getText().equalsIgnoreCase("Update")){
                stockDetailsTm.setQty(qty);
                stockDetailsTm.setTotal(total);
                tblStock.getSelectionModel().clearSelection();
            }else {
                stockDetailsTm.setQty(stockDetailsTm.getQty()+qty);
                total=(stockDetailsTm.getQty())*(unitPrice);
                stockDetailsTm.setTotal(total);
            }
            tblStock.refresh();
        }else {
            tblStock.getItems().add(new StockDetailsTm(supId,name,model,unitPrice,qty,total));
        }
        cmdSupplyer.getSelectionModel().clearSelection();
        cmdSupplyer.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();

    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (StockDetailsTm detail : tblStock.getItems()) {
            total = total.add(BigDecimal.valueOf(detail.getTotal()));
        }
        lblTotal.setText("Total: " + total);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable((cmdSupplyer.getSelectionModel().getSelectedItem() != null && !tblStock.getItems().isEmpty()));
    }

    @FXML
    void PlaceOrdeOnAction(ActionEvent event) {
        boolean b=saveOrder(orderId,LocalDate.now(),cmdSupplyer.getValue(),
                tblStock.getItems().stream().map(tm ->new StockDetailDTO(orderId,tm.getSupId(),tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b){
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }
        orderId=generateNewOrderId();
        lblStockId.setText("Order Id:"+orderId);
        cmdSupplyer.getSelectionModel().clearSelection();
        tblStock.getItems().clear();
        txtQTY.clear();
        calculateTotal();

    }

    public boolean saveOrder(String orderId, LocalDate supId, String localDate, List<StockDetailDTO> stockDetails){
        StockDTO stockDTO=new StockDTO(orderId,supId,localDate,stockDetails);
        return purchaseSTOCKBO.purchesOrder(stockDTO);
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
