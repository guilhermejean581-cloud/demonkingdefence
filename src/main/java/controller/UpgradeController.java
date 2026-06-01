package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.player;

public class UpgradeController {
    @FXML private Label labelstatus;
    @FXML private Label labelpontos;
    @FXML private Label labelcusto;
    @FXML private Label labelaviso;
    @FXML private Label labelpontosdistribuir;

    private player player = model.player.getinstancia();

    @FXML public void initialize() { atualizarinterface(); }

    private int calcularcusto() {
        return player.getnivel() * 100;
    }

    private void atualizarinterface() {
        labelstatus.setText("nivel: " + player.getnivel() + "\nhp maximo: " + player.getvidamaxima() + "\natf: " + player.getatf() + "\natm: " + player.getatm() + "\ndefesa: " + player.getdefesa() + "%");
        labelpontos.setText("pontos demoniacos: " + player.getpontosdemoniacos());
        labelcusto.setText("custo para subir de nivel: " + calcularcusto() + " pontos");
        labelpontosdistribuir.setText("pontos de status para distribuir: " + player.getpontosdistribuir());
        labelaviso.setText("");
    }

    @FXML public void uparnivel(ActionEvent e) {
        int custo = calcularcusto();
        if (player.gastarpontos(custo)) {
            player.setnivel(player.getnivel() + 1);
            player.addpontosdistribuir(8); // ganha 8 pontos a cada nivel como pede o PDF
            
            SelecaoPersonagemController.niveis[SelecaoPersonagemController.slotativo] = player.getnivel();
            SelecaoPersonagemController.salvardados();
            
            atualizarinterface();
            labelaviso.setText("nivel aumentado! voce ganhou 8 pontos para distribuir.");
        } else {
            labelaviso.setText("pontos demoniacos insuficientes!");
        }
    }

    private void uparatributo(String atributo) {
        if (player.gastarpontodistribuir()) {
            if (atributo.equals("hp")) {
                player.sethp(player.gethp() + 1); // +1 na base = +10 HP maximo real
            } else if (atributo.equals("atf")) {
                player.setatf(player.getatf() + 1);
            } else if (atributo.equals("atm")) {
                player.setatm(player.getatm() + 1);
            } else if (atributo.equals("def")) {
                player.setdefesa(Math.min(80, player.getdefesa() + 1));
            }
            atualizarinterface();
        } else {
            labelaviso.setText("sem pontos de status! compre um nivel primeiro.");
        }
    }

    @FXML public void uparhp(ActionEvent e) { uparatributo("hp"); }
    @FXML public void uparatf(ActionEvent e) { uparatributo("atf"); }
    @FXML public void uparatm(ActionEvent e) { uparatributo("atm"); }
    @FXML public void upardefesa(ActionEvent e) { uparatributo("def"); }

    @FXML public void acaovoltar(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/selecaoonda.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException ex) { }
    }
}