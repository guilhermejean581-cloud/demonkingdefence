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

    public static int ondaSelecionada = 1;

    @FXML
    public void acaoAbrirInventario(ActionEvent event) {
        abrirTela(event, "/view/inventario.fxml");
    }

    private void tentarAcessarOnda(int onda, ActionEvent event) {
        if (onda <= SelecaoPersonagemController.ondasLiberadas[SelecaoPersonagemController.slotAtivo]) {
            ondaSelecionada = onda;
            abrirTela(event, "/view/gameplay.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "onda bloqueada! derrote a anterior.");
            alert.showAndWait();
        }
    }

    @FXML public void acaoOnda1(ActionEvent e) { tentarAcessarOnda(1, e); }
    @FXML public void acaoOnda2(ActionEvent e) { tentarAcessarOnda(2, e); }
    @FXML public void acaoOnda3(ActionEvent e) { tentarAcessarOnda(3, e); }
    @FXML public void acaoOnda4(ActionEvent e) { tentarAcessarOnda(4, e); }
    @FXML public void acaoOnda5(ActionEvent e) { tentarAcessarOnda(5, e); }

    private void abrirTela(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    @FXML
    public void acaoVoltar(ActionEvent event) {
        abrirTela(event, "/view/selecaopersonagem.fxml");
    }
}