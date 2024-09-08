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

    CustomerService service = new CustomerController();

    @FXML
    void btnBackOnAction(ActionEvent event) {
        BackFunction dashForm = BackFunction.getInstance();
        dashForm.show();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        boolean isDeleted = service.deleteCustomer(txtSearchBar.getText());
        if (isDeleted){
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Customer Deleted Successfully");
            successAlert.showAndWait();

            // Show confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Add Another?");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Do You Want to Delete Another Customer?");
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
                    ((Stage) txtId.getScene().getWindow()).show();
                    // Close the current window

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




