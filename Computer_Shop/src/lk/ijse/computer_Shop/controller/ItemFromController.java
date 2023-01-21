package lk.ijse.computer_Shop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
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
import lk.ijse.computer_Shop.bo.custom.ItemBO;
import lk.ijse.computer_Shop.db.DBConnection;
import lk.ijse.computer_Shop.model.ItemDTO;
import lk.ijse.computer_Shop.view.tdm.ItemTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;



public class ItemFromController {

    public AnchorPane root;
    public JFXButton btnDelete;

    @FXML
    private JFXButton btnAddNewItem;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtUnitPrice;

    private String searchText = "";

    ItemBO itemBO = (ItemBO) Factory.getFactory().getBo(Factory.BOTypes.ITEM);

    public void initialize() {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        initUI();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue==null);
            btnSave.setText(newValue !=null ? "Update" : "Save");
            btnSave.setDisable(newValue==null);

            if (newValue !=null){
                txtItemId.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtQty.setText(String.valueOf(newValue.getQtyOnHand()));

                txtItemId.setDisable(false);
                txtDescription.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQty.setDisable(false);
            }
        });

        txtQty.setOnAction(event -> btnSave.fire());
        loadAllItems();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
//            loadAllItems();
        });
    }


    private void loadAllItems(){
//        String searchText = "%" + text + "%";

        try {
            /*Get all items*/
            itemBO.loadAllItem();
            ArrayList<ItemDTO>allItem=itemBO.loadAllItem();
            for (ItemDTO i:allItem){
                tblItem.getItems().add(new ItemTm(i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand()));
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI(){
        txtItemId.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQty.clear();
        txtItemId.setDisable(true);
        txtDescription.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQty.setDisable(true);
        txtItemId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnNewSaveOnAction(ActionEvent event) {
       txtItemId.setDisable(false);
       txtDescription.setDisable(false);
       txtUnitPrice.setDisable(false);
       txtQty.setDisable(false);
       txtItemId.clear();
       txtItemId.setText(generateNewId());
       txtDescription.clear();
       txtUnitPrice.clear();
       txtQty.clear();
       txtDescription.requestFocus();
       btnSave.setDisable(false);
       btnSave.setText("Save Item");
       tblItem.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return itemBO.generateNewIdItem();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I00-001";
    }

    @FXML
    void btnSaveOnItemAction(ActionEvent event) {
        String code=txtItemId.getText();
        String description=txtDescription.getText();
        double unitprice= Double.parseDouble(txtUnitPrice.getText());
        int qty= Integer.parseInt(txtQty.getText());

        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQty.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQty.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("Save Item")){
            try {
                if (existItem(code)){
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save Item

                itemBO.saveItem(new ItemDTO(code,description,unitprice,qty));

                tblItem.getItems().add(new ItemTm(code,description,unitprice,qty));
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            try {
                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }

                /*Update Item*/

                itemBO.updateItem(new ItemDTO(code,description,unitprice,qty));

                ItemTm selectedItem=tblItem.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setUnitPrice(unitprice);
                selectedItem.setQtyOnHand(qty);
                tblItem.refresh();


            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        btnAddNewItem.fire();
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.existItem(code);
    }


    @FXML
    void btnClearOnDeleteAction(ActionEvent event) {
        /*Delete Item*/
        String code=tblItem.getSelectionModel().getSelectedItem().getCode();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Deleted ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                itemBO.deleteItem(code);
                tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
                tblItem.getSelectionModel().clearSelection();
                initUI();
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

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

    public static ArrayList<ItemDTO> getAllItem() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet result = connection.prepareStatement("SELECT * FROM item").executeQuery();
        ArrayList<ItemDTO> data = new ArrayList();
        while (result.next()) {

            ItemDTO c = new ItemDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );

            data.add(c);
        }
        return data;
    }

}
