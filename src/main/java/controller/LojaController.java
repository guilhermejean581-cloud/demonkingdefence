package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LojaController {

    @FXML private Label labelmoedas;
    @FXML private Button btncomprarpocao;
    @FXML private Button btncomprarmagia;
    @FXML private Button btnproximaonda;

    @FXML
    public void initialize() {
        labelmoedas.setText("Pontos Demoniacos: 500");
    }

    @FXML
    public void acaocomprarpocao(ActionEvent event) {
        System.out.println("pocao comprada com sucesso");
    }

    @FXML
    public void acaocomprarmagia(ActionEvent event) {
        System.out.println("magia temporal adquirida");
    }

    @FXML
    public void acaoproximaonda(ActionEvent event) {
        System.out.println("carregando a proxima onda...");
    }
}