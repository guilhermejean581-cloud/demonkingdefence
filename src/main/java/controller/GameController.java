package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

public class GameController {
    @FXML private Label labelonda, labelvidaplayer, labelnomeinimigo, labelvidainimigo, labelnomeplayer;
    @FXML private ProgressBar barrahpplayer, barrahpinimigo;
    @FXML private TextArea textlogbatalha;
    @FXML private Button btnproximaonda, btnirparaloja, btnataquefisico, btnataquemagico, btnfugir, btnhabilidade1, btnhabilidade2;
    @FXML private VBox painelvitoria, listadrops;

    private player player = model.player.getinstancia();
    private gerenciadordeondas gerenciadorondas = new gerenciadordeondas();
    private onda ondaatual;
    private heroi inimigoatual;
    private int indexinimigo, vidaatualplayer, vidamaximainimigo;
    private boolean bufffisico = false;
    private boolean buffmagico = false;
    private int turnosbuffaprimoramento = 0;
    private boolean maldicaolich = false;
    private int turnostempestade = 0;
    private int castarmeteoro = 0;
    private int danoreduzido = 0;

    private int cdespecial = 0;
    private int cdhabilidade1 = 0;
    private int cdhabilidade2 = 0;
    private int turnosbonusfisico = 0;
    private int turnosgerais = 0;
    private int turnosprofecia = 0;

    @FXML public void initialize() {
        vidaatualplayer = player.getvidamaxima();
        ondaatual = gerenciadorondas.getonda(SelecaoOndaController.ondaselecionada);
        indexinimigo = 0;
        btnproximaonda.setVisible(false);
        btnirparaloja.setVisible(false);
        if(painelvitoria != null) {
            painelvitoria.setVisible(false);
            painelvitoria.setManaged(false);
        }
        textlogbatalha.setText("batalha iniciada.\n");

        carregarproximoinimigo();
        atualizarinterface();
    }

    private void carregarproximoinimigo() {
        if (indexinimigo < ondaatual.getinimigos().size()) {
            inimigoatual = ondaatual.getinimigos().get(indexinimigo);
            vidamaximainimigo = inimigoatual.getvida();
            textlogbatalha.appendText("inimigo " + inimigoatual.getclasse() + " apareceu.\n");
        } else {
            textlogbatalha.appendText("onda concluida.\n");
            inimigoatual = null;
            btnproximaonda.setVisible(true);
            btnirparaloja.setVisible(true);
            desativarbotoes();
            SelecaoPersonagemController.salvardados();
        }
        atualizarinterface();
    }

    @FXML public void acaoataquebasico(ActionEvent e) { executarturno("ataque_basico"); }
    
    @FXML public void acaoataquemagico(ActionEvent e) { 
        if (!player.temespecialdesbloqueado(player.getarmaequipada())) {
            textlogbatalha.appendText("ataque especial bloqueado.\n");
            return;
        }
        if (cdespecial > 0) {
            textlogbatalha.appendText("ataque especial em recarga.\n");
            return;
        }
        executarturno("ataque_especial"); 
    }
    
    @FXML public void acaohabilidade1(ActionEvent e) { 
        String h = player.getsloth1();
        if(h.equals("vazio")) return;
        if (cdhabilidade1 > 0) {
            textlogbatalha.appendText("habilidade em recarga.\n");
            return;
        }
        executarturno(h); 
    }
    
    @FXML public void acaohabilidade2(ActionEvent e) { 
        String h = player.getsloth2();
        if(h.equals("vazio")) return;
        if (cdhabilidade2 > 0) {
            textlogbatalha.appendText("habilidade em recarga.\n");
            return;
        }
        executarturno(h); 
    }
    
