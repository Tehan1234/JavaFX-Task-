package controller;

import javafx.collections.ObservableList;
import model.Customer;

public interface CustomerService {


    boolean addCustomer(Customer customer);

    boolean   searchCustomer(String custID);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String id );

    ObservableList<Customer> getAll();






}
