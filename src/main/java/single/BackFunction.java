package single;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class BackFunction {

    private static BackFunction instance;
    private Stage stage;

    private BackFunction() {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/dash_board_form.fxml"));
            Parent root = loader.load();
            stage.setTitle("Home page");
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BackFunction getInstance() {
        if (instance == null) {
            instance = new BackFunction();
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
