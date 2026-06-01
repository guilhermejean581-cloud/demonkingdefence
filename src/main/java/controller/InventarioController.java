package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.player;

public class InventarioController {
    @FXML private Label labelslot1, labelslot2, labelslotarma, labelyen, labelnivelxp;
    @FXML private ListView<String> listainventario;
    
    private player player = model.player.getinstancia();

    @FXML public void initialize() { atualizarinterface(); }

    private void atualizarinterface() {
        labelslotarma.setText("arma: " + player.getarmaequipada());
        labelslot1.setText("magia 1: " + player.getsloth1());
        labelslot2.setText("magia 2: " + player.getsloth2());
        labelyen.setText("pontos: " + player.getpontosdemoniacos() + " | yen: " + player.getyen());
        labelnivelxp.setText("nivel: " + player.getnivel() + " (hp:" + player.getvidamaxima() + " atf:" + player.getatf() + " atm:" + player.getatm() + " def:" + player.getdefesa() + "%)");
        
        listainventario.getItems().clear();
        listainventario.getItems().addAll(player.getarmas());
        listainventario.getItems().addAll(player.getmagias());
        listainventario.getItems().addAll(player.getpocoes());
    }

    @FXML public void acaoequipar(ActionEvent e) {
        String selecionado = listainventario.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        if (player.getarmas().contains(selecionado)) {
            player.setarmaequipada(selecionado);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "arma " + selecionado + " equipada com sucesso!");
            a.setHeaderText(null);
            a.showAndWait();
        } else if (player.getmagias().contains(selecionado)) {
            List<String> slots = new ArrayList<>();
            slots.add("slot 1");
            slots.add("slot 2");
            
            ChoiceDialog<String> dialog = new ChoiceDialog<>(slots.get(0), slots);
            dialog.setTitle("equipar magia");
            dialog.setHeaderText(null);
            dialog.setContentText("escolha onde equipar a magia " + selecionado + ":");
            dialog.showAndWait().ifPresent(slot -> {
                if (slot.equals("slot 1")) player.equiparslot1(selecionado);
                else player.equiparslot2(selecionado);
            });
        } else if (player.getpocoes().contains(selecionado)) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "as pocoes nao se equipam. podes usa-las diretamente na batalha clicando no botao 'usar pocoes'!");
            a.setHeaderText(null);
            a.showAndWait();
        }
        atualizarinterface();
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