package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class RankingController {

    @FXML private ListView<String> listajogadores;

    @FXML
    public void initialize() {
        listajogadores.getItems().add("1. jogadorA - 15 turnos - 2 mortes - onda 5");
        listajogadores.getItems().add("2. jogadorB - 22 turnos - 5 mortes - onda 4");
    }

    @FXML
    public void acaoexportarcsv(ActionEvent event) {
        System.out.println("arquivo csv gerado com sucesso");
    }

    @FXML
    public void acaoexportartxt(ActionEvent event) {
        System.out.println("arquivo txt gerado com sucesso");
    }

    @FXML
    public void acaovoltar(ActionEvent event) {
        System.out.println("voltando ao menu");
    }
}