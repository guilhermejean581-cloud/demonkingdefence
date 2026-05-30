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
import model.player;

public class InventarioController {
    @FXML private Label labelslot1, labelslot2, labelyen, labelnivelxp;
    private player player = model.player.getinstancia();

    @FXML public void initialize() { atualizarinterface(); }

    @FXML public void clicarboladefogo(ActionEvent e) {
        if (player.getmagias().contains("bola de fogo")) solicitarescolhadeslot("bola de fogo");
    }

    private void solicitarescolhadeslot(String magia) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("slot 1", "slot 1", "slot 2");
        dialog.setTitle("equipar");
        dialog.setContentText("escolha o slot:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(slot -> {
            if (slot.equals("slot 1")) player.equiparslot1(magia);
            else player.equiparslot2(magia);
            atualizarinterface();
        });
    }

    private void atualizarinterface() {
        labelslot1.setText("slot 1: " + player.getsloth1());
        labelslot2.setText("slot 2: " + player.getsloth2());
        labelyen.setText("dinheiro: " + player.getyen() + " yen");
        labelnivelxp.setText("nivel: " + player.getnivel() + " (xp: " + player.getxp() + "/" + (player.getnivel()*100) + ")");
    }

    @FXML public void acaovoltar(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/selecaoonda.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException ex) { }
    }
}