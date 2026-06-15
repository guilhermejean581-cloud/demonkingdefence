package controller;

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
import java.io.IOException;
import java.util.List;

public class LojaController {
    @FXML private Label labelyen;
    @FXML private Label labelaviso;
    private player player = model.player.getinstancia();

    @FXML public void initialize() { atualizaryen(); }

    private void atualizaryen() {
        if(labelyen != null) labelyen.setText("ouro: " + player.getouro());
        if(labelaviso != null) labelaviso.setText("");
    }

    @FXML public void acaocomprarpocao(ActionEvent event) {
        List<String> items = List.of("pocao de vida (25)", "pocao grande de vida (50)", "pocao de dano fisico (80)", "pocao de dano magico (80)");
        ChoiceDialog<String> dialog = new ChoiceDialog<>(items.get(0), items);
        dialog.setTitle("comprar pocao");
        dialog.setHeaderText("escolha a pocao que deseja comprar");
        dialog.showAndWait().ifPresent(i -> {
            int custo = Integer.parseInt(i.replaceAll("[^0-9]", ""));
            if(player.gastarouro(custo)) {
                player.addpocao(i.split(" \\(")[0]);
                atualizaryen();
                SelecaoPersonagemController.salvardados();
            } else {
                labelaviso.setText("ouro insuficiente.");
            }
        });
    }

    @FXML public void acaocomprarmagia(ActionEvent event) {
        List<String> items = List.of("missil de mana (100)", "relampago (200)", "cura (200)", "tempestade de fogo (300)", "meteoro (400)");
        ChoiceDialog<String> dialog = new ChoiceDialog<>(items.get(0), items);
        dialog.setTitle("comprar magia");
        dialog.setHeaderText("escolha a magia que deseja comprar");
        dialog.showAndWait().ifPresent(i -> {
            int custo = Integer.parseInt(i.replaceAll("[^0-9]", ""));
            String nome = i.split(" \\(")[0];
            if(player.getmagias().contains(nome)) {
                labelaviso.setText("voce ja possui esta magia.");
                return;
            }
            if(player.gastarouro(custo)) {
                player.addmagia(nome);
                atualizaryen();
                SelecaoPersonagemController.salvardados();
            } else {
                labelaviso.setText("ouro insuficiente.");
            }
        });
    }

    @FXML public void acaovoltarondas(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/selecaoonda.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { }
    }
}