package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.util.List;

public class ViewCustomerFormController {

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colContactNo;

    @FXML
    private TableColumn colDob;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colTitle;

    @FXML
    private TableView tblCustomers;

    @FXML
    void btnReloadOnAction(ActionEvent event) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        customerList.forEach(obj->{
            customerObservableList.add(obj);
        });
        tblCustomers.setItems(customerObservableList);



    }

}
