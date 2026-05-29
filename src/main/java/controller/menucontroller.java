
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class menucontroller {

    @FXML
    public void acaojogar(ActionEvent event) {
        System.out.println("iniciando a primeira onda de herois...");
    }

    @FXML
    public void acaoranking(ActionEvent event) {
        System.out.println("carregando o ranking dos jogadores...");
    }
    
    @FXML
    public void acaocreditos(ActionEvent event) {
        System.out.println("carregando creditos da dupla...");
    }

    @FXML
    public void acaoencerrar(ActionEvent event) {
        System.exit(0);
    }
}