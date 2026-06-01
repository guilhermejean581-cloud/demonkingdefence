package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SelecaoOndaController {

    public static int ondaselecionada = 1;

    @FXML
    public void acaoAbrirInventario(ActionEvent event) {
        abrirtela(event, "/view/inventario.fxml");
    }
    
    @FXML
    public void acaoAbrirUpgrade(ActionEvent event) {
        abrirtela(event, "/view/upgrade.fxml");
    }

    private void tentaracessaronda(int onda, ActionEvent event) {
        if (onda <= SelecaoPersonagemController.ondasliberadas[SelecaoPersonagemController.slotativo]) {
            ondaselecionada = onda;
            abrirtela(event, "/view/gameplay.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "onda bloqueada! derrote a anterior.");
            alert.showAndWait();
        }
    }

    @FXML public void acaoOnda1(ActionEvent e) { tentaracessaronda(1, e); }
    @FXML public void acaoOnda2(ActionEvent e) { tentaracessaronda(2, e); }
    @FXML public void acaoOnda3(ActionEvent e) { tentaracessaronda(3, e); }
    @FXML public void acaoOnda4(ActionEvent e) { tentaracessaronda(4, e); }
    @FXML public void acaoOnda5(ActionEvent e) { tentaracessaronda(5, e); }

    private void abrirtela(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { }
    }
    
    @FXML
    public void acaoVoltar(ActionEvent event) {
        abrirtela(event, "/view/selecaopersonagem.fxml");
    }
}