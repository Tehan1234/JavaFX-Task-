package single;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AddCustomerForm {

    private static AddCustomerForm instance;
    private Stage stage;

    private AddCustomerForm() {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/add_customer_form.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Customer Form ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AddCustomerForm getInstance() {
        if (instance == null) {
            instance = new AddCustomerForm();
        }
        return instance;
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
        instance = null; // Allow a new instance to be created next time
    }
}
