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
        ChoiceDialog<String> dialog = new ChoiceDialog<>("equipar arma", "equipar arma", "equipar magia 1", "equipar magia 2", "upar status (100 pontos)");
        dialog.setTitle("gerenciar");
        dialog.setHeaderText(null);
        dialog.setContentText("o que deseja fazer?");
        Optional<String> res = dialog.showAndWait();
        res.ifPresent(escolha -> {
            if(escolha.equals("equipar arma")) {
                ChoiceDialog<String> d2 = new ChoiceDialog<>(player.getarmas().get(0), player.getarmas());
                d2.showAndWait().ifPresent(w -> player.setarmaequipada(w));
            } else if(escolha.equals("equipar magia 1")) {
                ChoiceDialog<String> d2 = new ChoiceDialog<>(player.getmagias().get(0), player.getmagias());
                d2.showAndWait().ifPresent(m -> player.equiparslot1(m));
            } else if(escolha.equals("equipar magia 2")) {
                ChoiceDialog<String> d2 = new ChoiceDialog<>(player.getmagias().get(0), player.getmagias());
                d2.showAndWait().ifPresent(m -> player.equiparslot2(m));
            } else if(escolha.equals("upar status (100 pontos)")) {
                if(player.gastarpontos(100)) {
                    ChoiceDialog<String> d2 = new ChoiceDialog<>("hp", "hp", "atf", "atm", "defesa");
                    d2.showAndWait().ifPresent(s -> {
                        if(s.equals("hp")) player.sethp(player.gethp() + 8);
                        else if(s.equals("atf")) player.setatf(player.getatf() + 8);
                        else if(s.equals("atm")) player.setatm(player.getatm() + 8);
                        else if(s.equals("defesa")) player.setdefesa(Math.min(80, player.getdefesa() + 8));
                        player.setnivel(player.getnivel() + 1);
                        
                        SelecaoPersonagemController.niveis[SelecaoPersonagemController.slotativo] = player.getnivel();
                        SelecaoPersonagemController.salvardados();
                    });
                }
            }
            atualizarinterface();
        });
    }

    private void atualizarinterface() {
        labelslot1.setText("arma: " + player.getarmaequipada());
        labelslot2.setText("m1: " + player.getsloth1() + " | m2: " + player.getsloth2());
        labelyen.setText("pontos: " + player.getpontosdemoniacos());
        labelnivelxp.setText("nivel: " + player.getnivel() + " (hp:" + player.getvidamaxima() + " atf:" + player.getatf() + " atm:" + player.getatm() + " def:" + player.getdefesa() + ")");
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