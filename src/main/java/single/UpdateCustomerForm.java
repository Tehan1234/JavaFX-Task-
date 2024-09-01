package single;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class UpdateCustomerForm {

    private static UpdateCustomerForm instance;
    private Stage stage;

    private UpdateCustomerForm() {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/update_customer_form.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Customer Form ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static UpdateCustomerForm getInstance() {
        if (instance == null) {
            instance = new UpdateCustomerForm();
        }
        return instance;
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
        instance = null; // Allow a new instance to be created next time
    }
}