    @FXML public void acaofugir(ActionEvent e) {
        if(player.getpocoes().isEmpty()) { textlogbatalha.appendText("sem pocoes.\n"); return; }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(player.getpocoes().get(0), player.getpocoes());
        dialog.showAndWait().ifPresent(pocao -> {
            player.getpocoes().remove(pocao);
            if(pocao.equals("pocao de vida")) curarplayer(50);
            else if(pocao.equals("pocao grande de vida")) curarplayer(150);
            else if(pocao.equals("pocao de dano fisico")) bufffisico = true;
            else if(pocao.equals("pocao de dano magico")) buffmagico = true;
            textlogbatalha.appendText("usou " + pocao + ".\n");
            SelecaoPersonagemController.salvardados();
            turnoinimigos();
            fimdeturno();
        });
    }

    private void curarplayer(int valor) {
        if(!maldicaolich) vidaatualplayer = Math.min(player.getvidamaxima(), vidaatualplayer + valor);
    }

    private void executarturno(String acao) {
        if (inimigoatual == null || acao.equals("vazio")) return;
        heroi alvo = inimigoatual;
        boolean inimigoatacou = false;
        if (alvo.isatacaprimeiro()) { turnoinimigos(); inimigoatacou = true; }
        if (vidaatualplayer > 0) processaracaoplayer(acao);
        if (inimigoatual == alvo && inimigoatual != null && inimigoatual.getvida() > 0 && !inimigoatacou) turnoinimigos();
        fimdeturno();
    }

