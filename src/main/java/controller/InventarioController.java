package controller;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.player;

public class InventarioController {
    
    @FXML private Label labelslot1;
    @FXML private Label labelslot2;
    @FXML private Label labelyen;
    @FXML private Label labelnivelxp;
    @FXML private Label labelhp;
    @FXML private Label labelatf;
    @FXML private Label labelatm;
    @FXML private Label labeldefesa;
    
    private player p = model.player.getinstancia();

    @FXML public void initialize() { 
        atualizarinterface(); 
        
        Platform.runLater(() -> {
            if (labelslot1 != null && labelslot1.getParent() instanceof AnchorPane) {
                AnchorPane parent = (AnchorPane) labelslot1.getParent();
                for (Node node : parent.getChildren()) {
                    if (node instanceof Button) {
                        Button btn = (Button) node;
                        String textobtn = btn.getText();
                        if (textobtn != null) {
                            
                            String txt = textobtn.replaceAll("\\s+", "").toLowerCase();
                            
                            if (txt.contains("+") || 
                                txt.equals("-") || 
                                txt.contains("equipar") || 
                                txt.contains("desbloquear")) {
                                
                                btn.setVisible(true);
                                btn.setOpacity(0.0); 
                                btn.toFront();
                            }
                            else if (txt.contains("subirnivel")) {
                                btn.setText("subir nivel (custo: " + (p.getnivel() * 100) + " pontos)");
                            }
                        }
                    }
                }
            }
        });
    }

    @FXML public void acaosubirnivel(ActionEvent e) {
        int custonivel = p.getnivel() * 100;
        if(p.getpontosdemoniacos() >= custonivel) {
            p.gastarpontos(custonivel);
            p.setnivel(p.getnivel() + 1);
            p.setpontosdistribuir(p.getpontosdistribuir() + 4);
            
            SelecaoPersonagemController.niveis[SelecaoPersonagemController.slotativo] = p.getnivel();
            SelecaoPersonagemController.salvardados();
            atualizarinterface();
            
            if (e.getSource() instanceof Button) {
                Button btn = (Button) e.getSource();
                btn.setText("subir nivel (custo: " + (p.getnivel() * 100) + " pontos)");
            }
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("sucesso");
            alert.setHeaderText(null);
            alert.setContentText("nivel aumentado com sucesso. voce ganhou 4 pontos de atributo.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("aviso");
            alert.setHeaderText(null);
            alert.setContentText("pontos insuficientes para subir de nivel. necessario: " + custonivel);
            alert.showAndWait();
        }
    }

    @FXML public void acaomaishp(ActionEvent e) {
        if (p.getpontosdistribuir() > 0) {
            p.setpontosdistribuir(p.getpontosdistribuir() - 1);
            p.sethp(p.gethp() + 1);
            SelecaoPersonagemController.salvardados();
            atualizarinterface();
        } else {
            mostraavisopontos();
        }
    }

    @FXML public void acaomaisatf(ActionEvent e) {
        if (p.getpontosdistribuir() > 0) {
            p.setpontosdistribuir(p.getpontosdistribuir() - 1);
            p.setatf(p.getatf() + 1);
            SelecaoPersonagemController.salvardados();
            atualizarinterface();
        } else {
            mostraavisopontos();
        }
    }

    @FXML public void acaomaisatm(ActionEvent e) {
        if (p.getpontosdistribuir() > 0) {
            p.setpontosdistribuir(p.getpontosdistribuir() - 1);
            p.setatm(p.getatm() + 1);
            SelecaoPersonagemController.salvardados();
            atualizarinterface();
        } else {
            mostraavisopontos();
        }
    }

    @FXML public void acaomaisdefesa(ActionEvent e) {
        if (p.getpontosdistribuir() > 0) {
            if (p.getdefesa() < 50) {
                p.setpontosdistribuir(p.getpontosdistribuir() - 1);
                p.setdefesa(p.getdefesa() + 1);
                SelecaoPersonagemController.salvardados();
                atualizarinterface();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("aviso");
                alert.setHeaderText(null);
                alert.setContentText("defesa maxima atingida");
                alert.showAndWait();
            }
        } else {
            mostraavisopontos();
        }
    }

    private void mostraavisopontos() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("aviso");
        alert.setHeaderText(null);
        alert.setContentText("voce nao possui pontos disponiveis para distribuir");
        alert.showAndWait();
    }

