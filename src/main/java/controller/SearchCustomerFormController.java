package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Customer;
import single.BackFunction;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchCustomerFormController  {

    public ComboBox <String> cmbTitles;
    @FXML
    private DatePicker DOB;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchBar;

    @FXML
    void btnBackOnAction(ActionEvent event) {
        BackFunction dashForm = BackFunction.getInstance();
        dashForm.show();
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) {
        List<Customer> customerList = DBConnection.getInstance().getConnection();
        for (Customer customer : customerList){
            if (txtSearchBar.getText().equals(customer.getId())){
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtContactNo.setText(customer.getContact());
                cmbTitles.setValue(String.valueOf(customer.getTitle()));
                DOB.setValue(customer.getDob());
            }else {
                clearAll();
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("No Customer Found");
                confirmDialog.setHeaderText(null);
                confirmDialog.setContentText("Do You Want to Search Another Customer?");

                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                       txtSearchBar.setText(null);
                    } else {
                        // Close the current window
                        ((Stage) txtSearchBar.getScene().getWindow()).hide();
                    }
                });
            }
        }





    }
    private void clearAll(){
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNo.setText("");
        cmbTitles.setValue(null);
        DOB.setValue(null);

    }



}
