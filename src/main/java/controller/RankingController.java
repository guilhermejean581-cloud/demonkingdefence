package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class RankingController {

    @FXML private ListView<String> listajogadores;
    @FXML private Label labeldetalhes;

    @FXML
    public void initialize() {
        SelecaoPersonagemController.carregarDados();
        atualizarlista();
        
        listajogadores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("nenhum personagem criado")) {
                mostrarDetalhes(newValue);
            }
        });
    }

    private void atualizarlista() {
        listajogadores.getItems().clear();
        for (int i = 0; i < 4; i++) {
            if (!SelecaoPersonagemController.nomes[i].isEmpty()) {
                listajogadores.getItems().add(SelecaoPersonagemController.nomes[i] + " - onda " + SelecaoPersonagemController.ondasLiberadas[i]);
            }
        }
        
        listajogadores.getItems().sort((s1, s2) -> {
            int onda1 = Integer.parseInt(s1.split(" - onda ")[1]);
            int onda2 = Integer.parseInt(s2.split(" - onda ")[1]);
            return Integer.compare(onda2, onda1); 
        });

        if (listajogadores.getItems().isEmpty()) {
            listajogadores.getItems().add("nenhum personagem criado");
        }
        labeldetalhes.setText("selecione um personagem para ver os detalhes.");
    }

    private void mostrarDetalhes(String textoLista) {
        String nome = textoLista.split(" - onda ")[0];
        for (int i = 0; i < 4; i++) {
            if (SelecaoPersonagemController.nomes[i].equals(nome)) {
                String detalhes = "mortes: " + SelecaoPersonagemController.mortes[i] + "\n" +
                                  "herois eliminados: " + SelecaoPersonagemController.heroisDerrotados[i] + "\n" +
                                  "chefes eliminados: " + SelecaoPersonagemController.chefesDerrotados[i];
                labeldetalhes.setText(detalhes);
                break;
            }
        }
    }

    @FXML
    public void acaodeletar(ActionEvent event) {
        String selecionado = listajogadores.getSelectionModel().getSelectedItem();
        if (selecionado != null && !selecionado.equals("nenhum personagem criado")) {
            String nomedeletar = selecionado.split(" - onda ")[0];
            for (int i = 0; i < 4; i++) {
                if (SelecaoPersonagemController.nomes[i].equals(nomedeletar)) {
                    SelecaoPersonagemController.nomes[i] = "";
                    SelecaoPersonagemController.ondasLiberadas[i] = 1;
                    SelecaoPersonagemController.mortes[i] = 0;
                    SelecaoPersonagemController.heroisDerrotados[i] = 0;
                    SelecaoPersonagemController.chefesDerrotados[i] = 0;
                    break;
                }
            }
            SelecaoPersonagemController.salvarDados();
            atualizarlista();
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("aviso");
            alerta.setHeaderText(null);
            alerta.setContentText("selecione um personagem na lista para deletar");
            alerta.showAndWait();
        }
    }

    @FXML
    public void acaovoltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}