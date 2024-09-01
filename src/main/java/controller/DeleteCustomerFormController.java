package controller;


import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Customer;
import single.BackFunction;
import java.util.List;
import java.util.Objects;


public class DeleteCustomerFormController  {

    public ComboBox cmbTitles;
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
    void btnDeleteOnAction(ActionEvent event) {
        List<Customer> customerList = DBConnection.getInstance().getConnection();
        for (Customer customer : customerList){
            if (Objects.equals(txtSearchBar.getText(), customer.getId())){
                customer.setId(null);
                customer.setName(null);
                customer.setAddress(null);
                customer.setContact(null);
                customer.setDob(null);
                customer.setTitle(null);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Customer Deleted Successfully");
                successAlert.showAndWait();

                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Add Another?");
                confirmDialog.setHeaderText(null);
                confirmDialog.setContentText("Do You Want to Delete Another Customer?");
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
}




