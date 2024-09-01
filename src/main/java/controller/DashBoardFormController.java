package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import single.*;



public class DashBoardFormController {

    @FXML
    void btnAddFormOnAction(ActionEvent event) {
        AddCustomerForm addCustomerForm = AddCustomerForm.getInstance();
        addCustomerForm.show();

    }

    @FXML
    void btnDeleteFormOnAction(ActionEvent event) {
        DeleteCustomerForm deleteCustomerForm  = DeleteCustomerForm.getInstance();
        deleteCustomerForm.show();

    }

    @FXML
    void btnSearchFormOnAction(ActionEvent event) {
        SearchCustomerForm searchCustomerForm = SearchCustomerForm.getInstance();
        searchCustomerForm.show();

    }

    @FXML
    void btnUpdateFormOnAction(ActionEvent event) {
        UpdateCustomerForm updateCustomerForm = UpdateCustomerForm.getInstance();
        updateCustomerForm.show();

    }

    @FXML
    void btnViewFormOnAction(ActionEvent event) {
        ViewCustomerForm viewCustomerForm = ViewCustomerForm.getInstance();
        viewCustomerForm.show();

    }

}
