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
    public void acaoabririnventario(ActionEvent event) {
        abrirtela(event, "/view/inventario.fxml");
    }

    private void tentaracessaronda(int onda, ActionEvent event) {
        if (onda <= SelecaoPersonagemController.ondasliberadas[SelecaoPersonagemController.slotativo]) {
            ondaselecionada = onda;
            abrirtela(event, "/view/gameplay.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "onda bloqueada derrote a anterior");
            alert.showAndWait();
        }
    }

    @FXML public void acaoonda1(ActionEvent e) { tentaracessaronda(1, e); }
    @FXML public void acaoonda2(ActionEvent e) { tentaracessaronda(2, e); }
    @FXML public void acaoonda3(ActionEvent e) { tentaracessaronda(3, e); }
    @FXML public void acaoonda4(ActionEvent e) { tentaracessaronda(4, e); }
    @FXML public void acaoonda5(ActionEvent e) { tentaracessaronda(5, e); }

    private void abrirtela(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) { }
    }
    
    @FXML
    public void acaovoltar(ActionEvent event) {
        abrirtela(event, "/view/selecaopersonagem.fxml");
    }
}