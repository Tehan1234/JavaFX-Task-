package db;

import model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

        private  static  DBConnection instance;

        private Connection  connection;


    private DBConnection() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhoast:3306/smallshop","root","12345");
    }

    public  Connection getConnection(){

        return connection;
    }

    public static  DBConnection getInstance() throws SQLException {
        if(instance==null){
            return instance = new DBConnection();
        }
        return  instance;
    }




}
