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
import lk.ijse.computer_Shop.bo.custom.EmployeesBO;
import lk.ijse.computer_Shop.model.EmployeesDTO;
import lk.ijse.computer_Shop.view.tdm.EmployeesTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class EmployessFromController {

    public JFXButton btnDelete;
    @FXML
    private JFXButton btnAddNewEmployess;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colEmpAddress;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EmployeesTm> tblEmployees;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtEMPAddress;

    @FXML
    private TextField txtEMPId;

    @FXML
    private TextField txtEMPName;

    @FXML
    private TextField txtSearch;

    EmployeesBO employeesBO = (EmployeesBO) Factory.getFactory().getBo(Factory.BOTypes.EMPLOYESS);

    public void initialize() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));

        initUI();

        tblEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue==null);
            btnSave.setText(newValue !=null ? "Update" : "Save");
            btnSave.setDisable(newValue==null);

            if (newValue !=null){
                txtEMPId.setText(newValue.getId());
                txtEMPName.setText(newValue.getEmpName());
                txtEMPAddress.setText(newValue.getAddress());
                txtCusId.setText(newValue.getCusId());

                txtEMPId.setDisable(false);
                txtEMPName.setDisable(false);
                txtEMPAddress.setDisable(false);
                txtCusId.setDisable(false);
            }
        });

        txtCusId.setOnAction(event -> btnSave.fire());
        loadEmployess();

    }

    private void loadEmployess(){
        tblEmployees.getItems().clear();

        try {
            employeesBO.loadAllEmployees();
            ArrayList<EmployeesDTO>allEmployess=employeesBO.loadAllEmployees();
            for (EmployeesDTO e : allEmployess){
                tblEmployees.getItems().add(new EmployeesTm(e.getEmpId(),e.getEmpName(),e.getAddress(),e.getCusId()));
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void initUI(){
        txtEMPId.clear();
        txtEMPName.clear();
        txtEMPAddress.clear();
        txtCusId.clear();
        txtEMPId.setDisable(true);
        txtEMPName.setDisable(true);
        txtEMPAddress.setDisable(true);
        txtCusId.setDisable(true);
        txtEMPId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnNewSaveOnAction(ActionEvent event) {
        txtEMPId.setDisable(false);
        txtEMPName.setDisable(false);
        txtEMPAddress.setDisable(false);
        txtCusId.setDisable(false);
        txtEMPId.clear();
        txtEMPId.setText(generateNewId());
        txtEMPName.clear();
        txtEMPAddress.clear();
        txtCusId.clear();
        txtEMPName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save Employess");
        tblEmployees.getSelectionModel().clearSelection();
    }

    @FXML
    void btnSaveOnEmployessAction(ActionEvent event) {
        String id=txtEMPId.getText();
        String empName=txtEMPName.getText();
        String empAddress=txtEMPAddress.getText();
        String cusId=txtCusId.getText();

        if (!empName.matches(".*[a-zA-Z0-9]{4,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtEMPName.requestFocus();
//            txtName.setFocusColor(Paint.valueOf("Red"));
            return;
        } else if (!txtEMPAddress.getText().matches(".*[a-zA-Z0-9]{4,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should not ").show();
            txtEMPAddress.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("Save Employess")){
            try {
                if (existEmployes(id)){
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                // save Employees

                employeesBO.saveEmployees(new EmployeesDTO(id,empName,empAddress,cusId));

                tblEmployees.getItems().add(new EmployeesTm(id,empName,empAddress,cusId));

            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            try{

                if (!existEmployes(id)){
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + id).show();
                }

                employeesBO.updateEmployees(new EmployeesDTO(id,empName,empAddress,cusId));

                EmployeesTm employeesTm=tblEmployees.getSelectionModel().getSelectedItem();
                employeesTm.setEmpName(empName);
                employeesTm.setAddress(empAddress);
                employeesTm.setCusId(cusId);
                tblEmployees.refresh();

            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
        btnAddNewEmployess.fire();
    }

    private boolean existEmployes(String code) throws SQLException, ClassNotFoundException {
        return employeesBO.existEmployees(code);
    }


    @FXML
    void btnClearOnEmpolyessAction(ActionEvent event) {
        //delete Employees
        String code=tblEmployees.getSelectionModel().getSelectedItem().getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Deleted ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {

                if (!existEmployes(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such Employees associated with the id " + code).show();
                }

                employeesBO.deleteEmployees(code);

                tblEmployees.getItems().remove(tblEmployees.getSelectionModel().getSelectedItem());
                tblEmployees.getSelectionModel().clearSelection();

                initUI();

            } catch (SQLException e) {

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private String generateNewId() {
        try {

            return employeesBO.generateNewIdEmployees();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "E00-001";
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
