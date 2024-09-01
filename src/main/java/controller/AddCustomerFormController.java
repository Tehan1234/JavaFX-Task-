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

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String contact = txtContactNo.getText();
        String birth = String.valueOf(dob.getValue());

        if (isValidPhoneNumber(contact) && isValidBirthday(birth)) {
            List<Customer> customerList = DBConnection.getInstance().getConnection();
            Customer customer = new Customer(txtId.getText(),  cmbTitles.getValue(), txtName.getText(), txtAddress.getText(), txtContactNo.getText(), dob.getValue());
            customerList.add(customer);
            System.out.println(customer);

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

        } else if (!isValidPhoneNumber(contact)) {
            // Show phone number error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Invalid Phone Number");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Enter Phone Number Again");
            errorAlert.showAndWait();
            txtContactNo.setText("");

        } else {
            // Show birthday error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Invalid Birthday");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Set the Birthday Again");
            errorAlert.showAndWait();
            dob.setValue(null);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        BackFunction dashForm = BackFunction.getInstance();
        dashForm.show();
    }

    private boolean isValidPhoneNumber(String number) {
        if (number.charAt(0) != '0') {
            return false;
        }
        if (number.length() != 10) {
            return false;
        }
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidBirthday(String birth) {
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate birthDate = LocalDate.parse(birth);
            return !birthDate.isAfter(currentDate);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr.");
        titles.add("Mrs.");
        titles.add("Miss");
        cmbTitle.setItems(titles);*/
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
