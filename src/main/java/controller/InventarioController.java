package controller;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Player;

public class InventarioController {
    @FXML private Label labelSlot1, labelSlot2, labelYen, labelNivelXp;
    private Player player = Player.getInstancia();

    @FXML public void initialize() { atualizarInterface(); }

    @FXML public void clicarBolaDeFogo(ActionEvent e) {
        if (player.isTemMagia()) solicitarEscolhaDeSlot("bola de fogo");
    }

    private void solicitarEscolhaDeSlot(String magia) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Slot 1", "Slot 1", "Slot 2");
        dialog.setTitle("Equipar");
        dialog.setContentText("Escolha o slot:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(slot -> {
            player.equiparHabilidade(slot.equals("Slot 1") ? 1 : 2, magia);
            atualizarInterface();
        });
    }

    private void atualizarInterface() {
        labelSlot1.setText("Slot 1: " + player.getSlotH1());
        labelSlot2.setText("Slot 2: " + player.getSlotH2());
        labelYen.setText("Dinheiro: " + player.getYen() + " Yen");
        labelNivelXp.setText("Nível: " + player.getNivel() + " (XP: " + player.getXp() + "/" + (player.getNivel()*100) + ")");
    }

    @FXML public void acaovoltar(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/selecaoonda.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException ex) { ex.printStackTrace(); }
    }
}