    @FXML public void acaoequipararma(ActionEvent e) {
        if(p.getarmas().isEmpty()) return;
        ChoiceDialog<String> d = new ChoiceDialog<>(p.getarmas().get(0), p.getarmas());
        d.setTitle("equipar arma");
        d.setHeaderText(null);
        d.setContentText("escolha:");
        d.showAndWait().ifPresent(w -> { 
            p.setarmaequipada(w); 
            SelecaoPersonagemController.salvardados(); 
            atualizarinterface(); 
        });
    }

    @FXML public void acaoequiparmagia1(ActionEvent e) {
        if(p.getmagias().isEmpty()) return;
        ChoiceDialog<String> d = new ChoiceDialog<>(p.getmagias().get(0), p.getmagias());
        d.setTitle("equipar magia 1");
        d.setHeaderText(null);
        d.setContentText("escolha:");
        d.showAndWait().ifPresent(m -> { 
            p.equiparslot1(m); 
            SelecaoPersonagemController.salvardados(); 
            atualizarinterface(); 
        });
    }

    @FXML public void acaoequiparmagia2(ActionEvent e) {
        if(p.getmagias().isEmpty()) return;
        ChoiceDialog<String> d = new ChoiceDialog<>(p.getmagias().get(0), p.getmagias());
        d.setTitle("equipar magia 2");
        d.setHeaderText(null);
        d.setContentText("escolha:");
        d.showAndWait().ifPresent(m -> { 
            p.equiparslot2(m); 
            SelecaoPersonagemController.salvardados(); 
            atualizarinterface(); 
        });
    }

    @FXML public void acaodesbloquearespecial(ActionEvent e) {
        if(p.getarmas().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("aviso");
            alert.setHeaderText(null);
            alert.setContentText("voce nao possui armas no inventario");
            alert.showAndWait();
            return;
        }
        ChoiceDialog<String> d = new ChoiceDialog<>(p.getarmas().get(0), p.getarmas());
        d.setTitle("desbloquear especial");
        d.setHeaderText(null);
        d.setContentText("escolha qual arma deseja desbloquear o ataque especial:");
        d.showAndWait().ifPresent(w -> { 
            if (p.temespecialdesbloqueado(w)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("aviso");
                alert.setHeaderText(null);
                alert.setContentText("o especial desta arma ja esta desbloqueado");
                alert.showAndWait();
            } else if (p.getpontosdemoniacos() >= 200) {
                p.gastarpontos(200);
                p.desbloquarespecial(w); 
                SelecaoPersonagemController.salvardados(); 
                atualizarinterface();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("sucesso");
                alert.setHeaderText(null);
                alert.setContentText("especial da arma " + w + " desbloqueado com sucesso");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("aviso");
                alert.setHeaderText(null);
                alert.setContentText("pontos insuficientes para desbloquear o especial (necessario 200 pontos)");
                alert.showAndWait();
            }
        });
    }

    private void atualizarinterface() {
        labelslot1.setText("arma: " + p.getarmaequipada());
        labelslot2.setText("m1: " + p.getsloth1() + " | m2: " + p.getsloth2());
        labelyen.setText("pontos demoniacos: " + p.getpontosdemoniacos() + " | pontos para distribuir: " + p.getpontosdistribuir());
        labelnivelxp.setText("nivel: " + p.getnivel());
        labelhp.setText("hp: " + p.getvidamaxima());
        labelatf.setText("atf: " + p.getatf());
        labelatm.setText("atm: " + p.getatm());
        labeldefesa.setText("defesa: " + p.getdefesa() + "%");
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