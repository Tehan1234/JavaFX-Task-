import controller.AddCustomerFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import single.BackFunction;

public class Starter extends Application {
    public static void main(String[] args) {
        launch();

    }

    @Override
    public void start(Stage stage) throws Exception {
        BackFunction dashForm = BackFunction.getInstance();
        dashForm.show();
    }
}
