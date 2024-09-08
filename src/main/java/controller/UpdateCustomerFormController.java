package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Customer;
import single.BackFunction;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class UpdateCustomerFormController {

    public ComboBox <String> cmbTitles;
    @FXML
    private DatePicker DOB;



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

    CustomerService service = new CustomerController();

    @FXML
    void btnBackOnAction(ActionEvent event) {
        BackFunction dashForm = BackFunction.getInstance();
        dashForm.show();

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String custID = txtSearchBar.getText();
        Customer customer = service.searchCustomer(custID);
        if (Objects.equals(txtSearchBar.getText(), customer.getId())){

            txtId.setText(customer.getId());
            cmbTitles.setValue(customer.getTitle());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtContactNo.setText(customer.getContact());
            DOB.setValue(customer.getDob());

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
                    ((Stage) txtId.getScene().getWindow()).show();
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
        txtSearchBar.setText(null);
        txtId.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtContactNo.setText(null);
        cmbTitles.setValue(null);
        DOB.setValue(null);

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String title = cmbTitles.getValue();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContactNo.getText();
        LocalDate date = DOB.getValue();

        Customer customer = new Customer(id,title,name,address,contact,date);
        boolean isUpdated  = service.updateCustomer(customer);
        if (isUpdated){
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Customer Updated Successfully");
            successAlert.showAndWait();

            // Show confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Add Another?");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Do You Want to Update Another Customer?");
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
            alert.setHeaderText("Operation Failed");
            alert.setContentText("Customer Not Deleted!!!.");
        }


    }

}
