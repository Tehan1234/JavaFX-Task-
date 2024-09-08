package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
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
    public Customer searchCustomer(String custID) {
        try {
            // Establishing the connection
            Connection connection = DBConnection.getInstance().getConnection();

            // SQL query to search for customer by custID
            String SQL = "SELECT * FROM customer WHERE CustID = ?";

            // Prepare statement with the query
            PreparedStatement stmt = connection.prepareStatement(SQL);

            // Setting the custID parameter in the SQL query
            stmt.setString(1, custID);

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Check if the result set has data
            if (resultSet.next()) {
                // Extract customer details from the result set
                String id = resultSet.getString("CustID");
                String title = resultSet.getString("CustTitle");
                String names = resultSet.getString("CustName");
                String address = resultSet.getString("CustAddress");
                String contact = resultSet.getString("ContactNo");
                LocalDate date = resultSet.getDate("DOB").toLocalDate();

                // Create and return a new Customer object with the fetched details
                return new Customer(id, title, names, address, contact, date);
            } else {
                // If no customer found, return null or handle accordingly
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            // Establishing the connection
            Connection connection = DBConnection.getInstance().getConnection();

            // SQL query to update customer details based on the CustID
            String updateSQL = "UPDATE customer SET CustTitle = ?, CustName = ?, CustAddress = ?, ContactNo = ?, DOB = ? WHERE CustID = ?";

            // Prepare statement with the query
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);

            // Setting the parameters for the prepared statement
            updateStmt.setString(1, customer.getTitle()); // Title
            updateStmt.setString(2, customer.getName());  // Name
            updateStmt.setString(3, customer.getAddress());  // Address
            updateStmt.setString(4, customer.getContact()); // Contact No
            updateStmt.setDate(5, Date.valueOf(customer.getDob())); // DOB (convert LocalDate to SQL Date)
            updateStmt.setString(6, customer.getId()); // Where clause - CustID

            // Execute the update and check if any rows were affected
            return updateStmt.executeUpdate() > 0;

        } catch (SQLException e) {
            // Handle the SQL exception
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "DELETE FROM customer WHERE CustID = '" + id+ "'";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return  connection.createStatement().executeUpdate(SQL) > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM customer";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("ContactNo"),
                        resultSet.getDate("DOB").toLocalDate()

                );
                customerObservableList.add(customer);
            }
            return  customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
