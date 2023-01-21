package lk.ijse.computer_Shop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PlaceStockFromController {

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<?> cmdItemIds;

    @FXML
    private JFXComboBox<?> cmdSupplyer;

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

    @FXML
    void PlaceOrdeOnAction(ActionEvent event) {

    }

    @FXML
    void addToCartOnAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

}
