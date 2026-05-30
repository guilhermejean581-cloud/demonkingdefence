package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.player;
import java.io.IOException;

public class LojaController {

    @FXML private Label labelyen;
    @FXML private Label labelaviso;
    
    private player player;

    @FXML
    public void initialize() {
        player = model.player.getinstancia();
        atualizaryen();
    }

    private void atualizaryen() {
        if (labelyen != null) {
            labelyen.setText("dinheiro: " + player.getyen() + " yen");
        }
        if (labelaviso != null) {
            labelaviso.setText("");
        }
    }

    @FXML
    public void acaocomprarpocao(ActionEvent event) {
        if (player.gastaryen(50)) {
            player.additem("pocao de cura");
            labelaviso.setText("pocao de cura adquirida.");
            atualizaryen();
        } else {
            labelaviso.setText("yen insuficiente.");
        }
    }

    @FXML
    public void acaocomprarmagia(ActionEvent event) {
        if (player.getmagias().contains("bola de fogo")) {
            labelaviso.setText("voce ja possui a magia bola de fogo.");
            return;
        }
        if (player.gastaryen(150)) {
            player.addmagia("bola de fogo");
            labelaviso.setText("magia bola de fogo adquirida.");
            atualizaryen();
        } else {
            labelaviso.setText("yen insuficiente.");
        }
    }

    @FXML
    public void acaocomprargreatsword(ActionEvent event) {
        if (player.getinventario().contains("great sword abencoada")) {
            labelaviso.setText("voce ja possui a great sword.");
            return;
        }
        if (player.gastaryen(300)) {
            player.additem("great sword abencoada");
            labelaviso.setText("great sword abencoada adquirida.");
            atualizaryen();
        } else {
            labelaviso.setText("yen insuficiente.");
        }
    }

    @FXML
    public void acaocomprarhab1(ActionEvent event) {
        if (player.getmagias().contains("corte sombrio")) {
            labelaviso.setText("corte sombrio ja adquirido.");
            return;
        }
        if (player.gastaryen(400)) {
            player.addmagia("corte sombrio");
            labelaviso.setText("corte sombrio adquirido.");
            atualizaryen();
        } else {
            labelaviso.setText("yen insuficiente.");
        }
    }

    @FXML
    public void acaocomprarhab2(ActionEvent event) {
        if (player.getmagias().contains("explosao infernal")) {
            labelaviso.setText("explosao infernal ja adquirida.");
            return;
        }
        if (player.gastaryen(500)) {
            player.addmagia("explosao infernal");
            labelaviso.setText("explosao infernal adquirida.");
            atualizaryen();
        } else {
            labelaviso.setText("yen insuficiente.");
        }
    }

    @FXML
    public void acaovoltarondas(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/selecaoonda.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { }
    }
}