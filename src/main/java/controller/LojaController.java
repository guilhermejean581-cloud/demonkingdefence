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
import java.util.ArrayList;
import java.util.List;

public class LojaController {
    @FXML private Label labelyen;
    @FXML private Label labelaviso;
    private player player = model.player.getinstancia();

    @FXML public void initialize() { atualizaryen(); }

    private void atualizaryen() {
        if(labelyen != null) labelyen.setText("dinheiro: " + player.getyen() + " yen");
        if(labelaviso != null) labelaviso.setText("");
    }

    @FXML public void acaocomprarpocao(ActionEvent event) {
        List<String> items = new ArrayList<>();
        items.add("pocao de vida (50 yen)");
        items.add("pocao grande de vida (150 yen)");
        items.add("pocao de dano fisico (200 yen)");
        items.add("pocao de dano magico (200 yen)");
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>(items.get(0), items);
        dialog.setTitle("comprar pocoes");
        dialog.setHeaderText(null);
        dialog.setContentText("escolha a pocao desejada:");
        dialog.showAndWait().ifPresent(i -> {
            int custo = Integer.parseInt(i.replaceAll("[^0-9]", ""));
            if(player.gastaryen(custo)) {
                player.addpocao(i.split(" \\(")[0]);
                atualizaryen();
                labelaviso.setText("pocao comprada com sucesso!");
            } else {
                labelaviso.setText("yen insuficiente!");
            }
        });
    }

    @FXML public void acaocomprarmagia(ActionEvent event) {
        List<String> items = new ArrayList<>();
        items.add("missil de mana (300 yen)");
        items.add("relampago (500 yen)");
        items.add("cura (800 yen)");
        items.add("tempestade de fogo (1000 yen)");
        items.add("meteoro (1500 yen)");
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>(items.get(0), items);
        dialog.setTitle("comprar magias");
        dialog.setHeaderText(null);
        dialog.setContentText("escolha a magia desejada:");
        dialog.showAndWait().ifPresent(i -> {
            int custo = Integer.parseInt(i.replaceAll("[^0-9]", ""));
            String nome = i.split(" \\(")[0];
            if(player.getmagias().contains(nome)) {
                labelaviso.setText("voce ja possui esta magia!");
                return;
            }
            if(player.gastaryen(custo)) {
                player.addmagia(nome);
                atualizaryen();
                labelaviso.setText("magia comprada com sucesso!");
            } else {
                labelaviso.setText("yen insuficiente!");
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