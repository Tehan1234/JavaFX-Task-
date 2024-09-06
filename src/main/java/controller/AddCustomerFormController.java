package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Customer;
import single.BackFunction;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {


    public DatePicker dob;
    public JFXButton btnAdd;
    public ComboBox <String> cmbTitles;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    private static int nextID = 100;

    CustomerService service = new CustomerController();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtId.getText();
        String title = cmbTitles.getValue();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContactNo.getText();
        LocalDate birth = dob.getValue();

        Customer customer = new Customer(id,title,name,address,contact,birth);
        boolean isAdded = service.addCustomer(customer);
        if (isAdded){
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Customer Added Successfully");
            successAlert.showAndWait();

            // Show confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Add Another?");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Do You Want to add Another Customer?");
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
            alert.setContentText("Customer Not Added!!!.");
        }


    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        BackFunction dashForm = BackFunction.getInstance();
        dashForm.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTitles.getItems().addAll("Mr","Mrs","Miss");

        txtId.setText(generateId());

        // Add key event handlers to move the cursor to the next text field
        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });

        txtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtContactNo.requestFocus();
            }
        });

        txtContactNo.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                dob.requestFocus(); // Moves focus to the DatePicker
            }
        });

        // Handle action for DatePicker (trigger btnAdd action when Enter is pressed)
        dob.getEditor().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnAdd.fire(); // Trigger the button's action
            }
        });
    }

    private static String generateId() {
        return "C" + nextID++;
    }

    public void btnClearAllOnAction(ActionEvent actionEvent) {
        clearAll();
    }

    private void clearAll() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNo.setText("");
        cmbTitles.setValue(null);
        dob.setValue(null);
        txtId.setText(generateId());
    }
}
