package single;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteCustomerForm {

    private static DeleteCustomerForm instance;
    private Stage stage;

    private DeleteCustomerForm() {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/delete_customer_form.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle(" Delete Customer Form ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DeleteCustomerForm getInstance() {
        if (instance == null) {
            instance = new DeleteCustomerForm();
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
