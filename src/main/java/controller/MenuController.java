package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    @FXML private Button btnJogar;
    @FXML private Button btnRanking;
    @FXML private Button btnCreditos;
    @FXML private Button btnEncerrar;

    @FXML
    public void acaoJogar(ActionEvent event) {
        System.out.println("Iniciando Combate!");
        // Aqui você chamará a troca de tela para o GamePlay
    }

    @FXML
    public void acaoRanking(ActionEvent event) {
        System.out.println("Abrindo Ranking...");
    }

    @FXML
    public void acaoCreditos(ActionEvent event) {
        System.out.println("Desenvolvido por Otávio e Jean.");
    }

    @FXML
    public void acaoEncerrar(ActionEvent event) {
        System.exit(0);
    }
}