    private void processaracaoplayer(String acao) {
        String arma = player.getarmaequipada();
        int basearma = 25;
        if(arma.equals("machado de guerra")) basearma = 35;
        else if(arma.equals("arco rapido")) basearma = 30;
        else if(arma.equals("espada de honra")) basearma = 50;
        else if(arma.equals("espada nobre")) basearma = 60;
        else if(arma.equals("arco magico")) basearma = 60;
        else if(arma.equals("espada sagrada")) basearma = 100;
        else if(arma.equals("arco sagrado")) basearma = 75;

        double multf = 1.0 + (player.getatf() * 0.15);
        double multm = 1.0 + (player.getatm() * 0.15);
        
        if(bufffisico) multf += 0.3;
        if(buffmagico) multm += 0.3;
        if(turnosbuffaprimoramento > 0) { multf += 0.5; multm += 0.5; }
        if(turnosbonusfisico > 0) { multf += 0.4; }

        if (acao.equals("ataque_especial")) {
            if (arma.equals("espada antiga")) cdespecial = 1;
            else if (arma.equals("arco rapido")) cdespecial = 2;
            else cdespecial = 2;
        } else if (acao.equals(player.getsloth1())) {
            cdhabilidade1 = 2;
        } else if (acao.equals(player.getsloth2())) {
            cdhabilidade2 = 2;
        }

        if (arma.equals("arco rapido") && acao.equals("ataque_especial")) {
            for (int h = 0; h < 3; h++) {
                if (inimigoatual != null && inimigoatual.getvida() > 0) {
                    int d = (int)(basearma * multf * 0.6);
                    inimigoatual.receberdano(d);
                    textlogbatalha.appendText("arco rapido sequencial causou " + d + " de dano.\n");
                    verificarmorteinimigo();
                }
            }
            return;
        }

        if (arma.equals("arco magico") && acao.equals("ataque_especial")) {
            for (int h = 0; h < 2; h++) {
                if (inimigoatual != null && inimigoatual.getvida() > 0) {
                    int d = (int)(basearma * (multf + (player.getatm() * 0.15)) * 0.8);
                    inimigoatual.receberdano(d);
                    textlogbatalha.appendText("arco magico sequencial causou " + d + " de dano.\n");
                    verificarmorteinimigo();
                }
            }
            return;
        }

        int dano = 0;
        boolean magico = false;

        if(acao.equals("ataque_basico")) {
            dano = (int)(basearma * multf);
            if(arma.equals("arco magico")) dano = (int)(basearma * (multf + (player.getatm() * 0.15)));
        } else if(acao.equals("ataque_especial")) {
            if(arma.equals("espada antiga")) { dano = (int)(basearma * multf * 2); }
            else if(arma.equals("machado de guerra")) { 
                dano = (int)(basearma * multf * 1.5); 
                player.setdefesa(player.getdefesa() + 1);
                textlogbatalha.appendText("defesa aumentada.\n");
            }
            else if(arma.equals("espada de honra")) { 
                dano = (int)(basearma * multf); 
                turnosbonusfisico = 2;
                textlogbatalha.appendText("bonus fisico.\n");
            }
            else if(arma.equals("espada nobre")) { 
                dano = (int)(basearma * multf); 
                if(inimigoatual.getvida() < vidamaximainimigo * 0.3) dano *= 2; 
                int h = (int)(dano * 0.3);
                curarplayer(h);
                textlogbatalha.appendText("regenerou hp.\n");
            }
            else if(arma.equals("espada sagrada")) { 
                dano = (int)(basearma * multf); 
                int h = (int)(dano * 0.5);
                curarplayer(h);
                textlogbatalha.appendText("regenerou hp.\n");
            }
            else if(arma.equals("arco sagrado")) { dano = (int)(basearma * multf * 2); }
        } else if(acao.equals("bola de fogo")) { dano = (int)(25 * multm); magico = true; }
        else if(acao.equals("missil de mana")) { dano = (int)(35 * multm); magico = true; }
        else if(acao.equals("relampago")) { dano = (int)(50 * multm); magico = true; }
        else if(acao.equals("cura")) { curarplayer((int)(player.getvidamaxima() * 0.2)); textlogbatalha.appendText("curou.\n"); return; }
        else if(acao.equals("tempestade de fogo")) { dano = (int)(80 * multm); magico = true; }
        else if(acao.equals("meteoro")) { dano = (int)(100 * multm); magico = true; }
        else if(acao.equals("tornado")) { dano = (int)(50 * multm); magico = true; turnostempestade = 3; }
        else if(acao.equals("tempestade de gelo")) { dano = (int)(90 * multm); magico = true; danoreduzido = 30; }
        else if(acao.equals("explosao")) {
            if(castarmeteoro == 0) { castarmeteoro = 1; textlogbatalha.appendText("preparando explosao.\n"); return; }
            else { dano = (int)(120 * multm); magico = true; castarmeteoro = 0; }
        }
        else if(acao.equals("restauracao")) { curarplayer((int)(player.getvidamaxima() * 0.4)); textlogbatalha.appendText("restaurou.\n"); return; }
        else if(acao.equals("aprimoramento")) { turnosbuffaprimoramento = 3; textlogbatalha.appendText("aprimorado.\n"); return; }
        else if(acao.equals("maldicao")) { textlogbatalha.appendText("amaldicoado.\n"); return; }

        if(inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            if(magico && c.isimunemagico()) dano = 0;
            if(magico && c.isreduzmagico()) dano /= 2;
            if(!magico && c.isreduzfisico()) dano /= 2;
        }

        inimigoatual.receberdano(dano);
        textlogbatalha.appendText("causou " + dano + " de dano.\n");
        verificarmorteinimigo();
    }

