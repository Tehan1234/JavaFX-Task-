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
    public  DatePicker DOB;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
   public  JFXTextField txtAddress;

    @FXML
    public  JFXTextField txtContactNo;

    @FXML
    public  JFXTextField txtId;

    @FXML
    public  JFXTextField txtName;

    @FXML
    public  JFXTextField txtSearchBar;

    CustomerService  service = new CustomerController();

    @FXML
    void btnBackOnAction(ActionEvent event) {
        BackFunction dashForm = BackFunction.getInstance();
        dashForm.show();
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String custID = txtSearchBar.getText();
        boolean isSearch = service.searchCustomer(custID);
        if (isSearch){
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Customer Search  Successfully");
            successAlert.showAndWait();

            // Show confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Add Another?");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Do You Want to Search Another Customer?");
            confirmDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    clearAll();
                } else {
                    // Close the current window
                    ((Stage) txtId.getScene().getWindow()).hide();
                }
            });

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Customer cannot search");
            alert.setContentText(" No Customer Found  !!!.");
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
