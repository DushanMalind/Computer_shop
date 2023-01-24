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
import lk.ijse.computer_Shop.bo.custom.SerivseBO;
import lk.ijse.computer_Shop.model.ServiseDTO;
import lk.ijse.computer_Shop.view.tdm.ServiseTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ServiseFromController {

    @FXML
    private JFXButton btnAddNewServise;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colSerivseId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ServiseTm> tblServise;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSerivse;

    SerivseBO serivseBO = (SerivseBO) Factory.getFactory().getBo(Factory.BOTypes.SERVISES);

    public void initialize() {
        colSerivseId.setCellValueFactory(new PropertyValueFactory<>("servId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        initUI();

        tblServise.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue==null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue==null);

            if (newValue !=null){
                txtSerivse.setText(newValue.getServId());
                txtName.setText(newValue.getName());
                txtEmpId.setText(newValue.getEmpId());
                txtPrice.setText(String.valueOf(newValue.getPrice()));

                tblServise.setDisable(false);
                txtName.setDisable(false);
                txtEmpId.setDisable(false);
                txtPrice.setDisable(false);

            }
        });
        txtPrice.setOnAction(event -> btnSave.fire());
        loadServise();

    }

    private void loadServise(){
        try {
            serivseBO.loadAllSerivse();
            ArrayList<ServiseDTO>allServise=serivseBO.loadAllSerivse();
            for (ServiseDTO s : allServise){
                tblServise.getItems().add(new ServiseTm(s.getServiseId(),s.getName(),s.getEmpId(),s.getPrice()));
            }
        }catch (SQLException e){

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI(){
        txtSerivse.clear();
        txtName.clear();
        txtEmpId.clear();
        txtPrice.clear();
        txtSerivse.setDisable(true);
        txtName.setDisable(true);
        txtEmpId.setDisable(true);
        txtPrice.setDisable(true);
        txtSerivse.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnNewSaveOnAction(ActionEvent event) {
        txtSerivse.setDisable(false);
        txtName.setDisable(false);
        txtEmpId.setDisable(false);
        txtPrice.setDisable(false);
        txtSerivse.clear();
        txtSerivse.setText(generateNewId());
        txtName.clear();
        txtEmpId.clear();
        txtPrice.clear();
        txtName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save Servise");
        tblServise.getSelectionModel().clearSelection();

    }

    private String generateNewId() {
        try {
            return serivseBO.generateNewIdSerivse();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "S00-001";
    }

    @FXML
    void btnSaveOnServiseAction(ActionEvent event) {
        String serivseId=txtSerivse.getText();
        String name=txtName.getText();
        String empId=txtEmpId.getText();
        double price= Double.parseDouble(txtPrice.getText());

        if (!name.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtName.requestFocus();
            return;
        } else if (!txtPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtPrice.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("Save Servise")) {
            try {
                if (existSerivses(serivseId)) {
                    new Alert(Alert.AlertType.ERROR, serivseId + " already exists").show();
                }
                //Save Servise

                serivseBO.saveSerivse(new ServiseDTO(serivseId, name, empId, price));

                tblServise.getItems().add(new ServiseTm(serivseId, name, empId, price));


            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            try {
                if (!existSerivses(serivseId)){
                    new Alert(Alert.AlertType.ERROR, "There is no such Servise associated with the id " + serivseId).show();
                }
                // update Servise

                serivseBO.updateSerivse(new ServiseDTO(serivseId,name,empId,price));

                ServiseTm serviseTm=tblServise.getSelectionModel().getSelectedItem();
                serviseTm.setName(name);
                serviseTm.setEmpId(empId);
                serviseTm.setPrice(price);
                tblServise.refresh();


            }catch (SQLException e){

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean existSerivses(String code) throws SQLException, ClassNotFoundException {
        return serivseBO.existSerivse(code);
    }

    @FXML
    void btnClearOnServiseAction(ActionEvent event) {
        //Delete Servise
        String code=tblServise.getSelectionModel().getSelectedItem().getServId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Deleted ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {

                serivseBO.deleteSerivse(code);
                tblServise.getItems().remove(tblServise.getSelectionModel().getSelectedItem());
                tblServise.getSelectionModel().clearSelection();
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

}
