package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class InventarioController {

    @FXML private ListView<String> listaitens;

    @FXML
    public void initialize() {
        listaitens.getItems().add("great sword abencoada");
        listaitens.getItems().add("pocao de cura");
        listaitens.getItems().add("coracao de deus");
    }
}