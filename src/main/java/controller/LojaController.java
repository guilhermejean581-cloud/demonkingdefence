package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.player;
import java.io.IOException;

public class LojaController {
    @FXML private Label labelyen;
    @FXML private Label labelaviso;
    @FXML private VBox painelItens;
    
    private player player = model.player.getinstancia();

    @FXML public void initialize() { 
        atualizaryen(); 
        mostrarPocoes(); // Inicia ja mostrando as pocoes
    }

    private void atualizaryen() {
        if(labelyen != null) labelyen.setText(String.valueOf(player.getouro()));
        if(labelaviso != null) labelaviso.setText("");
    }

    @FXML public void mostrarPocoes() {
        painelItens.getChildren().clear();
        labelaviso.setText("");
        
        adicionarItemLoja("pocao de vida", 25, "pocao");
        adicionarItemLoja("pocao grande de vida", 50, "pocao");
        adicionarItemLoja("pocao de dano fisico", 80, "pocao");
        adicionarItemLoja("pocao de dano magico", 80, "pocao");
    }

    @FXML public void mostrarMagias() {
        painelItens.getChildren().clear();
        labelaviso.setText("");
        
        adicionarItemLoja("missil de mana", 100, "magia");
        adicionarItemLoja("relampago", 200, "magia");
        adicionarItemLoja("cura", 200, "magia");
        adicionarItemLoja("tempestade de fogo", 300, "magia");
        adicionarItemLoja("meteoro", 400, "magia");
    }
    
    private void adicionarItemLoja(String nome, int custo, String tipo) {
        HBox linha = new HBox();
        linha.setSpacing(10);
        linha.setAlignment(javafx.geometry.Pos.CENTER);
        
        Label nomeLabel = new Label(nome);
        nomeLabel.setStyle("-fx-text-fill: #e0d0b0; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Empurra o botao de comprar para a direita
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label custoLabel = new Label("💰 " + custo);
        custoLabel.setStyle("-fx-text-fill: #ffd700; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        Button btnComprar = new Button("comprar");
        btnComprar.setStyle("-fx-background-color: #4a3b2c; -fx-text-fill: white; -fx-cursor: hand; -fx-border-color: #8c7355; -fx-border-radius: 5; -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 5px 15px;");
        
        btnComprar.setOnAction(e -> {
            if(tipo.equals("magia") && player.getmagias().contains(nome)) {
                labelaviso.setText("voce ja possui esta magia.");
                return;
            }
            if(player.gastarouro(custo)) {
                if(tipo.equals("pocao")) {
                    player.addpocao(nome);
                    labelaviso.setText("comprou " + nome + "!");
                    labelaviso.setStyle("-fx-text-fill: #66ff66; -fx-font-size: 16px; -fx-font-weight: bold;");
                } else if(tipo.equals("magia")) {
                    player.addmagia(nome);
                    labelaviso.setText("aprendeu " + nome + "!");
                    labelaviso.setStyle("-fx-text-fill: #66ff66; -fx-font-size: 16px; -fx-font-weight: bold;");
                }
                atualizaryen();
                SelecaoPersonagemController.salvardados();
            } else {
                labelaviso.setText("ouro insuficiente.");
                labelaviso.setStyle("-fx-text-fill: #ff6666; -fx-font-size: 16px; -fx-font-weight: bold;");
            }
        });
        
        linha.getChildren().addAll(nomeLabel, spacer, custoLabel, btnComprar);
        linha.setStyle("-fx-background-color: rgba(0,0,0,0.4); -fx-padding: 10px; -fx-border-color: #554433; -fx-border-radius: 5;");
        linha.setPrefWidth(500);
        
        painelItens.getChildren().add(linha);
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