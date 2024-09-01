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
                confirmDialog.setContentText("No Customer Found");
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

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String title = cmbTitles.getValue();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContactNo.getText();
        LocalDate date = DOB.getValue();

        List<Customer>customerList = DBConnection.getInstance().getConnection();
        for (Customer customer : customerList){
            if (txtSearchBar.getText()==customer.getId()){
                customer.setId(id);
                customer.setTitle(title);
                customer.setName(name);
                customer.setAddress(address);
                customer.setContact(contact);
                customer.setDob(date);


                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Customer Update  Successfully");
                successAlert.showAndWait();

                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Update  Another?");
                confirmDialog.setHeaderText(null);
                confirmDialog.setContentText("Do You Want to Update  Another Customer?");
                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        txtSearchBar.setText(null);
                    } else {
                        // Close the current window
                        ((Stage) txtId.getScene().getWindow()).hide();
                    }
                });
            }
        }

    }

}
