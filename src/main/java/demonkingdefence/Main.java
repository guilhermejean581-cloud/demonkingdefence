package demonkingdefence;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/menu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Demon King Defence");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}