    private void verificarmorteinimigo() {
        if (inimigoatual == null) return;
        if (inimigoatual.getvida() <= 0) {
            textlogbatalha.appendText("derrotado.\n");
            player.addpontos(inimigoatual.getpontos());
            
            listadrops.getChildren().clear();
            boolean tevedrop = false;
            
            double rolagemdrop = Math.random() * 100;
            if (rolagemdrop <= inimigoatual.getchancedrop()) {
                String drop = inimigoatual.getdrop();
                if (drop != null && !drop.equals("nada") && !drop.isEmpty()) {
                    if (drop.contains("pocao") || drop.contains("elixir") || drop.contains("coracao")) {
                        player.addpocao(drop);
                    } else if (drop.contains("espada") || drop.contains("arco") || drop.contains("machado")) {
                        player.addarma(drop);
                    } else {
                        player.addmagia(drop);
                    }
                    textlogbatalha.appendText("dropou: " + drop + "\n");
                    Label labeldrop = new Label("drop: " + drop);
                    labeldrop.setStyle("-fx-text-fill: white;");
                    listadrops.getChildren().add(labeldrop);
                    tevedrop = true;
                }
            }
            
            if (inimigoatual instanceof chefe) {
                player.addouro(100);
                textlogbatalha.appendText("chefe derrotado.\n");
                Label labelouro = new Label("100 ouro");
                labelouro.setStyle("-fx-text-fill: white;");
                listadrops.getChildren().add(labelouro);
                tevedrop = true;
                SelecaoPersonagemController.chefesderrotados[SelecaoPersonagemController.slotativo]++;
                if (SelecaoOndaController.ondaselecionada >= SelecaoPersonagemController.ondasliberadas[SelecaoPersonagemController.slotativo]) {
                    SelecaoPersonagemController.ondasliberadas[SelecaoPersonagemController.slotativo] = SelecaoOndaController.ondaselecionada + 1;
                }
                SelecaoPersonagemController.niveis[SelecaoPersonagemController.slotativo] = player.getnivel();
            }
            
            if(!tevedrop) {
                Label semdrop = new Label("nenhum item");
                semdrop.setStyle("-fx-text-fill: white;");
                listadrops.getChildren().add(semdrop);
            }
            
            painelvitoria.setVisible(true);
            painelvitoria.setManaged(true);
            desativarbotoes();
        }
    }

    private void turnoinimigos() {
        if (inimigoatual == null) return;
        int danoi = inimigoatual.getdano();
        
        boolean ignorardefesa = false;
        if (inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            c.aplicaraumentodano();
            danoi = c.getdano();
            if (c.isamaldicoa()) maldicaolich = true;
            if (c.getclasse().toLowerCase().contains("deusa") || c.getclasse().toLowerCase().contains("rei")) {
                ignorardefesa = true;
            }
        } else if (inimigoatual.getclasse().contains("mago") && Math.random() < 0.25) {
            danoi += (danoi * 0.2);
        }

        if (inimigoatual.getclasse().toLowerCase().contains("profecia")) {
            if (turnosprofecia == 0) {
                danoi = 200;
                turnosprofecia = 1;
            } else if (turnosprofecia == 1) {
                danoi = 50;
                turnosprofecia = 2;
            } else {
                danoi = 50;
                turnosprofecia = 0;
            }
        }
        
        if (danoreduzido > 0) { danoi -= (danoi * danoreduzido / 100); danoreduzido = 0; }
        
        double multdef = ignorardefesa ? 0.0 : Math.min(0.5, player.getdefesa() / 100.0);
        int danofinal = (int)(danoi * (1.0 - multdef));
        
        if(player.getarmaequipada().equals("espada de honra")) inimigoatual.receberdano((int)(danofinal * 0.25));
        
        vidaatualplayer -= Math.max(1, danofinal);
        textlogbatalha.appendText("sofreu " + danofinal + " de dano.\n");
        if (vidaatualplayer <= 0) {
            SelecaoPersonagemController.mortes[SelecaoPersonagemController.slotativo]++;
            SelecaoPersonagemController.niveis[SelecaoPersonagemController.slotativo] = player.getnivel();
            SelecaoPersonagemController.salvardados();
            trocartela("/view/morte.fxml");
        }
    }

