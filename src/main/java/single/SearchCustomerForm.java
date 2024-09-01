package single;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SearchCustomerForm {

    private static SearchCustomerForm instance;
    private Stage stage;

    private SearchCustomerForm() {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/search_customer_form.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Search Customer Form ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SearchCustomerForm getInstance() {
        if (instance == null) {
            instance = new SearchCustomerForm();
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
