package lk.ijse.computer_Shop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lk.ijse.computer_Shop.bo.Factory;
import lk.ijse.computer_Shop.bo.custom.CustomerBO;
import lk.ijse.computer_Shop.db.DBConnection;
import lk.ijse.computer_Shop.model.CustomerDTO;
import lk.ijse.computer_Shop.view.tdm.CustomerTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class CustomerFromController {
    public AnchorPane root;
    public TextField txtCustomerId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtSearch;
    public TableView <CustomerTm>tblCustomer;
    public TableColumn colCusId;
    public TableColumn colCusName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colOption;

    public JFXButton btnSave;
    public JFXButton btnAddNewCustomer;



    CustomerBO customerBO = (CustomerBO) Factory.getFactory().getBo(Factory.BOTypes.CUSTOMER);

    public void initialize(){
        /*tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("btn"));*/
        colCusId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));


        iniUI();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            btnSave.setText(newValue !=null ?"update" : "Save");
            btnSave.setDisable(newValue==null);

            if (newValue!=null){
                txtCustomerId.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());

                txtCustomerId.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);
            }
        });

       txtContact.setOnAction(event -> btnSave.fire());
       lordAllCustomer();

    }

    private void lordAllCustomer(){
        tblCustomer.getItems().clear();

        try {
            ArrayList<CustomerDTO>allCustomer=customerBO.gelAllCustomer();
            for (CustomerDTO c:allCustomer){
                tblCustomer.getItems().add(new CustomerTm(c.getId(),c.getName(),c.getAddress(),c.getContact()));
            }
        }catch (ClassNotFoundException e){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void iniUI(){
        txtCustomerId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtCustomerId.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtCustomerId.setEditable(false);
        btnSave.setDisable(true);

    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/computer_Shop/view/main_from.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void btnNewSaveOnAction(ActionEvent actionEvent) {
        txtCustomerId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtCustomerId.clear();
        txtCustomerId.setText(generateNewIds());
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save Customer");
        tblCustomer.getSelectionModel().clearSelection();
    }

    private String generateNewIds() {
        try {

            return customerBO.genaRateNewId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "C00-001";
    }


    public void btnSaveOnCustomerAction(ActionEvent actionEvent) {
        String id=txtCustomerId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String contact=txtContact.getText();

        if (!name.matches(".*[a-zA-Z0-9]{4,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
//            txtName.setFocusColor(Paint.valueOf("Red"));
            return;
        } else if (!address.matches(".*[a-zA-Z0-9]{4,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should not ").show();
            txtAddress.requestFocus();
            return;
        }else if (!contact.matches(".*(?:7|0|(?:\\\\+94))[0-9]{9,10}")) {
            new Alert(Alert.AlertType.ERROR, "Contact should be at long").show();
            txtContact.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("Save Customer")){
            try {

                if (existCustomer(id)){
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }

                customerBO.addCustomer(new CustomerDTO(id,name,address,contact));

                tblCustomer.getItems().add(new CustomerTm(id,name,address,contact));
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            try {
                if (!existCustomer(id)){
//                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }
                customerBO.updateCustomer(new CustomerDTO(id,name,address,contact));

            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            CustomerTm customerTm=tblCustomer.getSelectionModel().getSelectedItem();
            customerTm.setName(name);
            customerTm.setAddress(address);
            customerTm.setContact(contact);
            tblCustomer.refresh();

        }
        btnAddNewCustomer.fire();
    }

    boolean existCustomer(String code) throws SQLException, ClassNotFoundException {
        return customerBO.existCustomer(code);
    }

    public void btnClearOnCustomerAction(ActionEvent actionEvent) {
        String id=tblCustomer.getSelectionModel().getSelectedItem().getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Deleted ?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.YES) {

                try {
                    if (existCustomer(id)){
                        new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                    }
                    customerBO.deleteCustomer(id);
                    tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
                    tblCustomer.getSelectionModel().clearSelection();
                    iniUI();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

    }


    private String getLastCustomerId() {
        List<CustomerTm> cust = new ArrayList<CustomerTm>(tblCustomer.getItems());
//        Collections.sort(cust);
        return cust.get(cust.size() - 1).getId();
    }
    public static ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {

            Connection connection = DBConnection.getDbConnection().getConnection();
            ResultSet result = connection.prepareStatement("SELECT *FROM customer").executeQuery();
            ArrayList<CustomerDTO> data = new ArrayList();
            while (result.next()) {
                CustomerDTO c = new CustomerDTO(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                );
                data.add(c);
            }
            return data;
    }
}