    private void fimdeturno() {
        turnosgerais++;
        if (turnostempestade > 0 && inimigoatual != null) {
            inimigoatual.receberdano(50);
            turnostempestade--;
            verificarmorteinimigo();
        }
        if (turnosbuffaprimoramento > 0) turnosbuffaprimoramento--;
        if (turnosbonusfisico > 0) turnosbonusfisico--;
        
        if (inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            if (c.getcuraturno() > 0) c.curar(c.getcuraturno());
            if (c.getclasse().toLowerCase().contains("deusa")) {
                if (turnosgerais % 5 == 0) {
                    c.curar(1000);
                    textlogbatalha.appendText("deusa regenerou 1000 hp.\n");
                }
            }
        }
        
        if (SelecaoOndaController.ondaselecionada == 3) {
            boolean temsanta = false;
            for (heroi h : ondaatual.getinimigos()) {
                if (h != null && h.getvida() > 0 && h.getclasse().toLowerCase().contains("santa")) {
                    temsanta = true;
                    break;
                }
            }
            if (temsanta) {
                for (heroi h : ondaatual.getinimigos()) {
                    if (h != null && h.getvida() > 0) {
                        h.receberdano(-40);
                    }
                }
                textlogbatalha.appendText("santa curou.\n");
            }
        }
        
        if (cdespecial > 0) cdespecial--;
        if (cdhabilidade1 > 0) cdhabilidade1--;
        if (cdhabilidade2 > 0) cdhabilidade2--;
        
        atualizarinterface();
    }

    private void atualizarinterface() {
        labelonda.setText("onda " + SelecaoOndaController.ondaselecionada);
        labelvidaplayer.setText("hp " + Math.max(0, vidaatualplayer) + "/" + player.getvidamaxima());
        
        String nomepersonagem = SelecaoPersonagemController.nomes[SelecaoPersonagemController.slotativo];
        labelnomeplayer.setText(nomepersonagem + " lv. " + player.getnivel());

        btnataquefisico.setText("atacar");
        btnataquemagico.setText("especial");
        btnhabilidade1.setText(player.getsloth1());
        btnhabilidade2.setText(player.getsloth2());
        btnfugir.setText("pocoes (" + player.getpocoes().size() + ")");
        
        barrahpplayer.setProgress((double) Math.max(0, vidaatualplayer) / player.getvidamaxima());
        if (inimigoatual != null) {
            labelnomeinimigo.setText(inimigoatual.getclasse() + " lv. " + inimigoatual.getlevel());
            labelvidainimigo.setText(Math.max(0, inimigoatual.getvida()) + "/" + vidamaximainimigo);
            barrahpinimigo.setProgress((double) Math.max(0, inimigoatual.getvida()) / vidamaximainimigo);
        }
    }

    private void trocartela(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) labelonda.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (Exception ex) { }
    }

    private void desativarbotoes() {
        btnataquefisico.setDisable(true);
        btnataquemagico.setDisable(true);
        btnfugir.setDisable(true);
        btnhabilidade1.setDisable(true);
        btnhabilidade2.setDisable(true);
    }
    
    private void reativarbotoes() {
        btnataquefisico.setDisable(false);
        btnataquemagico.setDisable(false);
        btnfugir.setDisable(false);
        btnhabilidade1.setDisable(false);
        btnhabilidade2.setDisable(false);
    }

    @FXML public void acaoproximaonda(ActionEvent e) {
        if (SelecaoOndaController.ondaselecionada < 6) {
            SelecaoOndaController.ondaselecionada++;
            trocartela("/view/gameplay.fxml");
        }
    }
    
    @FXML public void acaoirparaloja(ActionEvent e) { trocartela("/view/loja.fxml"); }
    
    @FXML public void acaocontinuar(ActionEvent e) {
        painelvitoria.setVisible(false);
        painelvitoria.setManaged(false);
        reativarbotoes();
        
        if (inimigoatual instanceof chefe) {
            textlogbatalha.clear(); 
            textlogbatalha.appendText("onda concluida. chefe derrotado.\n");
            inimigoatual = null;
            btnproximaonda.setVisible(true);
            btnirparaloja.setVisible(true);
            desativarbotoes();
            SelecaoPersonagemController.salvardados();
        } else {
            textlogbatalha.clear(); // Limpa o log de batalha para reiniciar a cada inimigo 
            SelecaoPersonagemController.heroisderrotados[SelecaoPersonagemController.slotativo]++;
            SelecaoPersonagemController.salvardados();
            indexinimigo++;
            carregarproximoinimigo();
        }
    }
    
    @FXML public void acaofugirbatalha(ActionEvent e) {
        trocartela("/view/selecaoonda.fxml");
    }
}