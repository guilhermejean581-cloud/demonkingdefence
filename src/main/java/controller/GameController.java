package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Player;

public class GameController {

    @FXML private Label labelonda;
    @FXML private Label labelvidaplayer;
    @FXML private TextArea textlogbatalha;
    @FXML private Button btnataquebasico;
    @FXML private Button btnataqueespecial;
    @FXML private Button btnhabilidade1;
    @FXML private Button btnhabilidade2;
    @FXML private Button btninventario;

    private Player player;

    @FXML
    public void initialize() {
        player = new Player();
        atualizarinterface();
        textlogbatalha.appendText("a batalha comecou! os herois invadiram a dungeon.\n");
    }

    @FXML
    public void acaoataquebasico(ActionEvent event) {
        textlogbatalha.appendText("voce usou ataque basico.\n");
        turnoinimigos();
    }

    @FXML
    public void acaoataqueespecial(ActionEvent event) {
        textlogbatalha.appendText("voce usou ataque especial.\n");
        turnoinimigos();
    }

    @FXML
    public void acaohabilidade1(ActionEvent event) {
        textlogbatalha.appendText("voce congelou o tempo.\n");
        turnoinimigos();
    }

    @FXML
    public void acaohabilidade2(ActionEvent event) {
        textlogbatalha.appendText("voce usou poder demoniaco.\n");
        turnoinimigos();
    }

    @FXML
    public void abririnventario(ActionEvent event) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/view/inventario.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Stage stage = new Stage();
        stage.setTitle("inventario");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void turnoinimigos() {
        textlogbatalha.appendText("turno dos herois: o guerreiro atacou!\n");
        textlogbatalha.appendText("--- proximo turno ---\n");
    }

    private void atualizarinterface() {
        labelonda.setText("onda: 1");
        labelvidaplayer.setText("vida do rei demonio: 1000");
    }
}