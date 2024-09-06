package controller;

import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import single.SearchCustomerForm;

import java.sql.*;
import java.time.LocalDate;

public class CustomerController implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        String contact = customer.getContact();
        String birth = customer.getDob().toString();

        if (isValidPhoneNumber(contact) && isValidBirthday(birth)) {
            try {
                Connection connection = DBConnection.getInstance().getConnection();

                String insertSQL = "INSERT INTO customer VALUES(?,?,?,?,?,?)";

                PreparedStatement insertStmt = connection.prepareStatement(insertSQL);

                insertStmt.setString(1, customer.getId());
                insertStmt.setString(2, customer.getTitle());
                insertStmt.setString(3, customer.getName());
                insertStmt.setString(4, customer.getAddress());
                insertStmt.setString(5, customer.getContact());
                insertStmt.setDate(6, Date.valueOf(customer.getDob()));

                // Insert customer and check the result
                return insertStmt.executeUpdate() > 0;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (!isValidPhoneNumber(contact)) {
            // Show phone number error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Invalid Phone Number");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Enter a valid Phone Number Again");
            errorAlert.showAndWait();
        } else {
            // Show birthday error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Invalid Birthday");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Set a valid Birthday Again");
            errorAlert.showAndWait();
        }

        return false; // Return false if validation fails
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
            return !birthDate.isAfter(currentDate); // Return true if birthDate is not in the future
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean  searchCustomer(String custID) {

        try {
            String search = custID;

            Connection connection = DBConnection.getInstance().getConnection();

            String SQL = "SELECT * FROM customer WHERE CustName = ?,?,?";

            PreparedStatement insertStmt = connection.prepareStatement(SQL);

            insertStmt.setString(3, search);

            ResultSet resultSet = insertStmt.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("CustID");
                String  title = resultSet.getString("CustTitle");
                String names = resultSet.getString("CustName");
                String address = resultSet.getString("CustAddress");
                String contact= resultSet.getString("ContactNo");
                LocalDate date = resultSet.getDate("DOB").toLocalDate();

                SearchCustomerFormController searchCustForm = new SearchCustomerFormController();
                searchCustForm.txtId.setText(id);
                searchCustForm.cmbTitles.setValue(title);
                searchCustForm.txtName.setText(names);
                searchCustForm.txtAddress.setText(address);
                searchCustForm.txtContactNo.setText(contact);
                searchCustForm.DOB.setValue(date);

            }
            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public ObservableList<Customer> getAll() {
        return null;
    }
}
