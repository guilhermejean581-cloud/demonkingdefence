package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class RankingController {

    @FXML private ListView<String> listajogadores;
    @FXML private Label labeldetalhes;

    @FXML
    public void initialize() {
        SelecaoPersonagemController.carregardados();
        atualizarlista();
        
        listajogadores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("nenhum personagem criado")) {
                mostrardetalhes(newValue);
            }
        });
    }

    private void atualizarlista() {
        listajogadores.getItems().clear();
        for (int i = 0; i < 5; i++) {
            if (!SelecaoPersonagemController.nomes[i].isEmpty()) {
                listajogadores.getItems().add(SelecaoPersonagemController.nomes[i] + " - nv. " + SelecaoPersonagemController.niveis[i] + " - onda " + SelecaoPersonagemController.ondasliberadas[i]);
            }
        }
        
        listajogadores.getItems().sort((s1, s2) -> {
            int onda1 = Integer.parseInt(s1.split(" - onda ")[1]);
            int onda2 = Integer.parseInt(s2.split(" - onda ")[1]);
            
            if (onda1 == onda2) {
                int nv1 = Integer.parseInt(s1.split(" - nv. ")[1].split(" - ")[0]);
                int nv2 = Integer.parseInt(s2.split(" - nv. ")[1].split(" - ")[0]);
                return Integer.compare(nv2, nv1); 
            }
            return Integer.compare(onda2, onda1); 
        });

        if (listajogadores.getItems().isEmpty()) {
            listajogadores.getItems().add("nenhum personagem criado");
        }
        labeldetalhes.setText("selecione um personagem para ver os detalhes.");
    }

    private void mostrardetalhes(String textolista) {
        String nome = textolista.split(" - nv. ")[0];
        for (int i = 0; i < 5; i++) {
            if (SelecaoPersonagemController.nomes[i].equals(nome)) {
                String detalhes = "mortes: " + SelecaoPersonagemController.mortes[i] + "\n\n" +
                                  "herois eliminados: " + SelecaoPersonagemController.heroisderrotados[i] + "\n\n" +
                                  "chefes eliminados: " + SelecaoPersonagemController.chefesderrotados[i];
                labeldetalhes.setText(detalhes);
                break;
            }
        }
    }

    @FXML
    public void acaovoltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { }
    }
}