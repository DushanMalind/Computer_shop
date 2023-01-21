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
import lk.ijse.computer_Shop.bo.custom.SupplyerBO;
import lk.ijse.computer_Shop.db.DBConnection;
import lk.ijse.computer_Shop.model.SupplyerDTO;
import lk.ijse.computer_Shop.view.tdm.SupplyerTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SupplyerFromController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnNewSupplyer;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colModelName;

    @FXML
    private TableColumn<?, ?> colNmae;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplyerTm> tblSupplyer;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSupplyerId;

    SupplyerBO supplyerBO = (SupplyerBO) Factory.getFactory().getBo(Factory.BOTypes.SUPPLYERS);

    public void initialize() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNmae.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colModelName.setCellValueFactory(new PropertyValueFactory<>("model"));

        initUI();

        tblSupplyer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue==null);
            btnSave.setText(newValue !=null ? "Update" : "Save");
            btnSave.setDisable(newValue==null);

            if (newValue !=null){
                txtSupplyerId.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtModel.setText(newValue.getModel());

                txtSupplyerId.setDisable(false);
                txtName.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtModel.setDisable(false);
            }
        });

        txtModel.setOnAction(event -> btnSave.fire());
        loadAllSupplyer();
    }

    private void loadAllSupplyer(){
        tblSupplyer.getItems().clear();

        try {
            ArrayList<SupplyerDTO>allSupplyer=supplyerBO.getAllCustomer();
            for (SupplyerDTO s :allSupplyer){
                tblSupplyer.getItems().add(new SupplyerTm(s.getSupId(),s.getName(),s.getUnitPrice(),s.getModel()));

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show(); new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtSupplyerId.clear();
        txtName.clear();
        txtUnitPrice.clear();
        txtModel.clear();
        txtSupplyerId.setDisable(true);
        txtName.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtModel.setDisable(true);
        txtSupplyerId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnNewSupplyerOnAction(ActionEvent event) {
        txtSupplyerId.setDisable(false);
        txtName.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtModel.setDisable(false);
        txtSupplyerId.clear();
        txtSupplyerId.setText(generateNewIds());
        txtName.clear();
        txtUnitPrice.clear();
        txtModel.clear();
        txtName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save Supplyer");
        tblSupplyer.getSelectionModel().clearSelection();
    }
    private String generateNewIds() {
        try {
            //Generate New Id

            return supplyerBO.genaRateNewIdSupplyer();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "S00-001";

    }

    @FXML
    void btnSaveSupplyerOnAction(ActionEvent event) {
        String id = txtSupplyerId.getText();
        String name = txtName.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String model = txtModel.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtModel.getText().matches(".*[a-zA-Z0-9]{4,}")) {
            new Alert(Alert.AlertType.ERROR, " Invalid Model Name").show();
            txtModel.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("Save Supplyer")){
            try {
                if (existSupplyer(id)){
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                //Add Supplyer
                supplyerBO.addCustomer(new SupplyerDTO(id,name,unitPrice,model));

                tblSupplyer.getItems().add(new SupplyerTm(id,name,unitPrice,model));

            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, "Failed to save the Supplyer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            /*Update Supplyer*/
            try {
                if (!existSupplyer(id)){
                    new Alert(Alert.AlertType.ERROR, "There is no such Supplyer associated with the id " + id).show();
                }

                supplyerBO.upDateCustomer(new SupplyerDTO(id,name,unitPrice,model));


            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, "Failed to update the Supplyer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            SupplyerTm supplyerTm=tblSupplyer.getSelectionModel().getSelectedItem();
            supplyerTm.setName(name);
            supplyerTm.setUnitPrice(unitPrice);
            supplyerTm.setModel(model);
            tblSupplyer.refresh();

        }
        btnNewSupplyer.fire();
    }

    boolean existSupplyer(String id) throws SQLException, ClassNotFoundException {
       return supplyerBO.existCustomer(id);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id=tblSupplyer.getSelectionModel().getSelectedItem().getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Deleted ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            try {
                if (!existSupplyer(id)){
                    new Alert(Alert.AlertType.ERROR, "There is no such Supplyer associated with the id " + id).show();
                }

                supplyerBO.deleteCustomer(id);

                tblSupplyer.getItems().remove(tblSupplyer.getSelectionModel().getSelectedItem());
                tblSupplyer.getSelectionModel().clearSelection();
                initUI();

            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, "Failed to Supplyer the customer " + id).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


   /* private String getLastCustomerId() {
        List<SupplyerTm> tempCustomersList = new ArrayList<>(tblSupplyer.getItems());
//        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getSupId();
    }*/

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

    public static ArrayList<SupplyerDTO> getAllItem() throws ClassNotFoundException, SQLException {

        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet result = connection.prepareStatement("SELECT * FROM supplyer").executeQuery();
        ArrayList<SupplyerDTO> data = new ArrayList();
        while (result.next()) {
            SupplyerDTO c = new SupplyerDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getString(4)
            );

            data.add(c);
        }
        return data;
    }

}
