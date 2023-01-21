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
import lk.ijse.computer_Shop.bo.custom.PurchaseBO;
import lk.ijse.computer_Shop.model.CustomerDTO;
import lk.ijse.computer_Shop.model.ItemDTO;
import lk.ijse.computer_Shop.model.OrderDetailDTO;
import lk.ijse.computer_Shop.model.OrdersDTO;
import lk.ijse.computer_Shop.view.tdm.OrderDetailTm;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaceOrderfromController {

    public AnchorPane root;
    public TableView <OrderDetailTm> tblPlaceOrder;
    public JFXButton btnSave;
    public JFXButton btnPlaceOrder;
    @FXML
    private JFXComboBox<String> cmdCustomerIds;

    @FXML
    private JFXComboBox<String> cmdItemIds;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private Label txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;
    private String orderId;

    PurchaseBO purchaseBO = (PurchaseBO) Factory.getFactory().getBo(Factory.BOTypes.PURCHERS);

    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTm, Button> lastCol = (TableColumn<OrderDetailTm, Button>) tblPlaceOrder.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblPlaceOrder.getItems().remove(param.getValue());
                tblPlaceOrder.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        txtOrderId.setText("Order ID: " + orderId);
        txtDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtName.setFocusTraversable(false);
        txtName.setEditable(false);
        txtAddress.setFocusTraversable(false);
        txtAddress.setEditable(false);
        txtContact.setFocusTraversable(false);
        txtContact.setEditable(false);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQty.setFocusTraversable(false);
        txtQty.setEditable(false);
        txtQtyOnHand.setOnAction(event -> btnSave.fire());
        txtQtyOnHand.setEditable(false);
        btnSave.setDisable(true);


        cmdCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newCusID) -> {
            enableOrDisablePlaceOrderButton();

            if (newCusID != null) {
                try {

                        if (!existCustomer(newCusID + "")) {
//                            "There is no such customer associated with the id " + id
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newCusID + "").show();
                        }
                        CustomerDTO customerDTO = purchaseBO.searchCustomer(newCusID + "");
                        txtName.setText(customerDTO.getName());
                        txtAddress.setText(customerDTO.getAddress());
                        txtContact.setText(customerDTO.getContact());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException  | NullPointerException e) {
//                    e.printStackTrace();
                }
            } else {
                txtName.clear();
            }
        });
        cmdItemIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQtyOnHand.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    if (!existItem(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }
                    //Search Item
                    ItemDTO item = purchaseBO.searchItem(newItemCode + "");

                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                    txtQty.setText(String.valueOf(item.getQtyOnHand()));

//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<OrderDetailTm> optOrderDetail = tblPlaceOrder.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    txtQty.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblPlaceOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmdItemIds.setDisable(true);
                cmdItemIds.setValue(selectedOrderDetail.getCode());
                btnSave.setText("Update");
                txtQty.setText(Integer.parseInt(txtQty.getText()) + selectedOrderDetail.getQty() + "");
                txtQtyOnHand.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnSave.setText("Add");
                cmdItemIds.setDisable(false);
                cmdItemIds.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllItemCodes();

    }

  /*  private void setCustomerDeatils(){
        try {
            for (CustomerDTO c:CustomerFromController.getAllCustomer()){
                if (c.getId().equals(cmdCustomerIds.getValue())){
                    txtName.setText(c.getName());
                    txtAddress.setText(c.getAddress());
                    txtContact.setText(c.getContact());
                }
            }
        }catch (SQLException e){

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> allCustomer= purchaseBO.loadAllCustomerId();
            for (CustomerDTO c : allCustomer){
                cmdCustomerIds.getItems().add(c.getId());
                if (c.getId().equals(cmdCustomerIds.getValue())){
                    txtName.setText(c.getName());
                    txtAddress.setText(c.getAddress());
                    txtContact.setText(c.getContact());
                }
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean existCustomer(String code) throws SQLException, ClassNotFoundException {
        return purchaseBO.existCustomer(code);
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseBO.existItem(code);
    }

    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> allItem=purchaseBO.loadAllItemId();
            for (ItemDTO i:allItem){
                cmdItemIds.getItems().add(i.getCode());
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String generateNewOrderId() {
        try {
            return purchaseBO.generateNewOrderIdItem();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    @FXML
    void addToCartOnAction(ActionEvent event) {
        if (!txtQtyOnHand.getText().matches("\\d+") || Integer.parseInt(txtQtyOnHand.getText()) <= 0 ||
                Integer.parseInt(txtQtyOnHand.getText()) > Integer.parseInt(txtQty.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQtyOnHand.requestFocus();
            txtQtyOnHand.selectAll();
            return;
        }

        String itemCode = cmdItemIds.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQtyOnHand.getText());
        double total = unitPrice*(qty);

        boolean exists = tblPlaceOrder.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTm orderDetailTM = tblPlaceOrder.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnSave.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblPlaceOrder.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total =(orderDetailTM.getQty())*(unitPrice);
                orderDetailTM.setTotal(total);
            }
            tblPlaceOrder.refresh();
        } else {
            tblPlaceOrder.getItems().add(new OrderDetailTm(itemCode, description, qty, unitPrice, total));
        }
        cmdItemIds.getSelectionModel().clearSelection();
        cmdItemIds.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderDetailTm detail : tblPlaceOrder.getItems()) {
            total = total.add(BigDecimal.valueOf(detail.getTotal()));
        }
        lblTotal.setText("Total: " + total);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmdCustomerIds.getSelectionModel().getSelectedItem() != null && !tblPlaceOrder.getItems().isEmpty()));
    }

    @FXML
    void PlaceOrdeOnAction(ActionEvent event) {
        boolean b = saveOrder(orderId, LocalDate.now(), cmdCustomerIds.getValue(),
                tblPlaceOrder.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        txtOrderId.setText("Order Id: " + orderId);
        cmdCustomerIds.getSelectionModel().clearSelection();
        cmdItemIds.getSelectionModel().clearSelection();
        tblPlaceOrder.getItems().clear();
        txtQtyOnHand.clear();
        calculateTotal();
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
        /*Transaction*/
//        PurchaseBoImpl purchaseBo = new PurchaseBoImpl();
        OrdersDTO orderDTO = new OrdersDTO(orderId, orderDate, customerId, orderDetails);
        return purchaseBO.purchesOrder(orderDTO);
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
