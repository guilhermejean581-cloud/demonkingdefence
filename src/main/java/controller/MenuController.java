package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML private Button btnjogar;
    @FXML private Button btnranking;
    @FXML private Button btncreditos;
    @FXML private Button btnencerrar;

    @FXML
    public void acaojogar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/selecaopersonagem.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void acaoranking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ranking.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void acaocreditos(ActionEvent event) {
        System.out.println("desenvolvido por otavio e jean");
    }

    @FXML
    public void acaoencerrar(ActionEvent event) {
        System.out.println("encerrando o jogo");
        System.exit(0);
    }
}